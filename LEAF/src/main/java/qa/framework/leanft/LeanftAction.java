package qa.framework.leanft;

public class LeanftAction {
/**@author BathriYO
 * @param field
 * @param text
 * @return GeneralLeanftException
 */
	/*public static void setText(Field field,String text) {
		String status ="FAIL";
		try
		{
			field.setText(text);
			status="PASS";
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}finally {
			Report.printOperation("Set Text");
			Report.printValue(text);
			Report.printStatus(status);
		}
	}*/
/**@author BathriYO
 *  @param field
 *  @return String Field Text
 */
	/*public static String getText(Field field) {
		String text=null;
		String status ="FAIL";
		try
		{
			text=field.getText();
			status="PASS";
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}finally {
			Report.printOperation("Get Text");
			Report.printStatus(status);
		}
		return text;
	}*/
	/**Send TE keys to screen
	 * @author BathriYO
	 * @param screen
	 * @param enumkeys
	 */
	/*public static void sendTEKeys(Screen screen,String enumKeys) {
		String status ="FAIL";
		try {
			screen.sendTEKeys(enumKeys);
			status="PASS";
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}finally {
			Report.printOperation("Send TE Keys");
			Report.printValue(enumKeys);
			Report.printStatus(status);
		}
		}*/
	/**Synchronization
	 * @author BathriYO
	 * @param testObj
	 */
	/*public static void sync(TestObject testObj) {
		String status ="FAIL";
		try {
			testObj.waitUntilExists(GlobalVariables.waitTime);
			status="PASS";
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}finally {
			Report.printOperation("sync");
			Report.printStatus(status);
		}
	}*/
/**Synchronization
 * @param testObj
 * @param millisec
 */
/*	public static void sync(TestObject testObj,int millisec) {
		String status ="FAIL";
		try {
			testObj.waitUntilExists(millisec);
			status="PASS";
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}finally {
			Report.printOperation("sync");
			Report.printStatus(status);
		}
	}*/
	/**checking if screen exist or not
	 *@param screen
	 *@return boolean 
	 */
	/*public static boolean isExists(Screen screen,int timeInSecond) {
		String status ="FAIL";
		boolean flag=false;
		try {
			flag=screen.exists(timeInSecond);
			status="PASS";
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}finally {
			Report.printOperation("isExists");
			Report.printStatus(status);
		}
		return flag;
	}*/
	/**checking if field exists or not
	 * @param field
	 * @return boolean
	 */
	/*public static boolean isExists(Field field) {
		String status ="FAIL";
		boolean flag=false;
		try {
			flag=field.exists();
			status="PASS";
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}finally {
			Report.printOperation("isExists");
			Report.printStatus(status);
		}
		return flag;
	}*/
	}


