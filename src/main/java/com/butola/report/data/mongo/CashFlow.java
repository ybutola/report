package com.butola.report.data.mongo;

import lombok.Data;

@Data
public class CashFlow {

    private long declaration;
    private long netChangeInOperatingLeaseActivity;
    private long lossOnSaleOfProperty;
    private long amountsPayable;
    private long accruedExpenses;
    private long grantsAndContractsReceivable;
    private long prepaidExpenses;
    private long totalAdjustments;

}
