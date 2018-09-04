package me.black.demolib.http;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpParamsUtil {

    /**
     * 统一添加countryCode参数，必须在finallyAppendSignature前调用
     * @param params
     */
    public static void appendCountryCode(Map<String, String> params) {
        params.put("countryCode", getCountryCode());
    }

    /**
     * 统一添加timestamp、nonce、appId、lang参数，必须在finallyAppendSignature前调用
     * @param params
     */
    public static void append_timestamp_nonce_appId_lang(Map<String, String> params) {
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("nonce", (int) (Math.random() * 10000000) + "");
        params.put("appId", "dapp");
        params.put("lang", getLang());
    }

    /**
     * 根据其他参数生成最后一个signature参数，必须在最后调用
     * @param params
     */
    public static void finallyAppendSignature(Map<String, String> params) {
        params.put("signature", createSignature(params));
    }

    private static String getLang() {
        return "zh-CN";
    }

    public static String getCountryCode() {
        return "+86";
    }

    public static String createSignature(Map<String, String> params) {
        if(params == null) return null;
        Set<Map.Entry<String, String>> set = params.entrySet();
        Iterator<Map.Entry<String, String>> it = set.iterator();
        StringBuilder sb = new StringBuilder();
        List<String> keyList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String key = entry.getKey();
            keyList.add(key);
        }
        Collections.sort(keyList);
        for(String key : keyList) {
            sb.append(key + "=" + params.get(key) + "&");
        }
        sb.append("secret=82c46fdf461e4a6abac5bedb03141aab");
        System.out.println(sb.toString());
        return md5(sb.toString());
    }

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
