package qa.framework.desktop.common.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import qa.framework.desktop.DesktopDriverManager;
import qa.framework.utils.PropertyFileUtils;

public class CommonDesktopStepDefs {

	@Given("^user start \"([^\"]*)\" application$")
    public void user_start_something_application(String appName)  {
        
		PropertyFileUtils appPro = new PropertyFileUtils("application.properties");
		String exePath = appPro.getProperty(appName);
		
		DesktopDriverManager.startApplication(exePath);
		
		
    }

    @And("^user close given application$")
    public void user_close_given_application()  {
        
    	DesktopDriverManager.stopApplication();
    	
    }
	
}
