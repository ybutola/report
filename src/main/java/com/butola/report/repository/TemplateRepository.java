package com.butola.report.repository;

import com.butola.report.data.mongo.Report;
import com.butola.report.data.mongo.Template;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TemplateRepository extends MongoRepository<Template, String>, TemplateRepositoryCustom {


/*  @Query("{and: [{ companyName: { $regex: ?0, $options: 'i' } }, { year: ?1 }, { version: ?2 }, { createdBy: ?3 } ,{ updatedBy: ?4 } ,{ createdDate: ?5 } , { updatedDate: ?6 }] }")
  List<Template> searchTemplates(String companyName,
                                 Integer year,
                                 Integer version,
                                 String createdBy,
                                 String updatedBy,
                                 Date createdDate,
                                 Date updatedDate);*/
}
