package com.butola.report.service.exhibits.exhibita;

import com.butola.report.data.Revenue;
import com.butola.report.utility.Style;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;

@Service
public class RevenueGenerator {

    public void generateRevenue(List<Revenue> revenueList, Workbook workbook, Sheet sheet) {
        generateRevenue(workbook, sheet, revenueList);

        try (FileOutputStream outputStream = new FileOutputStream("Metronet2023 - Audit Report.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateRevenue(Workbook workbook, Sheet sheet, List<Revenue> revenueList) {
        setColumnWidth(sheet);
        generateHeader(workbook, sheet);

        Row row_2 = sheet.createRow(2);
        Cell row_2cell_1 = row_2.createCell(1);
        row_2cell_1.setCellValue("");

        Row row_3 = sheet.createRow(2);
        Cell row_3Cell_1 = row_3.createCell(0);
        row_3Cell_1.setCellValue("Operating Revenues:");

        AtomicInteger rowNumber = new AtomicInteger(3);
        DecimalFormat df = new DecimalFormat("#,###");
        Revenue miscRevenue = new Revenue();
        miscRevenue.setItem("Miscellaneous");
        miscRevenue.setPreviousYearValue(0.0);
        miscRevenue.setCurrentYearValue(0.0);
        revenueList.add(miscRevenue);

        DoubleAdder thisYearsRevenue = new DoubleAdder();
        DoubleAdder previousYearsRevenue = new DoubleAdder();

        revenueList.stream()
                .forEach(revenue -> {
                    Row revenueRow = sheet.createRow(rowNumber.getAndIncrement());
                    Cell label = revenueRow.createCell(1);
                    label.setCellValue(revenue.getItem());

                    Cell valueCurrentYear = revenueRow.createCell(3);
                    Cell valuePrYear = revenueRow.createCell(5);
                    valueCurrentYear.setCellStyle(Style.alignRightStyle(workbook));
                    valuePrYear.setCellStyle(Style.alignRightStyle(workbook));

                    long currentYearValue = Math.round(revenue.getCurrentYearValue());
                    long previousYearValue = Math.round(revenue.getPreviousYearValue());
                    thisYearsRevenue.add(currentYearValue);
                    previousYearsRevenue.add(previousYearValue);

                    valueCurrentYear.setCellValue("$     " + df.format(currentYearValue));
                    valuePrYear.setCellValue("$     " + df.format(previousYearValue));
                });

        CellStyle topBorderStyle = workbook.createCellStyle();
        topBorderStyle.setBorderTop(BorderStyle.THIN);
        topBorderStyle.setAlignment(HorizontalAlignment.RIGHT);
        Row totalValueRow = sheet.createRow(rowNumber.getAndIncrement());
        Cell tcyItemCell = totalValueRow.createCell(2);
        tcyItemCell.setCellValue("Total Current Assets");

        Cell tstaCell_3 = totalValueRow.createCell(3);
        tstaCell_3.setCellValue(df.format(thisYearsRevenue.doubleValue()));
        tstaCell_3.setCellStyle(topBorderStyle);
        Cell tstaCell_5 = totalValueRow.createCell(5);
        tstaCell_5.setCellValue(df.format(previousYearsRevenue.doubleValue()));
        tstaCell_5.setCellStyle(topBorderStyle);
    }

    private void generateHeader(Workbook workbook, Sheet sheet) {
        CellStyle bottomBorderStyle = workbook.createCellStyle();
        bottomBorderStyle.setBorderBottom(BorderStyle.THIN);
        bottomBorderStyle.setAlignment(HorizontalAlignment.CENTER);
        bottomBorderStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Row row = sheet.createRow(1);
        Cell cell_7 = row.createCell(3);
        cell_7.setCellValue("2023");
        cell_7.setCellStyle(bottomBorderStyle);

        Cell cell_9 = row.createCell(5);
        cell_9.setCellValue("2022");
        cell_9.setCellStyle(bottomBorderStyle);
    }

    private void setColumnWidth(Sheet sheet) {
        sheet.setColumnWidth(0, 2 * 256);
        sheet.setColumnWidth(1, 2 * 256);
        sheet.setColumnWidth(2, 45 * 256);
        sheet.setColumnWidth(3, 12 * 256);
        sheet.setColumnWidth(4, 2 * 256);
        sheet.setColumnWidth(5, 12 * 256);
    }
}
