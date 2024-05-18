package com.butola.report.data.mongo;

import lombok.Data;

@Data
public class CashFlow {

    public long declaration;
    public long netChangeInOperatingLeaseActivity;
    public long lossOnSaleOfProperty;
    public long amountsPayable;
    public long accruedExpenses;
    public long grantsAndContractsReceivable;
    public long prepaidExpenses;
    public long totalAdjustments;

}
