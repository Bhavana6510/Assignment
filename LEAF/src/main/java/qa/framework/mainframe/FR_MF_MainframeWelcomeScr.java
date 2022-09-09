package qa.framework.mainframe;

public class FR_MF_MainframeWelcomeScr {

	/*private Screen screen;
	private Field fldNetvac;
	
	List<DBRowTO> listMainframeWelcomeScr=SQLDriver.getEleObjData("FR_MF_MainframeWelcomeScr");*/
	
	/**using constructor to initialize screen and fields
	 * @param tewindow
	 */
	/*public FR_MF_MainframeWelcomeScr() {
		try {
			LeanftAction.sync(FR_MF_MainframeWindow.getTeWindow(),3);
			/*screen should be initialized first and then the fields*/
			/*screen=FR_MF_MainframeWindow.getTeWindow().describe(Screen.class, new ScreenDescription.Builder().id(Integer.parseInt(Action.getValue("idScreen", listMainframeWelcomeScr))).build());
			fldNetvac=screen.describe(Field.class,
					new FieldDescription.Builder().id(Integer.parseInt(Action.getValue("idNetvac",listMainframeWelcomeScr))).build());
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}
	}*/
	/*--------------------------------Methods-------------------------------------------------*/
	/**navigate to terminal screen
	 * @author BAthriYO
	 * @param text
	 */
	/*public FR_MF_MainframeLoginScr navigateToTerminalLoginScr(String text) {
		PropertyFileUtils property= new PropertyFileUtils(System.getProperty("user.dir")+"/src/test/resources/mf/launcher/mainframe.properties");
		property.setProperty("terminalLoginCmd", text);
		try {
			LeanftAction.setText(fldNetvac, text);
			LeanftAction.sendTEKeys(screen, Keys.ENTER);
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}
		return new FR_MF_MainframeLoginScr();
	}
	public boolean isWelcomeScrExists() {
		return LeanftAction.isExists(screen,2);
	}*/
}
