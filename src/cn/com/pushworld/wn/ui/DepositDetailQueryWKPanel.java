package cn.com.pushworld.wn.ui;

import cn.com.infostrategy.to.mdata.BillVO;
import cn.com.infostrategy.ui.common.AbstractWorkPanel;
import cn.com.infostrategy.ui.mdata.BillListDialog;
import cn.com.infostrategy.ui.mdata.BillListHtmlHrefEvent;
import cn.com.infostrategy.ui.mdata.BillListHtmlHrefListener;
import cn.com.infostrategy.ui.mdata.BillListPanel;

/**
 * 
 * @author zzl
 *
 * 2019-5-29-下午03:34:56
 * 存款明细查询
 */
public class DepositDetailQueryWKPanel extends AbstractWorkPanel implements BillListHtmlHrefListener{
	private BillListPanel list=null;

	@Override
	public void initialize() {
		list=new BillListPanel("V_WN_DEPOSIT_MXCX_CODE1");
		list.addBillListHtmlHrefListener(this);
		this.add(list);
		
	}

	@Override
	public void onBillListHtmlHrefClicked(BillListHtmlHrefEvent event) {
		if(event.getSource()==list){
		    BillVO vo= list.getSelectedBillVO();
		    BillListDialog dialog=new BillListDialog(this,"有效户数查询","V_S_LOAN_HK_CODE1");
		    dialog.getBilllistPanel().QueryDataByCondition("xd_col1='"+vo.getStringValue("xd_col1")+"'");
		    dialog.getBtn_confirm().setVisible(false);
		    dialog.setVisible(true);
		}
		
	}
}
