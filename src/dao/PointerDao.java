package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.TaskList;


public class PointerDao {
	public int getPointer(Connection con) throws Exception  {
		String sql = "select * from point where num=1";		
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		int res;
		while (resultSet.next()) {
			res = resultSet.getInt("pointer");
			return res;
		}
		return -1;
	}
	
	public int increase(Connection con) throws Exception{
		String sql = "update point set pointer=pointer+1 where num=1";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		return preparedStatement.executeUpdate();
	}
	
}
