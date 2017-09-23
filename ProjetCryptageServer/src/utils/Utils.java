package utils;

public class Utils {
	
	public static String resolveExtension(String fileName){
		StringBuilder strb = new StringBuilder(32);
		int point = fileName.indexOf('.');
		int plast = fileName.lastIndexOf('.');
		int limit = point == plast ? fileName.length() : plast;
		while (point < limit){
			strb.append(fileName.charAt(point));
			point++;
		}
		return strb.toString();
	}
	
	public static String resolveName(String fileName){
		return fileName.substring(0, fileName.indexOf('.'));
	}

}
