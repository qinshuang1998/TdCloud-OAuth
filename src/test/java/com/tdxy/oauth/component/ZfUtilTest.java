package com.tdxy.oauth.component;

import com.tdxy.oauth.model.entity.ZfCookie;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZfUtilTest {

    @Test
    public void getCourse() {
        ZfCookie zfCookie = new ZfCookie("16250317", ""
                , "ASP.NET_SessionId", "");
        ZfUtil zfUtil = new ZfUtil(zfCookie);
        try {
            zfUtil.getCourse("16250317");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void getCourseByYearAndTerm() {
        ZfCookie zfCookie = new ZfCookie("16250317", ""
                , "ASP.NET_SessionId", "");
        ZfUtil zfUtil = new ZfUtil(zfCookie);
        try {
            zfUtil.getCourseByYearAndTerm("16250317", "2018-2019", "2");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}