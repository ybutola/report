package com.butola.report.controller;

import com.butola.report.data.mongo.Liquidity;
import com.butola.report.data.mongo.Report;
import com.butola.report.service.mongodbreport.ReportGeneratorService;
import com.butola.report.service.mongodbreport.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("report/word/liquidity")
public class LiquidityController {
    @Autowired
    ReportGeneratorService reportGenerator;

    @Autowired
    ReportService reportService;

    @CrossOrigin
    @PostMapping(value = "/liquidity", consumes = "application/json")
    public ResponseEntity<String> saveLiquidity(@RequestBody Liquidity liquidity, @RequestParam String companyName, @RequestParam Integer version) {
        reportService.createReportWithLiquidity(liquidity, companyName, version);
        return new ResponseEntity<>("Successfully modified liquidity and availability section.", HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/findLiquidity")
    public ResponseEntity<Liquidity> findLiquidity(@RequestParam String companyName, @RequestParam Integer version, @RequestParam Integer year) {
        Report report = reportService.getReport(companyName, version, year);
        return new ResponseEntity<>(report.getLiquidity(), HttpStatus.OK);
    }

}
