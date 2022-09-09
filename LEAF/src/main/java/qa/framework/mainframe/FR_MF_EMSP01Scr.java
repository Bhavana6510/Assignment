package qa.framework.mainframe;

public class FR_MF_EMSP01Scr {
	/*
private Screen screen;
private Field commandField;
List<DBRowTO> listEleEMSPScr=SQLDriver.getEleObjData("FR_MF_EMSP01Scr");
/**
 * Building terminal homescreen
 * @param tewindow
 */
/*public class FR_MF_EMSP01Scr() {
	try {
		screen=FR_MF_MainframeWindow.getTeWindow().
				describe(Screen.class, new ScreenDescription.Builder().label(Action.getValue("lblScreen",listEleEMSPScr)).build());
		commandField=screen.describe(Field.class,
				new FieldDescription.Builder().attachedText(Action.getValue("atcTxtCommand",listEleEMSPScr))).id(Integer.parseInt("idCommand",listEleEMSPScr))).build());
		
	}catch(Exception e) {
		ExceptionHandler.handleException(e);
	}
}
/*--------------------------------Methods-------------------------------------------------*/
/*public boolean isEMSP01ScrExists(int timeInSeconds) {
	try {
		return LeanftAction.isExists(screen,timeInSeconds);
	}catch(Exception e) {
		ExceptionHandler.handleException(e);
	}
	return false;
}
/**selecting application 
 * @param option
 * @return QCICSBTLoginScr
 */
/*public FR_MF_ApplicationLoginScr selectApplication(String option,String enumKeys) {
	try {
		/*waiting for terminal homescreen*/
		/*LeanftAction.sync(screen);
		/*selecting option*/
		/*LeanftAction.setText(commandField,option);
		LeanftAction.sendTeKeys(screen,enumKeys);
	}catch(Exception e) {
		ExceptionHandler.handleException(e);

	}
	return FR_MF_ApplicationLoginScr();
	
}*/
/**
 * Returing log off terminal
 * @return CO_LEG_MRG_TeLoginScr
 */
/*public FR_MF_MainframeLoginScr logoff() {
	try {
		/*waiting for terminal homescreen*/
		/*LeanftAction.sync(screen);
		LeanftAction.setText(commandField,"logoff");
		LeanftAction.sendTeKeys(screen,Keys.ENTER);
	}catch(Exception e) {
		ExceptionHandler.handleException(e);

	}
	return FR_MF_ApplicationLoginScr();
}*/

}
