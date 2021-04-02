package com.wlwoon.workbook.share;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wxy on 2021/4/1.
 */

@Entity(nameInDb = "ShareInfo2_DB")
public class ShareInfo2 extends BaseInfo{

    @Index(unique = true)
    String shareId;//93556
    String shareName;//海興電力
    long shareNum;//11,528,212
    double sharePercent;//2.35%



    @Generated(hash = 1234296057)
    public ShareInfo2(String shareId, String shareName, long shareNum,
            double sharePercent) {
        this.shareId = shareId;
        this.shareName = shareName;
        this.shareNum = shareNum;
        this.sharePercent = sharePercent;
    }



    @Generated(hash = 362332551)
    public ShareInfo2() {
    }



    @Override
    public String toString() {
        return "ShareInfo{" +
                "shareId='" + shareId + '\'' +
                ", shareName='" + shareName + '\'' +
                ", shareNum='" + shareNum + '\'' +
                ", sharePercent='" + sharePercent + '\'' +
                '}';
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

}
