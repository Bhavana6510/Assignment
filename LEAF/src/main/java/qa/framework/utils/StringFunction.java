package qa.framework.utils;

import com.google.common.base.CharMatcher;

public class StringFunction {
	
	public static String checkIsASCII(String value) {
		String strASCII="";
		boolean isASCII = CharMatcher.ascii().matchesAllOf(value);
		
		if(!isASCII) {
			char[] charArray=value.toCharArray();
			
			for(char c: charArray) {
				boolean isCharASCII = CharMatcher.ascii().matches(c);
				
				if(isCharASCII) {
					strASCII = strASCII +c;
				}
				
			}
			return strASCII;
		}else {
			return value;
		}
		
		
	}
	
	public static String cleanJavaString(String value) {
		// strips off all non-ASCII characters
		value = value.replaceAll("[^\\x00-\\x7F]", "");
	 
	    // erases all the ASCII control characters
		value = value.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
	     
	    // removes non-printable characters from Unicode
		value = value.replaceAll("\\p{C}", "");
		
		return value;
	}
	

}
