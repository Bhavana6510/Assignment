package qa.framework.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import qa.framework.utils.LoggerHelper;

public class DBManager {

	private Connection connection;
	private ResultSet resultSet;

	private Logger log = LoggerHelper.getLogger(DBManager.class);

	/**
	 * Step 1/3 Connect to DB
	 * 
	 * @author 10650956
	 * @param dbType
	 * @param host
	 * @param port
	 * @param databaseName
	 * @param username
	 * @param password
	 */
	public void connect(DBType dbType, String host, String port, String databaseName, String username,
			String password) {
		String connectionString = null;

		switch (dbType) {
		case POSTGRE:
		case REDSHIFT: {
			connectionString = "jdbc:postgresql://" + host + ":" + port + "/" + databaseName + "?" + "user=" + username
					+ "&" + "password=" + password;
			break;
		}

		case SQLSERVER: {
			// "jdbc:sqlserver://hostxxxxx\\sQLxxxx;DatabaseName=xxx;user=xxxx;password=xxx";
			/* NOTE: SQLSERVER may not need port. PORT is missing in above statement */
			connectionString = "jdbc:sqlserver://" + host + ";" + "DatabaseName=" + databaseName + ";" + "user="
					+ username + ";" + "password=" + password;
			break;
		}

		case MYSQL: {
			connectionString = "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?" + "user=" + username + "&"
					+ "password=" + password;
			break;
		}

		case ORACLE_THIN: {
			// jdbc:oracle:<driver_type>:<user>/<password>@<host>:<port>:<db_name>
			connectionString = "jdbc:oracle:thin:" + username + "/" + password + "@" + host + ":" + port + ":"
					+ databaseName;
			break;
		}
		case ORACLE_OCI: {
			// jdbc:oracle:<driver_type>:<user>/<password>@<host>:<port>:<db_name>
			connectionString = "jdbc:oracle:oci:" + username + "/" + password + "@" + host + ":" + port + ":"
					+ databaseName;
			break;
		}
		case DB2: {

			// REF:
			// https://www.ibm.com/support/knowledgecenter/SSEPGG_11.5.0/com.ibm.db2.luw.apdv.java.doc/src/tpc/imjcc_r0052341.html
			// jdbc:db2//<host>:<port>/<database_name>:user=<username>;password=<password>;
			connectionString = "jdbc:db2//" + host + ":" + port + "/" + databaseName + ":" + "user=" + username + ";"
					+ "password=" + password + ";";
			break;
		}

		case IRIS: {
			// jdbc:IRIS://<host>:<port>/<namespace>/<options>?username=<username>&password=<password>
			connectionString = "jdbc:IRIS://" + host + ":" + port + "/" + databaseName + "?" + "username=" + username
					+ "&" + "password=" + password;
			break;
		}

		}// switch end

		try {
			this.connection = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			log.info(" Failed to Connect to DB: " + dbType);
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * Step 2/3: Execute Select Query
	 * 
	 * @author 10650956
	 * @param sqlQuery
	 * @return
	 */
	public ResultSet selectQuery(String sqlQuery) {

		try {
			resultSet = connection.createStatement().executeQuery(sqlQuery);
		} catch (SQLException e) {
			log.info(" Failed to execute Select query " + sqlQuery);
			log.error(e);
			e.printStackTrace();
		}
		return resultSet;
	}

	public ResultSet selectQueryScrollableResultSet(String sqlQuery) {
		try {
			resultSet = connection
					.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery(sqlQuery);
		} catch (SQLException e) {
			log.info(" Failed to execute Select query " + sqlQuery);
			log.error(e);
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Step 3/3: close DB connection
	 * 
	 * @author 10650956
	 */
	public void disconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			log.info(" Failed to Disconnect DB connection");
			log.error(e);
			e.printStackTrace();
		}

	}

	/**
	 * Get Column Count
	 * 
	 * @author 10650956
	 * @return
	 */
	public int getColumnCount() {
		try {
			return resultSet.getMetaData().getColumnCount();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}

		return -1;
	}

	/**
	 * Get Column names
	 * 
	 * @author 10650956
	 * @return Set<String>
	 */
	public Set<String> getColumnNames() {
		Set<String> columnName = new HashSet<String>();

		int columnCount = getColumnCount();

		for (int index = 1; index <= columnCount; index++) {
			try {
				columnName.add(resultSet.getMetaData().getColumnName(index));
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
			}
		}

		return columnName;
	}

	public List<Map<String, String>> parseResultSet() {

		ResultSet resultSet = this.resultSet;

		int columnCount = getColumnCount();

		List<Map<String, String>> parseResultSet = new ArrayList<Map<String, String>>();

		try {

			while (resultSet.next()) {
				Map<String, String> map = new HashMap<String, String>();

				for (int index = 1; index <= columnCount; index++) {
					String columnTypeName = resultSet.getMetaData().getColumnTypeName(index).toLowerCase();
					String columnName = resultSet.getMetaData().getColumnName(index);
					String columnValue = null;

					switch (columnTypeName) {
					case "char":
					case "varchar":
					case "nvarchar": {
						/*
						 * toString() is added to handle null value for DB. For null, toString() will
						 * throw NullPointerException
						 */
						try {
							columnValue = resultSet.getString(columnName).toString();
						} catch (Exception e) {
							columnValue = null;
						}
						break;
					}
					case "numeric":
					case "double":
					case "integer":
					case "float":{
						/*2 is for numeric value form db*/
						columnValue = String.valueOf(resultSet.getDouble(columnName));
						break;
					}
					case "int":{
						columnValue = String.valueOf(resultSet.getInt(columnName));
						break;
					}
					case "date":{
						/*91 is for data value from db*/
						columnValue = String.valueOf(resultSet.getDate(columnName));
						break;
					}
					case "time":{
						/*93 is for time value from db*/
						columnValue =resultSet.getTime(columnName).toString();
						break;
						 
					}
					case "timestamp":{
						columnValue = resultSet.getTimestamp(columnName).toString();
						break;
					}
					case "datetime":{
						try {
							columnValue = resultSet.getDate(columnName).toString();	
						}catch(NullPointerException e) {
							columnValue = null;
						}
						break;
					}
					case "tinyint":
					case "boolean":{
						columnValue = String.valueOf(resultSet.getBoolean(columnName));
						break;
						
					}
					default:{
						log.error("!!! Column type is NOT available in switch statement: "+columnTypeName);
					}
					}// switch- end
					map.put(columnName,columnValue);
				}// for - end
				
				parseResultSet.add(map);
				
			} // while - end
			
			return parseResultSet;
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		
		return null;
	}

}
