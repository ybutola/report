package com.butola.report.data;

import lombok.Data;

import java.util.Date;

@Data
public class SearchTemplateData {

    private String companyName;
    private int year;
    private int version;
    private String createdBy;
    private String updatedBy;
    private Date createdDate;
    private Date updatedDate;
}
