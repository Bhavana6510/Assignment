package qa.framework.device;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import qa.framework.utils.FileManager;
import qa.framework.utils.GlobalVariables;

public class AndroidAppDriver {

	private AndroidDriver<MobileElement> driver;
	
	/**
	 * Setting Generic capabilities
	 * 
	 * @author 10650956
	 * @return
	 */
	private DesiredCapabilities getCapabilities() {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("platformName", DeviceDriverManager.getPlatformName());
		capabilities.setCapability("platformVersion", DeviceDriverManager.getPlatformVersion());
		capabilities.setCapability("automationName", DeviceDriverManager.getAutomationEngine());

		capabilities.setCapability("deviceName", DeviceDriverManager.getDeviceName());
		
		/* Do not stop app, do not clear app data, and do not uninstall apk. */
		capabilities.setCapability("noReset", true);
		
		return capabilities;

	}

	/**
	 * Starting app with app capabilities
	 * 
	 * @author 10650956
	 * @param apkFile
	 * @return
	 * @throws MalformedURLException
	 */
	public AndroidDriver<MobileElement> getDriver(String apkFile) throws MalformedURLException {

		String userDir = System.getProperty("user.dir");
		String apkFilePath = null;

		DesiredCapabilities capabilities = getCapabilities();
		/* Note: appium does not take relative path. */
		apkFilePath = FileManager.searchFile(userDir, apkFile);
		
		/*setting app capability*/
		capabilities.setCapability("app", apkFilePath);
		
		if (DeviceDriverManager.isAppiumServerCodeStarted() == true) {
			driver = new AndroidDriver<MobileElement>(new URL(
					"http://" + DeviceDriverManager.getHost() + ":" + DeviceDriverManager.getPort() + "/wd/hub"),
					capabilities);
		} else {
			driver = new AndroidDriver<MobileElement>(new URL(DeviceDriverManager.getServerUrl()), capabilities);
		}
		
		driver.manage().timeouts().implicitlyWait(GlobalVariables.waitTime, TimeUnit.SECONDS);

		return driver;

	}
	
	/**
	 * Starting app with app package and app activity
	 * 
	 * @author 10650956
	 * @param appPackage
	 * @param appActivity
	 * @return
	 * @throws MalformedURLException
	 */
	public AndroidDriver<MobileElement> getDriver(String appPackage, String appActivity) throws MalformedURLException {

		DesiredCapabilities capabilities = getCapabilities();
		capabilities.setCapability("appPackage", appPackage);
		capabilities.setCapability("appActivity", appActivity);
		
		if (DeviceDriverManager.isAppiumServerCodeStarted() == true) {
			driver = new AndroidDriver<MobileElement>(new URL(
					"http://" + DeviceDriverManager.getHost() + ":" + DeviceDriverManager.getPort() + "/wd/hub"),
					capabilities);
		} else {
			driver = new AndroidDriver<MobileElement>(new URL(DeviceDriverManager.getServerUrl()), capabilities);
		}
		
		driver.manage().timeouts().implicitlyWait(GlobalVariables.waitTime, TimeUnit.SECONDS);

		return driver;

	}

}
