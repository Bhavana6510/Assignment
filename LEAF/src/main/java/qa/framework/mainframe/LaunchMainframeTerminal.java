package qa.framework.mainframe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.testng.Assert;

import qa.framework.leanft.Leanft;
import qa.framework.utils.ExceptionHandler;
import qa.framework.utils.FileManager;
import qa.framework.utils.GlobalVariables;
import qa.framework.utils.LoggerHelper;

public class LaunchMainframeTerminal {
	private final static String TASKLIST = "tasklist";
	public static String mainframe = "false";
	private static String batchFilePath;
	static Logger log = LoggerHelper.getLogger(Leanft.class);

	public static void configMainframe() {
		/* reading from cmdline */
		String cmdMainframe = System.getProperty("mainframe");

		if (cmdMainframe != null) {
			mainframe = cmdMainframe;
		} else {
			/* reading from property file */
			mainframe = GlobalVariables.configProp.getProperty("mainframe");
		}

	}

	public static void launchTerminal() {
		// close any existing terminal windows
		String processName = "pcws.exe";
		try {
			if (isProcessRunning(processName)) {
				killProcess(processName);
			}
			/* creating batch file command */
			//createLaunchBatch("LaunchIBMPCOMM_3270.bat");

			// launch terminal emulator and connect
			//Runtime runtime = Runtime.getRuntime(batchFilePath);
		} catch (Exception e) {
			log.debug("!!!IBP COMM not started", e);
			Assert.fail("!!!IBP COMM not started");
		}
	}

	/**
	 * updating IBM PCOMM workstation file 'Defaultkeyboard' setting with(.kmp) file
	 * location To avoid emulator(keyboard) popup to appear should be called only
	 * once before starting execution
	 */
	public static void updateDefaultKeyboard_IBMPCOM_WS() {
		String filePath = "./src\\test\\resources\\mf\\launcher\\"+GlobalVariables.configProp.getProperty("mainframe_ws");
		StringBuffer content = new StringBuffer();
		BufferedReader bufferReader = null;
		boolean isExists = FileManager.isFileExists(filePath);
		if (isExists) {
			try {
				bufferReader = new BufferedReader(new FileReader(filePath));
				String line = null;
				while ((line = bufferReader.readLine()) != null) {
					if (line.matches("DefaultKeyboard=.*")) {
						content.append("DefaultKeyboard=" + System.getProperty("user.dir")
								+ "/src/test/resources/mf/launcher/"+GlobalVariables.configProp.getProperty("mainframe_kmp")).append("\n");
					} else {
						content.append(line).append("\n");
					}
				}
			} catch (Exception e) {
				ExceptionHandler.handleException(e);
				e.printStackTrace();
			} finally {
				try {
					bufferReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		FileManager.write(filePath, content.toString());
	}

	/* batch file cmd */
	public static void createLauncherBatch(String fileName) {
		String userDir = System.getProperty("user.dir");
		batchFilePath = userDir + "/src/test/resources/mf/launcher/" + fileName;
		String terminalFilePath = userDir + "/src/test/resources/mf/launcher/"+GlobalVariables.configProp.getProperty("mainframe_ws");
		FileManager.write(batchFilePath, terminalFilePath);
	}

//check if process is running
	public static boolean isProcessRunning(String serviceName) throws Exception {
		Process p = Runtime.getRuntime().exec(TASKLIST);
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			if (line.contains(serviceName)) {
				return true;
			}

		}
		return false;
	}

	/*-----------------------------------------------------------------------------------*/
//kill the specific process with servicename
	public static void killProcess(String serviceName) throws Exception {
		Runtime.getRuntime().exec("taskKill /F /IM" + serviceName);
	}

	/**
	 * Launch mainframe terminal enter 'netvac'
	 */
/*	public static void launchTerminalAndSetWindow() {
		if (mainframe.trim().equalsIgnoreCase("true")) {
			try {
				LaunchMainframeTerminal.launchTerminal();
			} catch (Exception e) {
				ExceptionHandler.handleException(e);
			}
			/* setting terminal window */
			/*FR_MF_MainframeWindow.setTeWindow();
		}
	}
/**
 * disconnect mainframe
 */
/*	public static void disconnect() {
Screen genericScr;
try {
	if(mainframe.trim().equalsIgnoreCase("true")) {
		PropertyFileUtils property=new PropertyFileUtils(
				System.getProperty("user.dir")+"/src/test/resources/mf/launcher/mainframe.properties");
		String terminalLoginCmd=property.getProperty("terminalLoginCmd");
		terminalLoginCmd=terminalLoginCmd.toLowerCase().trim();
		genericScr=FR_MF_MainframeWindow.getTeWindow().describe(Screen.class,
				new ScreenDescription.Builder().label(new RegExpProperty(".*")).build());
		switch(terminalLoginCmd) {
		case "netvac":
			disconnectNETVAC(genericScr);
			break;
		case "tsod":
			disconnectTSOD(genericScr);
			break;
			default:
				Assert.fail("!!!Terminal can not be disconnected.Unknown command"+terminalLoginCmd);
		}
	}
}catch(Exception e) {
	ExceptionHandler.handleException(e);
}
	}
	/*public static void disconnectNETVAC(Screen genericScr) {
		try {
			/*Terminal will get disconnetc whwn press f12
			 * terminal login screen
			 */
			/*if(new FR_MF_Emp01Scr.isEmp01ScrExists(3)||
					new FR_MF_MainframeLoginScr.isTerminalLoginScrExists(3)) {
				LeanftAction.sendTEKeys(genericScr, Keys.PF12);
			}else {
				LeanftAction.sendTEKeys(genericScr, Keys.PF12);
				LeanftAction.sendTEKeys(genericScr, Keys.PF12);
			}
			Thread.sleep(5000);
				
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}
	}*/
	/**
	 * Disconnecting tsod
	 */
	/*public static void disconnectTSOD(Screen genericScr) {
		int counter=0;
		FR_MF_ReadyScr objReadyScr=new FR_MF_ReadyScr();
		try {
			if(mainframe.trim().equalsIgnoreCase("true")) {
				while(counter<5) {
					if(objReadyScr.isScreenExists()) {
						objReadyScr.setReadyField("logoff",Keys.ENTER);
						Thread.sleep(2000);
						break;
					}else {
						genericScr.sendTEKeys(Keys.PF3);
					}
					counter+=1;
				}
			}
			Thread.sleep(3000);
		}catch(Exception e) {
			ExceptionHandler.handleException(e);
		}
	}
/**closing terminal window
 * Note:here stdwin.window is used
 * Leanft:setting should have 'winfroms' and 'Terminal Emulators'both checked 
 */
	/*public static void closeTerminalWindow() {
try {
	com.hp.lft.sdk.stdwin.Window x80Window =com.hp.lft.sdk.Desktop.describe(com.hp.lft.sdk.stdwin.Window.class,
			new com.hp.lft.sdk.stdwin.WindowDescription.Builder().childWindow(false).ownedWindow(false).windowClassRegExp("PCSWS:Main:00400000").windowTitleRegExp(" [24x80]").build());
	x80Window.close();
}catch(Exception e) {
	ExceptionHandler.handleException(e);
}
	}*/

}

