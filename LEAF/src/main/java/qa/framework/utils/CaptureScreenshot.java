package qa.framework.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;

import io.appium.java_client.AppiumDriver;
import qa.framework.desktop.DesktopDriverManager;
import qa.framework.device.DeviceDriverManager;
import qa.framework.webui.browsers.WebDriverManager;

public class CaptureScreenshot {
	static String format = "png";
	public static String imageFolderPath = null;

	private static int count = 0;

	private static Logger log = LoggerHelper.getLogger(CaptureScreenshot.class);
	
	
	/**
	 * capture browser screen shot
	 * 
	 * @author BathriYo
	 * @param fileName
	 * @return String filePath
	 * @throws IOException 
	 */
	public static synchronized String takeScreenshot(String fileName) throws IOException {
		
		String screenshotName="";
		
		if(fileName!=null) {
			screenshotName=fileName;
			
		}else {
			screenshotName = "screenshot" + (++count);
		}
		
		PropertyFileUtils extentPro = new PropertyFileUtils("./src/test/resources/extent.properties");
		String sparkDirPath = "./" + extentPro.getProperty("extent.reporter.spark.out");

		
		
		TakesScreenshot ts = (TakesScreenshot)WebDriverManager.getDriver();
		
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File(sparkDirPath+"/"+screenshotName + ".png");
		
		FileUtils.copyFile(src, dest);
		

		/* extension .png is hard coded in Shuttherbug library */
		return screenshotName + ".png";
	}
	
	
	
	
	/**
	 * capture browser screen shot
	 * 
	 * @author BathriYo
	 * @param fileName
	 * @return String filePath
	 */
	public static synchronized String screenCapture(String fileName) {
		
		String screenshotName="";
		
		if(fileName!=null) {
			screenshotName=fileName;
			
		}else {
			screenshotName = "screenshot" + (++count);
		}
		
		PropertyFileUtils extentPro = new PropertyFileUtils("./src/test/resources/extent.properties");
		String sparkDirPath = "./" + extentPro.getProperty("extent.reporter.spark.out");

		Shutterbug.shootPage(WebDriverManager.getDriver(), true).withName(screenshotName)
				.save(sparkDirPath);

		/* extension .png is hard coded in Shuttherbug library */
		return screenshotName + ".png";
	}

	/**
	 * Takes entire web application screenshot
	 * 
	 * @return String: name of screenshot
	 */
	public static synchronized String captureEntireScreen() {

		String screenshotName = "screenshot" + (++count);

		PropertyFileUtils extentPro = new PropertyFileUtils("./src/test/resources/extent.properties");
		String sparkDirPath = "./" + extentPro.getProperty("extent.reporter.spark.out");

		Shutterbug.shootPage(WebDriverManager.getDriver(), ScrollStrategy.WHOLE_PAGE, true).withName(screenshotName)
				.save(sparkDirPath);

		/* extension .png is hard coded in Shuttherbug library */
		return screenshotName + ".png";
	}

	/**
	 * Takes Desktop screenshot
	 * 
	 * @author 10650956
	 * @return
	 */
	public static String desktopScreenCapture(String fileName) {
		String screenshotName=null;
		
		if(fileName==null) {
			screenshotName = "screenshot" + (++count)+".png";	
		}else {
			screenshotName=fileName;
		}
		
		PropertyFileUtils extentPro = new PropertyFileUtils("./src/test/resources/extent.properties");
		String sparkDirPath = "./" + extentPro.getProperty("extent.reporter.spark.out");

		String dest = sparkDirPath +"/"+screenshotName;
		
		File src = DesktopDriverManager.getDriver().getScreenshotAs(OutputType.FILE);
		
		try {
			FileUtils.copyFile(src, new File(dest));
		}catch(IOException e) {
			log.info("!!!  Some problem occured while taking Desktop screenshot !!!");
			log.error(e);
			e.printStackTrace();
		}
				
		return screenshotName;

		
	}
	
	/**
	 * Takes screenshot of Device
	 * 
	 * @param fileName
	 * @return
	 */
	public static String deviceScreenCapture(String fileName) {
		String screenshotName=null;
		
		if(fileName==null) {
			screenshotName = "screenshot" + (++count)+".png";	
		}else {
			screenshotName=fileName;
		}
		
		PropertyFileUtils extentPro = new PropertyFileUtils("./src/test/resources/extent.properties");
		String sparkDirPath = "./" + extentPro.getProperty("extent.reporter.spark.out");

		String dest = sparkDirPath +"/"+screenshotName;
		
		AppiumDriver<?> driver= (AppiumDriver<?>) DeviceDriverManager.getDriver();
		File src = driver.getScreenshotAs(OutputType.FILE);
		
		try {
			FileUtils.copyFile(src, new File(dest));
		}catch(IOException e) {
			log.info("!!!  Some problem occured while taking Desktop screenshot !!!");
			log.error(e);
			e.printStackTrace();
		}
				
		return screenshotName;
		
	}
	

}
