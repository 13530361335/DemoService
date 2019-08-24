package com.joker.util;

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
 * @author: Joker Jing
 * @date: 2019/7/29
 */
@Slf4j
public class ExcelUtil {

    /**
     * 工作簿编号,0为第一个工作簿
     */
    private static final int DEFAULT_SHEET_NO = 0;

    /**
     * 数据起始行,0为第一行数据
     */
    private static final int DEFAULT_START = 1;

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
    public static <T> List<T> toData(InputStream in, int sheetNo, int start,
                                     List<String> fields, Class<T> clazz) throws IOException {
        Workbook work = new XSSFWorkbook(in);

        // 获取标题行
        List<String> headerFields = getHeaderFields(work, sheetNo);

        Sheet sheet = work.getSheetAt(sheetNo);
        List<T> list = new LinkedList<>();
        for (int rowIndex = start; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                JSONObject json = new JSONObject();
                for (int colIndex = 0; colIndex < headerFields.size(); colIndex++) {
                    Cell cell = row.getCell(colIndex);
                    String headerField = headerFields.get(colIndex);
                    if (fields.contains(headerField)) {
                        json.put(headerField, getValue(cell));
                    }
                }
                list.add(json.toJavaObject(clazz));
            }
        }
        return list;
    }

    /**
     * 获取标题行字段
     *
     * @param work
     * @param sheetNo
     * @return
     */
    public static List<String> getHeaderFields(Workbook work, int sheetNo) {
        Sheet sheet = work.getSheetAt(sheetNo);
        Row row = sheet.getRow(0);
        List<String> headerFields = new LinkedList<>();
        if (row != null) {
            for (int colIndex = 0; colIndex < row.getLastCellNum(); colIndex++) {
                Cell cell = row.getCell(colIndex);
                headerFields.add(cell.getStringCellValue());
            }
        }
        return headerFields;
    }

    /**
     * 导出excel
     *
     * @param out    生成excel文件流
     * @param list   数据集
     * @param fields 字段数组
     * @throws IOException
     */
    public static void toExcel(OutputStream out, List<?> list, List<String> fields) throws IOException {
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
    public static void toExcelByTemplate(InputStream in, int sheetNo, int start, OutputStream out,
                                         List<?> list, List<String> fields) throws IOException {
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
    private static void loadDataRow(XSSFWorkbook workbook, int sheetNo, int start,
                                    OutputStream out, List<?> list, List<String> fields) throws IOException {
        XSSFSheet sheet = workbook.getSheetAt(sheetNo);
        int rowIndex = start;
        for (Object object : list) {
            Row dataRow = sheet.createRow(rowIndex);
            Map map = JsonUtil.change(object, Map.class);
            for (int i = 0; i < fields.size(); i++) {
                Cell cell = dataRow.createCell(i);
                Object value = map.get(fields.get(i));
                setValue(cell, value);
            }
            rowIndex++;
        }
        workbook.write(out);
    }

    /**
     * 获取cell值
     * @param cell
     * @return
     */
    private static Object getValue(Cell cell) {
        if (null == cell) {
            return null;
        }
        Object value;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                value = DateUtil.isCellDateFormatted(cell) ?
                        DateUtil.getJavaDate(cell.getNumericCellValue()) : new DataFormatter().formatCellValue(cell);
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            default:
                value = cell.toString();
                break;
        }
        return value;
    }

    /**
     * 设置cell值
     * @param cell
     * @param value
     */
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