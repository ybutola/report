package com.butola.report.service;

import com.butola.report.service.mongodbreport.ReportGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;

@ContextConfiguration(classes = {ReportGeneratorService.class})
@ExtendWith(SpringExtension.class)
public class DynamicContentTest {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ReportGeneratorService generater;

    @Test
    void testChangeContent() {
        String filepath = "RegularAudit_Template.docx";

        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("{cy}", "2024");
        replacements.put("{cyc}", "34532");
        replacements.put("{cygacr}", "567575");
        replacements.put("{cytfa}", "2124329789");

       // generater.readReplaceAndWrite(filepath, replacements);
    }
}
