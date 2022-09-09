package qa.testng.generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

/*This a main class which will be called by maven(check pom.xml)*/
/*Note:maven will not pick this class if it is outside src/main/java*/
public class GenerateTestNGXml {
	/**
	 * Getting thread count from command line if not thread count is specified by
	 * default thread count will be 1
	 * 
	 * @return int count
	 */
	public static int getThreadCount() {
		/* reading from cmdline */
		String cmdThreadCount = System.getProperty("threadCount");
		if (cmdThreadCount != null) {
			return Integer.parseInt(cmdThreadCount);
		} else {
			return 1;
		}
	}

	/**
	 * Getting list of xml packages from scriptpackage.txt file
	 * 
	 * @return list
	 */
	public static List<XmlPackage> listXmlPackages() {
		String line = null;
		BufferedReader bufferedReader = null;
		List<XmlPackage> listXmlPackages = new ArrayList<XmlPackage>();
		try {
			bufferedReader = new BufferedReader(
					new FileReader("./src/main/java/qa/testng/generator/ScriptPackage.txt"));

			while ((line = bufferedReader.readLine()) != null) {
				listXmlPackages.add(new XmlPackage(line.trim()));
			}
		} catch (Exception e) {

		} finally {
			try {
				bufferedReader.close();
			} catch (Exception e) {

			}
		}
		return listXmlPackages;
	}

	/**
	 * Getting list of include groups
	 * 
	 * @return list
	 */
	public static List<String> getListOfIncludeGroup() {
		List<String> listIncludeGroups = new ArrayList<String>();
		String cmdGroups = System.getProperty("testNG.options");
		if (cmdGroups != null) {
			String[] split = cmdGroups.split(",");
			for (String group : split) {
				// checking with group contains tilt or not
				if (!group.contains("~")) {
					// tilt is used to define group which need to be excluded
					listIncludeGroups.add(group);
				}
			}
		}
		return listIncludeGroups;
	}

	/**
	 * Getting list of include groups
	 * 
	 * @return list
	 */
	public static List<String> getListOfExcludeGroup() {
		List<String> listExcludeGroups = new ArrayList<String>();
		String cmdGroups = System.getProperty("testNG.options");
		if (cmdGroups != null) {
			String[] split = cmdGroups.split(",");
			for (String group : split) {
				// checking with group contains tilt or not
				if (!group.contains("~")) {
					// removing tilt from group name
					listExcludeGroups.add(group.replace("~", ""));
				}
			}
		}
		return listExcludeGroups;
	}

	/**
	 * write to file
	 * 
	 * @param filePath
	 * @param writestr
	 */
	public static void write(String filePath, String writeStr) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(writeStr);
			writer.close();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public static void main(String args[]) {
		System.out.println("!!! Main class executed !!!");
		/* create an instance of xml suite and assign a name for it */
		XmlSuite suite = new XmlSuite();
		suite.setName("Suite");
		/* create an instance of xml test and assign to it */
		XmlTest test = new XmlTest(suite);
		test.setName("Test");
		/* settings method as parallel */
		test.setParallel(XmlSuite.ParallelMode.METHODS);
		/* setting thread count provided in cmd line */
		test.setThreadCount(getThreadCount());
		/* adding list of xml packages to test */
		test.setXmlPackages(listXmlPackages());
		/* adding include groups */
		List<String> listIncludeGroups = getListOfIncludeGroup();
		/* adding exclude groups */
		List<String> listExcludeGroups = getListOfExcludeGroup();
		/* adding include group in test if any */
		if (listIncludeGroups.size() > 0) {
			test.setIncludedGroups(listIncludeGroups);
		}
		/* adding exclude group in test if any */
		if (listExcludeGroups.size() > 0) {
			test.setExcludedGroups(listIncludeGroups);
		}
		String xml = suite.toXml();
		/*
		 * only one testng.xml will be maintained testng.xml will be form at run time
		 * and will be triggered from maven(check pom.xml)
		 */
		write("./testng.xml", xml);
	}

}
