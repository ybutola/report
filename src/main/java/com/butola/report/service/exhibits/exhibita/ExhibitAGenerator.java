package com.butola.report.service.exhibits.exhibita;

import com.butola.report.data.Expense;
import com.butola.report.data.Revenue;
import com.butola.report.utility.Excel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
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
            Sheet exhibitAsheet = auditReport.createSheet("exhibit A");
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
        //Row row = sheet.getRow(45);
        Row row = sheet.getRow(Excel.findRowNumber("State Grants", sheet));
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

        updateExpenseList(expenseList, "Personnel Expense", "Personnel Expense", sheet);
        updateExpenseList(expenseList, "Contract Services", "Contract Services", sheet);
        updateExpenseList(expenseList, "CE Scholarship", "CE Scholarship", sheet);
        updateExpenseList(expenseList, "Professional Fees", "Professional Fees", sheet);
        updateExpenseList(expenseList, "Rent", "Rent", sheet);
        updateExpenseList(expenseList, "Supplies and Equiptment", "Supplies and Equiptment", sheet);
        updateExpenseList(expenseList, "Telephone", "Telephone", sheet);
        updateExpenseList(expenseList, "Postage", "Postage", sheet);
        updateExpenseList(expenseList, "Insurance", "Insurance", sheet);
        updateExpenseList(expenseList, "Travel & Meetings", "Travel & Meetings", sheet);
        updateExpenseList(expenseList, "Training", "Training", sheet);
        updateExpenseList(expenseList, "Member Services", "Member Services", sheet);
        updateExpenseList(expenseList, "Depreciation", "Depreciation", sheet);
        updateExpenseList(expenseList, "Miscellaneous", "Miscellaneous", sheet);


//        updateExpenseList(expenseList, "Personnel Expense", "Personnel Expense", sheet, 53);
//        updateExpenseList(expenseList, "Contract Services", "Contract Services", sheet, 56);
//        updateExpenseList(expenseList, "CE Scholarship", "CE Scholarship", sheet, 59);
//        updateExpenseList(expenseList, "Professional Fees", "Professional Fees", sheet, 64);
//        updateExpenseList(expenseList, "Rent", "Rent", sheet, 68);
//        updateExpenseList(expenseList, "Supplies and Equiptment", "Supplies and Equiptment", sheet, 72);
//        updateExpenseList(expenseList, "Telephone", "Telephone", sheet, 76);
//        updateExpenseList(expenseList, "Postage", "Postage", sheet, 79);
//        updateExpenseList(expenseList, "Insurance", "Insurance", sheet, 82);
//        updateExpenseList(expenseList, "Travel & Meetings", "Travel & Meetings", sheet, 88);
//        updateExpenseList(expenseList, "Training", "Training", sheet, 92);
//        updateExpenseList(expenseList, "Member Services", "Member Services", sheet, 96);
//        updateExpenseList(expenseList, "Depreciation", "Depreciation", sheet, 99);
//        updateExpenseList(expenseList, "Miscellaneous", "Miscellaneous", sheet, 103);
        return expenseList;
    }

    private void updateExpenseList(List<Expense> expenseList, String item, String newItemName, Sheet sheet) {
        Row row = sheet.getRow(Excel.findRowNumber(item, sheet));

        String assetItem = row.getCell(0).getStringCellValue();
        if (assetItem.contains(item)) {
            Expense expense = new Expense();
            expense.setItem(newItemName);
            expense.setPreviousYearValue(row.getCell(1).getNumericCellValue());
            expense.setCurrentYearValue(row.getCell(5).getNumericCellValue());
            expenseList.add(expense);
        }
    }

   /* private int findRowNumber(String textToFind, Sheet sheet) {
        int columnIndex = 0; // The text to search should always be in the first column.

        for (Row row : sheet) {
            Cell cell = row.getCell(columnIndex);

            if (cell != null && cell.getCellType() == CellType.STRING) {
                String cellValue = cell.getStringCellValue();
                CellStyle cellStyle = cell.getCellStyle();
                XSSFFont font = ((XSSFCellStyle) cellStyle).getFont();

                boolean isBold = font.getBold();
                if (isBold && cellValue.contains(textToFind)) {
                    int rowNumber = row.getRowNum(); // 0-based index
                    System.out.println("Text found in row: " + rowNumber);
                    return rowNumber;
                }
            }
        }
        return 0;
    }*/
}
