package qa.framework.webui.element;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.paulhammant.ngwebdriver.ByAngular;

import io.appium.java_client.MobileBy;
import qa.framework.desktop.DesktopDriverManager;
import qa.framework.device.DeviceDriverManager;
import qa.framework.utils.LoggerHelper;
import qa.framework.webui.browsers.WebDriverManager;

public class Element {
	WebDriver driver;
	Logger log = LoggerHelper.getLogger(Element.class);

	public Element(WebDriver driver) {
		this.driver = WebDriverManager.getDriver();
	}

	/**
	 * get element(driver.findElement())
	 * 
	 * @param valueType
	 * @param value
	 * @return webelement
	 */
	public synchronized WebElement getElement(String valueType, String value) {
		switch (valueType.toLowerCase()) {
		case "id":
			return driver.findElement(By.id(value));
		case "name":
			return driver.findElement(By.name(value));
		case "classname":
			return driver.findElement(By.className(value));
		case "linktext":
			return driver.findElement(By.linkText(value));
		case "partiallinktext":
			return driver.findElement(By.partialLinkText(value));
		case "xpath":
			return driver.findElement(By.xpath(value));
		case "tagname":
			return driver.findElement(By.tagName(value));
		case "cssselector":
			return driver.findElement(By.cssSelector(value));
		case "ng-model":
			return driver.findElement(ByAngular.model(value));
		case "ng-bind":
			return driver.findElement(ByAngular.binding(value));
		case "exactbinding":
			return driver.findElement(ByAngular.exactBinding(value));
		case "buttontext":
			return driver.findElement(ByAngular.buttonText(value));
		case "partialbuttontext":
			return driver.findElement(ByAngular.partialButtonText(value));
		case "js":
			return (WebElement) ((JavascriptExecutor) driver).executeScript(value);

		case "desktop-runtimeid":
			return DesktopDriverManager.getDriver().findElementById(value);

		case "desktop-classname":
			return DesktopDriverManager.getDriver().findElementByClassName(value);

		case "desktop-automationid":
			return DesktopDriverManager.getDriver().findElementByAccessibilityId(value);

		case "desktop-name":
			return DesktopDriverManager.getDriver().findElementByName(value);

		case "desktop-localizedcontroltype":
			return DesktopDriverManager.getDriver().findElementByTagName(value);

		case "desktop-xpath":
			return DesktopDriverManager.getDriver().findElementByXPath(value);

		default:
			log.debug("Select by :" + valueType + "is incorrect!");
			return null;
		}

	}

	/**
	 * get element(driver.findElement())
	 * 
	 * @author SinhaRi
	 * @param parentElement
	 * @param valueType
	 * @param value
	 * @return webelement
	 */
	public synchronized WebElement getElement(WebElement parentElement, String valueType, String value) {
		switch (valueType.toLowerCase()) {
		case "id":
			return parentElement.findElement(By.id(value));
		case "name":
			return parentElement.findElement(By.name(value));
		case "classname":
			return parentElement.findElement(By.className(value));
		case "linktext":
			return parentElement.findElement(By.linkText(value));
		case "partiallinktext":
			return parentElement.findElement(By.partialLinkText(value));
		case "xpath":
			return parentElement.findElement(By.xpath(value));
		case "tagname":
			return parentElement.findElement(By.tagName(value));
		case "cssselector":
			return parentElement.findElement(By.cssSelector(value));
		case "ng-model":
			return parentElement.findElement(ByAngular.model(value));
		case "ng-bind":
			return parentElement.findElement(ByAngular.binding(value));
		case "exactbinding":
			return parentElement.findElement(ByAngular.exactBinding(value));
		case "buttontext":
			return parentElement.findElement(ByAngular.buttonText(value));
		case "partialbuttontext":
			return parentElement.findElement(ByAngular.partialButtonText(value));
		default:
			log.debug("Select by :" + valueType + "is incorrect!");
			return null;
		}
	}

	/**
	 * get list of elements(driver.findElements())
	 * 
	 * @param valueType
	 * @param value
	 * @return webelement
	 */
	@SuppressWarnings("unchecked")
	public synchronized List<WebElement> getElements(String valueType, String value) {
		switch (valueType.toLowerCase()) {
		case "id":
			return driver.findElements(By.id(value));
		case "name":
			return driver.findElements(By.name(value));
		case "classname":
			return driver.findElements(By.className(value));
		case "linktext":
			return driver.findElements(By.linkText(value));
		case "partiallinktext":
			return driver.findElements(By.partialLinkText(value));
		case "xpath":
			return driver.findElements(By.xpath(value));
		case "tagname":
			return driver.findElements(By.tagName(value));
		case "cssselector":
			return driver.findElements(By.cssSelector(value));
		case "ng-model":
			return driver.findElements(ByAngular.model(value));
		case "ng-bind":
			return driver.findElements(ByAngular.binding(value));
		case "exactbinding":
			return driver.findElements(ByAngular.exactBinding(value));
		case "buttontext":
			return driver.findElements(ByAngular.buttonText(value));
		case "partialbuttontext":
			return driver.findElements(ByAngular.partialButtonText(value));
		case "repeater":
			return driver.findElements(ByAngular.repeater(value));
		case "js":
			return (List<WebElement>) ((JavascriptExecutor) driver).executeScript(value);

		case "desktop-runtimeid":
			return (List<WebElement>) DesktopDriverManager.getDriver().findElementsById(value);

		case "desktop-classname":
			return (List<WebElement>) DesktopDriverManager.getDriver().findElementsByClassName(value);

		case "desktop-automationId":
			return (List<WebElement>) DesktopDriverManager.getDriver().findElementsByAccessibilityId(value);

		case "desktop-name":
			return (List<WebElement>) DesktopDriverManager.getDriver().findElementsByName(value);

		case "desktop-localizedControlType":
			return (List<WebElement>) DesktopDriverManager.getDriver().findElementsByTagName(value);

		case "desktop-xpath":
			return (List<WebElement>) DesktopDriverManager.getDriver().findElementsByXPath(value);

		default:
			log.debug("Select by :" + valueType + "is incorrect!");
			return null;
		}
	}

	/**
	 * get list of elements(parentelement.findElements())
	 * 
	 * @param parentElement
	 * @param valueType
	 * @param value
	 * @return webelement
	 */
	public synchronized List<WebElement> getElements(WebElement parentElement, String valueType, String value) {
		switch (valueType.toLowerCase()) {
		case "id":
			return parentElement.findElements(By.id(value));
		case "name":
			return parentElement.findElements(By.name(value));
		case "classname":
			return parentElement.findElements(By.className(value));
		case "linktext":
			return parentElement.findElements(By.linkText(value));
		case "partiallinktext":
			return parentElement.findElements(By.partialLinkText(value));
		case "xapth":
			return parentElement.findElements(By.xpath(value));
		case "tagname":
			return parentElement.findElements(By.tagName(value));
		case "cssselector":
			return parentElement.findElements(By.cssSelector(value));
		case "ng-model":
			return parentElement.findElements(ByAngular.model(value));
		case "ng-bind":
			return parentElement.findElements(ByAngular.binding(value));
		case "exactbinding":
			return parentElement.findElements(ByAngular.exactBinding(value));
		case "buttontext":
			return parentElement.findElements(ByAngular.buttonText(value));
		case "partialbuttontext":
			return parentElement.findElements(ByAngular.partialButtonText(value));
		case "repeater":
			return parentElement.findElements(ByAngular.repeater(value));
		default:
			log.debug("Select by :" + valueType + "is incorrect!");
			return null;

		}
	}

	/********************* Device Element **************************/
	/**
	 * Get Mobile device element
	 * 
	 * Why return type is Object Because for browser, the return type will be
	 * WebElement And for app the return type will be MobileElement. Use need to
	 * down caste as needed
	 * 
	 * @author 10650956
	 * @param valueType
	 * @param value
	 * @return
	 */
	public Object getDeviceElement(String valueType, String value) {
		switch (valueType.toLowerCase()) {

		/* resource-id */
		case "device-id":
			return DeviceDriverManager.getDriver().findElement(MobileBy.id(value));

		case "device-name":
			return DeviceDriverManager.getDriver().findElement(MobileBy.name(value));

		case "device-classname":
			return DeviceDriverManager.getDriver().findElement(MobileBy.className(value));

		case "device-xpath":
			return DeviceDriverManager.getDriver().findElement(MobileBy.xpath(value));

		/* for android : content-desc */
		case "device-accessibilityid":
			return DeviceDriverManager.getDriver().findElement(MobileBy.AccessibilityId(value));

		/* most of the time class name is the tag name */
		case "device-tagname":
			return DeviceDriverManager.getDriver().findElement(MobileBy.tagName(value));
		case "device-androiduiautomator":
			// eg: new UiSelector().text("BHIM UPI").className("android.widget.Button")
			//#ref: https://www.browserstack.com/guide/locators-in-appium
			return DeviceDriverManager.getDriver().findElement(MobileBy.AndroidUIAutomator(value));

		default:
			log.debug("Select by :" + valueType + "is incorrect!");
			return null;
		}

	}

	/**
	 * Get List of Mobile device elements
	 * 
	 * Why List<Object>? Because for browser, the return type will be WebElement And
	 * for app the return type will be MobileElement. Use need to down caste as
	 * needed
	 * 
	 * @author 10650956
	 * @param valueType
	 * @param value
	 * @return
	 */
	public List<Object> getDeviceElements(String valueType, String value) {

		switch (valueType.toLowerCase()) {
		/* resource-id */
		case "device-id":
			return DeviceDriverManager.getDriver().findElements(MobileBy.id(value)).stream()
					.collect(Collectors.toList());

		case "device-name":
			return DeviceDriverManager.getDriver().findElements(MobileBy.name(value)).stream()
					.collect(Collectors.toList());

		case "device-classname":
			return DeviceDriverManager.getDriver().findElements(MobileBy.className(value)).stream()
					.collect(Collectors.toList());

		case "device-xpath":
			return DeviceDriverManager.getDriver().findElements(MobileBy.xpath(value)).stream()
					.collect(Collectors.toList());

		/* for android : content-desc */
		case "device-accessibilityid":
			return DeviceDriverManager.getDriver().findElements(MobileBy.AccessibilityId(value)).stream()
					.collect(Collectors.toList());

		/* most of the time class name is the tag name */
		case "device-tagname":

			return DeviceDriverManager.getDriver().findElements(MobileBy.tagName(value)).stream()
					.collect(Collectors.toList());

		default:
			log.debug("Select by :" + valueType + "is incorrect!");
			return null;
		}
	}
}
