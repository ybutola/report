package com.butola.report.service.mongodbreport;

import com.butola.report.data.mongo.Liquidity;
import com.butola.report.data.mongo.Report;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.stereotype.Service;

@Service
public class GenerateLiquidity extends GenerateReportUtil {

    public void createLiquidity(XWPFDocument document, Report currentYearReport, Report previousYearReport) {
        Liquidity currentYearReportLiquidity = currentYearReport.getLiquidity();
        Liquidity previousYearLiquidity = previousYearReport.getLiquidity();

        String[][] data = {
                {"9.\t", "Liquidity and Availability", "", ""},
                {"\t", "", "", ""},
                {"\t", "The following represents COPAL'S financial assets as of:", "", ""},
                {"\t", "", "   December 31", ""},
                {"\t", "", "2024", "2023"},
                {"\t", "Financial Assets:", "", ""},
                {"\t", "    Cash", currentYearReportLiquidity.getCurrentYearCash() + "", "$678301"},
                {"\t", "    Grants and Contracts Receivable", " " + currentYearReportLiquidity.getCurrentYearGrantsAndContractsReceivable(), "" + previousYearLiquidity.getCurrentYearGrantsAndContractsReceivable()},
                {"\t", "        Total Financial Assets", " " + currentYearReportLiquidity.getCurrentYearTotalFinancialAssets(), "" + previousYearLiquidity.getCurrentYearTotalFinancialAssets()},
                {"\t", "", "", ""},
                {"\t", "Less assets not available to be used within one year:", "", ""},
                {"\t", "    Total Assets with Donor Restrictions", currentYearReportLiquidity.getTotalAssetsWithDonorRestrictions() + "", "$ " + previousYearLiquidity.getTotalAssetsWithDonorRestrictions()},
                {"\t", "    Net Assets with Restrictions to be met within a year", " " + currentYearReportLiquidity.getNetAssetsWithRestrictions(), "" + previousYearLiquidity.getNetAssetsWithRestrictions()},
                {"\t", "        Total assets not available to be used within one year ", " " + currentYearReportLiquidity.getTotalAssetsNotAvailable(), "" + previousYearLiquidity.getTotalAssetsNotAvailable()},
                {"\t", "    Financial assets available for general expenditures within one year", " " + currentYearReportLiquidity.getFinancialAssetsAvailable(), "" + previousYearLiquidity.getFinancialAssetsAvailable()},
        };

        XWPFTable table = document.createTable(20, 4);
        removeTableBorders(table);
        preserveBordersForSpecificCells(table);
        mergeCellsHorizontally(table, 4, 2, 3);
        table.setWidth("100%");
        populateTableData(data, table);
    }
}
