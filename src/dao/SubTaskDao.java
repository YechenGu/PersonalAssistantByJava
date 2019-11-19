package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.SubTask;
import util.Stringutil;



public class SubTaskDao {
	public int add(Connection con,SubTask task) throws Exception {
		String sql = "insert into subtask values(null,?,?,?,?)";	//id是自增的，可以设计为null
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1,task.getName());
		preparedStatement.setDate(2,(Date) task.getDueDate());
		preparedStatement.setInt(3, task.getFather());
		preparedStatement.setInt(4, task.getFatherTask());
		return preparedStatement.executeUpdate();		//预处理语句来执行更新操作
		}
	
	public ResultSet listFromTask(Connection con,SubTask task)throws Exception  {
		StringBuffer sb =  new StringBuffer("select * from subtask");
		if (task.getFatherTask() != 0) {
			sb.append(" and fatherTask like '%"+task.getFatherTask()+"%'");
		}
		PreparedStatement preparedStatement = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		return preparedStatement.executeQuery();
	}
	
	public ResultSet listFromSub(Connection con,SubTask task)throws Exception  {
		StringBuffer sb =  new StringBuffer("select * from subtask");
		if (task.getFather() != 0) {
			sb.append(" and father like '%"+task.getFather()+"%'");
		}
		PreparedStatement preparedStatement = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		return preparedStatement.executeQuery();
	}
	
}
