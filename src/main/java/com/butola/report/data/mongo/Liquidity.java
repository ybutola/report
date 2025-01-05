package com.butola.report.data.mongo;

import lombok.Data;

@Data
public class Liquidity {
    private int currentYear;
    private String currentYearCash;
    private String currentYearGrantsAndContractsReceivable;
    private String currentYearTotalFinancialAssets;
    private String totalAssetsWithDonorRestrictions;
    private String netAssetsWithRestrictions;
    private String totalAssetsNotAvailable;
    private String financialAssetsAvailable;

    @Override
    public String toString() {
        return "Liquidity{" +
                "currentYear=" + currentYear +
                ", currentYearCash='" + currentYearCash + '\'' +
                ", currentYearGrantsAndContractsReceivable='" + currentYearGrantsAndContractsReceivable + '\'' +
                ", currentYearTotalFinancialAssets='" + currentYearTotalFinancialAssets + '\'' +
                ", totalAssetsWithDonorRestrictions='" + totalAssetsWithDonorRestrictions + '\'' +
                ", netAssetsWithRestrictions='" + netAssetsWithRestrictions + '\'' +
                ", totalAssetsNotAvailable='" + totalAssetsNotAvailable + '\'' +
                ", financialAssetsAvailable='" + financialAssetsAvailable + '\'' +
                '}';
    }
}
