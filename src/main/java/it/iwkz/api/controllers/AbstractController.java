package it.iwkz.api.controllers;

import it.iwkz.api.payloads.FinanceWSResponse;
import it.iwkz.api.services.BillService;
import it.iwkz.api.services.IncomeService;
import it.iwkz.api.utils.AppConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class AbstractController {
    private static final String FINANCE_WS_URI = AppConst.WS_FINANCE_TOPIC;
    private static final Logger logger = LoggerFactory.getLogger(IncomeController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    protected BillService billService;

    @Autowired
    protected IncomeService incomeService;

    protected void sendFinanceDataToClient() {
        int month = AppConst.CURRENT_MONTH;
        int year = AppConst.CURRENT_YEAR;

        FinanceWSResponse financeWSResponse = new FinanceWSResponse();
        financeWSResponse.setIncomePercentageResponse(incomeService.calculateIncomePercentage(month, year));
        financeWSResponse.setTotalBillResponse(billService.getTotalBillByMonthYear(month, year));
        financeWSResponse.setTotalIncomeResponse(incomeService.getTotalIncomes(month, year));

        logger.info(financeWSResponse.toString());

        messagingTemplate.convertAndSend(FINANCE_WS_URI, financeWSResponse);
    }
}
