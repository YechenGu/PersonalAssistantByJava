package util;

import java.sql.Connection;
import java.sql.DriverManager;

import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * ���ݿ�Ĺ�����
 * ִ�����ݿ�����--�������Ӷ���
 * ִ�����ݿ�ر�--����void
 * @author Administrator
 *
 */
public class Dbutil {
     private String dbUrl = "jdbc:mysql://localhost:3306/db_assistant";
     private String dbUserName = "root";
     private String dbPassword = "123456";
     private String jdbcName = "com.mysql.jdbc.Driver";
     
     /**
                   ��ȡ���ݿ�����
      * @return Connection
      * @throws Exception
      */
     public Connection getCon() throws Exception {
		Class.forName(jdbcName);
		Connection con= DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
		return con;
	}
     
     /**
      	* �ر����ݿ�����
      * @param con
      * @throws Exception
      */
     public void closeCon(Connection con) throws Exception {
		if (con != null) {
			con.close();
		}
	}
     
//     public static void main(String[] args) {
//		final Dbutil dbutil = new Dbutil();
//		try {
//			dbutil.getCon();
//			System.out.println("666");
//		} catch (Exception e) {
//			// TODO �Զ����ɵ� catch ��
//			e.printStackTrace();
//		}
//		
//     }
}
