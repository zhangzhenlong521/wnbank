package cn.com.pushworld.wn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;

import com.ibm.db2.jcc.a.v;

import cn.com.infostrategy.bs.common.CommDMO;
import cn.com.infostrategy.to.common.HashVO;
import cn.com.infostrategy.to.common.WLTRemoteException;
import cn.com.infostrategy.to.mdata.BillVO;
import cn.com.infostrategy.to.mdata.UpdateSQLBuilder;
import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.MessageBox;
import cn.com.infostrategy.ui.common.SplashWindow;
import cn.com.infostrategy.ui.common.UIUtil;
import cn.com.infostrategy.ui.common.WLTButton;
import cn.com.infostrategy.ui.mdata.BillListPanel;
import cn.com.infostrategy.ui.mdata.BillListSelectListener;
import cn.com.infostrategy.ui.mdata.BillListSelectionEvent;
import cn.com.pushworld.wn.ui.WnSalaryServiceIfc;

/**
 * @author zzl
 *
 * 2019-3-27-����06:26:03
 * ��Ա����������ּƻ�
 */
public class GYServerKHPlan extends AbstractWorkPanel implements ActionListener, BillListSelectListener {
	private BillListPanel list = null;
	private WLTButton btn_ks, btn_end = null;
	private String str = null;
	private CommDMO dmo = new CommDMO();
	@Override
	public void initialize() {
		list = new BillListPanel("WN_GYKHPLAN_CODE1");
		btn_ks = new WLTButton("��ʼ���");
		btn_end = new WLTButton("�������");
		btn_ks.addActionListener(this);
		btn_end.addActionListener(this);
		list.addBillListButton(btn_ks);
		list.addBillListButton(btn_end);
		list.repaintBillListButton();
		list.addBillListSelectListener(this);
		this.add(list);
	}

	@Override
	public void actionPerformed(ActionEvent act) {
		if (act.getSource() == btn_ks) {
			gradeScore();
		}
		if (act.getSource() == btn_end) {
			gradeEnd();
		}
	}

	@Override
	public void onBillListSelectChanged(BillListSelectionEvent ect) {
		BillVO vo = list.getSelectedBillVO();
		if (vo.getStringValue("state").equals("������")) {
			btn_ks.setEnabled(false);
		} else if (vo.getStringValue("state").equals("���ֽ���")) {
			btn_ks.setEnabled(false);
			btn_end.setEnabled(false);
		} else {
			btn_ks.setEnabled(true);
			btn_end.setEnabled(true);
		}
	}

	public void gradeScore(int q) {
		try {
			final BillVO vo = list.getSelectedBillVO();
			HashVO[] vos = UIUtil.getHashVoArrayByDS(null, "select * from " + list.getTempletVO().getTablename() + " where state='������'");
			if (vos.length > 0) {
				MessageBox.show(this, "�������еĿ��˼ƻ������Ƚ��������еĿ��˼ƻ���Ȼ�����½����˼ƻ�");
				return;
			}
			HashVO[] timeVos = UIUtil.getHashVoArrayByDS(null, "select * from " + list.getTempletVO().getTablename() + " where PLANNAME='" + vo.getStringValue("PLANTIME") + "'");
			if (timeVos.length > 0) {
				MessageBox.show(this, "����ʱ��Ϊ��" + vo.getStringValue("PLANTIME") + "���ƻ��Ѿ����ڣ�");
				return;
			}
			final WnSalaryServiceIfc service = (WnSalaryServiceIfc) UIUtil.lookUpRemoteService(WnSalaryServiceIfc.class);
			new SplashWindow(this, new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent actionevent) {
					str = service.getSqlInsert(vo.getStringValue("PLANTIME"));
					UpdateSQLBuilder update = new UpdateSQLBuilder(list.getTempletVO().getTablename());
					update.setWhereCondition("id='" + vo.getStringValue("id") + "'");
					update.putFieldValue("state", "������");
					try {
						UIUtil.executeUpdateByDS(null, update.getSQL());
						list.refreshCurrSelectedRow();
					} catch (WLTRemoteException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			MessageBox.show(this, str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void gradeScore() {
		try {
//			BillVO selectedBillVO = list.getSelectedBillVO();
//			if("���ֽ���".equals(selectedBillVO.getStringValue("state"))){
//				MessageBox.show(this,"��ǰ���˼ƻ��Ѿ�����");
//			}
			final WnSalaryServiceIfc service = (WnSalaryServiceIfc) UIUtil.lookUpRemoteService(WnSalaryServiceIfc.class);
			new SplashWindow(this, new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent event) {
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					str = service.getSqlInsert(date);
				}
			});
			
//			UIUtil.executeUpdateByDS(null,"UPDATE WN_GYKHPLAN SET STATE='���ֿ�ʼ' WHERE ID='""'" );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void gradeEnd() {
		try {//�������:1.�޸�״̬;2.�����ܷ�
			HashVO[] vo = UIUtil.getHashVoArrayByDS(null, "SELECT distinct(USERCODE) AS USERCODE,STATE,PFTIME FROM WN_GYPF_TABLE WHERE STATE='������'");
			String result =UIUtil.getStringValueByDS(null, "SELECT COUNT(*) FROM WN_GYPF_TABLE WHERE STATE='������' OR FHRESULT IS NULL OR FHRESULT='δͨ��'");
			int  count=Integer.parseInt(result);
			int sumcount=0;
			if(count>0){
				sumcount=MessageBox.showOptionDialog(this, "��ǰ���˼ƻ��д����Ƿ�������δ��������δ���˵Ĺ�Ա���Ƿ������ǰ���˼ƻ�","��ʾ", new String[] { "��", "��" },1);
			}
			if(sumcount!=0){
				for (int i = 0; i < vo.length; i++) {
					HashVO v = vo[i];
					gradeEndEveryOne(v.getStringValue("USERCODE"));
				}
			}else{
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void gradeEnd(int w) {
		try {
			BillVO vo = list.getSelectedBillVO();
			HashVO[] vos = UIUtil.getHashVoArrayByDS(null, "select * from WN_GYPF_TABLE where 1=1 and PFTIME='" + vo.getStringValue("PLANTIME") + "' and KOUOFEN is null");
			if (vos.length > 0) {
				if (MessageBox.confirm(this, "��δ��������֣�ȷ��Ҫǿ�ƽ�����")) {
					UpdateSQLBuilder update = new UpdateSQLBuilder(list.getTempletVO().getTablename());
					update.setWhereCondition("id='" + vo.getStringValue("id") + "'");
					update.putFieldValue("state", "���ֽ���");
					UIUtil.executeUpdateByDS(null, update.getSQL());
					UpdateSQLBuilder update2 = new UpdateSQLBuilder("WN_GYPF_TABLE");
					update2.setWhereCondition("PFTIME='" + vo.getStringValue("PLANTIME") + "'");
					update2.putFieldValue("state", " ���ֽ���");
					UIUtil.executeUpdateByDS(null, update2.getSQL());
					list.refreshData();
					MessageBox.show(this, "������ֳɹ�");
				}
			} else {
				UpdateSQLBuilder update = new UpdateSQLBuilder(list.getTempletVO().getTablename());
				update.setWhereCondition("id='" + vo.getStringValue("id") + "'");
				update.putFieldValue("state", "���ֽ���");
				UIUtil.executeUpdateByDS(null, update.getSQL());
				UpdateSQLBuilder update2 = new UpdateSQLBuilder("WN_GYPF_TABLE");
				update2.setWhereCondition("PFTIME='" + vo.getStringValue("PLANTIME") + "'");
				update2.putFieldValue("state", "���ֽ���");
				UIUtil.executeUpdateByDS(null, update2.getSQL());
				list.refreshData();
				MessageBox.show(this, "������ֳɹ�");
			}
		} catch (Exception e) {
			MessageBox.show(this, "�������ʧ��");
			e.printStackTrace();
		}
	}

	public void gradeEndEveryOne(String usercode) {
		try {
			Double KOUOFEN = 0.0;
			Double result = 0.0;
			UpdateSQLBuilder update = new UpdateSQLBuilder("wnSalaryDb.WN_GYPF_TABLE");
			HashVO[] vo = UIUtil.getHashVoArrayByDS(null, "select * from wnSalaryDb.WN_GYPF_TABLE where usercode='" + usercode + "' and state='������' Order by ID");
			List list = new ArrayList<String>();
			for (int i = 0; i < vo.length - 1; i++) {
				String koufenValue = vo[i].getStringValue("KOUFEN");
				String defenValue = vo[i].getStringValue("FENZHI");
				if (koufenValue == null || "".equals(koufenValue)) {
					koufenValue = defenValue;
				}
				if (Double.parseDouble(koufenValue) > Double.parseDouble(defenValue)) {
					koufenValue = defenValue;
				}
				KOUOFEN = Double.parseDouble(koufenValue);
				result = result + KOUOFEN;
				update.setWhereCondition("id='" + vo[i].getStringValue("id") + "'");
				update.putFieldValue("KOUOFEN", KOUOFEN);
				update.putFieldValue("state", "���ֽ���");
				list.add(update.getSQL());
			}
			double sumfen = 100.00 - result;
			String sumSQL = "update wnSalaryDb.WN_GYPF_TABLE set KOUOFEN='" + sumfen + "',state='���ֽ���' where usercode='" + usercode + "' and xiangmu='�ܷ�'";
			System.out.println(sumSQL);
			list.add(sumSQL);
			if(list.size()>0){
				UIUtil.executeBatchByDS(null, list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
