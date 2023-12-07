package com.butola.report.service.exhibits.exhibita;

import com.butola.report.data.Expense;
import com.butola.report.data.Expense;
import com.butola.report.utility.Style;
import org.apache.commons.math3.analysis.function.Exp;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;

@Service
public class ExpenseGenerator {
    public void generateExpense(List<Expense> expenseList, Workbook workbook, Sheet sheet){

        generateExpense(workbook, sheet, expenseList);

        try (FileOutputStream outputStream = new FileOutputStream("Metronet2023 - Audit Report.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateExpense(Workbook workbook, Sheet sheet, List<Expense> expenseList) {

        Row row_7 = sheet.createRow(7);
        Cell row_7cell_1 = row_7.createCell(0);
        row_7cell_1.setCellValue("Operating Expenses:");

        AtomicInteger rowNumber = new AtomicInteger(8);
        DecimalFormat df = new DecimalFormat("#,###");

        DoubleAdder thisYearsExpense = new DoubleAdder();
        DoubleAdder previousYearsExpense = new DoubleAdder();

        expenseList.stream()
                .forEach(expense -> {
                    Row expenseRow = sheet.createRow(rowNumber.getAndIncrement());
                    Cell label = expenseRow.createCell(1);
                    label.setCellValue(expense.getItem());

                    Cell valueCurrentYear = expenseRow.createCell(3);
                    Cell valuePrYear = expenseRow.createCell(5);
                    valueCurrentYear.setCellStyle(Style.alignRightStyle(workbook));
                    valuePrYear.setCellStyle(Style.alignRightStyle(workbook));

                    long currentYearValue = Math.round(expense.getCurrentYearValue());
                    long previousYearValue = Math.round(expense.getPreviousYearValue());
                    thisYearsExpense.add(currentYearValue);
                    previousYearsExpense.add(previousYearValue);

                    valueCurrentYear.setCellValue(df.format(currentYearValue));
                    valuePrYear.setCellValue(df.format(previousYearValue));
                });

        CellStyle topBorderStyle = workbook.createCellStyle();
        topBorderStyle.setBorderTop(BorderStyle.THIN);
        topBorderStyle.setAlignment(HorizontalAlignment.RIGHT);
        Row totalValueRow = sheet.createRow(rowNumber.getAndIncrement());
        Cell tcyItemCell = totalValueRow.createCell(2);
        tcyItemCell.setCellValue("Total Operating Expenses");

        Cell tstaCell_3 = totalValueRow.createCell(3);
        tstaCell_3.setCellValue(df.format(thisYearsExpense.doubleValue()));
        tstaCell_3.setCellStyle(topBorderStyle);
        Cell tstaCell_5 = totalValueRow.createCell(5);
        tstaCell_5.setCellValue(df.format(previousYearsExpense.doubleValue()));
        tstaCell_5.setCellStyle(topBorderStyle);
    }
}
