package com.tdxy.oauth.model.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class Teacher {
    @JSONField(serialize = false)
    private Integer tchId;
    @JSONField(ordinal = 1)
    private String tchWorknum;
    @JSONField(ordinal = 2)
    private String tchName;
    @JSONField(serialize = false)
    private String tchPwd;
    @JSONField(ordinal = 3)
    private String tchIdCard;
    @JSONField(ordinal = 4)
    private String tchPhone;
    @JSONField(ordinal = 5)
    private String tchWorkType;

    public Integer getTchId() {
        return tchId;
    }

    public void setTchId(Integer tchId) {
        this.tchId = tchId;
    }

    public String getTchWorknum() {
        return tchWorknum;
    }

    public void setTchWorknum(String tchWorknum) {
        this.tchWorknum = tchWorknum == null ? null : tchWorknum.trim();
    }

    public String getTchName() {
        return tchName;
    }

    public void setTchName(String tchName) {
        this.tchName = tchName == null ? null : tchName.trim();
    }

    public String getTchPwd() {
        return tchPwd;
    }

    public void setTchPwd(String tchPwd) {
        this.tchPwd = tchPwd == null ? null : tchPwd.trim();
    }

    public String getTchIdCard() {
        return tchIdCard;
    }

    public void setTchIdCard(String tchIdCard) {
        this.tchIdCard = tchIdCard == null ? null : tchIdCard.trim();
    }

    public String getTchPhone() {
        return tchPhone;
    }

    public void setTchPhone(String tchPhone) {
        this.tchPhone = tchPhone == null ? null : tchPhone.trim();
    }

    public String getTchWorkType() {
        return tchWorkType;
    }

    public void setTchWorkType(String tchWorkType) {
        this.tchWorkType = tchWorkType == null ? null : tchWorkType.trim();
    }
}