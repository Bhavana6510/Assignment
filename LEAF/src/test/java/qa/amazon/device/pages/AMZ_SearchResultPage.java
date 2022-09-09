package qa.amazon.device.pages;

import qa.framework.dbutils.SQLDriver;
import qa.framework.device.DeviceActions;

public class AMZ_SearchResultPage {

	DeviceActions action = new DeviceActions( SQLDriver.getEleObjData("AMZ_SearchResultPage"));
	
	public boolean isPresentResult() {
		return action.isPresent("lblResult");
	}
	
}
