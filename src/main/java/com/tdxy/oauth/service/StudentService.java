package com.tdxy.oauth.service;

import com.tdxy.oauth.model.po.Student;
import com.tdxy.oauth.model.dao.StudentDao;
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
    private final StudentDao studentDao;

    @Autowired
    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    /**
     * 通过身份标识构造学生实体
     * @param identity 用户身份标识
     * @return 学生实体
     */
    public Student getInfo(String identity) {
        return studentDao.getStudent(identity);
    }
}
