package com.app.service;

import com.app.constant.AppConstant;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ExcelService {
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(AppConstant.DATE_TIME_FORMAT);

    public CellStyle createBorderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        short black = IndexedColors.BLACK.getIndex();
        BorderStyle thin = BorderStyle.THIN;
        style.setBorderBottom(thin);
        style.setBottomBorderColor(black);
        style.setBorderLeft(thin);
        style.setLeftBorderColor(black);
        style.setBorderRight(thin);
        style.setRightBorderColor(black);
        style.setBorderTop(thin);
        style.setTopBorderColor(black);
        return style;
    }

    public void createCell(Row row, int cellIndex, Object value, CellStyle style) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellStyle(style);
        if (value != null) {
            if (value instanceof Date) {
                cell.setCellValue(DATE_FORMAT.format((Date) value));
            } else if (value instanceof String) {
                cell.setCellValue((String) value);
            } else if (value instanceof Number) {
                cell.setCellValue(((Number) value).doubleValue());
                applyNumberFormat(cell, style, value instanceof Integer || value instanceof Long ? "#,##0" : "#,##0.0");
            }
        }
    }

    private void applyNumberFormat(Cell cell, CellStyle style, String format) {
        Workbook workbook = cell.getSheet().getWorkbook();
        CellStyle numberStyle = workbook.createCellStyle();
        numberStyle.cloneStyleFrom(style);
        numberStyle.setDataFormat(workbook.createDataFormat().getFormat(format));
        cell.setCellStyle(numberStyle);
    }

    public Date getCellDateValue(Row row, int cellIndex) throws ParseException {
        Cell cell = row.getCell(cellIndex);
        return cell != null ? DATE_FORMAT.parse(cell.getStringCellValue()) : null;
    }
}
