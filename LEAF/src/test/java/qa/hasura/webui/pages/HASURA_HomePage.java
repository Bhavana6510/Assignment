package qa.hasura.webui.pages;

import qa.framework.dbutils.SQLDriver;
import qa.framework.utils.Action;

public class HASURA_HomePage {
	
	Action action = new Action( SQLDriver.getEleObjData("HASURA_HomePage"));
	
	
	public void clickOnAccountLink() {
		Action.click(action.getElement("loginButton"));
		
	}

}
