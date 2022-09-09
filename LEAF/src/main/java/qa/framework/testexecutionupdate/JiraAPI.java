package qa.framework.testexecutionupdate;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import qa.framework.utils.BinarySearchListOfMap;
import qa.framework.utils.CSVFileUtils;
import qa.framework.utils.CalendarUtils;
import qa.framework.utils.FileManager;
import qa.framework.utils.GlobalVariables;
import qa.framework.utils.LoggerHelper;
import qa.framework.utils.PropertyFileUtils;
import qa.framework.restutils.RestApiUtils;
import qa.framework.utils.SortListOfMap;

public class JiraAPI {
	static Logger log = LoggerHelper.getLogger(JiraAPI.class);

	public static String strTestExecutionID = "testExecutionID";
	public static String strTestID = "testID";
	public static String strRunID = "runID";
	public static String strTestName = "testName";
	public static String strPreviousRunStatus = "previousRunStatus";
	public static String strCurrentRunStatus = "currentRunStatus";
	public static boolean enableJiraUpdate = false;
	public static boolean autoUpdateJiraRecords = false;
	public static boolean uploadReportZip = false;
	public static String authentication = "";
	public static String executionIds;
	static RequestSpecification request;
	public static List<Map<String, String>> LstRunInfo;
	public static int testIdIndex;
	public static int testCurrentRunStatusIndex;

	/*
	 * Setting authentication
	 */

	private static void setAuth() {

		request.header("Authorization", authentication);
		request.relaxedHTTPSValidation();
	}

	/*
	 * Getting total number of test ids in one Test Execution Id (this method is
	 * call to know who many total test are available under Test Execution so that
	 * page offset can be set.)
	 * 
	 * @param testExecutionIds
	 * 
	 * @return
	 */

	private static int getTotalTestIds(String testExecutionIds) {

		String baseURI = "https://jira.broadridge.net/rest/api/latest/issue/" + testExecutionIds;
		RestAssured.baseURI = baseURI;
		request = RestAssured.given();
		setAuth();
		Response response = request.get();
		if (response.statusCode() == 200) {
			ArrayList<HashMap<String, Object>> listOfTestIds = response.body().jsonPath()
					.get("fields.customfield_12056");
			return listOfTestIds.size();

		} else {
			Assert.fail("!!! Get request failed with code :" + response.statusCode());
		}
		return -1;
	}

	/*
	 * COMPLETED 1. Getting test id, run id and previous run status 2. Getting total
	 * number of entries and dividing then in pages of offset 200 3. Checking of
	 * status code is 200 else aborting the execution with valid message.
	 * 
	 * @param strTestExecutionID
	 */

	private static void getTestIDRunIdPreviousRunStatus(String testExecutionIDs) {
		int pageOffset = 200;
		int totalPages;
		LstRunInfo = new ArrayList<Map<String, String>>();
		String[] arrTestExecutionID = testExecutionIDs.split(",");

		for (String testExecutionID : arrTestExecutionID) {

			/* getting total number of entries (tests) in one test execution id */
			int totalTestIds = getTotalTestIds(testExecutionID);
			/* calculating total number of pages */
			if (totalTestIds % pageOffset == 0) {

				totalPages = totalTestIds / pageOffset;

			} else {

				totalPages = (totalTestIds / pageOffset) + 1;

			}
			for (int page = 1; page <= totalPages; page++) {

				RestAssured.baseURI = "https://jira.broadridge.net/rest/raven/1.0/testruns";
				request = RestAssured.given();
				setAuth();
				request.queryParam("testExecKey", testExecutionID);
				request.queryParam("page", page);

				Response response = request.get();

				if (response.statusCode() == 200) {

					/*
					 * return type is list of map with value of different data type thus Object as
					 * value
					 */

					ArrayList<HashMap<String, Object>> list = response.body().jsonPath().get();

					// Storing test id, run id and previous run status in a map and putting map in a
					// - list

					for (Map<String, Object> map : list) {

						Map<String, String> newMap = new HashMap<String, String>();

						newMap.put(JiraAPI.strTestExecutionID, testExecutionID);
						newMap.put(JiraAPI.strTestID, map.get("testKey").toString());
						newMap.put(JiraAPI.strRunID, map.get("id").toString());
						newMap.put(JiraAPI.strPreviousRunStatus, map.get("status").toString());
						LstRunInfo.add(newMap);
					}
				}

				else {
					Assert.fail("!!! Jira get request failed with response code " + response.statusCode() + " !!!");

				}
			}
		}
	}

	/* Getting test summary and storing in list of map */

	private static void getTestName() {

		String testIds = "";
		String str;

		RestAssured.baseURI = "https://jira.broadridge.net/rest/api/2/search";

		request = RestAssured.given();

		setAuth();

		request.header("Content-Type", "application/json");

		for (Map<String, String> map : LstRunInfo) {

			testIds = map.get(JiraAPI.strTestID) + "," + testIds;
		}

		/* removing last appended ',' */

		str = "{\"jql\":\"issuekey in (" + testIds.substring(0, testIds.length() - 1)
				+ ")\", \"fields\":[\"key\",\"summary\"]," + "\"maxResults\":\"10000\"}";

		request.body(str);

		Response response = request.post();

		@SuppressWarnings("unchecked")

		ArrayList<HashMap<String, Object>> lstTestSummary = (ArrayList<HashMap<String, Object>>) response.body()
				.jsonPath().get("issues");

		/* sorting 1stRunInfot bases on map key testID */

		Collections.sort(LstRunInfo, new SortListOfMap(JiraAPI.strTestID));
		/* Initializing binary search with map key testId */
		BinarySearchListOfMap search = new BinarySearchListOfMap(LstRunInfo, JiraAPI.strTestID);

		for (Map<String, Object> map : lstTestSummary) {

			/* search testId from response in 1stRunInfo */

			int index = search.binarySearch(0, LstRunInfo.size() - 1, map.get("key").toString());

			if (index >= 0) {

				/* return type og .get("fields") is HashMap with key 'summary' */
				HashMap<String, String> mapField = (HashMap<String, String>) map.get("fields");
				LstRunInfo.get(index).put(JiraAPI.strTestName, mapField.get("summary"));
			}
		}
	}

	/* Featuring test id, run id, test name and previous run status from Jira */

	private static void featchJiraRecord(String testExecutionId) {
		getTestIDRunIdPreviousRunStatus(testExecutionId);
		getTestName();
	}

	/* Configuring JIRA update feature in our framework */

	public static void configJiraUpdate() {

		String cmdEnabledJiraUpdate = System.getProperty("enableJiraUpdate");

		/* configuring enabledJiraUpdate */
		if (cmdEnabledJiraUpdate != null) {
			enableJiraUpdate = Boolean.parseBoolean(cmdEnabledJiraUpdate.toLowerCase().trim());
		} else {

			/* reading from property file */
			enableJiraUpdate = Boolean
					.parseBoolean(GlobalVariables.configProp.getProperty("enableJiraUpdate").toLowerCase().trim());

		}
		/*
		 * if enableJiraUpate is true, config autoUpdateJiraRecouds, executionIds and
		 * call featchJiraRecord()
		 */

		if (enableJiraUpdate == true) {

			String cmdAutoUpdateJiraRecords = System.getProperty("autoUpdateJiraRecords");
			String cmdExecutionIds = System.getProperty("executionIds");
			String cmdUploadReportZip = System.getProperty("uploadReportZip");
			String cmdAuthentication = System.getProperty("authentication");
			/* configuring autoUpdateJiraRecords only if enabledJiraUpdate is TRUE */
			if (cmdAutoUpdateJiraRecords != null) {
				autoUpdateJiraRecords = Boolean.parseBoolean(cmdAutoUpdateJiraRecords.toLowerCase().trim());
			} else {

				autoUpdateJiraRecords = Boolean.parseBoolean(
						GlobalVariables.configProp.getProperty("autoUpdateJiraRecords").toLowerCase().trim());

			}

			/* configuring executionIds only if enabledJiraUpdate is TRUE */

			if (cmdExecutionIds != null) {
				executionIds = cmdExecutionIds;
			} else {
				executionIds = GlobalVariables.configProp.getProperty("executionIds");

			}

			/* configuring uploadReportZip only if enabled JiraUpdate is TRUE */

			if (cmdUploadReportZip != null) {
				uploadReportZip = Boolean.parseBoolean(cmdUploadReportZip);
			} else {

				uploadReportZip = Boolean.parseBoolean(GlobalVariables.configProp.getProperty("uploadReportZip"));
			}

			/* configuring authentication only if enabled JiraUpdate is TRUE */

			if (cmdAuthentication != null) {
				authentication = cmdAuthentication;
			} else {

				authentication = GlobalVariables.configProp.getProperty("authentication");
			}
			/* call featchJiraRecord to Get test id and run it against Execution ID */
			featchJiraRecord(executionIds);
		}
	}

	/***************************
	 * JIRA UPDATE
	 ***************************/

	/*
	 * Get index based on column name
	 * 
	 * @param arrHearders
	 * 
	 * @param columnName
	 * 
	 * @return int
	 */

	public static int getIndex(String[] arrHearders, String columnName) {

		int index = -1;
		int counter = 0;

		for (String headerName : arrHearders) {

			if (headerName.toLowerCase().equals(columnName.toLowerCase())) {
				index = counter;

				break;
			} else {
				++counter;
			}
		}
		return index;
	}

	/*
	 * Responsible for generating JSON
	 * 
	 * @param list
	 * 
	 * @param testExeId
	 * 
	 * @return String
	 */

	@SuppressWarnings("unchecked")

	public static String generateJson(List<String[]> list, String testExeId) {

		JSONObject jobj = new JSONObject();
		jobj.put("testExecutionKey", testExeId);

		JSONArray jArr = new JSONArray();

		for (String[] strArr : list) {

			if (strArr[0].equals(testExeId)) {

				JSONObject arrValue = new JSONObject();

				/* try catch handles case when current status is not available in csv file */
				try {

					String currentStatus = strArr[testCurrentRunStatusIndex];

					arrValue.put("testKey", strArr[testIdIndex]);
					arrValue.put("status", currentStatus);

					jArr.add(arrValue);

				} catch (Exception e) {

					/* do nothing if current status is not available */
				}
			}
		}
		jobj.put("tests", jArr);
		System.out.println(jobj.toJSONString());
		return jobj.toJSONString();
	}

	/*
	 * Execute JIRA update API
	 * 
	 * @param json
	 * 
	 * @return int
	 */
	public static int update(String json) {

		RestAssured.baseURI = "https://jira.broadridge.net/rest/raven/1.0/import/execution";
		request = RestAssured.given();

		setAuth();
		Response response = request.contentType("application/json").body(json).post();

		int statusCode = response.statusCode();

		if (statusCode == 200 || statusCode == 201) {
			System.out.println("All Test IDs are updated.....");
			log.debug("All Test IDs are updated.....");
		} else {
			System.out.println("!!! Jira update is FAIL with below message: !!!\n" + response.body().asString());
			log.debug("!!! Jira update is FAIL with below message: !!!\n" + response.body().asString());
		}
		return response.statusCode();
	}

	/*
	 * Main Jira update method
	 * 
	 * @param reportPath
	 */

	public static void runJiraUpdate(String reportPath) {

		Set<String> uniquesTestIds = new HashSet<String>();

		CSVFileUtils csvReader = CSVFileUtils.getInstance();
		List<String[]> readAll = csvReader.readAll(reportPath, JiraReport.delimiter);

		/* allTestExeIDs have duplicate test ids. */
		List<String> allTestExeIDs = csvReader.getColumnValues(readAll, 0);

		/* storing test execution ids in set from list to remove duplicates */
		/* duplicates are not allowed in set */
		/* i index starting from 1 to remove header in report file */
		for (int i = 1; i < allTestExeIDs.size(); i++) {
			uniquesTestIds.add(allTestExeIDs.get(i));
		}

		/* getting test id column index */
		testIdIndex = getIndex(readAll.get(0), "testID");

		/* getting current run status column index */

		testCurrentRunStatusIndex = getIndex(readAll.get(0), "currentRunStatus");

		Iterator<String> iterator = uniquesTestIds.iterator();

		while (iterator.hasNext()) {

			/* generating ison */
			String json = generateJson(readAll, iterator.next());

			/* run api */
			update(json);
		}
	}

	public static void uploadZipReport() {

		if (uploadReportZip) {

			String[] arrExeIds = executionIds.split("");

			PropertyFileUtils proExtent = new PropertyFileUtils("./src/test/resources/extent.properties");
			String reportDir = "./" + proExtent.getProperty("extent.reporter.spark.out");
			String timeStamp = CalendarUtils.getCalendarUtilsObject().getTimeStamp("MMM DD YYYY hhmm");
			String fileName = "RunResult - " + timeStamp;

			FileManager.getFileManagerObj().zipDir(reportDir, fileName);

			for (String exeId : arrExeIds) {

				RestApiUtils.requestSpecification = null;
				RestAssured.basePath = "";
				RestAssured.baseURI = null;

				RestAssured.baseURI = "https://jira.broadridge.net/rest/api/2/issue/" + exeId + "/attachments";
				RestAssured.given().relaxedHTTPSValidation().header("Authorization", authentication)
						.header("X-Atlassian-Token", "nocheck").multiPart(new File(reportDir + "/" + fileName + ".zip"))
						.when().post();
			}
		}
	}
}
