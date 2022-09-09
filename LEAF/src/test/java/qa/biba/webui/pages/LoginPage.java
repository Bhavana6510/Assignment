package qa.biba.webui.pages;

import qa.framework.dbutils.SQLDriver;
import qa.framework.utils.Action;
import qa.framework.utils.PropertyFileUtils;

public class LoginPage {
	private final Action action = new Action(SQLDriver.getEleObjData("LoginPage"));
	//Action action = new Action();

	public void openApp() {
		PropertyFileUtils pro = new PropertyFileUtils("./application.properties");
		String url = pro.getProperty("biba_Url");
		Action.openUrl(url);

	}

	public void login(String username, String password) throws InterruptedException {

		Action.sendKeys(action.getElement("id", "ctl00_ContentPlaceHolder1_ctl00_ctl01_Login1_UserName"), username);
		Action.sendKeys(action.getElement("id", "ctl00_ContentPlaceHolder1_ctl00_ctl01_Login1_Password"), password);
		Action.click(action.getElement("id", "ctl00_ContentPlaceHolder1_ctl00_ctl01_Login1_LoginImageButton"));
	}

	public String getErrorMessage() {
		String check = Action
				.getText(action.getElement("id", "ctl00_ContentPlaceHolder1_ctl00_ctl01_Login1_FailureText"));
		return check;
	}

}
