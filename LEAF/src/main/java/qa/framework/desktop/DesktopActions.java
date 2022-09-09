package qa.framework.desktop;

import java.util.List;

import org.openqa.selenium.WebElement;

import qa.framework.dbutils.DBRowTO;
import qa.framework.report.Report;
import qa.framework.utils.Action;
import qa.framework.utils.ExceptionHandler;
import qa.framework.utils.Reporter;
import qa.framework.webui.element.Element;

/**
 * 
 * @author 10650956
 *
 */
public class DesktopActions {

	List<DBRowTO> lstElement;
	private static String srcCapPerAction = null;

	public DesktopActions(List<DBRowTO> lstElement) {
		this.lstElement = lstElement;
	}

	final private static void takeScrCapPerAction() {

		if (Action.srcCapPerAction == true) {
			Reporter.addDesktopScreenshot(null, null);
		}

	}
	
	/**
	 * Get WebElement
	 * 
	 * @author 10650956
	 * @param key
	 * @return
	 */
	final public WebElement getElement(String key) {
		WebElement webElement = null;
		String valueType = "undefined";
		String value = "undefined";

		String status = "FAIL";

		try {
			valueType = Action.getValueType(key, lstElement);
			value = Action.getValue(key, lstElement);

			Element element = new Element(null);
			webElement = element.getElement(valueType, value);
			status = "PASS";

		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get Element");
			Report.printKey(key);
			Report.printValue(value);
			Report.printValueType(valueType);
			Report.printStatus(status);
		}

		return webElement;

	}

	/**
	 * Get list of Elements
	 * 
	 * @author 10650956
	 * @param key
	 * @return
	 */
	final public List<WebElement> getElements(String key) {
		List<WebElement> lstWebElement = null;
		String valueType = "undefined";
		String value = "undefined";

		String status = "FAIL";

		try {
			valueType = Action.getValueType(key, lstElement);
			value = Action.getValue(key, lstElement);

			Element element = new Element(null);
			lstWebElement = element.getElements(valueType, value);
			status = "PASS";

		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get Element");
			Report.printKey(key);
			Report.printValue(value);
			Report.printValueType(valueType);
			Report.printStatus(status);
		}

		return lstWebElement;

	}

	/**
	 * Click
	 * 
	 * @author 10650956
	 * @param element
	 */
	final public static void click(WebElement element) {
		String status = "FAIL";

		try {
			element.click();
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Click");
			Report.printStatus(status);
		}
	}

	/**
	 * Clear
	 * 
	 * @author 10650956
	 * @param element
	 */
	final public static void clear(WebElement element) {
		String status = "FAIL";

		try {
			element.clear();
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Clear");
			Report.printStatus(status);
		}
	}

	/**
	 * Sendkeys
	 * 
	 * @author 10650956
	 * @param element
	 * @param value
	 */
	final public static void sendKeys(WebElement element, String value) {
		String status = "FAIL";

		try {
			element.sendKeys(value);
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Clear");
			Report.printValue(value);
			Report.printStatus(status);
		}
	}

	/**
	 * Get Text
	 * 
	 * @author 10650956
	 * @param element
	 * @return
	 */
	final public static String getText(WebElement element) {
		String value = null;
		String status = "FAIL";

		try {
			value = element.getText();
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get Text");
			Report.printStatus(status);
		}

		return value;
	}

	/**
	 * Get Attribute
	 * 
	 * @param element
	 * @param name
	 * @return
	 */
	final public static String getAttribute(WebElement element, String name) {
		String value = null;
		String status = "FAIL";

		try {
			value = element.getAttribute(name);
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get Text");
			Report.printStatus(status);
		}

		return value;
	}

	
}
