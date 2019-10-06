package com.tdxy.oauth.common;

import java.util.Base64;

public class Rc4Encrypt {
    public static String encrypt(String plainText, String key) {
        // 密钥
        char[] k = key.toCharArray();
        // 明文
        char[] m = plainText.toCharArray();
        // 密文
        char[] encrypt = new char[m.length];
        // 得到初始化后的S向量
        int[] ksa = ksa(k);
        // 得到密钥流
        int[] prng = prng(ksa, m);
        int i = 0;
        for (int p = 0; p < prng.length; p++) {
            encrypt[i++] = (char) (m[p] ^ prng[p]);
        }
        Base64.Encoder base64 = Base64.getEncoder();
        String result = new String(encrypt);
        return base64.encodeToString(result.getBytes());
    }

    public static String decrypt(String cipherText, String key) {
        Base64.Decoder base64 = Base64.getDecoder();
        String strRead = new String(base64.decode(cipherText));
        // 密钥
        char[] k = key.toCharArray();
        // 密文
        char[] e = strRead.toCharArray();
        // 明文
        char[] decrypt = new char[e.length];
        // 得到初始化后的S向量
        int[] ksa = ksa(k);
        // 得到密钥流
        int[] prng = prng(ksa, e);
        int i = 0;
        for (int p = 0; p < prng.length; p++) {
            decrypt[i++] = (char) (e[p] ^ prng[p]);
        }
        return new String(decrypt);
    }

    private static int[] ksa(char[] k) {
        int[] s = new int[256];
        char[] t = new char[256];
        // 初始化
        for (int i = 0; i <= 255; i++) {
            s[i] = i;
            t[i] = k[i % (k.length)];
        }
        // S的初始置换
        int j = 0;
        for (int i = 0; i <= 255; i++) {
            j = (j + s[i] + t[i]) % 256;
            s[i] ^= s[j];
            s[j] ^= s[i];
            s[i] ^= s[j];
        }
        return s;
    }

    private static int[] prng(int[] s, char[] m) {
        int i = 0, j = 0, t;
        int[] k = new int[m.length];
        for (int n = 0; n < m.length; n++) {
            i = (i + 1) % 256;
            j = (j + s[i]) % 256;
            s[i] ^= s[j];
            s[j] ^= s[i];
            s[i] ^= s[j];
            t = (s[i] + s[j]) % 256;
            k[n] = s[t];
        }
        return k;
    }
}
