package connection;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionManager {
	
	private static String cnString;
	
	private ConnectionManager(){
		try(InputStream is = new FileInputStream("config.properties")) {
			Properties prop = new Properties();
			prop.load(is);
			cnString = prop.getProperty("ConnectionString");
		}catch(Exception e){
			System.err.println("Properties not found");
		}
	}
	
	public static Connection getConnection(){
		try{
			return DriverManager.getConnection(cnString);
		}catch(Exception e){
			return null;
		}
	}
}
