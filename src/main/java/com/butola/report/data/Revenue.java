package com.butola.report.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Revenue {
    private String item;
    private Double previousYearValue;
    private Double currentYearValue;

}
