package com.butola.report.component;

import com.butola.report.service.AuditReportGenerator;
import com.butola.report.service.exhibits.exhibitb.ExhibitBGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExhibitBTest {

    @Autowired
    ExhibitBGenerator exhibitBGenerator;

    @Autowired
    AuditReportGenerator auditReportGenerator;
    @Test
    void testExhibitB() {
        try {
            auditReportGenerator.auditReportGenerator();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
