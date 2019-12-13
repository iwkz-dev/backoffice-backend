package it.iwkz.api.utils;

import java.util.Calendar;

public interface AppConst {
    // pagination
    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "10";
    String MAX_PAGE_SIZE = "30000";

    // current date
    int CURRENT_MONTH = Calendar.getInstance().get(Calendar.MONTH) + 1;
    int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);

    // websocket
    String WS_ENDPOINT = "/ws";
    String WS_BROKER_PREFIX = "/topic";
    String WS_FINANCE_TOPIC = "/topic/finance";
}
