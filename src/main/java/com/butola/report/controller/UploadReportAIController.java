package com.butola.report.controller;

import com.butola.report.service.documentai.FetchDataFromReport;
import com.butola.report.service.documentai.UploadReportAI;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("report/upload")
public class UploadReportAIController {

    @Autowired
    UploadReportAI uploadReportAI;

    @Autowired
    FetchDataFromReport
            fetchDataFromReport;

    @CrossOrigin
    @PostMapping("/saveReport")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam String companyName,
                                             @RequestParam Integer year,
                                             @RequestParam Integer version) throws IOException {
        uploadReportAI.processAndUploadReport(companyName, year, version, file.getBytes());
        fetchDataFromReport.getReportData(file.getBytes());
        return new ResponseEntity<>("Successfully saved the file and added the template.", HttpStatus.CREATED);
    }
}
