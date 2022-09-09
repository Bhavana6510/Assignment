package qa.framework.webui.browsers;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import qa.framework.utils.GlobalVariables;

public class EdgeBrowser implements BrowserInterface {
	private WebDriver driver;

	@Override
	public WebDriver getDriver() {
		System.setProperty("webdriver.edge.driver",
				System.getProperty("userDir") + "/src/test/resources/drivers/.exe");
		this.driver = new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(GlobalVariables.waitTime, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		return driver;

	}

}
