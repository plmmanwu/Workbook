package com.wlwoon.workbook;

import com.wlwoon.network.RetrofitFactory;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import androidx.annotation.RequiresApi;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by wxy on 2020/12/24.
 */

public class Twst {

    @RequiresApi(26)
    public static void main(String[] args) {

        Date date30 = new Date(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000l);
        SimpleDateFormat dateFormat30 = new SimpleDateFormat("yyyy/MM/dd");
        String time = dateFormat30.format(date30);

//        String format = String.format("wxy 加载进度：%.2f %<% %n", 67.8216);
        System.out.println(time);

        test();


    }

    static String requestBody = "{\n" +
            "    \"conversationId\": \"xxx\",\n" +
            "    \"atUsers\": [\n" +
            "        {\n" +
            "            \"dingtalkId\": \"xxx\",\n" +
            "            \"staffId\":\"xxx\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"chatbotCorpId\": \"dinge8a565xxxx\",\n" +
            "    \"chatbotUserId\": \"$:LWCP_v1:$Cxxxxx\",\n" +
            "    \"msgId\": \"msg0xxxxx\",\n" +
            "    \"senderNick\": \"杨xx\",\n" +
            "    \"isAdmin\": true,\n" +
            "    \"senderStaffId\": \"user123\",\n" +
            "    \"sessionWebhookExpiredTime\": 1613635652738,\n" +
            "    \"createAt\": 1613630252678,\n" +
            "    \"senderCorpId\": \"dinge8a565xxxx\",\n" +
            "    \"conversationType\": \"2\",\n" +
            "    \"senderId\": \"$:LWCP_v1:$Ff09GIxxxxx\",\n" +
            "    \"conversationTitle\": \"机器人测试-TEST\",\n" +
            "    \"isInAtList\": true,\n" +
            "    \"sessionWebhook\": \"https://oapi.dingtalk.com/robot/sendBySession?session=xxxxx\",\n" +
            "    \"text\": {\n" +
            "        \"content\": \" 你好\"\n" +
            "    },\n" +
            "    \"msgtype\": \"text\"\n" +
            "}";


    @RequiresApi(26)
    private static void test() {
        Map<String, String> map = new HashMap<>();
        long millis = System.currentTimeMillis();
        map.put("timestamp", millis + "");
        map.put("sign", generateSign(millis));
        String textMsg = "{\n" +
                "     \"msgtype\": \"text\",\n" +
                "     \"text\": {\n" +
                "         \"content\": \"我就是我, @150XXXXXXXX 是不一样的烟火\"\n" +
                "     },\n" +
                "     \"at\": {\n" +
                "         \"atMobiles\": [\n" +
                "             \"150XXXXXXXX\"\n" +
                "         ], \n" +
                "         \"isAtAll\": false\n" +
                "     }\n" +
                " }";
        Observable<String> dataObservable = RetrofitFactory.getInstance().creat2(CommonApi.class, Constance.hookUrl).sendHookMsg("bf2ac7e9a2a91b31ce248b1daba13a38ce78a081fcbeab7ca7c0235e64335c75",requestBody);
        dataObservable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });


    }

    @RequiresApi(26)
    private static String generateSign(long millis) {

        String appSecret = "SEC77a29f4add6cf1d81da3bfafdd9aac54f9d07b15864baacbd6492584681caa03";
        String stringToSign = millis + "\n" + appSecret;
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            mac.init(new SecretKeySpec(appSecret.getBytes("UTF-8"), "HmacSHA256"));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] signData = new byte[0];
        try {
            signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Base64.Encoder encoder = Base64.getEncoder();

        String sign = encoder.encodeToString(signData);

        System.out.println(sign);
        return sign;
    }
}
