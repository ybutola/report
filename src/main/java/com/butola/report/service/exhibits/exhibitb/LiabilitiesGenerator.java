package com.butola.report.service.exhibits.exhibitb;

import com.butola.report.data.Asset;
import com.butola.report.data.Liability;
import com.butola.report.utility.Style;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;

@Service
public class LiabilitiesGenerator {
    void generateLiabilities(List<Liability> liabilities, Workbook workbook, Sheet sheet) {
        generateHeader(workbook, sheet);
        generateShortTermLiabilities(workbook, sheet, liabilities);
        //   generateLongTermLiabilities(workbook, sheet, liabilities);

        try (FileOutputStream outputStream = new FileOutputStream("Metronet2023 - Audit Report.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateHeader(Workbook workbook, Sheet sheet) {
        Row row_1 = sheet.createRow(16);
        Cell cell_1 = row_1.createCell(1);
        cell_1.setCellValue("LIABILITIES AND NET POSITION");
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFont(Style.getUnderlineFont(workbook));
        cell_1.setCellStyle(cellStyle);

        CellRangeAddress mergedRegion = new CellRangeAddress(16, 16, 1, 4);
        sheet.addMergedRegion(mergedRegion);
    }

    private void generateShortTermLiabilities(Workbook workbook, Sheet sheet, List<Liability> liabilityList) {
        Row row_22 = sheet.createRow(18);
        Cell row_22Cell_1 = row_22.createCell(1);
        row_22Cell_1.setCellValue("Current Liabilities:");


        CellRangeAddress currentLiabilitiesMergeRegion = new CellRangeAddress(18, 18, 1, 4);
        sheet.addMergedRegion(currentLiabilitiesMergeRegion);

        AtomicInteger rowNumber = new AtomicInteger(19);
        DoubleAdder thisYearsCurrentLiabilitiesValue = new DoubleAdder();
        DoubleAdder previousYearsCurrentLiabilitiesValue = new DoubleAdder();
        DecimalFormat df = new DecimalFormat("#,###");

        CellStyle alignRightStyle = workbook.createCellStyle();
        alignRightStyle.setAlignment(HorizontalAlignment.RIGHT);

        liabilityList.stream()
//                .filter(liability ->
//                        !(liability.getItem().contains("Furniture") || liability.getItem().contains("ROU"))
//                ).
                .forEach(liability -> {
                    Row liabilityRow = sheet.createRow(rowNumber.getAndIncrement());
                    Cell label = liabilityRow.createCell(2);
                    label.setCellValue(liability.getItem());

                    Cell valueCurrentYear = liabilityRow.createCell(5);
                    Cell valuePrYear = liabilityRow.createCell(7);
                    valueCurrentYear.setCellStyle(alignRightStyle);
                    valuePrYear.setCellStyle(alignRightStyle);
                    long currentYearValue = Math.round(liability.getCurrentYearValue());
                    long previousYearValue = Math.round(liability.getPreviousYearValue());
                    thisYearsCurrentLiabilitiesValue.add(currentYearValue);
                    previousYearsCurrentLiabilitiesValue.add(previousYearValue);

                    if (rowNumber.get() == 5) {
                        valueCurrentYear.setCellValue("$        " + df.format(currentYearValue));
                        valuePrYear.setCellValue("$        " + df.format(previousYearValue));
                    } else {
                        valueCurrentYear.setCellValue(df.format(currentYearValue));
                        valuePrYear.setCellValue(df.format(previousYearValue));
                    }
                });

        CellStyle topBorderStyle = workbook.createCellStyle();
        topBorderStyle.setBorderTop(BorderStyle.THIN);
        topBorderStyle.setAlignment(HorizontalAlignment.RIGHT);
        Row totalShortTermLiabilityRow = sheet.createRow(rowNumber.getAndIncrement());
        Cell tstaCell = totalShortTermLiabilityRow.createCell(3);
        tstaCell.setCellValue("Total Liabilities");

        Cell tstaCell_5 = totalShortTermLiabilityRow.createCell(5);
        tstaCell_5.setCellValue(df.format(thisYearsCurrentLiabilitiesValue.doubleValue()));
        tstaCell_5.setCellStyle(topBorderStyle);
        Cell tstaCell_7 = totalShortTermLiabilityRow.createCell(7);
        tstaCell_7.setCellValue(df.format(previousYearsCurrentLiabilitiesValue.doubleValue()));
        tstaCell_7.setCellStyle(topBorderStyle);
        CellRangeAddress liabilitiesMergeRegion_1 = new CellRangeAddress(19, 19, 2, 4);
        sheet.addMergedRegion(liabilitiesMergeRegion_1);

        CellRangeAddress liabilitiesMergeRegion_2 = new CellRangeAddress(20, 20, 2, 4);
        sheet.addMergedRegion(liabilitiesMergeRegion_2);

        CellRangeAddress liabilitiesMergeRegion_3 = new CellRangeAddress(21, 21, 2, 4);
        sheet.addMergedRegion(liabilitiesMergeRegion_3);
    }

    private void generateLongTermLiabilities(Workbook workbook, Sheet sheet, List<Liability> LiabilityList) {
        Row row_8 = sheet.createRow(8);
        Row row_9 = sheet.createRow(9);

        CellRangeAddress currentLiabilitysMergeRegion = new CellRangeAddress(9, 9, 1, 4);
        sheet.addMergedRegion(currentLiabilitysMergeRegion);

        CellRangeAddress LiabilitysMergeRegion_1 = new CellRangeAddress(10, 10, 1, 4);
        sheet.addMergedRegion(LiabilitysMergeRegion_1);

        AtomicInteger rowNumber = new AtomicInteger(9);
        DoubleAdder thisYearsCurrentLiabilitysValue = new DoubleAdder();
        DoubleAdder previousYearsCurrentLiabilitysValue = new DoubleAdder();
        DecimalFormat df = new DecimalFormat("#,###");

        CellStyle alignRightStyle = workbook.createCellStyle();
        alignRightStyle.setAlignment(HorizontalAlignment.RIGHT);

        LiabilityList.stream()
                .filter(liability ->
                        liability.getItem().contains("Furniture") || liability.getItem().contains("ROU")
                ).forEach(Liability -> {
                    Row liabilityRow = sheet.createRow(rowNumber.getAndIncrement());
                    Cell label = liabilityRow.createCell(1);
                    label.setCellValue(Liability.getItem());

                    Cell valueCurrentYear = liabilityRow.createCell(5);
                    Cell valuePrYear = liabilityRow.createCell(7);
                    valueCurrentYear.setCellStyle(alignRightStyle);
                    valuePrYear.setCellStyle(alignRightStyle);
                    long currentYearValue = Math.round(Liability.getCurrentYearValue());
                    long previousYearValue = Math.round(Liability.getPreviousYearValue());
                    //thisYearsCurrentLiabilitiesValue.add(currentYearValue);
                    //previousYearsCurrentLiabilitiesValue.add(previousYearValue);

                    if (rowNumber.get() == 5) {
                        valueCurrentYear.setCellValue("$        " + df.format(currentYearValue));
                        valuePrYear.setCellValue("$        " + df.format(previousYearValue));
                    } else {
                        valueCurrentYear.setCellValue(df.format(currentYearValue));
                        valuePrYear.setCellValue(df.format(previousYearValue));
                    }
                });

        Row row_11 = sheet.createRow(11);
        Row row_12 = sheet.createRow(12);
        CellStyle topBorderStyle = workbook.createCellStyle();
        topBorderStyle.setBorderTop(BorderStyle.THIN);
        topBorderStyle.setAlignment(HorizontalAlignment.RIGHT);
        Cell tstaCell = row_12.createCell(3);
        tstaCell.setCellValue("TOTAL LiabilityS");

        Cell tstaCell_5 = row_12.createCell(5);
        tstaCell_5.setCellValue(df.format(thisYearsCurrentLiabilitysValue.doubleValue()));
        tstaCell_5.setCellStyle(topBorderStyle);
        Cell tstaCell_7 = row_12.createCell(7);
        tstaCell_7.setCellValue(df.format(previousYearsCurrentLiabilitysValue.doubleValue()));
        tstaCell_7.setCellStyle(topBorderStyle);
    }
}
