package qa.framework.device;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import qa.framework.dbutils.DBRowTO;
import qa.framework.report.Report;
import qa.framework.utils.Action;
import qa.framework.utils.ExceptionHandler;
import qa.framework.utils.Reporter;
import qa.framework.webui.element.Element;

public class DeviceActions extends TouchAction {

	List<DBRowTO> lstElement;

	public DeviceActions(List<DBRowTO> argListElement) {
		super((AppiumDriver) DeviceDriverManager.getDriver());
		this.lstElement = argListElement;
	}

	private static void takeScrCapPerAction() {
		if (Action.srcCapPerAction == true) {
			Reporter.addDeviceScreenshot(null, null);
		}
	}

	public static final boolean isPresent(WebElement element) {
		String status = "FAIL";
		boolean isPresent = false;
		try {
			isPresent = element.isDisplayed();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("isPresent");
			Report.printStatus(status);
		}

		return isPresent;

	}

	public synchronized static final void openUrl(String url) {
		String status = "FAIL";
		try {
			DeviceDriverManager.getDriver().get(url);
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("open url");
			Report.printValue(url);
			Report.printStatus(status);
		}
	}

	/**
	 * Get device Element
	 * 
	 * Please download cast to WebElement for Browser and MobileElement for App.
	 * 
	 * @param key
	 * @return
	 */
	final public Object getElement(String key) {
		Object object = null;
		String valueType = "undefined";
		String value = "undefined";

		String status = "FAIL";

		try {
			valueType = Action.getValueType(key, lstElement);
			value = Action.getValue(key, lstElement);

			Element objElement = new Element(null);
			object = objElement.getDeviceElement(valueType, value);
			status = "PASS";

		} catch (Exception e) {
			e.printStackTrace();
			// should be handled based on need.
		} finally {
			Report.printOperation("Get Element");
			Report.printKey(key);
			Report.printValue(value);
			Report.printValueType(valueType);
			Report.printStatus(status);
		}

		return object;

	}

	/**
	 * Get list of Elements
	 * 
	 * Please download cast to WebElement for Browser and MobileElement for App.
	 * 
	 * @author 10650956
	 * @param key
	 * @return
	 */
	final public List<Object> getElements(String key) {
		List<Object> lstWebElement = null;
		String valueType = "undefined";
		String value = "undefined";

		String status = "FAIL";

		try {
			valueType = Action.getValueType(key, lstElement);
			value = Action.getValue(key, lstElement);

			Element objElement = new Element(null);
			lstWebElement = objElement.getDeviceElements(valueType, value);
			status = "PASS";

		} catch (Exception e) {
			// should be handled based on need.
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
			Report.printOperation("Send keys");
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
			Report.printOperation("Get Attridute");
			Report.printValueType(name);
			Report.printValue(value);
			Report.printStatus(status);
		}

		return value;
	}

	/**
	 * PressKey
	 * 
	 * @author 10650956
	 * @param key
	 */
	final public static void pressKey(AndroidKey key) {
		String status = "FAIL";

		try {

			@SuppressWarnings("unchecked")
			AndroidDriver<WebElement> driver = (AndroidDriver<WebElement>) DeviceDriverManager.getDriver();
			driver.pressKey(new KeyEvent(key));

			/* pressKey() is not supported for IOS */
			/* Reference: http://appium.io/docs/en/commands/device/keys/press-keycode/ */

			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Press Key");
			Report.printStatus(status);
		}

	}

	/**
	 * LongPressKey
	 * 
	 * @author 10650956
	 * @param key
	 */
	final public static void longPressKey(AndroidKey key) {
		String status = "FAIL";

		try {

			@SuppressWarnings("unchecked")
			AndroidDriver<WebElement> driver = (AndroidDriver<WebElement>) DeviceDriverManager.getDriver();
			driver.longPressKey(new KeyEvent(key));

			/* pressKey() is NOT supported for IOS */
			/* Reference: http://appium.io/docs/en/commands/device/keys/press-keycode/ */

			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Long Press");
			Report.printStatus(status);
		}

	}

	/**
	 * Scroll
	 * 
	 * @author 10650956
	 * @param onElement
	 * @param xOffset
	 * @param yOffset
	 */
	final public static void scroll(String direction) {
		String status = "FAIL";

		try {
			JavascriptExecutor js = (JavascriptExecutor) ((AppiumDriver) DeviceDriverManager.getDriver());
			HashMap<String, String> scrollObject = new HashMap<String, String>();
			scrollObject.put("direction", direction);
			js.executeScript("mobile: scroll", scrollObject);

			status = "PASS";
			takeScrCapPerAction();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Scroll " + direction);
			Report.printStatus(status);
		}

	}

	public final boolean isPresent(String key) {
		boolean isPresent = false;
		String valueType = "undefined";
		String value = "undefined";
		String status = "FAIL";

		try {
			valueType = Action.getValueType(key, lstElement);
			value = Action.getValue(key, lstElement);

			Element objElement = new Element(null);
			objElement.getDeviceElement(valueType, value);

			isPresent = true;
			status = "PASS";
		} catch (Exception e) {
			// please do not enter any exception
			status = "PASS";
		} finally {
			Report.printOperation("isPresent");
			Report.printKey(key);
			Report.printValue(value);
			Report.printValueType(valueType);
			Report.printStatus(status);
		}
		return isPresent;
	}

	/**
	 * Set Progress bar
	 * 
	 * @author 10650956
	 * @param element
	 * @param desirePositionInPercent : 50
	 */
	public static void setProgressBar(WebElement element, double desirePositionInPercent) {

		String status = "FAIL";

		AppiumDriver<?> driver = (AppiumDriver<?>) DeviceDriverManager.getDriver();

		try {

			int start = element.getLocation().getX();
			int y = element.getLocation().getY();
			int end = element.getSize().getWidth();

			int position = (int) (end * desirePositionInPercent) / 100;

			new TouchAction(driver).press(PointOption.point(start, y)).moveTo(PointOption.point(position, y)).release()
					.perform();

			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Set Progress Bar");
			Report.printStatus(status);
		}

	}

	/**
	 * Get context handles
	 * 
	 * @param element
	 * @param desirePositionInPercent
	 * @return
	 */
	public static Set<String> getContexttHandles(WebElement element, double desirePositionInPercent) {

		String status = "FAIL";
		Set<String> contextHandles = null;

		AppiumDriver<?> driver = (AppiumDriver<?>) DeviceDriverManager.getDriver();

		try {
			contextHandles = driver.getContextHandles();

			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Get Context Handles");
			Report.printStatus(status);
		}

		return contextHandles;

	}

	/**
	 * Switch To Context
	 * 
	 * @author 10650956
	 * @param context
	 */
	public static void switchToContext(String context) {
		String status = "FAIL";

		AppiumDriver<?> driver = (AppiumDriver<?>) DeviceDriverManager.getDriver();

		try {

			driver.context(context);

			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Switch To Context");
			Report.printStatus(status);
		}

	}

	/**
	 * Swipe
	 * 
	 * 
	 * @param el
	 * @param direction
	 */
	public static void swipe(WebElement el, String direction) {
		System.out.println("swipeElementAndroid(): dir: '" + direction + "'"); // always log your actions

		AppiumDriver<?> driver = (AppiumDriver<?>) DeviceDriverManager.getDriver();

		// Animation default time:
		// - Android: 300 ms
		// - iOS: 200 ms
		// final value depends on your app and could be greater
		final int ANIMATION_TIME = 200; // ms

		final int PRESS_TIME = 200; // ms

		int edgeBorder;
		PointOption pointOptionStart, pointOptionEnd;

		// init screen variables
		Rectangle rect = el.getRect();
		// sometimes it is needed to configure edgeBorders
		// you can also improve borders to have vertical/horizontal
		// or left/right/up/down border variables
		edgeBorder = 0;

		switch (direction) {
		case "DOWN": // from up to down
			pointOptionStart = PointOption.point(rect.x + rect.width / 2, rect.y + edgeBorder);
			pointOptionEnd = PointOption.point(rect.x + rect.width / 2, rect.y + rect.height - edgeBorder);
			break;
		case "UP": // from down to up
			pointOptionStart = PointOption.point(rect.x + rect.width / 2, rect.y + rect.height - edgeBorder);
			pointOptionEnd = PointOption.point(rect.x + rect.width / 2, rect.y + edgeBorder);
			break;
		case "LEFT": // from right to left
			pointOptionStart = PointOption.point(rect.x + rect.width - edgeBorder, rect.y + rect.height / 2);
			pointOptionEnd = PointOption.point(rect.x + edgeBorder, rect.y + rect.height / 2);
			break;
		case "RIGHT": // from left to right
			pointOptionStart = PointOption.point(rect.x + edgeBorder, rect.y + rect.height / 2);
			pointOptionEnd = PointOption.point(rect.x + rect.width - edgeBorder, rect.y + rect.height / 2);
			break;
		default:
			throw new IllegalArgumentException("swipeElementAndroid(): direction: '" + direction + "' NOT supported");
		}

		// execute swipe using TouchAction
		try {
			new TouchAction(driver).press(pointOptionStart)
					// a bit more reliable when we add small wait
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME))).moveTo(pointOptionEnd).release()
					.perform();
		} catch (Exception e) {
			System.err.println("swipeElementAndroid(): TouchAction FAILED\n" + e.getMessage());
			return;
		}

		// always allow swipe action to complete
		try {
			Thread.sleep(ANIMATION_TIME);
		} catch (InterruptedException e) {
			// ignore
		}
	}

}
