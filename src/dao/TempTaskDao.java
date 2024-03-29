package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

import model.Task;
import model.TempTask;
import util.Stringutil;

public class TempTaskDao {
	public int add(Connection con,TempTask tempTask) throws Exception {
		String sql = "insert into tempTask values(?,?,?,?,?,?,?)";	//id是自增的，可以设计为null
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1,tempTask.getId());
		preparedStatement.setInt(2,tempTask.getListId());
		preparedStatement.setString(3, tempTask.getName());
		preparedStatement.setString(4,tempTask.getDesc());
		preparedStatement.setInt(5, tempTask.getCompleted());
		preparedStatement.setInt(6,tempTask.getTaskType());
		preparedStatement.setDate(7,(Date) tempTask.getDueDate());
		return preparedStatement.executeUpdate();		//预处理语句来执行更新操作
		}
	
	public ResultSet list(Connection con,TempTask task)throws Exception  {
		StringBuffer sb =  new StringBuffer("select * from temptask");
		if (!Stringutil.isEmpty(task.getName())) {
			sb.append(" and name like '%"+task.getName()+"%'");
			//bug report : missing SPACE can cause sql error
		}
		if (task.getId() != 0) {
			sb.append(" and id like '%"+task.getId()+"%'");
		}
		if (task.getListId() != 0) {
			sb.append(" and listId like '%"+task.getListId()+"%'");
		}
		PreparedStatement preparedStatement = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		return preparedStatement.executeQuery();
	}
	
	public int delete(Connection con,int id) throws Exception{
		String sql = "delete from temptask where id=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		return preparedStatement.executeUpdate();
	}
	//操作成功，返回为1
	
//	public int deleteWithList(Connection con,int listId) throws Exception{
//		String sql = "delete from temptask where listId=?";
//		PreparedStatement preparedStatement = con.prepareStatement(sql);
//		preparedStatement.setInt(1, listId);
//		return preparedStatement.executeUpdate();
//	}
	
	public int update(Connection con,TempTask task) throws Exception{
		String sql = "update temptask set name=?,temptask.desc=?,dueDate=? where id=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, task.getName());
		preparedStatement.setString(2,task.getDesc());;
		preparedStatement.setDate(3,(Date) task.getDueDate());
		preparedStatement.setInt(4, task.getId());
		return preparedStatement.executeUpdate();
	}
	
	public int complete(Connection con,TempTask task) throws Exception{
		String sql = "update temptask set isCompleted=1 where id=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, task.getId());
		return preparedStatement.executeUpdate();
	}
}
