package qa.framework.runner;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import qa.framework.dbutils.SQLDriver;
import qa.framework.utils.CaptureScreenshot;
import qa.framework.utils.ExceptionHandler;
import qa.framework.utils.ExtentReportManager;
import qa.framework.utils.GlobalVariables;
import qa.framework.utils.LoggerHelper;
import qa.framework.utils.PropertyFileUtils;
import qa.framework.webui.browsers.WebDriverManager;

/**
 * 
 * @author BathriYO
 *
 */
public class TNGBase {
	static boolean flagUI = false;
	static boolean flagMainframe = false;
	static String currentClassName;
	static String currentTestName;
	static String[] listGroupIDs;

	public ExtentTest test = null;
	Logger log = LoggerHelper.getLogger(TNGBase.class);

	@BeforeSuite(alwaysRun = true)
	public void setUpSuit() throws IOException {
		GlobalVariables.extentReportManagerObj = new ExtentReportManager();
		GlobalVariables.extentReportManagerObj.setExtentReport();
		/* initializing configuration property file */
		GlobalVariables.configProp = new PropertyFileUtils("config.properties");
		/* config webdriver */
		WebDriverManager.configureDriver();
		/* config test data column as per client and environment */
		SQLDriver.configSQL();
		/* 1st step configure mainFrame */
		//LaunchMainframeTerminal.configMainframe();
//		if (LaunchMainframeTerminal.mainframe.trim().equalsIgnoreCase("true")) {
//			/* This method is called to avoid Emulator(keyboard) popup to appear */
//			LaunchMainframeTerminal.updateDefaultKeyBoard_IBMPCOM_WS();
//			/* 2nd step:starting LeanFT */
//			Leanft.startEngine();
//			/* 3rd step:launch terminal is in before method */
//			/* 4th step:closing terminal is in after method */
//		}
	}

	@AfterSuite(alwaysRun = true)
	public void tearDownSuit() {
		/* final report flush */
		GlobalVariables.extentReport.flush();
		/* killing browser process */
		WebDriverManager.killDriverProcess();
//		if (LaunchMainframeTerminal.mainframe.trim().equalsIgnoreCase("true")) {
//			/* stopping leanft */
//			Leanft.stopEngine();
//		}
	}

	@BeforeMethod(dependsOnMethods = { "getMethodName" }, alwaysRun = true)
	public void loadTestData() {
		currentClassName = this.getClass().getSimpleName();
		String primaryKey = "'" + currentClassName + "'";
		for (String str : listGroupIDs) {
			if (str.matches("@td_.*")) {
				String key = str.replace("@td_", "");
				primaryKey = primaryKey + "," + "'" + key + "'";

			}
		}
		/*
		 * getting tets data base on class name.if user want to add more test data, then
		 * respective key should be mention groups with prefix '@td'
		 */
		SQLDriver.TTestData.set(SQLDriver.getData(primaryKey));
	}

	/* getting test method name */
	@BeforeMethod(alwaysRun = true)
	public void getMethodName(Method method) {
		long id = Thread.currentThread().getId();
		/* getting current method name */
		currentTestName = method.getName();
		/* getting current methods group name */
		Test currentTest = method.getAnnotation(Test.class);
		listGroupIDs = currentTest.groups();

		ExtentReportManager.setTest(GlobalVariables.extentReport.createTest(currentTest.description()));
		/* [mandatory step to perform to generate the report] */

		/* login method name */
		log.debug("****************CurrentTestRunning:" + currentTestName + "************\n");
	}

	/* setting by browser */
	@BeforeMethod(dependsOnMethods = { "getMethodName" }, alwaysRun = true)
	public void setUpBrowser() {
		if (currentClassName.matches("(.*)UI(.*)")) {
			flagUI = true;
			WebDriverManager.startDriver();
		}
//		/* fail safe for mainframe */
//		if (currentClassName.matches("(.*)MAN(.*)")) {
//			flagMainframe = true;
//
//			if (LaunchMainframeTerminal.mainframe.trim().equalsIgnoreCase("true")) {
//				/* 3rd step:launch terminal and login terminal */
//				LaunchMainframeTerminal.launchTerminalAndSetWindow();
//			} else {
//				Assert.fail("!!! 'mainframe' attribute is 'false' in 'config.properties' file !!!");
//			}
//		}

	}

	/* Extent Report status update */
	@AfterMethod(alwaysRun = true)
	public void ExtentReportStatusUpdate(ITestResult result) {
		/* creating tags in extent report */
		GlobalVariables.extentReportManagerObj.createCategories(listGroupIDs);
		/* extent report status update */
		ExtentTest test = GlobalVariables.extentReportManagerObj.updateTestStatus(result);
		try {
			/*
			 * taking screenshot in case of failure to prevent null pointer exception in
			 * case of api below check is performed
			 */
			if (result.getStatus() == ITestResult.FAILURE) {
				if (flagUI == true) {
					test.addScreenCaptureFromPath(CaptureScreenshot.screenCapture("Failure"));
				} /* Mainframe */
//				if (LaunchMainframeTerminal.mainframe.trim().equalsIgnoreCase("true") && flagMainframe == true) {
//					test.addScreenCaptureFromPath(CaptureScreenshot.screenCapture("Failure"));
//				}
			}
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			GlobalVariables.extentReport.flush();
		}
	}

	/* killing browser after @test */
	@AfterMethod(dependsOnMethods = { "extentReportStatusUpdate" }, alwaysRun = true)
	public void killProcess() {
		if (flagUI == true) {
			WebDriverManager.quitDriver();
		}
//		if (LaunchMainframeTerminal.mainframe.trim().equalsIgnoreCase("true") && flagMainframe == true) {
//			/* Reverting back the flagMainframe value */
//			flagMainframe = false;
//
//			try {
//				LaunchMainframeTerminal.disconnect();
//				LaunchMainframeTerminal.closeTerminalWindow();
//			} catch (Exception e) {
//				ExceptionHandler.handleException(e);
//			}
//		}	
		}
}
