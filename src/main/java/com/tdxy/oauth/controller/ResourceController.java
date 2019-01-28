package com.tdxy.oauth.controller;

import com.tdxy.oauth.component.ResponseHelper;
import com.tdxy.oauth.component.ZfUtil;
import com.tdxy.oauth.context.AuthUserContext;
import com.tdxy.oauth.exception.IllegalUserException;
import com.tdxy.oauth.model.entity.*;
import com.tdxy.oauth.service.StudentService;
import com.tdxy.oauth.service.TeacherService;
import com.tdxy.oauth.service.ZfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 资源控制器
 *
 * @author Qug_
 */
@Controller
@RequestMapping("/api")
public class ResourceController {
    /**
     * 学生用户的一些操作
     */
    private final StudentService studentService;

    private final TeacherService teacherService;

    private final ZfService zfService;

    private final AuthUserContext authUserContext;

    @Autowired
    public ResourceController(StudentService studentService,
                              TeacherService teacherService, ZfService zfService, AuthUserContext authUserContext) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.zfService = zfService;
        this.authUserContext = authUserContext;
    }

    /**
     * 获取已授权用户的个人信息
     *
     * @return 用户信息Json字符串
     */
    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHelper getInfo() {
        ResponseHelper<Object> result = new ResponseHelper<>();
        User user = this.authUserContext.getPrincipal();
        // token只保留唯一身份标识，具体信息需要入库查询
        switch (user.getRole()) {
            case "student":
                Student student = this.studentService.getInfo(user.getIdentity());
                result.sendSuccess(student, "student");
                break;
            case "teacher":
                Teacher teacher = this.teacherService.getTeacherByWorknum(user.getIdentity());
                result.sendSuccess(teacher, "teacher");
                break;
            default:
                break;
        }
        return result;
    }

    @RequestMapping(value = "/getAllScore", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHelper getAllScore() {
        ResponseHelper<Object> result = new ResponseHelper<>();
        try {
            User user = this.authUserContext.getPrincipal();
            String stuNumber = user.getIdentity();
            ZfCookie cookie = this.zfService.refreshCookie(stuNumber);
            ZfUtil zfUtil = new ZfUtil(cookie);
            ScoreTable scoreTable = zfUtil.getAllScore(stuNumber);
            return result.sendSuccess(scoreTable);
        } catch (IllegalUserException ex) {
            return result.sendError(ex.getMessage());
        } catch (IOException ex) {
            return result.sendError("系统错误");
        }
    }

    @RequestMapping(value = "/getScoreByYear", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHelper getScoreByYear(@RequestParam(name = "year") String year) {
        ResponseHelper<Object> result = new ResponseHelper<>();
        try {
            User user = this.authUserContext.getPrincipal();
            String stuNumber = user.getIdentity();
            ZfCookie cookie = this.zfService.refreshCookie(stuNumber);
            ZfUtil zfUtil = new ZfUtil(cookie);
            ScoreTable scoreTable = zfUtil.getScoreByYear(stuNumber, year);
            return result.sendSuccess(scoreTable);
        } catch (IllegalUserException ex) {
            return result.sendError(ex.getMessage());
        } catch (IOException ex) {
            return result.sendError("系统错误");
        }
    }

    @RequestMapping(value = "/getScoreByTerm", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHelper getScoreByTerm(@RequestParam(name = "year") String year,
                                         @RequestParam(name = "term") String term) {
        ResponseHelper<Object> result = new ResponseHelper<>();
        try {
            User user = this.authUserContext.getPrincipal();
            String stuNumber = user.getIdentity();
            ZfCookie cookie = this.zfService.refreshCookie(stuNumber);
            ZfUtil zfUtil = new ZfUtil(cookie);
            ScoreTable scoreTable = zfUtil.getScoreByTerm(stuNumber, year, term);
            return result.sendSuccess(scoreTable);
        } catch (IllegalUserException ex) {
            return result.sendError(ex.getMessage());
        } catch (IOException ex) {
            return result.sendError("系统错误");
        }
    }

    @RequestMapping(value = "/getCourse", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHelper getScoreByTerm() {
        ResponseHelper<Object> result = new ResponseHelper<>();
        try {
            User user = this.authUserContext.getPrincipal();
            String stuNumber = user.getIdentity();
            ZfCookie cookie = this.zfService.refreshCookie(stuNumber);
            ZfUtil zfUtil = new ZfUtil(cookie);
            CourseTable courseTable = zfUtil.getCourse(stuNumber);
            return result.sendSuccess(courseTable);
        } catch (IllegalUserException ex) {
            return result.sendError(ex.getMessage());
        } catch (IOException ex) {
            return result.sendError("系统错误");
        }
    }

    @RequestMapping(value = "/getCourseByYearAndTerm", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHelper getCourseByYearAndTerm(
            @RequestParam(name = "year") String year,
            @RequestParam(name = "term") String term) {
        ResponseHelper<Object> result = new ResponseHelper<>();
        try {
            User user = this.authUserContext.getPrincipal();
            String stuNumber = user.getIdentity();
            ZfCookie cookie = this.zfService.refreshCookie(stuNumber);
            ZfUtil zfUtil = new ZfUtil(cookie);
            CourseTable courseTable = zfUtil.getCourseByYearAndTerm(stuNumber, year, term);
            return result.sendSuccess(courseTable);
        } catch (IllegalUserException ex) {
            return result.sendError(ex.getMessage());
        } catch (IOException ex) {
            return result.sendError("系统错误");
        }
    }
}
