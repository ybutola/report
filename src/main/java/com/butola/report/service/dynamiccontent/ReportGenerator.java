package com.butola.report.service.dynamiccontent;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportGenerator {

    @Autowired
    ResourceLoader resourceLoader;

    public void readReplaceAndWrite(String filepath, HashMap<String, Object> replacements) {
        try {
            XWPFDocument document = readDocument(filepath);
            replaceText(document, replacements);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private XWPFDocument readDocument(String filePath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filePath);

        if (resource.exists()) {
            InputStream inputStream = null;
            try {
                inputStream = resource.getInputStream();
                XWPFDocument doc = new XWPFDocument(inputStream);
                return doc;
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                inputStream.close();
            }
        }
        return null;
    }

    private void replaceText(XWPFDocument doc, HashMap<String, Object> replacements) {
        for (XWPFParagraph paragraph : doc.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                int size = getRunSize(run);

                if (size == 1) {
                    String text = run.getText(0);
                    replaceText(text, replacements, run, 0);
                } else {
                    String text = "";
                    for (int i = 0; i < size; i++) {
                        text = run.getText(i);
                        replaceText(text, replacements, run, i);
                    }
                }
            }
        }
        createDocWithReplacedContent(doc);
    }

    private void replaceText(String text, HashMap<String, Object> replacements, XWPFRun run, int pos) {
        if (text != null) {
            for (Map.Entry<String, Object> entry : replacements.entrySet()) {
                if (text.contains(entry.getKey())) {
                    text = text.replace(entry.getKey(), entry.getValue().toString());
                    break;
                }
            }
            run.setText(text, pos);
        }
    }

    private static int getRunSize(XWPFRun run) {
        CTR ctr = run.getCTR();
        return ctr.sizeOfTArray();
    }
    private void createDocWithReplacedContent(XWPFDocument doc) {
        try {
            doc.write(new FileOutputStream("replaceContent_1.docx"));
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
