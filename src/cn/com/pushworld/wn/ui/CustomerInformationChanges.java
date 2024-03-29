package cn.com.pushworld.wn.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractAction;

import cn.com.infostrategy.bs.common.CommDMO;
import cn.com.infostrategy.to.common.TBUtil;
import cn.com.infostrategy.to.common.WLTRemoteException;
import cn.com.infostrategy.to.mdata.UpdateSQLBuilder;
import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.MessageBox;
import cn.com.infostrategy.ui.common.SplashWindow;
import cn.com.infostrategy.ui.common.UIUtil;
import cn.com.infostrategy.ui.common.WLTButton;

/**
 * 
 * @author zzl
 *
 * 2019-4-26-上午09:36:19
 * 客户经理信息变更    本月的贷款客户经理信息比对上月的贷款客户经理信息，修改上月的客户经理对应贷款信息
 */
public class CustomerInformationChanges extends AbstractWorkPanel implements ActionListener {
	private WLTButton btn_BG = new WLTButton("贷款客户经理信息变更");
	private WLTButton btn_BGCK = new WLTButton("存款客户经理信息变更");
	

	@Override
	public void initialize() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		btn_BG.setPreferredSize(new Dimension(130, 50));
		btn_BG.addActionListener(this);
		btn_BGCK.setPreferredSize(new Dimension(130, 50));
		btn_BGCK.addActionListener(this);
		this.add(btn_BG);
		this.add(btn_BGCK);
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		if(a.getSource()==btn_BG){
			new SplashWindow(this, new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					getChange();
				}
			});
		}if(a.getSource()==btn_BGCK){
			new SplashWindow(this, new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					getChangeCK();
				}
			});
		}
		
	}

	private void getChange() {
		try {
			WnSalaryServiceIfc service = (WnSalaryServiceIfc) UIUtil.lookUpRemoteService(WnSalaryServiceIfc.class);
			String str=service.getChange();
			MessageBox.show(this,str);
		}  catch (Exception e) {
			MessageBox.show(this,"客户经理信息变更失败");
			e.printStackTrace();
		}
	}
	private void getChangeCK() {
		try {
			WnSalaryServiceIfc service = (WnSalaryServiceIfc) UIUtil.lookUpRemoteService(WnSalaryServiceIfc.class);
			String str=service.getCKChange();
			MessageBox.show(this,str);
		}  catch (Exception e) {
			MessageBox.show(this,"客户经理信息变更失败");
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Date date=new Date();
		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。    
		cal.setTime(date);
//		cal.add(Calendar.YEAR, -1);//取当前日期的前一天. 
//		cal.add(Calendar.MONTH, -1);//取当前日期的后一天. 
		cal.set(Calendar.DAY_OF_MONTH,0);
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");  
		df.format(cal.getTime());
		System.out.println("-0>>>>"+df.format(cal.getTime()));
		Calendar cal2 = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。    
		cal2.setTime(date);
		cal2.set(Calendar.DAY_OF_MONTH,31);
		cal2.add(Calendar.MONTH, -2);//取当前日期的后一天.
		df.format(cal2.getTime());
		System.out.println("-1>>>>"+df.format(cal2.getTime()));
	}
}
