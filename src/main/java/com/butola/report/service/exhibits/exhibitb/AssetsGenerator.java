package com.butola.report.service.exhibits.exhibitb;

import com.butola.report.data.Asset;
import com.butola.report.data.Liability;
import com.butola.report.utility.Style;
import org.apache.poi.hssf.usermodel.HSSFFont;
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
public class AssetsGenerator {
    public void generateAssets(List<Asset> assetList, Workbook workbook, Sheet sheet) {
        setColumnWidth(sheet);
        generateHeader(workbook, sheet);
        Asset shortTermAsset = generateShortTermAssets(workbook, sheet, assetList);
        generateLongTermAssets(shortTermAsset, workbook, sheet, assetList);

        try (FileOutputStream outputStream = new FileOutputStream("Metronet2023 - Audit Report.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setColumnWidth(Sheet sheet) {
        sheet.setColumnWidth(0, 2 * 256);
        sheet.setColumnWidth(1, 2 * 256);
        sheet.setColumnWidth(2, 2 * 256);
        sheet.setColumnWidth(3, 2 * 256);
        sheet.setColumnWidth(4, 45 * 256);
        sheet.setColumnWidth(5, 12 * 256);
        sheet.setColumnWidth(7, 12 * 256);
    }

    private void generateHeader(Workbook workbook, Sheet sheet) {
        CellStyle bottomBorderStyle = workbook.createCellStyle();
        bottomBorderStyle.setBorderBottom(BorderStyle.THIN);
        bottomBorderStyle.setAlignment(HorizontalAlignment.CENTER);
        bottomBorderStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Row row = sheet.createRow(0);
        Cell cell_7 = row.createCell(5);
        cell_7.setCellValue("2023");
        cell_7.setCellStyle(bottomBorderStyle);

        Cell cell_9 = row.createCell(7);
        cell_9.setCellValue("2022");
        cell_9.setCellStyle(bottomBorderStyle);

        Row row_1 = sheet.createRow(1);
        Cell cell_1 = row_1.createCell(1);
        cell_1.setCellValue("ASSETS");

        CellRangeAddress mergedRegion = new CellRangeAddress(1, 1, 1, 4);
        sheet.addMergedRegion(mergedRegion);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFont(Style.getUnderlineFont(workbook));
        cell_1.setCellStyle(cellStyle);
    }

    private Asset generateShortTermAssets(Workbook workbook, Sheet sheet, List<Asset> assetList) {
        Row row_2 = sheet.createRow(2);
        Cell row_2cell_1 = row_2.createCell(1);
        row_2cell_1.setCellValue("");

        Row row_3 = sheet.createRow(3);
        Cell row_3Cell_1 = row_3.createCell(1);
        row_3Cell_1.setCellValue("Current Assets:");

        CellRangeAddress currentAssetsMergeRegion = new CellRangeAddress(3, 3, 1, 4);
        sheet.addMergedRegion(currentAssetsMergeRegion);

        CellRangeAddress assetsMergeRegion_1 = new CellRangeAddress(4, 4, 2, 4);
        sheet.addMergedRegion(assetsMergeRegion_1);

        CellRangeAddress assetsMergeRegion_2 = new CellRangeAddress(5, 5, 2, 4);
        sheet.addMergedRegion(assetsMergeRegion_2);

        CellRangeAddress assetsMergeRegion_3 = new CellRangeAddress(6, 6, 2, 4);
        sheet.addMergedRegion(assetsMergeRegion_3);
        AtomicInteger rowNumber = new AtomicInteger(4);
        DoubleAdder thisYearsCurrentAssetsValue = new DoubleAdder();
        DoubleAdder previousYearsCurrentAssetsValue = new DoubleAdder();
        DecimalFormat df = new DecimalFormat("#,###");

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        assetList.stream()
                .filter(asset ->
                        !(asset.getItem().contains("Furniture") || asset.getItem().contains("ROU"))
                ).forEach(asset -> {
                    Row assetRow = sheet.createRow(rowNumber.getAndIncrement());
                    Cell label = assetRow.createCell(2);
                    label.setCellValue(asset.getItem());

                    Cell valueCurrentYear = assetRow.createCell(5);
                    Cell valuePrYear = assetRow.createCell(7);
                    Cell difference = assetRow.createCell(9);
                    valueCurrentYear.setCellStyle(Style.alignRightStyle(workbook));
                    valuePrYear.setCellStyle(Style.alignRightStyle(workbook));
                    long currentYearValue = Math.round(asset.getCurrentYearValue());
                    long previousYearValue = Math.round(asset.getPreviousYearValue());
                    thisYearsCurrentAssetsValue.add(currentYearValue);
                    previousYearsCurrentAssetsValue.add(previousYearValue);

                    if (rowNumber.get() == 5) {
                        valueCurrentYear.setCellValue("$        " + df.format(currentYearValue));
                        valuePrYear.setCellValue("$        " + df.format(previousYearValue));
                    } else {
                        valueCurrentYear.setCellValue(df.format(currentYearValue));
                        valuePrYear.setCellValue(df.format(previousYearValue));
                    }
                    difference.setCellValue(df.format(previousYearValue - currentYearValue));
                    difference.setCellStyle(style);
                    difference.removeCellComment();

                    valuePrYear.removeCellComment();
                    valueCurrentYear.removeCellComment();
                });

        CellStyle topBorderStyle = workbook.createCellStyle();
        topBorderStyle.setBorderTop(BorderStyle.THIN);
        topBorderStyle.setAlignment(HorizontalAlignment.RIGHT);
        Row totalShortTermAssetRow = sheet.createRow(7);
        Cell tstaCell = totalShortTermAssetRow.createCell(3);
        tstaCell.setCellValue("Total Current Assets");

        Cell tstaCell_5 = totalShortTermAssetRow.createCell(5);
        tstaCell_5.setCellValue(df.format(thisYearsCurrentAssetsValue.doubleValue()));
        tstaCell_5.setCellStyle(topBorderStyle);
        Cell tstaCell_7 = totalShortTermAssetRow.createCell(7);
        tstaCell_7.setCellValue(df.format(previousYearsCurrentAssetsValue.doubleValue()));
        tstaCell_7.setCellStyle(topBorderStyle);

        Asset asset = new Asset();
        asset.setTotalCurrentYearAssets(thisYearsCurrentAssetsValue.doubleValue());
        asset.setTotalPreviousYearAssets(previousYearsCurrentAssetsValue.doubleValue());
        return asset;
    }

    private void generateLongTermAssets(Asset shortTermAsset, Workbook workbook, Sheet sheet, List<Asset> assetList) {
        Row row_8 = sheet.createRow(8);
        Row row_9 = sheet.createRow(9);

        CellRangeAddress currentAssetsMergeRegion = new CellRangeAddress(9, 9, 1, 4);
        sheet.addMergedRegion(currentAssetsMergeRegion);

        CellRangeAddress assetsMergeRegion_1 = new CellRangeAddress(10, 10, 1, 4);
        sheet.addMergedRegion(assetsMergeRegion_1);

        AtomicInteger rowNumber = new AtomicInteger(9);
        DoubleAdder thisYearsAssetsValue = new DoubleAdder();
        DoubleAdder previousYearsAssetsValue = new DoubleAdder();
        DecimalFormat df = new DecimalFormat("#,###");

        CellStyle alignRightStyle = workbook.createCellStyle();
        alignRightStyle.setAlignment(HorizontalAlignment.RIGHT);

        assetList.stream()
                .filter(asset ->
                        asset.getItem().contains("Furniture") || asset.getItem().contains("ROU")
                ).forEach(asset -> {
                    Row assetRow = sheet.createRow(rowNumber.getAndIncrement());
                    Cell label = assetRow.createCell(1);
                    label.setCellValue(asset.getItem());

                    Cell valueCurrentYear = assetRow.createCell(5);
                    Cell valuePrYear = assetRow.createCell(7);
                    valueCurrentYear.setCellStyle(alignRightStyle);
                    valuePrYear.setCellStyle(alignRightStyle);
                    long currentYearValue = Math.round(asset.getCurrentYearValue());
                    long previousYearValue = Math.round(asset.getPreviousYearValue());
                    thisYearsAssetsValue.add(currentYearValue);
                    previousYearsAssetsValue.add(previousYearValue);

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
        tstaCell.setCellValue("TOTAL ASSETS");

        Cell tstaCell_5 = row_12.createCell(5);
        tstaCell_5.setCellValue("$        " + df.format(thisYearsAssetsValue.doubleValue() + shortTermAsset.getTotalCurrentYearAssets()));
        tstaCell_5.setCellStyle(topBorderStyle);
        Cell tstaCell_7 = row_12.createCell(7);
        tstaCell_7.setCellValue("$        " + df.format(previousYearsAssetsValue.doubleValue() + shortTermAsset.getTotalPreviousYearAssets()));
        tstaCell_7.setCellStyle(topBorderStyle);
    }
}
