package qa.framwork.excel;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;
import org.testng.Assert;

import qa.framework.utils.ExceptionHandler;

/**
 * Utility to work on excels <br>
 * 
 * <b>Example:</b> <br>
 * Below example illustrate who to set cell data and cell style in excel <br>
 * <br>
 * ExcelUtils obj = new ExcelUtils(ExcelOperation.LOAD, "dummy.xlsx"); <br>
 * XSSFSheet sheet = obj.getSheet("Sheet1"); <br>
 * obj.setCellData(sheet, 2, 2, "value"); <br>
 * <br>
 * obj.setStyle(sheet, 2, 2) <br>
 * .setCellBorder(CellBorder.ALL, BorderStyle.THIN, Color.BLACK) <br>
 * .setAlignment(HorizontalAlignment.LEFT, VerticalAlignment.TOP) <br>
 * .setBackgroundColor(Color.CYAN, FillPatternType.SOLID_FOREGROUND) <br>
 * .setFontStyle(Color.RED, true, true, true) <br>
 * .build();<em>Mandatory</em> <br>
 * <br>
 * obj.write(); <em>Mandatory</em> <br>
 * obj.closeWorkBook();<em>Mandatory</em>
 * 
 * @author 10650956
 *
 */
public class ExcelUtils {
	private String filePath;
	private XSSFWorkbook workbook;
	private XSSFFormulaEvaluator evaluator;

	private Cell cell;
	private XSSFCellStyle style;

	/*
	 * do NOT create this variable to sheet. otherwise user will be restricted with
	 * only sheet per object
	 */

	public ExcelUtils(ExcelOperation operation, String filePath) {
		this.filePath = filePath;
		if (operation.getOpeartion().equalsIgnoreCase("load")) {
			loadExcel();
		} else if (operation.getOpeartion().equalsIgnoreCase("create")) {
			createExcel();
		}

		this.evaluator = workbook.getCreationHelper().createFormulaEvaluator();
	}

	/**
	 * creates a new excel in provided filepath(includes filename with extension)
	 * 
	 * @author BathriYO
	 */
	private void createExcel() {
		try {
			File newExcel = new File(this.filePath);
			newExcel.createNewFile();
			this.workbook = new XSSFWorkbook();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	/**
	 * creates sheet
	 * 
	 * @author BathriYO
	 * @param sheetName
	 * @return XSSFSheet
	 */
	public XSSFSheet createSheet(String sheetName) {
		XSSFSheet sheet = null;
		try {
			sheet = this.workbook.createSheet(sheetName);
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
		return sheet;
	}

	/**
	 * Removes given sheet
	 * 
	 * @author BathriYO
	 * @param sheet
	 */
	public void removeSheet(String sheetname) {
		// do not put code in try catch
		// sometime we may not know if the sheet we want to delete exists or not
		int sheetIndex = workbook.getSheetIndex(sheetname);
		workbook.removeSheetAt(sheetIndex);
	}

	/**
	 * Loads existing excel
	 * 
	 * @author BathriYO
	 */
	private void loadExcel() {
		FileInputStream inpstr = null;
		try {

			/*
			 * this code to added to handle 'ZIP bomb detected" error. Basically to handle
			 * large file
			 */
			ZipSecureFile.setMinInflateRatio(0);

			inpstr = new FileInputStream(this.filePath);
			this.workbook = new XSSFWorkbook(inpstr);
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			try {
				inpstr.close();
			} catch (IOException e) {
				ExceptionHandler.handleException(e);
			}
		}
	}

	/**
	 * set existing sheet in excel
	 * 
	 * @param sheetName
	 * @return XssfSheet
	 */
	public XSSFSheet getSheet(String sheetName) {
		XSSFSheet sheet = null;
		try {
			sheet = this.workbook.getSheet(sheetName);
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
		return sheet;
	}

	/**
	 * get string cell data from excel sheet
	 * 
	 * @param rowNum
	 * @param cellNum
	 * @return string data
	 * @throws IOException
	 */
	public String getStringCellData(XSSFSheet sheet, int rowNum, int cellNum) {
		String cellData = "";
		if (sheet.getRow(rowNum).getCell(cellNum) != null) {
			cellData = sheet.getRow(rowNum).getCell(cellNum).getStringCellValue();
		}
		return cellData;
	}

	/**
	 * Get Excel Row
	 * 
	 * @author 10650956
	 * @param sheet  : XSSFSheet
	 * @param rowNum : Integer
	 * @return Row
	 */
	public Row getRow(XSSFSheet sheet, int rowNum) {
		return sheet.getRow(rowNum);
	}

	/**
	 * Get Excel Cell
	 * 
	 * @param sheet   : XSSFSheet
	 * @param rowNum  : Integer
	 * @param cellNum : Integer
	 * @return Cell
	 */
	public Cell getCell(XSSFSheet sheet, int rowNum, int cellNum) {
		return sheet.getRow(rowNum).getCell(cellNum);
	}

	public Object evaluateCell(XSSFSheet sheet, int rowNum, int cellNum) {
		Cell cell = getCell(sheet, rowNum, cellNum);

		evaluator.evaluateFormulaCell(cell);

		return getCellData(sheet, rowNum, cellNum);
	}

	/**
	 * Calculate entire sheet formula
	 * 
	 * @author 10650956
	 */
	public void evaluteAll() {
		evaluator.evaluateAll();

	}

	/**
	 * Formula will be recalculate once user open the sheet
	 * 
	 * @author 10650956
	 * @param value : Boolean
	 */
	public void reCalculateFormulaOnOpen(boolean value) {

		workbook.setForceFormulaRecalculation(value);

	}

	/**
	 * get value in a cell(String,numeric,boolean)
	 * 
	 * @author BathriYO
	 * @param rowNum
	 * @param cellNum
	 * @return object
	 */
	public Object getCellData(XSSFSheet sheet, int rowNum, int cellNum) {

		Object cellData;
		Cell cell = getCell(sheet, rowNum, cellNum);

		try {

			CellType cellType = sheet.getRow(rowNum).getCell(cellNum).getCellType();

			if (cellType == CellType.FORMULA) {
				/* reassigning the cellType value */
				cellType = cell.getCachedFormulaResultType();
			} /* do not put else for this */

			if (cellType == CellType.STRING) {

				cellData = sheet.getRow(rowNum).getCell(cellNum).getStringCellValue();

			} else if (cellType == CellType.NUMERIC) {

				/* checking if cell data type is Date */
				if (DateUtil.isCellDateFormatted(cell)) {
					cellData = cell.getDateCellValue();
				} else {

					/*
					 * Apache POI does not provide a way to read integer value thus when read always
					 * returns double for example 123 a int value will be read as 123.0
					 */
					String temp = cell.getNumericCellValue() + "";

					/* logic to return int value as int and double value as double */
					/* check if the value is actually a double value or a int value */
					/* splitting the temp value with dot (.) */
					String[] split = temp.split("\\.");
					String fractionValue = split[1];
					/* if fraction contain any number but 0, length will be move than 0 */
					int lenght = fractionValue.replace("0", "").length();

					if (lenght > 0) {
						cellData = Double.parseDouble(temp);
					} else {
						cellData = Integer.parseInt(split[0]);
					}
				}

			} else if (cellType == CellType.BOOLEAN) {
				cellData = (Boolean) sheet.getRow(rowNum).getCell(cellNum).getBooleanCellValue();
			} else if (cellType == CellType._NONE || cellType == CellType.BLANK) {
				cellData = "";
			} else {
				cellData = null;
			}

		} catch (NullPointerException e) {
			cellData = "";
		}
		return cellData;
	}

	/**
	 * get all value in a column
	 * 
	 * @param columnName
	 * @return List<Object>
	 */
	public List<Object> getColumnData(XSSFSheet sheet, int headerRowIndex, String columnName) {
		int columnIndex = -1;
		int rowCount = getRowCount(sheet);
		int rowStartIndex = headerRowIndex + 1;

		List<Object> columnValue = new ArrayList<Object>();
		try {
			/* checking if column exist r not getting column index */
			columnIndex = getCellIndexByCellValue(sheet, headerRowIndex, columnName);
			if (columnIndex > -1) {
				// fetching column data and adding in list
				for (int rowIndex = rowStartIndex; rowIndex < rowCount; rowIndex++) {
					columnValue.add(getCellData(sheet, headerRowIndex, columnIndex));
				}
			} else {
				Assert.fail("column name is not available in excel sheet" + columnName);
			}

		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
		return columnValue;
	}

	/**
	 * Get Row data
	 * 
	 * @author 10650956
	 * @param sheet    : XSSFSheet
	 * @param rowIndex : Integer
	 * @return List<Object>
	 */
	public List<Object> getRowData(XSSFSheet sheet, int rowIndex) {

		List<Object> rowData = new ArrayList<Object>();
		int cellCount = getCellCount(sheet, 0);

		for (int index = 0; index < cellCount; index++) {
			rowData.add(getCellData(sheet, rowIndex, index));
		}

		return rowData;
	}

	/**
	 * Return one single row as mapped with header
	 * 
	 * @author 10650956
	 * @param sheet       : XSSFSheet
	 * @param headerIndex :Integer
	 * @param rowIndex    : Integer
	 * @return Map<String,String>
	 */
	public Map<String, Object> getMappedRowData(XSSFSheet sheet, int headerIndex, int rowIndex) {

		Map<String, Object> rowDataMap = new HashMap<String, Object>();

		List<Object> headerList = getRowData(sheet, headerIndex);
		List<Object> dataList = getRowData(sheet, rowIndex);

		for (int index = 0; index < headerList.size(); index++) {

			String header = String.valueOf(headerList.get(index)).trim();
			rowDataMap.put(header, dataList.get(index));

		}

		return rowDataMap;
	}

	/**
	 * Get entire excel mapped data
	 * 
	 * @author 10650956
	 * @param sheet       : String
	 * @param headerIndex : Integer
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getMappedExcelData(XSSFSheet sheet, int headerIndex) {

		List<Map<String, Object>> allMappedData = new ArrayList<Map<String, Object>>();

		int rowCount = getRowCount(sheet);

		for (int index = headerIndex + 1; index < rowCount; index++) {
			Map<String, Object> aMappedRow = getMappedRowData(sheet, headerIndex, index);

			allMappedData.add(aMappedRow);
		}

		return allMappedData;

	}

	/**
	 * Get Background color hexadecimal code
	 * 
	 * @author 10650956
	 * @param sheet
	 * @param rowNum
	 * @param cellNum
	 * @return
	 */
	public String getBackgroundColorHexCode(XSSFSheet sheet, int rowNum, int cellNum) {

		try {

			return sheet.getRow(rowNum).getCell(cellNum).getCellStyle().getFillForegroundXSSFColor().getARGBHex();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
		return null;

	}

	/**
	 * Set cell background color
	 * 
	 * @author 10650956
	 * @param color
	 * @param fillPatternType
	 * @return
	 */
	public ExcelUtils setBackgroundColor(Color color, FillPatternType fillPatternType) {

		IndexedColorMap indexedColors = workbook.getStylesSource().getIndexedColors();

		style.setFillForegroundColor(new XSSFColor(color, indexedColors));

		style.setFillPattern(fillPatternType);

		return this;
	}

	/**
	 * Set style
	 * 
	 * @author 10650956
	 * @param sheet
	 * @param rownum
	 * @param cellnum
	 * @return
	 */
	public ExcelUtils setStyle(XSSFSheet sheet, int rownum, int cellnum) {
		cell = getCell(sheet, rownum, cellnum);
		style = workbook.createCellStyle();
		return this;
	}

	/**
	 * Set font color
	 * 
	 * @param sheet   : XSSFSheet
	 * @param rownum  : Integer
	 * @param cellnum : Integer
	 * @param color   : awt.Color
	 * @return
	 */
	public ExcelUtils setFontStyle(Color color, boolean bold, boolean italic, boolean underline) {

		XSSFFont font = workbook.createFont();

		if (color != null) {
			IndexedColorMap indexedColors = workbook.getStylesSource().getIndexedColors();
			font.setColor(new XSSFColor(color, indexedColors));
		}

		if (bold == true) {
			font.setBold(true);
		}

		if (italic == true) {
			font.setItalic(true);
		}

		if (underline == true) {
			font.setUnderline(HSSFFont.U_SINGLE);
		}

		style.setFont(font);

		return this;
	}

	public void setFontBold() {

	}

	public void setFontItalic() {

	}

	/**
	 * Set cell border
	 * 
	 * @author 10650956
	 * @param cellBorder
	 * @param borderStyle
	 * @param color
	 * @return
	 */
	public ExcelUtils setCellBorder(CellBorder cellBorder, BorderStyle borderStyle, Color color) {

		/* getting color */
		IndexedColorMap indexedColorMap = workbook.getStylesSource().getIndexedColors();
		XSSFColor xssfColor = new XSSFColor(color, indexedColorMap);

		switch (cellBorder) {
		case ALL:

			style.setBorderTop(borderStyle);
			style.setBorderColor(BorderSide.TOP, xssfColor);

			style.setBorderRight(borderStyle);
			style.setBorderColor(BorderSide.RIGHT, xssfColor);

			style.setBorderBottom(borderStyle);
			style.setBorderColor(BorderSide.BOTTOM, xssfColor);

			style.setBorderLeft(borderStyle);
			style.setBorderColor(BorderSide.LEFT, xssfColor);

			break;

		case TOP:

			style.setBorderTop(borderStyle);
			style.setBorderColor(BorderSide.TOP, xssfColor);

			break;

		case RIGHT:

			style.setBorderRight(borderStyle);
			style.setBorderColor(BorderSide.RIGHT, xssfColor);

			break;

		case BOTTOM:

			style.setBorderBottom(borderStyle);
			style.setBorderColor(BorderSide.BOTTOM, xssfColor);

			break;

		case LEFT:

			style.setBorderLeft(borderStyle);
			style.setBorderColor(BorderSide.LEFT, xssfColor);

			break;
		}

		return this;
	}

	/**
	 * set alignment top, left, right, bottom
	 * 
	 * @author 10650956
	 * @param horizontalAlignment
	 * @param verticalAlignment
	 * @return
	 */
	public ExcelUtils setAlignment(HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment) {

		if (horizontalAlignment != null) {
			style.setAlignment(horizontalAlignment);
		}

		if (verticalAlignment != null) {

			style.setVerticalAlignment(verticalAlignment);
		}

		return this;
	}

	/**
	 * Build style
	 * 
	 * @author 10650956
	 */
	public void build() {
		cell.setCellStyle(style);
		cell = null;
		style = null;
	}

	/**
	 * Get font color
	 * 
	 * @author 10650956
	 * @param sheet   : XSSFSheet
	 * @param rownum  : Integer
	 * @param cellnum : Integer
	 * @return short
	 */
	public short getFontColor(XSSFSheet sheet, int rownum, int cellnum) {
		return workbook.getFontAt(getCell(sheet, rownum, cellnum).getCellStyle().getFontIndexAsInt()).getColor();
	}

	/**
	 * setting string cell value
	 * 
	 * @param rowNum
	 * @param cellNum
	 * @param value
	 * @throws IOException
	 */
	public void setCellData(XSSFSheet sheet, int rowNum, int cellNum, String value) {
		XSSFRow row;
		// if row does not exist
		try {
			row = sheet.getRow(rowNum);
			if (row == null) {
				row = sheet.createRow(rowNum);
			}
		} catch (Exception e) {
			// create row if does not exist
			row = sheet.createRow(rowNum);
		}
		// if cell does not exist then create cell else setcellvalue
		try {
			row.getCell(cellNum).setCellValue(value);
		} catch (Exception e) {
			// create cell if does not exist
			row.createCell(cellNum).setCellValue(value);
		}

	}

	/**
	 * get count of rows in a sheet
	 * 
	 * @return int
	 */
	public int getRowCount(XSSFSheet sheet) {
		int count = 0;
		if (sheet.getPhysicalNumberOfRows() > 0) {
			// getlastrownum method returns index not count thus count++
			count = sheet.getLastRowNum();
		} else {
			Assert.fail("!!! No Row Found !!!");
		}
		return ++count;
	}

	/**
	 * get cell count in a given row
	 * 
	 * @param row
	 * @return int
	 */
	public int getCellCount(XSSFSheet sheet, int rowNum) {
		int count = 0;
		XSSFRow row = sheet.getRow(rowNum);
		if (row.getPhysicalNumberOfCells() > 0) {
			// getlastcellnum returns count
			count = row.getLastCellNum();
		} else {
			Assert.fail("!!! No Cell Found !!!");
		}
		return count;
	}

	/**
	 * get row index bases on cell data
	 * 
	 * @author BathriYO
	 * @param columnNum
	 * @param searchvalue
	 * @return int row index if search value found else -1
	 */
	public int getRowIndexByCellValue(XSSFSheet sheet, int columnNum, String searchValue) {
		try {
			int rowCount = getRowCount(sheet);
			for (int index = 0; index < rowCount; index++) {
				if (getCellData(sheet, index, columnNum).toString().equals(searchValue)) {
					return index;
				}
			}
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
		return -1;
	}

	/**
	 * Return cell number based on row number and search value provided as argument
	 * 
	 * @author BathriYO
	 * @param rowNum
	 * @param searchvalue
	 * @return int
	 */
	public int getCellIndexByCellValue(XSSFSheet sheet, int rowNum, String searchValue) {
		try {
			int cellCount = getCellCount(sheet, rowNum);
			for (int index = 0; index < cellCount; index++) {
				if (searchValue.equals(getCellData(sheet, rowNum, index).toString().trim())) {
					return index;
				}
			}
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
		return -1;
	}

	/**
	 * Write data back to disk
	 * 
	 * @author 10650956
	 * 
	 */
	public void write() {

		FileOutputStream fos = null;
		try {

			fos = new FileOutputStream(filePath);
			workbook.write(fos);

		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			try {
				fos.flush();
				fos.close();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

	}

	/**
	 * closes workbook
	 */
	public void closeWorkBook() {
		try {
			workbook.close();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	public boolean isSheetAvailable(String name) {

		Iterator<Sheet> iterator = workbook.sheetIterator();

		while (iterator.hasNext()) {
			if (name.trim().equals(iterator.next().getSheetName().trim())) {
				return true;
			}
		}

		return false;
	}

	public CellStyle getCellStyle(XSSFSheet sheet, int rownum, int cellnum) {
		return getCell(sheet, rownum, cellnum).getCellStyle();
	}

	public void setStyle(XSSFSheet sheet, int rownum, int cellnum, CellStyle style) {

		getCell(sheet, rownum, cellnum).setCellStyle(style);
	}

}
