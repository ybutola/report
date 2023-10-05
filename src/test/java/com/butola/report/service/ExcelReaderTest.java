package com.butola.report.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.butola.report.data.CashFlow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ExcelReader.class})
@ExtendWith(SpringExtension.class)
class ExcelReaderTest {
    @Autowired
    private ExcelReader excelReader;

    /**
     * Method under test: {@link ExcelReader#readExcelFile(String)}
     */
    @Test
    void testReadExcelFile() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     FinacialReport.today

        ExcelReader excelReader = new ExcelReader();
        excelReader.readExcelFile("/directory/foo.txt");
        CashFlow readCashFlowResult = excelReader.readCashFlow("/directory/foo.txt");
        assertEquals("31,243", readCashFlowResult.getAmt_1());
        assertEquals("5 years", readCashFlowResult.getYears());
        assertEquals("19,753", readCashFlowResult.getAmt_6());
        assertEquals("20,071", readCashFlowResult.getAmt_5());
        assertEquals("5,550", readCashFlowResult.getAmt_4());
        assertEquals("11,172", readCashFlowResult.getAmt_3());
        assertEquals("25,303", readCashFlowResult.getAmt_2());
    }

    /**
     * Method under test: {@link ExcelReader#readCashFlow(String)}
     */
    @Test
    void testReadCashFlow() {
        CashFlow actualReadCashFlowResult = excelReader.readCashFlow("/Users/yogenderbutola/work/commercial/report/src/test/test.xlsx");
        assertEquals("31,243", actualReadCashFlowResult.getAmt_1());
        assertEquals("5 years", actualReadCashFlowResult.getYears());
        assertEquals("19,753", actualReadCashFlowResult.getAmt_6());
        assertEquals("20,071", actualReadCashFlowResult.getAmt_5());
        assertEquals("5,550", actualReadCashFlowResult.getAmt_4());
        assertEquals("11,172", actualReadCashFlowResult.getAmt_3());
        assertEquals("25,303", actualReadCashFlowResult.getAmt_2());
    }
}

