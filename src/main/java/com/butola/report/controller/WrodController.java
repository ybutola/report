package com.butola.report.controller;

import com.butola.report.data.mongo.CashFlow;
import com.butola.report.service.dynamiccontent.ReportGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("report/word/modifyContent")
public class WrodController {
    @Autowired
    ReportGenerator reportGenerator;

 /*   @CrossOrigin
    @PostMapping(value = "/liquidity",consumes = "application/json")
    public ResponseEntity<String> generateReport(@RequestBody Liquidity liquidity) {
        String filepath = "RegularAudit_Template.docx";

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
    }*/

    @PostMapping(value = "/cashflow",consumes = "application/json")
    public ResponseEntity<String> modifyCashFlowOperatingAdjustments(@RequestBody CashFlow cashFlow) {
        String filepath = "RegularAudit_Template.docx";

        HashMap<String, Object> replacements = new HashMap<>();
        replacements.put("{dec}", cashFlow.declaration);
        replacements.put("{nciola}", cashFlow.netChangeInOperatingLeaseActivity);
        replacements.put("{losop}", cashFlow.lossOnSaleOfProperty);
        replacements.put("{ap}", cashFlow.amountsPayable);
        replacements.put("{acexp}", cashFlow.accruedExpenses);
        replacements.put("{gacr}", cashFlow.grantsAndContractsReceivable);
        replacements.put("{pe}", cashFlow.prepaidExpenses);
        replacements.put("{ta}", cashFlow.totalAdjustments);

        reportGenerator.readReplaceAndWrite(filepath, replacements);
        return new ResponseEntity<>("Successfully modified Cash Flow Operating Adjustments section.", HttpStatus.OK);
    }
}
