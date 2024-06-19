package com.butola.report.controller;

import com.butola.report.data.SearchTemplateData;
import com.butola.report.data.mongo.Template;
import com.butola.report.repository.TemplateRepositoryCustom;
import com.butola.report.service.mongodbreport.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("report/template")
public class TemplateController {

    @Autowired
    TemplateService templateService;

    @CrossOrigin
    @PostMapping("/saveTemplate")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam String companyName,
                                             @RequestParam Integer year,
                                             @RequestParam Integer version) throws IOException {
        templateService.saveTemplate(companyName, year, version, file.getBytes());
        return new ResponseEntity<>("Successfully saved the file and added the template.", HttpStatus.CREATED);
    }
    @PostMapping("/searchTemplate")
    public ResponseEntity<List<Template>> submitData(@RequestBody SearchTemplateData data) {
        List<Template> templates =templateService.findTemplate(data);
        return new ResponseEntity<>(templates, HttpStatus.OK);
    }
}
