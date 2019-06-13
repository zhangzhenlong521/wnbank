package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.ClientEnvironment;
import cn.com.infostrategy.ui.mdata.BillListPanel;
import cn.com.infostrategy.ui.mdata.BillListSelectListener;
/**
 * 
 * @author ZPY[助农商户维护信息查询]
 * 2019-05-17
 */
public class ZNkhSearachWKPanel  extends AbstractWorkPanel implements ActionListener{
    private  String str="";
    private  BillListPanel listPanel;
	@Override
	public void initialize() {
		listPanel=new BillListPanel("V_WN_ZNWH_ZPY_Q01");
		//获取到快速查询的按钮的查询事件
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
		//获取到当前登录人的客户经理名称
		String name=ClientEnvironment.getInstance().getLoginUserName();//获取到当前登录人的姓名
		//获取到当亲的查询条件
		String sqlCondition = listPanel.getQuickQueryPanel().getQuerySQLCondition();//获取对应的查询条件
		String sql="select * from V_WN_ZNWH  where 1=1 and B='"+name+"'"+sqlCondition;
		listPanel.QueryData(sql);
	}

	

}
