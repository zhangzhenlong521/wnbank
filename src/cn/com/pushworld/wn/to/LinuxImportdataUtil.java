package cn.com.pushworld.wn.to;

import java.io.BufferedReader;
import java.io.InputStreamReader;



import cn.com.infostrategy.ui.mdata.hmui.util.Base64.InputStream;
import ch.ethz.ssh2.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class LinuxImportdataUtil {
	/**
	 * ��¼Linuxϵͳ (����ִ��OK)
	 * @param ip:�����IP
	 * @param username:�û���
	 * @param password:����
	 * @return
	 */
	public static  Connection login(String ip, String username, String password) {
		boolean flag = false;
		Connection conn = null;
		try {
			conn = new Connection(ip);
			conn.connect();//����Linux
			flag = conn.authenticateWithPassword(username, password);//��¼��֤
			if (flag) {
				return conn;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	/**
	 * 
	 * @param conn:���������������
	 * @param cmd:ִ�е�cmd����
	 * @return
	 */
	public static String execute(Connection conn, String cmd) {
		String result = "";
		try {
			if (conn != null) {
				Session session = conn.openSession();//�򿪻Ự��������
				session.execCommand(cmd);//ִ��Shell�ű�����
				result="ִ������ɹ�,����conn" + conn + ",ִ�е�����:" + cmd;
				conn.close();
				session.close();
			}else{
				result="δ�������ӷ�����,���������Ϊ:"+conn;
			}
		} catch (Exception e) {
			result="ִ������ʧ��,����Ϊ:" + conn + ",ִ�е�����:" + cmd;
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return result;
	}

	/**
	 * ��ȡ����һ�ε����еĽ�ֹ����
	 * @return
	 */
	public  static  String getLastImport1(){
		String result="";
		try {
			Connection login = login("192.168.1.25", "oracle", "ABCabc@123");//��¼Linuxϵͳ
			SFTPv3Client sft=new SFTPv3Client(login);
			String remotePath="/home/oracle/wnCtlLog/";
			Vector ls = sft.ls(remotePath);
			SFTPv3DirectoryEntry s = new SFTPv3DirectoryEntry();
			s=(SFTPv3DirectoryEntry)ls.get(ls.size()-1);
			result=s.filename;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String getNextDay(String date) {
		String nextDate = "";
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date d = format.parse(date);
			cal.setTime(d);
			int day = cal.get(Calendar.DATE);
			cal.set(Calendar.DATE, day + 1);
			nextDate = format.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nextDate;
	}
	
	/**
	 * ���㵼�����ݵĴ�С
	 * @return
	 */
	public static long getImportLength(){
	    Long importLength=0L;
	    Connection login = null;
		try {
		    login=login("192.168.1.25", "oracle", "ABCabc@123");//��¼Linuxϵͳ
			SFTPv3Client sft=new SFTPv3Client(login);
			String remotePath="/home/oracle/wnData/";
			Vector ls = sft.ls(remotePath);
			String lastImport=getLastImport1();//��ȡ����һ�ε�����������
			for (int i = 0; i < ls.size(); i++) {
				SFTPv3DirectoryEntry s = new SFTPv3DirectoryEntry();
				s=(SFTPv3DirectoryEntry)ls.get(i);
				String fileName=s.filename;
				if(fileName.compareTo(lastImport)>1){
					continue;
				}
				importLength+=s.attributes.size;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			login.close();
		}
		return importLength;
	}
	/**
	 * ���㵼��һ����ļ��Ĵ�С
	 * @param date:���ڸ�ʽ(��ʽ:20190101)
	 * @return
	 */
	public static long getImportLength(String date){
		long importLength=0L;
		Connection login =null;
		try {
			login=login("192.168.1.25", "oracle", "ABCabc@123");//��¼Linuxϵͳ
			SFTPv3Client sft=new SFTPv3Client(login);
			String remotePath="/home/oracle/wnData/"+date;
			Session session = login.openSession();
			String cmd="sh /home/oracle/wntest2.sh";
			session.execCommand(cmd);//��ʱ�������ʽ��ѹ�ļ�
			Vector ls = sft.ls(remotePath);
			for (int i = 0; i < ls.size(); i++) {
				SFTPv3DirectoryEntry s = new SFTPv3DirectoryEntry();
				s=(SFTPv3DirectoryEntry)ls.get(i);
				String fileName=s.filename;
				String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
				if("del".equals(suffix)){
					importLength+=s.attributes.size;//ֻ����del�ļ��Ĵ�С
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			login.close();
		}
		return importLength;
	}
	
	
}
