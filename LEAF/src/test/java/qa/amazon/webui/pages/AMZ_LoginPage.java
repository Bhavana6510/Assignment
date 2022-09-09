package qa.amazon.webui.pages;

import qa.framework.dbutils.SQLDriver;
import qa.framework.utils.Action;

public class AMZ_LoginPage {

	private final Action action = new Action( SQLDriver.getEleObjData("AMZ_LoginPage"));
	
	public void enterUserId(String userId) {
		Action.sendKeys(action.getElement("txtUserId"), Action.getTestData("userid"));
	}
	
	public void clickContiunu() {
		Action.click(action.getElement("btnContinue"));
	}
	
	public String getErrorMsg() {
		return Action.getText(action.getElement("hedErrorMsg"));
	}
	
}
