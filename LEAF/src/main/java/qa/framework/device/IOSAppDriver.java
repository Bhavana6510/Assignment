package qa.framework.device;

import java.net.MalformedURLException;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import qa.framework.utils.FileManager;

public class IOSAppDriver {
	
	private IOSDriver<MobileElement> driver;
	
	private DesiredCapabilities getCapabilities() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
				
		return capabilities;

	}

	public IOSDriver<MobileElement> getDriver(String apkFile) throws MalformedURLException {

		String userDir = System.getProperty("user.dir");
		String apkFilePath = null;

		DesiredCapabilities capabilities = getCapabilities();
		/* Note: appium does not take relative path. */
		apkFilePath = FileManager.searchFile(userDir, apkFile);

		
		// IN PROGRESS
		
		return driver;

	}
}
