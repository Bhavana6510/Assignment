package qa.amazon.webui.pages;

import qa.framework.dbutils.SQLDriver;
import qa.framework.utils.Action;

public class AMZ_HomePage {
	
	private final Action action = new Action( SQLDriver.getEleObjData("AMZ_HomePage"));
	
	
	public void clicOnAccountLink() {
		
		Action.click(action.getElement("lnkAccount"));
		
	}

}
