package qa.indusmobile.device.pages;

import io.appium.java_client.MobileElement;
import qa.framework.dbutils.SQLDriver;
import qa.framework.device.DeviceActions;

public class INSM_HomePage {
	
	DeviceActions action = new DeviceActions( SQLDriver.getEleObjData("INSM_HomePage"));
	
	public void clickBhimUPI() {
		
		DeviceActions.click((MobileElement)action.getElement("bhimUPIBtn"));
	}
	
	public boolean isBhimUPIPresent() {
		return DeviceActions.isPresent((MobileElement)action.getElement("bhimUPIBtn"));
	}
}
