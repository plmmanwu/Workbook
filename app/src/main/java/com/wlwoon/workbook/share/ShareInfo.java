package com.wlwoon.workbook.share;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wxy on 2021/4/1.
 */

@Entity(nameInDb = "ShareInfo_DB",indexes = {
        @Index(value = "date DESC, shareId DESC", unique = true)
})
public class ShareInfo extends BaseInfo {

    @Id(autoincrement = true)
    Long id;
    String shareId;//93556
    String shareName;//海興電力
    long shareNum;//11,528,212
    double sharePercent;//2.35%
    String date;
    //时间戳
    long time;


@Generated(hash = 665195297)
public ShareInfo(Long id, String shareId, String shareName, long shareNum,
        double sharePercent, String date, long time) {
    this.id = id;
    this.shareId = shareId;
    this.shareName = shareName;
    this.shareNum = shareNum;
    this.sharePercent = sharePercent;
    this.date = date;
    this.time = time;
}


@Generated(hash = 980962533)
public ShareInfo() {
}


    @Override
    public String toString() {
        return "ShareInfo{" +
                "id=" + id +
                ", shareId='" + shareId + '\'' +
                ", shareName='" + shareName + '\'' +
                ", shareNum=" + shareNum +
                ", sharePercent=" + sharePercent +
                ", date='" + date + '\'' +
                '}';
    }


public Long getId() {
    return this.id;
}


public void setId(Long id) {
    this.id = id;
}


public String getShareId() {
    return this.shareId;
}


public void setShareId(String shareId) {
    this.shareId = shareId;
}


public String getShareName() {
    return this.shareName;
}


public void setShareName(String shareName) {
    this.shareName = shareName;
}


public long getShareNum() {
    return this.shareNum;
}


public void setShareNum(long shareNum) {
    this.shareNum = shareNum;
}


public double getSharePercent() {
    return this.sharePercent;
}


public void setSharePercent(double sharePercent) {
    this.sharePercent = sharePercent;
}


public String getDate() {
    return this.date;
}


public void setDate(String date) {
    this.date = date;
}


public long getTime() {
    return this.time;
}


public void setTime(long time) {
    this.time = time;
}
}
