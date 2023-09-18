package com.butola.report.service.worddoc;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileOutputStream;

@ContextConfiguration(classes = {WriteHeader.class})
@ExtendWith(SpringExtension.class)
class WriteHeaderTest {
    @Autowired
    private WriteHeader writeHeader;

    /**
     * Method under test: {@link WriteHeader#writeHeaderParagraph(XWPFDocument)}
     */
    @Test
    void testWriteHeaderParagraph() throws Exception {
        String wordFilePath = "testoutput.docx";
        XWPFDocument document = new XWPFDocument();
        writeHeader.writeHeaderParagraph(document);
        FileOutputStream outputStream = new FileOutputStream(wordFilePath);
        document.write(outputStream);
        outputStream.close();
    }

    /**
     * Method under test: {@link WriteHeader#writeHeaderParagraph(XWPFDocument)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testWriteHeaderParagraph2() {


        writeHeader.writeHeaderParagraph(null);
    }
}

