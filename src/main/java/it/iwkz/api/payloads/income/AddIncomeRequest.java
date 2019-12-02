package it.iwkz.api.payloads.income;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddIncomeRequest {
    @NotNull
    private Long incomeTypeId;

    @NotNull
    private Double amount;

    @NotNull
    private Integer month;

    @NotNull
    private Integer year;

    private String info;
}
