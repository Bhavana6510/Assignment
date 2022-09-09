package qa.framework.webui.common.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import qa.framework.webui.browsers.WebDriverManager;

public class CommonUIStepDefs {
	
	@Given("^user launches the browser$")
    public void user_launch_the_browser(){
		WebDriverManager.startDriver();
		WebDriverManager.setWebUIFlag(true);
    }

    @And("^user closes the browser$")
    public void user_quit_the_browser(){
    	WebDriverManager.quitDriver();
    	WebDriverManager.setWebUIFlag(false);
    }

}
