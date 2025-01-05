package com.butola.report.service.mongodbreport;

import com.butola.report.data.mongo.CashFlow;
import com.butola.report.data.mongo.Liquidity;
import com.butola.report.data.mongo.Org;
import com.butola.report.data.mongo.Report;
import com.butola.report.repository.ReportCollectionRepository;
import com.butola.report.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    ReportCollectionRepository reportCollection;

    public void createReport(Report report) {
        reportRepository.save(report);

    }

    public void createReportWithLiquidity(Liquidity liquidity, String companyName, int version) {
        Report report = reportRepository.findByCompanyName(companyName, version, liquidity.getCurrentYear());
        if (report == null) {
            Report newReport = new Report();
            newReport.setCompanyName(companyName);
            newReport.setVersion(version);
            newReport.setYear(liquidity.getCurrentYear());
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

    public void saveOrgInfo(Org org) {
        Report newReport = new Report();
        newReport.setCompanyName(org.getCompanyName());
        newReport.setYear(org.getAssessmentYear());
        newReport.setVersion(1);
        reportCollection.saveReportCollection(newReport);
    }

    public Report getReport(String companyName, int version, int year) {
        Report report = reportRepository.findByCompanyName(companyName, version, year);
        return report;
    }


    public Report[] getReports(String companyName, int version, int year) {
        Report currentYearReport = reportRepository.findByCompanyName(companyName, version, year);
        Report prevoiusYearReport = reportRepository.findByCompanyName(companyName, version, year - 1);
        Report[] reports = {currentYearReport, prevoiusYearReport};
        return reports;
    }
}
