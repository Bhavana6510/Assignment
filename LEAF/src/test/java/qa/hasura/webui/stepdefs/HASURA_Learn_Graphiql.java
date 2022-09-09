package qa.hasura.webui.stepdefs;


import io.cucumber.java.en.And;
import qa.hasura.webui.pages.HASURA_LoginPage;


public class HASURA_Learn_Graphiql {
	
	HASURA_LoginPage objLoginPage = new HASURA_LoginPage();
	
	@And("^user get the authentication id$")
	public void user_get_the_authentication_id() throws InterruptedException {
		Thread.sleep(2000);
		objLoginPage.getTokenId();
	}

}
