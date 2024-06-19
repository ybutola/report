package com.butola.report.utility;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;

import java.text.DecimalFormat;

public class Style {
    public static Font getUnderlineFont(Workbook workbook) {
        Font underlineFont = workbook.createFont();
        underlineFont.setUnderline(HSSFFont.U_DOUBLE);
        return underlineFont;
    }

    public static CellStyle alignRightStyle(Workbook workbook) {
        CellStyle alignRightStyle = workbook.createCellStyle();
        alignRightStyle.setAlignment(HorizontalAlignment.RIGHT);
        return alignRightStyle;
    }

    public static CellStyle greenBackground(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    public static void createDifferenceCell(Row row, Workbook workbook ,  long previousYearValue, long currentYearValue){
        DecimalFormat df = new DecimalFormat("#,###");
        Cell difference = row.createCell(9);
        difference.setCellValue(df.format(previousYearValue - currentYearValue));
        difference.setCellStyle(greenBackground(workbook));
        difference.removeCellComment();
    }
}
