package qa.notepad.desktop.stepdefs;

import org.junit.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import qa.framework.assertions.AssertLogger;
import qa.framework.utils.Reporter;
import qa.notepad.desktop.screens.NTP_HomeScreen;

public class DesktopDemo {

	NTP_HomeScreen homeScr = new NTP_HomeScreen();
	
	@When("^user enter text \"([^\"]*)\" in notpad$")
    public void user_enter_text_something_in_notpad(String text) {
        
		//DesktopDriverManager.getDriver().findElementByAccessibilityId("15").sendKeys(text);
		homeScr.enterText(text);
		
    }

    @Then("^text \"([^\"]*)\" should get entered in notepad$")
    public void text_something_should_get_entered_in_notepad(String text) {
        
    	//String acutalText = DesktopDriverManager.getDriver().findElementByAccessibilityId("15").getText();
    	String acutalText = homeScr.getText();
    	AssertLogger.assertEquals(text, acutalText, "Entered text didn't match");
    	
    }

    
    
    @When("^user open 'About Notepad' dialog box$")
    public void user_open_about_notepad_dialog_box() {
        //DesktopDriverManager.getDriver().findElementByName("Help").click();
        //DesktopDriverManager.getDriver().findElementByName("About Notepad").click();
    	
    	homeScr.clickHelp();
    	homeScr.clickAboutNotepad();
    }

    @When("^user close 'About Notepad' dialog box$")
    public void user_close_about_notepad_dialog_box() {
        //DesktopDriverManager.getDriver().findElementByName("OK").click();
    	homeScr.clickOKBtnAbout();
    }

    @Then("^notepad version should be \"([^\"]*)\"$")
    public void notepad_version_should_be_something(String version) {
        //String acutalVersion = DesktopDriverManager.getDriver().findElementByXPath("//*[contains(@Name,'Version')]").getAttribute("Name");
    	String acutalVersion = homeScr.getNotepadVersion();
        
        AssertLogger.multiAssertStart();
        
        if(acutalVersion.contains(version)) {
        	AssertLogger.multiAssertTrue(true,"Version didn't match");
        }else {
        	AssertLogger.multiAssertTrue(false,"Version didn't match");
        }
        
        AssertLogger.multiAssertStart();
        
        /*pass null for default message and default color*/
        Reporter.addDesktopScreenshot(null,null);
    }

    @And("^user intentionally fail this scenario$")
    public void user_instentionally_fail_this_scenario() {
        Assert.fail("Know Reason");
    }

    @And("^user close the notepad application$")
    public void user_close_the_notepad_application() {
	
    	//DesktopDriverManager.getDriver().findElementByName("Close").click();
    	homeScr.clickCloseBtn();
    	
    	try {
    		//DesktopDriverManager.getDriver().findElementByName("Don't Save").click();
    		homeScr.clickDontSaveBtn();
    	}catch(Exception e) {
    		//do nothing
    	}
    }
	
	
}
