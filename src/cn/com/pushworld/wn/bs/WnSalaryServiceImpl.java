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
	 * zzl[2019-3-28]  �ݲ�ʹ��
	 * ��Ա����������������
	 */
	public String getSqlInsert(String time, int num) {
		System.out.println(time + "��ǰʱ��");
		String str = null;
		InsertSQLBuilder insert = new InsertSQLBuilder("wn_gypf_table");
		List list = new ArrayList<String>();
		String[][] date = getTowWeiDate();
		try {
			HashVO[] vos = dmo.getHashVoArrayByDS(null, "select * from V_PUB_USER_POST_1 where POSTNAME like '%��Ա%'");
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
					insert.putFieldValue("state", "������");
					insert.putFieldValue("seq", j + 1);
					insert.putFieldValue("timezone", timezone);
					list.add(insert.getSQL());
				}
			}
			dmo.executeBatchByDS(null, list);
			str = "��Ա���������������ֿ�ʼ�ɹ�";
		} catch (Exception e) {
			str = "��Ա���������������ֿ�ʼʧ��";
			e.printStackTrace();
		}
		return str;
	}

	//zpy
	@Override
	public String getSqlInsert(String time) {
		System.out.println(time + "��ǰʱ��");
		String str = null;
		InsertSQLBuilder insert = new InsertSQLBuilder("wn_gypf_table");
		List list = new ArrayList<String>();
		String[][] date = getTowWeiDate();
		try {
			HashVO[] vos = dmo.getHashVoArrayByDS(null, "select * from V_PUB_USER_POST_1 where POSTNAME like '%��Ա%'");
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
					insert.putFieldValue("state", "������");
					insert.putFieldValue("seq", j + 1);
					insert.putFieldValue("timezone", timezone);
					if ("�ܷ�".equals(date[j][0])) {
						insert.putFieldValue("KOUOFEN", "100.0");
					} else {
						insert.putFieldValue("KOUOFEN", "0.0");
					}

					list.add(insert.getSQL());
				}
			}
			dmo.executeBatchByDS(null, list);
			str = "��Ա���������������ֿ�ʼ�ɹ�";
		} catch (Exception e) {
			str = "��Ա���������������ֿ�ʼʧ��";
			e.printStackTrace();
		}
		return str;
	}

	public String[][] getTowWeiDate() {
		String[][] date = new String[][] { { "ְҵ����", "��װ", "5.0", "", "��Ҫ����װ������ͳһ���淶������" }, { "ְҵ����", "����", "5.0", "", "վ�ˡ����ˡ����˶�ׯ��" }, { "Ӫҵ׼��", "��ʱ", "3.0", "", "׼������Ҫ��Ӫҵǰ���" }, { "����ڷŹ�λ����", "����", "3.0", "", "�����ֻ����⣬��Ʒ�ڷű��������淶�����򣬲���������ã�����ƾ֤��ӡ�¡�ӡ�ࡢӡ���ᡢ��������ˮ�������Ŵ��������ֽ�β�䡢�ǼǱ���˽����Ʒ�Ȳ��÷����������ϣ�����ҵ����Ҫʹ�õģ�ʹ����Ϻ�������ڹ����»�����У�δ��λ�ڷš����ҵȲ��÷�" },
				{ "����ڷŹ�λ����", "����", "3.0", "", "�����ֻ����⣬��Ʒ�ڷű��������淶�����򣬲���������ã�����ƾ֤��ӡ�¡�ӡ�ࡢӡ���ᡢ��������ˮ�������Ŵ��������ֽ�β�䡢�ǼǱ���˽����Ʒ�Ȳ��÷����������ϣ�����ҵ����Ҫʹ�õģ�ʹ����Ϻ�������ڹ����»�����У�δ��λ�ڷš����ҵȲ��÷�" }, { "����ڷŹ�λ����", "Ǯ��", "3.0", "", "�����ֻ����⣬��Ʒ�ڷű��������淶�����򣬲���������ã�����ƾ֤��ӡ�¡�ӡ�ࡢӡ���ᡢ��������ˮ�������Ŵ��������ֽ�β�䡢�ǼǱ���˽����Ʒ�Ȳ��÷����������ϣ�����ҵ����Ҫʹ�õģ�ʹ����Ϻ�������ڹ����»�����У�δ��λ�ڷš����ҵȲ��÷�" },
				{ "����ڷŹ�λ����", "����", "3.0", "", "�����ֻ����⣬��Ʒ�ڷű��������淶�����򣬲���������ã�����ƾ֤��ӡ�¡�ӡ�ࡢӡ���ᡢ��������ˮ�������Ŵ��������ֽ�β�䡢�ǼǱ���˽����Ʒ�Ȳ��÷����������ϣ�����ҵ����Ҫʹ�õģ�ʹ����Ϻ�������ڹ����»�����У�δ��λ�ڷš����ҵȲ��÷�" }, { "��������", "�㳮��", "3.0", "", "�л��ҡ����ա�ֽм�Ȳ��÷�" }, { "��������", "�����", "3.0", "", "�л��ҡ����ա�ֽм�Ȳ��÷�" }, { "��������", "����", "3.0", "", "�л��ҡ����ա�ֽм�Ȳ��÷�" }, { "��������", "����", "3.0", "", "�л��ҡ����ա�ֽм�Ȳ��÷�" },
				{ "��������", "����", "3.0", "", "�л��ҡ����ա�ֽм�Ȳ��÷�" }, { "����", "����", "2.0", "", "����" }, { "����", "����", "2.0", "", "��������" }, { "����", "����", "2.0", "", "����������" }, { "����", "΢Ц", "2.0", "", "��ʱ΢Ц" }, { "�ʺ�ӭ��", "����", "3.0", "", "����ʾ��" }, { "�ʺ�ӭ��", "�ʺ�", "3.0", "", "���ʡ����á�/�ճ���ν���������" }, { "�Ӵ�����", "����", "5.0", "", "����������ڣ�����ҵ��ִ��һ���Իظ����Է�����Χ��ҵ������������֪�������䵽��ش��ڰ���" },
				{ "�Ӵ�����", "����", "10.0", "", "�������ġ����ġ����ģ��Ծ�ʹ�á��롢���á�лл���Բ����ټ�����������Ҫ����ʲôҵ�����Եȡ������鿴�������������˶Ժ�������ǩ�֡�����������" }, { "�Ӵ�����", "����", "5.0", "", "˫�ֵݽӡ����ĳ���" }, { "�Ӵ�����", "����", "5.0", "", "��ʱ������ͻ������������۲�Ʒ���ݣ���������ҵ�񣬴������ۻ��ᣬ����ͻ�Э����ɷ�����������ۡ�" }, { "�ͱ����", "����", "3.0", "", "����������Ҫ��������ҵ����/�����ߣ��ټ�" }, { "�������", "1.��ҵ���ڼ䲻������̸", "1.0", "", "��Щ����ڷ���ʱ���÷�" },
				{ "�������", "2.ָ���������Ƴ���", "1.0", "", "��Щ����ڷ���ʱ���÷�" }, { "�������", "3.���ҵ��ʱδ��˿�����ʾ���Ǹ��", "1.0", "", "��Щ����ڷ���ʱ���÷�" }, { "�������", "4.�ֻ�δ����", "1.0", "", "��Щ����ڷ���ʱ���÷�" }, { "�������", "5.�����λʱ����δ��λ", "1.0", "", "��Щ����ڷ���ʱ���÷�" }, { "�������", "6.����Ҫ��֫�嶯������", "1.0", "", "��Щ����ڷ���ʱ���÷�" }, { "�������", "7.��ͣҵ��ʱδ����ʾ����", "1.0", "", "��Щ����ڷ���ʱ���÷�" }, { "�������", "8.��ʱ��ҵ��δ���������ͻ�����", "1.0", "", "��Щ����ڷ���ʱ���÷�" },
				{ "�������", "9.��������Ͷ�����", "5.0", "", "��Щ����ڷ���ʱ���÷�" }, { "Ӫҵ����", "��������", "5.0", "", "��Щ����ڷ���ʱ���÷�" }, { "�ܷ�", "", "", "", "" } };
		return date;
	}

	/**
	 * ���ſ��˼ƻ�����
	 * planid:�ƻ�id
	 */
	@Override
	public String getBMsql(String planid) {
		String str = null;
		try {
			InsertSQLBuilder insert = new InsertSQLBuilder("wn_BMPF_table");
			HashVO[] vos = dmo.getHashVoArrayByDS(null, "select * from sal_target_list where type='���Ŷ���ָ��'");
			HashMap deptMap = getdeptName();
			InsertSQLBuilder insertSQLBuilder = new InsertSQLBuilder("wn_BMPF_table");
			List<String> list = new ArrayList<String>();
			List<String> wmsumList = new ArrayList<String>();
			List<String> djsumList = new ArrayList<String>();
			List<String> nksumList = new ArrayList<String>();
			List<String> aqsumList = new ArrayList<String>();
			//������ʱ������޸�
			String[] date = dmo.getStringArrayFirstColByDS(null, "select PFTIME from WN_BMPF_TABLE where state='���ֽ���'");
			String pftime="";
			if(date==null||date.length==0){
				int month=Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
				int currentQuarter=getQuarter2(month);//���ݵ�ǰʱ���ȡ����ǰ����
				pftime=new SimpleDateFormat("yyyy").format(new Date())+"-"+getQuarterEnd(currentQuarter);//��ȡ����ǰ�������һ��
			}else {
			   String maxTime=dmo.getStringValueByDS(null, "SELECT max(PFTIME) PFTINE FROM WN_BMPF_TABLE WHERE STATE='���ֽ���'");
			   int year= Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));//��ȡ����ǰ��
			   int nextQuarter=getQuarter(maxTime)+1;
			   if(nextQuarter>=5){
				   nextQuarter=1;
				   year=year+1;
			   }
			   pftime=String.valueOf(year)+"-"+getQuarterEnd(nextQuarter);
			}
			for (int i = 0; i < vos.length; i++) {//ÿһ��ָ��
				String deptid = vos[i].getStringValue("checkeddept");//��ȡ�������˲��ŵĻ�����
				deptid = deptid.replaceAll(";", ",").substring(1, deptid.length() - 1);
				String[] deptcodes = deptid.split(",");
				String xiangmu = vos[i].getStringValue("name");//��Ŀ����
				String evalstandard = vos[i].getStringValue("evalstandard");//��Ŀ�۷�����
				String weights = vos[i].getStringValue("weights");//��ĿȨ��
				String koufen = "0.0";//�۷����(Ĭ����0.0)
				String state = "������";//��ǰ�������״̬:������
//				String pftime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());//��������
				//Ϊÿ����������ÿһ���
				for (int j = 0; j < deptcodes.length; j++) {
					if ("964".equals(deptcodes[j]) || "965".equals(deptcodes[j])) {
						continue;
					}
					//ÿһ���ָ�궼��Ϊÿһ�����˲�����һ�����˼ƻ�
					String deptName = deptMap.get(deptcodes[j]).toString();//��ȡ����������
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
					//Ϊÿһ�����ŵ�ÿһ�����������ܷ�
					String name = xiangmu.substring(0, xiangmu.indexOf("-"));
					if ("�����ͻ�����".equals(name)) {
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
					} else if ("��������".equals(name)) {
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
					} else if ("��ȫ����".equals(name)) {
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
					} else if ("�ڿغϹ�".equals(name)) {
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
			str = "���ſ��˼ƻ����ɳɹ�";
		} catch (Exception e) {
			e.printStackTrace();
			str = "���ſ��˼ƻ����ɳɹ�";
		}
		return str;
	}

	//��ȡ�����ű��
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
	public String gradeBMScoreEnd() {//������ּƻ� ���ܷ� ��״̬
		try {
			String[] csName = dmo.getStringArrayFirstColByDS(null, "SELECT DISTINCT(xiangmu) FROM WN_BMPF_TABLE WHERE fenzhi IS NULL");
			HashMap codeVos = dmo.getHashMapBySQLByDS(null, "SELECT DISTINCT(SUBSTR(name,0,INSTR(NAME,'-')-1)) name,CHECKEDDEPT FROM sal_target_list  WHERE TYPE ='���Ŷ���ָ��'");
			UpdateSQLBuilder update = new UpdateSQLBuilder("WN_BMPF_TABLE");
			UpdateSQLBuilder update2 = new UpdateSQLBuilder("WN_BMPF_TABLE");
			List<String> list = new ArrayList<String>();
			
			for (int i = 0; i < csName.length; i++) {//����ÿһ�����ִ������ ����  ���� ��ȫ��
				String deptcodes = codeVos.get(csName[i]).toString();
				deptcodes = deptcodes.substring(1, deptcodes.lastIndexOf(";"));
				String[] codes = deptcodes.split(";");//������
				for (int j = 0; j < codes.length; j++) {//ÿ������ÿ������ÿ��С��  �޸�״̬�������ܷ�
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
						update.setWhereCondition("1=1 and deptcode='" + codes[j] + "' and xiangmu like '" + csName[i] + "%' and state='������'");
						update.putFieldValue("state", "���ֽ���");
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
			update3.setWhereCondition("1=1 and state='������'");
			update3.putFieldValue("state", "���ֽ���");
			dmo.executeUpdateByDS(null, update3.getSQL());
			return "��ǰ�ƻ������ɹ�";
		} catch (Exception e) {
			e.printStackTrace();
			return "��ǰ�ƻ�����ʧ��";
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

	//date��ʽ:2019-01-01
	public int getQuarter(String date) {//����date��ȡ����ǰ����
		date = date.substring(5);
		if ("03-31".equals(date)) {
			return 1;
		} else if ("06-30".equals(date)) {
			return 2;
		} else if ("09-30".equals(date)) {
			return 3;
		} else if ("12-31".equals(date)) {
			return 4;
		} else {//����һ�����벻��������
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
 * ȫ����������
 */
	@Override
	public String ImportAll() {//ȫ�����ݵ���
		return importDmo.ImportAll();
	}
/**
 * ����һ�������
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
	 * ÿ���µĿͻ������Ӧ�Ĵ�����Ҫ����������Ҫ�޸����µĻ�����
	 */
	public String getChange(){
		String strjv=null;
		Date date=new Date();
		Calendar scal = Calendar.getInstance();//ʹ��Ĭ��ʱ�������Ի������һ��������
		scal.setTime(date);
		scal.add(Calendar.MONTH, -2);
		scal.set(Calendar.DAY_OF_MONTH,scal.getActualMaximum(Calendar.DATE));
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");  
		String smonth=df.format(scal.getTime());//���µ�����
		Calendar cal = Calendar.getInstance();//ʹ��Ĭ��ʱ�������Ի������һ��������
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		String kmonth=df.format(cal.getTime());//�����µ�����
		StringBuffer sb=new StringBuffer();
		StringBuffer sqlsb=new StringBuffer();
		try {
			UpdateSQLBuilder update=new UpdateSQLBuilder("wnbank.s_loan_dk");
			List list=new ArrayList<String>();
			//�ͻ��������Ϣ��map
			HashMap<String,String> map=dmo.getHashMapBySQLByDS(null,"select xd_col1,xd_col2 from wnbank.s_loan_ryb");
			//�����µĿͻ�֤���Ϳͻ������map
			HashMap<String, String> kmap=dmo.getHashMapBySQLByDS(null,"select distinct(dk.xd_col16),dk.XD_COL81 from wnbank.s_loan_dk dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+kmonth+"' and XD_COL7<>0");
			//���µĿͻ�֤���Ϳͻ������map
			HashMap<String, String> smap=dmo.getHashMapBySQLByDS(null,"select distinct(dk.xd_col16),dk.XD_COL81 from wnbank.s_loan_dk dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+smonth+"' and XD_COL7<>0");
			for(String str:kmap.keySet()){
				if(kmap.get(str).equals(smap.get(str))){
					continue;
				}else{
					update.setWhereCondition("to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+smonth+"' and xd_col16='"+str+"'");
					update.putFieldValue("XD_COL81", kmap.get(str));
					list.add(update.getSQL());
					sb.append(smonth+"�ͻ�֤����Ϊ["+str+"]�ͻ�����Ϊ["+map.get(smap.get(str))+"]�뿼���µĿͻ�������Ϣ���������޸Ŀͻ�����Ϊ["+map.get(kmap.get(str))+"] "+System.getProperty("line.separator"));
				}
				if(list.size()>5000){//zzl 1000 һ�ύ
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
			strjv="�ͻ�������Ϣ���ʧ��";
			e.printStackTrace();
		}
		return "�ͻ�������Ϣ����ɹ�";
	}
	public static void main(String[] args) {
		Date date=new Date();
		Calendar scal = Calendar.getInstance();//ʹ��Ĭ��ʱ�������Ի������һ��������
		scal.setTime(date);
		scal.add(Calendar.MONTH, -2);
		scal.set(Calendar.DAY_OF_MONTH,scal.getActualMaximum(Calendar.DATE));
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");  
		String smonth=df.format(scal.getTime());//���µ�����
		Calendar cal = Calendar.getInstance();//ʹ��Ĭ��ʱ�������Ի������һ��������
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		String kmonth=df.format(cal.getTime());//�����µ�����
		System.out.println(">>>>smonth>>>>>>>"+smonth);
		System.out.println(">>>>kmonth>>>>>>>"+kmonth);
	}
	/**
	 * zzl
	 * @param date
	 * @return ��������ʱ��
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
 * zzl[���ͻ�������Ϣ���]
 * 
 */
	@Override
	public String getCKChange() {
		String strjv=null;
		Date date=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");  
		Calendar cal = Calendar.getInstance();//ʹ��Ĭ��ʱ�������Ի������һ��������
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH,0);
//		cal.add(Calendar.MONTH, -1);//ȡ��ǰ���ڵĺ�һ��. 
		String kmonth=df.format(cal.getTime());//�����µ�����
		StringBuffer sb=new StringBuffer();
		StringBuffer sqlsb=new StringBuffer();
		try{
			UpdateSQLBuilder update=new UpdateSQLBuilder("wnbank.s_loan_KHXX");
			List list=new ArrayList<String>();
			//�ͻ��������Ϣ��map
			HashMap<String,String> map=dmo.getHashMapBySQLByDS(null,"select xd_col1,xd_col2 from wnbank.s_loan_ryb");
			//�����µĿͻ�֤���Ϳͻ������map
			HashMap<String, String> kmap=dmo.getHashMapBySQLByDS(null,"select XD_COL7,XD_COL96 from wnbank.S_LOAN_KHXX where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+kmonth+"' and XD_COL7 is not null and XD_COL96 is not null");
			//����ͻ�֤���Ϳͻ������map
			String year=String.valueOf(Integer.parseInt(kmonth.substring(0,4))-1);			
			HashMap<String, String> smap=dmo.getHashMapBySQLByDS(null,"select XD_COL7,XD_COL96 from wnbank.S_LOAN_KHXX where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+year+"-12-31' and XD_COL7 is not null and XD_COL96 is not null");
			for(String str:kmap.keySet()){
				if(kmap.get(str).equals(smap.get(str))){
					continue;
				}else{
					update.setWhereCondition("to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+year+"-12-31' and xd_col16='"+str+"'");
					update.putFieldValue("XD_COL96", kmap.get(str));
					list.add(update.getSQL());
					sb.append("2018-12-31�ͻ�֤����Ϊ["+str+"]�ͻ�����Ϊ["+map.get(smap.get(str))+"]�뿼���µĿͻ�������Ϣ���������޸Ŀͻ�����Ϊ["+map.get(kmap.get(str))+"] "+System.getProperty("line.separator"));
				}
				if(list.size()>5000){//zzl 1000 һ�ύ
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
			strjv="�ͻ�������Ϣ���ʧ��";
			e.printStackTrace();
		}
		return "�ͻ�������Ϣ����ɹ�";
	}
/**
 * zzl ����ܻ�����
 */
	@Override
	public String getDKFinishB(String date) {
		String jv=null;
		InsertSQLBuilder insert=new InsertSQLBuilder("wn_loans_wcb");
		List list=new ArrayList<String>();
		try {
			//�õ��ͻ������Map
			HashMap<String, String> userMap=dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('�����ͻ�����','����ͻ�����','�����μ�ְ�ͻ�����','�������㸱����','�������㸱����')");
		    //�õ��ͻ������������
			HashMap<String,String> rwMap=dmo.getHashMapBySQLByDS(null, "select A,sum(D) from EXCEL_TAB_53 where year||'-'||month='"+date.substring(0,7)+"' group by A");
			if(rwMap.size()==0){
				return "��ǰʱ�䡾"+date+"��û���ϴ�������";
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
			jv="��ѯ���";
		} catch (Exception e) {
			jv="��ѯʧ��";
			e.printStackTrace();
		}
		
		return jv;
	}
	/**
	 * zzl
	 * ����ܻ���-ũ��Map
	 * @return
	 */
	public HashMap<String, String> getDKNH(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%��˾%' and xd_col166<>'81320101' and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * ����ܻ���-�ڲ�ְ��Map
	 * @return
	 */
	public HashMap<String, String> getDKNBZG(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%��˾%' and xd_col166<>'81320101' and xd_col72 in('26','19') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * ����ܻ���-���ҿͻ�Map
	 * @return
	 */
	public HashMap<String, String> getDKAJ(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%��˾%' and xd_col166<>'81320101' and xd_col72 in('20','29','28','37','h01','h02') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * ����ܻ���-һ����Ȼ��Map
	 * @return
	 */
	public HashMap<String, String> getDKYBZRR(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%��˾%' and xd_col166<>'81320101' and xd_col72 not in ('20','29','28','37','h01','h02','26','19','16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * ����ܻ���-��ҵMap
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
 * �������������ɱ�
 */
	@Override
	public String getDKBalanceXZ(String date) {
		String jl=null;
		try{
			InsertSQLBuilder insert=new InsertSQLBuilder("wn_loan_balance");
			List list=new ArrayList<String>();
			//�õ��ͻ������Map
			HashMap<String, String> userMap=dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('�����ͻ�����','����ͻ�����','�����μ�ְ�ͻ�����','�������㸱����','�������㸱����')");
		    //�õ��ͻ������������
			HashMap<String,String> rwMap=dmo.getHashMapBySQLByDS(null, "select A,sum(E) from EXCEL_TAB_53 where year||'-'||month='"+date.substring(0,7)+"' group by A");
			if(rwMap.size()==0){
				return "��ǰʱ�䡾"+date+"��û���ϴ�������";
			}
			//�õ����������map
			HashMap<String,String> deptCodeMap=dmo.getHashMapBySQLByDS(null, "select sal.name,dept.code from v_sal_personinfo sal left join pub_corp_dept dept on sal.deptid=dept.id");
	        //�õ��������͵�map
			HashMap<String,String> deptTypeMap=dmo.getHashMapBySQLByDS(null, "select sal.name,dept.corptype from v_sal_personinfo sal left join pub_corp_dept dept on sal.deptid=dept.id");
			//Ӫҵ�������´������-ũ��Map
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
				}else if(deptTypeMap.get(str).equals("��������")){
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
			jl="��ѯ�ɹ�";
		}catch (Exception  e){
			jl="��ѯʧ��";
			e.printStackTrace();
		}
		return jl;
	}
	/**
	 * zzl
	 * Ӫҵ�������´������-ũ��Map
	 * @return
	 */
	public HashMap<String, String> getKDKNHSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"'  and xd_col2 not like '��˾' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0  and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * Ӫҵ�����´������-ũ��Map
	 * @return
	 */
	public HashMap<String, String> getSDKNHSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"'  and xd_col2 not like '��˾' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0  and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * Ӫҵ�������´������-����Map
	 * @return
	 */
	public HashMap<String, String> getKDKQTSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"'  and xd_col2 not like '��˾' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * Ӫҵ�����´������-����Map
	 * @return
	 */
	public HashMap<String, String> getSDKQTSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"'  and xd_col2 not like '��˾' and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 where aa.xd_col6<5000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * Ӫҵ���Թ��������Map
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
	 * Ӫҵ�����¶Թ��������Map
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
	 * ��������ũ�������������Map
	 * @return
	 */
	public HashMap<String, String> getKDKCQNHSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%��˾%' group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * ������������ũ�������������Map
	 * @return
	 */
	public HashMap<String, String> getSDKCQNHSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col7) as xd_col7,sum(xd_col6) xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%��˾%' group by XD_COL14,xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * �����������������������Map
	 * @return
	 */
	public HashMap<String, String> getKDKCQQTSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col6) as xd_col6,sum(xd_col7) xd_col7  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%��˾%' group by XD_COL14,xd_col81 ) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * ���������������������������Map
	 * @return
	 */
	public HashMap<String, String> getSDKCQQTSalesOffice(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,sum(aa.xd_col7)/10000 xd_col7 from (select xd_col81,sum(xd_col6) as xd_col6,sum(xd_col7) xd_col7  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101') and XD_COL7<>0 and xd_col2 not like '%��˾%' group by XD_COL14,xd_col81 ) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1 and aa.xd_col6<1000000 group by bb.xd_col2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * �Թ��������100������Map
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
	 * �Թ��������100������Map
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
	 * �����´������-ũ��Map
	 * @return
	 */
	public HashMap<String, String> getKDKNHSales(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,aa.xd_col7 xd_col7 from (select xd_col81,sum(xd_col7)/10000 as xd_col7,sum(xd_col6)/10000 xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%��˾%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * ���´������-ũ��Map
	 * @return
	 */
	public HashMap<String, String> getSDKNHSales(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,aa.xd_col7 xd_col7 from (select xd_col81,sum(xd_col7)/10000 as xd_col7,sum(xd_col6)/10000 xd_col6  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"' and xd_col72 in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%��˾%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * �����´������-����Map
	 * @return
	 */
	public HashMap<String, String> getKDKQTSales(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,aa.xd_col7 xd_col7 from (select xd_col81,sum(xd_col7)/10000 as xd_col7  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%��˾%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * ���´������-����Map
	 * @return
	 */
	public HashMap<String, String> getSDKQTSales(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select bb.xd_col2 as xd_col2 ,aa.xd_col7 xd_col7 from (select xd_col81,sum(xd_col7)/10000 as xd_col7  from wnbank.s_loan_dk where to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"' and xd_col72 not in ('16','24','15','25','30','z01','38','45','46','47','48') and XD_COL22<>'05' and XD_COL166 not in('81320101')  and XD_COL7<>0 and xd_col2 not like '%��˾%' group by xd_col81) aa left join wnbank.s_loan_ryb bb on aa.xd_col81=bb.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
/**
 * zzl
 * ���������
 */
	@Override
	public String getDKHouseholdsXZ(String date) {
		String jl=null;
		try{
			InsertSQLBuilder insert=new InsertSQLBuilder("WN_LOANS_newly");
			List list=new ArrayList<String>();
			//�õ��ͻ������Map
			HashMap<String, String> userMap=dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('�����ͻ�����','����ͻ�����','�����μ�ְ�ͻ�����','�������㸱����','�������㸱����')");
		    //�õ��ͻ������������
			HashMap<String,String> rwMap=dmo.getHashMapBySQLByDS(null, "select A,sum(F) from EXCEL_TAB_53 where year||'-'||month='"+date.substring(0,7)+"' group by A");
			if(rwMap.size()==0){
				return "��ǰʱ�䡾"+date+"��û���ϴ�������";
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
			jl="��ѯ�ɹ�";
		}catch(Exception e){
			jl="��ѯʧ��";
			e.printStackTrace();
		}
		return jl;
	}
	/**
	 * zzl
	 * �����´����-ũ��Map
	 * @return
	 */
	public HashMap<String, String> getKDKNHNewly(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%��˾%' and xd_col166<>'81320101' and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * ���´����-ũ��Map
	 * @return
	 */
	public HashMap<String, String> getSDKNHNewly(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%��˾%' and xd_col166<>'81320101' and xd_col72 in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * �����´����-����Map
	 * @return
	 */
	public HashMap<String, String> getKDKQTNewly(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%��˾%' and xd_col166<>'81320101' and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+date+"') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * �����´����-����Map
	 * @return
	 */
	public HashMap<String, String> getSDKQTNewly(String date){
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map=dmo.getHashMapBySQLByDS(null,"select rr.xd_col2 xd_col2,dgxj.countxj countxj from (select dg.xd_col81 xd_col81,count(dg.xd_col81) countxj from( select distinct(xd_col16) xd_col16,xd_col81 xd_col81 from wnbank.s_loan_dk dk where xd_col7<>0 and xd_col22<>'05' and xd_col2 not like '%��˾%' and xd_col166<>'81320101' and xd_col72 not in('16','24','15','25','30','z01','38','45','46','47','48') and to_char(to_date(load_dates,'yyyy-mm-dd'),'yyyy-mm-dd')='"+getSMonthDate(date)+"') dg group by dg.xd_col81) dgxj left join wnbank.s_loan_ryb rr on dgxj.xd_col81=rr.xd_col1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * zzl
	 * �����´����-��ҵMap
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
	 * ���´����-��ҵMap
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
 * zzl ���ⲻ��������ɱ�
 * @param date
 * @return
 */
	@Override
	public String getBadLoans(String date) {
		String jl=null;
		try{
			InsertSQLBuilder insert=new InsertSQLBuilder("WN_OFFDK_BAB");
			List list=new ArrayList<String>();
			//�õ��ͻ������Map
			HashMap<String, String> userMap=dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('�����ͻ�����','����ͻ�����','�����μ�ְ�ͻ�����','�������㸱����','�������㸱����')");
		    //�õ��ͻ������������
			HashMap<String,String> rwMap=dmo.getHashMapBySQLByDS(null, "select A,sum(K) from EXCEL_TAB_53 where year||'-'||month='"+date.substring(0,7)+"' group by A");
			if(rwMap.size()==0){
				return "��ǰʱ�䡾"+date+"��û���ϴ�������";
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
			jl="��ѯ�ɹ�";
		}catch (Exception e){
			jl="��ѯʧ��";
			e.printStackTrace();
		}
		return jl;
	}
	/**
	 * zzl
	 * �ֽ��ջر��ⲻ�����Ϣ���2017Map
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
	 * �ֽ��ջر��ⲻ�����Ϣ���(2018)Map
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
	 * zzl �����³�������
	 * @param date
	 * @return
	 */
	public String getMonthC(String date){
		date=date.substring(0,7);
		return date+"-01";
	}
	/**
	 * zzl �������������
	 * @param date
	 * @return
	 */
	public String getYearC(String date){
		date=date.substring(0,4);
		date=String.valueOf(Integer.valueOf(date)-1);
		return date+"-12-31";
	}
/**
 * zzl[�ջش�������������ɱ�&��������ѹ��] 
 */
	@Override
	public String getTheStockOfLoan(String date) {
		String jl=null;
		try{
			InsertSQLBuilder insert=new InsertSQLBuilder("WN_Stock_Loan");
			List list=new ArrayList<String>();
			//�õ��ͻ������Map
			HashMap<String, String> userMap=dmo.getHashMapBySQLByDS(null, "select name,name from v_sal_personinfo where stationkind in('�����ͻ�����','����ͻ�����','�����μ�ְ�ͻ�����','�������㸱����','�������㸱����')");
		    //�õ��ͻ������������
			HashMap<String,String> rwMap=dmo.getHashMapBySQLByDS(null, "select A,sum(G) from EXCEL_TAB_53 where year||'-'||month='"+date.substring(0,7)+"' group by A");
			if(rwMap.size()==0){
				return "��ǰʱ�䡾"+date+"��û���ϴ�������";
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
			jl="��ѯ�ɹ�";
		}catch (Exception e){
			jl="��ѯʧ��";
			e.printStackTrace();
		}

		return jl;
	}
	/**
	 * zzl
	 * �ջش������������
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
////		contentToTxt("C:\\Users\\longlonggo521\\Desktop\\niubi","niubi.txt","ţ��+ɵ��");
////		StringBuffer sb=new StringBuffer();
////		sb.append("������");
////		sb.append("ţ��");
////		sb.append("����");
////		System.out.println(">>>>>>>>>>>>");
//	}
	/**
	 * ��¼�ͻ�����������־
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
			// ����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
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
	 * ��¼�ͻ�������SQL����־
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
			// ����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
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
