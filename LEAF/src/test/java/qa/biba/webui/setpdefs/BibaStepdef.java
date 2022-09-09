package qa.biba.webui.setpdefs;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import qa.biba.webui.pages.LoginPage;
import qa.framework.utils.Reporter;

public class BibaStepdef {

	LoginPage loginPage= new LoginPage();
	
	@Given("^biba application is available$")
    public void flipkart_application_is_available() throws InterruptedException {
		loginPage.openApp();   
    }

    @When("^User login with invalid username as (.+) and password as (.+)$")
    public void user_login_with_invalid_username_as_and_password_as(String username, String password) throws InterruptedException {
        loginPage.login(username, password);
    }

    @Then("^application should show error message as \"([^\"]*)\"$")
    public void application_should_show_error_message_as_something(String strArg1) {
    	String expErrorMessage="The password you have entered is invalid. Please try again.";
    	String actErrorMessage = loginPage.getErrorMessage();
    	
    	Reporter.addScreenCapture();
    	Reporter.addScreenCapture();
    	Reporter.addScreenCapture();
    	
    	Assert.assertEquals("Expected Message didn't match actual message", expErrorMessage, actErrorMessage);
    }
	
	
}



