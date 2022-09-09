package qa.framework.mainframe;

public class FR_MF_MainframeLoginScr {
	
	/*private Screen screen;
	private Field fldUsername;
	private Field fldPassword;
	
	List<DBRowTO> listEleMainfarmeLoginScr=SQLDriver.getEleObjData("FR_MF_MainframeLoginScr");*/
	
/**using constructor to initialize screen and fields
 * @param tewindow
 */
	/*public FR_MF_MainframeLoginScr() {
		try {
			screen=FR_MF_MainframeWindow.getTeWindow().
					describe(Screen.class, new ScreenDescription.Builder().id(Integer.parseInt(Action.getValue("idScreen", listEleMainfarmeLoginScr))).build());
			fldPassword=screen.describe(Field.class,
					new FieldDescription.Builder().id(Integer.parseInt(Action.getValue("idPassword",listEleMainfarmeLoginScr))).build());
			fldUsername=screen.describe(Field.class,
					new FieldDescription.Builder().id(Integer.parseInt(Action.getValue("idUserName",listEleMainfarmeLoginScr))).build());
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}
	}*/
	/*--------------------------------Methods-------------------------------------------------*/
	/**@author BathriYO
	 * @return boolean
	 */
	/*public boolean isTerminalLoginScrExists(int timeInSeconds) {
		try {
			return LeanftAction.isExists(screen,timeInSeconds);
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}
		return false;
	}*/
	/**Login to terminal
	 * @author BathriYO
	 * @param username
	 * @param password
	 */
	/*public FR_MF_EMSP01Scr LoginToTerminal(String username,String password) {
		try {
			LeanftAction.setText(fldUsername, username);
			LeanftAction.setText(fldPassword, password);
			LeanftAction.sendTEKeys(screen, Keys.ENTER);
			
			return new FR_MF_EMSP01Scr();
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}
		return null;
	}*/
}
