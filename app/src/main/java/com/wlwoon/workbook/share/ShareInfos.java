package com.wlwoon.workbook.share;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

import java.util.List;

/**
 * Created by wxy on 2021/4/1.
 */

@Entity(nameInDb = "ShareInfos_DB")
public class ShareInfos extends BaseInfo{

//    @Id(autoincrement = true)
//    Long id;
    @Index(unique = true)
    String date;//2021/04/01
    @Convert(columnType = String.class,converter = Level_Converter.class)
    List<ShareInfo> info;
    @Generated(hash = 1901246476)
    public ShareInfos(String date, List<ShareInfo> info) {
        this.date = date;
        this.info = info;
    }
    @Generated(hash = 571343457)
    public ShareInfos() {
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public List<ShareInfo> getInfo() {
        return this.info;
    }
    public void setInfo(List<ShareInfo> info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ShareInfos{" +
                "date='" + date + '\'' +
                ", info=" + info +
                '}';
    }
}
