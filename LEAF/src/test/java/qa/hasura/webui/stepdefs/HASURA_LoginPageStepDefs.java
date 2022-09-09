package qa.hasura.webui.stepdefs;

import io.cucumber.java.en.And;
import qa.framework.utils.Action;
import qa.framework.utils.Reporter;
import qa.hasura.webui.pages.HASURA_LoginPage;

public class HASURA_LoginPageStepDefs {

	HASURA_LoginPage objLoginPage = new HASURA_LoginPage();
	

    @And("^user enters credentials and clicks on 'Submit' button$")
    public void user_enters_redentials_and_clicks_on_login_button()  {
    	
    	String txtUserId = Action.getTestData("userid");
    	String txtPwd = Action.getTestData("password");
    	
    	objLoginPage.enterUserId(txtUserId);  
    	objLoginPage.enterPassword(txtPwd);
    	
    	Reporter.addStepLog("<strong>User id entered as: </strong>"+txtUserId);
    	Reporter.addStepLog("<strong>User id entered as: </strong>"+txtPwd);
    	
    	objLoginPage.clickSubmit();
    }
}
