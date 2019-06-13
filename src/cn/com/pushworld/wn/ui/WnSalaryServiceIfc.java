package cn.com.pushworld.wn.ui;

import cn.com.infostrategy.ui.common.WLTRemoteCallServiceIfc;

public interface WnSalaryServiceIfc extends WLTRemoteCallServiceIfc{
	/**
	 * zzl[��Ա������������]
	 * @return
	 */
	public String getSqlInsert(String time);
	/**
	 * zpy[����ָ����]
	 */
	public String getBMsql(String planid);
	public String gradeBMScoreEnd();
	/**
	 * zpy[����ȫ������]
	 * @return
	 */
	public String ImportAll();
	/**
	 * zpy[����һ�������]
	 * @param date:���ڣ������ʽΪ:[20190301]
	 * @return
	 */
	public String ImportDay(String date);
	/**
	 * zpy[����ĳ�ű�ĳ�������]
	 * @param filePath
	 * @return
	 */
	public String ImportOne(String filePath);
	/**
	 * zzl[����ͻ�������Ϣ����]
	 * @return
	 */
	public String getChange();
	/**
	 * zzl[���ͻ�������Ϣ����]
	 * @return
	 */
	public String getCKChange();
	/**
	 * zzl[�������ɱ�]
	 */
	public String getDKFinishB(String date);
	/**
	 * zzl[�������������ɱ�]
	 */
	public String getDKBalanceXZ(String date);
	/**
	 * zzl[�����������ɱ�]
	 */
	public String getDKHouseholdsXZ(String date);
	/**
	 * zzl [�ջر��ⲻ��������ɱ�]
	 */
	public String getBadLoans(String date);
	/**
	 * zzl[�ջش�������������ɱ�&��������ѹ��]
	 */
	public String getTheStockOfLoan(String date);

}
