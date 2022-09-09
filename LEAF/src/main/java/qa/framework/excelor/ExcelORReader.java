package qa.framework.excelor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import qa.framwork.excel.ExcelOperation;
import qa.framwork.excel.ExcelUtils;

public class ExcelORReader implements Connection {

	String filePath;

	public ExcelORReader(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Executing Where Clause
	 * 
	 * @author 10650956
	 * @param table      : String - sheet name
	 * @param conditions : String - where condition
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> whereClause(String table, String conditions) {

		ExcelUtils objExcelUtils = new ExcelUtils(ExcelOperation.LOAD, filePath);
		XSSFSheet sheet = objExcelUtils.getSheet(table);

		if (conditions == null || conditions.length() == 0) {
			List<Map<String, Object>> mappedExcelData = objExcelUtils.getMappedExcelData(sheet, 0);
			objExcelUtils.closeWorkBook();

			return mappedExcelData;
		}

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		String[] andConditions = conditions.replace("'", "").split("AND");

		int rowCount = objExcelUtils.getRowCount(sheet);

		for (int row = 1; row < rowCount; row++) {

			boolean isConditionSatisfied = false;

			Map<String, Object> tempData = objExcelUtils.getMappedRowData(sheet, 0, row);

			for (String condition : andConditions) {

				String[] split = condition.split("=");
				String columnName = split[0];
				String columnValue = split[1];

				if (!tempData.get(columnName).toString().equalsIgnoreCase(columnValue)) {
					isConditionSatisfied = false;
					break;
				} else {
					isConditionSatisfied = true;
				}

			}

			if (isConditionSatisfied) {
				dataList.add(tempData);
			}

		}
		objExcelUtils.closeWorkBook();
		return dataList;
	}

	private String reframeSelectQuery(String select) {
		boolean inBracket = false;

		String temp = "";

		char[] charArray = select.toCharArray();

		for (char letter : charArray) {

			if (letter == '(') {
				temp = temp + letter;
				inBracket = true;
			} else if (letter == ')') {
				temp = temp + letter;
				inBracket = false;
			} else if (letter != ',') {
				temp = temp + letter;
			} else if (letter == ',') {
				if (inBracket) {
					temp = temp + letter;
				} else {
					temp = temp + ";";
				}
			}
		}
		return temp;
	}

	/**
	 * 
	 * @param select  : String - select condtion
	 * @param records : List<Map<String, Object>>
	 * @return List<Map<String, Object>>
	 */
	private Object selectClause(String select, List<Map<String, Object>> records) {
		// ElementKey, ifNull(ElementValue,Test) Alias, ElementValueType

		if (records.size() == 0) {
			return "No Record Found";
		}

		if (select.startsWith("*")) {
			return records;
		} else {

		}

		List<Map<String, Object>> selectRecord = new ArrayList<Map<String, Object>>();
		/* changing comma outside bracket to semi-comma for split */
		String[] columns = reframeSelectQuery(select).split(";");

		/* storing alias */
		Map<String, String> columnAliasMap = new HashMap<String, String>();
		for (String column : columns) {
			/*
			 * column alias is always given after space after column name. for example:
			 * ElementKey DBKey
			 */
			column = column.trim();
			if (column.contains(" ")) {
				String[] split=null;
				
				if(column.startsWith("ifnull") || column.startsWith("IFNULL")) {
					
					String tempColumn=new String();
					
					char[] charArray = column.toCharArray();
					boolean openBracket=false;
					
					/*splitting column by space only present outside bracket eg. IFNULL( Column, Column) Alias*/
					for(char ch: charArray) {
						if(ch=='(') {
							
							openBracket=!openBracket;
							tempColumn=tempColumn+"(";
							
						}else if (ch==')') {
							
							openBracket=!openBracket;
							tempColumn=tempColumn+")";
							
						}else if(ch==' ' && openBracket==true) {
							/*above if condition means that space is inside bracket*/
							tempColumn=tempColumn+" ";
						}else if(ch==' ' && openBracket==false) {
							/*above if condition means that space is outside bracket*/
							tempColumn=tempColumn+";";
							/*replacing space outside bracket with semi-colon ; */
						}else {
							tempColumn=tempColumn+ch;
						}				
					}
					
					split = tempColumn.split(";");
							
					
				}else {
					/*if column name doesn't contains IFNULL then direct split by space*/
					split = column.split(" ");	
				}
				

				/* column name and its alias */
				columnAliasMap.put(split[0], split[1]);
			} else {
				columnAliasMap.put(column, column);
			}
		}

		records.forEach(record -> {
			Map<String, Object> temp = new HashMap<String, Object>();

			columnAliasMap.forEach((column, alias) -> {
				/* checking if column contain function */
				/* currently hard coded to 'ifnull' function */
				if (column.startsWith("IFNULL")) {
					Map<String, Object> ifnull = new Functions().ifnull(column, record);

					temp.put(alias, ifnull.get(column));

				} else {
					temp.put(alias, record.get(column));
				}

			});

			selectRecord.add(temp);

		});

		return selectRecord;
	}

	@Override
	public Object runSelectQuery(String query) {

		String whereCondition;
		String selectCondition;

		ParseQuery objParseQuery = new ParseQuery();
		objParseQuery.parseQuery(query);

		whereCondition = objParseQuery.getWhere();
		selectCondition = objParseQuery.getSelect();

		return selectClause(selectCondition, whereClause(objParseQuery.getFrom(), whereCondition));

	}

	@Override
	public int runInsertQuery(String query) {

		return 0;
	}

	@Override
	public int runDeleteQuery(String query) {

		return 0;
	}

	@Override
	public int runUpdateQuery(String query) {

		return 0;
	}

}
