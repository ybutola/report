package com.butola.report.repository;

import com.butola.report.data.mongo.Template;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends MongoRepository<Template, String>, TemplateRepositoryCustom {
    @Query(value = "{}", sort = "{'_id': 1}")
    Template findFirstByIdAsc();
}
