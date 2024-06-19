package com.butola.report.repository;

import com.butola.report.data.mongo.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TemplateRepositoryCustomImpl implements TemplateRepositoryCustom {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Template> searchTemplates(String companyName,
                                          Integer year,
                                          Integer version,
                                          String createdBy,
                                          String updatedBy,
                                          Date createdDate,
                                          Date updatedDate) {

        Query query = new Query();
        List<Criteria> criteria = new ArrayList<>();

        if (companyName != null && !companyName.isEmpty()) {
            criteria.add(Criteria.where("companyName").regex(companyName, "i"));
        }
        if (year != null && year != 0) {
            criteria.add(Criteria.where("year").is(year));
        }
        if (version != null && version != 0) {
            criteria.add(Criteria.where("version").is(version));
        }
        if (createdBy != null && !createdBy.isEmpty()) {
            criteria.add(Criteria.where("createdBy").regex(createdBy, "i"));
        }
        if (updatedBy != null && !updatedBy.isEmpty()) {
            criteria.add(Criteria.where("updatedBy").regex(updatedBy, "i"));
        }
        if (createdDate != null) {
            criteria.add(Criteria.where("createdDate").is(createdDate));
        }
        if (updatedDate != null) {
            criteria.add(Criteria.where("updatedDate").is(updatedDate));
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        return mongoTemplate.find(query, Template.class);
    }
}
