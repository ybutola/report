package com.butola.report.service;

import com.butola.report.data.FinacialReport;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class ExcelReader {
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
}