package com.butola.report.data.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Liquidity {
    private int currentYear;
    private long currentYearCash;
    private long currentYearGrantsAndContractsReceivable;
    private long currentYearTotalFinancialAssets;
    private long totalAssetsWithDonorRestrictions;
    private long netAssetsWithRestrictions;
    private long totalAssetsNotAvailable;
    private long financialAssetsAvailable;
}
