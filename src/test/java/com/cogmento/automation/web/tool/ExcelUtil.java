package com.cogmento.automation.web.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	public static String testDataExcelFilePath = System.getProperty("user.dir") + "/testdata/" + ConfigUtil.getProperty("testDataExcelFile");
	static Workbook workbook;

	public static Object[][] getTestData(String sheetName) {
		Sheet sheet = getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
			}
		}
		return data;
	}

	public static Object[][] getTestDataMaps(String sheetName) {
		Sheet sheet = getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][1];
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Map<String, String> datamap = new HashMap<String, String>();
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				Object headerCell = null;
				Object cell = null;
				String key = null;
				String value = null;
				if ((headerCell = sheet.getRow(0).getCell(k)) != null) {
					key = headerCell.toString();
					if ((cell = sheet.getRow(i).getCell(k)) != null) {
						value = cell.toString();
					} else {
						value = "";
					}
					datamap.put(key, value); // put only when key is not null
				}

			}
			data[i - 1][0] = datamap;
		}
		return data;
	}

	private static Sheet getSheet(String sheetName) {
		Sheet sheet;
		InputStream is = null;
		try {
			try {
				is = new FileInputStream(testDataExcelFilePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (new File(testDataExcelFilePath).getName().toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(is);
			} else {
				workbook = new HSSFWorkbook(is);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sheet = workbook.getSheet(sheetName);
		return sheet;
	}

	public static void main(String[] args) {
		Object[][] data = getTestDataMaps("Companies");
		data[0][0].toString();
	}
}
