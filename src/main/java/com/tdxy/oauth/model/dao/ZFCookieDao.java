package com.tdxy.oauth.model.dao;

import com.tdxy.oauth.model.bo.ZFCookie;
import com.tdxy.oauth.model.mapper.ZFCookieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ZFCookieDao {
    @Autowired
    private ZFCookieMapper zfCookieMapper;

    public void addOne(ZFCookie cookie) {
        this.zfCookieMapper.insertOne(cookie);
    }

    public void updateOne(ZFCookie cookie) {
        this.zfCookieMapper.updateOne(cookie);
    }

    public ZFCookie findByStuNumber(String stuNumber) {
        return this.zfCookieMapper.selectByStuNumber(stuNumber);
    }

    public ZFCookie findByHash(String cookieHash) {
        return this.zfCookieMapper.selectByHash(cookieHash);
    }
}
