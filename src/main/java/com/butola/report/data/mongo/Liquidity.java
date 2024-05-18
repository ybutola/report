package com.butola.report.data.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Liquidity {
    public int currentYear;
    public long currentYearCash;
    public long currentYearGrantsAndContractsReceivable;
    public long currentYearTotalFinancialAssets;
    public long totalAssetsWithDonorRestrictions;
    public long netAssetsWithRestrictions;
    public long totalAssetsNotAvailable;
    public long financialAssetsAvailable;
}
