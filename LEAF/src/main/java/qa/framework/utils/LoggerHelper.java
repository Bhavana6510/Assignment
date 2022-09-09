package qa.framework.utils;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerHelper {
	private static boolean root = false;
	public static String logPath;
	private static String logFilePath;
	
	public synchronized static void createLogFolder() {
		String folderName = CalendarUtils.getTimeStamp("dd MMM yy_HH mm ss");
		//logPath = userDir + "./logs/" + folderName;
		logPath = "./logs/" + folderName;
		logFilePath = logPath + "/logs.log";
		new File(LoggerHelper.logPath).mkdir();
	}

	public synchronized static Logger getLogger(Class<?> cls) {

		String log4jPropertyFilePath = "./src/main/resources/log4j.properties";
		if (!root) {

			LoggerHelper.createLogFolder();
			
			try {
				
				PropertyFileUtils pro = new PropertyFileUtils(log4jPropertyFilePath);
				try {
					pro.setProperty("logFolder", logPath);
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//configLog4jPropertyFile(log4jPropertyFilePath, logPath);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			PropertyConfigurator.configure(log4jPropertyFilePath);
			root = true;
			return Logger.getLogger(cls);

		} else {
			return Logger.getLogger(cls);
		}

	}

	public static void shutdown() {
		org.apache.log4j.LogManager.shutdown();
		
		String log4jPropertyFilePath = "./src/main/resources/log4j.properties";
		PropertyFileUtils pro = new PropertyFileUtils(log4jPropertyFilePath);
		try {
			pro.setProperty("logFolder", "");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getGetLogFilePath() {
		return logFilePath;
	}
}
