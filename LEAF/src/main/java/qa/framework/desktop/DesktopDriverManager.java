package qa.framework.desktop;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.windows.WindowsDriver;
import qa.framework.utils.GlobalVariables;
import qa.framework.utils.LoggerHelper;

/**
 * 
 * @author 10650956
 *
 */
public class DesktopDriverManager {

	private static boolean desktop = false;
	private static String insPath = null;
	private static String host = null;
	private static String port = null;
	private static WindowsDriver<?> driver;

	private static boolean flag = false;

	private static Process process = null;

	private static Logger log = LoggerHelper.getLogger(DesktopActions.class);

	/* configuring switch 'desktop' setting */
	private static synchronized void configSwitchDesktop() {

		desktop = Boolean
				.parseBoolean(System.getProperty("desktop", GlobalVariables.configProp.getProperty("desktop")));
	}

	/* configuring WindAppDriver installed dir path setting */
	private static synchronized void configInstalledPath() {
			
		insPath = System.getProperty("winappdriver.installation.dir",GlobalVariables.configProp.getProperty("winappdriver.installation.dir"));
	}

	/* configuring WinAppDriver 'host' setting */
	private static synchronized void configHost() {
				
		host = System.getProperty("winappdriver.host",GlobalVariables.configProp.getProperty("winappdriver.host"));
	}

	/* configuring WinAppDriver 'port' setting */
	private static synchronized void configPort() {
		
		port = System.getProperty("winappdriver.port",GlobalVariables.configProp.getProperty("winappdriver.port"));
	}

	/**
	 * Configuring all the preconditions required to start WinAppDriver
	 * 
	 * @author 10650956
	 */
	public static void config() {
		/* 1st: configuring switch button for Desktop app */
		configSwitchDesktop();

		/* checking if switch button is enabled or not */
		if (desktop == true) {

			/* 2nd: configuring WinAppDriver installed dir path */
			configInstalledPath();

			/* 3rd: configuring host */
			configHost();

			/* 4th: configuring port */
			configPort();

			/* 5th: starting WinAppDriver */
			startDriver(host, port);

		}
	}

	/**
	 * Starts WinAppDriver server
	 * 
	 * @author 10650956
	 * @param host : restricted to localhost
	 * @param port : default i.e. 4723 or any open port number
	 */
	private static void startDriver(String host, String port) {
		List<String> cmds;

		cmds = Arrays.asList("cmd.exe", "/C", "start", "WinAppDriver.exe", port);

		ProcessBuilder builder = new ProcessBuilder(cmds);
		builder.directory(new File(insPath));

		try {
			process = builder.start();
		} catch (IOException e) {
			e.printStackTrace();

			log.info("!!! WinAppDriver failed to Start !!!");
			log.info(e);
			Assert.fail("!!! WinAppDriver failed to Start !!!");
		}
	}

	/**
	 * Stops WinAppDriver server
	 * 
	 * @author 10650956
	 */
	public static void stopDriver() {
		process.destroyForcibly();

		try {
			Runtime.getRuntime().exec("taskkill /F /IM WinAppDriver.exe");
		} catch (IOException e) {
			e.printStackTrace();
			log.info("!!! WinAppDriver failed to Stop !!!");
			log.error(e);
		}
	}

	/**
	 * Start any desktop application
	 * 
	 * @author 10650956
	 * @param exePath
	 * @return
	 */
	public static void startApplication(String exePath) {

		if (desktop) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("app", exePath);
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("deviceName", "WindowsPC");

			try {
				driver = new WindowsDriver<WebElement>(new URL("http://" + host + ":" + port), capabilities);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

				flag = true;
			} catch (MalformedURLException e) {
				log.info("!!! Application failed to Start !!!" + exePath);
				log.error(e);
				e.printStackTrace();

				flag = false;
			}
		} else {
			log.error("!!! Make sure that 'desktop' property is set 'true' in config.property file ");
			Assert.fail("!!! Make sure that 'desktop' property is set 'true' in config.property file ");

			flag = false;
		}

	}

	public static void stopApplication() {
		try {
			flag = false;
			driver.closeApp();
			log.info("Desktop Application closed.");

		} catch (NoSuchWindowException e) {
			log.info("!!! Window seem to be closed already !!!");
		}
	}

	public static WindowsDriver<?> getDriver() {
		return driver;
	}

	public static boolean getAppFlag() {
		return flag;
	}

	public static void setAppFlag(boolean flag) {
		DesktopDriverManager.flag = flag;
	}

	public static boolean getDesktopSwitch() {
		return desktop;
	}
}
