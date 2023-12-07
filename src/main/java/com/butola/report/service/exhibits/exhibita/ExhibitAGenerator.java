package com.butola.report.service.exhibits.exhibita;

import com.butola.report.data.Expense;
import com.butola.report.data.Revenue;
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
public class ExhibitAGenerator {
    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    RevenueGenerator revenueGenerator;

    @Autowired
    ExpenseGenerator expenseGenerator;

    public void generateExhibitA(Workbook auditReport) throws IOException {
        try {
            Sheet sheet = readTrialBalance();
            Sheet exhibitAsheet = auditReport.createSheet("exhibitA");
            revenueGenerator.generateRevenue(createRevenueList(sheet), auditReport, exhibitAsheet);
            expenseGenerator.generateExpense(createExpenseList(sheet), auditReport, exhibitAsheet);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private Sheet readTrialBalance() throws IOException {
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

    private List<Revenue> createRevenueList(Sheet sheet) {
        List<Revenue> revenueList = new ArrayList<>();
        Row row = sheet.getRow(45);
        String assetItem = row.getCell(0).getStringCellValue();

        if (assetItem.contains("State Grants")) {
            Revenue revenue = new Revenue();
            revenue.setItem("State Grants");
            revenue.setPreviousYearValue(row.getCell(1).getNumericCellValue());
            revenue.setCurrentYearValue(row.getCell(5).getNumericCellValue());
            revenueList.add(revenue);
        }
        return revenueList;
    }

    private List<Expense> createExpenseList(Sheet sheet) {
        List<Expense> expenseList = new ArrayList<>();
        updateExpenseList(expenseList, "Personnel Expense", "Personnel Expense", sheet, 53);
        updateExpenseList(expenseList, "Contract Services", "Contract Services", sheet, 56);
        updateExpenseList(expenseList, "CE Scholarship", "CE Scholarship", sheet, 59);
        updateExpenseList(expenseList, "Professional Fees", "Professional Fees", sheet, 64);
        updateExpenseList(expenseList, "Rent", "Rent", sheet, 68);
        updateExpenseList(expenseList, "Supplies and Equiptment", "Supplies and Equiptment", sheet, 72);
        updateExpenseList(expenseList, "Telephone", "Telephone", sheet, 76);
        updateExpenseList(expenseList, "Postage", "Postage", sheet, 79);
        updateExpenseList(expenseList, "Insurance", "Insurance", sheet, 82);
        updateExpenseList(expenseList, "Travel & Meetings", "Travel & Meetings", sheet, 88);
        updateExpenseList(expenseList, "Training", "Training", sheet, 92);
        updateExpenseList(expenseList, "Member Services", "Member Services", sheet, 96);
        updateExpenseList(expenseList, "Depreciation", "Depreciation", sheet, 99);
        updateExpenseList(expenseList, "Miscellaneous", "Miscellaneous", sheet, 103);
        return expenseList;
    }

    private void updateExpenseList(List<Expense> expenseList, String item, String newItemName, Sheet sheet, int rowNumber) {
        Row row = sheet.getRow(rowNumber);
        String assetItem = row.getCell(0).getStringCellValue();
        if (assetItem.contains(item)) {
            Expense expense = new Expense();
            expense.setItem(newItemName);
            expense.setPreviousYearValue(row.getCell(1).getNumericCellValue());
            expense.setCurrentYearValue(row.getCell(5).getNumericCellValue());
            expenseList.add(expense);
        }
    }
}
