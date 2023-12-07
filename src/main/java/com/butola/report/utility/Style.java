package com.butola.report.utility;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;

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

    public void removeCellComment(Cell cell){
        cell.removeCellComment();
    }
}
