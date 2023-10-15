package com.butola.report.service.exhibits;

import com.butola.report.data.Assets;
import com.butola.report.data.Liabilities;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
                generateExhibit(createAssetsList(sheet), createLiabilitiesList(sheet));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                inputStream.close();
            }
        }
    }

    private Map<String, Double> createAssetsList(Sheet sheet) {
        Map<String, Double> assets = new HashMap<>();
        Row row = sheet.getRow(11);
        String assetItem = row.getCell(0).getStringCellValue();

        if (assetItem.contains("Cash")) {
            assets.put("previousYearCashAndCashEquivalents", row.getCell(1).getNumericCellValue());
            assets.put("currentYearCashAndCashEquivalents", row.getCell(5).getNumericCellValue());
        }

        row = sheet.getRow(15);
        assetItem = row.getCell(0).getStringCellValue();

        if (assetItem.contains("Receivable")) {
            assets.put("previousYearAccountReceivable", row.getCell(1).getNumericCellValue());
            assets.put("currentYearAccountReceivable", row.getCell(5).getNumericCellValue());
        }

        row = sheet.getRow(18);
        assetItem = row.getCell(0).getStringCellValue();

        if (assetItem.contains("Prepaid")) {
            assets.put("previousYearPrepaidExpenses", row.getCell(1).getNumericCellValue());
            assets.put("currentYearPrepaidExpenses", row.getCell(5).getNumericCellValue());
        }
        row = sheet.getRow(22);
        assetItem = row.getCell(0).getStringCellValue();

        if (assetItem.contains("Office")) {
            assets.put("previousYearCLongFurnitureAndEquipment", row.getCell(1).getNumericCellValue());
            assets.put("currentYearCLongFurnitureAndEquipment", row.getCell(5).getNumericCellValue());
        }

        row = sheet.getRow(25);
        assetItem = row.getCell(0).getStringCellValue();
        if (assetItem.contains("Other Assets")) {
            assets.put("previousYearCLongTermROU", row.getCell(1).getNumericCellValue());
            assets.put("currentYearCLongTermROU", row.getCell(5).getNumericCellValue());
        }
        return assets;
    }

    private Map<String, Double> createLiabilitiesList(Sheet sheet) {
        Map<String, Double> liabilities = new HashMap<>();
        Row row = sheet.getRow(30);
        String item = row.getCell(0).getStringCellValue();

        if (item.contains("Accounts Payable")) {
            liabilities.put("previousYearAccountsPayable", row.getCell(1).getNumericCellValue());
            liabilities.put("currentYearAccountsPayable", row.getCell(5).getNumericCellValue());
        }

        row = sheet.getRow(34);
        item = row.getCell(0).getStringCellValue();

        if (item.contains("Accrued Salaries and Vacatio")) {
            liabilities.put("previousYearAccuredSalariesAndVacation", row.getCell(1).getNumericCellValue());
            liabilities.put("currentYearAccuredSalariesAndVacation", row.getCell(5).getNumericCellValue());
        }

        row = sheet.getRow(38);
        item = row.getCell(0).getStringCellValue();

        if (item.contains("Other Liabilities")) {
            liabilities.put("previousYearOtherLiabilites", row.getCell(1).getNumericCellValue());
            liabilities.put("currentYearOtherLiabilites", row.getCell(5).getNumericCellValue());
        }

        return liabilities;
    }

    private Assets createAssets(Sheet sheet) {
        Assets assets = new Assets();
        Row row = sheet.getRow(11);
        String assetItem = row.getCell(0).getStringCellValue();

        if (assetItem.contains("Cash")) {
            assets.setPreviousYearCashAndCashEquivalents(row.getCell(1).getNumericCellValue());
            assets.setCurrentYearCashAndCashEquivalents(row.getCell(5).getNumericCellValue());
        }

        row = sheet.getRow(15);
        assetItem = row.getCell(0).getStringCellValue();

        if (assetItem.contains("Receivable")) {
            assets.setPreviousYearAccountReceivable(row.getCell(1).getNumericCellValue());
            assets.setCurrentYearAccountReceivable(row.getCell(5).getNumericCellValue());
        }

        row = sheet.getRow(18);
        assetItem = row.getCell(0).getStringCellValue();

        if (assetItem.contains("Prepaid")) {
            assets.setPreviousYearPrepaidExpenses(row.getCell(1).getNumericCellValue());
            assets.setCurrentYearPrepaidExpenses(row.getCell(5).getNumericCellValue());
        }
        row = sheet.getRow(22);
        assetItem = row.getCell(0).getStringCellValue();

        if (assetItem.contains("Office")) {
            assets.setPreviousYearCLongFurnitureAndEquipment(row.getCell(1).getNumericCellValue());
            assets.setCurrentYearCLongFurnitureAndEquipment(row.getCell(5).getNumericCellValue());
        }

        row = sheet.getRow(25);
        assetItem = row.getCell(0).getStringCellValue();
        if (assetItem.contains("Other Assets")) {
            assets.setPreviousYearCLongTermROU(row.getCell(1).getNumericCellValue());
            assets.setCurrentYearCLongTermROU(row.getCell(5).getNumericCellValue());
        }
        return assets;
    }

    private Liabilities createLiabilites(Sheet sheet) {
        Liabilities liabilities = new Liabilities();
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

    private void generateExhibit(Map<String, Double> assets, Map<String, Double> liabilities) {
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

        Row row_2 = sheet.createRow(2);
        Cell row_2cell_1 = row_2.createCell(1);
        row_2cell_1.setCellValue("");

        Row row_3 = sheet.createRow(3);
        Cell row_3Cell_1 = row_3.createCell(1); // 9 is the cell column
        row_3Cell_1.setCellValue("Current Assets");

        int rowNumber = 4;
        assets.forEach((k, v) ->
                System.out.println("Key =" + k + ", value = " + v)
        );
        System.out.println("-------------------------------------");
        liabilities.forEach((k, v) ->
                System.out.println("Key =" + k + ", value = " + v)
        );

//        for (String asset : CURRENT_ASSETS) {
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
//            newRowCell_5.setCellValue(assets.getCurrentYearCashAndCashEquivalents());
//
//            Cell newRowCell_6 = row.createCell(6);
//            newRowCell_6.setCellValue("");
//
//            Cell newRowCell_7 = row.createCell(5);
//            newRowCell_7.setCellValue(assets.getPreviousYearCashAndCashEquivalents());
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
