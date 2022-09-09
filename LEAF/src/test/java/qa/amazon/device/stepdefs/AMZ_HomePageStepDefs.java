package qa.amazon.device.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import qa.amazon.device.pages.AMZ_HomePage;
import qa.framework.device.DeviceActions;

public class AMZ_HomePageStepDefs {

	AMZ_HomePage objHomePage = new AMZ_HomePage();
	
	@And("^user open amazon application$")
    public void user_open_amazon_application() throws Throwable {
      
		DeviceActions.openUrl("https://www.amazon.in/");
    }
	
	@When("^user search \"([^\"]*)\" on search box$")
    public void user_search_something_on_search_box(String value){
        
		objHomePage.searchProject(value);
    }

}
