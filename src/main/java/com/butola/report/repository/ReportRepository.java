package com.butola.report.repository;

import com.butola.report.data.mongo.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {
    Report findByCompanyName(String companyName, Integer version, Integer year);
    public long count();
}
