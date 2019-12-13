package it.iwkz.api.payloads;

import it.iwkz.api.payloads.bill.TotalBillResponse;
import it.iwkz.api.payloads.income.IncomePercentageResponse;
import it.iwkz.api.payloads.income.TotalIncomeResponse;
import lombok.Data;

@Data
public class FinanceWSResponse {
    private IncomePercentageResponse incomePercentageResponse;
    private TotalIncomeResponse totalIncomeResponse;
    private TotalBillResponse totalBillResponse;
}
