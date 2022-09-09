package qa.amazon.webui.stepdefs;

import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import qa.amazon.webui.pages.AMZ_LoginPage;
import qa.framework.assertions.AssertLogger;
import qa.framework.utils.Action;
import qa.framework.utils.Reporter;

public class AMZ_LoginPageStepDefs {

	AMZ_LoginPage objLoginPage = new AMZ_LoginPage();
	

    @And("^user enters invalid user id and clicks on 'Continue' button$")
    public void user_enters_invalid_user_id_and_clicks_on_continue_button()  {
    	
    	String tdUserId = Action.getTestData("userid");
    	
    	objLoginPage.enterUserId(tdUserId);  
    	
    	Reporter.addStepLog("<strong>User id entered as: </strong>"+tdUserId);
    	
    	objLoginPage.clickContiunu();
    }
    
    @Then("^user verifies error message as \"([^\"]*)\" on Login page$")
    public void user_verifies_error_message_as_something_on_login_page(String expErrorMsg)  {
        
    	String actualErrorMsg = objLoginPage.getErrorMsg();
    	
    	Reporter.addScreenCapture();    	
    	
    	AssertLogger.assertEquals(actualErrorMsg, expErrorMsg, "Error message does not match.");
    	
    	
    }
	
    
    @And("^intentionally failing this step$")
    public void intentionally_failing_this_step(){
        
    	AssertLogger.assertEquals("some", "some", "msgOnFailure");
		AssertLogger.assertNotEquals("some", "one","msgOnFailure");
		AssertLogger.assertNull(null, "msgOnFailure");
		AssertLogger.assertNotNull("some",  "msgOnFailure");
		AssertLogger.assertTrue(true,  "msgOnFailure");
		AssertLogger.assertFalse(false,  "msgOnFailure");
    	
    	Assert.fail();
    }
}
