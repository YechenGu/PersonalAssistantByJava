package util;

import java.sql.Connection;
import java.sql.DriverManager;

import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * 数据库的工具类
 * 执行数据库连接--返回连接对象
 * 执行数据库关闭--返回void
 * @author Administrator
 *
 */
public class Dbutil {
     private String dbUrl = "jdbc:mysql://localhost:3306/db_assistant";
     private String dbUserName = "root";
     private String dbPassword = "123456";
     private String jdbcName = "com.mysql.jdbc.Driver";
     
     /**
                   获取数据库连接
      * @return Connection
      * @throws Exception
      */
     public Connection getCon() throws Exception {
		Class.forName(jdbcName);
		Connection con= DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
		return con;
	}
     
     /**
      	* 关闭数据库连接
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
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
//		
//     }
}
