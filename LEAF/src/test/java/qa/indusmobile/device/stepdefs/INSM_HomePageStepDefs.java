package qa.indusmobile.device.stepdefs;

import io.cucumber.java.en.When;
import qa.framework.assertions.AssertLogger;
import qa.framework.utils.Action;
import qa.framework.utils.Reporter;
import qa.indusmobile.device.pages.INSM_HomePage;

public class INSM_HomePageStepDefs {

	INSM_HomePage objHomePage = new INSM_HomePage();
	
	@When("^user clicks on 'BhimUPI' button$")
    public void user_clicks_on_bhimupi_button() throws Throwable {
		
		Action.pause(20000);
		AssertLogger.assertTrue(objHomePage.isBhimUPIPresent(), "Bhim UPI button is NOT available !!");;
		Reporter.addDeviceScreenshot(null, null);
    }

	
	
}
