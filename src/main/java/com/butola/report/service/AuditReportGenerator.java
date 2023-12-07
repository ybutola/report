package com.butola.report.service;

import com.butola.report.service.exhibits.exhibita.ExhibitAGenerator;
import com.butola.report.service.exhibits.exhibitb.ExhibitBGenerator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuditReportGenerator {

    @Autowired
    ExhibitAGenerator exhibitAGenerator;

    @Autowired
    ExhibitBGenerator exhibitBGenerator;

    public void auditReportGenerator() throws IOException {
        Workbook auditReport = new XSSFWorkbook();
        exhibitAGenerator.generateExhibitA(auditReport);
        exhibitBGenerator.generateExhibitB(auditReport);
    }
}
