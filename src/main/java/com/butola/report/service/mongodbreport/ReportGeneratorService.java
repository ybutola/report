package com.butola.report.service.mongodbreport;

import com.butola.report.data.mongo.CashFlow;
import com.butola.report.data.mongo.Liquidity;
import com.butola.report.data.mongo.Report;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportGeneratorService {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    GridFsFileService gridFsFileService;
    @Autowired
    ReportService reportService;

    public byte[] generatePreview(String templateName, String companyName, int version, int year) {
        String filepath = "RegularAudit_Template.docx";
        HashMap<String, Object> replacements = new HashMap<>();
        Report report = reportService.getReport(companyName, version, year);

        Liquidity liquidity = report.getLiquidity();
        replacements.put("{cy}", liquidity.getCurrentYear());
        replacements.put("{cyc}", liquidity.getCurrentYearCash());
        replacements.put("{cygacr}", liquidity.getCurrentYearGrantsAndContractsReceivable());
        replacements.put("{cytfa}", liquidity.getCurrentYearTotalFinancialAssets());
        replacements.put("{tawdr}", liquidity.getTotalAssetsWithDonorRestrictions());
        replacements.put("{nawr}", liquidity.getNetAssetsWithRestrictions());
        replacements.put("{tana}", liquidity.getTotalAssetsNotAvailable());
        replacements.put("{faa}", liquidity.getFinancialAssetsAvailable());

        CashFlow cashFlow = report.getCashFlow();
        replacements.put("{dec}", cashFlow.getDeclaration());
        replacements.put("{nciola}", cashFlow.getNetChangeInOperatingLeaseActivity());
        replacements.put("{losop}", cashFlow.getLossOnSaleOfProperty());
        replacements.put("{ap}", cashFlow.getAmountsPayable());
        replacements.put("{acexp}", cashFlow.getAccruedExpenses());
        replacements.put("{gacr}", cashFlow.getGrantsAndContractsReceivable());
        replacements.put("{pe}", cashFlow.getPrepaidExpenses());
        replacements.put("{ta}", cashFlow.getTotalAdjustments());

        return readReplaceAndWrite(filepath, templateName, replacements);
    }

    private byte[] readReplaceAndWrite(String filepath, String templateName, HashMap<String, Object> replacements) {
        try {
            // XWPFDocument document = readDocument(filepath);
            XWPFDocument document = readTemplateDocument(templateName);
            return replaceText(document, replacements);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
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

    private XWPFDocument readTemplateDocument(String templateName) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = gridFsFileService.findDocument(templateName);
            XWPFDocument doc = new XWPFDocument(inputStream);
            return doc;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            inputStream.close();
        }
        return null;
    }

    private byte[] replaceText(XWPFDocument doc, HashMap<String, Object> replacements) {
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

        return createDocWithReplacedContent(doc);
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

    private byte[] createDocWithReplacedContent(XWPFDocument doc) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            doc.write(outputStream);
            String previewFileName = "butolaorg_1_2024_preview";
            byte[] content = outputStream.toByteArray();
            gridFsFileService.replaceDocument(previewFileName, content);
            return content;
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }
}
