package com.butola.report.service.mongodbreport;

import com.butola.report.data.mongo.CashFlow;
import com.butola.report.data.mongo.Liquidity;
import com.butola.report.data.mongo.Report;
import com.butola.report.data.mongo.mapping.CashflowMappingConstants;
import com.butola.report.data.mongo.mapping.LiquidityMappingConstants;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
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

    @Autowired
    GenerateLiquidity generateLiquidity;

    @Autowired
    GenerateCashflow generateCashflow;

    public byte[] generatePreview(String templateName, String companyName, int version, int year) {
/*        HashMap<String, Object> replacements = new HashMap<>();
        Report report = reportService.getReport(companyName, version, year);


        Liquidity liquidity = reports[0].getLiquidity();
        replacements.put(LiquidityMappingConstants.currentYear, liquidity.getCurrentYear());
        replacements.put(LiquidityMappingConstants.currentYearCash, liquidity.getCurrentYearCash());
        replacements.put(LiquidityMappingConstants.currentYearGrantsAndContractsReceivable, liquidity.getCurrentYearGrantsAndContractsReceivable());
        replacements.put(LiquidityMappingConstants.currentYearTotalFinancialAssets, liquidity.getCurrentYearTotalFinancialAssets());
        replacements.put(LiquidityMappingConstants.totalAssetsWithDonorRestrictions, liquidity.getTotalAssetsWithDonorRestrictions());
        replacements.put(LiquidityMappingConstants.netAssetsWithRestrictions, liquidity.getNetAssetsWithRestrictions());
        replacements.put(LiquidityMappingConstants.totalAssetsNotAvailable, liquidity.getTotalAssetsNotAvailable());
        replacements.put(LiquidityMappingConstants.getFinancialAssetsAvailable, liquidity.getFinancialAssetsAvailable());

        CashFlow cashFlow = reports[0].getCashFlow();
        replacements.put(CashflowMappingConstants.declaration, cashFlow.getDeclaration());
        replacements.put(CashflowMappingConstants.netChangeInOperatingLeaseActivity, cashFlow.getNetChangeInOperatingLeaseActivity());
        replacements.put(CashflowMappingConstants.lossOnSaleOfProperty, cashFlow.getLossOnSaleOfProperty());
        replacements.put(CashflowMappingConstants.amountsPayable, cashFlow.getAccountsPayable());
        replacements.put(CashflowMappingConstants.accruedExpenses, cashFlow.getAccruedExpenses());
        replacements.put(CashflowMappingConstants.grantsAndContractsReceivable, cashFlow.getGrantsAndContractsReceivable());
        replacements.put(CashflowMappingConstants.prepaidExpenses, cashFlow.getPrepaidExpenses());
        replacements.put(CashflowMappingConstants.totalAdjustments, cashFlow.getTotalAdjustments());*/

        //return readReplaceAndWrite(templateName, replacements);
        //return createDocument(companyName, version, year);

        Report[] reports = reportService.getReports(companyName, version, year);
        return createDocument(reports);
    }

    private byte[] createDocument(Report[] reports) {
        XWPFDocument document = new XWPFDocument();
        generateLiquidity.createLiquidity(document, reports[0], reports[1]);
        generateCashflow.createCashflow(document, reports[0], reports[1]);
        return createDocWithReplacedContent(document);
    }
    private byte[] createDocument(String companyName, int version, int year) {
        XWPFDocument document = new XWPFDocument();
        Report currentYearReport = reportService.getReport(companyName, version, year);
        Report previousYearReport = reportService.getReport(companyName, version, year-1);
        generateLiquidity.createLiquidity(document, currentYearReport, previousYearReport);
        generateCashflow.createCashflow(document, currentYearReport, previousYearReport);
        return createDocWithReplacedContent(document);
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

    /**************************************************************************************************************************************/

    private void replaceText(XWPFParagraph paragraph, String text, HashMap<String, Object> replacements, XWPFRun run, int pos) {
        if (text != null) {
            for (Map.Entry<String, Object> entry : replacements.entrySet()) {
                if (text.contains(entry.getKey())) {
                    text = text.replace(entry.getKey(), entry.getValue().toString());
                    run.setText(text, pos);
                    break;
                }
            }
        }
    }

    private byte[] readReplaceAndWrite(String templateName, HashMap<String, Object> replacements) {
        try {
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
                    replaceText(paragraph, text, replacements, run, 0);
                } else {
                    String text = "";
                    for (int i = 0; i < size; i++) {
                        text = run.getText(i);
                        replaceText(paragraph, text, replacements, run, i);
                    }
                }
            }
        }
        return createDocWithReplacedContent(doc);
    }
    private static int getRunSize(XWPFRun run) {
        CTR ctr = run.getCTR();
        return ctr.sizeOfTArray();
    }
}
