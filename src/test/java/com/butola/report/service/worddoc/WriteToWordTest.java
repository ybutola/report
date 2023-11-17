package com.butola.report.service.worddoc;

import java.io.IOException;

import com.butola.report.service.ExcelReader;
import com.butola.report.service.worddoc.WriteHeader;
import com.butola.report.service.worddoc.WriteToWord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WriteToWord.class})
@ExtendWith(SpringExtension.class)
class WriteToWordTest {

    @MockBean
    private ExcelReader excelReader;

    @MockBean
    private WriteCashFlow writeCashFlow;

    @MockBean
    private WriteHeader writeHeader;

    @Autowired
    private WriteToWord writeToWord;

    @Test
    void testWriteToWord() throws IOException {
        writeToWord.writeToWord();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
}