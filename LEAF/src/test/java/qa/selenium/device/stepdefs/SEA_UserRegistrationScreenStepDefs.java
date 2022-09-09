package qa.selenium.device.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import qa.framework.assertions.AssertLogger;
import qa.framework.utils.Reporter;
import qa.selenium.device.pages.SEA_UserRegistrationScreen;

public class SEA_UserRegistrationScreenStepDefs {

	SEA_UserRegistrationScreen objUserReg = new SEA_UserRegistrationScreen();
	
	@Then("^user verifies the regiestration$")
	public void user_verifies_the_regiestration() {
		String actualName = objUserReg.getName();
		
		Reporter.addDeviceScreenshot(null, null);
		AssertLogger.assertEquals(actualName, "Mr. SMITH", "Name didn't mathch");
	}

	@And("^user fill registration form and submit$")
	public void user_fill_registration_form_and_submit() {
		objUserReg.enterUserName("Smith");
		objUserReg.enterEmail("smith@live.com");
		objUserReg.enterPassword("password");
		objUserReg.enterName("Mr. SMITH");
		objUserReg.selectLanguage("Java");
		objUserReg.selectIAcceptAdds();
		objUserReg.clickRegisterButton();
		
	}

}
