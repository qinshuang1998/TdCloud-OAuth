package com.tdxy.oauth.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisUtilTest {
    private final static Logger logger = LoggerFactory.getLogger(RedisUtilTest.class);
    @Autowired
    public RedisUtil redisUtil;

    @Test
    public void get() {
        redisUtil.del("a");
        System.out.println(redisUtil.get("a"));
    }
}