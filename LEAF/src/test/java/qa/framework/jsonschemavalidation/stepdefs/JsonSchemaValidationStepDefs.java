package qa.framework.jsonschemavalidation.stepdefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.everit.json.schema.ValidationException;
import org.json.JSONObject;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import qa.framework.jsonschemavalidation.JsonSchemaValidation;
import qa.framework.jsonschemavalidation.SchemaValidationModes;
import qa.framework.restutils.RestApiUtils;
import qa.framework.utils.FileManager;
import qa.framework.utils.Reporter;

public class JsonSchemaValidationStepDefs extends qa.framework.jsonschemavalidation.JsonSchemaValidation {

	String response;
	String swaggerJsonAsString;
	JSONObject definition;

	private static boolean ignoreNull = false;

	/*STEP 1: Reporting the end point JSON being tested*/
	@Given("^verification of json schema for (.+) endpoint$")
	public void verifying_json_schema_for_endpoint(String endpoint) { 
	
		/*Reporting Step*/
		Reporter.addStepLog("<strong>End Point: </strong>"+endpoint);
	
	}
	
	/*STEP 2: Setting *STATIC JSON Response which is to be validated*/
	@Given("^user have json response (.*) available$")
	public void user_has_response_json_something_available(String responseJsonFileName) {

		/* generalizing "args" and <args> from feature file */
		responseJsonFileName = responseJsonFileName.replace("\"", "");

		String userDir = System.getProperty("user.dir");

		String filepath = FileManager.searchFile(userDir, responseJsonFileName);
		response = FileManager.readFile(filepath);

		/*Reporting Step*/
		Reporter.addFile(response, "json", "Click on link icon to open Response !!");
		
	}
	
	/*STEP 2: Setting *LIVE JSON Response which is to be validated*/
	@When("^user set api response as input for json schema validation$")
	public void user_set_api_response_as_input_for_json_schema_validation() {
		
		response = RestApiUtils.response.body().asString();
		
	}

	/*STEP 3: Converting Swagger doc. (YAML / JSON) into string*/
	@And("^user have swagger yaml (.*) doc available$")
	public void user_have_swagger_yaml_something_available(String swaggerYmlFileName) throws IOException {

		/* "generalizing "args" and sargs> from feature file" */
		swaggerYmlFileName = swaggerYmlFileName.replace("\"", "");
		swaggerJsonAsString = convertSwaggerToString(swaggerYmlFileName);

	}

	/*STEP 4: Converting Swagger doc. to JSONObject i.e. definition*/
	@And("^user convert swagger yaml to json schema for object to validate (.+) and of object type (.+)$")
    public void user_convert_swagger_yaml_to_json_schema_for_object_to_validate_and_of_object_type(String objectToValidate, String objectType){
      objectToValidate = objectToValidate.replace("\"", "");
      objectType=objectType.replace("\"", "");
      
      JsonSchemaValidation.objectType=objectType;
      definition = createJsonSchema(swaggerJsonAsString, objectToValidate, objectType);
    }
	
	/*STEP 5: Add validation options*/
	@And("^user add validation options \"([^\"]*)\" to existing json schema$")
	public void user_add_validation_options_something_to_existing_json_schema(String options) {

		List<String> optionList = Arrays.asList(options.split(","));
		HashSet<SchemaValidationModes> schemaValidationModes = new HashSet<SchemaValidationModes>();

		if (!optionList.isEmpty()) {
			if (options.contains("ALL_PROPERTIES REQUIRED")) {
				schemaValidationModes.add(SchemaValidationModes.ALL_PROPERTIES_REQUIRED);
			}

			if (options.contains("NO_ADDITIONAL_PROPERTIES")) {
				schemaValidationModes.add(SchemaValidationModes.NO_ADDITIONAL_PROPERTIES);
			}
			if (options.contains("IGNORE_NULL_VALUES")) {
				schemaValidationModes.add(SchemaValidationModes.IGNORE_NULL_VALUES);
				ignoreNull = true;
			}
			if(options.contains("FAIL_EMPTY_VALUES")) {
				schemaValidationModes.add(SchemaValidationModes.FAIL_EMPTY_ARRAYS);
			}
		}

		definition = addSchemaValidationOptions(definition, schemaValidationModes);

		/*Reporting Step*/
		Reporter.addFile(definition.toString(), "json", "Click on link icon to open JSON Schema !!");

	}

	/*STEP 6: Validation and Reporting*/
	@Then("^user perform json validation and results are logged$")
	public void user_perform_json_validation_and_results_are_logged() {

		try {
			
			validateSchema(definition, response);
			
		} catch (ValidationException validationException) {
			
			reportJsonSchemaValidationError(validationException);
			
		} catch (Exception e) {
			throw new AssertionError(e);
		}
		Reporter.addStepLog("<strong>JSON Schema Validation Passed !!</strong>");
	}

	/*Validation and Reporting method*/
	public static void reportJsonSchemaValidationError(ValidationException validationException) {

		List<String> missingRequiredProperties = new ArrayList<String>();
		List<String> missingDependencies = new ArrayList<String>();
		List<String> foundAdditionalProperties = new ArrayList<String>();
		List<String> typeMisMatch = new ArrayList<String>();
		List<String> unknown = new ArrayList<String>();
		List<String> allMessages = validationException.getAllMessages();

		for (String message : allMessages) {
			System.out.println("ERROR: " + message);

			if (message.contains("not found")) {
				//String propertyName = message.substring((message.indexOf("[") + 1), message.indexOf("]"));
				missingRequiredProperties.add(message);

			} else if (message.contains("is required")) {
				//String propertyName = message.substring((message.indexOf("[") + 1), message.indexOf("]"));
				missingDependencies.add(message);

			} else if (message.contains("extraneous key")) {
				//String propertyName = message.substring((message.indexOf("[") + 1), message.indexOf("]"));
				foundAdditionalProperties.add(message);

			} else if (message.contains("expected") && !message.contains("expected minium item count")) {

				if (ignoreNull) {

					/* "message will only get added if does not contain 'Null' */
					if (!message.contains("Null")) {
						typeMisMatch.add(message);
					}
				} else {
					typeMisMatch.add(message);
				}

			} else if (ignoreNull && message.contains("null is not a valid enum value")) {
				/*ENUM null value is ignore if ignoreNull is true*/
			}
			else {

				
				if(message.contains("expected minimum item count: 1, found: 0")) {
					message= message.replace("expected minimum item count: 1, found: 0","Item is an empty Array[].");
					unknown.add(message);
				}else {
					unknown.add(message);
				}
				
				
			}
		}

		/* reverting to default ignore status */
		ignoreNull = false;

		/* Logging Missing Required Properties */
		if (missingRequiredProperties.size() > 0) {

			if (missingRequiredProperties.size() > 1) {
				Reporter.addStepLog("<strong>Missing Required Properties:</strong>");
			} else {
				Reporter.addStepLog("<strong>Missing Required Property:</strong>");
			}
			missingRequiredProperties.forEach(property -> Reporter.addStepLog(property));
		}

		/* Logging Missing Dependent Properties */
		if (missingDependencies.size() > 0) {

			if (missingDependencies.size() > 1) {
				Reporter.addStepLog("<strong>Missing Dependent Properties:</strong>");
			} else {
				Reporter.addStepLog("<strong>Missing Dependent Property:</strong>");
			}

			missingDependencies.forEach(property -> Reporter.addStepLog(property));
		}

		/* Logging Found Addition Properties */
		if (foundAdditionalProperties.size() > 0) {
			if (foundAdditionalProperties.size() > 1) {
				Reporter.addStepLog("<strong>Found Additional Properties:</strong>");
			} else {
				Reporter.addStepLog("<strong>Found Additional Property:</strong>");
			}
			foundAdditionalProperties.forEach(property -> Reporter.addStepLog(property));
		}

		/* Logging Type MisMatch */
		if (typeMisMatch.size() > 0) {
			Reporter.addStepLog("<strong>Type MisMatch:</strong>");
			typeMisMatch.forEach(property -> Reporter.addStepLog(property));

		}
		/* Logging unknown message */
		if (unknown.size() > 0) {
			Reporter.addStepLog("<strong>Other Errors:</strong>");
			unknown.forEach(property -> Reporter.addStepLog(property));
		}
		/* marking scenario as failed */

		if (missingRequiredProperties.size() > 0 || missingDependencies.size() > 0
				|| foundAdditionalProperties.size() > 0 || typeMisMatch.size() > 0 || unknown.size() > 0) {

			throw new AssertionError();
		}
	}


	

}
