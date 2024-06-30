package com.butola.report.service.mongodbreport;

import com.butola.report.exceptions.FileNotFoundException;
import com.butola.report.exceptions.FileReadException;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class GridFsFileService {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations gridFsOperations;

    public InputStream findDocument(String fileName) {

        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("filename").is(fileName)));
        if (gridFSFile != null) {
            try {
                InputStream inputStream = gridFsOperations.getResource(gridFSFile).getInputStream();
                return inputStream;
            } catch (IOException ioex) {
                // Log the error.
                throw new FileReadException(fileName);
            }
        } else {
            throw new FileNotFoundException(fileName);
        }
    }

    public ObjectId saveDocument(String fileName, byte[] content) {
        InputStream inputStream = new ByteArrayInputStream(content);
        GridFSUploadOptions options = new GridFSUploadOptions()
                .metadata(new org.bson.Document("type", "document").append("fileName", fileName));

        ObjectId fileId = gridFsTemplate.store(inputStream, fileName, "application/vnd.openxmlformats-officedocument.wordprocessingml.document", options);
        return fileId;
    }

    public String replaceDocument(String previewFileName, byte[] content) {
        gridFsTemplate.delete(new Query(Criteria.where("filename").is(previewFileName)));
        ObjectId fileId = saveDocument(previewFileName, content);
        return fileId.toString();
    }
}
