package com.butola.report.repository;

import com.butola.report.data.mongo.Template;

import java.util.Date;
import java.util.List;

public interface TemplateRepositoryCustom {
    List<Template> searchTemplates(String companyName,
                                   Integer year,
                                   Integer version,
                                   String createdBy,
                                   String updatedBy,
                                   Date createdDate,
                                   Date updatedDate);
}
