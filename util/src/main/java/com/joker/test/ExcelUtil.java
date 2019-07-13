package com.joker.test;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * created by Joker on 2019/7/3
 */
@Slf4j
public class ExcelUtil {

    private static final int DEFAULT_SHEET_NO = 0; // 工作簿编号,0为第一个工作簿
    private static final int DEFAULT_START = 1;    // 数据起始行,0为第一行数据

    /**
     * @param in
     * @param sheetNo 工作簿编号,0为第一个工作簿
     * @param start   数据起始行,0为第一行数据
     * @param fields  字段数组
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> toData(InputStream in, int sheetNo, int start, String[] fields, Class<T> clazz) {
        Workbook work;
        try {
            work = new XSSFWorkbook(in);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
        Sheet sheet = work.getSheetAt(sheetNo);
        List<T> list = new LinkedList();
        for (int rowIndex = start; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                JSONObject json = new JSONObject();
                for (int colIndex = 0; colIndex < fields.length; colIndex++) {
                    Cell cell = row.getCell(colIndex);
                    json.put(fields[colIndex], getValue(cell));
                }
                list.add(json.toJavaObject(clazz));
            }
        }
        return list;
    }

    /**
     * 导出excel
     *
     * @param out    生成excel文件流
     * @param list   数据集
     * @param fields 字段数组
     * @throws IOException
     */
    public static void toExcel(OutputStream out, List list, String[] fields) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();

        // 加载标题行
        Row headRow = sheet.createRow(0);
        int colIndex = 0;
        for (String field : fields) {
            Cell headCell = headRow.createCell(colIndex);
            headCell.setCellValue(field);
            colIndex++;
        }

        loadDataRow(workbook, DEFAULT_SHEET_NO, DEFAULT_START, out, list, fields);
    }

    /**
     * 导入excel,根据模板导出
     *
     * @param in      模板文件流
     * @param sheetNo
     * @param start
     * @param out     生成excel文件流
     * @param fields  字段数组
     * @param list    数据集
     * @throws IOException
     */
    public static void toExcelByTemplate(InputStream in, int sheetNo, int start, OutputStream out, List list, String[] fields) throws IOException {
        loadDataRow(new XSSFWorkbook(in), sheetNo, start, out, list, fields);
    }

    /**
     * 加载数据行
     *
     * @param workbook
     * @param out
     * @param list
     * @param fields
     * @throws IOException
     */
    private static void loadDataRow(XSSFWorkbook workbook, int sheetNo, int start, OutputStream out, List list, String[] fields) throws IOException {
        XSSFSheet sheet = workbook.getSheetAt(sheetNo);
        int rowIndex = start;
        for (Object object : list) {
            Row dataRow = sheet.createRow(rowIndex);
            Map map = JSONUtil.change(object, Map.class);
            for (int i = 0; i < fields.length; i++) {
                Cell cell = dataRow.createCell(i);
                Object value = map.get(fields[i]);
                setValue(cell, value);
            }
            rowIndex++;
        }
        workbook.write(out);
    }

    private static Object getValue(Cell cell) {
        if (null == cell) {
            return null;
        }
        Object value;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                value = DateUtil.isCellDateFormatted(cell) ?
                        DateUtil.getJavaDate(cell.getNumericCellValue()) : new DataFormatter().formatCellValue(cell);
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                value = cell.getBooleanCellValue();
                break;
            default:
                value = cell.toString();
                break;
        }
        return value;
    }

    private static void setValue(Cell cell, Object value) {
        if (null == cell) {
            return;
        } else if (null == value) {
            cell.setCellValue("");
            return;
        }
        if (value instanceof String) {
            cell.setCellValue(value.toString());
        } else if (value instanceof Date) {
            cell.setCellValue(DateUtil.getExcelDate((Date) value));
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue(value.toString());
        }
    }

}