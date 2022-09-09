package qa.hasura.webui.stepdefs;


import io.cucumber.java.en.Given;
import qa.framework.utils.Action;
import qa.framework.utils.Reporter;

public class HAUSRA_CommonStepDefs {
	
	@Given("^hasura application is avaialble$")
    public void hausra_application_is_avaialble()  {
     
		Action.openUrl(Action.getTestData("url"));
		Reporter.addEntireScreenCaptured();   
    }

}
