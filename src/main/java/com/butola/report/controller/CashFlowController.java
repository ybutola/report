package com.butola.report.controller;

import com.butola.report.data.mongo.CashFlow;
import com.butola.report.data.mongo.Report;
import com.butola.report.service.mongodbreport.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("report/word/cashflow")
public class CashFlowController {

    @Autowired
    ReportService reportService;

    @CrossOrigin
    @PostMapping(value = "/saveCashflow", consumes = "application/json")
    public ResponseEntity<Map<String, String>> saveCashFlow(@RequestBody CashFlow cashFlow,
                                                            @RequestParam String companyName,
                                                            @RequestParam Integer version,
                                                            @RequestParam Integer year) {
        String filepath = "RegularAudit_Template.docx";
        reportService.createReportWithCashflow(cashFlow, companyName, version, year);
        Map<String, String> map = new HashMap();
        map.put("message", "Successfully added/updated Cashflow object.");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/findCashflow")
    public ResponseEntity<CashFlow> findCashFlow(@RequestParam String companyName,
                                                 @RequestParam Integer version,
                                                 @RequestParam Integer year) {
        Report report = reportService.getReport(companyName, version, year);
        return new ResponseEntity<>(report.getCashFlow(), HttpStatus.OK);
    }

}
