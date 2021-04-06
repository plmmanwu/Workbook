package com.wlwoon.workbook.share;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

/**
 * Created by wxy on 2021/4/6.
 */

class Level_Converter implements PropertyConverter<List<ShareInfo>,String> {
    @Override
    public List<ShareInfo> convertToEntityProperty(String databaseValue) {
        if (databaseValue==null) {
            return null;
        }
        List<ShareInfo> o = new Gson().fromJson(databaseValue, new TypeToken<List<ShareInfo>>() {

        }.getType());
        return o;
    }

    @Override
    public String convertToDatabaseValue(List<ShareInfo> entityProperty) {
        if (entityProperty == null||entityProperty.size()==0) {
            return null;
        }
        String s = new Gson().toJson(entityProperty);
        return s;
    }
}
