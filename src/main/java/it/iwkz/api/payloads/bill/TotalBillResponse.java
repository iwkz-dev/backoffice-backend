package it.iwkz.api.payloads.bill;

import lombok.Data;

import java.util.Map;

@Data
public class TotalBillResponse {
    private int month;
    private int year;
    private double totalBills;
    private Map<String, Double> totalBillByTypes;
}
