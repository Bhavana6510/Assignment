package qa.framework.device;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.ios.IOSDriver;
import qa.framework.utils.GlobalVariables;

public class SafariBrowserDevice {
	
	private IOSDriver<WebElement> driver;
	
	public IOSDriver<WebElement> getDriver() throws MalformedURLException {

		String userDir = System.getProperty("user.dir");

		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("platformName", DeviceDriverManager.getPlatformName());
		capabilities.setCapability("platformVersion", DeviceDriverManager.getPlatformVersion());
		capabilities.setCapability("automationName", DeviceDriverManager.getAutomationEngine());
		
		// In Progress
		
		driver = new IOSDriver<WebElement>(new URL("http://" + DeviceDriverManager.getHost() + ":" + DeviceDriverManager.getPort() + "/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(GlobalVariables.waitTime, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(GlobalVariables.waitTime, TimeUnit.SECONDS);
		
		
		return driver;
	}
}
