package cn.com.pushworld.wn.bs;

import java.math.BigDecimal;
import java.util.HashMap;

import cn.com.infostrategy.bs.common.CommDMO;
/**
 * 
 * @author zzl
 *
 * 2019-4-21-下午12:21:09
 * 上月存款日均数
 */
public class SMonthDeptCkscoleCountRJ {
private static CommDMO dmo = new CommDMO();
/**
 * 上月存款日均数合计
 * @param date
 * @param dept
 * @return
 */
	public HashMap<String, String> getCountYearRJ(String date,String [] deptids,HashMap<String, String> deptMap){
		HashMap<String, String> map = new HashMap<String, String>();
		HashMap<String, String> dghqmap=getDGHQBMRJSY(date);//对公活期
		HashMap<String, String> dgdqmap=getDGDQBMRJSY(date);//对公定期
		HashMap<String, String> dshqmap=getDSHQBMRJSY(date);//对私活期
		HashMap<String, String> dsdqmap=getDSDQBMRJSY(date);//对私定期
		for(int i=0;i<deptids.length;i++){
			BigDecimal db=new BigDecimal("0.0");
			if(dghqmap.get(deptMap.get(deptids[i]))!=null){
				db=db.add(new BigDecimal(dghqmap.get(deptMap.get(deptids[i]))));
			}
			if(dgdqmap.get(deptMap.get(deptids[i]))!=null){
				db=db.add(new BigDecimal(dgdqmap.get(deptMap.get(deptids[i]))));
			}
			if(dshqmap.get(deptMap.get(deptids[i]))!=null){
				db=db.add(new BigDecimal(dshqmap.get(deptMap.get(deptids[i]))));		
					}
			if(dsdqmap.get(deptMap.get(deptids[i]))!=null){
				db=db.add(new BigDecimal(dsdqmap.get(deptMap.get(deptids[i]))));
			}
			map.put(deptMap.get(deptids[i]), db.toString());
		}

		return map;
	}
	/**
	 * 对公存款定期部门日均余额-上月Map getDGDQBMRJSY 
	 * 月初日期 月末日期  上月的月初日期 上月的月末日期  年初月初日期  年末日期  当月天数 上月天数 年初天数
	 */
	public HashMap<String, String> getDGDQBMRJSY(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		String[] time = date.split(";");
		try {
			map = dmo.getHashMapBySQLByDS(null, "select c.code code,a.acct_bal acct_bal from(select oact_inst_no oact_inst_no, (sum(acct_bal)/"+time[7].toString()+") as acct_bal  from wnbank.a_agr_dep_acct_ent_fx where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')>='" + time[2] + "' and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')<='" + time[3]+ "' group by oact_inst_no) a left join excel_tab_28 b on a.oact_inst_no=b.a left join pub_corp_dept c on substr(b.b,12)=c.name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 对公存款活期部门日均余额-上月Map getDGHQBMRJSY
	 * 月初日期 月末日期  上月的月初日期 上月的月末日期  年初月初日期  年末日期  当月天数 上月天数 年初天数
	 */
	public HashMap<String, String> getDGHQBMRJSY(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		String[] time = date.split(";");
		try {
			map = dmo.getHashMapBySQLByDS(null, "select c.code code,a.acct_bal acct_bal from(select oact_inst_no, (sum(acct_bal)/"+time[7].toString()+") as acct_bal  from wnbank.a_agr_dep_acct_ent_sv where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')>='" + time[2] + "' and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')<='" + time[3]+ "' group by oact_inst_no) a left join excel_tab_28 b on a.oact_inst_no=b.a left join pub_corp_dept c on substr(b.b,12)=c.name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 对私存款定期部门日均余额-上月Map getDSDQBMRJSY
	 * 月初日期 月末日期  上月的月初日期 上月的月末日期  年初月初日期  年末日期  当月天数 上月天数 年初天数
	 */
	public HashMap<String, String> getDSDQBMRJSY(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		String[] time = date.split(";");
		try {
			map = dmo.getHashMapBySQLByDS(null, "select c.code code,a.acct_bal acct_bal from(select oact_inst_no, (sum(acct_bal)/"+time[7].toString()+") as acct_bal  from wnbank.a_agr_dep_acct_psn_fx where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')>='" + time[2] + "' and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')<='" + time[3]+ "' group by oact_inst_no) a left join excel_tab_28 b on a.oact_inst_no=b.a left join pub_corp_dept c on substr(b.b,12)=c.name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 对私存款活期部门日均余额-上月Map  getDSHQBMRJSY
	 * 月初日期 月末日期  上月的月初日期 上月的月末日期  年初月初日期  年末日期  当月天数 上月天数 年初天数
	 */
	public HashMap<String, String> getDSHQBMRJSY(String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		String[] time = date.split(";");
		try {
			map = dmo.getHashMapBySQLByDS(null, "select c.code code,a.acct_bal acct_bal from(select oact_inst_no, (sum(f)/"+time[7].toString()+") as acct_bal  from wnbank.a_agr_dep_acct_psn_sv where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')>='" + time[2] + "' and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')<='" + time[3]+ "' group by oact_inst_no) a left join excel_tab_28 b on a.oact_inst_no=b.a left join pub_corp_dept c on substr(b.b,12)=c.name ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}



}
