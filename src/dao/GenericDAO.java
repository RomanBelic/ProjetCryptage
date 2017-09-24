package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import connection.ConnectionManager;
import interfaces.Patterns.IDelegate;

public class GenericDAO<T> {
	
	public T getObject(String query, IDelegate<T, ResultSet> reader, Object...params){
		T obj = null;
		try (Connection cn = ConnectionManager.getConnection()){
			PreparedStatement st = cn.prepareStatement(query);
			setParameters(st, params);
			ResultSet rs = st.executeQuery();
			if(rs.next()){
				obj = reader.action(rs);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return obj;
	}
	
	public int insertObject(String query, Object...params){
		int id = 0;
		try (Connection cn = ConnectionManager.getConnection()){
			PreparedStatement st = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			setParameters(st, params);
			if (st.executeUpdate() > 0){
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()){
					id = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			id = -1;
			System.err.println(e.getMessage());
		}
		return id;
	}
	
	private void setParameters(PreparedStatement st, Object[] params) throws SQLException{
		int index = 1;
		for(Object pValue : params){
			if (pValue instanceof String){
				st.setString(index, (String)pValue);
			}
			else if (pValue instanceof Integer){
				st.setInt(index, (int)pValue);
			}
			else if (pValue instanceof Long){
				st.setLong(index, (long)pValue);
			}
			else {
				st.setNull(index, Types.NULL);
			}
			index++;
		}
	}

}
