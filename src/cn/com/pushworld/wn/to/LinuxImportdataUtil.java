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
	 * 登录Linux系统 (功能执行OK)
	 * @param ip:虚拟机IP
	 * @param username:用户名
	 * @param password:密码
	 * @return
	 */
	public static  Connection login(String ip, String username, String password) {
		boolean flag = false;
		Connection conn = null;
		try {
			conn = new Connection(ip);
			conn.connect();//连接Linux
			flag = conn.authenticateWithPassword(username, password);//登录认证
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
	 * @param conn:和虚拟机建立连接
	 * @param cmd:执行的cmd命令
	 * @return
	 */
	public static String execute(Connection conn, String cmd) {
		String result = "";
		try {
			if (conn != null) {
				Session session = conn.openSession();//打开会话建立连接
				session.execCommand(cmd);//执行Shell脚本命令
				result="执行命令成功,链接conn" + conn + ",执行的命令:" + cmd;
				conn.close();
				session.close();
			}else{
				result="未正常连接服务器,错误的连接为:"+conn;
			}
		} catch (Exception e) {
			result="执行命令失败,链接为:" + conn + ",执行的命令:" + cmd;
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return result;
	}

	/**
	 * 获取到上一次导入中的截止日期
	 * @return
	 */
	public  static  String getLastImport1(){
		String result="";
		try {
			Connection login = login("192.168.1.25", "oracle", "ABCabc@123");//登录Linux系统
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
	 * 计算导入数据的大小
	 * @return
	 */
	public static long getImportLength(){
	    Long importLength=0L;
	    Connection login = null;
		try {
		    login=login("192.168.1.25", "oracle", "ABCabc@123");//登录Linux系统
			SFTPv3Client sft=new SFTPv3Client(login);
			String remotePath="/home/oracle/wnData/";
			Vector ls = sft.ls(remotePath);
			String lastImport=getLastImport1();//获取到上一次导入最后的日期
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
	 * 计算导入一天的文件的大小
	 * @param date:日期格式(格式:20190101)
	 * @return
	 */
	public static long getImportLength(String date){
		long importLength=0L;
		Connection login =null;
		try {
			login=login("192.168.1.25", "oracle", "ABCabc@123");//登录Linux系统
			SFTPv3Client sft=new SFTPv3Client(login);
			String remotePath="/home/oracle/wnData/"+date;
			Session session = login.openSession();
			String cmd="sh /home/oracle/wntest2.sh";
			session.execCommand(cmd);//暂时用这个方式解压文件
			Vector ls = sft.ls(remotePath);
			for (int i = 0; i < ls.size(); i++) {
				SFTPv3DirectoryEntry s = new SFTPv3DirectoryEntry();
				s=(SFTPv3DirectoryEntry)ls.get(i);
				String fileName=s.filename;
				String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
				if("del".equals(suffix)){
					importLength+=s.attributes.size;//只计算del文件的大小
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
