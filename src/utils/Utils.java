package utils;

import java.io.File;

public class Utils {
	
	public static String resolveFileName(String filePath){
		int subFrom = filePath.lastIndexOf(File.separatorChar) > - 1 ? filePath.lastIndexOf(File.separatorChar) + 1 : 0;
		return filePath.substring(subFrom, filePath.length());
	}

}
