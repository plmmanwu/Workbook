package com.wlwoon.workbook;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wxy on 2020/12/24.
 */

public class Twst {

    public static void main(String[] args) {

        Date date30 = new Date(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000l);
        SimpleDateFormat dateFormat30 = new SimpleDateFormat("yyyy/MM/dd");
        String time = dateFormat30.format(date30);

//        String format = String.format("wxy 加载进度：%.2f %<% %n", 67.8216);
        System.out.println(time);
    }
}
