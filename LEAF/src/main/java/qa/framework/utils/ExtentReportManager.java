package qa.framework.utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
	String reportPath;
	ExtentHtmlReporter htmlReport;
	ExtentTest test;

	String userDir = System.getProperty("user.dir");
	String extentReportDirPath = userDir + "/test-output/extent-report";
	String screenshotDirPath = extentReportDirPath + "/images";

	private static ThreadLocal<ExtentTest> TTest = new ThreadLocal<ExtentTest>();

	private void configExtentReport() {
		/* configuring extent report */
		reportPath = extentReportDirPath + "extent-testng.html";
		htmlReport = new ExtentHtmlReporter(reportPath);

		htmlReport.config().setDocumentTitle("unicorn");
		htmlReport.config().setReportName("ExecutionReport");
		htmlReport.config().setTheme(Theme.STANDARD);
		htmlReport.config().enableTimeline(true);

		GlobalVariables.extentReport = new ExtentReports();
		GlobalVariables.extentReport.attachReporter(htmlReport);
		/* end */
	}

	private void configLogger() {
		LoggerHelper.createLogFolder();
	}

	private void configReportFolder() {
		/******************************/
		/* create image folder for screenshot */
		File extentReportDir = new File(extentReportDirPath);
		File screenshotDir = new File(screenshotDirPath);

		/* checking if test output folder exist or not */
		String testOutputFolder = System.getProperty("user.dir") + "/" + "test-output";
		File testOutputDir = new File(testOutputFolder);
		if (!testOutputDir.exists()) {
			testOutputDir.mkdir();
		}
		/* checking if cucumber-reports directory exist or not */
		if (!extentReportDir.exists()) {
			/*
			 * Note:After maven clean target directory will not have cucumber-report
			 * directory due to which 'image' dir will not get created and screenshot will
			 * not get saved thus checking and creating cucumber-reports dir.
			 */
			new File(extentReportDirPath).mkdir();
		}
		/* removing screenshot from previous run by deleting images folder */
		if (screenshotDir.exists()) {
			try {
				FileUtils.deleteDirectory(screenshotDir);
			} catch (Exception e) {
				ExceptionHandler.handleException(e);
			}
		}
		new File(screenshotDirPath).mkdir();
		CaptureScreenshot.imageFolderPath = screenshotDirPath;
		/************************************************************/
	}

	public void setExtentReport() {
		/* creating logger folder */
		configLogger();
		/* creating image folder */
		configReportFolder();
		/* configuring extent report */
		configExtentReport();
	}

	/**
	 * updating test method status as passed,failed or skipped
	 * 
	 * @param result
	 * @return ExtentTest
	 */
	public ExtentTest updateTestStatus(ITestResult result) {
		ExtentTest status = null;
		/* extent report status update */
		try {
			int statusCode = result.getStatus();
			status = TTest.get().createNode("Test Status");

			if (statusCode == ITestResult.SUCCESS) {
				status.log(Status.PASS, "PASSED");
			} else if (statusCode == ITestResult.FAILURE) {
				status.log(Status.FAIL, result.getThrowable());
			} else if (statusCode == ITestResult.SKIP) {
				status.log(Status.SKIP, "SKIPPED");
			}

		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
		return status;
	}

	/**
	 * creating categories in extent report based on input provided
	 * 
	 * @param listGroupIDs
	 */
	public void createCategories(String[] listGroupIDs) {
		/* creating categories based on groups */
		if (listGroupIDs.length > 0) {
			/* creating categories for test */
			for (String category : listGroupIDs) {
				TTest.get().assignCategory(category);
			}
		}
	}

	/**
	 * @param fileName
	 */
	public void attachScreenshot(String fileName) {
		try {
			TTest.get().addScreenCaptureFromPath(CaptureScreenshot.screenCapture(fileName));
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	/**
	 * setting extent report test
	 * 
	 * @author BathriYO
	 * @param test
	 */
	public static void setTest(ExtentTest test) {
		TTest.set(test);
	}

	/**
	 * get ExtentTest based on thread
	 * 
	 * @author BathriYO
	 * @return ExtentTest
	 */
	public static ExtentTest getTest() {
		return TTest.get();
	}

	/**
	 * creates a node with message
	 * 
	 * @author BathriYO
	 * @param test
	 * @param message
	 * @return ExtentTest
	 */
	public static ExtentTest logNode(String message) {
		ExtentTest node = getTest().createNode(message);
		return node;
	}

	/**
	 * creates a node with message and attach screenshot
	 * 
	 * @author BathriYO
	 * @param test
	 * @param message
	 * @param screenshotpath
	 * @return ExtentTest
	 */
	public static ExtentTest logNode(ExtentTest test, String message, String screenshotPath) {
		try {
			ExtentTest node = getTest().createNode(message);
			node.info("screenshot:");
			node.addScreenCaptureFromPath(screenshotPath);
			return node;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
		return null;
	}
}
