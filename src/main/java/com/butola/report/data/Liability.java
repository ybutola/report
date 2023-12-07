package com.butola.report.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Liability {

    private String item;
    private Double previousYearValue;
    private Double currentYearValue;


    private double currentYearAccountsPayable;
    private double currentYearAccuredSalariesAndVacation;
    private double currentYearOtherLiabilites;
    private double previousYearAccountsPayable;
    private double previousYearAccuredSalariesAndVacation;
    private double previousYearOtherLiabilites;
}