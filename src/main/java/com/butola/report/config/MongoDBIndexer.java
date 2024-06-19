package com.butola.report.config;

import com.butola.report.data.mongo.Report;
import com.butola.report.data.mongo.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;
import org.springframework.stereotype.Component;
import org.bson.Document;

@Component
public class MongoDBIndexer {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public CommandLineRunner createIndexes() {
        return args -> {
            mongoTemplate.indexOps(Report.class).ensureIndex(
                    new CompoundIndexDefinition(new Document("companyName", 1).append("version", 1).append("year", 1)).unique()
            );

            mongoTemplate.indexOps(Template.class).ensureIndex(
                    new CompoundIndexDefinition(new Document("companyName", 1).append("version", 1).append("year", 1)).unique()
            );
        };
    }
}
