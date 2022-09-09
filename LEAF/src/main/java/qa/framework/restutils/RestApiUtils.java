package qa.framework.restutils;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookie.Builder;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import qa.framework.utils.ExceptionHandler;
import qa.framework.utils.LoggerHelper;
import qa.framework.utils.Reporter;
import qa.framwork.excel.ExcelOperation;
import qa.framwork.excel.ExcelUtils;

public class RestApiUtils {

	private static RequestSpecification request;
	public static String requestJson;
	public static Response response;
	
	public static String path;
	public static RequestSpecification requestSpecification = given();
	public static Logger log = LoggerHelper.getLogger(RestApiUtils.class);

	public static int getStatusCode(Response response) {
		int statusCode = response.getStatusCode();
		return statusCode;
	}

	/**
	 * Verification of status code
	 * 
	 * @author 10650956 
	 * @param response
	 * @param status
	 */
	public static void verifyStatusCode(Response response, long status) {
		log.debug("Verifying status code" + status);

		Reporter.addStepLog("<strong>Actual Status   :</strong>" + response.getStatusCode());
		Reporter.addStepLog("<strong>Expected Status :</strong>" + status);

		Assert.assertEquals(getStatusCode(response), status,
				"Status code verification failed.Received Status code:" + getStatusCode(response));

	}

	public static String getStatusMessage(Response response) {
		String statusMessage = response.getStatusLine();
		return statusMessage;
	}

	public static void verifyStatusMessage(Response response, Object status) {
		log.debug("Verifying status message" + response);

		Assert.assertEquals(getStatusMessage(response), status,
				"Status message verification failed.Received Status message:" + getStatusMessage(response));
	}

	/**
	 * This method takes the header from given Excel File and create the map with
	 * the value from row index(valueRowIndex)
	 * 
	 * @author BathriYO
	 * @param excelFilePath
	 * @param sheet
	 * @param valueRowIndex
	 * @return HashMap<String,Object>
	 */
	public static HashMap<String, Object> createMapFromExcelHeaders(String excelFilePath, String sheetName,
			int valueRowIndex) {
		HashMap<String, Object> map = null;
		ExcelUtils objExcel = null;
		try {

			objExcel = new ExcelUtils(ExcelOperation.LOAD, excelFilePath);
			XSSFSheet sheet = objExcel.getSheet(sheetName);

			int cellCount = objExcel.getCellCount(sheet, 0);

			map = new HashMap<String, Object>();
			// putting headers in map with blank value
			for (int index = 0; index < cellCount; index++) {
				String header = objExcel.getStringCellData(sheet, 0, index).trim();
				// adding header only if header length is greater than 0
				if (header.length() > 0) {
					header = "${" + header+"}";
					Object value = objExcel.getCellData(sheet, valueRowIndex, index);
					map.put(header, value);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			objExcel.closeWorkBook();
		}
		return map;
	}

	/**
	 * This method takes json file path and returns json in string format
	 * 
	 * @author BathriYO
	 * @param jsonFilePath
	 * @return String
	 */
	public static String convertJsonToSTring(String jsonFilePath) {
		String strJson = "undefined";
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFilePath));
			StringBuffer stringBuffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line).append("\n");
			}
			strJson = strJson.replace("undefined", stringBuffer);
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strJson;
	}

	/**
	 * public static method to create a json with values provided in map
	 * 
	 * Note:It is found that JSONParser while parsing json String changes the order
	 * of nodes which is causing script to fail thus below is method is introduce
	 * where returns String as it is(no change of order).
	 * 
	 * @author BathriYO
	 * @param jsonFilePath
	 * @param map
	 * @return String
	 */
	public static String createJSON(String jsonFilePath, Map<String, Object> map) {
		/* converting json into String */
		String strJson = convertJsonToSTring(jsonFilePath);
		Set<String> keySet = map.keySet();
		Iterator<String> iterate = keySet.iterator();
		/* creating json with excel value */
		while (iterate.hasNext()) {
			String key = iterate.next();
			Object value = map.get(key);

			if (value instanceof Double || value instanceof Long || value instanceof Integer) {
				/* logic to remove trailing zeroes */
				if ((double) value == (long) (double) value)
					value = String.format("%d", (long) (double) value);
				else
					value = String.format("%s", value);
				strJson = strJson.replace("\"" + key + "\"", (value) + "");

			} else if (key.contains("${int_")) {
				strJson = strJson.replace("\"" + key + "\"", (value) + "");
			} else {
				strJson = strJson.replace(key, value + "");
			}
		}
		/* converting String null into null null in json string */
		strJson = strJson.replace("\"null\"", "null");
		/* converting String true into boolean true in json string */
		strJson = strJson.replace("\"true\"", "true");
		/* converting String false into boolean false in json string */
		strJson = strJson.replace("\"false\"", "false");
		return strJson;
	}

	/**
	 * provided single method to put excel row data into given json file
	 * 
	 * @author BathriYO
	 * @param jsonFilePath
	 * @param excelFilePath
	 * @param excelSheet
	 * @param excelRow
	 * @return String
	 */
	public static String putExcelRowDataToJSON(String jsonFilePath, String excelFilePath, String excelSheet,
			int excelRow) {
		return createJSON(jsonFilePath, createMapFromExcelHeaders(excelFilePath, excelSheet, excelRow));
	}

	/**
	 * Use to generate json array with excel data
	 * 
	 * @author BathriYO
	 * @param jsonFilePath
	 * @param excelFilePath
	 * @param sheetName
	 * @param scenario
	 * @param columnName
	 * @return String:json array
	 */
	public static String generateJsonArray(String jsonFilePath, String excelFilePath, String sheetName, String scenario,
			String columnName) {
		int rowIndex = 1;
		String finalJsonArray = "";
		List<Object> listScenarios;

		ExcelUtils objExcel = new ExcelUtils(ExcelOperation.LOAD, excelFilePath);
		XSSFSheet sheet = objExcel.getSheet(sheetName);

		listScenarios = objExcel.getColumnData(sheet, 0, columnName);

		for (Object value : listScenarios) {
			if (((String) value).trim().equalsIgnoreCase(scenario)) {
				String jsonArray = putExcelRowDataToJSON(jsonFilePath, excelFilePath, sheetName, rowIndex);
				finalJsonArray = finalJsonArray + jsonArray + ",";
			}
			rowIndex += 1;
		}

		objExcel.closeWorkBook();
		/* removing extra , from last */
		finalJsonArray = finalJsonArray.substring(0, finalJsonArray.length() - 1);
		return finalJsonArray;
	}

	/**
	 * Set base URI
	 * 
	 * @author 10650956 
	 * @param baseurl
	 */
	public static void setBaseURL(String baseurl) {

		RestAssured.baseURI = baseurl;

		Reporter.addStepLog("<strong> Base URI set as: </strong>" + baseurl);

	}

	/**
	 * Set base path
	 * 
	 * @author 10650956 
	 * @param basePath
	 */
	public static void setBasePath(String basePath) {

		RestAssured.basePath = basePath;

		request = RestAssured.given();

		Reporter.addStepLog("<strong> Base Path set as: </strong>" + basePath);
	}

	/**
	 * Set Path parameters takes Map as input
	 * 
	 * @author 10650956 
	 * @param parameters
	 */
	public static void setPathParameters(Map<String, String> pathParamMap) {
		request.pathParams(pathParamMap);
	}

	/**
	 * Relaxing HTTPS validation
	 * 
	 * @author 10650956 
	 */
	public static void relaxHTTPSValidation() {
		request.relaxedHTTPSValidation();
	}

	/**
	 * Set Path parameter takes sting parameter name and parameter value
	 * 
	 * @author 10650956 
	 * @param parameterName
	 * @param parameterValue
	 */
	public static void setPathParameter(String parameterName, String parameterValue) {
		request.pathParam(parameterName, parameterValue);
	}

	/**
	 * Set Query parameters takes Map as input
	 * 
	 * @author 10650956 
	 * @param queryParamMap
	 */
	public static void setQueryParameters(Map<String, String> queryParamMap) {
		request.queryParams(queryParamMap);
	}

	/**
	 * Set Query parameter takes sting parameter name and parameter value
	 * 
	 * @author 10650956 
	 * @param parameterName
	 * @param parameterValue
	 */
	public static void setQueryParameter(String parameterName, String parameterValue) {
		request.queryParam(parameterName, parameterValue);
	}

	/**
	 * Set Form parameters takes Map as input
	 * 
	 * @author 10650956 
	 * @param formParamMap
	 */
	public static void setFormParameters(Map<String, String> formParamMap) {
		request.formParams(formParamMap);
	}

	/**
	 * Set Form parameter takes sting parameter name and parameter value
	 * 
	 * @author 10650956 
	 * @param parameterName
	 * @param parameterValue
	 */
	public static void setFormParameter(String parameterName, String parameterValue) {
		request.formParam(parameterName, parameterValue);
	}

	/**
	 * Setting Cookie to Rest request
	 * 
	 * @author 10650956
	 * @param name       : String
	 * @param value      : String
	 * @param domain     : String
	 * @param path       : String
	 * @param expiryDate : Date
	 * @param isSecure   : boolean
	 */
	public static void setCookie(String name, String value, String domain, String path, Date expiryDate,
			boolean isSecure) {

		Cookie cookie;
		Builder builder = new Cookie.Builder(name, value);

		if (domain != null) {
			builder.setDomain(domain);
		}

		if (path != null) {
			builder.setPath(path);
		}

		if (expiryDate != null) {
			builder.setExpiryDate(expiryDate);
		}

		builder.setSecured(isSecure);

		cookie = builder.build();

		request.cookie(cookie);

		Reporter.addStepLog("Cookie <strong>" + name + "</strong>" + " is set.");
	}

	/**
	 * Setting browser cookies to rest call
	 * 
	 * @author 10650956
	 * @param browserCookies : org.openqa.selenium.Cookie
	 */
	public static void setCookies(Set<org.openqa.selenium.Cookie> browserCookies) {

		Iterator<org.openqa.selenium.Cookie> cookieSet = browserCookies.iterator();

		while (cookieSet.hasNext()) {

			org.openqa.selenium.Cookie cookie = cookieSet.next();

			String name = cookie.getName();
			String value = cookie.getValue();
			String domain = cookie.getDomain();
			String path = cookie.getPath();
			Date expiry = cookie.getExpiry();
			boolean secure = cookie.isSecure();

			setCookie(name, value, domain, path, expiry, secure);
		}

	}

	/**
	 * Set Relaxed HTTP Name: Basic Auth: Hexacode authNameValue: Basic
	 * HEXOQANDUZSAEE
	 * 
	 * @author 10650956 
	 * @param authNameValue
	 */
	public static void setAuth(String authNameValue) {
		request.relaxedHTTPSValidation().header("Authorization", authNameValue);

	}

	/**
	 * Set Multiple headers
	 * 
	 * @author 10650956 
	 * @param headerParamMap
	 */
	public static void setHeaders(Map<String, String> headerParamMap) {

		request.headers(headerParamMap);
	}

	/**
	 * Set Header
	 * 
	 * @author 10650956 
	 * @param headerName
	 * @param headerValue
	 */
	public static void setHeader(String headerName, String headerValue) {

		request.header(headerName, headerValue);

		Reporter.addStepLog("<strong>Header Name  : </strong>" + headerName);
		Reporter.addStepLog("<strong>Header Value : </strong>" + headerValue);
	}

	/**
	 * Set Json as request request body
	 * 
	 * @author 10650956 
	 * @param json
	 */
	public static void setRequestBody(String json) {
		request.body(json);
	}

	/**
	 * Set PATH/QUERY/FORM parameter
	 * 
	 * @author 10650956 
	 * @param parameterType: Type of parameter PATH, QUERY or FORM
	 * @param parameterName
	 * @param parameterValue
	 */
	public static void setAnyParameter(String parameterType, String parameterName, String parameterValue) {

		parameterType = parameterType.trim().toLowerCase();
		parameterName = parameterName.trim().toLowerCase();
		parameterValue = parameterValue.trim().toLowerCase();

		switch (parameterType) {
		case "path": {

			setPathParameter(parameterName, parameterValue);
			Reporter.addStepLog("<strong>Path Parameter Name : </strong>" + parameterName);
			Reporter.addStepLog("<strong>Path Parameter Value : </strong>" + parameterValue);

			break;
		}
		case "query": {
			setQueryParameter(parameterName, parameterValue);
			Reporter.addStepLog("<strong>Query Parameter Name : </strong>" + parameterName);
			Reporter.addStepLog("<strong>Query Parameter Value : </strong>" + parameterValue);

			break;
		}
		case "form": {
			setFormParameter(parameterName, parameterValue);
			Reporter.addStepLog("<strong>Form Parameter Name : </strong>" + parameterName);
			Reporter.addStepLog("<strong>Form Parameter Value : </strong>" + parameterValue);
			break;
		}
		default: {
			Reporter.addStepLog("<strong><font style='color:red'> !!! INCORRECT PARAMETER TYPE PROVIDED !!! </font> : "
					+ parameterType.toUpperCase() + "</strong>");

			Assert.fail("!!! INCORRECT PARAMETER TYPE PROVIDED !!! " + parameterType.toUpperCase());

		}
		}

	}

	/**
	 * Executes GET/POST/PUT/DELETE Request
	 * 
	 * @author 10650956 
	 * @param methodType
	 * @param jsonCondition
	 * @return Response
	 */
	public static Response executeRequest(String methodType, String jsonCondition) {

		Response response = null;

		methodType = methodType.toLowerCase().trim();
		jsonCondition = jsonCondition.toLowerCase().trim();

		/* checking if request JSON is needed */
		if (jsonCondition.equals("without")) {
			// do nothing
		} else if (jsonCondition.equals("with")) {

			RestApiUtils.setHeader("Content-Type", "application/json");
			RestApiUtils.setRequestBody(RestApiUtils.requestJson);

			//Reporter.addStepLog("<strong>Resquest Json: </strong><br>" + RestApiUtils.requestJson);
			Reporter.addFile(RestApiUtils.requestJson,"json","Click on link to see Request !!");

		} else {

			Reporter.addStepLog("<strong><font style='color:red'> !!! INCORRECT JSON CONDITION PROVIDED !!! </font> : "
					+ jsonCondition + "</strong>");

			Assert.fail(" !!! INCORRECT JSON CONDITION PROVIDED !!! ");
		}

		switch (methodType) {

		case "post": {
			response = request.post();
			break;
		}
		case "get": {
			response = request.get();
			break;
		}
		case "put": {
			response = request.put();
			break;
		}
		case "delete": {
			response = request.delete();
			break;
		}
		default: {
			Reporter.addStepLog("<strong><font style='color:red'> !!! INCORRECT METHOD TYPE PROVIDED !!! </font> : "
					+ methodType.toUpperCase() + "</strong>");

			Assert.fail("!!! INCORRECT METHOD TYPE PROVIDED !!! " + methodType);

		}

		}

		Reporter.addStepLog("<strong>HTTP Method : </strong>" + methodType.toUpperCase());

		String strReponse = response.body().asString();

		if (strReponse.length() > 0) {
			//Reporter.addStepLog("<strong>Response : </strong>" + strReponse);
			Reporter.addFile(strReponse,"json","Click on link to see Response !!");
		}
		
		long responseTime= response.getTimeIn(TimeUnit.SECONDS);
		/*for some reason if rest assured not able to retrieve response time it return -1. avoiding that*/
		if(responseTime>-1) {
			Reporter.addStepLog("<strong>Response Time  : </strong>" + responseTime+"<strong> Sec.</strong>");	
		}
				

		return response;
	}

	/**
	 * Reset RequestSpecification
	 * 
	 * Recommended to use after each end point
	 * 
	 * @author 10650956 
	 */
	public static void reset() {
		RestAssured.reset();
	}

	/**
	 * Get response body
	 * 
	 * @author 10650956 
	 * @param response
	 * @return String
	 */
	public static String getResponseBody(Response response) {
		return response.body().asString();
	}

	/**
	 * Returns json value against json path from response
	 * 
	 * @author 10650956 
	 * @param response
	 * @param jsonPath
	 * @return String
	 */
	public static String getJsonValue(Response response, String jsonPath) {

		return response.body().jsonPath().get(jsonPath);
	}

	/**
	 * Get list of json value
	 * 
	 * Takes comma separated json path
	 * 
	 * @author 10650956 
	 * @param response
	 * @param jsonPaths
	 * @return
	 */
	public static List<String> getJsonValues(Response response, String jsonPaths) {

		List<String> lstValues = new ArrayList<String>();
		String[] arrJsonPaths = jsonPaths.split(",");

		for (String jsonPath : arrJsonPaths) {
			lstValues.add(jsonPath);
		}

		return lstValues;
	}

	/**
	 * public static utility to get tag value from Xml file using xpath
	 * Reference://https://howtodoinjava.com/xml/java-xpath-tutorial-example/
	 * 
	 * @author BathriYO
	 * @param file
	 * @param xpathInString
	 * @return List<String>
	 */
	public static List<String> getXMLNodeStringValue(String xmlFilePath, String nodeXpath) {
		List<String> resultList = new ArrayList<String>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(xmlFilePath));

			XPathFactory xpathfactory = XPathFactory.newInstance();
			XPath xpath = xpathfactory.newXPath();

			XPathExpression expr = xpath.compile(nodeXpath);
			Object result = expr.evaluate(document, XPathConstants.NODESET);

			NodeList nodes = (NodeList) result;
			for (int i = 0; i < nodes.getLength(); i++) {
				resultList.add(nodes.item(i).getNodeValue());
			}

		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
		return resultList;
	}

	/**
	 * xml response schema validation
	 * Reference://https://www.james-willett.com/rest-assured-schema-validation-json-xml
	 * 
	 * @author BathriYO
	 * @param response
	 * @param xsdFilePath
	 */
	public static void verifyResponseXMLSchema(Response response, String xsdFilePath) {
		try {
			response.then().assertThat().body(RestAssuredMatchers.matchesXsd(new File(xsdFilePath)));
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	/**
	 * JSON schema validation
	 * 
	 * Reference://https://www.james-willett.com/rest-assured-schema-validation-json-xml
	 * 
	 * @author BathriYO
	 * @param response
	 * @param jsonSchemaValidationFilePath
	 */
	public static void verifyResponseJSONSchema(Response response, String jsonSchemaValidationFilePath) {
		try {
			response.then().assertThat()
					.body(JsonSchemaValidator.matchesJsonSchema(new File(jsonSchemaValidationFilePath)));
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	/**
	 * Attach file to request
	 * 
	 * @author BathriYO
	 * @param filePath
	 */
	public static void attacheFile(String filePath) {
		request.multiPart(new File(filePath));
	}

	/**
	 * This utility will set RequestSpecification.requestSpecification with basic
	 * authentication
	 * 
	 * @param username
	 * @param password
	 */
	public static void basicAuth(String username, String password) {
		try {

			request.auth().basic(username, password);

		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	/**
	 * This utility will set RequestSpecification.requestSpecification with
	 * preemptive basic authentication
	 * 
	 * @param username
	 * @param password
	 */
	public static void preemptiveBasicAuth(String username, String password) {
		try {

			request.auth().preemptive().basic(username, password);

		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	/**
	 * This utility will set RequestSpecification.requestSpecification with digest
	 * authentication
	 * 
	 * @param username
	 * @param password
	 */
	public static void digestAuth(String username, String password) {
		try {

			request.auth().digest(username, password);

		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}
	
	/**
	 * Sets proxy
	 * 
	 * @author 10650956
	 * @param host
	 * @param port
	 */
	public static void setProxy(String host, int port) {
		request.proxy(host, port);
	}
	
	/**
	 * Sets proxy
	 * 
	 * @author 10650956
	 * @param host
	 * @param port
	 * @param scheme
	 */
	public static void setProxy(String host, int port, String scheme) {
		request.proxy(host, port,scheme);
	}
}
