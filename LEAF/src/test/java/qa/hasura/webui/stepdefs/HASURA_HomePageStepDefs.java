package qa.hasura.webui.stepdefs;

import io.cucumber.java.en.When;
import qa.hasura.webui.pages.HASURA_HomePage;

public class HASURA_HomePageStepDefs {
	HASURA_HomePage objHomePage = new HASURA_HomePage();

	@When("^user clicks on login button on home page$")
	public void user_clicks_on_Login_button_on_home_page() {

		objHomePage.clickOnAccountLink();

	}

}
