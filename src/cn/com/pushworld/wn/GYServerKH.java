package cn.com.pushworld.wn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.com.infostrategy.bs.common.CommDMO;
import cn.com.infostrategy.to.common.HashVO;
import cn.com.infostrategy.to.mdata.BillVO;
import cn.com.infostrategy.to.mdata.UpdateSQLBuilder;
import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.ClientEnvironment;
import cn.com.infostrategy.ui.common.MessageBox;
import cn.com.infostrategy.ui.common.UIUtil;
import cn.com.infostrategy.ui.common.WLTButton;
import cn.com.infostrategy.ui.common.WLTSplitPane;
import cn.com.infostrategy.ui.mdata.BillListPanel;
import cn.com.infostrategy.ui.mdata.BillListSelectListener;
import cn.com.infostrategy.ui.mdata.BillListSelectionEvent;
import cn.com.infostrategy.ui.mdata.BillTreePanel;
import cn.com.infostrategy.ui.mdata.BillTreeSelectListener;
import cn.com.infostrategy.ui.mdata.BillTreeSelectionEvent;
import cn.com.infostrategy.ui.report.BillCellPanel;

/**
 * 
 * @author zzl
 *
 * 2019-3-29-����11:13:41
 * ��Ա������������:һ��һ�Σ�ί�ɻ�Ƹ�������Ĺ�Ա��֣�����������ˣ���������������κ�ί�ɻ�ƶ�ֻ�ܸ�������Ĺ�Ա��
 * ��������ſ��Բ鿴���еġ�
 */
public class GYServerKH extends AbstractWorkPanel implements BillTreeSelectListener, BillListSelectListener, ActionListener {

	private CommDMO dmo = new CommDMO();
	private BillTreePanel billTreePanel_Dept = null;//������
	private BillListPanel billListPanel_User_Post = null;//��Ա��
	private BillListPanel billListPanel_User_check = null;//��Ա����
	private WLTSplitPane splitPanel_all = null;
	private String str_currdeptid, str_currdeptname = null; // ��ǰ����ID����������
	private String panelStyle = "3";
	private BillCellPanel billCellPanel = null;//��Ա���ֱ�
	private WLTSplitPane splitPanel = null;
	private WLTButton btn_save, btn_end, btn_verify;//ZPY��2019-05-20���������˹���
	private JPanel panel = new JPanel();
	private String PFUSERNAME = ClientEnvironment.getInstance().getLoginUserName();//��¼��Ա����
	private String PFSUERCODE = ClientEnvironment.getInstance().getLoginUserCode();//��¼��Ա����
	private String PFUSERDEPT = ClientEnvironment.getInstance().getLoginUserDeptId();//��¼��Ա������
	private HashMap USERCODES = null;
	private HashMap USERTYPE = null;
	private String pfTime = null;

	public void initialize() {
		//��ȡ����ǰ��¼�˵Ļ���code
		try {
			USERCODES = dmo.getHashMapBySQLByDS(null, "select id,code from PUB_CORP_DEPT");
			USERTYPE = dmo.getHashMapBySQLByDS(null, "select USERCODE,POSTNAME from  V_PUB_USER_POST_1 ");//������Ա���ֱ���ͬ���˿�����Ա���ֱ��е�����
		} catch (Exception e) {
			e.printStackTrace();
		}
		billTreePanel_Dept = new BillTreePanel(getCorpTempletCode());//������: ��ȡ��Ӧ������
		billListPanel_User_Post = new BillListPanel("V_PUB_USER_POST_zpy");//�Ҳ�������Ա��
		billListPanel_User_Post.addBillListSelectListener(this);
		//		billCellPanel = new BillCellPanel("��Ա���񿼺����۵�", null, null, true, false);
		billListPanel_User_check = new BillListPanel("WN_GYPF_TABLE_CODE1");//��Ա���ֱ�
		splitPanel_all = new WLTSplitPane(WLTSplitPane.HORIZONTAL_SPLIT);//ˮƽ���
		billTreePanel_Dept.queryDataByCondition("1=1 ");
		billTreePanel_Dept.queryDataByCondition("CORPTYPE!='����' AND CORPTYPE!='����' AND CORPTYPE!='ĸ��' ");
		splitPanel = new WLTSplitPane(WLTSplitPane.VERTICAL_SPLIT, billListPanel_User_Post, billListPanel_User_check);//���²��
		splitPanel.setDividerLocation(200);
		btn_end = new WLTButton("��������");
		btn_save = new WLTButton("����");
		btn_save.addActionListener(this);
		btn_end.addActionListener(this);
		billListPanel_User_check.addBillListButton(btn_save);
		billListPanel_User_check.addBillListButton(btn_end);
		if ("����".equals(USERTYPE.get(PFSUERCODE).toString())) {
			//
			btn_verify = new WLTButton("���ָ���");
			btn_verify.addActionListener(this);
			billListPanel_User_check.addBillListButton(btn_verify);
			billListPanel_User_check.setItemEditable("fhreason", true);
		} else {
			billListPanel_User_check.setItemEditable("fhreason", false);
		}
		billListPanel_User_check.repaintBillListButton();
		billListPanel_User_check.addBillListSelectListener(this);

		String PFDEPTCODE = USERCODES.get(PFUSERDEPT).toString();//��ȡ����ǰ�Ļ���code
		billListPanel_User_Post.queryDataByCondition("deptcode='" + PFDEPTCODE + "' and POSTNAME like '%��Ա%'", "seq,usercode");
		//��ȡ����ǰ��¼�˵Ļ���code
		System.out.println("��ǰ��¼��Ա�Ļ�����:" + PFDEPTCODE);
		if ("282007".equals(PFDEPTCODE)) {
			splitPanel_all.add(billTreePanel_Dept);//�����ǰ��¼�����ڿͻ����񲿣�����ʾ������
			billTreePanel_Dept.addBillTreeSelectListener(this);
		}

		//		else{
		//			//��ȡ����ǰѡ�еĹ�Ա
		//			
		//			
		//            //����ʾ��������ǰ���£����ݻ����Ż�ȡ����ǰ�����Ĺ�Ա
		//			String userType=USERTYPE.get(PFSUERCODE).toString();
		//			if("ί�ɻ��".equals(userType)){//ί�ɻ���޷�ֻ�ܽ������֣����ܶԷ������и���
		//				billListPanel_User_check.QueryData("select * from WN_GYPF_TABLE where 1=1 and  USERNAME='"+gyBillVo.getStringValue("USERNAME")+"' AND pftime='"+pfTime+"'");
		//		        String[] notVisiable=new String[]{"PFUSERNAME","PFSUERCODE","PFUSERDEPT","FHUSERNAME","FHUSERDEPT","FHTIME","FHREASON"};
		//		        billListPanel_User_check.setItemsVisible(notVisiable, false);//ί�ɻ��������Щ�ֶβ��ɼ�
		//			}
		//		}
		splitPanel_all.add(splitPanel);
		splitPanel_all.setDividerLocation(180);
		this.add(splitPanel_all);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_save) {
			try {
				StringBuffer sb = new StringBuffer();
				BillVO[] bos = billListPanel_User_check.getBillVOs();
				Double FENZHI = 0.0;
				Double KOUOFEN = 0.0;
				Double result = 0.0;
				String pfreason = "";
				int count = 1;
				BillVO vov = billListPanel_User_check.getSelectedBillVO();
				String pfUsercode=vov.getStringValue("USERCODE");
				pfTime=dmo.getStringValueByDS(null, "SELECT max(PFTIME) FROM WN_GYPF_TABLE WHERE USERCODE='"+pfUsercode+"'");
				if (vov == null) {
					MessageBox.show(this, "��ѡ��һ�����ݲ���!");
					return;
				}
				List list = new ArrayList<String>();
				System.out.println("��ǰ�����ı�Ϊ:" + billListPanel_User_check.getTempletVO().getTablename());
				UpdateSQLBuilder update = new UpdateSQLBuilder(billListPanel_User_check.getTempletVO().getTablename());
				for (int i = 0; i < bos.length - 1; i++) {
					FENZHI = Double.parseDouble(bos[i].getStringValue("FENZHI"));
					pfreason = bos[i].getStringValue("FHREASON");
					if (bos[i].getStringValue("KOUOFEN") != null) {
						KOUOFEN = Double.parseDouble(bos[i].getStringValue("KOUOFEN"));
						if (FENZHI < KOUOFEN) {
							sb.append("��" + (count) + "��������ĿΪ[" + bos[i].getStringValue("XIANGMU") + "],ָ��Ϊ[" + bos[i].getStringValue("ZHIBIAO") + "]�۷�����ڷ�ֵ   \n");
						}
					} else {
						sb.append("��" + (count) + "��������ĿΪ[" + bos[i].getStringValue("XIANGMU") + "],ָ��Ϊ[" + bos[i].getStringValue("ZHIBIAO") + "]�۷���Ϊ��   \n");
					}
					result = result + KOUOFEN;
					count = count + 1;
					update.setWhereCondition("id='" + bos[i].getStringValue("id") + "'");
					update.putFieldValue("KOUOFEN", KOUOFEN);
					update.putFieldValue("FHREASON", pfreason);
					list.add(update.getSQL());
				}
				if (sb.length() <= 0) {
					billListPanel_User_check.setRealValueAt(String.valueOf(100 - result), bos.length - 1, "KOUOFEN");
					update.setWhereCondition("id='" + bos[bos.length - 1].getStringValue("id") + "'");
					update.putFieldValue("KOUOFEN", 100 - result);
					list.add(update.getSQL());
					UpdateSQLBuilder update2 = new UpdateSQLBuilder(billListPanel_User_check.getTempletVO().getTablename());
					update2.setWhereCondition("USERCODE='" + vov.getStringValue("USERCODE") + "' and USERDEPT='" + vov.getStringValue("USERDEPT") + "'");
					update2.putFieldValue("PFUSERNAME", PFUSERNAME);
					update2.putFieldValue("PFSUERCODE", PFSUERCODE);
					String PFDEPTCODE = USERCODES.get(PFUSERDEPT).toString();
					update2.putFieldValue("PFUSERDEPT", PFDEPTCODE);
					update2.putFieldValue("FHRESULT", "δ����");
					list.add(update2.getSQL());
					UIUtil.executeBatchByDS(null, list);
					MessageBox.show(this, "�������");
					billListPanel_User_check.refreshData();
				} else {
					MessageBox.show(this, sb.toString());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (e.getSource() == btn_end) {
			try {
				//��ȡ����ǰѡ�й�Ա��Ϣ����
				Double FENZHI = 0.0;
				Double KOUOFEN = 0.0;
				Double result = 100.0;
				BillVO gyselected = billListPanel_User_check.getSelectedBillVO();
				String gyUserCode = gyselected.getStringValue("USERCODE");
				String gyUserName = gyselected.getStringValue("USERNAME");
				pfTime=dmo.getStringValueByDS(null,"SELECT max(PFTIME) FROM WN_GYPF_TABLE WHERE USERCODE='"+gyUserCode+"'");
				String[] state = UIUtil.getStringArrayFirstColByDS(null, "select state from WN_GYPF_TABLE where state='������' AND USERCODE='" + gyUserCode + "'");//
				if (state.length <= 0) {
					MessageBox.show(this, "��ǰ��Ա��" + gyUserName + "���������������Ѿ������������ظ�������");
					return;
				}
				HashVO[] vos = UIUtil.getHashVoArrayByDS(null, "select USERDEPT,USERNAME,PFTIME from WN_GYPF_TABLE where 1=1 and KOUOFEN is null group by USERDEPT,USERNAME,PFTIME order by USERDEPT");
				BillVO[] bos = billListPanel_User_check.getBillVOs();
				StringBuffer sb = new StringBuffer();
				HashMap<String, String> map = UIUtil.getHashMapBySQLByDS(null, "select id,name from pub_corp_dept");
				UpdateSQLBuilder update3=new UpdateSQLBuilder(billListPanel_User_check.getTempletVO().getTablename());
				update3.setWhereCondition("USERCODE='"+gyUserCode+"' and PFTIME='"+pfTime+"'");
				MessageBox.show(this,billListPanel_User_check.getTempletVO().getTablename());
				for (int j = 0; j < bos.length; j++) {
					if(bos[j].getStringValue("KOUOFEN")==null||bos[j].getStringValue("KOUOFEN").isEmpty()){
						sb.append("����Ϊ[" + map.get(bos[j].getStringValue("USERDEPT")) + "],��Ա����Ϊ[" + bos[j].getStringValue("USERNAME") + "]����δ��ɣ� \n");
					}
				}
				if (sb.length()> 0) {
					if (MessageBox.confirm(this, "��ǰ��Ա��"+gyUserName+"����δ���,ȷ��ǿ�ƽ�����  \n" + sb.toString())) {
						UpdateSQLBuilder update = new UpdateSQLBuilder(billListPanel_User_check.getTempletVO().getTablename());
						
					    List<String> sqlList=new ArrayList<String>();
						UpdateSQLBuilder update2 = new UpdateSQLBuilder("WN_GYPF_TABLE");
						for(int j = 0; j < bos.length; j++){
							if(bos[j].getStringValue("XIANGMU").equals("�ܷ�")){
								continue;
							}
							update.setWhereCondition(" USERCODE='"+gyUserCode+"' and PFTIME='"+pfTime+"' and zhibiao='"+bos[j].getStringValue("zhibiao")+"'");
							//��ȡ���۷���
							FENZHI=Double.parseDouble(bos[j].getStringValue("FENZHI"));
							if(bos[j].getStringValue("KOUOFEN")==null){
								KOUOFEN=0.0;
							}
							KOUOFEN=Double.parseDouble(bos[j].getStringValue("KOUOFEN"));
							if (FENZHI <= KOUOFEN) {
							    FENZHI=KOUOFEN;
							}
							System.out.println("��ǰ�۷���:"+bos[j].getStringValue("xiangmu")+",�۷�="+KOUOFEN);
							result=result-KOUOFEN;
							update.putFieldValue("KOUOFEN", KOUOFEN);
							update.putFieldValue("state", "���ֽ���");
							sqlList.add(update.getSQL());
						}
						UIUtil.executeBatchByDS(null,sqlList);
						update2.setWhereCondition("USERCODE='" + gyUserCode + "' and pftime='" + pfTime + "' and xiangmu='�ܷ�'");
						update2.putFieldValue("state", "���ֽ���");
						update2.putFieldValue("kouofen",result);
						UIUtil.executeUpdateByDS(null, update2.getSQL());
						MessageBox.show(this, "���ֽ����ɹ�");
						billListPanel_User_check.refreshData();
					}
				} else {
					UpdateSQLBuilder update = new UpdateSQLBuilder(billListPanel_User_check.getTempletVO().getTablename());
					List<String> sqlList=new ArrayList<String>();
					UpdateSQLBuilder update2 = new UpdateSQLBuilder("WN_GYPF_TABLE");
					for(int j = 0; j < bos.length; j++){
						update.setWhereCondition(" USERCODE='"+gyUserCode+"' and PFTIME='"+pfTime+"' and zhibiao='"+bos[j].getStringValue("zhibiao")+"'");
						//��ȡ���۷���
						if(bos[j].getStringValue("XIANGMU").equals("�ܷ�")){
							continue;
						}
						FENZHI=Double.parseDouble(bos[j].getStringValue("FENZHI"));
						if(bos[j].getStringValue("KOUOFEN")==null){
							KOUOFEN=0.0;
						}
						KOUOFEN=Double.parseDouble(bos[j].getStringValue("KOUOFEN"));
						if (FENZHI <= KOUOFEN) {
						    FENZHI=KOUOFEN;
						}
						System.out.println("��ǰ�۷���:"+bos[j].getStringValue("xiangmu")+",�۷�="+KOUOFEN);
						update.putFieldValue("state", "���ֽ���");
						update.putFieldValue("KOUOFEN",KOUOFEN);
						System.out.println("��ǰִ�е�sql:"+update.getSQL());
						sqlList.add(update.getSQL());
						result=result-KOUOFEN;
					}
				    dmo.executeBatchByDS(null, sqlList);
					update2.setWhereCondition("USERCODE='" + gyUserCode + "' and pftime='" + pfTime + "' and xiangmu='�ܷ�'");
					update2.putFieldValue("state", "���ֽ���");
					update2.putFieldValue("kouofen",result);
					UIUtil.executeUpdateByDS(null, update2.getSQL());
					MessageBox.show(this, "���ֽ����ɹ�");
					billListPanel_User_check.refreshData();
				}
			} catch (Exception eq) {
				MessageBox.show(this, "���ֽ���ʧ��");
				eq.printStackTrace();
			}
		} else if (e.getSource() == btn_verify) {//������˹���
			VersifySource();
		}
	}

	/**
	 * ���ָ��˹���
	 */
	private void VersifySource() {
		try {
			
			BillVO fhUser = billListPanel_User_Post.getSelectedBillVO();
			String fhUserCode = fhUser.getStringValue("USERCODE");
			HashVO[] pfNotEnd = dmo.getHashVoArrayByDS(null, "SELECT  * FROM WN_GYPF_TABLE WHERE PFTIME='"+pfTime+"' and USERCODE='"+fhUserCode+"' and state='������'");
			if(pfNotEnd.length>0){
				MessageBox.show(this,"��ǰ��Ա��"+fhUser.getStringValue("USERNAME")+"��������δ�������޷����з������ˡ�");
			    return;
			}
			//��ȡ����������Ա����Ϣ
			int result = MessageBox.showOptionDialog(this, "��ǰ��Ա�������и���", "��ʾ", new String[] { "����ͨ��", "�����˻�" }, 1);
			String FHUSERNAME = PFUSERNAME;
			String FHUSERDEPT = USERCODES.get(PFUSERDEPT).toString();
			String FHTIME = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
			pfTime = dmo.getStringValueByDS(null, "SELECT max(PFTIME) FROM WN_GYPF_TABLE WHERE USERCODE='" + fhUserCode + "'");//����ʱ������
			UpdateSQLBuilder update = new UpdateSQLBuilder("WN_GYPF_TABLE");
			update.setWhereCondition("pfTime='" + pfTime + "' and USERCODE='" + fhUserCode + "'");
			update.putFieldValue("FHUSERNAME", PFUSERNAME);
			update.putFieldValue("FHUSERDEPT", FHUSERDEPT);
			update.putFieldValue("FHTIME", FHTIME);
		    
		    if (result == 0) {//���ͨ��
				//���ø�����Ϣ
				update.putFieldValue("FHRESULT", "����ͨ��");
				dmo.executeUpdateByDS(null, update.getSQL());//ִ���޸�
				billListPanel_User_check.setItemEditable("FHREASON", false);//���ֽ������޷��޸���˲�ͨ������
				billListPanel_User_check.refreshData();
			} else {
				update.putFieldValue("FHRESULT", "����δͨ��");
				String pfreason = JOptionPane.showInputDialog("�����븴��δͨ��������:");
				update.putFieldValue("FHREASON", pfreason);
				update.putFieldValue("STATE", "������");//��״̬�����ֽ����޸�Ϊ�����У���ί�ɻ�Ƽ�������
				dmo.executeUpdateByDS(null, update.getSQL());//ִ���޸�
				billListPanel_User_check.refreshData();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������ѡ���¼�
	 */
	@Override
	public void onBillTreeSelectChanged(BillTreeSelectionEvent _event) {
		str_currdeptid = _event.getCurrSelectedVO().getStringValue("id"); //
		str_currdeptname = _event.getCurrSelectedVO().getStringValue("name"); //
		if (billListPanel_User_Post != null) {
			billListPanel_User_Post.queryDataByCondition("deptid='" + str_currdeptid + "' and POSTNAME like '%��Ա%'", "seq,usercode"); //
		}
	}

	/**
	 * ��û���ģ�� 
	 * @return
	 */
	public String getCorpTempletCode() {
		return "PUB_CORP_DEPT_CODE1"; // ��򵥵�
	}

	@Override
	public void onBillListSelectChanged(BillListSelectionEvent e) {
		try {
			if (e.getSource() == billListPanel_User_Post) {
				BillVO vo = billListPanel_User_Post.getSelectedBillVO();
				billListPanel_User_check.QueryDataByCondition("USERDEPT='" + vo.getStringValue("deptid") + "' and usercode='" + vo.getStringValue("usercode") + "'");
			}
			if (e.getSource() == billListPanel_User_check) {
				BillVO vo = billListPanel_User_check.getSelectedBillVO();
				if (vo.getStringValue("state").equals("���ֽ���")) {
					btn_save.setEnabled(false);
					billListPanel_User_check.setItemEditable("KOUOFEN", false);//�������֮�����ò��ɱ༭
				} else {
					btn_save.setEnabled(true);
					billListPanel_User_check.setItemEditable("KOUOFEN", true);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
