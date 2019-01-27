package com.tdxy.oauth.service;

import com.tdxy.oauth.model.entity.Student;
import com.tdxy.oauth.model.impl.StudentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 学生服务层
 *
 * @author Qug_
 */
@Service
public class StudentService {
    /**
     * 学生DAO层
     */
    private final StudentImpl studentImpl;

    @Autowired
    public StudentService(StudentImpl studentImpl) {
        this.studentImpl = studentImpl;
    }

    /**
     * 通过身份标识构造学生实体
     * @param identity 用户身份标识
     * @return 学生实体
     */
    public Student getInfo(String identity) {
        return this.studentImpl.getStudent(identity);
    }
}
