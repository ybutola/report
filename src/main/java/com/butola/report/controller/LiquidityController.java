package com.butola.report.controller;

import com.butola.report.data.mongo.Liquidity;
import com.butola.report.data.mongo.Report;
import com.butola.report.service.dynamiccontent.ReportGenerator;
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
    ReportGenerator reportGenerator;

    @Autowired
    ReportService reportService;


    @CrossOrigin
    @PostMapping(value = "/liquidity", consumes = "application/json")
    public ResponseEntity<String> generateReport(@RequestBody Liquidity liquidity, @RequestParam String companyName, @RequestParam Integer version) {
        String filepath = "RegularAudit_Template.docx";
        reportService.createReportWithLiquidity(liquidity, companyName, version);

        HashMap<String, Object> replacements = new HashMap<>();
        replacements.put("{cy}", liquidity.currentYear);
        replacements.put("{cyc}", liquidity.currentYearCash);
        replacements.put("{cygacr}", liquidity.currentYearGrantsAndContractsReceivable);
        replacements.put("{cytfa}", liquidity.currentYearTotalFinancialAssets);
        replacements.put("{tawdr}", liquidity.totalAssetsWithDonorRestrictions);
        replacements.put("{nawr}", liquidity.netAssetsWithRestrictions);
        replacements.put("{tana}", liquidity.totalAssetsNotAvailable);
        replacements.put("{faa}", liquidity.financialAssetsAvailable);

        reportGenerator.readReplaceAndWrite(filepath, replacements);
        return new ResponseEntity<>("Successfully modified liquidity and availability section.", HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/findLiquidity")
    public ResponseEntity<Liquidity> getLiquidity(@RequestParam String companyName, @RequestParam Integer version, @RequestParam Integer year) {
        Report report = reportService.getReport(companyName, version, year);
        return new ResponseEntity<>(report.getLiquidity(), HttpStatus.OK);
    }

}
