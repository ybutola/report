package com.butola.report.service.mongodbreport;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

public class GenerateReportUtil {

    protected static void populateTableData(String[][] data, XWPFTable table) {

        for (int row = 1; row <= data.length; row++) {
            for (int col = 0; col < data[row - 1].length; col++) {
                XWPFTableCell cell = table.getRow(row).getCell(col);
                cell.setText(data[row - 1][col]);
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            }
        }
    }
    protected static void removeTableBorders(XWPFTable table) {
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
    protected static void preserveBordersForSpecificCells(XWPFTable table) {
        int[][] myArray = {
                {1, 1, 3, 5},
                {3, 1, 3},
                {4, 1, 3}
        };
        for (int[] ints : myArray) {
            int rowIndex = ints[0];
            int numCols = ints.length;
            for (int col = 0; col < numCols - 1; col++) {
                int columnIndex = ints[col + 1];
                XWPFTableCell cell = table.getRow(rowIndex).getCell(columnIndex);
/*                CTTcBorders cellBorders = cell.getCTTc().addNewTcPr().addNewTcBorders();
                cellBorders.addNewBottom().setVal(STBorder.SINGLE);*/
            }
        }
    }

    protected static void mergeCellsHorizontally(XWPFTable table, int row, int fromCell, int toCell) {
        XWPFTableRow tableRow = table.getRow(row);
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = tableRow.getCell(cellIndex);
            if (cellIndex == fromCell) {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
}
