package qa.onecs.webui.pages;

import java.util.ArrayList;
import java.util.List;
import qa.framework.dbutils.SQLDriver;
import qa.framework.utils.Action;
import qa.framework.webui.browsers.WebDriverManager;
import qa.onecs.webui.SharedData.SharedData;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import qa.framework.utils.GlobalVariables;
import qa.framework.utils.Reporter;
import qa.framework.webui.browsers.WebDriverManager;

import qa.framework.dbutils.SQLDriver;
import qa.framework.utils.Action;
import qa.framework.utils.Reporter;

public class AssignmentPage {
	static Action action = new Action(SQLDriver.getEleObjData("OneCS_HomePage"));

	// SharedData sharedData;

	public static void openUrl() {
		WebDriverManager.startDriver();
		WebDriverManager.setWebUIFlag(true);
		Action.openUrl(Action.getTestData("URL"));
	}

	public static String pageTitleDisplayed() {
		String title = Action.getPageTitle();
		// System.out.println(title);
		return title;
	}

	public static void selectValueFromDropdown(String value, String element) throws InterruptedException {
		// Thread.sleep(2000);
		Select select = new Select(action.getElement(element));
		Action.selectByVisibleText(select, Action.getTestData(value));
		Reporter.addScreenCapture();
	}

	public static boolean fieldsDisplayed() {

		boolean flag = false;
		List<WebElement> fields = new ArrayList<WebElement>();
		fields.add(action.getElement("SUMMARY"));
		fields.add(action.getElement("BGCODE_LABEL"));
		fields.add(action.getElement("ASSIGNED_TO_LABEL"));
		fields.add(action.getElement("DOCUMENTS_LABEL"));
		fields.add(action.getElement("ALL_TABS_TITLE"));
		for (WebElement element : fields) {
			flag = element.isDisplayed();
		}

		System.out.println(flag);
		return flag;
	}

	public static void enterBGCode() throws InterruptedException {
		Thread.sleep(4000);
		action.getElement("BGCODE_FIELD").sendKeys("DOX", Keys.ENTER);
		Thread.sleep(4000);

		// action.click(action.getElement("BGCODE_FIELD"));
		// action.click(action.getElement("BG_CODE"));

		action.getElement("ASSIGNED_TO_FIELD").sendKeys("TEST");
		Thread.sleep(4000);
	}

	public static boolean verifyErrorDisplayed() {
		boolean flag = action.getElement("ERROR_MSG").isDisplayed();
		return flag;
	}

	public static void enterValue(String value, String element) throws InterruptedException {
		action.getElement(element).sendKeys(Action.getTestData(value));
		// Thread.sleep(2000);
		Reporter.addScreenCapture();
	}

	// FOR IND
	public static void selectAccount(String value, String element) {
		List<WebElement> elements = action.getElements(element);
		int size = elements.size();
		String xpath = "(" + action.getXpath(element) + ")[" + size + "]";

		WebElement webElement = action.getElementByXpath(xpath, "xpath");
		Select select = new Select(webElement);
		Action.selectByVisibleText(select, value);
	}

	// FOR JOINT
	public static void selectWhichClient(String element) {
		List<WebElement> elements = action.getElements(element);
		int size = elements.size();
		String xpath = "(" + action.getXpath(element) + ")[" + size + "]";

		WebElement webElement = action.getElementByXpath(xpath, "xpath");
		Action.click(webElement);
	}

}
