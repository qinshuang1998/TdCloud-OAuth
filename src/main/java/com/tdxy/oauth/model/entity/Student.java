package com.tdxy.oauth.model.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class Student {
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
}