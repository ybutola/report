package com.butola.report.service.mongodbreport;

import com.butola.report.data.mongo.CashFlow;
import com.butola.report.data.mongo.Liquidity;
import com.butola.report.data.mongo.Report;
import com.butola.report.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    ReportRepository reportRepository;

    public void createReportWithLiquidity(Liquidity liquidity, String companyName, int version) {
        Report report = reportRepository.findByCompanyName(companyName, version, liquidity.currentYear);
        if (report == null) {
            Report newReport = new Report();
            newReport.setCompanyName(companyName);
            newReport.setVersion(version);
            newReport.setYear(liquidity.currentYear);
            newReport.setLiquidity(liquidity);
            reportRepository.save(newReport);
        } else {
            report.setLiquidity(liquidity);
            reportRepository.save(report);
        }
    }

    public void createReportWithCashflow(CashFlow cashFlow, String companyName, int version, int year) {
        Report report = reportRepository.findByCompanyName(companyName, version, year);
        if (report == null) {
            Report newReport = new Report();
            newReport.setCompanyName(companyName);
            newReport.setVersion(version);
            newReport.setYear(year);
            newReport.setCashFlow(cashFlow);
            reportRepository.save(newReport);
        } else {
            report.setCashFlow(cashFlow);
            reportRepository.save(report);
        }
    }

    public Report getReport(String companyName, int version, int year){
        Report report = reportRepository.findByCompanyName(companyName, version, year);
        return report;
    }
}
