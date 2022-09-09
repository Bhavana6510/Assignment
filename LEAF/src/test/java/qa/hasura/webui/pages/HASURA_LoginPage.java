package qa.hasura.webui.pages;

import qa.framework.dbutils.SQLDriver;
import qa.framework.utils.Action;

public class HASURA_LoginPage {

	Action action = new Action( SQLDriver.getEleObjData("HASURA_LoginPage"));
	
	public void enterUserId(String userId) {
		Action.sendKeys(action.getElement("txtUserId"), Action.getTestData("userid"));
	}
	
	public void enterPassword(String password) {
		Action.sendKeys(action.getElement("txtPwd"), Action.getTestData("password"));
	}
	
	public void clickSubmit() {
		Action.click(action.getElement("btnSubmit"));
	}
	
	public String getTokenId() {
		return Action.getAttribute(action.getElement("tokenId"),"value");
	}
}
