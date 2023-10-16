package com.butola.report.service.exhibits;

import com.butola.report.data.Asset;
import com.butola.report.data.Liability;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExhibitBGenerator {
    @Autowired
    ResourceLoader resourceLoader;

    public void readTriaBalance() throws IOException {
        String excelFilePath = "files/MetronetTB.xlsx";
        Resource resource = resourceLoader.getResource("classpath:" + excelFilePath);

        if (resource.exists()) {
            InputStream inputStream = null;
            try {
                inputStream = resource.getInputStream();
                Workbook workbook = new XSSFWorkbook(inputStream);
                Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
                generateExhibit(createassetList(sheet), createLiabilitiesList(sheet));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                inputStream.close();
            }
        }
    }

    private List<Asset> createassetList(Sheet sheet) {
        List<Asset> assetList = new ArrayList<>();

        Row row = sheet.getRow(11);
        String assetItem = row.getCell(0).getStringCellValue();


        if (assetItem.contains("Cash")) {
            Asset asset = new Asset();
            asset.setItem("Cash And Cash Equivalents");
            asset.setPreviousYearValue(row.getCell(1).getNumericCellValue());
            asset.setCurrentYearValue(row.getCell(5).getNumericCellValue());
            assetList.add(asset);
        }

        row = sheet.getRow(15);
        assetItem = row.getCell(0).getStringCellValue();

        if (assetItem.contains("Receivable")) {
            Asset asset = new Asset();
            asset.setItem("Accounts Receivable");
            asset.setPreviousYearValue(row.getCell(1).getNumericCellValue());
            asset.setCurrentYearValue(row.getCell(5).getNumericCellValue());
            assetList.add(asset);
        }

        row = sheet.getRow(18);
        assetItem = row.getCell(0).getStringCellValue();

        if (assetItem.contains("Prepaid")) {
            Asset asset = new Asset();
            asset.setItem("Prepaid Expenses");
            asset.setPreviousYearValue(row.getCell(1).getNumericCellValue());
            asset.setCurrentYearValue(row.getCell(5).getNumericCellValue());
            assetList.add(asset);
        }
        row = sheet.getRow(22);
        assetItem = row.getCell(0).getStringCellValue();

        if (assetItem.contains("Office")) {
            Asset asset = new Asset();
            asset.setItem("Furniture and Equipment - Net");
            asset.setPreviousYearValue(row.getCell(1).getNumericCellValue());
            asset.setCurrentYearValue(row.getCell(5).getNumericCellValue());
            assetList.add(asset);
        }

        row = sheet.getRow(25);
        assetItem = row.getCell(0).getStringCellValue();
        if (assetItem.contains("Other Assets")) {
            Asset asset = new Asset();
            asset.setItem("ROU Asset");
            asset.setPreviousYearValue(row.getCell(1).getNumericCellValue());
            asset.setCurrentYearValue(row.getCell(5).getNumericCellValue());
            assetList.add(asset);
        }
        return assetList;
    }

    private List<Liability> createLiabilitiesList(Sheet sheet) {
        List<Liability> liabilities = new ArrayList<>();
        Row row = sheet.getRow(30);
        String item = row.getCell(0).getStringCellValue();

        if (item.contains("Accounts Payable")) {
            Liability liability = new Liability();
            liability.setItem("Accounts Payable");
            liability.setPreviousYearValue(row.getCell(1).getNumericCellValue());
            liability.setCurrentYearValue(row.getCell(5).getNumericCellValue());
            liabilities.add(liability);
        }

        row = sheet.getRow(34);
        item = row.getCell(0).getStringCellValue();

        if (item.contains("Accrued Salaries and Vacation")) {
            Liability liability = new Liability();
            liability.setItem("Compensated Absences Payable");
            liability.setPreviousYearValue(row.getCell(1).getNumericCellValue());
            liability.setCurrentYearValue(row.getCell(5).getNumericCellValue());
            liabilities.add(liability);
        }

        row = sheet.getRow(38);
        item = row.getCell(0).getStringCellValue();

        if (item.contains("Other Liabilities")) {
            Liability liability = new Liability();
            liability.setItem("Other Liabilities");
            liability.setPreviousYearValue(row.getCell(1).getNumericCellValue());
            liability.setCurrentYearValue(row.getCell(5).getNumericCellValue());
            liabilities.add(liability);
        }

        return liabilities;
    }

    private Asset createasset(Sheet sheet) {
        Asset asset = new Asset();
        Row row = sheet.getRow(11);
        String assetItem = row.getCell(0).getStringCellValue();

        if (assetItem.contains("Cash")) {
            asset.setPreviousYearCashAndCashEquivalents(row.getCell(1).getNumericCellValue());
            asset.setCurrentYearCashAndCashEquivalents(row.getCell(5).getNumericCellValue());
        }

        row = sheet.getRow(15);
        assetItem = row.getCell(0).getStringCellValue();

        if (assetItem.contains("Receivable")) {
            asset.setPreviousYearAccountReceivable(row.getCell(1).getNumericCellValue());
            asset.setCurrentYearAccountReceivable(row.getCell(5).getNumericCellValue());
        }

        row = sheet.getRow(18);
        assetItem = row.getCell(0).getStringCellValue();

        if (assetItem.contains("Prepaid")) {
            asset.setPreviousYearPrepaidExpenses(row.getCell(1).getNumericCellValue());
            asset.setCurrentYearPrepaidExpenses(row.getCell(5).getNumericCellValue());
        }
        row = sheet.getRow(22);
        assetItem = row.getCell(0).getStringCellValue();

        if (assetItem.contains("Office")) {
            asset.setPreviousYearCLongFurnitureAndEquipment(row.getCell(1).getNumericCellValue());
            asset.setCurrentYearCLongFurnitureAndEquipment(row.getCell(5).getNumericCellValue());
        }

        row = sheet.getRow(25);
        assetItem = row.getCell(0).getStringCellValue();
        if (assetItem.contains("Other asset")) {
            asset.setPreviousYearCLongTermROU(row.getCell(1).getNumericCellValue());
            asset.setCurrentYearCLongTermROU(row.getCell(5).getNumericCellValue());
        }
        return asset;
    }

    private Liability createLiabilites(Sheet sheet) {
        Liability liabilities = new Liability();
        Row row = sheet.getRow(30);
        String liabilityItem = row.getCell(0).getStringCellValue();

        if (liabilityItem.contains("Accounts Payable")) {
            liabilities.setPreviousYearAccountsPayable(row.getCell(1).getNumericCellValue());
            liabilities.setCurrentYearAccountsPayable(row.getCell(5).getNumericCellValue());
        }

        row = sheet.getRow(15);
        liabilityItem = row.getCell(0).getStringCellValue();

        if (liabilityItem.contains("Accrued Salaries")) {
            liabilities.setPreviousYearAccuredSalariesAndVacation(row.getCell(1).getNumericCellValue());
            liabilities.setCurrentYearAccuredSalariesAndVacation(row.getCell(5).getNumericCellValue());
        }

        row = sheet.getRow(18);
        liabilityItem = row.getCell(0).getStringCellValue();

        if (liabilityItem.contains("Other Liabilities")) {
            liabilities.setPreviousYearOtherLiabilites(row.getCell(1).getNumericCellValue());
            liabilities.setCurrentYearOtherLiabilites(row.getCell(5).getNumericCellValue());
        }
        return liabilities;
    }

    private void generateExhibit(List<Asset> assetList, List<Liability> liabilities) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("exhibitB");

        Row row = sheet.createRow(0); // 0 is the row number
        Cell cell_7 = row.createCell(5); // 7 is the cell column
        cell_7.setCellValue("2023");

        Cell cell_9 = row.createCell(7); // 9 is the cell column
        cell_9.setCellValue("2022");

        Row row_1 = sheet.createRow(1);
        Cell cell_1 = row_1.createCell(1);
        cell_1.setCellValue("ASSETS");

        // Merge cells from A1 to B1
        CellRangeAddress mergedRegion = new CellRangeAddress(1, 1, 1, 4);
        sheet.addMergedRegion(mergedRegion);
        CellStyle cellStyle = workbook.createCellStyle();

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cell_1.setCellStyle(cellStyle);

        Row row_2 = sheet.createRow(2);
        Cell row_2cell_1 = row_2.createCell(1);
        row_2cell_1.setCellValue("");

        Row row_3 = sheet.createRow(3);
        Cell row_3Cell_1 = row_3.createCell(1); // 9 is the cell column
        row_3Cell_1.setCellValue("Current Assets:");

        CellRangeAddress currentAssetsMergeRegion = new CellRangeAddress(3, 3, 1, 4);
        sheet.addMergedRegion(currentAssetsMergeRegion);

        AtomicInteger rowNumber = new AtomicInteger(4);
        assetList.stream().forEach((asset) -> {
            Row assetRow = sheet.createRow(rowNumber.getAndIncrement());
            Cell label = assetRow.createCell(2); // 9 is the cell column
            label.setCellValue(asset.getItem());

            Cell valuePrYear = assetRow.createCell(5); // 9 is the cell column
            valuePrYear.setCellValue(asset.getPreviousYearValue());

            Cell valueCurrentYear = assetRow.createCell(7); // 9 is the cell column
            valueCurrentYear.setCellValue(asset.getCurrentYearValue());
        });
        System.out.println("-------------------------------------");


//        for (String asset : CURRENT_asset) {
//            Row newRow = sheet.createRow(rowNumber);
//            Cell newRowCell_1 = row.createCell(1);
//            newRowCell_1.setCellValue("");
//
//            Cell newRowCell_2 = row.createCell(2);
//            newRowCell_2.setCellValue(asset);
//
//            Cell newRowCell_3 = row.createCell(3);
//            newRowCell_3.setCellValue("");
//
//            Cell newRowCell_4 = row.createCell(4);
//            newRowCell_4.setCellValue("");
//
//            Cell newRowCell_5 = row.createCell(5);
//            newRowCell_5.setCellValue(asset.getCurrentYearCashAndCashEquivalents());
//
//            Cell newRowCell_6 = row.createCell(6);
//            newRowCell_6.setCellValue("");
//
//            Cell newRowCell_7 = row.createCell(5);
//            newRowCell_7.setCellValue(asset.getPreviousYearCashAndCashEquivalents());
//
//            rowNumber++;
//        }

        try (FileOutputStream outputStream = new FileOutputStream("Metronet2023 - Audit Report.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
