package it.iwkz.api.payloads.income;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddIncomeTypeRequest {
    @NotNull
    private String name;

    private String description;
}
