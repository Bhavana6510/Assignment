package qa.framework.device;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import qa.framework.utils.GlobalVariables;

public class ChromeBrowserDevice {

	private AndroidDriver<WebElement> driver;

	public AndroidDriver<WebElement> getDriver() throws MalformedURLException {

		String userDir = System.getProperty("user.dir");

		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("platformName", DeviceDriverManager.getPlatformName());
		capabilities.setCapability("platformVersion", DeviceDriverManager.getPlatformVersion());
		capabilities.setCapability("automationName", DeviceDriverManager.getAutomationEngine());
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceDriverManager.getDeviceName());

		capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
		capabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
		capabilities.setCapability("chromedriverExecutable", userDir + "\\src\\test\\resources\\drivers\\chromedriver_"
				+ DeviceDriverManager.getBrowserVersion() + ".exe");
		if (DeviceDriverManager.isAppiumServerCodeStarted() == true) {
			driver = new AndroidDriver<WebElement>(new URL(
					"http://" + DeviceDriverManager.getHost() + ":" + DeviceDriverManager.getPort() + "/wd/hub"),
					capabilities);
		} else {
			
			driver = new AndroidDriver<WebElement>(new URL(DeviceDriverManager.getServerUrl()), capabilities);
		}

		driver.manage().timeouts().implicitlyWait(GlobalVariables.waitTime, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(GlobalVariables.waitTime, TimeUnit.SECONDS);

		return driver;
	}
}
