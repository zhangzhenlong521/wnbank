package cn.com.pushworld.wn.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.common.WLTButton;
import cn.com.infostrategy.ui.mdata.BillListPanel;

public class GydxdfWKPanel extends AbstractWorkPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private BillListPanel listPanel;
	private WLTButton btn_ks, btn_end;//��ʼ��֣��������
	@Override
	public void initialize() {
		listPanel = new BillListPanel("WN_GYDXPLAN_ZPY_Q01");
		btn_ks = new WLTButton("��ʼ���");
		btn_ks.addActionListener(this);
		btn_end = new WLTButton("�������");
		btn_end.addActionListener(this);
		listPanel.addBatchBillListButton(new WLTButton[] { btn_ks, btn_end });
		listPanel.repaintBillListButton();
		this.add(listPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_ks) {//��ʼ���ˣ����ɴ�ּƻ�
			gradeScore();
		} else if (e.getSource() == btn_end) {//��������
			gradeEnd();
		}
	}
	private void gradeScore() {//��ʼ����
	}

	private void gradeEnd() {//��������
	}

}
