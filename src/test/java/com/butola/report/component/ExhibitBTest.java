package com.butola.report.component;

import com.butola.report.service.exhibits.exhibitb.ExhibitBGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExhibitBTest {

    @Autowired
    ExhibitBGenerator exhibitBGenerator;

    @Test
    void testExhibitB() {
        try {
            exhibitBGenerator.readTriaBalance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
