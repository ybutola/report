package com.butola.report.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Asset {

    private String item;
    private Double previousYearValue;
    private Double currentYearValue;


    private double currentYearCashAndCashEquivalents;
    private double currentYearAccountReceivable;
    private double currentYearPrepaidExpenses;
    private double currentYearCLongTermROU;
    private double currentYearCLongFurnitureAndEquipment;
    private double previousYearCashAndCashEquivalents;
    private double previousYearAccountReceivable;
    private double previousYearPrepaidExpenses;
    private double previousYearCLongTermROU;
    private double previousYearCLongFurnitureAndEquipment;


    private double totalCurrentYearAssets;
    private double totalPreviousYearAssets;
}
