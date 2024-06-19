package com.butola.report.controller;

import com.butola.report.service.mongodbreport.ReportGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("report/word/report")
public class WordReportController {
    @Autowired
    ReportGeneratorService reportGenerator;

    @CrossOrigin
    @GetMapping(value = "/preview")
    public ResponseEntity<byte[]> previewReport(@RequestParam String companyName,
                                                @RequestParam Integer version,
                                                @RequestParam Integer year) {
        byte[] documentBytes = reportGenerator.generatePreview(companyName, version, year);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=document.docx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        return new ResponseEntity<>(documentBytes, headers, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/generate", consumes = "application/json")
    public ResponseEntity<String> generateReport(@RequestParam String companyName,
                                                 @RequestParam Integer version,
                                                 @RequestParam Integer year) {
        reportGenerator.generatePreview(companyName, version, year);
        return new ResponseEntity<>("Successfully modified liquidity and availability section.", HttpStatus.OK);
    }
}
