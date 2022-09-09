package qa.petstore.api.stepdefs;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;

import com.jayway.jsonpath.JsonPath;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import qa.framework.restutils.RestApiUtils;
import qa.framework.utils.Reporter;

public class PetStoreStepDefs {

	public static String petId="101001";

	
	@And("^user update pet Id in request json$")
	public void user_update_pet_id_in_request_json() {
		int random = (int) (Math.random() * ((900 - 100) + 1)) + 100;
		petId = random + "";

		RestApiUtils.requestJson = RestApiUtils.requestJson.replace("\"@{unique-id}\"", petId);
	}

	@And("^user update existing pet Id in request json$")
	public void user_update_existing_pet_id_in_request_json() {
		RestApiUtils.requestJson = RestApiUtils.requestJson.replace("\"${id}\"", petId);
	}

	@And("^user provide pet ID to retrive employee information$")
	public void user_provide_pet_id_to_retrive_employee_information() {

		RestApiUtils.setPathParameter("petId", petId);
	}

	@Then("^user verify pet name$")
	public void user_verify_pet_name() throws JSONException {
		String expectedPetName = JsonPath.parse(RestApiUtils.requestJson).read("$.name").toString();

		String responseBody = RestApiUtils.getResponseBody(RestApiUtils.response);

		String actualPetName = JsonPath.parse(responseBody).read("$.name").toString();

		Reporter.addStepLog("Pet ID: " + petId);
		Reporter.addStepLog("Expected Pet Name: " + expectedPetName);
		Reporter.addStepLog("Actual Pet Name: " + actualPetName);

		Assert.assertEquals(actualPetName, expectedPetName, "Pet Name are NOT as expected.");

		JSONAssert.assertEquals(RestApiUtils.requestJson, responseBody, JSONCompareMode.LENIENT);

	}

	@And("^verify response message as \"([^\"]*)\"$")
	public void verify_response_message_as_something(String rspMessage) {

		String actualMsg = RestApiUtils.getJsonValue(RestApiUtils.response, "message");

		Reporter.addStepLog("Pet ID: " + petId);
		Reporter.addStepLog("Expected Error Message: " + rspMessage);
		Reporter.addStepLog("Actual Error Message: " + actualMsg);

		Assert.assertEquals(actualMsg, rspMessage, "Error message is not as expected.");
	}

	/* invalid scenario */
	@And("^user update invalid pet Id in request json$")
	public void user_update_invalid_pet_id_in_request_json() {
		int random = (int) (Math.random() * ((900 - 100) + 1)) + 100;
		petId = "0" + random + "";

		RestApiUtils.requestJson = RestApiUtils.requestJson.replace("\"@{unique-id}\"", petId);
	}

}
