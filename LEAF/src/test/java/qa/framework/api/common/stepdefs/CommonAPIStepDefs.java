package qa.framework.api.common.stepdefs;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import qa.framework.assertions.AssertLogger;
import qa.framework.restutils.RestApiUtils;
import qa.framework.utils.Action;
import qa.framework.utils.FileManager;
import qa.framwork.excel.ExcelOperation;
import qa.framwork.excel.ExcelUtils;

public class CommonAPIStepDefs {

	@Given("^user set base uri for \"([^\"]*)\" api$")
	public void user_set_base_uri_for_something_api(String baseUriKey) throws Throwable {

		String baseUrl = Action.getTestData(baseUriKey);

		RestApiUtils.setBaseURL(baseUrl);

	}

	@And("^user set base path for \"([^\"]*)\" endpoint$")
	public void user_set_base_path_for_something_endpoint(String basePathKey) {

		String basePath = Action.getTestData(basePathKey);

		RestApiUtils.setBasePath(basePath);

	}

	@And("^user execute \"([^\"]*)\" request \"([^\"]*)\" json$")
	public void user_execute_something_request_something_json(String methodType, String jsonCondition) {

		RestApiUtils.response = RestApiUtils.executeRequest(methodType, jsonCondition);
	}

	@Then("^user verifies response code as \"([^\"]*)\"$")
	public void user_verifies_response_code_as_something(String statusCode) {

		RestApiUtils.verifyStatusCode(RestApiUtils.response, Long.parseLong(statusCode));
	}

	@And("^user reset rest assured parameters$")
	public void user_reset_rest_assured_parameters() {
		RestApiUtils.reset();
	}

	@And("^user set header \"([^\"]*)\" as \"([^\"]*)\" for current endpoint$")
	public void user_set_header_something_as_something_for_current_endpoint(String headerName, String headerValue) {

		RestApiUtils.setHeader(headerName, headerValue);

	}

	@And("^user set \"([^\"]*)\" parameter \"([^\"]*)\" as \"([^\"]*)\" for current endpoint$")
	public void user_set_something_parameter_something_as_something_for_current_endpoint(String parameterType,
			String parameterName, String parameterValue) {

		RestApiUtils.setAnyParameter(parameterType, parameterName, parameterValue);
	}

	@And("^user set relaxed HTTPS for current endpoint$")
	public void user_set_relaxed_https_for_current_endpoint() {
		RestApiUtils.relaxHTTPSValidation();
	}

	@When("^user form request json \"([^\"]*)\" with test data in excel \"([^\"]*)\" and sheet \"([^\"]*)\" for scneario (.+)$")
	public void user_form_request_json_something_with_test_data_in_excel_something_and_sheet_something_for_scneario(
			String jsonFileName, String excelFileName, String sheetName, String scenario) {

		String userDir = System.getProperty("user.dir");
		String jsonFilePath = FileManager.searchFile(userDir, jsonFileName);
		String excelFilePath = FileManager.searchFile(userDir, excelFileName);

		ExcelUtils objExcel = new ExcelUtils(ExcelOperation.LOAD, excelFilePath);
		XSSFSheet sheet = objExcel.getSheet(sheetName);

		/* getting row index */
		int scenarioColumnIndex = objExcel.getCellIndexByCellValue(sheet, 0, "Scenario");
		int rowIndex = objExcel.getRowIndexByCellValue(sheet, scenarioColumnIndex, scenario);

		RestApiUtils.requestJson = RestApiUtils.putExcelRowDataToJSON(jsonFilePath, excelFilePath, sheetName, rowIndex);

		objExcel.closeWorkBook();

	}

	@And("^user add json \"([^\"]*)\" to request body$")
	public void user_add_json_something_to_request_body(String jsonFileName) {
		String userDir = System.getProperty("user.dir");
		String jsonFilePath = FileManager.searchFile(userDir, jsonFileName);

		RestApiUtils.requestJson = FileManager.readFile(jsonFilePath);
	}

	@And("^user set proxy details \"([^\"]*)\" as host and \"([^\"]*)\" as port$")
	public void user_set_proxy_details_something_as_host_and_something_as_port(String hostname, String port) {
		RestApiUtils.setProxy(hostname, Integer.parseInt(port));

	}
	
	@When("^user provide the graphQl query from \"([^\"]*)\" file$")
	public void user_provide_the_gtaphql_query_from_json_file(String jsonFileName) {

		String userDir = System.getProperty("user.dir");
		String jsonFilePath = FileManager.searchFile(userDir, jsonFileName);
		RestApiUtils.requestJson = FileManager.readFile(jsonFilePath).replace("/n", "").replace("/r", "");

	}

	@And("^user verifies the response content for \"([^\"]*)\" with \"([^\"]*)\"$")
	public void user_verifies_the_response_content_for_something_with_something(String dataPath,
			String expected) {
		
		String actual = RestApiUtils.response.body().jsonPath().getString(dataPath);
		AssertLogger.assertEquals(actual, expected, "Value mismatch !! ");
	}
	
}
