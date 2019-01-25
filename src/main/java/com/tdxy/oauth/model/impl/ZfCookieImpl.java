package com.tdxy.oauth.model.impl;

import com.tdxy.oauth.model.entity.ZfCookie;
import com.tdxy.oauth.model.mapper.ZfCookieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ZfCookieImpl {
    @Autowired
    private ZfCookieMapper zfCookieMapper;

    public int addOne(ZfCookie cookie) {
        return this.zfCookieMapper.insertOne(cookie);
    }

    public int updateOne(ZfCookie cookie) {
        return this.zfCookieMapper.updateOne(cookie);
    }

    public ZfCookie findByStuNumber(String stuNumber) {
        return this.zfCookieMapper.selectByStuNumber(stuNumber);
    }

    public ZfCookie findByHash(String cookieHash) {
        return this.zfCookieMapper.selectByHash(cookieHash);
    }
}
