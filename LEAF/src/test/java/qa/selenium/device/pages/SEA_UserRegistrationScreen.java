package qa.selenium.device.pages;

import java.util.List;

import org.openqa.selenium.WebElement;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import qa.framework.dbutils.SQLDriver;
import qa.framework.device.DeviceActions;
import qa.framework.utils.Action;

public class SEA_UserRegistrationScreen {

	
	DeviceActions action = new DeviceActions( SQLDriver.getEleObjData("SEA_UserRegistrationScreen"));
	
	public void enterUserName(String value) {
		DeviceActions.sendKeys((MobileElement)action.getElement("edtUsername"), value);
	}
	
	public void enterEmail(String value) {
		DeviceActions.sendKeys((MobileElement)action.getElement("edtEmail"), value);
	}
	
	public void enterPassword(String value) {
		DeviceActions.sendKeys((MobileElement)action.getElement("edtPassword"), value);
	}
	
	public void enterName(String value) {
		
		WebElement eleName = (WebElement)action.getElement("edtName");
		
		DeviceActions.clear(eleName);
		Action.pause(500);
		DeviceActions.sendKeys(eleName, value);
	}
	
	public void selectLanguage(String value) {
		
		DeviceActions.pressKey(AndroidKey.BACK);
		
		DeviceActions.click((MobileElement)action.getElement("btnLanguage"));
		List<Object> programmingLanguages = action.getElements("lstLanguages");
		
		for (Object option : programmingLanguages) {

			String des = ((MobileElement)option).getText();

			if (des.equalsIgnoreCase(value)) {
				DeviceActions.click((MobileElement)option);
				break;
			}
		}
		
	}
	
	public void selectIAcceptAdds() {
		DeviceActions.click((MobileElement)action.getElement("chkIAcceptAdds"));
	}
	
	public void clickRegisterButton() {
		DeviceActions.click((MobileElement)action.getElement("btnRegister"));
	}
	
	public String getName() {
		return DeviceActions.getText((MobileElement)action.getElement("lblName"));
	}

	
}
