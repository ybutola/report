package com.butola.report.data.mongo;

import lombok.Data;

@Data
public class CashFlow {
    private String declaration;
    private String netChangeInOperatingLeaseActivity;
    private String lossOnSaleOfProperty;
    private String accountsPayable;
    private String accruedExpenses;
    private String grantsAndContractsReceivable;
    private String prepaidExpenses;
    private String totalAdjustments;

    @Override
    public String toString() {
        return "CashFlow{" +
                "declaration='" + declaration + '\'' +
                ", netChangeInOperatingLeaseActivity='" + netChangeInOperatingLeaseActivity + '\'' +
                ", lossOnSaleOfProperty='" + lossOnSaleOfProperty + '\'' +
                ", accountsPayable='" + accountsPayable + '\'' +
                ", accruedExpenses='" + accruedExpenses + '\'' +
                ", grantsAndContractsReceivable='" + grantsAndContractsReceivable + '\'' +
                ", prepaidExpenses='" + prepaidExpenses + '\'' +
                ", totalAdjustments='" + totalAdjustments + '\'' +
                '}';
    }
}
