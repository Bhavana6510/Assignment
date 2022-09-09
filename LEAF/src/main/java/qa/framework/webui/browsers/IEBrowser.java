package qa.framework.webui.browsers;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import qa.framework.utils.GlobalVariables;

public class IEBrowser implements BrowserInterface {
	private WebDriver driver;

	@Override
	public WebDriver getDriver() {
		System.setProperty("webdriver.ie.driver",
				"./src/test/resources/drivers/IEDriverServer.exe");
		
		DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
		cap.setCapability("nativeEvents", false);
		cap.setCapability("unexpectedAlertBehaviour", "accept");
		cap.setCapability("ignoreProtectedModeSettings", true);
		cap.setCapability("disable-popup-blocking", true);
		cap.setCapability("enablePersistentHover", true);
		cap.setCapability("ignoreZoomSetting", true);
		cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		
		InternetExplorerOptions options = new InternetExplorerOptions();
	    //options.IntroduceInstabilityByIgnoringProtectedModeSettings = true;
	    //options.RequireWindowFocus = true;
	    
		
//		  options.ignoreZoomSettings();
//		  options.introduceFlakinessByIgnoringSecurityDomains();
//		  options.setCapability("ignoreProtectedModeSettings",true);
//		  options.requireWindowFocus(); options.setCapability("nativeEvents", false);
//		  options.setCapability("unexpectedAlertBehaviour", "accept");
//		  options.setCapability("disable-popup-blocking", true);
		 
	    
		options.merge(cap);
		
		
		this.driver = new InternetExplorerDriver(cap);
		
		driver.manage().timeouts().implicitlyWait(GlobalVariables.waitTime, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		return driver;
	}

}
