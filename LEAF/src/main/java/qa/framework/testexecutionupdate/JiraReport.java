package qa.framework.testexecutionupdate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.Assert;
import org.testng.AssertJUnit;
import qa.framework.utils.CalendarUtils;
import qa.framework.utils.FileManager;
import qa.framework.utils.PropertyFileUtils;

public class JiraReport {

	public static String filePath;
	static String uniqueFileName;
	public static String delimiter = ";";
	static String extentPropetiesFilePath = "/src/test/resources/extent.properties";
	static String extentSparkPropertyName = "extent.reporter.spark.out";

	private static String counter() {

		return CalendarUtils.getCalendarUtilsObject().getTimeStamp("MM dd yyyy - HHmmss");

	}

	private static String getReportPath() {

		PropertyFileUtils properties = new PropertyFileUtils(extentPropetiesFilePath);
		String reportFilePath = properties.getProperty(extentSparkPropertyName);
		return System.getProperty("user.dir") + "/" + reportFilePath;
	}

	public static String write(String fileName, String format) {
		uniqueFileName = fileName + "-" + counter();

		String writeStr = "testExecutionID" + delimiter + "testID" + delimiter + "runID" + delimiter + "testName"
				+ delimiter + "previousRunStatus" + delimiter + "currentRunStatus";
		filePath = getReportPath() + "/" + uniqueFileName + format;

		FileManager fileMng = FileManager.getFileManagerObj();

		/* creating jira update file */
		boolean status = fileMng.createFile(filePath);

		if (!status) {

			AssertJUnit.fail(" !!! Given file didn't got created !!! " + filePath);

		}

		/* writing header to newly created file */
		fileMng.write(filePath, writeStr + "\n");

		for (Map<String, String> map : JiraAPI.LstRunInfo) {

			String appendStr = "";

			appendStr = appendStr + map.get(JiraAPI.strTestExecutionID) + delimiter + map.get(JiraAPI.strTestID)
					+ delimiter + map.get(JiraAPI.strRunID) + delimiter + map.get(JiraAPI.strTestName) + delimiter
					+ map.get(JiraAPI.strPreviousRunStatus);

			if (map.get(JiraAPI.strCurrentRunStatus) != null) {
				appendStr = appendStr + delimiter + map.get(JiraAPI.strCurrentRunStatus);

			}

			fileMng.append(filePath, appendStr + "\n");

		}

		return filePath;

	}

}
