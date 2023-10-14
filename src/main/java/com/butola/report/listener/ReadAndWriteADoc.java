package com.butola.report.listener;

import com.butola.report.data.Property;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReadAndWriteADoc {

    @Autowired
    ResourceLoader resourceLoader;

    public XWPFDocument reandNWrite(int pageNumber) {
        try {
            // Input Word document file path
            String inputFile = "files/RegularAudit.docx";

            // Output Word document file path
            String outputFile = "outputwonder.docx";

            Resource resource = resourceLoader.getResource("classpath:" + inputFile);
            XWPFDocument document = new XWPFDocument(resource.getInputStream());
            resource.getInputStream().close();

            FileOutputStream fos = new FileOutputStream(outputFile);
            XWPFDocument newDocument = new XWPFDocument();

            int currentPageNumber = 0;
            StringBuilder stringBuilder = new StringBuilder();
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                currentPageNumber++;
                //    if (pageNumber >= currentPageNumber && pageNumber <= currentPageNumber + 30) {
                XWPFParagraph newParagraph = newDocument.createParagraph();
                newParagraph.setAlignment(paragraph.getAlignment());
                newParagraph.setBorderBottom(paragraph.getBorderBottom());
                if (paragraph.getNumIlvl() != null) {
                    newParagraph.setNumILvl(paragraph.getNumIlvl());
                    newParagraph.setNumID(paragraph.getNumID());
                }

                System.out.println(paragraph.getParagraphText());
                for (XWPFRun sourceRun : paragraph.getRuns()) {
                    stringBuilder.append(getTabsAndText(sourceRun));
                    XWPFRun newRun = newParagraph.createRun();
                    newRun.setText(getTabsAndText(sourceRun));
                    newRun.setBold(sourceRun.isBold());
                    newRun.setItalic(sourceRun.isItalic());
                    newRun.setStrike(sourceRun.isStrike());
                    newRun.setFontSize(sourceRun.getFontSize());
                    newRun.setUnderline(sourceRun.getUnderline());
                    newRun.setColor(sourceRun.getColor());
                }

            }
            //    }
            // Save the new document

            // replace the properties with data.
            replacePropertyData(stringBuilder);
            // format  the content.
            formatPropertyData(stringBuilder);
            newDocument.write(fos);
            fos.close();
            System.out.println("Document copied and saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void replacePropertyData(StringBuilder stringBuilder) {
        Property property = getDummyObject();
        String propertyContent = stringBuilder.toString();
        propertyContent.replace("", "");
        // read property data from excel file
        // put that into an object
        // replace
        // do we really need to put data into an object?
    }

    private void formatPropertyData(StringBuilder stringBuilder) {

    }

    private Property getDummyObject() {
        Property property = new Property();
        property.setPropertyDate("December 31");
        property.setPropertyYear("2022");
        property.setPropertyPrYear("2021");
        property.setPropertyUsefulLive("5");
        property.setProperty_0_1("31,243");
        property.setProperty_0_2("11,172");
        property.setProperty_0_final("20,071");
        property.setProperty_1_1("25,303");
        property.setProperty_1_2("5,550");
        property.setProperty_1_final("19,753");
        return property;
    }

    private void readPropertyContent(String content) {
        private StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(content);
    }

    private String getTabsAndText(XWPFRun sourceRun) {
        List<CTEmpty> ctEmpties = sourceRun.getCTR().getTabList();

        String tabs = "";
        for (CTEmpty ctEmpty : ctEmpties) {
            tabs = tabs + "\t";
        }

        String runText = sourceRun.getText(0);

        if (runText == null) {
            return tabs;
        } else {
            return tabs + runText;
        }

    }

    private static boolean isBulletedList(XWPFParagraph paragraph) {
        if (paragraph.getNumIlvl() != null && paragraph.getNumID() != null) {
            return true;
        }
        return false;
    }
}

