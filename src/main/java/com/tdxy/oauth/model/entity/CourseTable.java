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
        @JSONField(ordinal = 1)
        private String courseName;
        @JSONField(ordinal = 2)
        private String courseTime;
        @JSONField(ordinal = 3)
        private String courseTeacher;
        @JSONField(ordinal = 4)
        private String courseAddress;

        public Course() {
        }

        public Course(List<String> data) {
            int length = data.size();
            this.courseName = (length >= 1) ? data.get(0) : "";
            this.courseTime = (length >= 2) ? data.get(1) : "";
            this.courseTeacher = (length >= 3) ? data.get(2) : "";
            this.courseAddress = (length >= 4) ? data.get(3) : "";
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseTime() {
            return courseTime;
        }

        public void setCourseTime(String courseTime) {
            this.courseTime = courseTime;
        }

        public String getCourseTeacher() {
            return courseTeacher;
        }

        public void setCourseTeacher(String courseTeacher) {
            this.courseTeacher = courseTeacher;
        }

        public String getCourseAddress() {
            return courseAddress;
        }

        public void setCourseAddress(String courseAddress) {
            this.courseAddress = courseAddress;
        }
    }
}
