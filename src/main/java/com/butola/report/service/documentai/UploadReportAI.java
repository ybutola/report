package com.butola.report.service.documentai;

import com.butola.report.data.mongo.CashFlow;
import com.butola.report.data.mongo.Liquidity;
import com.butola.report.data.mongo.Report;
import com.butola.report.service.mongodbreport.ReportService;
import com.google.cloud.documentai.v1.*;
import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UploadReportAI {

    @Autowired
    ReportService reportService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadReportAI.class);
    private static final String MIME_TYPE_PDF = "application/pdf";
    private static final Map<String, String> ENTITY_MAPPINGS;

    static {
        ENTITY_MAPPINGS = new HashMap<>();
        ENTITY_MAPPINGS.put("cashflow_accured-expenses", "setAccruedExpenses");
        ENTITY_MAPPINGS.put("cashflow_accounts-payable", "setAccountsPayable");
        ENTITY_MAPPINGS.put("cashflow_depreciation", "setDeclaration");
        ENTITY_MAPPINGS.put("cashflow_prepaid-expenses", "setPrepaidExpenses");
        ENTITY_MAPPINGS.put("cashflow_total-adjustments", "setTotalAdjustments");
        ENTITY_MAPPINGS.put("cashflow_loss-on-sale", "setLossOnSaleOfProperty");
        ENTITY_MAPPINGS.put("cashflow_grants", "setGrantsAndContractsReceivable");
        ENTITY_MAPPINGS.put("cashflow_operating-lease", "setNetChangeInOperatingLeaseActivity");
        ENTITY_MAPPINGS.put("liquidity_cash", "setCurrentYearCash");
        ENTITY_MAPPINGS.put("liquidity_fin-assets-available", "setFinancialAssetsAvailable");
        ENTITY_MAPPINGS.put("liquidity_grants", "setCurrentYearGrantsAndContractsReceivable");
        ENTITY_MAPPINGS.put("liquidity_net-assets-with-restrictions", "setNetAssetsWithRestrictions");
        ENTITY_MAPPINGS.put("liquidity_total-assets-donar-restrictions", "setTotalAssetsWithDonorRestrictions");
        ENTITY_MAPPINGS.put("liquidity_total-assets-not-available", "setTotalAssetsNotAvailable");
        ENTITY_MAPPINGS.put("liquidity_total-fin-assets", "setCurrentYearTotalFinancialAssets");
    }

    public void processAndUploadReport(String companyName, Integer year, Integer version, byte[] content) {
        String projectId = "209368501132";
        String location = "us";
        String processorId = "1841379655b6b948";

        ByteString contentString = ByteString.copyFrom(content);

 /*       String filePath = "src/main/resources/static/documents/RegularAudit_1.pdf";
        if (filePath == null || filePath.isBlank()) {
            LOGGER.error("File path cannot be null or empty.");
            return;
        }*/

        try (DocumentProcessorServiceClient client = DocumentProcessorServiceClient.create()) {
            processDocument(client, projectId, location, processorId, companyName, year, contentString);
        } catch (IOException e) {
            LOGGER.error("Error processing document: {}", e.getMessage(), e);
        }
    }

    private void processDocument(DocumentProcessorServiceClient client,
                                 String projectId,
                                 String location,
                                 String processorId,
                                 String companyName,
                                 Integer year,
                                 ByteString content) throws IOException {
        String processorName = String.format("projects/%s/locations/%s/processors/%s", projectId, location, processorId);
//        ByteString content = ByteString.readFrom(new FileInputStream(filePath));

        RawDocument rawDocument = RawDocument.newBuilder()
                .setContent(content)
                .setMimeType(MIME_TYPE_PDF)
                .build();

        ProcessRequest request = ProcessRequest.newBuilder()
                .setName(processorName)
                .setRawDocument(rawDocument)
                .build();

        ProcessResponse response = client.processDocument(request);
        Document document = response.getDocument();

        if (document.getEntitiesList().isEmpty()) {
            LOGGER.info("No entities found in the document.");
            return;
        }

        LOGGER.info("Extracting entities...");
        extractEntities(document, companyName, year);
    }

    private void extractEntities(Document document, String companyName, Integer year) {
        CashFlow cashFlow = buildCashFlow(document);
        Liquidity liquidity = buildLiquidity(document);

        Report report = new Report();
        report.setCompanyName(companyName);
        report.setYear(year);
        report.setCashFlow(cashFlow);
        report.setLiquidity(liquidity);

        uploadReport(report);
    }

    private CashFlow buildCashFlow(Document document) {
        CashFlow cashFlow = new CashFlow();

        document.getEntitiesList().stream()
                .filter(entity -> entity.getType().toLowerCase().startsWith("cashflow"))
                .forEach(entity -> {
                    String methodName = ENTITY_MAPPINGS.get(entity.getType());
                    try {
                        CashFlow.class.getMethod(methodName, String.class)
                                .invoke(cashFlow, entity.getMentionText());
                    } catch (ReflectiveOperationException e) {
                        LOGGER.error("Error setting value for CashFlow: {}", e.getMessage(), e);
                    }
                });

        return cashFlow;
    }

    private Liquidity buildLiquidity(Document document) {
        Liquidity liquidity = new Liquidity();

        document.getEntitiesList().stream()
                .filter(entity -> entity.getType().toLowerCase().startsWith("liquidity"))
                .forEach(entity -> {
                    String methodName = ENTITY_MAPPINGS.get(entity.getType());
                    try {
                        Liquidity.class.getMethod(methodName, String.class)
                                .invoke(liquidity, entity.getMentionText());
                    } catch (ReflectiveOperationException e) {
                        LOGGER.error("Error setting value for Liquidity: {}", e.getMessage(), e);
                    }
                });

        return liquidity;
    }

    private void uploadReport(Report report) {
        if (report == null) {
            LOGGER.error("Report is null. Upload skipped.");
            return;
        }

        reportService.createReport(report);
        LOGGER.info("Report uploaded successfully: {}", report);
    }
}
