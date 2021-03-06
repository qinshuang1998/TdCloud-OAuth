package com.tdxy.oauth.common;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Apache_HttpClient
 *
 * @author Qug_
 */
public class HttpUtil {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private CloseableHttpClient httpClient;

    private CookieStore cookieStore = new BasicCookieStore();

    public HttpUtil() {
        init();
    }

    public HttpUtil(BasicClientCookie cookie) {
        init(cookie);
    }

    private void init() {
        this.httpClient = HttpClients.custom()
                .setDefaultCookieStore(this.cookieStore)
                .build();
    }

    private void init(BasicClientCookie cookie) {
        this.cookieStore.addCookie(cookie);
        this.httpClient = HttpClients.custom()
                .setDefaultCookieStore(this.cookieStore)
                .build();
    }

    /**
     * 返回的是byte[]，注意转换
     *
     * @param url 链接
     * @return Map
     */
    public byte[] doGet(String url, Map<String, String> header) {
        byte[] content;
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response;
        try {
            if (header != null) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    get.setHeader(entry.getKey(), entry.getValue());
                }
            }
            response = this.httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toByteArray(entity);
            response.close();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> doPost(String url, Map<String, String> data, Map<String, String> header) {
        HttpPost httpPost;
        String content = null;
        Map<String, Object> result = new HashMap<>(2);
        try {
            httpPost = new HttpPost(url);
            // 设置参数
            if (header != null) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            List<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, String> elem : data.entrySet()) {
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, StandardCharsets.UTF_8);
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse response = this.httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                content = EntityUtils.toString(resEntity, StandardCharsets.UTF_8);
            }
            response.close();
            result.put("statusCode", statusCode);
            result.put("content", content);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public void closeUtil() {
        try {
            this.httpClient.close();
        } catch (IOException e) {
            logger.warn("HttpClient close failure");
        }
    }

    public Cookie getCookie() {
        List<Cookie> cookie = this.cookieStore.getCookies();
        return cookie.get(0);
    }
}
