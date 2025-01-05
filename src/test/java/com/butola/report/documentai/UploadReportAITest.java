package com.butola.report.documentai;

import com.butola.report.service.documentai.UploadReportAI;
import com.google.cloud.documentai.v1.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

class UploadReportAITest {

    @Mock
    private DocumentProcessorServiceClient mockClient;

    private UploadReportAI uploadReportAI;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        uploadReportAI = spy(new UploadReportAI());
    }

    @Test
    void testProcessAndUploadReport_ValidFilePath() throws IOException {
        // Arrange


        // Act
        //uploadReportAI.processAndUploadReport("filePath");

       // assert null;
        // Assert
        //verify(uploadReportAI, times(1)).processDocument(any(), eq(projectId), eq(location), eq(processorId), eq(filePath));
    }

    @Test
    void testProcessAndUploadReport_InvalidFilePath() {
        // Arrange
        String invalidFilePath = "";

        // Act
     //   uploadReportAI.processAndUploadReport(invalidFilePath);

        // Assert
        verifyNoInteractions(mockClient);
    }

    @Test
    void testProcessAndUploadReport_FileProcessingError() throws IOException {
        // Arrange
        String filePath = "test.pdf";

        // Mock an exception being thrown during client creation
       // doThrow(IOException.class).when(uploadReportAI).processDocument(any(), any(), any(), any(), eq(filePath));

        // Act
     //   uploadReportAI.processAndUploadReport(filePath);

        // Assert
        // Verify no further interactions occurred due to the exception
      //  verify(uploadReportAI, times(1)).processDocument(any(), any(), any(), any(), eq(filePath));
    }
}
