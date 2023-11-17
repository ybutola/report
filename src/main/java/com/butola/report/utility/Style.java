package com.butola.report.utility;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class Style {
    public static Font getUnderlineFont(Workbook workbook) {
        Font underlineFont = workbook.createFont();
        underlineFont.setUnderline(HSSFFont.U_DOUBLE);
        return underlineFont;
    }
}
