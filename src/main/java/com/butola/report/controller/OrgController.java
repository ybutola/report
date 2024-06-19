package com.butola.report.controller;

import com.butola.report.data.mongo.Org;
import com.butola.report.service.mongodbreport.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("report/word/org")
public class OrgController {

    @Autowired
    ReportService reportService;

    @CrossOrigin
    @PostMapping(value = "/saveOrgInfo", consumes = "application/json")
    public ResponseEntity<String> saveOrgInfo(@RequestBody Org org) {
        String filepath = "RegularAudit_Template.docx";
        reportService.saveOrgInfo(org);
        return new ResponseEntity<>("Successfully added Org object.", HttpStatus.OK);
    }
}
