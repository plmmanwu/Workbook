package com.wlwoon.workbook.share;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wxy on 2021/3/31.
 */

public class Date_Converter implements PropertyConverter<String, Long> {
    @Override
    public String convertToEntityProperty(Long databaseValue) {

        if (databaseValue == null) {
            return null;
        }
        Date date = new Date(databaseValue);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String format = simpleDateFormat.format(date);
        return format;
    }

    @Override
    public Long convertToDatabaseValue(String arrays) {
        if (arrays == null) {
            return null;
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = null;
            try {
                date = simpleDateFormat.parse(arrays);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date==null?null:date.getTime();
        }
    }
}
