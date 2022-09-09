package qa.framework.jsonschemavalidation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.JsonPath;

import qa.framework.utils.FileManager;

public class JsonSchemaValidation {

	public static String objectToValidate;
	public static String objectType;

	public static Object getValue(String json, String query) {
		return JsonPath.read(json, query);
	}

	/* Convert YAML file to json */
	public static String convertYamlToJson(String swaggerFileName) throws IOException {

		String userDir = System.getProperty("user.dir");
		String filePath = FileManager.searchFile(userDir, swaggerFileName);

		String yamlStr = FileManager.readFile(filePath);
		ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
		Object obj = yamlReader.readValue(yamlStr, Object.class);
		ObjectMapper jsonWriter = new ObjectMapper();
		return jsonWriter.writeValueAsString(obj);

	}

	/* converting swagger doc to string */
	public static String convertSwaggerToString(String swaggerFileName) throws IOException {
		String swaggerJsonAsString;
		String userDir = System.getProperty("user.dir");
		String filePath = FileManager.searchFile(userDir, swaggerFileName);

		if (swaggerFileName.contains(".yaml") || swaggerFileName.contains(".yml")) {
			swaggerJsonAsString = convertYamlToJson(swaggerFileName);
		} else {

			swaggerJsonAsString = FileManager.readFile(filePath);
		}
		return swaggerJsonAsString;
	}

	public static JSONObject createJsonSchema(String swaggerJsonAsString, String objectToValidate, String objectType) {

		JsonSchemaValidation.objectToValidate = objectToValidate;
		JsonSchemaValidation.objectType = objectType;

		JSONObject definitions = new JSONObject();
		JSONObject swaggerObject = new JSONObject(swaggerJsonAsString);

		if (swaggerObject.has("openapi")) {
			swaggerObject = new JSONObject(swaggerJsonAsString.replaceAll("components/schemas", "definitions"));
			definitions = swaggerObject.getJSONObject("components").getJSONObject("schemas");

		} else {
			definitions = swaggerObject.getJSONObject("definitions");
		}
		JSONObject targetObject = definitions.getJSONObject(objectToValidate);

		if (!(definitions.getJSONObject(objectToValidate).has("allof"))) {

			if ((targetObject.has("items")) && !(targetObject.has("type"))) {
				targetObject.put("type", "array");
			}

			String objectTypeInDefinition = targetObject.getString("type");

			if ((!objectTypeInDefinition.equals(objectType)) && objectType.equals("array")) {
				JSONObject properties = targetObject.getJSONObject("properties");
				definitions.getJSONObject(objectToValidate).put("type", objectType);
				definitions.getJSONObject(objectToValidate).remove("properties");
				definitions.getJSONObject(objectToValidate).put("items",
						new JSONObject().put("properties", properties).put("type", "object"));
			} else if ((!objectTypeInDefinition.equals(objectType)) && objectType.equals("object")) {
				JSONObject properties = targetObject.getJSONObject("items").getJSONObject("properties");
				definitions.getJSONObject(objectToValidate).put("type", objectType);
				definitions.getJSONObject(objectToValidate).remove("properties");
				definitions.getJSONObject(objectToValidate).put("properties", properties);
			}
		}
		return definitions;
	}

//	public static JSONObject createJsonSchema(String jsonSchema, Set<SchemaValidationModes> schemaValidationModes) {
//		jsonObjectSchema = new JSONObject(jsonSchema);
//
//		if (!(schemaValidationModes.isEmpty())) {
//			if (schemaValidationModes.contains(SchemaValidationModes.NO_ADDITIONAL_PROPERTIES)) {
//				setNoAdditionalPropertiesToFalseInJsonSchema(jsonObjectSchema);
//			}
//			if (schemaValidationModes.contains(SchemaValidationModes.IGNORE_NULL_VALUES)) {
//				setIgnoreNullValuesInJsonSchema(jsonObjectSchema);
//			}
//
//			if (schemaValidationModes.contains(SchemaValidationModes.ALL_PROPERTIES_REQUIRED)) {
//				setAllPropertiesRequiredInJsonSchema(jsonObjectSchema);
//			}
//		}
//		return jsonObjectSchema;
//
//	}
//
//	public static JSONObject createJsonSchema(String jsonSchemaTemplate, List<String> keysRequired) {
//		jsonObjectSchema = new JSONObject(jsonSchemaTemplate);
//		setSpecificPropertiesRequired(jsonObjectSchema, keysRequired);
//		return jsonObjectSchema;
//	}
//
//	public static JSONObject createJsonSchema(String jsonSchemaTemplate, Map<String, Set<String>> dependencies) {
//
//		jsonObjectSchema = new JSONObject(jsonSchemaTemplate);
//		setDependenciesInJsonSchema(jsonObjectSchema, dependencies);
//		return jsonObjectSchema;
//	}

	/**
	 * As Schema Validation Options
	 * 
	 * @param definitions
	 * @param schemaValidation Modes
	 * @return
	 **/

	public static JSONObject addSchemaValidationOptions(JSONObject definitions,
			Set<SchemaValidationModes> schemaValidationModes) {

		if (!(schemaValidationModes.isEmpty())) {
			for (String keyStr : definitions.keySet()) {

				combineAllOfInJsonSchema(definitions, keyStr);
				correctExclusiveMinimumMaximum(definitions.getJSONObject(keyStr));

				if (schemaValidationModes.contains(SchemaValidationModes.NO_ADDITIONAL_PROPERTIES)) {
					setNoAdditionalPropertiesToFalseInJsonSchema(definitions.getJSONObject(keyStr));
				}
				if (schemaValidationModes.contains(SchemaValidationModes.IGNORE_NULL_VALUES)) {
					setIgnoreNullValuesInJsonSchema(definitions.getJSONObject(keyStr));
				}
				if (schemaValidationModes.contains(SchemaValidationModes.ALL_PROPERTIES_REQUIRED)) {
					setAllPropertiesRequiredInJsonSchema(definitions.getJSONObject(keyStr));
				}
				if (schemaValidationModes.contains(SchemaValidationModes.FAIL_EMPTY_ARRAYS)) {
					failOnEmptyArrayInJsonSchema(definitions.getJSONObject(keyStr));
				}
			}
		}

		JSONObject schema = new JSONObject().put("$ref", "#/definitions/" + objectToValidate).put("definitions",
				definitions);

		return schema;

	}

	/**
	 * @param jsonObjectSchema
	 * @param jsonToValidate
	 * @throws IOException
	 **/

	public static void validateSchema(JSONObject jsonObjectSchema, String jsonToValidate) throws Exception {

		Schema schema = SchemaLoader.builder().schemaJson(jsonObjectSchema).draftV7Support().build().load().build();

		if (objectType.equals("object")) {

			JSONObject actualReponse = new JSONObject(jsonToValidate);
			schema.validate(actualReponse);

		} else if (objectType.equals("array")) {

			JSONArray actualReponse = new JSONArray(jsonToValidate);
			schema.validate(actualReponse);

		} else {
			throw new IOException("Object type is expected to be \"object\" or \"array\"");
		}
	}

	/********************************/
	/** SCHEMA MODIFICATION CODE **/
	/********************************/

	private static void failOnEmptyArrayInJsonSchema(JSONObject jsonObj) {
		if (jsonObj.has("properties")) {
			JSONObject properties = jsonObj.getJSONObject("properties");

			for (String keyStr : properties.keySet()) {
				failOnEmptyArrayInJsonSchema(properties.getJSONObject(keyStr));
			}
		}

		if (jsonObj.has("items")) {
			jsonObj.put("minItems", 1);
			JSONObject items = jsonObj.getJSONObject("items");
			failOnEmptyArrayInJsonSchema(items);

		}
	}

	/**
	 * No additional Properties
	 * 
	 * @param definations
	 **/
	private static void setNoAdditionalPropertiesToFalseInJsonSchema(JSONObject definitions) {

		if (!(definitions.has("additional Properties"))) {
			definitions.put("additional Properties", false);
		}

		if (definitions.has("additionalProperties")) {
			if (!definitions.get("additionalProperties").toString().equals("false")
					|| definitions.get("additionalProperties").toString().equals("true")) {
				definitions.put("additonalProperties", false);
			}
		}

		if (definitions.has("properties")) {
			JSONObject properties = definitions.getJSONObject("properties");
			for (String keyStr : properties.keySet()) {
				setNoAdditionalPropertiesToFalseInJsonSchema(properties.getJSONObject(keyStr));
			}

		} else if (definitions.has("items")) {

			JSONObject items = definitions.getJSONObject("items");
			setNoAdditionalPropertiesToFalseInJsonSchema(items);
		}
	}

	/**
	 * Ignore Null value
	 * 
	 * @param : definations
	 */
	private static void setIgnoreNullValuesInJsonSchema(JSONObject definitions) {

		if (definitions.has("type")) {

			String currentType = definitions.getString("type");
			definitions.remove("type");

			JSONArray anyOf = new JSONArray().put(new JSONObject().put("type", currentType))
					.put(new JSONObject().put("type", "null"));
			definitions.put("any0f", anyOf);

		}
		if (definitions.has("properties")) {
			JSONObject properties = definitions.getJSONObject("properties");
			for (String keyStr : properties.keySet()) {
				setIgnoreNullValuesInJsonSchema(properties.getJSONObject(keyStr));
			}
		} else if (definitions.has("items")) {
			JSONObject items = definitions.getJSONObject("items");
			setIgnoreNullValuesInJsonSchema(items);
		}
		
		if(definitions.has("allOf")) {
			JSONArray allOf = definitions.getJSONArray("allOf");
			for(int i=0;i<allOf.length();i++) {
				JSONObject object = allOf.getJSONObject(i);
				setIgnoreNullValuesInJsonSchema(object);
			}
		}
		
		if(definitions.has("enum")) {
			JSONArray enumAr = definitions.getJSONArray("enum");
			enumAr.put("null");
		}
	}

	/**
	 * All properties required
	 * 
	 * @param : defination
	 */
	private static void setAllPropertiesRequiredInJsonSchema(JSONObject definitions) {

		JSONArray requiredList = new JSONArray();
		if (definitions.has("required")) {
			definitions.remove("required");
		}

		if (definitions.has("properties")) {
			JSONObject properties = definitions.getJSONObject("properties");

			for (String keyStr : properties.keySet()) {
				requiredList.put(keyStr);
				setAllPropertiesRequiredInJsonSchema(properties.getJSONObject(keyStr));
			}
			definitions.put("required", requiredList);
		}
		if (definitions.has("items")) {
			JSONObject items = definitions.getJSONObject("items");
			setAllPropertiesRequiredInJsonSchema(items);
		}
	}
	
	/* Added to handle allOf case of json schema*/
	private static void combineAllOfInJsonSchema(JSONObject definition, String keyModel) {
		if(definition.getJSONObject(keyModel).has("allOf")) {
			ArrayList<String> refStringList = new ArrayList<String>();
			
			JSONObject baseObject = new JSONObject();
			
			for(int i=0;i<definition.getJSONObject(keyModel).getJSONArray("allOf").length();i++) {
				JSONArray allOfArray = definition.getJSONObject(keyModel).getJSONArray("allOf");
				JSONObject allOfObject = allOfArray.getJSONObject(i);
				
				if(allOfObject.has("$ref")) {
					refStringList.add(allOfObject.getString("ref").split("#/definitions/")[1]);
				}else if(allOfObject.has("properties")) {
					baseObject = allOfObject;
				}
				
			}
			
			if(definition.getJSONObject(keyModel).has("properties")) {
				definition.getJSONObject(keyModel).remove("allof");
				baseObject = definition.getJSONObject(keyModel);
			}
			
			definition.getJSONObject(keyModel).remove(keyModel);
			definition.put(keyModel, baseObject);
			
			JSONObject mergedProp;
			if(!baseObject.isEmpty()) {
				JSONObject baseProp = baseObject.getJSONObject("properties");
				mergedProp = new JSONObject(baseProp,JSONObject.getNames(baseProp));
			}else {
				mergedProp = new JSONObject();
			}
			
			for (int i=0; i< refStringList.size(); i++) {
				if(definition.getJSONObject(refStringList.get(i)).has("properties")) {
					
					JSONObject refProp = definition.getJSONObject(refStringList.get(i)).getJSONObject("properties");
					
					for(String key: JSONObject.getNames(refProp)) {
						if(!mergedProp.has(key)) {
							mergedProp.put(key, refProp.get(key));
						}
					}
					
				}else {
					combineAllOfInJsonSchema(definition, refStringList.get(i));
				}
			}
			
			definition.getJSONObject(keyModel).put("properties", mergedProp);
		}
	}
	
	/*for converting draftv4 to draft v6/v7*/
	private static void correctExclusiveMinimumMaximum(JSONObject jsonObj) {
		if(jsonObj.has("exclusiveMinimum")&& jsonObj.has("minimum")) {
			if(jsonObj.getBoolean("exclusiveMinimum")) {
				int min = jsonObj.getInt("minimum");
				jsonObj.remove("minimum");
				jsonObj.put("excusiveMinimum", min);
			}
		}else if(jsonObj.has("exclusiveMinimum")) {
			Object exMin = jsonObj.get("exclusiveMinimum");
			if(exMin instanceof Boolean && !jsonObj.has("minimum")) {
				jsonObj.remove("exclusiveMinimum");
			}
			
		}
		
		if(jsonObj.has("exclusiveMaximum") && jsonObj.has("maximum")) {
			if(jsonObj.getBoolean("exclusiveMaximum")) {
				int max = jsonObj.getInt("maximum");
				jsonObj.remove("maximum");
				jsonObj.put("exclusiveMaximum", max);
				
			}else {
				jsonObj.remove("exclusiveMaximum");
			}
		}else if (jsonObj.has("exclusiveMaximum")) {
			Object exMax = jsonObj.get("exclusiveMaximum");
			if(exMax instanceof Boolean && !jsonObj.has("maximum")) {
				jsonObj.remove("exclusiveMaximum");
			}
		}
		
		if(jsonObj.has("properties")) {
			JSONObject properties = jsonObj.getJSONObject("properties");
			for(String keyStr: properties.keySet()) {
				correctExclusiveMinimumMaximum(properties.getJSONObject(keyStr));
			}
		}
		if(jsonObj.has("items")) {
			JSONObject items = jsonObj.getJSONObject("items");
			correctExclusiveMinimumMaximum(items);
		}
	}
	

	/**
	 * Adds properties dependent on one or more other properties e.g. *
	 * "dependencies": {"credit card": ["billing address"]}
	 * 
	 * @param jsonObj
	 * @param dependencies: Map<Strin, Set<String>>
	 */

	private static void setDependenciesInJsonSchema(JSONObject jsonObj, Map<String, Set<String>> dependencies) {

		JSONObject jsonDependencies = new JSONObject();

		for (Entry<String, Set<String>> entry : dependencies.entrySet()) {
			String property = entry.getKey();
			Set<String> setDependencies = entry.getValue();

			JSONArray jsonArray = new JSONArray();
			for (String key : setDependencies) {
				jsonArray.put(key);
			}

			jsonDependencies.put(property, jsonArray);
		}

		JSONObject items = jsonObj.getJSONObject("items");
		boolean hasRequired = items.has("dependencies");
		if (hasRequired) {
			items.remove("dependencies");
		}
		items.put("dependencies", jsonDependencies);
	}

	/**
	 * Adds Specific properties required in JSON Schema e.g. "required":
	 * ["name","email"]
	 * 
	 * @param jsonObj
	 * @param keysRequired: List<String>
	 */
	private static void setSpecificPropertiesRequired(JSONObject jsonObj, List<String> keysRequired) {

		JSONArray jsonArray = new JSONArray();
		for (String key : keysRequired) {
			jsonArray.put(key);
		}
		JSONObject items = jsonObj.getJSONObject("items");
		boolean hasRequired = items.has("requried");
		if (hasRequired) {
			items.remove("requried");
		}
		items.put("required", jsonArray);
	}

	@Test
	public void testYmaltoJson() throws Exception {

		try {
			String strJsonObjectSchema = convertYamlToJson("Account_API.yaml");
			JSONObject jsonObjectSchema;

			String jsonToValidate = FileManager.readFile("relatedAccounts-post-response.json");
			objectType = "array";

			Set<SchemaValidationModes> schemaValidationModes = new HashSet<SchemaValidationModes>();
			schemaValidationModes.add(SchemaValidationModes.IGNORE_NULL_VALUES);
			schemaValidationModes.add(SchemaValidationModes.NO_ADDITIONAL_PROPERTIES);
			schemaValidationModes.add(SchemaValidationModes.ALL_PROPERTIES_REQUIRED);

			JSONObject definition = createJsonSchema(strJsonObjectSchema, "AccountsCodeCommand", "array");

			jsonObjectSchema = addSchemaValidationOptions(definition, schemaValidationModes);

			validateSchema(jsonObjectSchema, jsonToValidate);

		} catch (ValidationException validation) {
			List<String> allMessages = validation.getAllMessages();
			for (String error : allMessages) {
				System.out.println(error);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}