package com.wlwoon.workbook;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by wxy on 2021/6/8.
 */

public class Utils {

    public static long get3MonthAgoTime() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -3); //获取3月前
        c.add(Calendar.DATE, -1);//得到前一天
        Date cTime = c.getTime();

        return cTime.getTime();
    }
}
