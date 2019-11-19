package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.CycleTask;
import model.Task;
import model.TempTask;
import util.Stringutil;

public class CycleTaskDao {
	public int add(Connection con,CycleTask cycleTask) throws Exception {
		String sql = "insert into cycletask values(?,?,?,?,?,?,?,?,?)";	//id是自增的，可以设计为null
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1,cycleTask.getId());
		preparedStatement.setInt(2,cycleTask.getListId());
		preparedStatement.setString(3, cycleTask.getName());
		preparedStatement.setString(4,cycleTask.getDesc());
		preparedStatement.setInt(5, cycleTask.getCompleted());
		preparedStatement.setInt(6,cycleTask.getTaskType());
		preparedStatement.setDate(7,(Date) cycleTask.getExcDate());
		preparedStatement.setInt(8,cycleTask.getTimes());
		preparedStatement.setInt(9,cycleTask.getCycle());
		return preparedStatement.executeUpdate();		//预处理语句来执行更新操作
		}
	
	public ResultSet list(Connection con,CycleTask task)throws Exception  {
		StringBuffer sb =  new StringBuffer("select * from cycletask");
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
		String sql = "delete from cycletask where id=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		return preparedStatement.executeUpdate();
	}
	//操作成功，返回为1
//	public int deleteWithList(Connection con,int listId) throws Exception{
//		String sql = "delete from cycletask where listId=?";
//		PreparedStatement preparedStatement = con.prepareStatement(sql);
//		preparedStatement.setInt(1, listId);
//		return preparedStatement.executeUpdate();
//	}
	
	public int update(Connection con,CycleTask task) throws Exception{
		String sql = "update cycletask set name=?,cycletask.desc=?,excDate=?,times=?,cycle=? where id=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, task.getName());
		preparedStatement.setString(2,task.getDesc());;
		preparedStatement.setDate(3,(Date) task.getExcDate());
		preparedStatement.setInt(4,task.getTimes());
		preparedStatement.setInt(5,task.getCycle());
		preparedStatement.setInt(6, task.getId());
		return preparedStatement.executeUpdate();
	}
	
	public int complete(Connection con,CycleTask task) throws Exception{
		String sql = "update cycletask set isCompleted=? where id=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, task.getCompleted());
		preparedStatement.setInt(2, task.getId());
		return preparedStatement.executeUpdate();
	}
}
