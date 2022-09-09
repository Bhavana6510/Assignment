package qa.framework.webui.browsers;

import org.openqa.selenium.WebDriver;
/**
 *This interface contains method for getting driver instance for different browsers with 
 *different requirements and implementations
 */
public interface BrowserInterface {
public WebDriver getDriver();
}
