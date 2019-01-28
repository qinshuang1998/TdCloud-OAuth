package com.tdxy.oauth.service;

import com.tdxy.oauth.OauthSystem;
import com.tdxy.oauth.component.HttpUtil;
import com.tdxy.oauth.component.ImageOcr;
import com.tdxy.oauth.component.Rc4Encrypt;
import com.tdxy.oauth.component.ZfUtil;
import com.tdxy.oauth.exception.IllegalUserException;
import com.tdxy.oauth.model.entity.Student;
import com.tdxy.oauth.model.entity.ZfCookie;
import com.tdxy.oauth.model.impl.StudentImpl;
import com.tdxy.oauth.model.impl.ZfCookieImpl;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 正方教务服务层
 *
 * @author Qug_
 */
@Service
public class ZfService {
    /**
     * 学生DAO层
     */
    private final StudentImpl studentImpl;

    private final ZfCookieImpl zfCookieImpl;

    /**
     * 验证码识别
     */
    private final ImageOcr imageOcr;

    @Autowired
    public ZfService(StudentImpl studentImpl, ZfCookieImpl zfCookieImpl, ImageOcr imageOcr) {
        this.studentImpl = studentImpl;
        this.zfCookieImpl = zfCookieImpl;
        this.imageOcr = imageOcr;
    }

    /**
     * 自动注入配置的路径，用来存下载的验证码图片
     */
    @Value("${srcPath}")
    private String srcPath;

    /**
     * 教务系统地址
     */
    private String url = OauthSystem.ZhengFang.URL;

    /**
     * 下发Code
     *
     * @param httpUtil 传递同一个HttpUtil，保证cookie一致
     * @return 字符串
     * @throws Exception 异常
     */
    private String getCode(HttpUtil httpUtil) throws IOException {
        String imgSrc = this.url + "/CheckCode.aspx";
        String saveSrc = this.srcPath + System.currentTimeMillis() + ".gif";
        FileOutputStream imgSave = new FileOutputStream(saveSrc);
        imgSave.write(httpUtil.doGet(imgSrc, null));
        imgSave.flush();
        imgSave.close();
        return saveSrc;
    }

    /**
     * 正方模拟登录
     *
     * @param stuNumber 学号
     * @param stuPwd    密码
     * @return boolean
     * @throws Exception 异常
     */
    public boolean doLogin(String stuNumber, String stuPwd) throws IOException {
        int cookieStatus = checkCookie(getCookieByStuNmuber(stuNumber));
        HttpUtil httpUtil = new HttpUtil();
        int statusCode;
        String code;
        Map<String, String> loginForm;
        // 为提高成功率，这里尝试了三次，事不过三，不宜过多
        for (int count = 0; count < OauthSystem.ZhengFang.TRY_LOGIN_TIMES; ++count) {
            // 自动识别验证码
            code = this.imageOcr.getAllOcr(getCode(httpUtil));
            loginForm = getForm(stuNumber, stuPwd, code);
            statusCode = (int) httpUtil.doPost(this.url, loginForm, null).get("statusCode");
            if (statusCode == OauthSystem.HttpStatus.REDIRECT) {
                String hash = Md5Crypt.apr1Crypt(
                        stuNumber + Md5Crypt.apr1Crypt(stuPwd, OauthSystem.Security.MD5_SALT_STU_PWD),
                        OauthSystem.Security.MD5_SALT_CACHE);
                ZfCookie zfCookie = new ZfCookie(stuNumber, hash,
                        httpUtil.getCookie().getName(), httpUtil.getCookie().getValue());
                storeCookie(zfCookie, cookieStatus);
                ZfUtil zfUtil = new ZfUtil(zfCookie);
                Student student = zfUtil.getInfo(stuNumber);
                String key = Md5Crypt.apr1Crypt(
                        student.getStuNumber() + student.getStuName(), OauthSystem.Security.MD5_SALT_RC4);
                student.setStuPwd(Rc4Encrypt.encrypt(stuPwd, key));
                storeStudent(student);
                // 销毁会话
                httpUtil.closeUtil();
                return true;
            }
        }
        return false;
    }

    public int checkCookie(ZfCookie cookie) throws UnsupportedEncodingException {
        if (cookie == null) {
            return OauthSystem.ZhengFang.COOKIE_NOT_EXIST;
        }
        ZfUtil zfUtil = new ZfUtil(cookie);
        String stuNumber = cookie.getStuNumber();
        Student student = zfUtil.getInfo(stuNumber);
        return stuNumber.equals(student.getStuNumber()) ?
                OauthSystem.ZhengFang.COOKIE_VALID : OauthSystem.ZhengFang.COOKIE_INVALID;
    }

    private void storeCookie(ZfCookie cookie, int cookieStatus) {
        switch (cookieStatus) {
            case OauthSystem.ZhengFang.COOKIE_NOT_EXIST:
                this.zfCookieImpl.addOne(cookie);
                break;
            case OauthSystem.ZhengFang.COOKIE_INVALID:
                this.zfCookieImpl.updateOne(cookie);
                break;
            default:
                break;
        }
    }

    public ZfCookie getCookieByStuNmuber(String stuNumber) {
        return this.zfCookieImpl.findByStuNumber(stuNumber);
    }

    public ZfCookie getCookieByHash(String cookieHash) {
        return this.zfCookieImpl.findByHash(cookieHash);
    }

    public ZfCookie refreshCookie(String stuNumber) throws IllegalUserException, IOException {
        Student student = this.studentImpl.getStudent(stuNumber);
        if (student != null) {
            String key = Md5Crypt.apr1Crypt(
                    student.getStuNumber() + student.getStuName(), OauthSystem.Security.MD5_SALT_RC4);
            doLogin(student.getStuNumber(), Rc4Encrypt.decrypt(student.getStuPwd(), key));
        } else {
            throw new IllegalUserException("未找到相应的学生信息");
        }
        return getCookieByStuNmuber(stuNumber);
    }

    /**
     * 构造登录POST数据
     *
     * @param stuNumber 学号
     * @param stuPwd    密码
     * @param code      验证码
     * @return Map
     * @throws UnsupportedEncodingException 异常
     */
    private Map<String, String> getForm(String stuNumber, String stuPwd, String code) throws UnsupportedEncodingException {
        Map<String, String> loginForm = new HashMap<>(8);
        // 构造post参数，以下参数缺一不可，包括空值， 前两个参数我们学院没变过
        loginForm.put("__VIEWSTATE", "dDwtNTE2MjI4MTQ7Oz45bWhfCessXfhRjACUXmYSn6Dlyw==");
        loginForm.put("__VIEWSTATEGENERATOR", "92719903");
        loginForm.put("lbLanguage", "");
        loginForm.put("Button1", "");
        loginForm.put("txtUserName", stuNumber);
        loginForm.put("TextBox2", stuPwd);
        loginForm.put("txtSecretCode", code);
        loginForm.put("RadioButtonList1", new String("学生".getBytes(StandardCharsets.UTF_8), "GBK"));
        return loginForm;
    }

    /**
     * 信息入库
     *
     * @param student 学生
     */
    private void storeStudent(Student student) {
        String stuName = student.getStuNumber();
        // 首先判断数据库里有没有当前学号，没有的话直接INSERT
        if (this.studentImpl.hasStudent(stuName) == 0) {
            this.studentImpl.addStudent(student);
            // 如果有学号记录，就再比对下是否一致，更新下数据
        } else if (!this.studentImpl.getStudent(stuName).equals(student)) {
            this.studentImpl.updateInfo(student);
        }
    }
}
