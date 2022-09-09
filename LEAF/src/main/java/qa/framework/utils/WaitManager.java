package qa.framework.utils;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import qa.framework.dbutils.DBRowTO;
import qa.framework.report.Report;
import qa.framework.webui.browsers.WebDriverManager;

public class WaitManager {
	private static ThreadLocal<String> TValue = new ThreadLocal<String>();
	private static ThreadLocal<String> TValueType = new ThreadLocal<String>();

	static Logger log = LoggerHelper.getLogger(WaitManager.class);

	/**
	 * Get by based on dbKey
	 * 
	 * @author BathriYO
	 * @param dbKey
	 * @return by
	 */
	public static synchronized By getBy(String dbKey, List<DBRowTO> listElement) {
		String value = Action.getValue(dbKey, listElement);
		String valueType = Action.getValueType(dbKey, listElement);

		getTValue().set(value);
		getTValueType().set(valueType);

		switch (valueType.toLowerCase()) {
		case "id":
			return By.id(value);
		case "name":
			return By.name(value);
		case "classname":
			return By.className(value);
		case "linktext":
			return By.linkText(value);
		case "partiallinktext":
			return By.partialLinkText(value);
		case "xpath":
			return By.xpath(value);
		case "tagname":
			return By.tagName(value);
		case "cssselector":
			return By.cssSelector(value);
		default:
			log.debug("!!! Select by:" + valueType + "is incorrect");
			return null;
		}
	}

	/**
	 * wait for element to be present using By
	 * 
	 * @author BathriYO
	 * @param dbKey
	 * @return webElement
	 */
	public static synchronized WebElement presenceOfElementWaitUsingBy(String dbKey, List<DBRowTO> listElement) {
		String status = "FAIL";
		WebElement element = null;
		try {
			element = new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime)
					.until(ExpectedConditions.presenceOfElementLocated(getBy(dbKey, listElement)));
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("wait for presence of element");
			Report.printKey(dbKey);
			Report.printValueType(getTValueType().get());
			Report.printValue(getTValue().get());
			Report.printStatus(status);
		}
		return element;
	}

	/**
	 * wait for child element to be presence under parent element using by
	 * 
	 * @author BathriYO
	 * @param parentelement
	 * @param dbkey
	 * @return webElement
	 */
	public static synchronized WebElement presenceOfElementWaitUsingBy(WebElement parentElement, String dbKey,
			List<DBRowTO> listElement) {
		String status = "FAIL";
		WebElement element = null;
		try {
			element = new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime).until(
					ExpectedConditions.presenceOfNestedElementLocatedBy(parentElement, getBy(dbKey, listElement)));
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("wait for presence of element");
			Report.printKey(dbKey);
			Report.printValueType(getTValueType().get());
			Report.printValue(getTValue().get());
			Report.printStatus(status);
		}
		return element;
	}

	/**
	 * wait for all elements to be present
	 * 
	 * @author BathriYO
	 * @param dbkey
	 * @return List<webElement>
	 */
	public static synchronized List<WebElement> presenceOfElementsWaitUsingBy(String dbKey,
			List<DBRowTO> listDBElement) {
		String status = "FAIL";
		List<WebElement> listElement = null;
		try {
			listElement = new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime)
					.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getBy(dbKey, listDBElement)));
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("wait for presence of elements");
			Report.printKey(dbKey);
			Report.printValueType(getTValueType().get());
			Report.printValue(getTValue().get());
			Report.printStatus(status);
		}
		return listElement;
	}

	/**
	 * wait for element to be visible using By
	 * 
	 * @author BathriYO
	 * @param dbkey
	 * @return WebElement
	 */
	public synchronized static WebElement visibilityOfElementWaitUsingBy(String dbKey, List<DBRowTO> listDBElement) {
		String status = "FAIL";
		WebElement element = null;
		try {
			element = new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime)
					.until(ExpectedConditions.visibilityOfElementLocated(getBy(dbKey, listDBElement)));
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("wait visibility of elements");
			Report.printKey(dbKey);
			Report.printValueType(getTValueType().get());
			Report.printValue(getTValue().get());
			Report.printStatus(status);
		}
		return element;
	}

	/**
	 * wait for element to be visible using By under parent element
	 * 
	 * @author BathriYO
	 * @param parentelement
	 * @param dbkey
	 * @return List<webElement>
	 */
	public static synchronized List<WebElement> visibilityOfElementWaitUsingBy(WebElement parentElement, String dbKey,
			List<DBRowTO> listElement) {
		String status = "FAIL";
		List<WebElement> elist = null;
		try {
			elist = new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime).until(
					ExpectedConditions.visibilityOfNestedElementsLocatedBy(parentElement, getBy(dbKey, listElement)));
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("wait visibility of elements");
			Report.printKey(dbKey);
			Report.printValueType(getTValueType().get());
			Report.printValue(getTValue().get());
			Report.printStatus(status);
		}
		return elist;
	}

	/**
	 * Get list of elements using By
	 * 
	 * @author BathriYO
	 * @param element
	 * @param dbkey
	 * @return List<webElement>
	 */
	public static synchronized List<WebElement> visibilityOfElementsWaitUsingBy(String dbKey,
			List<DBRowTO> listDBElement) {
		String status = "FAIL";
		List<WebElement> listElement = null;
		try {
			listElement = new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime)
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getBy(dbKey, listDBElement)));
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("wait visibility of elements");
			Report.printKey(dbKey);
			Report.printValueType(getTValueType().get());
			Report.printValue(getTValue().get());
			Report.printStatus(status);
		}
		return listElement;
	}

	/**
	 * wait for element to be invisible
	 * 
	 * @author BathriYO
	 * @param dbkey
	 * @param listElement
	 * @return boolean
	 */
	public static synchronized boolean elementToBeInvisible(String dbKey, List<DBRowTO> listElement) {
		boolean isInVisible = false;
		String status = "FAIL";
		try {
			isInVisible = new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime)
					.until(ExpectedConditions.invisibilityOfElementLocated(getBy(dbKey, listElement)));
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("waitfor element to be invisible");
			Report.printKey(dbKey);
			Report.printValueType(getTValueType().get());
			Report.printValue(getTValue().get());
			Report.printStatus(status);
		}
		return isInVisible;
	}

	/**
	 * wait for element to be invisible
	 * 
	 * @author BathriYO
	 * @param element
	 * @return boolean
	 */
	public static synchronized boolean elementToBeInvisible(WebElement element) {
		boolean isInVisible = false;
		String status = "FAIL";
		try {
			isInVisible = new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime)
					.until(ExpectedConditions.invisibilityOf(element));
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("waitfor element to be invisible");
			Report.printStatus(status);
		}
		return isInVisible;
	}

	/**
	 * wait for alert to be present
	 * 
	 * @author BathriYO
	 */
	public synchronized static void alertIsPresent() {
		String status = "FAIL";
		try {
			new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime)
					.until(ExpectedConditions.alertIsPresent());
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("wait for alert to be present");
			Report.printStatus(status);
		}
	}

	/**
	 * wait for element to be clickable
	 * 
	 * @param dbkey
	 * @return WebElement
	 */
	public synchronized static WebElement elementToBeClickable(String dbKey, List<DBRowTO> listElement) {
		String status = "FAIL";
		WebElement element = null;
		try {
			element = new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime)
					.until(ExpectedConditions.elementToBeClickable(getBy(dbKey, listElement)));
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("wait for element to be clickable");
			Report.printKey(dbKey);
			Report.printValueType(getTValueType().get());
			Report.printValue(getTValue().get());
			Report.printStatus(status);

		}
		return element;
	}

	/**
	 * wait for element to be clickable
	 * 
	 * @author BathriYO
	 * @param Webelement
	 */
	public synchronized static void elementToBeClickable(WebElement element) {
		String status = "FAIL";
		try {
			new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime)
					.until(ExpectedConditions.elementToBeClickable(element));
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("wait for element to be clickable");
			Report.printStatus(status);
		}
	}

	/**wait for element to be selectable
	 *  @author BathriYO
	 *  @param dbkey
	 *  @return boolean
	 */
	public synchronized static boolean elementToBeSelected(String dbKey, List<DBRowTO> listElement) {
		String status = "FAIL";
		boolean isSelected=false;
		try {
			isSelected=new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime)
					.until(ExpectedConditions.elementToBeSelected(getBy(dbKey, listElement)));
			status = "PASS";	
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}finally {
			Report.printOperation("wait for element to be selected");
			Report.printKey(dbKey);
			Report.printValueType(getTValueType().get());
			Report.printValue(getTValue().get());
			Report.printStatus(status);
		
		}
		return isSelected;
	}
	
	/**wait for element to be selectable
	 *  @author BathriYO
	 *  @param element
	 *  @return boolean
	 */
	public synchronized static boolean elementToBeSelected(WebElement element) {
		String status = "FAIL";
		boolean isSelected=false;
		try {
			isSelected=new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime)
			.until(ExpectedConditions.elementToBeSelected(element));
			status = "PASS";	
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}finally {
			Report.printOperation("wait for element to be selectable");
			Report.printStatus(status);
		}
		return isSelected;
	}
	/**wait till page refreshed
	 * @author  BathriYO
	 * @param element
	 * @return boolean 
	 */
	public synchronized static boolean waitTillPageRefresh(WebElement element) {
		String status = "FAIL";
		boolean isRefreshed=false;
		try {
			isRefreshed=new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime)
					.until(ExpectedConditions.stalenessOf(element));
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}finally {
			Report.printOperation("wait till page refresh");
			Report.printStatus(status);
		}
		return isRefreshed;
	}
	public static ThreadLocal<String> getTValueType(){
		return TValueType;
	}
	public static void setTValueType( ThreadLocal<String> tValueType) {
		 TValueType=tValueType;
	}
	public static ThreadLocal<String> getTValue(){
		return TValue;
	}
	public static void setTValue(ThreadLocal<String> tValue) {
		TValue=tValue;
	}
}