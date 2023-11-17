package com.butola.report.service;

import com.butola.report.data.CashFlow;
import com.butola.report.data.FinacialReport;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class ExcelReader {

    @Autowired
    ResourceLoader resourceLoader;

    public FinacialReport readExcelFile(String excelFilePath) {
        try {
            FileInputStream excelFile = new FileInputStream(excelFilePath);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet

            // Update the FinancialReport object.

            workbook.close();
            excelFile.close();
            System.out.println("Excel data written to Word document successfully!");
            return new FinacialReport();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new FinacialReport();
    }

    public CashFlow readCashFlow(String excelFilePath) {
        try {
            Resource resource = resourceLoader.getResource("classpath:" + excelFilePath);
            if (resource.exists()) {
                //FileInputStream excelFile = new FileInputStream(excelFilePath);
                Workbook workbook = new XSSFWorkbook(resource.getInputStream());
                Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
                Row rowOne = sheet.getRow(1);
                String amt_1 = getCellValue(rowOne.getCell(1));
                String amt_2 = getCellValue(rowOne.getCell(2));

                Row rowTwo = sheet.getRow(1);
                String amt_3 = getCellValue(rowTwo.getCell(1));
                String amt_4 = getCellValue(rowTwo.getCell(1));

                Row rowThree = sheet.getRow(1);
                String amt_5 = getCellValue(rowThree.getCell(1));
                String amt_6 = getCellValue(rowThree.getCell(1));

                workbook.close();
                //    excelFile.close();
                System.out.println("Excel data written to Word document successfully!");
                CashFlow cashFlow = new CashFlow();
                cashFlow.setAmt_1(amt_1);
                cashFlow.setAmt_2(amt_2);
                cashFlow.setAmt_3(amt_3);
                cashFlow.setAmt_4(amt_4);
                cashFlow.setAmt_5(amt_5);
                cashFlow.setAmt_6(amt_6);
                return cashFlow;
            } else {
                throw new IOException("File not found: ");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CashFlow();
    }

    public static String getCellValue(Cell cell) {
        String value = "";
        if (null != cell) {
            switch (cell.getCellType()) {
                case STRING:
                    value = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    value = String.valueOf(cell.getNumericCellValue());
                  //  value = value.substring(0, value.length() - 2);
                    break;
                default:
                    System.out.print("\t");
                    break;
            }
        }
        return value;
    }
}