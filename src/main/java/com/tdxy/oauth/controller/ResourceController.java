package com.tdxy.oauth.controller;

import com.tdxy.oauth.common.ResponseHelper;
import com.tdxy.oauth.common.ZFUtil;
import com.tdxy.oauth.context.AuthUserContext;
import com.tdxy.oauth.exception.IllegalUserException;
import com.tdxy.oauth.model.bo.CourseTable;
import com.tdxy.oauth.model.bo.ScoreTable;
import com.tdxy.oauth.model.bo.User;
import com.tdxy.oauth.model.bo.ZFCookie;
import com.tdxy.oauth.model.po.Member;
import com.tdxy.oauth.service.ZFService;
import com.tdxy.oauth.service.strategy.UserStrategy;
import com.tdxy.oauth.service.strategy.factory.UserStrategyFactory;
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

    private final ZFService zfService;

    private final AuthUserContext authUserContext;

    @Autowired
    public ResourceController(ZFService zfService, AuthUserContext authUserContext) {
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
        ResponseHelper<Member> result = new ResponseHelper<>();
        User user = authUserContext.getPrincipal();
        // token只保留唯一身份标识，具体信息需要入库查询
        UserStrategy userStrategy = UserStrategyFactory.getStrategy(user.getRole());
        Member member = userStrategy.getInfo(user.getIdentity());
        return result.sendSuccess(member, user.getRole());
    }

    @RequestMapping(value = "/getAllScore", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHelper getAllScore() {
        ResponseHelper<Object> result = new ResponseHelper<>();
        try {
            User user = authUserContext.getPrincipal();
            String stuNumber = user.getIdentity();
            ZFCookie cookie = zfService.refreshCookie(stuNumber);
            ZFUtil zfUtil = new ZFUtil(cookie);
            ScoreTable scoreTable = zfUtil.getAllScore(stuNumber);
            return result.sendSuccess(scoreTable);
        } catch (IllegalUserException ex) {
            return result.sendError(ex.getMessage());
        }
    }

    @RequestMapping(value = "/getScoreByYear", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHelper getScoreByYear(@RequestParam(name = "year") String year) {
        ResponseHelper<Object> result = new ResponseHelper<>();
        try {
            User user = authUserContext.getPrincipal();
            String stuNumber = user.getIdentity();
            ZFCookie cookie = zfService.refreshCookie(stuNumber);
            ZFUtil zfUtil = new ZFUtil(cookie);
            ScoreTable scoreTable = zfUtil.getScoreByYear(stuNumber, year);
            return result.sendSuccess(scoreTable);
        } catch (IllegalUserException ex) {
            return result.sendError(ex.getMessage());
        }
    }

    @RequestMapping(value = "/getScoreByTerm", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHelper getScoreByTerm(@RequestParam(name = "year") String year,
                                         @RequestParam(name = "term") String term) {
        ResponseHelper<Object> result = new ResponseHelper<>();
        try {
            User user = authUserContext.getPrincipal();
            String stuNumber = user.getIdentity();
            ZFCookie cookie = zfService.refreshCookie(stuNumber);
            ZFUtil zfUtil = new ZFUtil(cookie);
            ScoreTable scoreTable = zfUtil.getScoreByTerm(stuNumber, year, term);
            return result.sendSuccess(scoreTable);
        } catch (IllegalUserException ex) {
            return result.sendError(ex.getMessage());
        }
    }

    @RequestMapping(value = "/getCourse", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHelper getScoreByTerm() {
        ResponseHelper<Object> result = new ResponseHelper<>();
        try {
            User user = authUserContext.getPrincipal();
            String stuNumber = user.getIdentity();
            ZFCookie cookie = zfService.refreshCookie(stuNumber);
            ZFUtil zfUtil = new ZFUtil(cookie);
            CourseTable courseTable = zfUtil.getCourse(stuNumber);
            return result.sendSuccess(courseTable);
        } catch (IllegalUserException ex) {
            return result.sendError(ex.getMessage());
        }
    }

    @RequestMapping(value = "/getCourseByYearAndTerm", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHelper getCourseByYearAndTerm(
            @RequestParam(name = "year") String year,
            @RequestParam(name = "term") String term) {
        ResponseHelper<Object> result = new ResponseHelper<>();
        try {
            User user = authUserContext.getPrincipal();
            String stuNumber = user.getIdentity();
            ZFCookie cookie = zfService.refreshCookie(stuNumber);
            ZFUtil zfUtil = new ZFUtil(cookie);
            CourseTable courseTable = zfUtil.getCourseByYearAndTerm(stuNumber, year, term);
            return result.sendSuccess(courseTable);
        } catch (IllegalUserException ex) {
            return result.sendError(ex.getMessage());
        }
    }
}
