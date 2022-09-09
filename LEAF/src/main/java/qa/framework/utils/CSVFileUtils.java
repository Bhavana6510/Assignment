package qa.framework.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BathriYO
 */
public class CSVFileUtils {
	private static CSVFileUtils _obj;

	private CSVFileUtils() {
	}

	public static CSVFileUtils getInstance() {
		return _obj == null ? _obj = new CSVFileUtils() : _obj;

	}

	/**
	 * Reads CSV files and store it as list of array
	 * 
	 * @author BathriYO
	 * @param filePath
	 * @param delimiter
	 * @return List<String[]>
	 */
	public List<String[]> readAll(String filePath, String delimiter) {
		BufferedReader bufReader = null;
		List<String[]> readAll = new ArrayList<String[]>();
		try {
			bufReader = new BufferedReader(new FileReader(filePath));
			String line;
			while ((line = bufReader.readLine()) != null) {
				readAll.add(line.split(delimiter));
			}
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
		return readAll;
	}

	/**
	 * returns string cell value in a row
	 * 
	 * @author BathriYO
	 * @param List
	 * @param row
	 * @param column
	 * @return String
	 */
	public String getValue(List<String[]> list, int row, int column) {
		return list.get(row)[column];
	}

	/**
	 * returns all values in column
	 * 
	 * @author BathriYO
	 * @param List
	 * @param column
	 * @return List<String>
	 */
	public List<String> getColumnValues(List<String[]> list, int column) {
		List<String> columnValues = new ArrayList<String>();
		for (String[] arr : list) {
			columnValues.add(arr[column]);
		}
		return columnValues;
	}
}
