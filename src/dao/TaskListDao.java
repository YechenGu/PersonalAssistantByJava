package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.TaskList;
import util.Stringutil;

/**
 * 任务列表data access object 类
 * 即数据操作类
 * 执行数据库操作
 * @author Administrator
 *
 */
public class TaskListDao {
	public int add(Connection con,TaskList taskList) throws Exception {
		String sql = "insert into task_list values(null,?,?)";	//id是自增的，可以设计为null
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1,taskList.getName());
		preparedStatement.setString(2, taskList.getType());
		return preparedStatement.executeUpdate();		//预处理语句来执行更新操作
		}
	
	public ResultSet list(Connection con,TaskList taskList) throws Exception {
		StringBuffer sb =  new StringBuffer("select * from task_list");
		//用字符拼接类来防止名字为空的情况
		if (!Stringutil.isEmpty(taskList.getName())) {
			sb.append(" and name like '%"+taskList.getName()+"%'");
			//bug report : missing SPACE can cause sql error
		}
			PreparedStatement preparedStatement = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
			return preparedStatement.executeQuery();	//select 语句执行的是excuteQuery,返回的是ResultSet
	}
	
	public ResultSet listByOrder(Connection con,TaskList taskList,int order) throws Exception {
		StringBuffer sb =  new StringBuffer("select * from task_list");
		//用字符拼接类来防止名字为空的情况
		if (!Stringutil.isEmpty(taskList.getName())) {
			sb.append(" and name like '%"+taskList.getName()+"%'");
			//bug report : missing SPACE can cause sql error
		}
		if (order==1) {
			sb.append(" order by id");
		}
		if (order==2) {
			sb.append(" order by name");
		}
		if (order==3) {
			sb.append(" order by type");
		}
			PreparedStatement preparedStatement = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
			return preparedStatement.executeQuery();	//select 语句执行的是excuteQuery,返回的是ResultSet
	}
	
	public int delete(Connection con,String id) throws Exception{
		String sql = "delete from task_list where id=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, id);
		return preparedStatement.executeUpdate();
	}
	//操作成功，返回为1
	
	public int update(Connection con,TaskList taskList) throws Exception{
		String sql = "update task_list set name=?,type=? where id=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1,taskList.getName());
		preparedStatement.setString(2, taskList.getType());
		preparedStatement.setInt(3, taskList.getId());
		return preparedStatement.executeUpdate();
	}
}
