package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.WLTButton;
import cn.com.infostrategy.ui.mdata.BillListPanel;

public class GydxdfWKPanel extends AbstractWorkPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private BillListPanel listPanel;
	private WLTButton btn_ks, btn_end;//开始打分，结束打分
	@Override
	public void initialize() {
		listPanel = new BillListPanel("WN_GYDXPLAN_ZPY_Q01");
		btn_ks = new WLTButton("开始打分");
		btn_ks.addActionListener(this);
		btn_end = new WLTButton("结束打分");
		btn_end.addActionListener(this);
		listPanel.addBatchBillListButton(new WLTButton[] { btn_ks, btn_end });
		listPanel.repaintBillListButton();
		this.add(listPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_ks) {//开始考核，生成打分计划
			gradeScore();
		} else if (e.getSource() == btn_end) {//结束考核
			gradeEnd();
		}
	}
	private void gradeScore() {//开始考核
	}

	private void gradeEnd() {//结束考核
	}

}
