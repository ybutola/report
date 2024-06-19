package com.butola.report.data.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
        @CompoundIndex(name = "companyName_version_year_idx", def = "{'companyName': 1, 'version': 1, , 'year': 1}", unique = true)
})
@Data
public class Report {
    @Id
    private String id;
    private String companyName;
    private int year;
    private int version = 1;
    private Liquidity liquidity;
    private CashFlow cashFlow;
    private Audit audit;
}
