package qa.framework.webui.browsers;

import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import qa.framework.utils.ExceptionHandler;
import qa.framework.utils.FileManager;
import qa.framework.utils.GlobalVariables;
import qa.framework.utils.LoggerHelper;
import qa.framework.utils.SystemProcess;

/**
 * @author 10650956
 *
 */
public class WebDriverManager {
	private static String browser = null;
	private static String driverVerison = null;
	private static String browserVersion = null;
	private static String platform = null;
	private static boolean remote = false;
	private static String huburl = null;

	private static boolean flag = false;

	public static DesiredCapabilities capabilities;
	//Initializing the webdriver object as a thread local for parallel execution
	private static ThreadLocal<WebDriver> TDriver = new ThreadLocal<WebDriver>();
	private static Logger log = LoggerHelper.getLogger(WebDriverManager.class);

	/* configuring browser */
	private static synchronized void configBrowser() {

		browser = System.getProperty("browser", GlobalVariables.configProp.getProperty("browser"));
	}

	/* configuring remote */
	private static synchronized void configRemote() {

		remote = Boolean.parseBoolean(System.getProperty("remote", GlobalVariables.configProp.getProperty("remote")));
	}

	/* configuring browser version */
	private static synchronized void configDriverVersion() {

		driverVerison = System.getProperty("driverVersion", GlobalVariables.configProp.getProperty("driverVersion"));

	}

	/* configuring browser version */
	private static synchronized void configBrowserVersion() {
		/* these settings is only configured for remote execution */
		if (remote == true) {

			browserVersion = System.getProperty("browserVersion",
					GlobalVariables.configProp.getProperty("browserVersion"));
		}
	}

	/* configuring platform */
	private static synchronized void configPlatform() {
		/* these settings is only configured for remote execution */
		if (remote == true) {

			platform = System.getProperty("platform", GlobalVariables.configProp.getProperty("platform"));
		}
	}

	/* configuring Hub url */
	private static synchronized void configHubUrl() {
		/* these settings is only configured for remote execution */
		if (remote == true) {

			huburl = System.getProperty("huburl", GlobalVariables.configProp.getProperty("huburl"));
		}
	}

	private static synchronized void setPlatfromCapability() {
		switch (platform) {
		case "WINDOWS":
			capabilities.setPlatform(Platform.WINDOWS);
			break;
		case "WIN10":
			capabilities.setPlatform(Platform.WIN10);
			break;
		case "ANY":
			capabilities.setPlatform(Platform.ANY);
			break;
		default:
			log.debug("!!!INCORRECT PLATFORM PROVIDED!!!" + platform);
		}
	}

	/**
	 * setting remote webdriver
	 * 
	 * @author BathriYO
	 * @param browserName
	 */
	private static synchronized void setRemoteWebDriver(String browserName) {
		switch (browserName) {
		case "incognitochrome":
		case "chrome": {
			ChromeOptions options = new ChromeOptions();

			// capabilities = DesiredCapabilities.chrome();
			capabilities.setVersion(browserVersion);
			setPlatfromCapability();
			capabilities.setCapability("profile.default_content_settings.popups", 0);
			capabilities.setCapability("download.default_directory", FileManager.downloadFolderFilePath());
			capabilities.setCapability("safebrowsing.enabled", true);
			capabilities.acceptInsecureCerts();

			if (WebDriverManager.getBrowserName().contains("incognito")) {
				options.addArguments("--incognito");
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			}
			break;
		}
		case "ie": {
			// capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setVersion(browserVersion);
			setPlatfromCapability();
			break;
		}
		case "edge": {
			// capabilities = DesiredCapabilities.edge();
			capabilities.setVersion(browserVersion);
			setPlatfromCapability();
			break;
		}
		case "firefox": {
			// capabilities = DesiredCapabilities.firefox();
			capabilities.setVersion(browserVersion);
			setPlatfromCapability();
			/* disabling download popup */
			capabilities.setCapability("browser.helperApps.neverAsk.saveToDisk",
					"image/jpeg,application/pdf,application/octet-stream,application/zip");
			/* user specified download folder config */
			capabilities.setCapability("browser.download,folderList", 2);
			/* download folder path */
			capabilities.setCapability("browser,download.dir", FileManager.downloadFolderFilePath());
			capabilities.setCapability("pdfjs.disabled", "true");
			capabilities.acceptInsecureCerts();
			break;
		}
		default: {
			Assert.fail("!!!incorrect browser name provided !!!" + browserName);
		}
		}
		try {
			TDriver.set(new RemoteWebDriver(new URL(huburl), capabilities));
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	/* setting driver */
	private static synchronized void setDriver(String browserName) {
		switch (browserName) {
		case "incognitochrome":
		case "chrome": {
			TDriver.set(new ChromeBrowser().getDriver());
			break;
		}
		case "ie": {
			TDriver.set(new IEBrowser().getDriver());
			break;
		}
		case "edge": {
			TDriver.set(new EdgeBrowser().getDriver());
			break;
		}
		case "firefox": {
			TDriver.set(new FirefoxBrowser().getDriver());
			break;
		}
		default: {
			Assert.fail("!!!incorrect browser name provided !!!" + browserName);
		}
		}
	}

	/* configuring driver */
	public static synchronized void configureDriver() {
		/* configuring browser */
		configBrowser();
		/* configuring remote value */
		configRemote();

		/* configuring chrome driver version */
		configDriverVersion();

		if (remote) {
			configBrowserVersion();
			configHubUrl();
			configPlatform();

		}
	}

	/* getting browser */
	public static synchronized String getBrowserName() {
		return browser;
	}

	public static String getDriverVerison() {
		return driverVerison;
	}

	public static String getBrowserVersion() {
		return browserVersion;
	}

	/* start driver */
	public static synchronized void startDriver() {
		if (remote == true) {
			setRemoteWebDriver(browser);
		} else if (remote == false) {
			setDriver(browser);
		}
	}

	/* get driver */
	public static synchronized WebDriver getDriver() {
		return TDriver.get();
	}

	/* set driver */
	public static synchronized void setDriver(WebDriver driver) {
		TDriver.set(driver);
	}

	/**
	 * close browser
	 * 
	 * @author BathriYO
	 */
	public static synchronized void closeDriver() {
		TDriver.get().close();
	}

	/**
	 * quit browser
	 * 
	 * @author BathriYO
	 */
	public static synchronized void quitDriver() {
		TDriver.get().quit();
	}

	/**
	 * kills driver processes
	 * 
	 * @author BathriYO
	 */
	public static void killDriverProcess() {
		try {
			switch (browser) {
			case "chrome":
				SystemProcess.killProcess("chromedriver.exe");
				break;
			case "firefox":
				SystemProcess.killProcess("geckodriver.exe");
				break;
			case "ie":
				SystemProcess.killProcess("IEDriverServer.exe");
				break;
			case "edge":
				SystemProcess.killProcess("Microsoft Web Driver.exe");
				break;
			}
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	public static boolean getWebUIFlag() {
		return flag;
	}

	public static void setWebUIFlag(boolean flag) {
		WebDriverManager.flag = flag;
	}

}