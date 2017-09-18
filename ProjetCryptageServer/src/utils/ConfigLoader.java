package utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
	
	private static final ConfigLoader instance = new ConfigLoader();
	private final String[] props = new String[8];
	
	private ConfigLoader(){
		try(InputStream is = new FileInputStream("config.properties")) {
			Properties prop = new Properties();
			prop.load(is);
			props[0] = prop.getProperty("ConnectionString");
			props[1] = prop.getProperty("CBCKey");
			props[2] = prop.getProperty("HashPrefix");
			props[3] = prop.getProperty("HashPostfix");
			props[4] = prop.getProperty("CBCVector");
		}catch(Exception e){
			System.err.println("Properties not found");
		}
	}
	
	public static String getConnectionString(){
		return instance.props[0];
	}
	
	public static String getCBCKey (){
		return instance.props[1];
	}
	
	public static String getHashPrefix (){
		return instance.props[2];
	}
	
	public static String getHashPostfix (){
		return instance.props[3];
	}
	
	public static String getCBCVector (){
		return instance.props[4];
	}

}
