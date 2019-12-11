package it.iwkz.api.payloads.bill;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddBillRequest {
    @NotNull
    private Long billTypeId;

    @NotNull
    private Double amount;

    private Integer month;

    private Integer year;

    private String info;
}
