package com.tdxy.oauth.component;

import com.tdxy.oauth.model.entity.ScoreTable;
import com.tdxy.oauth.model.entity.Student;
import com.tdxy.oauth.model.entity.ZfCookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZfUtil {
    /**
     * 教务系统地址
     */
    private String url = "http://42.247.7.170";

    private HttpUtil httpUtil;

    private Map<String, String> header = new HashMap<>(1);

    public ZfUtil(ZfCookie zfCookie) {
        BasicClientCookie cookie = new BasicClientCookie(zfCookie.getCookiePrefix(),
                zfCookie.getCookieValue());
        cookie.setVersion(0);
        cookie.setDomain("42.247.7.170");
        cookie.setPath("/");
        this.httpUtil = new HttpUtil(cookie);
        header.put("Referer", this.url);
    }

    /**
     * 获取学生个人信息
     *
     * @param stuNumber 当前登录学号
     * @return Studeng实体
     * @throws Exception 异常
     */
    public Student getInfo(String stuNumber) throws Exception {
        String sUrl = this.url + "/xsgrxx.aspx?xh=" + stuNumber;
        String getContent = new String((byte[]) this.httpUtil
                .doGet(sUrl, this.header).get("content"), "GBK");
        String html = new String(getUtf8ByteFromGbkString(getContent),
                StandardCharsets.UTF_8);
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

    public ScoreTable getAllScore(String stuNumber) throws Exception {
        String sUrl = this.url + "/xscj_gc.aspx?xh=" + stuNumber;
        String[] csrfAttr = getCsrfAttr(sUrl);
        Map<String, String> postData = new HashMap<>(3);
        postData.put("__VIEWSTATE", csrfAttr[0]);
        postData.put("__VIEWSTATEGENERATOR", csrfAttr[1]);
        postData.put("Button2", new String("在校学习成绩查询"
                .getBytes(StandardCharsets.UTF_8), "GBK"));
        String html = (String) this.httpUtil
                .doPost(sUrl, postData, this.header).get("content");
        Document doc = Jsoup.parse(html);
        ScoreTable scoreTable = new ScoreTable("全部成绩", "undefine", "undefine");
        return buildScoreTable(doc, scoreTable);
    }

    public ScoreTable getScoreByYear(String stuNumber, String year) throws Exception {
        String sUrl = this.url + "/xscj_gc.aspx?xh=" + stuNumber;
        String[] csrfAttr = getCsrfAttr(sUrl);
        Map<String, String> postData = new HashMap<>(3);
        postData.put("__VIEWSTATE", csrfAttr[0]);
        postData.put("__VIEWSTATEGENERATOR", csrfAttr[1]);
        postData.put("ddlXN", year);
        postData.put("Button5", new String("按学年查询"
                .getBytes(StandardCharsets.UTF_8), "GBK"));
        String html = (String) this.httpUtil
                .doPost(sUrl, postData, this.header).get("content");
        Document doc = Jsoup.parse(html);
        ScoreTable scoreTable = new ScoreTable("按学年查询", year, "undefine");
        return buildScoreTable(doc, scoreTable);
    }

    public ScoreTable getScoreByTerm(String stuNumber, String year, String term) throws Exception {
        String sUrl = this.url + "/xscj_gc.aspx?xh=" + stuNumber;
        String[] csrfAttr = getCsrfAttr(sUrl);
        Map<String, String> postData = new HashMap<>(3);
        postData.put("__VIEWSTATE", csrfAttr[0]);
        postData.put("__VIEWSTATEGENERATOR", csrfAttr[1]);
        postData.put("ddlXN", year);
        postData.put("ddlXQ", term);
        postData.put("Button1", new String("按学期查询"
                .getBytes(StandardCharsets.UTF_8), "GBK"));
        String html = (String) this.httpUtil
                .doPost(sUrl, postData, this.header).get("content");
        Document doc = Jsoup.parse(html);
        ScoreTable scoreTable = new ScoreTable("按学期查询", year, term);
        return buildScoreTable(doc, scoreTable);
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

    private String[] getCsrfAttr(String url) throws Exception {
        String[] result = new String[2];
        String getContent = new String((byte[]) this.httpUtil
                .doGet(url, this.header).get("content"), "GBK");
        String html = new String(getUtf8ByteFromGbkString(getContent),
                StandardCharsets.UTF_8);
        Document doc = Jsoup.parse(html);
        result[0] = doc.select("[name=__VIEWSTATE]").val();
        result[1] = doc.select("[name=__VIEWSTATEGENERATOR]").val();
        return result;
    }

    /**
     * utf-8转gbk
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
