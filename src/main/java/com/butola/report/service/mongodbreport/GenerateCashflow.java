package com.butola.report.service.mongodbreport;

import com.butola.report.data.mongo.CashFlow;
import com.butola.report.data.mongo.Report;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.stereotype.Service;

@Service
public class GenerateCashflow extends GenerateReportUtil {

    public void createCashflow(XWPFDocument document, Report currentYearReport, Report previousYearReport) {
        CashFlow currentYearReportCashFlow = currentYearReport.getCashFlow();
        CashFlow previousYearReportCashFlow = previousYearReport.getCashFlow();

        String[][] data = {
                {"10.\t", "Cash Flow Operating Adjustments", "", ""},
                {"\t", "", "", ""},
                {"\t", "Adjustments to reconcile Change in Net Assets to Net Cash Provided by Operating Activities were as follows as of:", "", ""},
                {"\t", "", "   December 31", ""},
                {"\t", "", "2024", "2023"},
                {"\t", "Depreciation", currentYearReportCashFlow.getDeclaration() + "", "$" + previousYearReportCashFlow.getDeclaration()},
                {"\t", "Net Change in Operating Lease Activity", currentYearReportCashFlow.getNetChangeInOperatingLeaseActivity() + "", "$" + previousYearReportCashFlow.getNetChangeInOperatingLeaseActivity()},
                {"\t", "Loss on Sale of Property", " " + currentYearReportCashFlow.getLossOnSaleOfProperty(), "" + previousYearReportCashFlow.getLossOnSaleOfProperty()},
                {"\t", "Increases (Decreases) in Current Liabilities:", " ", ""},
                {"\t", "        Accounts Payable", " " + currentYearReportCashFlow.getAccountsPayable(), "" + previousYearReportCashFlow.getAccountsPayable()},
                {"\t", "        Accounts Expenses", " " + currentYearReportCashFlow.getAccruedExpenses(), "" + previousYearReportCashFlow.getAccruedExpenses()},

                {"\t", "Decreases (Increases) in Current Assets:", " ", ""},
                {"\t", "        Grants and Contracts Receivable", " " + currentYearReportCashFlow.getGrantsAndContractsReceivable(), "" + previousYearReportCashFlow.getGrantsAndContractsReceivable()},
                {"\t", "        Prepaid Expense", " " + currentYearReportCashFlow.getPrepaidExpenses(), "" + previousYearReportCashFlow.getPrepaidExpenses()},
                {"\t", "        Total Adjustments", " " + currentYearReportCashFlow.getTotalAdjustments(), "" + previousYearReportCashFlow.getTotalAdjustments()},
        };

        XWPFTable table = document.createTable(20, 4);
        removeTableBorders(table);
        preserveBordersForSpecificCells(table);
        mergeCellsHorizontally(table, 4, 2, 3);
        table.setWidth("100%");
        populateTableData(data, table);
    }
}
