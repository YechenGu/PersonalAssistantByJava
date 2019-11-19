package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Task;
import model.TaskList;
import util.Stringutil;

public class TaskDao {
	public int add(Connection con,Task task) throws Exception {
		String sql = "insert into task values(?,?,?,?,?,?)";	//id是自增的，可以设计为null
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1,task.getId());
		preparedStatement.setInt(2,task.getListId());
		preparedStatement.setString(3, task.getName());
		preparedStatement.setString(4,task.getDesc());
		preparedStatement.setInt(5, task.getCompleted());
		preparedStatement.setInt(6,task.getTaskType());
		return preparedStatement.executeUpdate();		//预处理语句来执行更新操作
		}

	public ResultSet list(Connection con,Task task)throws Exception  {
		StringBuffer sb =  new StringBuffer("select * from task");
		if (!Stringutil.isEmpty(task.getName())) {
			sb.append(" and name like '%"+task.getName()+"%'");
			//bug report : missing SPACE can cause sql error
		}
		if (task.getListId() != 0) {
			sb.append(" and listId like '%"+task.getListId()+"%'");
		}
		PreparedStatement preparedStatement = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		return preparedStatement.executeQuery();
	}
	
	public ResultSet listByOrder(Connection con,Task task,int order)throws Exception  {
		StringBuffer sb =  new StringBuffer("select * from task");
		if (!Stringutil.isEmpty(task.getName())) {
			sb.append(" and name like '%"+task.getName()+"%'");
			//bug report : missing SPACE can cause sql error
		}
		if (task.getListId() != 0) {
			sb.append(" and listId like '%"+task.getListId()+"%'");
		}
		if (order==1) {
			sb.append(" order by id");
		}
		if (order==2) {
			sb.append(" order by name");
		}
		if (order==3) {
			sb.append(" order by taskType");
		}
		PreparedStatement preparedStatement = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		return preparedStatement.executeQuery();
	}
	
	public int delete(Connection con,int id) throws Exception{
		String sql = "delete from task where id=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		return preparedStatement.executeUpdate();
	}
	//操作成功，返回为1
//	public int deleteWithList(Connection con,int listId) throws Exception{
//		String sql = "delete from task where listId=?";
//		PreparedStatement preparedStatement = con.prepareStatement(sql);
//		preparedStatement.setInt(1, listId);
//		return preparedStatement.executeUpdate();
//	}
	
	public int update(Connection con,Task task) throws Exception{
		String sql = "update task set name=?,task.desc=? where id=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1,task.getName());
		preparedStatement.setString(2,task.getDesc());
		preparedStatement.setInt(3, task.getId());
		return preparedStatement.executeUpdate();
	}
	
	public int complete(Connection con,Task task) throws Exception{
		String sql = "update task set isCompleted=1 where id=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, task.getId());
		return preparedStatement.executeUpdate();
	}
}
