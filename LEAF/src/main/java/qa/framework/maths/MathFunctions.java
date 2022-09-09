package qa.framework.maths;

import java.text.DecimalFormat;

public class MathFunctions {

	/**
	 * Converts String into float with specified number of decimal digits
	 *
	 * @author 10650956
	 * @param value : String 
	 * @param decimalCount : Integer
	 * @return String
	 */
	public static String floatFormat(String value, int decimalCount) {
		
		/* converting string into float*/
		Float parsedFloat = Float.parseFloat(value);
		
		/* creating DecimalFormat object*/
		DecimalFormat df = new DecimalFormat("0.00000");
		
		/*setting maximum digit after decimal.*/
		df.setMaximumFractionDigits(decimalCount);
		
		/*formatting parse float value*/
		return df.format(parsedFloat);
		
	}
	
	/**
	 * Prefix zero to integer
	 * 
	 * @author 10650956
	 * @param value : String
	 * @param digits : Integer
	 * @return String
	 */
	public static String intFormat(String value, int digits) {
		 
		int parseInt = Integer.parseInt(value);
		 
		 return String.format("%0"+digits+"d", parseInt);
	}
	
	/**
	 * Generate random function in range
	 * 
	 * @author 10650956
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
}
