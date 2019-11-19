package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.TaskList;
import util.Stringutil;

/**
 * �����б�data access object ��
 * �����ݲ�����
 * ִ�����ݿ����
 * @author Administrator
 *
 */
public class TaskListDao {
	public int add(Connection con,TaskList taskList) throws Exception {
		String sql = "insert into task_list values(null,?,?)";	//id�������ģ��������Ϊnull
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1,taskList.getName());
		preparedStatement.setString(2, taskList.getType());
		return preparedStatement.executeUpdate();		//Ԥ���������ִ�и��²���
		}
	
	public ResultSet list(Connection con,TaskList taskList) throws Exception {
		StringBuffer sb =  new StringBuffer("select * from task_list");
		//���ַ�ƴ��������ֹ����Ϊ�յ����
		if (!Stringutil.isEmpty(taskList.getName())) {
			sb.append(" and name like '%"+taskList.getName()+"%'");
			//bug report : missing SPACE can cause sql error
		}
			PreparedStatement preparedStatement = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
			return preparedStatement.executeQuery();	//select ���ִ�е���excuteQuery,���ص���ResultSet
	}
	
	public ResultSet listByOrder(Connection con,TaskList taskList,int order) throws Exception {
		StringBuffer sb =  new StringBuffer("select * from task_list");
		//���ַ�ƴ��������ֹ����Ϊ�յ����
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
			return preparedStatement.executeQuery();	//select ���ִ�е���excuteQuery,���ص���ResultSet
	}
	
	public int delete(Connection con,String id) throws Exception{
		String sql = "delete from task_list where id=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, id);
		return preparedStatement.executeUpdate();
	}
	//�����ɹ�������Ϊ1
	
	public int update(Connection con,TaskList taskList) throws Exception{
		String sql = "update task_list set name=?,type=? where id=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1,taskList.getName());
		preparedStatement.setString(2, taskList.getType());
		preparedStatement.setInt(3, taskList.getId());
		return preparedStatement.executeUpdate();
	}
}
