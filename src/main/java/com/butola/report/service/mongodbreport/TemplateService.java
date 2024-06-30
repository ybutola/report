package com.butola.report.service.mongodbreport;

import com.butola.report.data.SearchTemplateData;
import com.butola.report.data.mongo.Template;
import com.butola.report.exceptions.FileReadException;
import com.butola.report.exceptions.TemplateAlreadyExistsException;
import com.butola.report.repository.TemplateRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TemplateService {

    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    GridFsFileService gridFsFileService;

    public void saveTemplate(String companyName, Integer year, Integer version, byte[] content) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

        Template template = new Template();
        template.setCompanyName(companyName);
        template.setYear(year);
        template.setVersion(version);
        template.setCreatedBy("ybutola");

        String fileName = companyName + "_" + year + "_" + version;
        template.setFileName(fileName);
        try {
            template.setCreatedDate(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
            templateRepository.save(template);
            gridFsFileService.saveDocument(fileName, content);
        } catch (DuplicateKeyException dke) {
            throw new TemplateAlreadyExistsException(companyName, year, version);
        } catch (ParseException pex) {
            pex.printStackTrace();
            ;
            //Log the error.
        }
    }

    public List<Template> findTemplates(SearchTemplateData data) {
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

    public byte[] findTemplate(String companyName,
                               Integer version,
                               Integer year) {
        String fileName = companyName + "_" + year + "_" + version;
        try {
            InputStream inputStream = gridFsFileService.findDocument(fileName);
            return IOUtils.toByteArray(inputStream);
        } catch (IOException ioex) {
            // Log the error.
            throw new FileReadException(fileName);
        }
    }
}
