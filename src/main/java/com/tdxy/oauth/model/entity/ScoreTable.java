package com.tdxy.oauth.model.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

public class ScoreTable {
    @JSONField(ordinal = 1)
    private String scoreType;
    @JSONField(ordinal = 2)
    private String scoreYear;
    @JSONField(ordinal = 3)
    private String scoreTerm;
    @JSONField(ordinal = 4)
    private List<String> tableTitle;
    @JSONField(ordinal = 5)
    private List<List<String>> tableValue = new ArrayList<>(100);

    public ScoreTable() {
    }

    public ScoreTable(String scoreType, String scoreYear, String scoreTerm) {
        this.scoreType = scoreType;
        this.scoreYear = scoreYear;
        this.scoreTerm = scoreTerm;
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    public String getScoreYear() {
        return scoreYear;
    }

    public void setScoreYear(String scoreYear) {
        this.scoreYear = scoreYear;
    }

    public String getScoreTerm() {
        return scoreTerm;
    }

    public void setScoreTerm(String scoreTerm) {
        this.scoreTerm = scoreTerm;
    }

    public List<String> getTableTitle() {
        return tableTitle;
    }

    public void setTableTitle(List<String> tableTitle) {
        this.tableTitle = tableTitle;
    }

    public List<List<String>> getTableValue() {
        return tableValue;
    }

    public void setTableValue(List<List<String>> tableValue) {
        this.tableValue = tableValue;
    }

    public void addTableValue(List<String> tableValue) {
        this.tableValue.add(tableValue);
    }
}
