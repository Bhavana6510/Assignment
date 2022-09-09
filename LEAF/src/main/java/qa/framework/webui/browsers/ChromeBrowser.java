package qa.framework.webui.browsers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import qa.framework.utils.FileManager;
import qa.framework.utils.GlobalVariables;

public class ChromeBrowser implements BrowserInterface {
	private WebDriver driver; //driver  object created

	@Override
	public synchronized WebDriver getDriver() {
		//String userDir = System.getProperty("user.dir"); //this gives you absolute path of the user directory
		System.setProperty("webdriver.chrome.driver","./src/test/resources/drivers/chromedriver_"+WebDriverManager.getDriverVerison()+".exe");

		Map<String, Object> preferences = new HashMap<String, Object>();

		/* do not show any pop up(windows pop up) while downloading any file */
		preferences.put("profile.default_content_settings.popups", 0);

		/* set default folder */
		preferences.put("download.default_directory", FileManager.downloadFolderFilePath());

		/* disable keep/discard warning messages form Chrome */
		preferences.put("safebrowsing.enabled", "true");

		/* handling certification issue */
		preferences.put("CapabilityType.ACCEPT_SSL_CERTS", "true");

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", preferences);

		if (WebDriverManager.getBrowserName().contains("incognito")) {
			options.addArguments("--incognito");
		}
		//--ignore-certificate-errors

		//options.addArguments("user-data-dir=C:\\Users\\10650956\\AppData\\Local\\Google\\Chrome\\User Data");
		
				
		this.driver = new ChromeDriver(options);
		
		
		driver.manage().timeouts().implicitlyWait(GlobalVariables.waitTime, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(GlobalVariables.waitTime,TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		
		return driver;

	}

}
