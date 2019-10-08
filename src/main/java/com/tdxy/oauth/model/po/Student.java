package com.tdxy.oauth.model.po;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Objects;

public class Student implements Member {
    @JSONField(ordinal = 1)
    private String stuNumber;
    @JSONField(ordinal = 2)
    private String stuName;
    @JSONField(ordinal = 3)
    private String stuSex;
    @JSONField(ordinal = 4)
    private String stuNation;
    @JSONField(ordinal = 5)
    private String stuMajor;
    @JSONField(ordinal = 6)
    private String stuIdCard;
    @JSONField(ordinal = 7)
    private String stuPhone;
    @JSONField(ordinal = 8)
    private String stuEmail;
    @JSONField(serialize = false)
    private String stuPwd;

    public String getStuPwd() {
        return stuPwd;
    }

    public void setStuPwd(String stuPwd) {
        this.stuPwd = stuPwd;
    }

    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber == null ? null : stuNumber.trim();
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName == null ? null : stuName.trim();
    }

    public String getStuSex() {
        return stuSex;
    }

    public void setStuSex(String stuSex) {
        this.stuSex = stuSex == null ? null : stuSex.trim();
    }

    public String getStuNation() {
        return stuNation;
    }

    public void setStuNation(String stuNation) {
        this.stuNation = stuNation == null ? null : stuNation.trim();
    }

    public String getStuMajor() {
        return stuMajor;
    }

    public void setStuMajor(String stuMajor) {
        this.stuMajor = stuMajor == null ? null : stuMajor.trim();
    }

    public String getStuIdCard() {
        return stuIdCard;
    }

    public void setStuIdCard(String stuIdCard) {
        this.stuIdCard = stuIdCard == null ? null : stuIdCard.trim();
    }

    public String getStuPhone() {
        return stuPhone;
    }

    public void setStuPhone(String stuPhone) {
        this.stuPhone = stuPhone == null ? null : stuPhone.trim();
    }

    public String getStuEmail() {
        return stuEmail;
    }

    public void setStuEmail(String stuEmail) {
        this.stuEmail = stuEmail == null ? null : stuEmail.trim();
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuNumber='" + stuNumber + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuSex='" + stuSex + '\'' +
                ", stuNation='" + stuNation + '\'' +
                ", stuMajor='" + stuMajor + '\'' +
                ", stuIdCard='" + stuIdCard + '\'' +
                ", stuPhone='" + stuPhone + '\'' +
                ", stuEmail='" + stuEmail + '\'' +
                ", stuPwd='" + stuPwd + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(stuNumber, student.stuNumber) &&
                Objects.equals(stuName, student.stuName) &&
                Objects.equals(stuSex, student.stuSex) &&
                Objects.equals(stuNation, student.stuNation) &&
                Objects.equals(stuMajor, student.stuMajor) &&
                Objects.equals(stuIdCard, student.stuIdCard) &&
                Objects.equals(stuPhone, student.stuPhone) &&
                Objects.equals(stuEmail, student.stuEmail) &&
                Objects.equals(stuPwd, student.stuPwd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stuNumber, stuName, stuSex, stuNation, stuMajor, stuIdCard, stuPhone, stuEmail, stuPwd);
    }
}