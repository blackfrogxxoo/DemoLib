package me.black.library.util.java;

import android.text.TextUtils;

import java.util.regex.Pattern;


/**
 * 正则表达式工具类
 * Created by maodeqiang on 2015/8/13.
 */
public class RegularUtil {

    static final String TAG = "RegularUtil";
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证密码
     */

    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,18}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";

    /**
     * 正则表达式：验证数字
     */
    public static final String REGEX_NUMBER = "\\d+";


    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-zA-Z0-9_\\.-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\\u4e00-\\u9fa5],{0,6}$";

    /**
     * 正则表达式：验证身份证
     */
//    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";


    //身份证正则表达式(15位)
    public static final String ID_CARD_1 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    //身份证正则表达式(18位)
    public static final String ID_CARD_2 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";


    /**
     * 正则表达式：验证URL
     */
//    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        if (TextUtils.isEmpty(chinese)) return false;
        Boolean isChinese;
        for (char ch : chinese.toCharArray()) {
            if (!Pattern.matches(REGEX_CHINESE, String.valueOf(ch))) {
                System.out.println(TAG + "isChinese(): " + "false   " + chinese);
                return false;
            }
        }
        System.out.println(TAG + "isChinese(): " + "true   " + chinese);
        return true;
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(ID_CARD_1, idCard) || Pattern.matches(ID_CARD_2, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
//    public static boolean isUrl(String url) {
//        return Pattern.matches(REGEX_URL, url);
//    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    public static boolean isNumber(String number) {
        return Pattern.matches(REGEX_NUMBER, number);
    }
}
