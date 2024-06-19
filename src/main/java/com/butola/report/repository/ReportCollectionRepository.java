package com.butola.report.repository;

import com.butola.report.data.mongo.Report;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportCollectionRepository {

    @Autowired
    MongoClient mongoClient;

    public MongoCollection<Report> getCollection() {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(pojoCodecProvider));

        MongoDatabase reportDatabase = mongoClient.getDatabase("accountsreport").withCodecRegistry(pojoCodecRegistry);
        MongoCollection<Report> reportMongoCollection = reportDatabase.getCollection("report", Report.class);
        return reportMongoCollection;
    }

    public void saveReportCollection(Report report) {
        getCollection().insertOne(report);
    }

    public void updateReportCollection(Report report) {
        getCollection().find(Filters.eq("companyName", "butolaorg"));
    }
}
