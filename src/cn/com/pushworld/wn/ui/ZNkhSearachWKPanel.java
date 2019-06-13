package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.ClientEnvironment;
import cn.com.infostrategy.ui.mdata.BillListPanel;
import cn.com.infostrategy.ui.mdata.BillListSelectListener;
/**
 * 
 * @author ZPY[��ũ�̻�ά����Ϣ��ѯ]
 * 2019-05-17
 */
public class ZNkhSearachWKPanel  extends AbstractWorkPanel implements ActionListener{
    private  String str="";
    private  BillListPanel listPanel;
	@Override
	public void initialize() {
		listPanel=new BillListPanel("V_WN_ZNWH_ZPY_Q01");
		//��ȡ�����ٲ�ѯ�İ�ť�Ĳ�ѯ�¼�
		listPanel.getQuickQueryPanel().addBillQuickActionListener(this);
		listPanel.repaintBillListButton();
		this.add(listPanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==listPanel.getQuickQueryPanel()){
			QuickQuery();
		}
	}

	private void QuickQuery() {
		//��ȡ����ǰ��¼�˵Ŀͻ���������
		String name=ClientEnvironment.getInstance().getLoginUserName();//��ȡ����ǰ��¼�˵�����
		//��ȡ�����׵Ĳ�ѯ����
		String sqlCondition = listPanel.getQuickQueryPanel().getQuerySQLCondition();//��ȡ��Ӧ�Ĳ�ѯ����
		String sql="select * from V_WN_ZNWH  where 1=1 and B='"+name+"'"+sqlCondition;
		listPanel.QueryData(sql);
	}

	

}
