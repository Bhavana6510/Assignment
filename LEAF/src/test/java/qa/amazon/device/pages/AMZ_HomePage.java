package qa.amazon.device.pages;

import io.appium.java_client.MobileElement;
import qa.framework.dbutils.SQLDriver;
import qa.framework.device.DeviceActions;

public class AMZ_HomePage {

	DeviceActions action = new DeviceActions( SQLDriver.getEleObjData("AMZ_HomePage"));
	
	public void searchProject(String value) {
		
		DeviceActions.sendKeys((MobileElement)action.getElement("edtSearchBox"),value);
		DeviceActions.click((MobileElement)action.getElement("btnSearch"));
	
		
	}
}
