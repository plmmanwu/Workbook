package com.wlwoon.workbook;

import android.net.Uri;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxy on 2021/5/31.
 */

public class DemoA {
    static List<String> aList = new ArrayList<>();
    static List<String> bList = new ArrayList<>();

    static {
        bList.add("a");
        bList.add("b");
        bList.add("c");


    }

    public static void main(String[] args) {
        int a ;
        int b = 2;
        a = b;
        b = 3;
        System.out.println("a="+a);//a=2

//        aList = bList;
        List<String> cList = new ArrayList<>();
        cList.addAll(bList);
//        bList.remove("c");
        cList.add("d");
        System.out.println(new Gson().toJson(cList)+"==B=="+new Gson().toJson(bList));//[a,b]
        String url = "https://ac.hoopluz.com/prod/f954d983-b823-43de-946a-639eaf202855.png?e=1623049733&token=BFkqRg7CRBYPGs1IIsW3xTv0G0ze918TvZI3gexN:WA15JTGfR80zndP4ZGqsQZFEdN0=";
        Uri parse = Uri.parse(url);
        String host = parse.getHost();
        String path = parse.getPath();
        String authority = parse.getAuthority();
        int port = parse.getPort();
        String query = parse.getQuery();
        String scheme = parse.getScheme();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("host=" + host);
        stringBuilder.append("  path=" + path);
        stringBuilder.append("  authority=" + authority);
        stringBuilder.append("  query=" + query);
        stringBuilder.append("  scheme=" + scheme);
        stringBuilder.append("  port=" + port);

        System.out.println(stringBuilder.toString());
    }
}
