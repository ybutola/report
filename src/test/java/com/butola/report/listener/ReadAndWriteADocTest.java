package com.butola.report.listener;

import com.butola.report.service.worddoc.WriteCashFlow;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileOutputStream;
@ContextConfiguration(classes = {ReadAndWriteADoc.class})
@ExtendWith(SpringExtension.class)
class ReadAndWriteADocTest {
    @Autowired
    ReadAndWriteADoc readAndWriteADoc;

    @Test
    void testWriteHeaderParagraph() throws Exception {
        XWPFDocument document =  readAndWriteADoc.reandNWrite(1);
    }
}

