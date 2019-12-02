package it.iwkz.api.payloads.income;

import it.iwkz.api.models.IncomeType;
import lombok.Data;

@Data
public class TotalIncomeByType {
    private double totalAmount;
    private IncomeType incomeType;
}
