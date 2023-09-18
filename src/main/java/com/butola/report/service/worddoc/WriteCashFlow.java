package com.butola.report.service.worddoc;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
public class WriteCashFlow {
    public void writeCashFlow(XWPFDocument document) {

        XWPFParagraph titleParagraph = document.createParagraph();
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setText("Accounting Data Report");
        titleRun.setBold(true);
        titleRun.setFontSize(16);

        // Create a table for the accounting data
        XWPFTable table = document.createTable(5, 6);
        removeTableBorders(table);
        preserveBordersForSpecificCells(table);
        // Merge two vertical cells in the second column
        table.setWidth("100%");
        populateTable(table);
    }

    private static void populateTable(XWPFTable table) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd", Locale.US);
        String[] headers = {"", "      " + sdf.format(date) + "      ", "  ", " ", "", "Estimated"};
        String years = "5 years";
        String amt_1 = "31,243";
        String amt_2 = "25,303";
        String amt_3 = "11,172";
        String amt_4 = "5,550";
        String amt_5 = "20,071";
        String amt_6 = "19,753";
        String[][] data = {
                {"", "2022", "     ", "2021", "     ", "Useful Lives"},
                {"Furniture and Equipment", "$            " + amt_1, "  ", "$            " + amt_2, "     ", years},
                {"Less Accumulated Depreciation", amt_3, "  ", amt_4, "     ", ""},
                {"", "$            " + amt_5, "  ", "$            " + amt_6, "     ", ""}
        };

        populateHeaderData(headers, table);
        populateTableData(data, table);
    }

    private static void populateHeaderData(String[] headers, XWPFTable table) {
        for (int i = 0; i < headers.length; i++) {
            XWPFTableCell headerCell = table.getRow(0).getCell(i);
            headerCell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            headerCell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

            XWPFRun xwpfRun = headerCell.addParagraph().createRun();
            xwpfRun.setText(headers[i]);
            headerCell.getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
            if (i == 1) {
                headerCell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
                CTTcBorders cellBorders = headerCell.getCTTc().addNewTcPr().addNewTcBorders();
                cellBorders.addNewBottom().setVal(STBorder.SINGLE);
            //    xwpfRun.setUnderline(UnderlinePatterns.SINGLE);
            } else if (i == 2 || i == 3) {
                headerCell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            } else if (i == 5) {
                headerCell.getParagraphs().get(0).setAlignment(ParagraphAlignment.LEFT);
            }
        }
    }

    private static void populateTableData(String[][] data, XWPFTable table) {
        for (int row = 1; row <= data.length; row++) {
            for (int col = 0; col < data[row - 1].length; col++) {
                XWPFTableCell cell = table.getRow(row).getCell(col);
                cell.setText(data[row - 1][col]);

                if (col == 1 || col == 3) {
                    cell.getParagraphs().get(0).setAlignment(ParagraphAlignment.RIGHT);
                } else if (col == 5) {
                    cell.getParagraphs().get(0).setAlignment(ParagraphAlignment.LEFT);
                }

                if (col == 2 || col == 4) {
                    cell.setWidth(String.valueOf(300));
                }
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            }
        }
    }

    private static void removeTableBorders(XWPFTable table) {
        for (XWPFTableRow row : table.getRows()) {
            for (XWPFTableCell cell : row.getTableCells()) {
                CTTcBorders borders = cell.getCTTc().addNewTcPr().addNewTcBorders();
                borders.addNewBottom().setVal(STBorder.NIL);
                borders.addNewTop().setVal(STBorder.NIL);
                borders.addNewLeft().setVal(STBorder.NIL);
                borders.addNewRight().setVal(STBorder.NIL);
            }
        }
    }

    private static void preserveBordersForSpecificCells(XWPFTable table) {
        int[][] myArray = {
                {1, 1, 3, 5},
                {3, 1, 3},
                {4, 1, 3}
        };

        int numRows = myArray.length;

        for (int row = 0; row < numRows; row++) {
            int rowIndex = myArray[row][0];
            int numCols = myArray[row].length;
            for (int col = 0; col < numCols - 1; col++) {
                int columnIndex = myArray[row][col + 1];
                XWPFTableCell cell = table.getRow(rowIndex).getCell(columnIndex);
                CTTcBorders cellBorders = cell.getCTTc().addNewTcPr().addNewTcBorders();
                cellBorders.addNewBottom().setVal(STBorder.SINGLE);
            }
        }
    }
}
