package com.butola.report.data;

import lombok.Data;

@Data
public class Liquidity {
    public long currentYear;
    public long currentYearCash;
    public long currentYearGrantsAndContractsReceivable;
    public long currentYearTotalFinancialAssets;
    public long totalAssetsWithDonorRestrictions;
    public long netAssetsWithRestrictions;
    public long totalAssetsNotAvailable;
    public long financialAssetsAvailable;
}
