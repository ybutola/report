package com.butola.report.service.mongodbreport;

import com.butola.report.data.mongo.CashFlow;
import com.butola.report.data.mongo.Liquidity;
import com.butola.report.data.mongo.Report;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.bson.types.ObjectId;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportGeneratorService {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    ReportService reportService;

    public byte[] generatePreview(String companyName, int version, int year) {
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

        return readReplaceAndWrite(filepath, replacements);
    }

    private byte[] readReplaceAndWrite(String filepath, HashMap<String, Object> replacements) {
        try {
            XWPFDocument document = readDocument(filepath);
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
            //doc.write(new FileOutputStream("replaceContent_1.docx")); Use it if you want to write to a file.
            saveDocumentToMongoDB(outputStream.toByteArray());
            return outputStream.toByteArray();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    /**
     * @param content
     * @return In preview mode, no need to keep multiple versions of the same file.
     * Also no need to first check if a file exists.
     */
    public String saveDocumentToMongoDB(byte[] content) {
        String previewFileName = "butolaorg_1_2024_preview";
        gridFsTemplate.delete(new Query(Criteria.where("filename").is(previewFileName)));

        InputStream inputStream = new ByteArrayInputStream(content);
        GridFSUploadOptions options = new GridFSUploadOptions()
                .metadata(new org.bson.Document("type", "document").append("fileName", previewFileName));

        ObjectId fileId = gridFsTemplate.store(inputStream, previewFileName, "application/vnd.openxmlformats-officedocument.wordprocessingml.document", options);
        return fileId.toString();
    }
}
