package qa.amazon.device.stepdefs;

import io.cucumber.java.en.Then;
import qa.amazon.device.pages.AMZ_SearchResultPage;
import qa.framework.assertions.AssertLogger;
import qa.framework.utils.Reporter;

public class AMZ_SearchResultPageStepDefs {

	AMZ_SearchResultPage objSearchResult  = new AMZ_SearchResultPage();
	
	@Then("^user verifies the search result$")
    public void user_verifies_the_search_result(){
		boolean isPresent = objSearchResult.isPresentResult();
		
		Reporter.addDeviceScreenshot(null, null);
		AssertLogger.assertTrue(isPresent, "Result is didn't on Amazon application");
			
    }
	
}
