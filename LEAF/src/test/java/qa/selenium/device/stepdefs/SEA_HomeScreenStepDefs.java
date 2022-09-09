package qa.selenium.device.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import qa.framework.assertions.AssertLogger;
import qa.framework.utils.Reporter;
import qa.selenium.device.pages.SEA_HomeScreen;

public class SEA_HomeScreenStepDefs {

	SEA_HomeScreen objHomeScreen = new SEA_HomeScreen();
	
	@When("^navigate to registration page on the app$")
    public void navigate_to_registration_page_on_the_app() {
        
		objHomeScreen.clickRegisterUserFolderIcon();
    }
	
	@When("^user enter text \"([^\"]*)\" on the app$")
    public void user_enter_text_something_on_the_app(String value)  {
        objHomeScreen.enterText(value);
    }

    @When("^user enable popup on the app$")
    public void user_enable_popup_on_the_app()  {
    	objHomeScreen.openPopup();
    	Reporter.addDeviceScreenshot(null, null);
    }

    @Then("^user verified display text as \"([^\"]*)\"$")
    public void user_verified_display_text_as_something(String expected)  {
    	String actual = objHomeScreen.getDispalyText();
    	Reporter.addDeviceScreenshot(null, null);
    	AssertLogger.assertEquals(actual, expected, "Text didn't matched!!");
    }

    @Then("^user should be able to disable the popup on the app$")
    public void user_should_be_able_to_disable_the_popup_on_the_app()  {
        objHomeScreen.closePopup();
    }

    @And("^user enabled progress bar$")
    public void user_enabled_progress_bar()  {
        objHomeScreen.enableProgressBar();
    }

    @And("^user enable dispaly text$")
    public void user_enable_dispaly_text()  {
    	objHomeScreen.enableDispalyText();
    	Reporter.addDeviceScreenshot(null, null);
    	
    }
	
}
