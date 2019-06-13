package cn.com.pushworld.wn.ui;

import cn.com.infostrategy.ui.common.WLTRemoteCallServiceIfc;

public interface WnSalaryServiceIfc extends WLTRemoteCallServiceIfc{
	/**
	 * zzl[柜员服务质量评价]
	 * @return
	 */
	public String getSqlInsert(String time);
	/**
	 * zpy[部门指标打分]
	 */
	public String getBMsql(String planid);
	public String gradeBMScoreEnd();
	/**
	 * zpy[导入全量数据]
	 * @return
	 */
	public String ImportAll();
	/**
	 * zpy[导入一天的数据]
	 * @param date:日期，具体格式为:[20190301]
	 * @return
	 */
	public String ImportDay(String date);
	/**
	 * zpy[导入某张表某天的数据]
	 * @param filePath
	 * @return
	 */
	public String ImportOne(String filePath);
	/**
	 * zzl[贷款客户经理信息更新]
	 * @return
	 */
	public String getChange();
	/**
	 * zzl[存款客户经理信息更新]
	 * @return
	 */
	public String getCKChange();
	/**
	 * zzl[贷款户数完成比]
	 */
	public String getDKFinishB(String date);
	/**
	 * zzl[贷款余额新增完成比]
	 */
	public String getDKBalanceXZ(String date);
	/**
	 * zzl[贷款户数新增完成比]
	 */
	public String getDKHouseholdsXZ(String date);
	/**
	 * zzl [收回表外不良贷款完成比]
	 */
	public String getBadLoans(String date);
	/**
	 * zzl[收回存量不良贷款完成比&不良贷款压降]
	 */
	public String getTheStockOfLoan(String date);

}
