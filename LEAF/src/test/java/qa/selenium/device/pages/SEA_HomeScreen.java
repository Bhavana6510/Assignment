package qa.selenium.device.pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import qa.framework.dbutils.SQLDriver;
import qa.framework.device.DeviceActions;
import qa.framework.utils.Action;

public class SEA_HomeScreen {

	DeviceActions action = new DeviceActions(SQLDriver.getEleObjData("SEA_HomeScreen"));

	public void clickRegisterUserFolderIcon() {

		DeviceActions.click((MobileElement) action.getElement("btnFolder"));

	}

	public void enterText(String value) {

		DeviceActions.sendKeys((MobileElement) action.getElement("edtTextbox"), value);

	}

	public void enableProgressBar() {
		DeviceActions.click((MobileElement) action.getElement("btnProgressbar"));

		String percent = "0";
		try {
			while (!percent.contains("75")) {
				percent = DeviceActions.getText((MobileElement) action.getElement("txtProgressCompletion"));
				
			}

		} catch (Exception e) {
			//ignore
		}

		Action.pause(2000);
		DeviceActions.pressKey(AndroidKey.BACK);
		Action.pause(500);
		DeviceActions.pressKey(AndroidKey.BACK);
		Action.pause(100);

	}

	public void enableDispalyText() {

		DeviceActions.click((MobileElement) action.getElement("btnDisplayText"));
	}

	public String getDispalyText() {

		return DeviceActions.getText((MobileElement) action.getElement("txtDisplayText"));
	}

	public void openPopup() {

		DeviceActions.click((MobileElement) action.getElement("btnWindowPopup"));
	}

	public void closePopup() {

		DeviceActions.click((MobileElement) action.getElement("btnWinPopupDismiss"));
	}
}
