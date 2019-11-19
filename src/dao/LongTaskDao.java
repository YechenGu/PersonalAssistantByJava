package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.LongTask;
import model.Task;
import model.TempTask;
import util.Stringutil;

public class LongTaskDao {
	public int add(Connection con,LongTask longTask) throws Exception {
		String sql = "insert into longtask values(?,?,?,?,?,?,?)";	//id是自增的，可以设计为null
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1,longTask.getId());
		preparedStatement.setInt(2,longTask.getListId());
		preparedStatement.setString(3, longTask.getName());
		preparedStatement.setString(4,longTask.getDesc());
		preparedStatement.setInt(5, longTask.getCompleted());
		preparedStatement.setInt(6,longTask.getTaskType());
		preparedStatement.setDate(7,(Date) longTask.getDueDate());
		return preparedStatement.executeUpdate();		//预处理语句来执行更新操作
		}
	
	public ResultSet list(Connection con,LongTask task)throws Exception  {
		StringBuffer sb =  new StringBuffer("select * from longtask");
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
		String sql = "delete from longtask where id=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		return preparedStatement.executeUpdate();
	}
	//操作成功，返回为1
//	public int deleteWithList(Connection con,int listId) throws Exception{
//		String sql = "delete from longtask where listId=?";
//		PreparedStatement preparedStatement = con.prepareStatement(sql);
//		preparedStatement.setInt(1, listId);
//		return preparedStatement.executeUpdate();
//	}
	
	public int update(Connection con,LongTask task) throws Exception{
		String sql = "update longtask set name=?,longtask.desc=?,dueDate=? where id=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, task.getName());
		preparedStatement.setString(2,task.getDesc());;
		preparedStatement.setDate(3,(Date) task.getDueDate());
		preparedStatement.setInt(4, task.getId());
		return preparedStatement.executeUpdate();
	}
	
	public int complete(Connection con,LongTask task) throws Exception{
		String sql = "update longtask set isCompleted=? where id=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, task.getCompleted());
		preparedStatement.setInt(2, task.getId());
		return preparedStatement.executeUpdate();
	}
}
