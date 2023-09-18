package com.butola.report.service.worddoc;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileOutputStream;

@ContextConfiguration(classes = {WriteCashFlow.class})
@ExtendWith(SpringExtension.class)
class WriteCashFlowTest {
    @Autowired
    private WriteCashFlow writeCashFlow;

    @Test
    void testWriteHeaderParagraph() throws Exception {
        String wordFilePath = "testoutput2.docx";
        XWPFDocument document = new XWPFDocument();
        writeCashFlow.writeCashFlow(document);
        FileOutputStream outputStream = new FileOutputStream(wordFilePath);
        document.write(outputStream);
        outputStream.close();
    }

    /**
     * Method under test: {@link WriteCashFlow#writeHeaderParagraph(XWPFDocument)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testWriteHeaderParagraph2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.apache.poi.xwpf.usermodel.XWPFDocument.createParagraph()" because "document" is null
        //       at com.butola.report.service.worddoc.WriteCashFlow.writeHeaderParagraph(WriteCashFlow.java:16)
        //   See https://diff.blue/R013 to resolve this issue.

        writeCashFlow.writeCashFlow(null);
    }
}

