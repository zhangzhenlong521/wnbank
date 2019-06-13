package cn.com.pushworld.wn.bs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.com.infostrategy.bs.common.CommDMO;
import cn.com.infostrategy.bs.common.ServerEnvironment;
import cn.com.infostrategy.to.common.HashVO;
import cn.com.infostrategy.to.mdata.InsertSQLBuilder;
import cn.com.infostrategy.to.mdata.UpdateSQLBuilder;
import cn.com.pushworld.wn.ui.WnSalaryServiceIfc;

public class WnSalaryServiceImpl implements WnSalaryServiceIfc {
	private CommDMO dmo = new CommDMO();
	private  ImportDataDMO importDmo=new  ImportDataDMO();
    
	/**
	 * zzl[2019-3-28]  暂不使用
	 * 柜员服务质量考核评分
	 */
	public String getSqlInsert(String time, int num) {
		System.out.println(time + "当前时间");
		String str = null;
		InsertSQLBuilder insert = new InsertSQLBuilder("wn_gypf_table");
		List list = new ArrayList<String>();
		String[][] date = getTowWeiDate();
		try {
			HashVO[] vos = dmo.getHashVoArrayByDS(null, "select * from V_PUB_USER_POST_1 where POSTNAME like '%柜员%'");
			String d = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 6);
			Date t = cal.getTime();
			String day7 = new SimpleDateFormat("yyyy.MM.dd").format(t);
			String timezone = d + "~" + day7;
			for (int i = 0; i < vos.length; i++) {
				for (int j = 0; j < date.length; j++) {
					String id = dmo.getSequenceNextValByDS(null, "WN_GYPF_TABLE");
					insert.putFieldValue("id", id);
					insert.putFieldValue("username", vos[i].getStringValue("USERNAME"));
					insert.putFieldValue("usercode", vos[i].getStringValue("usercode"));
					insert.putFieldValue("userdept", vos[i].getStringValue("deptid"));
					insert.putFieldValue("xiangmu", date[j][0]);
					insert.putFieldValue("zhibiao", date[j][1]);
					insert.putFieldValue("fenzhi", date[j][2]);
					insert.putFieldValue("khsm", date[j][4]);
					insert.putFieldValue("pftime", time);
					insert.putFieldValue("state", "评分中");
					insert.putFieldValue("seq", j + 1);
					insert.putFieldValue("timezone", timezone);
					list.add(insert.getSQL());
				}
			}
			dmo.executeBatchByDS(null, list);
			str = "柜员服务质量考核评分开始成功";
		} catch (Exception e) {
			str = "柜员服务质量考核评分开始失败";
			e.printStackTrace();
		}
		return str;
	}

	//zpy
	@Override
	public String getSqlInsert(String time) {
		System.out.println(time + "当前时间");
		String str = null;
		InsertSQLBuilder insert = new InsertSQLBuilder("wn_gypf_table");
		List list = new ArrayList<String>();
		String[][] date = getTowWeiDate();
		try {
			HashVO[] vos = dmo.getHashVoArrayByDS(null, "select * from V_PUB_USER_POST_1 where POSTNAME like '%柜员%'");
			String d = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 6);
			Date t = cal.getTime();
			String day7 = new SimpleDateFormat("yyyy.MM.dd").format(t);
			String timezone = d + "~" + day7;
			for (int i = 0; i < vos.length; i++) {
				for (int j = 0; j < date.length; j++) {
					String id = dmo.getSequenceNextValByDS(null, "WN_GYPF_TABLE");
					insert.putFieldValue("id", id);
					insert.putFieldValue("username", vos[i].getStringValue("USERNAME"));
					insert.putFieldValue("usercode", vos[i].getStringValue("usercode"));
					insert.putFieldValue("userdept", vos[i].getStringValue("deptid"));
					insert.putFieldValue("xiangmu", date[j][0]);
					insert.putFieldValue("zhibiao", date[j][1]);
					insert.putFieldValue("fenzhi", date[j][2]);
					insert.putFieldValue("khsm", date[j][4]);
					insert.putFieldValue("pftime", time);
					insert.putFieldValue("state", "评分中");
					insert.putFieldValue("seq", j + 1);
					insert.putFieldValue("timezone", timezone);
					if ("总分".equals(date[j][0])) {
						insert.putFieldValue("KOUOFEN", "100.0");
					} else {
						insert.putFieldValue("KOUOFEN", "0.0");
					}

					list.add(insert.getSQL());
				}
			}
			dmo.executeBatchByDS(null, list);
			str = "柜员服务质量考核评分开始成功";
		} catch (Exception e) {
			str = "柜员服务质量考核评分开始失败";
			e.printStackTrace();
		}
		return str;
	}

	public String[][] getTowWeiDate() {
		String[][] date = new String[][] { { "职业形象", "着装", "5.0", "", "按要求着装，形象统一、规范、整洁" }, { "职业形象", "三姿", "5.0", "", "站姿、坐姿、走姿端庄大方" }, { "营业准备", "及时", "3.0", "", "准备工作要求营业前完成" }, { "物件摆放归位整齐", "抽屉", "3.0", "", "除各种机具外，物品摆放必须做到规范、有序，不得随意放置，各种凭证、印章、印泥、印鉴册、剪刀、胶水、报表、信贷档案、现金尾箱、登记薄、私人物品等不得放置于桌面上；办理业务需要使用的，使用完毕后需放置于柜面下或抽屉中；未定位摆放、杂乱等不得分" },
				{ "物件摆放归位整齐", "桌面", "3.0", "", "除各种机具外，物品摆放必须做到规范、有序，不得随意放置，各种凭证、印章、印泥、印鉴册、剪刀、胶水、报表、信贷档案、现金尾箱、登记薄、私人物品等不得放置于桌面上；办理业务需要使用的，使用完毕后需放置于柜面下或抽屉中；未定位摆放、杂乱等不得分" }, { "物件摆放归位整齐", "钱箱", "3.0", "", "除各种机具外，物品摆放必须做到规范、有序，不得随意放置，各种凭证、印章、印泥、印鉴册、剪刀、胶水、报表、信贷档案、现金尾箱、登记薄、私人物品等不得放置于桌面上；办理业务需要使用的，使用完毕后需放置于柜面下或抽屉中；未定位摆放、杂乱等不得分" },
				{ "物件摆放归位整齐", "桌牌", "3.0", "", "除各种机具外，物品摆放必须做到规范、有序，不得随意放置，各种凭证、印章、印泥、印鉴册、剪刀、胶水、报表、信贷档案、现金尾箱、登记薄、私人物品等不得放置于桌面上；办理业务需要使用的，使用完毕后需放置于柜面下或抽屉中；未定位摆放、杂乱等不得分" }, { "卫生保洁", "点钞机", "3.0", "", "有积灰、污渍、纸屑等不得分" }, { "卫生保洁", "计算机", "3.0", "", "有积灰、污渍、纸屑等不得分" }, { "卫生保洁", "地面", "3.0", "", "有积灰、污渍、纸屑等不得分" }, { "卫生保洁", "窗面", "3.0", "", "有积灰、污渍、纸屑等不得分" },
				{ "卫生保洁", "柜面", "3.0", "", "有积灰、污渍、纸屑等不得分" }, { "声音", "音量", "2.0", "", "适中" }, { "声音", "语速", "2.0", "", "清晰适中" }, { "表情", "眼神", "2.0", "", "交流、对视" }, { "表情", "微笑", "2.0", "", "适时微笑" }, { "问候迎接", "呼叫", "3.0", "", "举手示意" }, { "问候迎接", "问候", "3.0", "", "首问“您好”/日常称谓，请客入座" }, { "接待办理", "义务", "5.0", "", "做到先外后内，办理业务执行一次性回复；对非受理范围内业务主动主动告知、引导其到相关窗口办理。" },
				{ "接待办理", "语言", "10.0", "", "做到热心、诚心、耐心，自觉使用“请、您好、谢谢、对不起、再见、请问您需要办理什么业务、请稍等、请您查看计数器、请您核对后在这里签字”等文明用语" }, { "接待办理", "动作", "5.0", "", "双手递接、掌心朝上" }, { "接待办理", "动作", "5.0", "", "适时主动与客户交流我社销售产品内容，介绍我社业务，创造销售机会，或请客户协助完成服务满意度评价。" }, { "送别结束", "语言", "3.0", "", "请问您还需要办理其他业务吗？/请慢走，再见" }, { "其他情况", "1.办业务期间不大声闲谈", "1.0", "", "这些情况在发生时不得分" },
				{ "其他情况", "2.指引动作手掌朝上", "1.0", "", "这些情况在发生时不得分" }, { "其他情况", "3.间断业务时未向顾客做请示或表歉意", "1.0", "", "这些情况在发生时不得分" }, { "其他情况", "4.手机未设震动", "1.0", "", "这些情况在发生时不得分" }, { "其他情况", "5.暂离岗位时椅子未归位", "1.0", "", "这些情况在发生时不得分" }, { "其他情况", "6.不必要的肢体动作过多", "1.0", "", "这些情况在发生时不得分" }, { "其他情况", "7.暂停业务时未放置示意牌", "1.0", "", "这些情况在发生时不得分" }, { "其他情况", "8.长时间业务未主动安抚客户情绪", "1.0", "", "这些情况在发生时不得分" },
				{ "其他情况", "9.有其他被投诉情况", "5.0", "", "这些情况在发生时不得分" }, { "营业结束", "卫生保洁", "5.0", "", "这些情况在发生时不得分" }, { "总分", "", "", "", "" } };
		return date;
	}

	/**
	 * 部门考核计划生成
	 * planid:计划id
	 */
	@Override
	public String getBMsql(String planid) {
		String str = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_BMPF_table");
			HashVO[] vos = dmo.getHashVoArrayByDS(null, "select * from sal_target_list where type='部门定性指标'");
			HashMap deptMap = getdeptName();
			InsertSQLBuilder insertSQLBuilder = new InsertSQLBuilder("wn_BMPF_table");
			List<String> list = new ArrayList<String>();
			List<String> wmsumList = new ArrayList<String>();
			List<String> djsumList = new ArrayList<String>();
			List<String> nksumList = new ArrayList<String>();
			List<String> aqsumList = new ArrayList<String>();
			//对批复时间进行修改
			String[] date = dmo.getStringArrayFirstColByDS(null, "select PFTIME from WN_BMPF_TABLE where state='评分结束'");
			String pftime="";
			if(date==null||date.length==0){
				int month=Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
				int currentQuarter=getQuarter2(month);//根据当前时间获取到当前季度
				pftime=new SimpleDateFormat("yyyy").format(new Date())+"-"+getQuarterEnd(currentQuarter);//获取到当前季度最后一天
			}else {
			   String maxTime=dmo.getStringValueByDS(null, "SELECT max(PFTIME) PFTINE FROM WN_BMPF_TABLE WHERE STATE='评分结束'");
			   int year= Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));//获取到当前年
			   int nextQuarter=getQuarter(maxTime)+1;
			   if(nextQuarter>=5){
				   nextQuarter=1;
				   year=year+1;
			   }
			   pftime=String.valueOf(year)+"-"+getQuarterEnd(nextQuarter);
			}
			for (int i = 0; i < vos.length; i++) {//每一项指标
				String deptid = vos[i].getStringValue("checkeddept");//获取到被考核部门的机构号
				deptid = deptid.replaceAll(";", ",").substring(1, deptid.length() - 1);
				String[] deptcodes = deptid.split(",");
				String xiangmu = vos[i].getStringValue("name");//项目名称
				String evalstandard = vos[i].getStringValue("evalstandard");//项目扣分描述
				String weights = vos[i].getStringValue("weights");//项目权重
				String koufen = "0.0";//扣分情况(默认是0.0)
				String state = "评分中";//当前项的评分状态:评分中
//				String pftime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());//评分日期
				//为每个部门生成每一项考核
				for (int j = 0; j < deptcodes.length; j++) {
					if ("964".equals(deptcodes[j]) || "965".equals(deptcodes[j])) {
						continue;
					}
					//每一项考核指标都会为每一个考核部门生一个考核计划
					String deptName = deptMap.get(deptcodes[j]).toString();//获取到机构名称
					insert.putFieldValue("PLANID", planid);
					insert.putFieldValue("deptcode", deptcodes[j]);
					insert.putFieldValue("deptname", deptName);
					insert.putFieldValue("xiangmu", xiangmu);
					insert.putFieldValue("evalstandard", evalstandard);
					insert.putFieldValue("fenzhi", weights);
					insert.putFieldValue("koufen", koufen);
					insert.putFieldValue("state", state);
					insert.putFieldValue("pftime", pftime);
					insert.putFieldValue("id", dmo.getSequenceNextValByDS(null, "S_WN_BMPF_TABLE"));
					list.add(insert.getSQL());
					//为每一个部门的每一个大项生成总分
					String name = xiangmu.substring(0, xiangmu.indexOf("-"));
					if ("文明客户服务部".equals(name)) {
						if (!wmsumList.contains(deptcodes[j])) {
							insert.putFieldValue("PLANID", planid);
							insert.putFieldValue("deptcode", deptcodes[j]);
							insert.putFieldValue("xiangmu", name);
							insert.putFieldValue("koufen", "100.0");
							insert.putFieldValue("state", state);
							insert.putFieldValue("pftime", pftime);
							insert.putFieldValue("fenzhi", "");
							insert.putFieldValue("evalstandard", "");
							insert.putFieldValue("id", dmo.getSequenceNextValByDS(null, "S_WN_BMPF_TABLE"));
							wmsumList.add(deptcodes[j]);
							list.add(insert.getSQL());
						}
					} else if ("党建工作".equals(name)) {
						if (!djsumList.contains(deptcodes[j])) {
							insert.putFieldValue("PLANID", planid);
							insert.putFieldValue("deptcode", deptcodes[j]);
							insert.putFieldValue("xiangmu", name);
							insert.putFieldValue("koufen", "100.0");
							insert.putFieldValue("fenzhi", "");
							insert.putFieldValue("state", state);
							insert.putFieldValue("pftime", pftime);
							insert.putFieldValue("evalstandard", "");
							insert.putFieldValue("id", dmo.getSequenceNextValByDS(null, "S_WN_BMPF_TABLE"));
							list.add(insert.getSQL());
							djsumList.add(deptcodes[j]);
						}
					} else if ("安全保卫".equals(name)) {
						if (!aqsumList.contains(deptcodes[j])) {
							insert.putFieldValue("PLANID", planid);
							insert.putFieldValue("deptcode", deptcodes[j]);
							insert.putFieldValue("xiangmu", name);
							insert.putFieldValue("koufen", "100.0");
							insert.putFieldValue("state", state);
							insert.putFieldValue("pftime", pftime);
							insert.putFieldValue("fenzhi", "");
							insert.putFieldValue("evalstandard", "");
							insert.putFieldValue("id", dmo.getSequenceNextValByDS(null, "S_WN_BMPF_TABLE"));
							list.add(insert.getSQL());
							aqsumList.add(deptcodes[j]);
						}
					} else if ("内控合规".equals(name)) {
						if (!nksumList.contains(deptcodes[j])) {
							insert.putFieldValue("PLANID", planid);
							insert.putFieldValue("deptcode", deptcodes[j]);
							insert.putFieldValue("xiangmu", name);
							insert.putFieldValue("koufen", "100.0");
							insert.putFieldValue("state", state);
							insert.putFieldValue("fenzhi", "");
							insert.putFieldValue("pftime", pftime);
							insert.putFieldValue("evalstandard", "");
							insert.putFieldValue("id", dmo.getSequenceNextValByDS(null, "S_WN_BMPF_TABLE"));
							list.add(insert.getSQL());
							nksumList.add(deptcodes[j]);
						}
					}
				}
			}
			dmo.executeBatchByDS(null, list);
			list.clear();
			str = "部门考核计划生成成功";
		} catch (Exception e) {
			e.printStackTrace();
			str = "部门考核计划生成成功";
		}
		return str;
	}

	//获取到部门编号
	public HashMap getdeptName() {
		HashMap hash = null;
		try {
			hash = dmo.getHashMapBySQLByDS(null, "SELECT id,NAME FROM pub_corp_dept");
		} catch (Exception e) {
			hash = new HashMap();
			e.printStackTrace();
		}
		return hash;
	}

	@Override
	public String gradeBMScoreEnd() {//结束打分计划 算总分 改状态
		try {
			String[] csName = dmo.getStringArrayFirstColByDS(null, "SELECT DISTINCT(xiangmu) FROM WN_BMPF_TABLE WHERE fenzhi IS NULL");
			HashMap codeVos = dmo.getHashMapBySQLByDS(null, "SELECT DISTINCT(SUBSTR(name,0,INSTR(NAME,'-')-1)) name,CHECKEDDEPT FROM sal_target_list  WHERE TYPE ='部门定性指标'");
			UpdateSQLBuilder update = new UpdateSQLBuilder("WN_BMPF_TABLE");
			UpdateSQLBuilder update2 = new UpdateSQLBuilder("WN_BMPF_TABLE");
			List<String> list = new ArrayList<String>();
			
			for (int i = 0; i < csName.length; i++) {//遍历每一个评分大项（文明 党建  案防 安全）
				String deptcodes = codeVos.get(csName[i]).toString();
				deptcodes = deptcodes.substring(1, deptcodes.lastIndexOf(";"));
				String[] codes = deptcodes.split(";");//机构号
				for (int j = 0; j < codes.length; j++) {//每个大项每个机构每个小项  修改状态，计算总分
					if ("964".equals(codes[j]) || "965".equals(codes[j])) {
						continue;
					}
					String sql = "select * from WN_BMPF_TABLE where xiangmu like '" + csName[i] + "%' and deptcode='" + codes[j] + "' order by fenzhi";
					HashVO[] vos = dmo.getHashVoArrayByDS(null, sql);
					double result = 0.0;
					String KOUFEN = "";
					String FENZHI = "";
					for (int k = 0; k < vos.length; k++) {
						if (csName[i].equals(vos[k].getStringValue("xiangmu"))) {
							continue;
						}
						FENZHI = vos[i].getStringValue("fenzhi");
						KOUFEN = vos[i].getStringValue("koufen");
						if (KOUFEN == null || KOUFEN.isEmpty() || "".equals(KOUFEN)) {
							KOUFEN = "0.0";
						}
						if (Double.parseDouble(KOUFEN) > Double.parseDouble(FENZHI)) {
							KOUFEN = FENZHI;
						}
						result = result + Double.parseDouble(KOUFEN);
						update.setWhereCondition("1=1 and deptcode='" + codes[j] + "' and xiangmu like '" + csName[i] + "%' and state='评分中'");
						update.putFieldValue("state", "评分结束");
						update.putFieldValue("KOUFEN", KOUFEN);
						list.add(update.getSQL());
					}
					update2.setWhereCondition("1=1 and deptcode='" + codes[j] + "' and xiangmu='" + csName[i] + "' and fenzhi is null");
					update2.putFieldValue("koufen", 100.0 - result);
					list.add(update2.getSQL());
					dmo.executeBatchByDS(null, list);
					list.clear();
				}
			}
			UpdateSQLBuilder update3 = new UpdateSQLBuilder("WN_BMPFPLAN");
			update3.setWhereCondition("1=1 and state='评分中'");
			update3.putFieldValue("state", "评分结束");
			dmo.executeUpdateByDS(null, update3.getSQL());
			return "当前计划结束成功";
		} catch (Exception e) {
			e.printStackTrace();
			return "当前计划结束失败";
		}

	}

	public String getQuarterEnd(int num) {
		switch (num) {
		case 1:
			return "03-31";
		case 2:
			return "06-30";
		case 3:
			return "09-30";
		case 4:
			return "12-31";
		default:
			return "12-31";
		}
	}

	//date格式:2019-01-01
	public int getQuarter(String date) {//根据date获取到当前季度
		date = date.substring(5);
		if ("03-31".equals(date)) {
			return 1;
		} else if ("06-30".equals(date)) {
			return 2;
		} else if ("09-30".equals(date)) {
			return 3;
		} else if ("12-31".equals(date)) {
			return 4;
		} else {//处理一切输入不合理的情况
			return 4;
		}

	}

	public int getQuarter2(int month) {
		switch (month) {
		case 1:
		case 2:
		case 3:
			return 1;
		case 4:
		case 5:
		case 6:
			return 2;
		case 7:
		case 8:
		case 9:
			return 3;
		case 10:
		case 11:
		case 12:
			return 4;
		default:
			return 4;
		}
	}
/**
 * 全量导入数据
 */
	@Override
	public String ImportAll() {//全量数据导入
		return importDmo.ImportAll();
	}
/**
 * 导入一天的数据
 */
	@Override
	public String ImportDay(String date) {
		
		return importDmo.ImportDay(date);
	}

@Override
public String ImportOne(String filePath) {
	return importDmo.importOne(filePath);
}

	@Override
	/**
	 * zzl[2019-4-28]
	 * 每个月的客户经理对应的贷款需要调整，故需要修改上月的基数。
	 */
	public String getChange(){
		String strjv=null;
		Date date=new Date();
		Calendar scal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
		scal.setTime(date);
		scal.add(Calendar.MONTH, -2);
		scal.set(Calendar.DAY_OF_MONTH,scal.getActualMaximum(Calendar.DATE));
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");  
		String smonth=df.format(scal.getTime());//上月的月数
		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		String kmonth=df.format(cal.getTime());//考核月的月数
		StringBuffer sb=new StringBuffer();
		StringBuffer sqlsb=new StringBuffer();
		try {
			UpdateSQLBuilder update=new UpdateSQLBuilder("wnbank.s_loan_dk");
			List list=new ArrayList<String>();
			//客户经理的信息表map
			HashMap<String,String> map=dmo.getHashMapBySQLByDS(null,"select xd_col1,xd_col2 from wnbank.s_loan_ryb");
			//考核月的客户证件和客户经理号map
			HashMap<String, String> kmap=dmo.getHashMapBySQLByDS(null,"select distinct(dk.xd_col16),dk.XD_COL81 from wnbank.s_loan_dk dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+kmonth+"' and XD_COL7<>0");
			//上月的客户证件和客户经理号map
			HashMap<String, String> smap=dmo.getHashMapBySQLByDS(null,"select distinct(dk.xd_col16),dk.XD_COL81 from wnbank.s_loan_dk dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+smonth+"' and XD_COL7<>0");
			for(String str:kmap.keySet()){
				if(kmap.get(str).equals(smap.get(str))){
					continue;
				}else{
					update.setWhereCondition("to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+smonth+"' and xd_col16='"+str+"'");
					update.putFieldValue("XD_COL81", kmap.get(str));
					list.add(update.getSQL());
					sb.append(smonth+"客户证件号为["+str+"]客户经理为["+map.get(smap.get(str))+"]与考核月的客户经理信息不符，故修改客户经理为["+map.get(kmap.get(str))+"] "+System.getProperty("line.separator"));
				}
				if(list.size()>5000){//zzl 1000 一提交
					dmo.executeBatchByDS(null, list);
					for(int i=0;i<list.size();i++){
						sqlsb.append(list.get(i).toString()+" "+System.getProperty("line.separator"));
					}
					contentToSQL("DK", sqlsb.toString());
					list.clear();
				}
			}
			if(list.size()>0){
				dmo.executeBatchByDS(null, list);
				sqlsb.delete(0,sqlsb.length());
				for(int i=0;i<list.size();i++){
					sqlsb.append(list.get(i).toString()+" "+System.getProperty("line.separator"));
				}
				contentToSQL("DK", sqlsb.toString());
			}
			contentToTxt("DK",sb.toString());			
		} catch (Exception e) {
			strjv="客户经理信息变更失败";
			e.printStackTrace();
		}
		return "客户经理信息变更成功";
	}
	public static void main(String[] args) {
		Date date=new Date();
		Calendar scal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
		scal.setTime(date);
		scal.add(Calendar.MONTH, -2);
		scal.set(Calendar.DAY_OF_MONTH,scal.getActualMaximum(Calendar.DATE));
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");  
		String smonth=df.format(scal.getTime());//上月的月数
		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		String kmonth=df.format(cal.getTime());//考核月的月数
		System.out.println(">>>>smonth>>>>>>>"+smonth);
		System.out.println(">>>>kmonth>>>>>>>"+kmonth);
	}
	/**
	 * zzl
	 * @param date
	 * @return 返回上月时间
	 */
	public String getSMonthDate(String date){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		try {
			cal.setTime(format.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		Date otherDate = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(otherDate);
		
	}
/**
 * zzl[存款客户经理信息变更]
 * 
 */
	@Override
	public String getCKChange() {
		String strjv=null;
		Date date=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");  
		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH,0);
//		cal.add(Calendar.MONTH, -1);//取当前日期的后一天. 
		String kmonth=df.format(cal.getTime());//考核月的月数
		StringBuffer sb=new StringBuffer();
		StringBuffer sqlsb=new StringBuffer();
		try{
			UpdateSQLBuilder update=new UpdateSQLBuilder("wnbank.s_loan_KHXX");
			List list=new ArrayList<String>();
			//客户经理的信息表map
			HashMap<String,String> map=dmo.getHashMapBySQLByDS(null,"select xd_col1,xd_col2 from wnbank.s_loan_ryb");
			//考核月的客户证件和客户经理号map
			HashMap<String, String> kmap=dmo.getHashMapBySQLByDS(null,"select XD_COL7,XD_COL96 from wnbank.S_LOAN_KHXX where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+kmonth+"' and XD_COL7 is not null and XD_COL96 is not null");
			//年初客户证件和客户经理号map
			String year=String.valueOf(Integer.parseInt(kmonth.substring(0,4))-1);			
			HashMap<String, String> smap=dmo.getHashMapBySQLByDS(null,"select XD_COL7,XD_COL96 from wnbank.S_LOAN_KHXX where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+year+"-12-31' and XD_COL7 is not null and XD_COL96 is not null");
			for(String str:kmap.keySet()){
				if(kmap.get(str).equals(smap.get(str))){
					continue;
				}else{
					update.setWhereCondition("to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+year+"-12-31' and xd_col16='"+str+"'");
					update.putFieldValue("XD_COL96", kmap.get(str));
					list.add(update.getSQL());
					sb.append("2018-12-31客户证件号为["+str+"]客户经理为["+map.get(smap.get(str))+"]与考核月的客户经理信息不符，故修改客户经理为["+map.get(kmap.get(str))+"] "+System.getProperty("line.separator"));
				}
				if(list.size()>5000){//zzl 1000 一提交
					dmo.executeBatchByDS(null, list);
					for(int i=0;i<list.size();i++){
						sqlsb.append(list.get(i).toString()+" "+System.getProperty("line.separator"));
					}
					contentToSQL("CK", sqlsb.toString());
					list.clear();
				}
			}
			if(list.size()>0){
				dmo.executeBatchByDS(null, list);
				sqlsb.delete(0,sqlsb.length());
				for(int i=0;i<list.size();i++){
					sqlsb.append(list.get(i).toString()+" \n");
				}
				contentToSQL("CK", sqlsb.toString());
			}
			contentToTxt("CK",sb.toString());
		}catch(Exception e){
			strjv="客户经理信息变更失败";
			e.printStackTrace();
		}
		return "客户经理信息变更成功";
	}
/**
 * zzl 贷款管户数比
 */
	@Override
	public String getDKFinishB(String date) {
		String jv=null;
		InsertSQLBuilder insert=new InsertSQLBuilder("wn_loans_wcb");
		List list=new ArrayList<String>();
		try {
			//得到客户经理的Map
			HashMap<String, String> userMap=dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
		    //得到客户经理的任务数
			HashMap<String,String> rwMap=dmo.getHashMapBySQLByDS(null, "select A,sum(D) from EXCEL_TAB_53 where year||'-'||month='"+date.substring(0,7)+"' group by A");
			if(rwMap.size()==0){
				return "当前时间【"+date+"】没有上传任务数";
			}
			HashMap<String, String> nhMap=getDKNH(date);
			HashMap<String, String> nbzgMap=getDKNBZG(date);
			HashMap<String, String> ajMap=getDKAJ(date);
			HashMap<String, String> ybzrrMap=getDKYBZRR(date);
			HashMap<String, String> qyMap=getDKQY(date);
			for(String str:userMap.keySet()){
				int count,nh,nbzg,aj,yb,qy=0;
				if(nhMap.get(str)==null){
					nh=0;
				}else{
					nh=Integer.parseInt(nhMap.get(str));
					} 
				if(nbzgMap.get(str)==null){
					nbzg=0;
				}else {
					nbzg=Integer.parseInt(nbzgMap.get(str));
				}
				if(ajMap.get(str)==null){
					aj=0;
				}else {
					aj=Integer.parseInt(ajMap.get(str));
				}
				if(ybzrrMap.get(str)==null){
					yb=0;
				}else{
					yb=Integer.parseInt(ybzrrMap.get(str));
				}
				if(qyMap.get(str)==null){
					qy=0;
				}else{
					qy=Integer.parseInt(qyMap.get(str));
				}
				count=nh+nbzg+aj+yb+qy;
				insert.putFieldValue("name",str);
				insert.putFieldValue("passed",count);
				insert.putFieldValue("task",rwMap.get(str));
				insert.putFieldValue("date_time",date);
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, list);
			jv="查询完成";
		} catch (Exception e) {
			jv="查询失败";
			e.printStackTrace();
		}
		
		return jv;
	}
	/**
	 * zzl
	 * 贷款管户数-农户Map
	 * @return
	 */
	public HashMap<String, String> getDKNH(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 贷款管户数-内部职工Map
	 * @return
	 */
	public HashMap<String, String> getDKNBZG(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 in('26','19') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 贷款管户数-按揭客户Map
	 * @return
	 */
	public HashMap<String, String> getDKAJ(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 in('20','29','28','37','h01','h02') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 贷款管户数-一般自然人Map
	 * @return
	 */
	public HashMap<String, String> getDKYBZRR(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 not in ('20','29','28','37','h01','h02','26','19','16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 贷款管户数-企业Map
	 * @return
	 */
	public HashMap<String, String> getDKQY(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select dg.cus_manager cus_manager,count(dg.cus_manager) countxj from (select distinct(xx.cert_code),dg.cus_manager  from wnbank.s_cmis_acc_loan dg left join wnbank.s_cmis_cus_base xx on dg.cus_id=xx.cus_id  where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"'and dg.cla<>'05' and account_status='1' and loan_balance<>'0')dg group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
/**
 * zzl
 * 贷款余额新增完成比
 */
	@Override
	public String getDKBalanceXZ(String date) {
		String jl=null;
		try{
			InsertSQLBuilder insert=new InsertSQLBuilder("wn_loan_balance");
			List list=new ArrayList<String>();
			//得到客户经理的Map
			HashMap<String, String> userMap=dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
		    //得到客户经理的任务数
			HashMap<String,String> rwMap=dmo.getHashMapBySQLByDS(null, "select A,sum(E) from EXCEL_TAB_53 where year||'-'||month='"+date.substring(0,7)+"' group by A");
			if(rwMap.size()==0){
				return "当前时间【"+date+"】没有上传任务数";
			}
			//得到机构编码的map
			HashMap<String,String> deptCodeMap=dmo.getHashMapBySQLByDS(null, "select sal.name,dept.code from v_sal_personinfo sal left join pub_corp_dept dept on sal.deptid=dept.id");
	        //得到机构类型的map
			HashMap<String,String> deptTypeMap=dmo.getHashMapBySQLByDS(null, "select sal.name,dept.corptype from v_sal_personinfo sal left join pub_corp_dept dept on sal.deptid=dept.id");
			//营业部考核月贷款余额-农户Map
			HashMap<String,String> YEKDKNHMap=getKDKNHSalesOffice(date);
			HashMap<String,String> YESDKNHMap=getSDKNHSalesOffice(date);
			HashMap<String,String> YEKDKQTMap=getKDKQTSalesOffice(date);
			HashMap<String,String> YESDKQTMap=getKDKQTSalesOffice(date);
			HashMap<String,String> YEKDKDGMap=getKDKDGSalesOffice(date);
			HashMap<String,String> YESDKDGMap=getSDKDGSalesOffice(date);
			HashMap<String,String> KCQNHMap=getKDKCQNHSalesOffice(date);
			HashMap<String,String> SCQNHMap=getSDKCQNHSalesOffice(date);
			HashMap<String,String> KCQQTMap=getKDKCQQTSalesOffice(date);
			HashMap<String,String> SCQQTMap=getSDKCQQTSalesOffice(date);
			HashMap<String,String> KCQDGMap=getKDKDG100SalesOffice(date);
			HashMap<String,String> SCQDGMap=getSDKDG100SalesOffice(date);
			HashMap<String,String> KDGNHMap=getKDKNHSales(date);
			HashMap<String,String> SDGNHMap=getSDKNHSales(date);
			HashMap<String,String> KDGQTMap=getKDKQTSales(date);
			HashMap<String,String> SDGQTMap=getSDKQTSales(date);
			for(String str:userMap.keySet()){
				Double yyb1,yyb2,yyb3,yyb4,yyb5,yyb6=0.0;
				Double count=0.0;
				if(deptCodeMap.get(str).equals("2820100")){
					if(YEKDKNHMap.get(str)==null){
						yyb1=0.0;
					}else{
						yyb1=Double.parseDouble(YEKDKNHMap.get(str));
					}
					if(YESDKNHMap.get(str)==null){
						yyb2=0.0;
					}else{
						yyb2=Double.parseDouble(YESDKNHMap.get(str));
					}
					if(YEKDKQTMap.get(str)==null){
						yyb3=0.0;
					}else{
						yyb3=Double.parseDouble(YEKDKQTMap.get(str));
					}
					if(YESDKQTMap.get(str)==null){
						yyb4=0.0;
					}else{
						yyb4=Double.parseDouble(YESDKQTMap.get(str));
					}
					if(YEKDKDGMap.get(str)==null){
						yyb5=0.0;
					}else{
						yyb5=Double.parseDouble(YEKDKDGMap.get(str));
					}
					if(YESDKDGMap.get(str)==null){
						yyb6=0.0;
					}else{
						yyb6=Double.parseDouble(YESDKDGMap.get(str));
					}
					count=(yyb1-yyb2)+(yyb3-yyb4)+(yyb5-yyb6);
					insert.putFieldValue("name",str);
					insert.putFieldValue("passed",count);
					insert.putFieldValue("task",rwMap.get(str));
					insert.putFieldValue("date_time",date);
					list.add(insert.getSQL());
				}else if(deptTypeMap.get(str).equals("城区银行")){
					if(KCQNHMap.get(str)==null){
						yyb1=0.0;
					}else{
						yyb1=Double.parseDouble(KCQNHMap.get(str));
					}
					if(SCQNHMap.get(str)==null){
						yyb2=0.0;
					}else{
						yyb2=Double.parseDouble(SCQNHMap.get(str));
					}
					if(KCQQTMap.get(str)==null){
						yyb3=0.0;
					}else{
						yyb3=Double.parseDouble(KCQQTMap.get(str));
					}
					if(SCQQTMap.get(str)==null){
						yyb4=0.0;
					}else{
						yyb4=Double.parseDouble(SCQQTMap.get(str));
					}
					if(KCQDGMap.get(str)==null){
						yyb5=0.0;
					}else{
						yyb5=Double.parseDouble(KCQDGMap.get(str));
					}
					if(SCQDGMap.get(str)==null){
						yyb6=0.0;
					}else{
						yyb6=Double.parseDouble(SCQDGMap.get(str));
					}
					count=(yyb1-yyb2)+(yyb3-yyb4)+(yyb5-yyb6);
					insert.putFieldValue("name",str);
					insert.putFieldValue("passed",count);
					insert.putFieldValue("task",rwMap.get(str));
					insert.putFieldValue("date_time",date);
					list.add(insert.getSQL());
				}else{
					if(KDGNHMap.get(str)==null){
						yyb1=0.0;
					}else{
						yyb1=Double.parseDouble(KDGNHMap.get(str));
					}
					if(SDGNHMap.get(str)==null){
						yyb2=0.0;
					}else{
						yyb2=Double.parseDouble(SDGNHMap.get(str));
					}
					if(KDGQTMap.get(str)==null){
						yyb3=0.0;
					}else{
						yyb3=Double.parseDouble(KDGQTMap.get(str));
					}
					if(SDGQTMap.get(str)==null){
						yyb4=0.0;
					}else{
						yyb4=Double.parseDouble(SDGQTMap.get(str));
					}
					count=(yyb1-yyb2)+(yyb3-yyb4);
					insert.putFieldValue("name",str);
					insert.putFieldValue("passed",count);
					insert.putFieldValue("task",rwMap.get(str));
					insert.putFieldValue("date_time",date);
					list.add(insert.getSQL());
				}
			}
			dmo.executeBatchByDS(null,list);
			jl="查询成功";
		}catch (Exception  e){
			jl="查询失败";
			e.printStackTrace();
		}
		return jl;
	}
	/**
	 * zzl
	 * 营业部考核月贷款余额-农户Map
	 * @return
	 */
	public HashMap<String, String> getKDKNHSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"'  and xd_col2 not like '公司' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0  and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 营业部上月贷款余额-农户Map
	 * @return
	 */
	public HashMap<String, String> getSDKNHSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"'  and xd_col2 not like '公司' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0  and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 营业部考核月贷款余额-其他Map
	 * @return
	 */
	public HashMap<String, String> getKDKQTSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"'  and xd_col2 not like '公司' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 营业部上月贷款余额-其他Map
	 * @return
	 */
	public HashMap<String, String> getSDKQTSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"'  and xd_col2 not like '公司' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 营业部对公贷款余额Map
	 * @return
	 */
	public HashMap<String, String> getKDKDGSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select dg.cus_manager cus_manager,sum(LOAN_BALANCE)/10000 tj from (select dg.cus_manager cus_manager,sum(LOAN_AMOUNT) tj,sum(LOAN_BALANCE) LOAN_BALANCE  from wnbank.s_cmis_acc_loan dg where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"' and dg.cla<>'05' and account_status='1' and loan_balance<>'0' group by CUS_ID,dg.cus_manager) dg where dg.tj<10000000 group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 营业部上月对公贷款余额Map
	 * @return
	 */
	public HashMap<String, String> getSDKDGSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select dg.cus_manager cus_manager,sum(LOAN_BALANCE)/10000 tj from (select dg.cus_manager cus_manager,sum(LOAN_AMOUNT) tj,sum(LOAN_BALANCE) LOAN_BALANCE  from wnbank.s_cmis_acc_loan dg where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"' and dg.cla<>'05' and account_status='1' and loan_balance<>'0' group by CUS_ID,dg.cus_manager) dg where dg.tj<10000000 group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * zzl
	 * 城区网点农户贷款余额新增Map
	 * @return
	 */
	public HashMap<String, String> getKDKCQNHSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%公司%' group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 城区网点上月农户贷款余额新增Map
	 * @return
	 */
	public HashMap<String, String> getSDKCQNHSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%公司%' group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 城区网点其他贷款余额新增Map
	 * @return
	 */
	public HashMap<String, String> getKDKCQQTSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col6) as xd_col6,sum(xd_col7) xd_col7  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%公司%' group by XD_COL14,xd_col81 ) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 城区网点上月其他贷款余额新增Map
	 * @return
	 */
	public HashMap<String, String> getSDKCQQTSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col6) as xd_col6,sum(xd_col7) xd_col7  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%公司%' group by XD_COL14,xd_col81 ) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 对公贷款余额100万以下Map
	 * @return
	 */
	public HashMap<String, String> getKDKDG100SalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select dg.cus_manager cus_manager,sum(LOAN_BALANCE)/10000 tj from (select dg.cus_manager cus_manager,sum(LOAN_AMOUNT) tj,sum(LOAN_BALANCE) LOAN_BALANCE  from wnbank.s_cmis_acc_loan dg where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"' and dg.cla<>'05' and account_status='1' and loan_balance<>'0' group by CUS_ID,dg.cus_manager) dg where dg.tj<1000000 group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 对公贷款余额100万以下Map
	 * @return
	 */
	public HashMap<String, String> getSDKDG100SalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select dg.cus_manager cus_manager,sum(LOAN_BALANCE)/10000 tj from (select dg.cus_manager cus_manager,sum(LOAN_AMOUNT) tj,sum(LOAN_BALANCE) LOAN_BALANCE  from wnbank.s_cmis_acc_loan dg where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"' and dg.cla<>'05' and account_status='1' and loan_balance<>'0' group by CUS_ID,dg.cus_manager) dg where dg.tj<1000000 group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 考核月贷款余额-农户Map
	 * @return
	 */
	public HashMap<String, String> getKDKNHSales(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,aa.xd_col7 xd_col7 from (select xd_col81,sum(xd_col7)/10000 as xd_col7,sum(xd_col6)/10000 xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%公司%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 上月贷款余额-农户Map
	 * @return
	 */
	public HashMap<String, String> getSDKNHSales(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,aa.xd_col7 xd_col7 from (select xd_col81,sum(xd_col7)/10000 as xd_col7,sum(xd_col6)/10000 xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%公司%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 考核月贷款余额-其他Map
	 * @return
	 */
	public HashMap<String, String> getKDKQTSales(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,aa.xd_col7 xd_col7 from (select xd_col81,sum(xd_col7)/10000 as xd_col7  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%公司%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 上月贷款余额-其他Map
	 * @return
	 */
	public HashMap<String, String> getSDKQTSales(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,aa.xd_col7 xd_col7 from (select xd_col81,sum(xd_col7)/10000 as xd_col7  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%公司%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
/**
 * zzl
 * 贷款户数新增
 */
	@Override
	public String getDKHouseholdsXZ(String date) {
		String jl=null;
		try{
			InsertSQLBuilder insert=new InsertSQLBuilder("WN_LOANS_newly");
			List list=new ArrayList<String>();
			//得到客户经理的Map
			HashMap<String, String> userMap=dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
		    //得到客户经理的任务数
			HashMap<String,String> rwMap=dmo.getHashMapBySQLByDS(null, "select A,sum(F) from EXCEL_TAB_53 where year||'-'||month='"+date.substring(0,7)+"' group by A");
			if(rwMap.size()==0){
				return "当前时间【"+date+"】没有上传任务数";
			}
			HashMap<String, String> knhMap=getKDKNHNewly(date);
			HashMap<String, String> snhMap=getSDKNHNewly(date);
			HashMap<String, String> kqtMap=getKDKQTNewly(date);
			HashMap<String, String> sqtMap=getSDKQTNewly(date);
			HashMap<String, String> kdgMap=getKDKDGNewly(date);
			HashMap<String, String> sdgMap=getSDKDGNewly(date);
			for(String str:userMap.keySet()){
				int count=0;
				int hs1,hs2,hs3,hs4,hs5,hs6=0;
				if(knhMap.get(str)==null){
					hs1=0;
				}else{
					hs1=Integer.parseInt(knhMap.get(str));
				}
				if(snhMap.get(str)==null){
					hs2=0;
				}else{
					hs2=Integer.parseInt(snhMap.get(str));
				}
				if(kqtMap.get(str)==null){
					hs3=0;
				}else{
					hs3=Integer.parseInt(kqtMap.get(str));
				}
				if(sqtMap.get(str)==null){
					hs4=0;
				}else{
					hs4=Integer.parseInt(sqtMap.get(str));
				}
				if(kdgMap.get(str)==null){
					hs5=0;
				}else{
					hs5=Integer.parseInt(kdgMap.get(str));
				}
				if(sdgMap.get(str)==null){
					hs6=0;
				}else{
					hs6=Integer.parseInt(sdgMap.get(str));
				}
				count=(hs1-hs2)+(hs3-hs4)+(hs5-hs6);
				insert.putFieldValue("name",str);
				insert.putFieldValue("passed",count);
				insert.putFieldValue("task",rwMap.get(str));
				insert.putFieldValue("date_time",date);
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, list);
			jl="查询成功";
		}catch(Exception e){
			jl="查询失败";
			e.printStackTrace();
		}
		return jl;
	}
	/**
	 * zzl
	 * 考核月贷款户数-农户Map
	 * @return
	 */
	public HashMap<String, String> getKDKNHNewly(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 上月贷款户数-农户Map
	 * @return
	 */
	public HashMap<String, String> getSDKNHNewly(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 考核月贷款户数-其他Map
	 * @return
	 */
	public HashMap<String, String> getKDKQTNewly(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 上月月贷款户数-其他Map
	 * @return
	 */
	public HashMap<String, String> getSDKQTNewly(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%公司%' and xd_col166<>'81320101' and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 考核月贷款户数-企业Map
	 * @return
	 */
	public HashMap<String, String> getKDKDGNewly(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select dg.cus_manager cus_manager,count(dg.cus_manager) countxj from (select distinct(xx.cert_code),dg.cus_manager  from wnbank.s_cmis_acc_loan dg left join wnbank.s_cmis_cus_base xx on dg.cus_id=xx.cus_id  where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"'and dg.cla<>'05' and account_status='1' and loan_balance<>'0')dg group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 上月贷款户数-企业Map
	 * @return
	 */
	public HashMap<String, String> getSDKDGNewly(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select dg.cus_manager cus_manager,count(dg.cus_manager) countxj from (select distinct(xx.cert_code),dg.cus_manager  from wnbank.s_cmis_acc_loan dg left join wnbank.s_cmis_cus_base xx on dg.cus_id=xx.cus_id  where to_char(to_date(dg.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"'and dg.cla<>'05' and account_status='1' and loan_balance<>'0')dg group by dg.cus_manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
/**
 * zzl 表外不良贷款完成比
 * @param date
 * @return
 */
	@Override
	public String getBadLoans(String date) {
		String jl=null;
		try{
			InsertSQLBuilder insert=new InsertSQLBuilder("WN_OFFDK_BAB");
			List list=new ArrayList<String>();
			//得到客户经理的Map
			HashMap<String, String> userMap=dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
		    //得到客户经理的任务数
			HashMap<String,String> rwMap=dmo.getHashMapBySQLByDS(null, "select A,sum(K) from EXCEL_TAB_53 where year||'-'||month='"+date.substring(0,7)+"' group by A");
			if(rwMap.size()==0){
				return "当前时间【"+date+"】没有上传任务数";
			}
			HashMap<String, String> bab2017Map=get2017DBadLoans(date);
			HashMap<String, String> bab2018Map=get2018DBadLoans(date);
			for(String str:userMap.keySet()){
				Double count=0.0;
				Double hj1,hj2=0.0;
				if(bab2017Map.get(str)==null){
					hj1=0.0;
				}else{
					hj1=Double.parseDouble(bab2017Map.get(str));
				}
				if(bab2018Map.get(str)==null){
					hj2=0.0;
				}else{
					hj2=Double.parseDouble(bab2018Map.get(str));
				}
				count=hj1+hj2;
				insert.putFieldValue("name",str);
				insert.putFieldValue("passed",count);
				insert.putFieldValue("task",rwMap.get(str));
				insert.putFieldValue("date_time",date);
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, list);
			jl="查询成功";
		}catch (Exception e){
			jl="查询失败";
			e.printStackTrace();
		}
		return jl;
	}
	/**
	 * zzl
	 * 现金收回表外不良贷款本息金额2017Map
	 * @return
	 */
	public HashMap<String, String> get2017DBadLoans(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select yb.xd_col2 xd_col2,sum(bwbl.sumtj)/10000 tj from(select hk.xd_col1 xd_col1,hk.sumtj sumtj,dk.xd_col81 xd_col81 from(select XD_COL1 XD_COL1,sum(XD_COL5+XD_COL11) sumtj from  wnbank.S_LOAN_HK hk where to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')>='"+getMonthC(date)+"' and to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')<='"+date+"' and  XD_COL16='05'  and XD_COL20<>'81320101' group by XD_COL1) hk  left join wnbank.s_loan_dk dk on hk.xd_col1=dk.xd_col1  where to_char(to_date(dk.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"' and  XD_COL5<='2017-12-31') bwbl left join wnbank.S_LOAN_RYB yb on bwbl.XD_COL81=yb.xd_col1 group by yb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * 现金收回表外不良贷款本息金额(2018)Map
	 * @return
	 */
	public HashMap<String, String> get2018DBadLoans(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select yb.xd_col2,sum(bwbl.sumtj)/10000 from(select hk.xd_col1 xd_col1,hk.sumtj sumtj,dk.xd_col81 xd_col81 from(select XD_COL1 XD_COL1,sum(XD_COL5+XD_COL11) sumtj from  wnbank.S_LOAN_HK hk where to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')>='"+getMonthC(date)+"' and to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')<='"+date+"' and  XD_COL16='05'  and XD_COL20<>'81320101' group by XD_COL1) hk left join wnbank.s_loan_dk dk on hk.xd_col1=dk.xd_col1  where to_char(to_date(dk.load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"' and  XD_COL5<='2018-12-31' and XD_COL5>='2018-01-01') bwbl left join wnbank.S_LOAN_RYB yb on bwbl.XD_COL81=yb.xd_col1 group by yb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl 返回月初的日期
	 * @param date
	 * @return
	 */
	public String getMonthC(String date){
		date=date.substring(0,7);
		return date+"-01";
	}
	/**
	 * zzl 返回年初的日期
	 * @param date
	 * @return
	 */
	public String getYearC(String date){
		date=date.substring(0,4);
		date=String.valueOf(Integer.valueOf(date)-1);
		return date+"-12-31";
	}
/**
 * zzl[收回存量不良贷款完成比&不良贷款压降] 
 */
	@Override
	public String getTheStockOfLoan(String date) {
		String jl=null;
		try{
			InsertSQLBuilder insert=new InsertSQLBuilder("WN_Stock_Loan");
			List list=new ArrayList<String>();
			//得到客户经理的Map
			HashMap<String, String> userMap=dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('城区客户经理','乡镇客户经理','副主任兼职客户经理','乡镇网点副主任','城区网点副主任')");
		    //得到客户经理的任务数
			HashMap<String,String> rwMap=dmo.getHashMapBySQLByDS(null, "select A,sum(G) from EXCEL_TAB_53 where year||'-'||month='"+date.substring(0,7)+"' group by A");
			if(rwMap.size()==0){
				return "当前时间【"+date+"】没有上传任务数";
			}
			HashMap<String, String> slMap=getStockLoans(date);
			for(String str:userMap.keySet()){
				Double count=0.0;
				if(slMap.get(str)==null){
					count=0.0;
				}else{
					count=Double.parseDouble(slMap.get(str));
				}
				insert.putFieldValue("name",str);
				insert.putFieldValue("passed",count);
				insert.putFieldValue("task",rwMap.get(str));
				insert.putFieldValue("date_time",date);
				list.add(insert.getSQL());
			}
			dmo.executeBatchByDS(null, list);
			jl="查询成功";
		}catch (Exception e){
			jl="查询失败";
			e.printStackTrace();
		}

		return jl;
	}
	/**
	 * zzl
	 * 收回存量不良贷款考核
	 * @return
	 */
	public HashMap<String, String> getStockLoans(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select yb.xd_col2,sum(zjj.XD_COL5)/10000 from(select zj.XD_COL81 XD_COL81,sum(zj.XD_COL5) XD_COL5 from(select hk.xd_col14,hk.XD_COL5,hk.XD_COL81,dk.xd_col1 from (select xdk.xd_col1,xhk.XD_COL4,xhk.XD_COL5,xdk.XD_COL2,xdk.XD_COL81,xdk.XD_COL14 from(select * from wnbank.S_LOAN_HK where Xd_Col16 in('03','04') and to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')<='"+date+"' and to_char(cast (cast (XD_COL4 as timestamp) as date),'yyyy-mm-dd')>='"+getMonthC(date)+"') xhk left join (select * from wnbank.S_LOAN_DK where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"' and xd_col22 in('03','04')) xdk on xdk.xd_col1=xhk.xd_col1) hk left join (select * from wnbank.S_LOAN_DK where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getYearC(date)+"' and xd_col22 in('03','04')) dk on dk.xd_col1=hk.xd_col1) zj where zj.xd_col1 is not null group by zj.xd_col14,zj.XD_COL81) zjj left join wnbank.S_LOAN_RYB yb on zjj.XD_COL81=yb.xd_col1 group by yb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
//	public static void main(String[] args) {
////		contentToTxt("C:\\Users\\longlonggo521\\Desktop\\niubi","niubi.txt","牛逼+傻吊");
////		StringBuffer sb=new StringBuffer();
////		sb.append("张珍龙");
////		sb.append("牛逼");
////		sb.append("雄起");
////		System.out.println(">>>>>>>>>>>>");
//	}
	/**
	 * 记录客户经理变更的日志
	 * zzl [2019-6-12] 
	 * @param filename
	 * @param content
	 */
    public static void contentToTxt(String filename,String content) {
		String str_path = ServerEnvironment.getProperty("WLTUPLOADFILEDIR");
		Date date=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");  
		str_path=str_path+"\\ManagerLog\\TXT_"+sf.format(date).toString();
		FileWriter fw = null;
		try {
			// 如果文件存在，则追加内容；如果文件不存在，则创建文件
			File f = new File(str_path);
		       if (!f.exists()) {
		            f.mkdirs();
		        }
		    filename=filename+sf.format(date).toString()+".txt";
			File f2 = new File(str_path+"\\"+filename);
 			fw = new FileWriter(f2, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(content);
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 记录客户经理变更SQL的日志
	 * zzl [2019-6-12] 
	 * @param filename
	 * @param content
	 */
    public static void contentToSQL(String filename,String content) {
		String str_path = ServerEnvironment.getProperty("WLTUPLOADFILEDIR");
		Date date=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");  
		str_path=str_path+"\\ManagerLog\\SQL_"+sf.format(date).toString();
		FileWriter fw = null;
		try {
			// 如果文件存在，则追加内容；如果文件不存在，则创建文件
			File f = new File(str_path);
		       if (!f.exists()) {
		            f.mkdirs();
		        }
		    filename=filename+sf.format(date).toString()+".sql";
			File f2 = new File(str_path+"\\"+filename);
 			fw = new FileWriter(f2, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(content);
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
