package com.tdxy.oauth.model.impl;

import com.tdxy.oauth.model.entity.Teacher;
import com.tdxy.oauth.model.mapper.TeacherMapper;
import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeacherImplTest {
    @Autowired
    private TeacherMapper teacherMapper;

    @Test
    public void getTeacherByName() {
        //Assert.assertEquals("返回不一致", Md5Crypt.apr1Crypt("xxxxxx", "xxxx"), teacher.getTchPwd());
    }
}