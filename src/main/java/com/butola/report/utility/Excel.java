package com.butola.report.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class Excel {
    public static int findRowNumber(String textToFind, Sheet sheet) {
        int columnIndex = 0; // The text to search should always be in the first column.

        for (Row row : sheet) {
            Cell cell = row.getCell(columnIndex);

            if (cell != null && cell.getCellType() == CellType.STRING) {
                String cellValue = cell.getStringCellValue();
                CellStyle cellStyle = cell.getCellStyle();
                XSSFFont font = ((XSSFCellStyle) cellStyle).getFont();

                boolean isBold = font.getBold();
                if (isBold && cellValue.contains(textToFind)) {
                    int rowNumber = row.getRowNum(); // 0-based index
                    System.out.println("Text found in row: " + rowNumber);
                    return rowNumber;
                }
            }
        }
        return 0;
    }
}
