package qa.framework.excelasdb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

/**
 * Helps to query Excel as DataBase <br>
 * <b>Query Examples:</b>
 * <ul>
 * <li><em>Select Query:</em> "Select * from Sheet1 where ID=100 and
 * name='John'"</li>
 * 
 * <li><em>Update Query:</em> "Update Sheet1 Set Country='US' where ID=100 and
 * name='John'"</li>
 * 
 * <li><em>Insert Query:</em> "INSERT INTO sheet4(Name,Country)
 * VALUES('Peter','UK')"</li>
 * 
 * <li><em>Multiple Where Clause:</em> "Select * from Sheet1 where
 * column1=value1 and column2=value2 and column3=value3"</li>
 * 
 * <li><em>Like:</em> "Select * from Sheet1 where Name like 'Cod%'"</li>
 * </ul>
 * 
 * <b>Basic Code Usage:</b>
 * <p>
 * ExcelFilloUtils obj = new
 * ExcelFilloUtils("./src/test/resources/petstore/api/excels/CreatePet.xlsx");
 * <br>
 * obj.executeQuery("Select * from Valid where Scenario='Valid_CreatePet_01'");
 * <br>
 * List parseRecordSet = obj.parseRecordSet(); <br>
 * obj.close();
 * </p>
 * 
 * 
 * <b>Reference:</b>
 * <ul>
 * <li>http://codoid.com/fillo/</li>
 * </ul>
 * 
 * 
 * @author 10650956
 *
 */
public class ExcelFilloUtils {

	private Fillo fillo;
	private Connection connection;
	private Recordset recordSet;

	/**
	 * Set excel
	 * 
	 * @param filePath : String
	 */
	public ExcelFilloUtils(String filePath) {
		this.fillo = new Fillo();
		try {

			connection = fillo.getConnection(filePath);

		} catch (FilloException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Execute Select Query
	 * 
	 * @author 10650956
	 * @param query : String
	 * @return Recordset
	 * @throws FilloException
	 */
	public void exeSelectQuery(String query, Operation operation) throws FilloException {

		switch (operation) {
		
		case INSERT:
		case UPDATE:
			connection.executeUpdate(query);
			break;
		case SELECT:
			this.recordSet = connection.executeQuery(query);
			break;
		
		}

	}
	
	/**
	 * Create a table
	 * 
	 * @author 10650956
	 * @param name
	 * @throws FilloException
	 */
	public void createTable(String name) throws FilloException {
		connection.createTable(name);
	}
	
	/**
	 * Delete a table
	 * 
	 * @author 10650956
	 * @param name
	 * @throws FilloException
	 */
	public void deleteTable(String name) throws FilloException {
		connection.deleteTable(name);
	}

	/**
	 * Get total record count
	 * 
	 * @author 10650956
	 * @return
	 */
	public int getRecordCount() {
		try {
			return recordSet.getCount();
		} catch (FilloException e) {

			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Index start from 0. Return column name
	 * 
	 * @author 10650956
	 * @param index : Integer
	 * @return String
	 */
	public String getColumnName(int index) {

		return getColumnNames().get(index);

	}

	/**
	 * Get list of Columns
	 * 
	 * @author 10650956
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getColumnNames() {
		try {
			return recordSet.getFieldNames();
		} catch (FilloException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get record count
	 * 
	 * @author 10650956
	 * @return : Integer
	 */
	public int getColumnCount() {
		try {
			return recordSet.getFieldNames().size();
		} catch (FilloException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Converting Record Set into List of Map<String,String>
	 * 
	 * @author 10650956
	 * @return List<Map<String,String>>
	 */
	public List<Map<String, String>> parseRecordSet() {

		List<Map<String, String>> records = new ArrayList<Map<String, String>>();

		int columnCount = getColumnCount();
		ArrayList<String> columnNames = getColumnNames();

		try {

			while (recordSet.next()) {
				Map<String, String> map = new HashMap<String, String>();

				for (int index = 0; index < columnCount; index++) {
					String columnName = columnNames.get(index);
					map.put(columnName, recordSet.getField(columnName));
				}

				records.add(map);

			}
		} catch (FilloException e) {
			e.printStackTrace();
		}

		return records;
	}

	/**
	 * Closing
	 * 
	 * @author 10650956
	 */
	public void close() {

		if (recordSet != null) {
			recordSet.close();
		}

		connection.close();
	}

}
