package qa.onecs.webui.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import qa.framework.webui.browsers.WebDriverManager;
import qa.onecs.webui.SharedData.SharedData;
import qa.onecs.webui.pages.AssignmentPage;
import qa.framework.assertions.AssertLogger;
import qa.framework.utils.Action;
import qa.framework.utils.Reporter;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import io.cucumber.datatable.DataTable;

import io.cucumber.java.en.Then;
import qa.framework.assertions.AssertLogger;
import qa.framework.dbutils.SQLDriver;
import qa.framework.utils.Action;
import qa.framework.utils.Reporter;
import qa.framework.webui.browsers.WebDriverManager;
import qa.onecs.*;

public class AssignmentStepDef {

	// SoftAssert softAssertion = new SoftAssert();
	static Action action = new Action(SQLDriver.getEleObjData("OneCS_HomePage"));

	// SharedData sharedData;

	@Given("user launches the url")
	public void user_launches_the_url() {
		AssignmentPage.openUrl();
	}

	@Then("user verifies Application Dashboard screen is displayed")
	public void user_verifies_Application_Dashboard_screen_is_displayedd() {
		String actulTitle = AssignmentPage.pageTitleDisplayed();
		String expectedTitle = "Dashboard";
		AssertLogger.assertEquals(actulTitle, expectedTitle, "Error... Title do not match.");

	}

	@Then("user clicks on {string} button")
	public void user_clicks_on_button(String button) throws InterruptedException {
		Action.click(action.getElement(button));
		// Thread.sleep(4000);
		Reporter.addScreenCapture();
	}

	@Then("user selects {string} from the {string} dropdown")
	public void user_selects_from_the_dropdown(String value, String element) throws InterruptedException {
		AssignmentPage.selectValueFromDropdown(value, element);
		// Thread.sleep(2000);
		Reporter.addEntireScreenCaptured();
	}

	@Then("user verifies {string} screen is displayed")
	public void user_verifies_screen_is_displayed(String string) {
		boolean res = AssignmentPage.fieldsDisplayed();
		AssertLogger.assertTrue(res, "Error... Screen not displayed");
		Reporter.addScreenCapture();
	}

	@Then("user enters BGCode")
	public void user_enters_BGCode() throws InterruptedException {
		AssignmentPage.enterBGCode();
		Reporter.addScreenCapture();
	}

	@Then("user clicks on {string} tab")
	public void user_clicks_on_tab(String button) throws InterruptedException {
		Action.click(action.getElement(button));
		Thread.sleep(4000);
		boolean flag = action.getElement("CLIENT_DETAILS_LABEL").isDisplayed();
		AssertLogger.assertTrue(flag, "Error... Client screen not displayed");

		Reporter.addScreenCapture();
	}

	@Then("user clicks on {string} tab and verfies error message is displayed")
	public void user_clicks_on_tab_and_verfies_error_message_is_displayed(String string) throws InterruptedException {
		Action.click(action.getElement("PORTFOLIO_DETAILS_TAB"));
		// Thread.sleep(2000);
		boolean res = AssignmentPage.verifyErrorDisplayed();
		AssertLogger.assertTrue(res, "Error... Error message not displayed");
	}

	@Then("user enters {string} in {string} field")
	public void user_enters_in_field(String value, String element) throws InterruptedException {
		AssignmentPage.enterValue(value, element);
	}

	@Then("user selects {string} checkbox")
	public void user_selects_checkbox(String checkbox) throws InterruptedException {
		Action.click(action.getElement(checkbox));
		Reporter.addScreenCapture();
	}

	@Then("user chooses {string} option")
	public void user_chooses_option(String option) throws InterruptedException {
		Action.click(action.getElement(option));
		// Thread.sleep(1000);
		Reporter.addScreenCapture();
	}

	@Then("user selects {string} from the {string} Dropdown")
	public void user_selects_from_the_dropdownn(String value, String element) throws InterruptedException {
		AssignmentPage.selectAccount(value, element);
		// Thread.sleep(2000);
		Reporter.addEntireScreenCaptured();
	}

	@Then("user chooses {string} optionn")
	public void user_chooses_optionn(String option) throws InterruptedException {
		AssignmentPage.selectWhichClient(option);
		// Thread.sleep(1000);
		Reporter.addScreenCapture();
	}

}
