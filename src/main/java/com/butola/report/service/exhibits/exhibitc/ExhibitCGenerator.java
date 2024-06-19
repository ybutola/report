package com.butola.report.service.exhibits.exhibitc;

import com.butola.report.data.Asset;
import com.butola.report.data.Liability;
import com.butola.report.service.exhibits.exhibitb.AssetsGenerator;
import com.butola.report.service.exhibits.exhibitb.LiabilitiesGenerator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExhibitCGenerator {
    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    AssetsGenerator assetsGenerator;

    @Autowired
    LiabilitiesGenerator liabilitiesGenerator;

    public void generateExhibitB(Workbook auditReport) throws IOException {
        try {
            Sheet sheet = readTrialBalance();
            Sheet exhibitBsheet = auditReport.createSheet("exhibit B");

            assetsGenerator.generateAssets(createAssetList(sheet), auditReport, exhibitBsheet);
        //    liabilitiesGenerator.generateLiabilities(createLiabilitiesList(sheet), auditReport, exhibitBsheet);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public Sheet readTrialBalance() throws IOException {
        String excelFilePath = "files/MetronetTB.xlsx";
        Resource resource = resourceLoader.getResource("classpath:" + excelFilePath);
        Sheet sheet = null;
        if (resource.exists()) {
            InputStream inputStream = null;
            try {
                inputStream = resource.getInputStream();
                Workbook workbook = new XSSFWorkbook(inputStream);
                sheet = workbook.getSheetAt(0);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                inputStream.close();
            }
        }
        return sheet;
    }

    private List<Asset> createAssetList(Sheet sheet) {
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

}
