package com.butola.report.controller;

import com.butola.report.data.mongo.CashFlow;
import com.butola.report.data.mongo.Report;
import com.butola.report.service.mongodbreport.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("report/word/cashflow")
public class CashFlowController {

    @Autowired
    ReportService reportService;

    @CrossOrigin
    @PostMapping(value = "/saveCashflow", consumes = "application/json")
    public ResponseEntity<String> generateReport(@RequestBody CashFlow cashFlow,
                                                 @RequestParam String companyName,
                                                 @RequestParam Integer version,
                                                 @RequestParam Integer year) {
        String filepath = "RegularAudit_Template.docx";
        reportService.createReportWithCashflow(cashFlow, companyName, version, year);
        return new ResponseEntity<>("Successfully added/updated Cashflow object.", HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/findCashflow")
    public ResponseEntity<CashFlow> getLiquidity(@RequestParam String companyName,
                                                 @RequestParam Integer version,
                                                 @RequestParam Integer year) {
        Report report = reportService.getReport(companyName, version, year);
        return new ResponseEntity<>(report.getCashFlow(), HttpStatus.OK);
    }

}
