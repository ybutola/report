package com.butola.report.service.worddoc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.butola.report.data.CashFlow;
import com.butola.report.service.ExcelReader;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileOutputStream;

@ContextConfiguration(classes = {WriteCashFlow.class})
@ExtendWith(SpringExtension.class)
class WriteCashFlowTest {
    @MockBean
    private ExcelReader excelReader;

    @Autowired
    private WriteCashFlow writeCashFlow;

    /**
     * Method under test: {@link WriteCashFlow#writeCashFlow(XWPFDocument)}
     */
    @Test
    void testWriteCashFlow() {
        CashFlow cashFlow = new CashFlow();
        cashFlow.setAmt_1("Amt 1");
        cashFlow.setAmt_2("Amt 2");
        cashFlow.setAmt_3("Amt 3");
        cashFlow.setAmt_4("Amt 4");
        cashFlow.setAmt_5("Amt 5");
        cashFlow.setAmt_6("Amt 6");
        cashFlow.setYears("Years");
        when(excelReader.readCashFlow(Mockito.<String>any())).thenReturn(cashFlow);
        XWPFDocument document = new XWPFDocument();
        writeCashFlow.writeCashFlow(document);
        verify(excelReader).readCashFlow(Mockito.<String>any());
        assertEquals(1, document.getTables().size());
    }

    /**
     * Method under test: {@link WriteCashFlow#writeCashFlow(XWPFDocument)}
     */
    @Test
    void testWriteCashFlow2() {
        CashFlow cashFlow = mock(CashFlow.class);
        when(cashFlow.getAmt_1()).thenReturn("");
        when(cashFlow.getAmt_2()).thenReturn("Amt 2");
        when(cashFlow.getAmt_3()).thenReturn("Amt 3");
        when(cashFlow.getAmt_4()).thenReturn("Amt 4");
        when(cashFlow.getAmt_5()).thenReturn("Amt 5");
        when(cashFlow.getAmt_6()).thenReturn("Amt 6");
        when(cashFlow.getYears()).thenReturn("Years");
        doNothing().when(cashFlow).setAmt_1(Mockito.<String>any());
        doNothing().when(cashFlow).setAmt_2(Mockito.<String>any());
        doNothing().when(cashFlow).setAmt_3(Mockito.<String>any());
        doNothing().when(cashFlow).setAmt_4(Mockito.<String>any());
        doNothing().when(cashFlow).setAmt_5(Mockito.<String>any());
        doNothing().when(cashFlow).setAmt_6(Mockito.<String>any());
        doNothing().when(cashFlow).setYears(Mockito.<String>any());
        cashFlow.setAmt_1("Amt 1");
        cashFlow.setAmt_2("Amt 2");
        cashFlow.setAmt_3("Amt 3");
        cashFlow.setAmt_4("Amt 4");
        cashFlow.setAmt_5("Amt 5");
        cashFlow.setAmt_6("Amt 6");
        cashFlow.setYears("Years");
        when(excelReader.readCashFlow(Mockito.<String>any())).thenReturn(cashFlow);
        XWPFDocument document = new XWPFDocument();
        writeCashFlow.writeCashFlow(document);
        verify(excelReader).readCashFlow(Mockito.<String>any());
        verify(cashFlow).getAmt_1();
        verify(cashFlow).getAmt_2();
        verify(cashFlow).getAmt_3();
        verify(cashFlow).getAmt_4();
        verify(cashFlow).getAmt_5();
        verify(cashFlow).getAmt_6();
        verify(cashFlow).getYears();
        verify(cashFlow).setAmt_1(Mockito.<String>any());
        verify(cashFlow).setAmt_2(Mockito.<String>any());
        verify(cashFlow).setAmt_3(Mockito.<String>any());
        verify(cashFlow).setAmt_4(Mockito.<String>any());
        verify(cashFlow).setAmt_5(Mockito.<String>any());
        verify(cashFlow).setAmt_6(Mockito.<String>any());
        verify(cashFlow).setYears(Mockito.<String>any());
        assertEquals(1, document.getTables().size());
    }

    /**
     * Method under test: {@link WriteCashFlow#writeCashFlow(XWPFDocument)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testWriteCashFlow3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.apache.poi.xwpf.usermodel.XWPFDocument.createParagraph()" because "document" is null
        //       at com.butola.report.service.worddoc.WriteCashFlow.writeCashFlow(WriteCashFlow.java:24)
        //   See https://diff.blue/R013 to resolve this issue.

        CashFlow cashFlow = mock(CashFlow.class);
        when(cashFlow.getAmt_1()).thenReturn("Amt 1");
        when(cashFlow.getAmt_2()).thenReturn("Amt 2");
        when(cashFlow.getAmt_3()).thenReturn("Amt 3");
        when(cashFlow.getAmt_4()).thenReturn("Amt 4");
        when(cashFlow.getAmt_5()).thenReturn("Amt 5");
        when(cashFlow.getAmt_6()).thenReturn("Amt 6");
        when(cashFlow.getYears()).thenReturn("Years");
        doNothing().when(cashFlow).setAmt_1(Mockito.<String>any());
        doNothing().when(cashFlow).setAmt_2(Mockito.<String>any());
        doNothing().when(cashFlow).setAmt_3(Mockito.<String>any());
        doNothing().when(cashFlow).setAmt_4(Mockito.<String>any());
        doNothing().when(cashFlow).setAmt_5(Mockito.<String>any());
        doNothing().when(cashFlow).setAmt_6(Mockito.<String>any());
        doNothing().when(cashFlow).setYears(Mockito.<String>any());
        cashFlow.setAmt_1("Amt 1");
        cashFlow.setAmt_2("Amt 2");
        cashFlow.setAmt_3("Amt 3");
        cashFlow.setAmt_4("Amt 4");
        cashFlow.setAmt_5("Amt 5");
        cashFlow.setAmt_6("Amt 6");
        cashFlow.setYears("Years");
        when(excelReader.readCashFlow(Mockito.<String>any())).thenReturn(cashFlow);
        writeCashFlow.writeCashFlow(null);
    }

    @Test
    void testWriteHeaderParagraph() throws Exception {
        String wordFilePath = "testoutput2.docx";
        XWPFDocument document = new XWPFDocument();
        writeCashFlow.writeCashFlow(document);
        FileOutputStream outputStream = new FileOutputStream(wordFilePath);
        document.write(outputStream);
        outputStream.close();
    }

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

