package com.tdxy.oauth.model.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

public class CourseTable {
    @JSONField(ordinal = 1)
    private String courseYear;
    @JSONField(ordinal = 2)
    private String courseTerm;
    @JSONField(ordinal = 3)
    private List<List<Course>> courseData = new ArrayList<>(7);

    public CourseTable() {
    }

    public CourseTable(String courseYear, String courseTerm) {
        this.courseYear = courseYear;
        this.courseTerm = courseTerm;
        for (int i = 0; i < 7; ++i) {
            this.courseData.add(new ArrayList<>());
        }
    }

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }

    public List<List<Course>> getCourseData() {
        return courseData;
    }

    public void setCourseData(List<List<Course>> courseData) {
        this.courseData = courseData;
    }

    public void addCourseData(int week, Course course) {
        this.courseData.get(week).add(course);
    }

    public class Course {
        private List<String> courseInfo;

        public Course() {
        }

        public Course(List<String> data) {
            this.courseInfo = data;
        }

        public List<String> getCourseInfo() {
            return courseInfo;
        }

        public void setCourseInfo(List<String> courseInfo) {
            this.courseInfo = courseInfo;
        }
    }
}
