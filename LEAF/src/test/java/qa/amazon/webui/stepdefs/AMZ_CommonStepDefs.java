package qa.amazon.webui.stepdefs;


import io.cucumber.java.en.Given;
import qa.framework.utils.Action;
import qa.framework.utils.Reporter;

public class AMZ_CommonStepDefs {
	
	@Given("^amazon application is avaialble$")
    public void amazon_application_is_avaialble()  {
     
		Action.openUrl(Action.getTestData("url"));
		Reporter.addEntireScreenCaptured();   
    }

}
