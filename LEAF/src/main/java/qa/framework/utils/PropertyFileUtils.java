package qa.framework.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * 
 * @author BathriYO
 *
 */
public class PropertyFileUtils {
	Properties properties;
	String filepath;

	/**
	 * This constructor loads property file
	 * 
	 * @param PropertyFilePath
	 */
	public PropertyFileUtils(String propertyFilePath) {
		BufferedReader reader;
		this.filepath = propertyFilePath;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			properties.load(reader);
			reader.close();
		} catch (FileNotFoundException e) {
			ExceptionHandler.handleException(e);
		} catch (IOException e1) {
			ExceptionHandler.handleException(e1);
		}
	}

	/**
	 * @return properties
	 */
	public String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}
	
	
	public Set<String> getAllKeys() {
		Set<String> keySet = new HashSet<String>();
		properties.keySet().forEach(x->{
		keySet.add(String.valueOf(x));
		});
		
		return keySet;
	}
	

	/**
	 * 
	 * 
	 * Set key value in property file
	 * 
	 * @param propertyName
	 * @param propertyValue
	 * @throws ConfigurationException 
	 */
	public void setProperty(String propertyName, String propertyValue) throws ConfigurationException {
		
		PropertiesConfiguration conf = new PropertiesConfiguration(filepath);
		conf.setProperty(propertyName, propertyValue);
		conf.save();
		
		/*
		 * FileOutputStream fileOut = null; try { fileOut = new
		 * FileOutputStream(this.filepath); properties.setProperty(propertyName,
		 * propertyValue); properties.store(fileOut, "Property file updated with key:" +
		 * propertyName + "value" + propertyValue); } catch (Exception e) {
		 * ExceptionHandler.handleException(e); } finally { try { fileOut.close(); }
		 * catch (Exception e) { ExceptionHandler.handleException(e); } }
		 */
	}
	
	
}
