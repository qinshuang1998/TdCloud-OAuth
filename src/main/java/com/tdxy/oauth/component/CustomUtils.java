package com.tdxy.oauth.component;

/**
 * 自定义工具类
 *
 * @author Qug_
 */
public class CustomUtils {
    public String getNonceStr() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder str = new StringBuilder();
        int rand;
        int charsLength = chars.length();
        for (int i = 0; i < 10; i++) {
            rand = (int) (Math.random() * charsLength);
            str.append(chars.charAt(rand));
        }
        return str.toString();
    }

    public String getNonceStr(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder str = new StringBuilder();
        int rand;
        int charsLength = chars.length();
        for (int i = 0; i < length; i++) {
            rand = (int) (Math.random() * charsLength);
            str.append(chars.charAt(rand));
        }
        return str.toString();
    }
}
