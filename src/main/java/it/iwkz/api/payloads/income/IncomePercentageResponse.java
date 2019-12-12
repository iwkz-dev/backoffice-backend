package it.iwkz.api.payloads.income;

import lombok.Data;

@Data
public class IncomePercentageResponse {
    double totalIncome;
    double totalBill;
    double incomePercentage;
}
