package it.iwkz.api.payloads.bill;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddBillTypeRequest {
    @NotNull
    private String name;

    private String description;
}
