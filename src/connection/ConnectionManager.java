package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import utils.ConfigLoader;

public class ConnectionManager {
	
	public static Connection getConnection(){
		try{
			return DriverManager.getConnection(ConfigLoader.getConnectionString());
		}catch(Exception e){
			System.err.println(e.getMessage());
			return null;
		}
	}
}
