package com.butola.report.service.worddoc;

import com.butola.report.service.ExcelReader;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class WriteToWord {
    @Autowired
    private ExcelReader excelReader;

    @Autowired
    WriteCashFlow writeCashFlow;
    @Autowired
    WriteHeader writeHeader;
    String excelFilePath = "input.xlsx"; // Provide the path to your Excel file
    String wordFilePath = "output.docx"; // Provide the path for the output Word document

    public void writeToWord() throws IOException {
        XWPFDocument document = new XWPFDocument();
        writeHeader.writeHeaderParagraph(document);
        writeCashFlow.writeCashFlow(document);
        FileOutputStream outputStream = new FileOutputStream(wordFilePath);
        document.write(outputStream);
        outputStream.close();
    }


    private void addNewPage(XWPFRun run) {
        run.addBreak(BreakType.PAGE);
        run.setText("INDEPENDENT AUDITOR'S REPORT");
        run.addBreak();
        run.setText("Board of Directors");
        run.addBreak();
        run.setText("Comunidades Organizando El Poder Y La Accion Latina");
        run.addBreak();
        run.setText("Minneapolis, Minnesota");
        run.addBreak();

    }


    private static void addParagraphWithAlignment(XWPFDocument document, String text, ParagraphAlignment alignment) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(alignment);

        XWPFRun run = paragraph.createRun();
        run.setText(text);
    }
}
