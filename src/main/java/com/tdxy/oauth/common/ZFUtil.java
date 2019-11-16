package com.tdxy.oauth.common;

import com.tdxy.oauth.Constant;
import com.tdxy.oauth.model.bo.CourseTable;
import com.tdxy.oauth.model.bo.ScoreTable;
import com.tdxy.oauth.model.po.Student;
import com.tdxy.oauth.model.bo.ZFCookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ZFUtil {
    private final static Logger logger = LoggerFactory.getLogger(ZFUtil.class);
    /**
     * 教务系统地址
     */
    private String url = Constant.ZhengFang.URL;

    private HttpUtil httpUtil;

    private Map<String, String> header = new HashMap<>(1);

    public ZFUtil(ZFCookie zfCookie) {
        BasicClientCookie cookie = new BasicClientCookie(zfCookie.getCookiePrefix(),
                zfCookie.getCookieValue());
        cookie.setVersion(0);
        cookie.setDomain(Constant.ZhengFang.DOMAIN);
        cookie.setPath("/");
        this.httpUtil = new HttpUtil(cookie);
        header.put("Referer", this.url);
    }

    /**
     * 获取学生个人信息
     *
     * @param stuNumber 当前登录学号
     * @return Student实体
     */
    public Student getInfo(String stuNumber) {
        String sUrl = this.url + "/xsgrxx.aspx?xh=" + stuNumber;
        String getContent = "";
        try {
            getContent = new String(this.httpUtil.doGet(sUrl, this.header), "GBK");
        } catch (UnsupportedEncodingException e) {
            logger.warn("String encoding error when access url [{}]", sUrl);
        }
        String html = new String(getUtf8ByteFromGbkString(getContent), StandardCharsets.UTF_8);
        Document doc = Jsoup.parse(html);
        Student student = new Student();
        student.setStuNumber(doc.select("#xh").text());
        student.setStuName(doc.select("#xm").text());
        student.setStuSex(doc.select("#lbl_xb").text());
        student.setStuNation(doc.select("#lbl_mz").text());
        student.setStuMajor(doc.select("#lbl_zymc").text());
        student.setStuIdCard(doc.select("#lbl_sfzh").text());
        student.setStuPhone(doc.select("#TELNUMBER").val());
        student.setStuEmail(doc.select("#dzyxdz").val());
        return student;
    }

    public CourseTable getCourse(String stuNumber) {
        String sUrl = this.url + "/xskbcx.aspx?xh=" + stuNumber;
        String getContent = "";
        try {
            getContent = new String(this.httpUtil.doGet(sUrl, this.header), "GBK");
        } catch (UnsupportedEncodingException e) {
            logger.warn("String encoding error when access url [{}]", sUrl);
        }
        String html = new String(getUtf8ByteFromGbkString(getContent), StandardCharsets.UTF_8);
        Document doc = Jsoup.parse(html);
        Elements el = doc.select("[selected=selected]");
        CourseTable courseTable = new CourseTable(el.get(0).text(), el.get(1).text());
        return buildCourseTable(doc, courseTable);
    }

    public CourseTable getCourseByYearAndTerm(String stuNumber, String year, String term) {
        String sUrl = this.url + "/xskbcx.aspx?xh=" + stuNumber;
        String[] hiddenView = getHiddenElement(sUrl);
        Map<String, String> postData = new HashMap<>(5);
        postData.put("__VIEWSTATE", hiddenView[0]);
        postData.put("__VIEWSTATEGENERATOR", hiddenView[1]);
        postData.put("__EVENTTARGET", "xqd");
        postData.put("xnd", year);
        postData.put("xqd", term);
        String html = (String) this.httpUtil
                .doPost(sUrl, postData, this.header).get("content");
        Document doc = Jsoup.parse(html);
        CourseTable courseTable = new CourseTable(year, term);
        return buildCourseTable(doc, courseTable);
    }

    public ScoreTable getAllScore(String stuNumber) {
        String sUrl = this.url + "/xscj_gc.aspx?xh=" + stuNumber;
        String[] hiddenView = getHiddenElement(sUrl);
        Map<String, String> postData = new HashMap<>(3);
        postData.put("__VIEWSTATE", hiddenView[0]);
        postData.put("__VIEWSTATEGENERATOR", hiddenView[1]);
        try {
            postData.put("Button2", new String("在校学习成绩查询"
                    .getBytes(StandardCharsets.UTF_8), "GBK"));
        } catch (UnsupportedEncodingException e) {
            logger.warn("String encoding error when access url [{}]", sUrl);
        }
        String html = (String) this.httpUtil
                .doPost(sUrl, postData, this.header).get("content");
        Document doc = Jsoup.parse(html);
        ScoreTable scoreTable = new ScoreTable("全部成绩", "undefined", "undefined");
        return buildScoreTable(doc, scoreTable);
    }

    public ScoreTable getScoreByYear(String stuNumber, String year) {
        String sUrl = this.url + "/xscj_gc.aspx?xh=" + stuNumber;
        String[] hiddenView = getHiddenElement(sUrl);
        Map<String, String> postData = new HashMap<>(4);
        postData.put("__VIEWSTATE", hiddenView[0]);
        postData.put("__VIEWSTATEGENERATOR", hiddenView[1]);
        postData.put("ddlXN", year);
        try {
            postData.put("Button5", new String("按学年查询"
                    .getBytes(StandardCharsets.UTF_8), "GBK"));
        } catch (UnsupportedEncodingException e) {
            logger.warn("String encoding error when access url [{}]", sUrl);
        }
        String html = (String) this.httpUtil
                .doPost(sUrl, postData, this.header).get("content");
        Document doc = Jsoup.parse(html);
        ScoreTable scoreTable = new ScoreTable("按学年查询", year, "undefined");
        return buildScoreTable(doc, scoreTable);
    }

    public ScoreTable getScoreByTerm(String stuNumber, String year, String term) {
        String sUrl = this.url + "/xscj_gc.aspx?xh=" + stuNumber;
        String[] hiddenView = getHiddenElement(sUrl);
        Map<String, String> postData = new HashMap<>(5);
        postData.put("__VIEWSTATE", hiddenView[0]);
        postData.put("__VIEWSTATEGENERATOR", hiddenView[1]);
        postData.put("ddlXN", year);
        postData.put("ddlXQ", term);
        try {
            postData.put("Button1", new String("按学期查询"
                    .getBytes(StandardCharsets.UTF_8), "GBK"));
        } catch (UnsupportedEncodingException e) {
            logger.warn("String encoding error when access url [{}]", sUrl);
        }
        String html = (String) this.httpUtil
                .doPost(sUrl, postData, this.header).get("content");
        Document doc = Jsoup.parse(html);
        ScoreTable scoreTable = new ScoreTable("按学期查询", year, term);
        return buildScoreTable(doc, scoreTable);
    }

    private CourseTable buildCourseTable(Document doc, CourseTable courseTable) {
        Elements tr = doc.select("#Table1 tr");
        CourseTable.Course course;
        List<String> data;
        int week;
        for (int i = 2; i < tr.size(); ++i) {
            Elements td = tr.get(i).select("td");
            week = 0;
            for (Element tdNode : td) {
                if (tdNode.hasAttr("align")) {
                    if (tdNode.text().length() != 1) {
                        data = Arrays.asList(tdNode.text().split(" "));
                        course = courseTable.new Course(data);
                        courseTable.addCourseData(week, course);
                    }
                    ++week;
                }
            }
        }
        return courseTable;
    }

    private ScoreTable buildScoreTable(Document doc, ScoreTable scoreTable) {
        Elements tr = doc.select("#Datagrid1 tr");
        boolean isFirst = true;
        List<String> line;
        for (Element trNode : tr) {
            Elements td = trNode.select("td");
            line = new ArrayList<>(6);
            line.add(td.get(0).text());
            line.add(td.get(1).text());
            line.add(td.get(3).text());
            line.add(td.get(6).text());
            line.add(td.get(7).text());
            line.add(td.get(8).text());
            if (!isFirst) {
                scoreTable.addTableValue(line);
            } else {
                scoreTable.setTableTitle(line);
                isFirst = false;
            }
        }
        return scoreTable;
    }

    private String[] getHiddenElement(String url) {
        String[] result = new String[2];
        String getContent = null;
        try {
            getContent = new String(this.httpUtil.doGet(url, this.header), "GBK");
        } catch (UnsupportedEncodingException e) {
            logger.warn("String encoding error when access url [{}]", url);
        }
        String html = new String(getUtf8ByteFromGbkString(getContent), StandardCharsets.UTF_8);
        Document doc = Jsoup.parse(html);
        result[0] = doc.select("[name=__VIEWSTATE]").val();
        result[1] = doc.select("[name=__VIEWSTATEGENERATOR]").val();
        return result;
    }

    /**
     * gbk转utf-8
     *
     * @param gbkStr gbk字符串
     * @return byte数组
     */
    private byte[] getUtf8ByteFromGbkString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }
}
