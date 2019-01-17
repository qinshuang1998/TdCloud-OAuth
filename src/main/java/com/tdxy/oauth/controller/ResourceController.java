package com.tdxy.oauth.controller;

import com.tdxy.oauth.component.ResponseHelper;
import com.tdxy.oauth.component.ZfUtil;
import com.tdxy.oauth.exception.InvalidTokenException;
import com.tdxy.oauth.model.entity.*;
import com.tdxy.oauth.service.StudentService;
import com.tdxy.oauth.service.TeacherService;
import com.tdxy.oauth.service.TokenService;
import com.tdxy.oauth.service.ZfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private StudentService studentService;

    private TeacherService teacherService;

    private ZfService zfService;

    /**
     * access_token的一些服务
     */
    private TokenService tokenService;

    @Autowired
    public ResourceController(StudentService studentService, TeacherService teacherService, ZfService zfService, TokenService tokenService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.zfService = zfService;
        this.tokenService = tokenService;
    }

    /**
     * 获取已授权用户的个人信息
     *
     * @param accessToken 合法的access_token
     * @return 用户信息Json字符串
     */
    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHelper getInfo(@RequestParam(name = "access_token") String accessToken) {
        ResponseHelper<Object> result = new ResponseHelper<>();
        try {
            // 通过token获取用户信息
            User user = this.tokenService.getUserByToken(accessToken);
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
        } catch (InvalidTokenException ex) {
            result.sendError(ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getAllScore", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHelper getAllScore(@RequestParam(name = "access_token") String accessToken) {
        ResponseHelper<Object> result = new ResponseHelper<>();
        try {
            User user = this.tokenService.getUserByToken(accessToken);
            String stuNumber = user.getIdentity();
            ZfCookie cookie = this.zfService.refreshCookie(stuNumber);
            ZfUtil zfUtil = new ZfUtil(cookie);
            ScoreTable scoreTable = zfUtil.getAllScore(stuNumber);
            return result.sendSuccess(scoreTable);
        } catch (Exception ex) {
            return result.sendError(ex.getMessage());
        }
    }

    @RequestMapping(value = "/getScoreByYear", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHelper getScoreByYear(@RequestParam(name = "access_token") String accessToken,
                                         @RequestParam(name = "year") String year) {
        ResponseHelper<Object> result = new ResponseHelper<>();
        try {
            User user = this.tokenService.getUserByToken(accessToken);
            String stuNumber = user.getIdentity();
            ZfCookie cookie = this.zfService.refreshCookie(stuNumber);
            ZfUtil zfUtil = new ZfUtil(cookie);
            ScoreTable scoreTable = zfUtil.getScoreByYear(stuNumber, year);
            return result.sendSuccess(scoreTable);
        } catch (Exception ex) {
            return result.sendError(ex.getMessage());
        }
    }

    @RequestMapping(value = "/getScoreByTerm", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHelper getScoreByTerm(@RequestParam(name = "access_token") String accessToken,
                                         @RequestParam(name = "year") String year,
                                         @RequestParam(name = "term") String term) {
        ResponseHelper<Object> result = new ResponseHelper<>();
        try {
            User user = this.tokenService.getUserByToken(accessToken);
            String stuNumber = user.getIdentity();
            ZfCookie cookie = this.zfService.refreshCookie(stuNumber);
            ZfUtil zfUtil = new ZfUtil(cookie);
            ScoreTable scoreTable = zfUtil.getScoreByTerm(stuNumber, year, term);
            return result.sendSuccess(scoreTable);
        } catch (Exception ex) {
            return result.sendError(ex.getMessage());
        }
    }
}
