package com.butola.report.service.documentai;

import com.butola.report.data.mongo.Report;
import com.butola.report.service.mongodbreport.ReportService;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.api.GenerationConfig;
import com.google.cloud.vertexai.api.HarmCategory;
import com.google.cloud.vertexai.api.SafetySetting;
import com.google.cloud.vertexai.generativeai.ContentMaker;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.PartMaker;
import com.google.cloud.vertexai.generativeai.ResponseStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class FetchDataFromReport {
    private static final Logger logger = LoggerFactory.getLogger(FetchDataFromReport.class);
    private static final String MODEL_NAME = "gemini-2.0-flash-exp";
    private static final String PROJECT_ID = "stable-smithy-270416";
    private static final String LOCATION = "us-central1";
    private static final String MIME_TYPE = "application/pdf";

    @Autowired
    ReportService reportService;

    @Autowired
    ReportTransformer reportTransformer;

    public void getReportData(byte[] pdfContent) {
        logger.info("Inside service method. ");
        try (VertexAI vertexAi = new VertexAI(PROJECT_ID, LOCATION);) {
            GenerationConfig generationConfig = getGenerationConfig();
            List<SafetySetting> safetySettings = getSafetySettings();
            GenerativeModel model = getGenerativeModel(vertexAi, generationConfig, safetySettings);


            try {
                logger.info("Reading the pdf file. ");
                var scoreSheet = PartMaker.fromMimeTypeAndData(MIME_TYPE, pdfContent);

                if (pdfContent.length > 0) {
                    var prompt = getThePrompt();
                    var content = ContentMaker.fromMultiModalData(scoreSheet, prompt);
                    logger.info("Requesting Gemini Model to process the request.");
                    ResponseStream<GenerateContentResponse> responseStream = model.generateContentStream(content);
                    String responseMsg = processTheResponse(responseStream);
                    logger.info("AI Response:" + responseMsg);
                    Report report = reportTransformer.transform(responseMsg);
                    uploadReport(report);
                } else {
                    logger.error("Please submit a valid pdf file.");
                }
            } catch (IOException ioe) {
                //  writer.printf("The file is empty, please submit a valid pdf file.");
                logger.error(ioe.toString());
            } catch (Exception e) {
                logger.error("There is something wrong. Please try again. Contact yogibutola@icloud.com, if the issue persists.");
                logger.error(e.toString());
            }
        }
    }

    private GenerationConfig getGenerationConfig() {
        GenerationConfig generationConfig = GenerationConfig.newBuilder()
                .setMaxOutputTokens(1568)
                .setTemperature(0F)
                .setTopP(0.95F)
                .build();
        return generationConfig;
    }

    private List<SafetySetting> getSafetySettings() {
        List<SafetySetting> safetySettings = Arrays.asList(SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_HATE_SPEECH)
                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_NONE)
                        .build(),

                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT)
                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_NONE)
                        .build(),

                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT)
                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_NONE)
                        .build(), SafetySetting.newBuilder().setCategory(HarmCategory.HARM_CATEGORY_HARASSMENT)
                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_NONE)
                        .build());

        return safetySettings;
    }

    private GenerativeModel getGenerativeModel(VertexAI vertexAi, GenerationConfig generationConfig, List<SafetySetting> safetySettings) {
        GenerativeModel generativeModel = new GenerativeModel(MODEL_NAME, generationConfig, vertexAi);
        return generativeModel;
    }

    private String getThePrompt() {
        var prompt = "Give me the following information in the json format:\n" +
                "1. Property \n" +
                "2. Contribution of non financial assets.\n" +
                "3. Net assets with dinar restrictions\n" +
                "4. Board Designated Net Assets\n" +
                "5. Lease\n" +
                "6. liquidity and Availability\n" +
                "7. Cashflow";

        return prompt;
    }

    private String processTheResponse(ResponseStream<GenerateContentResponse> responseStream) {
        logger.info("Processing the response.");
        StringBuilder responseBuilder = new StringBuilder();
        responseStream.stream().forEach(msg -> {
                    responseBuilder.append(msg.getCandidates(0).getContent().getPartsList().get(0).getText());
                }
        );

        String responseMsg = responseBuilder.toString();
        responseMsg = responseMsg.replace("```json", "");
        responseMsg = responseMsg.replace("```", "");

        return responseMsg;
    }

    private void uploadReport(Report report) {
        if (report == null) {
            logger.error("Report is null. Upload skipped.");
            return;
        }
        reportService.createReport(report);
        logger.info("Report uploaded successfully: {}", report);
    }
}
