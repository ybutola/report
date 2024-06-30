package com.butola.report.data.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class Template {
    @Id
    private String id;
    @Indexed
    private String companyName;
    @Indexed
    private int year;
    @Indexed
    private int version;
    private String createdBy;
    private String updatedBy;
    private Date createdDate;
    private Date updatedDate;
    private String fileName;
}
