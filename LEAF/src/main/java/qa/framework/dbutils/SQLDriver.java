package qa.framework.dbutils;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;

import qa.framework.excelor.ExcelORReader;
import qa.framework.utils.ExceptionHandler;
import qa.framework.utils.FileManager;
import qa.framework.utils.GlobalVariables;
import qa.framework.utils.LoggerHelper;

public class SQLDriver {
	private static String client = null;
	private static String environment = null;
	private static String testDataColumn = null;
	private static String language = null;
	private static String source = null;
	private static DBType dbType = null;

	private static int currentOpenConnection = 0;

	public static ThreadLocal<List<DBRowTO>> TTestData = new ThreadLocal<List<DBRowTO>>();

	private static Logger log = LoggerHelper.getLogger(SQLDriver.class);

	private static void configClient() {

		client = System.getProperty("client", GlobalVariables.configProp.getProperty("client"));
	}

	private static void configEnvironment() {

		environment = System.getProperty("environment", GlobalVariables.configProp.getProperty("environment"));
	}

	private static void configLanguage() {

		language = System.getProperty("language", GlobalVariables.configProp.getProperty("language"));
	}

	private static void configSource() {

		source = System.getProperty("source", GlobalVariables.configProp.getProperty("source"));

	}

	private static void configDBType() {

		dbType = DBType.valueOf(System.getProperty("db_type", GlobalVariables.configProp.getProperty("db_type")));

	}

	public static void configSQL() {

		configDBType();
		configClient();
		configEnvironment();
		configLanguage();
		configSource();
		testDataColumn = client + "_" + environment + "_" + "TestData_Value";
	}

	/**
	 * opening qa db connection
	 */
	private static DBManager openConnection() {
		try {

			String host = GlobalVariables.configProp.getProperty("db_url");
			String port = GlobalVariables.configProp.getProperty("db_port");
			String databaseName = GlobalVariables.configProp.getProperty("db_name");
			String username = GlobalVariables.configProp.getProperty("db_username");
			String password = GlobalVariables.configProp.getProperty("db_password");

			DBManager dbManager = new DBManager();
			dbManager.connect(dbType, host, port, databaseName, username, password);
			log.info("Current Open DB Connection: " + (++currentOpenConnection));
			return dbManager;

		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}

		return null;
	}

	/**
	 * Loading element locator from excel
	 * 
	 * @author 10650956
	 * @param pageName
	 * @return List<DBRowTO>
	 */
	@SuppressWarnings("unchecked")
	private static List<DBRowTO> getEleObjFromExcel(String file_page_names) {

		String query;
		List<DBRowTO> eleData = new ArrayList<DBRowTO>();

		String[] arrFilePages = file_page_names.split(",");

		for (String filePage : arrFilePages) {

			int indexOfUnderScore = filePage.indexOf("_");

			String excelFileName = filePage.substring(0, indexOfUnderScore) + ".xlsx";
			String pagesName = filePage.substring(indexOfUnderScore + 1);

			String filePath = "./src/test/resources/object-repository/" + excelFileName;

			File file = new File(filePath);

			/*
			 * searching for excel file in project folder. if file does not exist in default
			 * folder i.e. object-repository
			 */
			if (!file.exists()) {
				String userDir = System.getProperty("user.dir");
				filePath = FileManager.searchFile(userDir, excelFileName);
			}

			if (filePath == null) {
				Assert.fail("File Not Found: " + excelFileName);
			}

			/* language configuration in sql */
			if (language.trim().toLowerCase().equals("english") || language.trim().length() <= 0) {

				query = "SELECT Element_key,Element_Value,VALUE_TYPE FROM " + pagesName;

			} else {
				query = "Select ELEMENT_KEY,ifnull(" + language + ",ELEMENT_VALUE) ELEMENT_VALUE, VALUE_TYPE FROM "
						+ pagesName;
			}

			ExcelORReader objExcelAsDB = new ExcelORReader(filePath);
			Object object = objExcelAsDB.runSelectQuery(query);

			if (object instanceof List) {
				List<Map<String, Object>> runSelectQuery = (List<Map<String, Object>>) object;
				runSelectQuery.forEach(map -> {

					DBRowTO temp = new DBRowTO();
					temp.setKey((String) map.get("ELEMENT_KEY"));
					temp.setValue((String) map.get("ELEMENT_VALUE"));
					temp.setValueType((String) map.get("VALUE_TYPE"));

					eleData.add(temp);

				});

			}

		} // end of for loop
		return eleData;
	}

	/**
	 * Getting element data from db
	 * 
	 * @param pageName
	 * @return List<DBRowTO>
	 */
	public synchronized static List<DBRowTO> getEleObjData(String pageName) {

		if (source.equalsIgnoreCase("excel")) {

			return getEleObjFromExcel(pageName);

		}

		/* below is code to interact with DB */

		String finalSQLArg = "";
		List<DBRowTO> eleData = new ArrayList<DBRowTO>();
		String[] pageNameArr = pageName.split(",");
		if (pageNameArr.length > 1) {
			for (String temp : pageNameArr) {
				finalSQLArg = finalSQLArg + "'" + temp + "'" + ",";
			}
			finalSQLArg = finalSQLArg.substring(0, finalSQLArg.length() - 1);
		} else {
			finalSQLArg = "'" + pageName + "'";
		}
		String query;
		/* language configuration in sql */
		if (language.trim().toLowerCase().equals("english") || language.trim().length() <= 0) {
			query = "SELECT ELEMENTS.Element_key,ELEMENTS.Element_Value,VALUE_TYPE.Value_Type from ELEMENTS,VALUE_TYPE Where ELEMENTS.Value_Type_ID=VALUE_TYPE.Value_Type_ID AND ELEMENTS.Page_ID in ("
					+ finalSQLArg + ");";
		} else {
			query = "SELECT ELEMENTS.Element_key,IFNULL(" + language
					+ ",ELEMENTS.Element_Value)Element_Value,VALUE_TYPE.Value_Type from ELEMENTS,VALUE_TYPE Where ELEMENTS.Value_Type_ID=VALUE_TYPE.Value_Type_ID AND ELEMENTS.Page_ID in ("
					+ finalSQLArg + ");";
		}
		ResultSet result;
		DBManager dbManager = SQLDriver.openConnection();

		try {

			result = dbManager.selectQuery(query);

			while (result.next()) {
				DBRowTO temp = new DBRowTO();
				temp.setKey(result.getString("Element_Key"));
				temp.setValue(result.getString("Element_Value"));
				temp.setValueType(result.getString("Value_Type"));
				eleData.add(temp);
			}
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			try {

				dbManager.disconnect();
				log.info("Current Open DB Connection: " + (--currentOpenConnection));
			} catch (Exception e) {
				ExceptionHandler.handleException(e);
			}
		}
		return eleData;
	}

	/**
	 * Loading data from excel
	 * 
	 * @author 10650956
	 * @param featureIDs : String
	 * @return List<DBRowTO>
	 */
	@SuppressWarnings("unchecked")
	private synchronized static List<DBRowTO> getDataFromExcel(String featureIDs) {

		String query;

		List<DBRowTO> eleData = new ArrayList<DBRowTO>();

		String[] arrFeatureIds = featureIDs.replace("'", "").split(",");

		for (String featureID : arrFeatureIds) {

			String filePath = "./src/test/resources/test-data/" + featureID + ".xlsx";

			File file = new File(filePath);

			/*
			 * searching for excel file in project folder. if file does not exist in default
			 * folder i.e. object-repository
			 */
			if (!file.exists()) {
				String userDir = System.getProperty("user.dir");
				filePath = FileManager.searchFile(userDir, featureID + ".xlsx");
			}

			if (filePath == null) {
				Assert.fail("File Not Found: " + featureIDs + ".xlsx");
			}

			query = "SELECT TESTDATA_KEY, IFNULL(" + testDataColumn + ",LTI_QA_TESTDATA_VALUE) testData_alias" + " "
					+ "FROM " + "TESTDATA";

			ExcelORReader objExcelAsDB = new ExcelORReader(filePath);
			Object object = objExcelAsDB.runSelectQuery(query);

			if (object instanceof List) {
				List<Map<String, Object>> runSelectQuery = (List<Map<String, Object>>) object;

				runSelectQuery.forEach(map -> {

					DBRowTO temp = new DBRowTO();
					temp.setKey((String) map.get("TESTDATA_KEY"));
					temp.setValue(map.get(("testData_alias").toUpperCase()) + "");

					eleData.add(temp);

				});
			}
		}
		return eleData;

	}

	/**
	 * Getting TestData from db
	 * 
	 * @param featureIDs
	 * @return List<DBRowTO>
	 */
	public synchronized static List<DBRowTO> getData(String featureIDs) {

		if (source.equalsIgnoreCase("excel")) {

			return getDataFromExcel(featureIDs);

		}

		List<DBRowTO> testData = new ArrayList<DBRowTO>();
		/*
		 * String query = "SELECT TESTDATA.Testdata_Key,TESTDATA." + testDataColumn +
		 * " " + "FROM TESTDATA WHERE TESTDATA.Feature_ID in (" + featureIDs + ")";
		 */

		String query = "SELECT TESTDATA.Testdata_Key,IFNULL (TESTDATA." + testDataColumn
				+ ",TESTDATA.lti_qa_testdata_value) testData_alias" + " "
				+ "FROM TESTDATA WHERE TESTDATA.Feature_ID in (" + featureIDs + ")";

		ResultSet result;
		DBManager dbManager = SQLDriver.openConnection();

		try {

			result = dbManager.selectQuery(query);
			while (result.next()) {
				DBRowTO temp = new DBRowTO();
				temp.setKey(result.getString("Testdata_Key"));
				temp.setValue(result.getString("testData_alias"));
				testData.add(temp);
			}
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			try {

				dbManager.disconnect();
				log.info("Current Open DB Connection: " + (--currentOpenConnection));
			} catch (Exception e) {
				ExceptionHandler.handleException(e);
			}

		}
		return testData;
	}

}
