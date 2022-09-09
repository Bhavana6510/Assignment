package qa.notepad.desktop.screens;

import org.openqa.selenium.WebElement;

import qa.framework.dbutils.SQLDriver;
import qa.framework.desktop.DesktopActions;

public class NTP_HomeScreen {
	
	private DesktopActions desktopActions = new DesktopActions(SQLDriver.getEleObjData("NTP_HomeScreen"));

	/**
	 * Enter text in notepad edit area
	 * 
	 * @param value
	 */
	public void enterText(String value) {

		WebElement editFiled = desktopActions.getElement("editField");

		DesktopActions.clear(editFiled);

		DesktopActions.sendKeys(editFiled, value);
	}

	/**
	 * Get entered text
	 * 
	 * @return
	 */
	public String getText() {

		WebElement editFiled = desktopActions.getElement("editField");

		return DesktopActions.getText(editFiled);
	}
	
	public void clickHelp() {
		
		DesktopActions.click(desktopActions.getElement("mnuHelp"));
	}
	
	public void clickAboutNotepad() {
		DesktopActions.click(desktopActions.getElement("mnuAboutNotepad"));
	}
	
	public void clickOKBtnAbout() {
		
		DesktopActions.click(desktopActions.getElement("btnOK"));
	}
	
	public String getNotepadVersion() {
		
		return DesktopActions.getAttribute(desktopActions.getElement("txtVersion"),"Name");
	}
	
	public void clickCloseBtn() {
		DesktopActions.click(desktopActions.getElement("btnClose"));
	}
	
	public void clickDontSaveBtn() {
		DesktopActions.click(desktopActions.getElement("btnDon'tSave"));
	}
	

}
