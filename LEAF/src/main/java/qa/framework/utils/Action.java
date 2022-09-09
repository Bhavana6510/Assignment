package qa.framework.utils;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import qa.framework.dbutils.DBRowTO;
import qa.framework.dbutils.SQLDriver;
import qa.framework.report.Report;
import qa.framework.webui.browsers.WebDriverManager;
import qa.framework.webui.element.Element;

public class Action {

	List<DBRowTO> listElement;
	public static boolean srcCapPerAction = false;

//Assigning action class list element to argListElement
	public Action(List<DBRowTO> argListElement) {
		this.listElement = argListElement;
	}

	/**
	 * config screenshot per action
	 * 
	 * @author BathriYO
	 */
	public static synchronized void configScrCapPerAction() {

		srcCapPerAction = Boolean.parseBoolean(
				System.getProperty("srcCapPerAction", GlobalVariables.configProp.getProperty("scrCapPerAction")));
	}

	/**
	 * @author BathriYO
	 */
	private static void takeScrCapPerAction() {
		if (srcCapPerAction) {
			if (Reporter.TScenario.get() != null) {
				Reporter.addScreenCapture();
			}
		}
	}

	/**
	 * @author BathriYO performs click
	 * @param element
	 */
	public synchronized static final void click(WebElement element) {
		String status = "FAIL";
		try {
			new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime)
					.until(ExpectedConditions.elementToBeClickable(element));
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
	 * context click
	 * 
	 * @param element
	 */
	public synchronized static final void contextClick(WebElement element) {
		String status = "FAIL";
		try {
			new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime)
					.until(ExpectedConditions.elementToBeClickable(element));
			new Actions(WebDriverManager.getDriver()).contextClick(element).perform();
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Context Click");
			Report.printStatus(status);
		}
	}

	/**
	 * Double click
	 * 
	 * @param element
	 */
	public synchronized static final void doubleClick(WebElement element) {
		String status = "FAIL";
		try {
			new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime)
					.until(ExpectedConditions.elementToBeClickable(element));
			new Actions(WebDriverManager.getDriver()).doubleClick(element).perform();
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Double Click");
			Report.printStatus(status);
		}
	}

	/**
	 * clear
	 * 
	 * @param element
	 */
	public synchronized static final void clear(WebElement element) {
		String status = "FAIL";
		try {
			new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime)
					.until(ExpectedConditions.visibilityOf(element));
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
	 * SendKeys
	 * 
	 * @param element
	 * @param value
	 */
	public synchronized static final void sendKeys(WebElement element, String value) {
		String status = "FAIL";
		try {
			element.sendKeys(value);
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("sendKeys");
			Report.printValue(value);
			Report.printStatus(status);
		}
	}

	/**
	 * SendKeys
	 * 
	 * @param element
	 * @param value
	 */
	public synchronized static final void sendKeys(WebElement element, Keys key) {
		String status = "FAIL";
		try {
			element.sendKeys(key);
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("sendKeys");
			Report.printValue(key.toString());
			Report.printStatus(status);
		}
	}

	/**
	 * closeBrowser
	 */
	public synchronized static final void closeBrowser() {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().close();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Close Browser");
			Report.printStatus(status);
		}
	}

	/**
	 * Quit Browsers
	 */
	public synchronized static final void quitBrowser() {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().quit();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Quit Browser");
			Report.printStatus(status);
		}
	}

	/**
	 * Hard wait to pause the execution of thread.
	 * 
	 * @author 10650956
	 * @param timeInMillisec : long
	 */
	public static final void pause(long timeInMillisec) {

		try {
			Thread.sleep(timeInMillisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			Report.printOperation("PAUSED");
			Report.printStatus("PASS");
		}
	}

	/**
	 * isAlertPresent
	 * 
	 * @return boolean
	 */
	public synchronized static final boolean isAlertPresent() {
		boolean isAlertPresent = false;
		String status = "FAIL";
		try {
			ExpectedCondition<Alert> alertIsPresent = ExpectedConditions.alertIsPresent();

			if (alertIsPresent != null)
				isAlertPresent = true;
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("isAlertPresent");
			Report.printStatus(status);
		}
		return isAlertPresent;
	}

	/**
	 * switch to alert
	 * 
	 * @author BathriYo
	 * @return alert
	 */
	public synchronized static final Alert switchToAlert() {
		Alert alert = null;
		String status = "FAIL";
		try {
			alert = WebDriverManager.getDriver().switchTo().alert();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Switch to alert");
			Report.printStatus(status);
		}
		return alert;
	}

	/**
	 * Accept alert
	 * 
	 * @param alert
	 */
	public synchronized static final void acceptAlert(Alert alert) {
		String status = "FAIL";
		try {
			alert.accept();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Accept alert");
			Report.printStatus(status);
		}
	}

	/**
	 * Dismiss alert
	 * 
	 * @param alert
	 */
	public final void dismissAlert(Alert alert) {
		String status = "FAIL";
		try {
			alert.dismiss();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Dismiss alert");
			Report.printStatus(status);
		}
	}

	/**
	 * get alert text
	 * 
	 * @param alert
	 * @return String
	 */
	public final String getAlertText(Alert alert) {
		String alertText = null;
		String status = "FAIL";
		try {
			alertText = alert.getText();
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Getting alert text");
			Report.printStatus(status);
		}
		return alertText;
	}

	/**
	 * move to element
	 * 
	 * @param element
	 */
	public synchronized static final void moveToElement(WebElement element) {
		String status = "FAIL";
		try {
			new Actions(WebDriverManager.getDriver()).moveToElement(element).build().perform();
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Move to Element");
			Report.printStatus(status);
		}
	}

	/**
	 * @param source
	 * @param destination
	 */
	public synchronized static final void dragAndDrop(WebElement sourceEle, WebElement destinationEle) {
		String status = "FAIL";
		try {
			new Actions(WebDriverManager.getDriver()).dragAndDrop(sourceEle, destinationEle).build().perform();
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("drag and drop");
			Report.printStatus(status);
		}
	}

	/**
	 * Get element text
	 * 
	 * @param element
	 * @return string
	 */
	public static final String getText(WebElement element) {
		String text = "undefined";
		String status = "FAIL";
		try {
			text = element.getText();
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get Text");
			Report.printValue(text);
			Report.printStatus(status);
		}
		return text;
	}

	/**
	 * Get value
	 * 
	 * @param element
	 * @return string
	 */
	public static final String getTextBoxValue(WebElement element) {
		String text = "undefined";
		String status = "FAIL";
		try {
			text = element.getAttribute("value");
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get Value");
			Report.printValue(text);
			Report.printStatus(status);
		}
		return text;
	}

	/**
	 * public method to get the attribute value of any Webelement passed
	 * 
	 * @author BathriYO
	 * @param element
	 * @param attributeName
	 * @return String
	 */
	public static final String getAttribute(WebElement element, String attributeName) {
		String value = "undefined";
		String status = "FAIL";
		try {
			value = element.getAttribute(attributeName);
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get Attribute-" + attributeName);
			Report.printValue(value);
			Report.printStatus(status);
		}
		return value;
	}

	/**
	 * Get page Title
	 * 
	 * @return String
	 */
	public static final String getPageTitle() {
		String title = null;
		String status = "FAIL";
		try {
			title = WebDriverManager.getDriver().getTitle();
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Getting page title");
			Report.printStatus(status);
		}
		return title;
	}

	/**
	 * Get browser cookies
	 * 
	 * @author 10650956
	 * @return : Set<Cookie>
	 */
	public static synchronized final Set<Cookie> getCookies() {

		String status = "FAIL";
		Set<Cookie> cookies = null;
		try {

			cookies = WebDriverManager.getDriver().manage().getCookies();
			status = "PASS";

		} catch (Exception e) {

			ExceptionHandler.handleException(e);

		} finally {
			Report.printOperation("Get Browser Cookies");
			Report.printStatus(status);
		}

		return cookies;
	}

	/**
	 * Get browser cookie with name
	 * 
	 * @author 10650956
	 * @param cookieName : Cookies
	 * @return : Cookie
	 */
	public static synchronized final Cookie getCookie(String cookieName) {
		String status = "FAIL";
		Cookie cookie = null;
		try {

			cookie = WebDriverManager.getDriver().manage().getCookieNamed(cookieName);
			status = "PASS";

		} catch (Exception e) {

			ExceptionHandler.handleException(e);

		} finally {
			Report.printOperation("Get Browser Cookie");
			Report.printStatus(status);
		}

		return cookie;
	}

	/**
	 * Expands shadow root element
	 * 
	 * @author Het Veera
	 * @param element
	 * @return webElement
	 */
	public synchronized static final WebElement expandRootElement(WebElement element) {
		WebElement eExpanded = null;
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		String status = "FAIL";
		try {
			eExpanded = (WebElement) js.executeScript("arguments[0].shadowRoot", element);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Expand ShadowRoot");
			Report.printStatus(status);
		}
		return eExpanded;
	}

	/**
	 * Highlight WebElement
	 * 
	 * @param element
	 */
	public synchronized static final void highlightElement(WebElement element) {
		String status = "FAIL";
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		try {
			js.executeScript("arguments[0].setAttribute('style','background:yellow; border:2px solid red;');", element);
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Highlight Element");
			Report.printStatus(status);
		}
	}

	/**
	 * IsDisplayed
	 * 
	 * @param element
	 * @return boolean
	 */
	public final boolean isDisplayed(WebElement element) {
		boolean isDisplayed = false;
		String status = "FAIL";
		try {
			isDisplayed = element.isDisplayed();
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("isDisplayed");
			Report.printStatus(status);
		}
		return isDisplayed;
	}

	/**
	 * IsEnabled
	 * 
	 * @param element
	 * @return boolean
	 */
	public synchronized static final boolean isEnabled(WebElement element) {
		boolean isEnabled = false;
		String status = "FAIL";
		try {
			isEnabled = element.isEnabled();
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("isEnabled");
			Report.printStatus(status);
		}
		return isEnabled;
	}

	/**
	 * IsSelected
	 * 
	 * @param element
	 * @return boolean
	 */
	public synchronized static final boolean isSelected(WebElement element) {
		boolean isSelected = false;
		String status = "FAIL";
		try {
			isSelected = element.isSelected();
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("isSelected");
			Report.printStatus(status);
		}
		return isSelected;
	}

	/**
	 * isBold based on tag b and strong
	 * 
	 * @param element
	 * @return boolean
	 */
	public synchronized static final boolean isBold(WebElement element) {
		boolean flag = false;

		if (element.getTagName().equals("b") || element.getTagName().equals("strong")) {
			flag = true;
		}
		return flag;
	}

	/**
	 * isbold based on font-weight css value
	 * 
	 * @author BathriYO
	 * @param element
	 * @param font-weight
	 * @return boolean
	 */
	public final boolean isBold(WebElement element, int fontWeight) {
		boolean flag = false;
		String value = element.getCssValue("font-weight");
		if (Integer.parseInt(value) >= fontWeight) {
			flag = true;
		}
		return flag;
	}

	/**
	 * isUnderline :based on css decoration
	 * 
	 * @author BathriYO
	 * @param element
	 * @return boolean
	 */
	public synchronized static final boolean isUnderline(WebElement element) {
		boolean flag = false;
		if (element.getCssValue("text-decoration").equals("underline")) {
			flag = true;
		}
		return flag;
	}

	/**
	 * returns css value of an element
	 * 
	 * @param element
	 * @param cssName
	 * @return value
	 */
	public final String getCssValue(WebElement element, String cssName) {
		String value = "";
		String status = "FAIL";
		try {
			value = element.getCssValue(cssName);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Getting css value");
			Report.printKey(cssName);
			Report.printValue(value);
			Report.printStatus(status);
		}
		return value;
	}

	/**
	 * javascript click
	 * 
	 * @param element
	 */
	public synchronized static final void jsClick(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		String status = "FAIL";
		try {
			new WebDriverWait(WebDriverManager.getDriver(), GlobalVariables.waitTime)
					.until(ExpectedConditions.elementToBeClickable(element));
			js.executeScript("arguments[0].click();", element);
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("java script click");
			Report.printStatus(status);
		}
	}

	/**
	 * open application url from gloabal variable class
	 */
	public synchronized static final void openUrl() {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().get(GlobalVariables.applicationUrl);
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("open url");
			Report.printValue(GlobalVariables.applicationUrl);
			Report.printStatus(status);
		}
	}

	/**
	 * open application url passed as parameter
	 * 
	 * @param url
	 */
	public synchronized static final void openUrl(String url) {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().get(url);
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("open url");
			Report.printValue(url);
			Report.printStatus(status);
		}
	}

	/**
	 * getting page url
	 * 
	 * @author BathriYo
	 * @return String
	 */
	public final String getCurrentUrl() {
		String currentUrl = "undefine";
		String status = "FAIL";
		try {
			currentUrl = WebDriverManager.getDriver().getCurrentUrl();
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("get current url");
			Report.printStatus(status);
		}
		return currentUrl;
	}

	/**
	 * 
	 * driver.ExecuteScript("window.open('your URL', '_blank');");
	 * #ref:https://stackoverflow.com/questions/17547473/how-to-open-a-new-tab-using-selenium-webdriver-in-java#:~:text=new%20tab%20driver.-,findElement(By.,windowIterator%20%3D%20driver.
	 * 
	 * @param url
	 * @return
	 */
	public static final void openNewTab(String url) {

		String status = "FAIL";
		try {
			JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
			js.executeScript("window.open('" + url + "', '_blank');");
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Open New Tab");
			Report.printValue(url);
			Report.printStatus(status);
		}

	}

	/**
	 * Getting page source
	 * 
	 * @author BathriYo
	 * @return String
	 */
	public final String getPageSource() {
		String pageSource = "undefine";
		String status = "FAIL";
		try {
			pageSource = WebDriverManager.getDriver().getPageSource();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Getting page source");
			Report.printStatus(status);
		}
		return pageSource;
	}

	/**
	 * Scroll to bottom
	 */
	public synchronized final static void scrollToBottom() {
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		String status = "FAIL";
		try {
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Scroll to bottom");
			Report.printStatus(status);
		}
	}

	/**
	 * Scroll to up
	 */
	public synchronized final static void scrollToUp() {
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		String status = "FAIL";
		try {
			js.executeScript("window.scrollBy(0,-350)", "");
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Scroll up");
			Report.printStatus(status);
		}
	}

	/**
	 * Scroll by pixel
	 * 
	 * @param value
	 */
	public synchronized static final void ScrollByPixel(String value) {
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		String status = "FAIL";
		try {
			js.executeScript("window.scrollBy(0," + value + ")");
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Scroll By pixel");
			Report.printStatus(status);
		}
	}

	/**
	 * Scroll to element
	 * 
	 * @param element
	 */
	public synchronized static final void ScrollToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		String status = "FAIL";
		try {
			js.executeScript("arguments[0].scrollIntoView();", element);
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Scroll To element");
			Report.printStatus(status);
		}
	}

	/**
	 * Switch frame by name
	 * 
	 * @param name
	 */
	public synchronized static final void switchFrameByName(String name) {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().switchTo().frame(name);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("switch frame by name");
			Report.printValue(name);
			Report.printStatus(status);
		}
	}

	/**
	 * switch frame by index
	 * 
	 * @param index
	 */
	public synchronized static final void switchFrameByIndex(int index) {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().switchTo().frame(index);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("switch frame by index");
			Report.printValue(index + "");
			Report.printStatus(status);
		}
	}

	/**
	 * switch frame by frame webelement
	 * 
	 * @param frameElement
	 */
	public final void switchFrameByWebElement(WebElement frameElement) {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().switchTo().frame(frameElement);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("switch frame by webelelement");
			Report.printStatus(status);
		}
	}

	/**
	 * switch to default content
	 */
	public synchronized static final void switchToDefaultContent() {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().switchTo().defaultContent();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("switch to default content");
			Report.printStatus(status);
		}
	}

	public synchronized static final void switchToParentFrame() {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().switchTo().parentFrame();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("switch to parent frame");
			Report.printStatus(status);
		}
	}

	/**
	 * select by value
	 * 
	 * @param select
	 * @param value
	 */
	public final void selectByValue(Select select, String value) {
		String status = "FAIL";
		try {
			select.selectByValue(value);
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("select by value");
			Report.printStatus(status);
		}
	}

	/**
	 * select by index
	 * 
	 * @param select
	 * @param value
	 */
	public synchronized static final void selectByIndex(Select select, int index) {
		String status = "FAIL";
		try {
			select.selectByIndex(index);
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("select by index");
			Report.printValue(index + "");
			Report.printStatus(status);
		}
	}

	/**
	 * select by visible text
	 * 
	 * @param select
	 * @param visibleText
	 */
	public synchronized static final void selectByVisibleText(Select select, String visibleText) {
		String status = "FAIL";
		try {
			select.selectByVisibleText(visibleText);
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("select by visible text");
			Report.printValue(visibleText);
			Report.printStatus(status);
		}
	}

	/**
	 * deselect all the select options in the select dropdown
	 * 
	 * @author BathriYO
	 * @param select
	 */
	public synchronized static final void deslectAllOptions(Select select) {
		String status = "FAIL";
		try {
			select.deselectAll();
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("deselect all options");
			Report.printStatus(status);
		}
	}

	/**
	 * public utils to get all the options available in dropdown
	 * 
	 * @author BathriYO
	 * @param select
	 * @return List<WebElement>
	 */
	public final List<WebElement> getSelectOptions(Select select) {
		String status = "FAIL";
		List<WebElement> options = null;
		try {
			options = select.getOptions();
			status = "PASS";

		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("select all options");
			Report.printStatus(status);
		}
		return options;
	}

	/**
	 * wait for page load
	 */
	public synchronized static final boolean waitForPageLoad() {
		String status = "FAIL";
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		boolean isPageLoad = false;
		try {
			while (isPageLoad == false) {
				isPageLoad = js.executeScript("return document.readyState").toString().equals("complete");
				status = "PASS";
			}
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("wait for page load");
			Report.printStatus(status);
		}
		return isPageLoad;
	}

	/**
	 * return true if element is present in dom
	 * 
	 * @param dbKey
	 * @return boolean
	 */
	public final boolean isPresent(String dbkey) {
		boolean isPresent = false;
		String valueType = "undefined";
		String value = "undefined";
		String status = "FAIL";
		try {
			valueType = getValueType(dbkey, listElement);
			value = getValue(dbkey, listElement);
			Element element = new Element(WebDriverManager.getDriver());
			element.getElement(valueType, value);
			isPresent = true;
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			// please do not enter any exception
			status = "PASS";
		} finally {
			Report.printOperation("isPresent");
			Report.printKey(dbkey);
			Report.printValue(value);
			Report.printValueType(valueType);
			Report.printStatus(status);
		}
		return isPresent;
	}

	/**
	 * return true if element is present in dom
	 * 
	 * @param dbkey
	 * @return boolean
	 */
	public final boolean isPresentJavaScript(String dbkey) {
		boolean isPresent = false;
		String valueType = "undefined";
		String value = "undefined";
		String status = "FAIL";
		try {
			valueType = getValueType(dbkey, listElement);
			value = getValue(dbkey, listElement);
			JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
			js.executeScript(valueType, value);
			isPresent = true;
			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			isPresent = false;
		} finally {
			Report.printOperation("isPresent");
			Report.printKey(dbkey);
			Report.printValue(value);
			Report.printValueType(valueType);
			Report.printStatus(status);
		}
		return isPresent;
	}

	/**
	 * isPresent for dynamic xpath
	 * 
	 * @param locator
	 * @param value
	 * @return boolean
	 */
	public synchronized static final boolean isPresent(String locator, String value) {
		boolean isPresent = false;
		String status = "FAIL";
		try {
			Element objElement = new Element(WebDriverManager.getDriver());
			objElement.getElement(locator, value);
			isPresent = true;
			status = "PASS";
		} catch (Exception e) {
			// please do not throw any exception here
		} finally {
			Report.printOperation("isPresent");
			Report.printValue(value);
			Report.printValueType(locator);
			Report.printStatus(status);
		}
		return isPresent;
	}

	/**
	 * Wait for Javascript element
	 * 
	 * @author 10650956
	 * @param key : String
	 * @return : WebElement
	 */
	public final WebElement waitForJSElement(String key) {

		String status = "FAIL";
		int waitTime = 0;
		WebElement element = null;

		while (status.equals("FAIL") && waitTime <= GlobalVariables.waitTime) {

			try {

				element = (WebElement) getElement(key);

				if (element == null) {
					status = "FAIL";
				} else {
					status = "PASS";
				}

			} catch (Exception ex) {
				pause(1000);
				waitTime += 1;
			}

		}
		Report.printOperation("Wait for Javascript Element");
		Report.printKey(key);
		Report.printStatus(status);

		return element;
	}

	/**
	 * Wait for Javascript elements
	 * 
	 * @author 10650956
	 * @param key : String
	 * @return : List<WebElement>
	 */
	public final List<WebElement> waitForJSElements(String key) {

		String status = "FAIL";
		int waitTime = 0;
		List<WebElement> elements = null;

		while (status.equals("FAIL") && waitTime < GlobalVariables.waitTime) {

			try {

				elements = (List<WebElement>) getElement(key);

				if (elements == null) {
					status = "FAIL";
				} else {
					status = "PASS";
				}

			} catch (Exception ex) {
				pause(1000);
				waitTime += 1;
			}

		}

		Report.printOperation("Wait for Javascript Elements");
		Report.printKey(key);
		Report.printStatus(status);

		return elements;
	}

	/**
	 * get element using driver.findElement
	 * 
	 * @param key
	 * @param condition
	 * @return webElement
	 */
	public final WebElement getElement(String key) {
		WebElement webElement = null;
		String valueType = "undefined";
		String value = "undefined";
		String status = "FAIL";
		try {
			valueType = getValueType(key, listElement);
			value = getValue(key, listElement);
			Element element = new Element(WebDriverManager.getDriver());
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
	 * get element using driver.findElement
	 * 
	 * @param key
	 * @param condition
	 * @return webElement
	 */
	public final List<WebElement> getElements(String key) {
		List<WebElement> listWebElement = null;
		String valueType = "undefined";
		String value = "undefined";
		String status = "FAIL";
		try {
			Element element = new Element(WebDriverManager.getDriver());
			valueType = getValueType(key, listElement);
			value = getValue(key, listElement);
			listWebElement = element.getElements(valueType, value);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get Elements");
			Report.printKey(key);
			Report.printValue(value);
			Report.printValueType(valueType);
			Report.printStatus(status);
		}
		return listWebElement;
	}

	/**
	 * Get webelement using element.findElement()
	 * 
	 * @param parentElement
	 * @param key
	 * @param condition
	 * @return webelement
	 */
	public final WebElement getElementFromParentElement(WebElement parentElement, String key) {
		WebElement webElement = null;
		String valueType = "undefined";
		String value = "undefined";

		String status = "FAIL";
		try {
			Element element = new Element(WebDriverManager.getDriver());
			value = getValue(key, listElement);
			valueType = getValueType(key, listElement);
			webElement = element.getElement(parentElement, valueType, value);
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
	 * get list of elements using parent.findElements()
	 * 
	 * @param parentElement
	 * @param key
	 * @return List<webelement>
	 */
	public final List<WebElement> getElementsFromParentElement(WebElement parentElement, String key) {
		List<WebElement> listWebElement = null;
		String valueType = "undefined";
		String value = "undefined";

		String status = "FAIL";
		try {
			Element element = new Element(WebDriverManager.getDriver());
			value = getValue(key, listElement);
			valueType = getValueType(key, listElement);
			listWebElement = element.getElements(parentElement, valueType, value);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get Elements");
			Report.printKey(key);
			Report.printValue(value);
			Report.printValueType(valueType);
			Report.printStatus(status);
		}
		return listWebElement;
	}

	/**
	 * get element by directly passing locator and locator value
	 * 
	 * @author BathriYO
	 * @param locator
	 * @param locatorValue
	 * @return WebElement
	 */
	public final WebElement getElement(String locator, String locatorValue) {
		WebElement element = null;
		String status = "FAIL";
		try {
			Element objElement = new Element(WebDriverManager.getDriver());
			element = objElement.getElement(locator, locatorValue);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get Element");
			Report.printValue(locatorValue);
			Report.printValueType(locator);
			Report.printStatus(status);
		}
		return element;
	}

	/**
	 * get list of element by directly passing locator and locator value
	 * 
	 * @author BathriYO
	 * @param locator
	 * @param locatorValue
	 * @return List<WebElement>
	 */
	public final List<WebElement> getElements(String locator, String locatorValue) {
		List<WebElement> lstElement = null;
		String status = "FAIL";
		try {
			Element objElement = new Element(WebDriverManager.getDriver());
			lstElement = objElement.getElements(locator, locatorValue);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get Elements");
			Report.printValue(locatorValue);
			Report.printValueType(locator);
			Report.printStatus(status);
		}
		return lstElement;
	}

	/**
	 * checking brokenlink
	 * 
	 * @return boolean
	 */
	public final boolean checkBrokenLinks() {
		boolean check = false;
		String status = "FAIL";
		try {
			CheckBrokenLinks checkBrokenLinks = new CheckBrokenLinks();
			check = checkBrokenLinks.checkBrokenLinks(WebDriverManager.getDriver(),
					WebDriverManager.getDriver().getCurrentUrl());
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Check Broken Links");
			Report.printStatus(status);
		}
		return check;
	}

	/**
	 * this method executes javascript (without parameter) and returns object type
	 * reference variable
	 * 
	 * @author BathriYO
	 * @param javascript
	 * @return object
	 */
	public final Object executeJavaScript(String javaScript) {
		Object scriptReturn = null;
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		String status = "FAIL";
		try {
			scriptReturn = js.executeScript(javaScript);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Execute java script");
			Report.printValue(javaScript);
			Report.printStatus(status);
		}
		return scriptReturn;
	}

	/**
	 * this method executes javascript (with parameter as webElement) and returns
	 * object type reference variable
	 * 
	 * @author BathriYO
	 * @param javascript
	 * @return object
	 */
	public synchronized static final Object executeJavaScript(String javaScript, WebElement element) {
		Object scriptReturn = null;
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		String status = "FAIL";
		try {
			scriptReturn = js.executeScript(javaScript, element);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Execute java script");
			Report.printValue(javaScript);
			Report.printStatus(status);
		}
		return scriptReturn;
	}

	/**
	 * This is used to get value based on the key from the list
	 * 
	 * @param dbKey
	 * @param data
	 * @return String
	 */
	public synchronized final static String getValue(String dbkey, List<DBRowTO> data) {
		for (DBRowTO temp : data) {
			if (temp.getKey().equalsIgnoreCase(dbkey)) {
				return temp.getValue();
			}
		}
		return "undefined";
	}

	/**
	 * This is used to get value type based on the key from the list
	 * 
	 * @param dataKey
	 * @param data
	 * @return String
	 */
	public synchronized final static String getValueType(String datakey, List<DBRowTO> data) {
		for (DBRowTO temp : data) {
			if (temp.getKey().equalsIgnoreCase(datakey)) {
				return temp.getValueType();
			}
		}
		return "user defined";
	}

	/**
	 * public final util to navigate forward
	 * 
	 * @author BathriYo
	 */
	public synchronized static final void navigateForward() {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().navigate().forward();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Navigate Forward");
			Report.printStatus(status);
		}
	}

	/**
	 * public final util to navigate backward
	 * 
	 * @author BathriYo
	 */
	public synchronized static final void navigateBackward() {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().navigate().back();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Navigate Backward");
			Report.printStatus(status);
		}
	}

	/**
	 * public final util to refresh browser
	 * 
	 * @author BathriYo
	 */
	public synchronized static final void refresh() {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().navigate().refresh();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Refresh");
			Report.printStatus(status);
		}
	}

	/**
	 * copy string to clipboard
	 * 
	 * @author BathriYo
	 * @param str
	 */
	public synchronized static final void copyStringToClipboard(String str) {
		String status = "FAIL";
		try {
			StringSelection stringSelection = new StringSelection(str);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("copy to clipboard");
			Report.printStatus(status);
		}
	}

	/**
	 * get window handles
	 * 
	 * @author BathriYo
	 * @return set<String>
	 */
	public synchronized static final Set<String> getWindowHandles() {
		Set<String> handles = null;
		String status = "FAIL";
		try {
			handles = WebDriverManager.getDriver().getWindowHandles();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get window handles");
			Report.printStatus(status);
		}
		return handles;
	}

	/**
	 * @param windowHandles
	 * @param nthWindow
	 * @return String
	 */
	public synchronized static final String getWindowHandle(Set<String> windowHandles, int nthWindow) {
		String windowHandle = "undefine";
		String status = "FAIL";
		Iterator<String> iterator = windowHandles.iterator();
		try {
			int i = 1;
			while (iterator.hasNext()) {
				if (i == nthWindow) {
					windowHandle = iterator.next();
					break;
				} else {
					iterator.next();

				}
				i += 1;
				status = "PASS";
			}
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get nthwindow handle");
			Report.printStatus(status);
		}
		return windowHandle;
	}

	/**
	 * switch to window
	 * 
	 * @author BathriYo
	 * @param windowhandle
	 */
	public synchronized static final void switchToWindow(String windowHandle) {
		String status = "FAIL";
		try {
			WebDriverManager.getDriver().switchTo().window(windowHandle);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Switch to Window");
			Report.printStatus(status);
		}
	}

	/**
	 * public final Utils to get browser storage key value pair
	 * 
	 * @author BathriYo
	 * @return Map<String,String>
	 */
	public final Map<String, String> getBrowserLocalStorage() {
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		long totalkeys = (long) js.executeScript("return window.localStorage.length");
		String key;
		String status = "FAIL";
		Map<String, String> mapKeyValue = new HashMap<String, String>();
		try {
			for (long index = 0; index < totalkeys; index++) {
				key = (String) js.executeScript("return window.localStorage.key(" + index + ")");
				mapKeyValue.put(key, (String) js.executeScript("return window.localStorage.getItem('" + key + "')"));
			}
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get browser local storage");
			Report.printStatus(status);
		}
		return mapKeyValue;
	}

	/**
	 * clears Chrome browser local storage caches
	 */
	public synchronized static final void clearCacheLocalStorage() {
		JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();
		String status = "FAIL";
		try {
			js.executeScript("window.localStorage.clear()");
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("clear cache chrome local storage");
			Report.printStatus(status);
		}
	}

	/**
	 * @param dbkey
	 * @return String
	 */
	public final synchronized static String getTestData(String dbkey) {
		return getValue(dbkey, SQLDriver.TTestData.get());
	}

	/**
	 * this method will save/download file in IE
	 */
	public synchronized static final void downloadFileInIE() {
		String status = "FAIL";
		try {
			Robot robot = new Robot();
			robot.setAutoDelay(250);
			robot.keyPress(KeyEvent.VK_ALT);
			Thread.sleep(1000);
			robot.keyPress(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_S);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Download File in IE");
			Report.printStatus(status);
		}
	}

	/**
	 * this method will close the window using robot class
	 */
	public synchronized static final void closeWindow() {
		String status = "FAIL";
		try {
			Robot robot = new Robot();
			robot.setAutoDelay(250);
			robot.keyPress(KeyEvent.VK_ALT);
			Thread.sleep(1000);
			robot.keyPress(KeyEvent.VK_F4);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_F4);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Close window");
			Report.printStatus(status);
		}
	}
	
	public final String getXpath(String key) {
		//WebElement webElement = null;
		String valueType = "undefined";
		String value = "undefined";
		String status = "FAIL";
		try {
			valueType = getValueType(key, listElement);
			value = getValue(key, listElement);
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
		return value;
	}
	
	/**
	 * get element using driver.findElement
	 * 
	 * @param locatorValue
	 * @param type: type of locator (id, xpath, name, etc.)
	 * @return webElement
	 */
	public final WebElement getElementByXpath(String locatorValue, String type) {
		WebElement webElement = null;
		String status = "FAIL";
		try {
		//	valueType = getValueType(type, listElement);
			//value = getValue(key, listElement);
			Element element = new Element(WebDriverManager.getDriver());
			webElement = element.getElement(type, locatorValue);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get Element");
			//Report.printKey(xpath);
			Report.printValue(locatorValue);
			Report.printValueType(type);
			Report.printStatus(status);
		}
		return webElement;
	}
	
	
	
}
