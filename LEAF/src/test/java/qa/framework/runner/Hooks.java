package qa.framework.runner;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchWindowException;

import io.cucumber.core.api.Scenario;
import io.cucumber.core.event.Status;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import qa.framework.dbutils.SQLDriver;
import qa.framework.desktop.DesktopDriverManager;
import qa.framework.device.DeviceDriverManager;
import qa.framework.restutils.RestApiUtils;
import qa.framework.testexecutionupdate.JiraAPI;
import qa.framework.utils.BinarySearchListOfMap;
import qa.framework.utils.ExceptionHandler;
import qa.framework.utils.LoggerHelper;
import qa.framework.utils.Reporter;
import qa.framework.webui.browsers.WebDriverManager;

public class Hooks {
	private Logger log = LoggerHelper.getLogger(Hooks.class);
	/* do not assign featuredes as null.will cause issue */
	String featureDes = "";
	
	public static boolean flagAPI = false;
	public static boolean flagMainframe = false;

	@Before(order = 0)
	public void beforeStartDriver(Scenario scenario) {
		/* setting scenario for report purpose */
		Reporter.TScenario.set(scenario);

		/*
		 * @Depercated: done from Step definition
		 Collection<String> sourceTagNames = scenario.getSourceTagNames();
		for (String tagName : sourceTagNames) {
			if (tagName.matches("(.*)UI(.*)")) {
				flagUI = true;
				WebDriverManager.startDriver();
				break;// in case of multiple tag have ui browser should open only once
			}
		}
		
		for (String tagName : sourceTagNames) {
			if (tagName.matches("(.*)API(.*)")) {
				flagAPI = true;
				break;
			}
		}
		
		*/
		/*-------------------------------------------------------------------------------*/
		
//		/* fail safe for mainframe */
//		for (String tagName : sourceTagNames) {
//			if (tagName.matches("(.*)MAN(.*)")) {
//				flagMainframe = true;
//				if (LaunchMainframeTerminal.mainframe.trim().equalsIgnoreCase("true")) {
//					/* 3rd step:launch terminal and login to terminal */
//					LaunchMainframeTerminal.launchTerminalAndSetWindow();
//				} else {
//					Assert.fail("!!! 'mainframe' attribute is 'false' in 'config.properties' file !!!");
//				}
//				break;
//			}
//		}
	}

	@Before(order = 1)
	public void beforeLoadTestData(Scenario scenario) {
		String tdTags = "";
		log.debug("******************************************");
		log.debug("Current Scenario Running: " + scenario.getName());
		log.debug("******************************************");

		Collection<String> sourceTagNames = scenario.getSourceTagNames();
		for (String tagName : sourceTagNames) {
			if (tagName.contains("td_")) {
				String temp = tagName.substring(4);
				tdTags = tdTags + "'" + temp + "'" + ",";
			}
		}
		/* removing last ',' */
		if(tdTags.length()!=0) {
			tdTags = tdTags.substring(0, tdTags.length() - 1);
			SQLDriver.TTestData.set(SQLDriver.getData(tdTags));
		}
		
		
	}

	@After(order = 0)
	public void afterDestroy() {
		/* closing webDriver in case web application */
//		if (flagUI == true) {
//			WebDriverManager.quitDriver();
//		}
		/* resetting rest assured request specification */
		if (flagAPI == true) {
			RestApiUtils.reset();
		}
//		/* closing mainframe terminal in case of main frame applicaion */
//		if (LaunchMainframeTerminal.mainframe.trim().equalsIgnoreCase("true") && flagMainframe == true) {
//			/* Reverting back the flagMainframe value */
//			flagMainframe = false;
//
//			try {
//				/* disconnecting terminal */
//				LaunchMainframeTerminal.disconnect();
//				/* closing terminal window after disconnect */
//				LaunchMainframeTerminal.closeTerminalWindow();
//			} catch (Exception e) {
//				ExceptionHandler.handleException(e);
//			}
//		}
		
		
	}

	@After(order = 10)
	public void afterOnScenarioFailure(Scenario scenario) {
		if (scenario.isFailed()) {
			
			/* UI */
			if (WebDriverManager.getWebUIFlag() == true) {
				try {
					
					/*on failure resetting the webui flag*/
					WebDriverManager.setWebUIFlag(false);
					
					/*on failure taking screenshot*/
					Reporter.addScreenCapture("Click on image to see Failure Screenshot !!","#ff1a1a");
					
					/*quitting the browser*/
					WebDriverManager.quitDriver();
				} catch (Exception e) {
					ExceptionHandler.handleException(e);
				}
			}
//			/* MainFrame */
//			if (LaunchMainframeTerminal.mainframe.trim().equalsIgnoreCase("true") && flagMainframe == true) {
//				Reporter.addEntireScreenCaptured();
//			}
			
			
			/*for Desktop*/
			if(DesktopDriverManager.getAppFlag()==true) {
				
				try {
					
					/*on failure resetting the application flag*/
					DesktopDriverManager.setAppFlag(false);
					
					/*on failure taking screenshot*/
					Reporter.addDesktopScreenshot("Click on image to see Failure Screenshot !!","#ff1a1a");
					
					/*on Failure killing the application*/
					DesktopDriverManager.stopApplication();
					
				}catch(NoSuchWindowException e) {
					log.info("!!! Desktop window seem to be closed already !!! NO Screenshot taken");
				}
				
				
			}
			
			/*for Mobile Devices*/
			if(DeviceDriverManager.getflag()==true) {
				
				try {
					
					/*on failure resetting the application flag*/
					DeviceDriverManager.setflag(false);
					
					/*on failure taking screenshot*/
					Reporter.addDeviceScreenshot("Click on image to see Failure Screenshot !!","#ff1a1a");
					
					/*on Failure killing the app and browser*/
					try {
						DeviceDriverManager.quit();
					}catch(Exception e) {
						e.printStackTrace();
						DeviceDriverManager.closeApp();
					}
					
					
				}catch(NoSuchWindowException e) {
					e.printStackTrace();
					log.info("!!! Desktop window seem to be closed already !!! NO Screenshot taken");
				}
				
				
			}
			
			
			
		}
	}

	/***************************************************************
	 * JIRA UPDATE
	 ***************************************************************/
	String testID;

	/* getting testId */
	/* this statement should only run in case of scenario outline */
	@Given("^Scenario Variation:(.+)-(.+)$")
	public void scenario_variation_(String jiratestid, String scenarioName) {
		this.testID = jiratestid;
	}

	public void updateScenarioStatus(String testID, Status status, Scenario scenario) {
		/* searching testID in list */
		BinarySearchListOfMap search = new BinarySearchListOfMap(JiraAPI.LstRunInfo, JiraAPI.strTestID);
		/* got respective index */
		int index = search.binarySearch(0, JiraAPI.LstRunInfo.size() - 1, testID);
		if (index >= 0) {
			/*
			 * checking if 'currentrunstatus' is available in map or not,if no it will be
			 * added
			 */
			if (!JiraAPI.LstRunInfo.get(index).containsKey(JiraAPI.strCurrentRunStatus)) {
				/* updating scenario/scenario outline status in list against testID */
				if (status == Status.PASSED) {
					JiraAPI.LstRunInfo.get(index).put(JiraAPI.strCurrentRunStatus, "PASS");
				} else if (status == Status.FAILED) {
					JiraAPI.LstRunInfo.get(index).put(JiraAPI.strCurrentRunStatus, "FAIL");
				} else if (status == Status.SKIPPED) {
					JiraAPI.LstRunInfo.get(index).put(JiraAPI.strCurrentRunStatus, "ABORTED");
				}

			} else {
				/* 'currentrunstatus' is only updated if previous run status is not fail */
				if (JiraAPI.LstRunInfo.get(index).get(JiraAPI.strCurrentRunStatus).equals("PASS")) {
					/* updating scenario/scenario outline status in list against testID */
					if (status == Status.PASSED) {
						JiraAPI.LstRunInfo.get(index).put(JiraAPI.strCurrentRunStatus, "PASS");
					} else if (status == Status.FAILED) {
						JiraAPI.LstRunInfo.get(index).put(JiraAPI.strCurrentRunStatus, "FAIL");
					} else if (status == Status.SKIPPED) {
						JiraAPI.LstRunInfo.get(index).put(JiraAPI.strCurrentRunStatus, "ABORTED");
					}
				}

			}
		} else {
			scenario.write("[Alert] TestID is not provided");
		}
	}

	@After(order = 1)
	public void updateJiraTestCurrentStatus(Scenario scenario) {
		if (JiraAPI.enableJiraUpdate == true) {
			String scenarioName = scenario.getName().trim();
			/* getting scenario status */
			Status status = scenario.getStatus();

			/*
			 * getting testID this statement will only run in case of scenario
			 */
			if (scenarioName.startsWith("[")) {
				/* removing square brackets */
				String testIDs[] = scenarioName.split("]")[0].replace("[", "").split(",");
				for (String testID : testIDs) {
					/* for scenario having multiple testIDs */
					updateScenarioStatus(testID, status, scenario);
				}
			} else {
				/* for scenario outline */
				updateScenarioStatus(testID, status, scenario);
			}
			testID = null;
		}
	}
}
