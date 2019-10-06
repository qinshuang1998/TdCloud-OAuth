package com.tdxy.oauth.model.dao;

import com.tdxy.oauth.model.po.Student;
import com.tdxy.oauth.model.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Qug_
 */
@Repository
public class StudentDao {
    /**
     * Mybatis_Student的mapper接口
     */
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 学生个人信息入库
     * @param student 学生实体
     */
    public void addStudent(Student student) {
        this.studentMapper.insertOne(student);
    }

    /**
     * 是否存在此学生
     * @param stuNumber 学号
     * @return 记录条数
     */
    public int hasStudent(String stuNumber) {
        return this.studentMapper.findLineByStuNumber(stuNumber);
    }

    /**
     * 以学号查询学生姓名
     * @param stuNumber 学号
     * @return 姓名
     */
    public String getNameByStuNumber(String stuNumber) {
        return this.studentMapper.findNameByStuNumber(stuNumber);
    }

    /**
     * 更新原有的学生记录
     * @param student 学生实体
     */
    public void updateInfo(Student student) {
        this.studentMapper.updateByStuNumber(student);
    }

    /**
     * 以学号查询学生
     * @param stuNumber 学号
     * @return 学生实体
     */
    public Student getStudent(String stuNumber) {
        return this.studentMapper.findAllByStuNumber(stuNumber);
    }
}
