package com.butola.report.service.exhibits.exhibita;

import com.butola.report.data.Expense;
import com.butola.report.data.Revenue;
import org.apache.commons.math3.analysis.function.Exp;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ExpenseGenerator {
    public void generateExpense(List<Expense> expenseList, Workbook workbook, Sheet sheet){

    }
}
