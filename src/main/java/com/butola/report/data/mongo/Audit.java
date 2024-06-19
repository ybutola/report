package com.butola.report.data.mongo;

import lombok.Data;

import java.util.Date;

@Data
public class Audit {
    private Date createdDateTime;
    private Date updatedDateTime;
    private String createdBy;
    private String updatedBy;
}
