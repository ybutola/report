package com.butola.report.service.worddoc;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class WriteHeader {
    public void writeHeaderParagraph(XWPFDocument document) {
        writeCompanyName(document);
        writeCompanyCityNState(document);
        writeCompanyAddress(document);
        writeCompanyLogo(document);
    }

    private void writeCompanyName(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(18);
        run.setText("Comunidades Organizando El Poder Y La Accion Latina  ");
    }

    private void writeCompanyCityNState(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        paragraph.setBorderBottom(Borders.SINGLE);

        XWPFRun run = paragraph.createRun();
        run.setFontSize(14);
        run.setText("Minneapolis, Minnesota");
        run.addBreak();
    }

    private void writeCompanyAddress(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);

        XWPFRun run = paragraph.createRun();
        run.setFontSize(14);
        run.setText("Combined Financial Statements");
        run.addBreak();
        run.setText("Auditor’s Report");
        run.addBreak();
        run.setText("For the Years Ended");
        run.addBreak();
        run.setText("December 31, 2022 and 2021");
    }

    private void writeCompanyLogo(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        paragraph.setVerticalAlignment(TextAlignment.BOTTOM);
        XWPFRun run = paragraph.createRun();
        try {
            String imagePath = "/static/images/cealogo.png";
            File image = new File(imagePath);
            FileInputStream imageData = new FileInputStream(image);

            int imageType = XWPFDocument.PICTURE_TYPE_PNG;
            String imageFileName = image.getName();
            int width = 450;
            int height = 400;
            run.addPicture(imageData, imageType, imageFileName, Units.toEMU(width), Units.toEMU(height));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InvalidFormatException ife) {
            ife.printStackTrace();
        } finally {
            System.out.println("Keep writing to the document");
        }
    }
}
