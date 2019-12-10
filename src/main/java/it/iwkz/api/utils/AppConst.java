package it.iwkz.api.utils;

import java.util.Calendar;

public interface AppConst {
    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "10";

    String MAX_PAGE_SIZE = "30000";

    int CURRENT_MONTH = Calendar.getInstance().get(Calendar.MONTH) + 1;
    int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);
}
