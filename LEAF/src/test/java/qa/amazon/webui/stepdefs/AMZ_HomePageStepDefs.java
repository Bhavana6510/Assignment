package qa.amazon.webui.stepdefs;


import io.cucumber.java.en.When;
import qa.amazon.webui.pages.AMZ_HomePage;

public class AMZ_HomePageStepDefs {

	AMZ_HomePage objHomePage = new AMZ_HomePage();
	
	@When("^user clicks on sign button on home page$")
	public void user_clicks_on_sign_button_on_home_page() {
		
		objHomePage.clicOnAccountLink();

	}

}
