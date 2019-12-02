package it.iwkz.api.payloads.income;

import lombok.Data;

import java.util.Map;

@Data
public class TotalIncomeResponse {
    private int month;
    private int year;
    private double totalIncomes;
    private Map<String, Double> totalIncomeByTypes;
}
