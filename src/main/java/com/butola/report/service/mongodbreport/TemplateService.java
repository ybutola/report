package com.butola.report.service.mongodbreport;

import com.butola.report.data.SearchTemplateData;
import com.butola.report.data.mongo.Template;
import com.butola.report.exceptions.TemplateAlreadyExistsException;
import com.butola.report.repository.TemplateRepository;

import com.butola.report.repository.TemplateRepositoryCustom;
import com.mongodb.MongoWriteException;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.DuplicateFormatFlagsException;
import java.util.List;

@Service
public class TemplateService {

    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    private GridFsTemplate gridFsTemplate;

    public void saveTemplate(String companyName, Integer year, Integer version, byte[] content) {
        Template template = new Template();
        template.setCompanyName(companyName);
        template.setYear(year);
        template.setVersion(version);
        template.setCreatedBy("ybutola");
        template.setCreatedDate(new Date());
        String fileName = companyName + "_" + year + "_" + version;
        template.setFileName(fileName);
        try {
            templateRepository.save(template);
            saveFile(fileName, content);
        } catch (DuplicateKeyException dke) {
            throw new TemplateAlreadyExistsException(companyName, year, version);
        }
    }

    private boolean saveFile(String fileName, byte[] content) {
        InputStream inputStream = new ByteArrayInputStream(content);
        GridFSUploadOptions options = new GridFSUploadOptions()
                .metadata(new org.bson.Document("type", "document").append("fileName", fileName));

        ObjectId fileId = gridFsTemplate.store(inputStream, fileName, "application/vnd.openxmlformats-officedocument.wordprocessingml.document", options);
        return true;
    }

    public List<Template> findTemplate(SearchTemplateData data) {
        return templateRepository.searchTemplates(
                data.getCompanyName(),
                data.getYear(),
                data.getVersion(),
                data.getCreatedBy(),
                data.getUpdatedBy(),
                data.getCreatedDate(),
                data.getUpdatedDate()
        );
    }
}
