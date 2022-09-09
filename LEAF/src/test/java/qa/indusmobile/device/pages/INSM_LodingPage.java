package qa.indusmobile.device.pages;

import qa.framework.dbutils.SQLDriver;
import qa.framework.device.DeviceActions;

public class INSM_LodingPage {
	DeviceActions action = new DeviceActions( SQLDriver.getEleObjData("INSM_LodingPage"));
	
	public void waitForLodingIconToDisappear() {
		
	}
}
