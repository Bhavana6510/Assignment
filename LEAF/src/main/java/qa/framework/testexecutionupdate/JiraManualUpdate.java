package qa.framework.testexecutionupdate;
import qa.framework.utils.GlobalVariables; 
import qa.framework.utils.PropertyFileUtils;

public class JiraManualUpdate {
	public static void main(String[] args) {

		GlobalVariables.configProp = new PropertyFileUtils("./config.properties");

		/*configuring jira update*/ 
		JiraAPI.configJiraUpdate();

		String reportPath = "<path of file Execution Report-xxxxxxxxx.txt under spark folder>";

		JiraAPI.runJiraUpdate (reportPath); JiraAPI.uploadZipReport();
	}
}
