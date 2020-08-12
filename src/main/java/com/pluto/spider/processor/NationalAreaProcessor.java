package com.pluto.spider.processor;

import com.pluto.spider.dao.ErrorUrlMapper;
import com.pluto.spider.entity.ErrorUrl;
import com.pluto.spider.entity.SysArea;
import com.pluto.spider.excutor.AreaAsyncExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import javax.annotation.Resource;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hehaijin
 * @date 2020/8/10 15:52
 * @description
 */
@Component
@Slf4j
public class NationalAreaProcessor implements PageProcessor {

    @Resource
    private AreaAsyncExecutor executor;

    @Resource
    private ErrorUrlMapper errorUrlMapper;

    public static String NATIONNAL_AREA_URL = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/index.html";

    private Site site = Site
            .me()
            .setRetryTimes(3)
            .setSleepTime(100)
            .setCharset("GBK")
            .addHeader("Connection", "keep-alive")
            .addHeader("Cache-Control", "max-age=0")
            .addHeader("Accept-Encoding", "gzip, deflate")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0");

    private Integer pageCount = 0;

    @Override
    public void process(Page page) {
        try {
            String url = page.getRequest().getUrl();
            log.info("page start ************************** " + url);
            if (url.equals(NATIONNAL_AREA_URL)) {
                generateProvinceUrl(page);
                page.setSkip(true);
                pageCount++;
                return;
            }
            String substring = url.substring(url.indexOf("2019/") + 5, url.indexOf(".html"));
            if (substring.length() == 2) {
                generateCityUrl(page);
                page.setSkip(true);
            } else if (substring.length() == 7) {
                generateCountyUrl(page);
                page.setSkip(true);
            }
            log.info("page end ************************** " + url);
            pageCount++;
            System.out.println("共爬取了  " + pageCount + "  页");
        } catch (Exception e) {
            log.info("异常了 ： {}, 地址： {}", e.getMessage(), page.getRequest().getUrl());
            errorUrlMapper.insert(new ErrorUrl().setUrl(page.getRequest().getUrl()).setFlag(0));
        }

    }


    @Override
    public Site getSite() {
        return this.site;
    }

    /**
     * 生成省份url
     *
     * @param page
     * @return
     */
    private void generateProvinceUrl(Page page) {
        Html html = page.getHtml();
        List<String> provinceUrl = html.xpath("//tr[@class='provincetr']//td//a").links().all();
        List<String> provinceNames = html.xpath("//tr[@class='provincetr']//td//a//text()").all();
        page.addTargetRequests(provinceUrl);
        List<Integer> codeList = generateAreaCode(provinceUrl);
        dealInsert(provinceNames, codeList, 1, 0);
    }

    /**
     * 生成城市url
     *
     * @param page
     * @return
     */
    private void generateCityUrl(Page page) {
        Html html = page.getHtml();
        List<String> cityUrl = html.xpath("//tr[@class='citytr']//td[1]//a").links().all();
        List<String> cityCode = html.xpath("//tr[@class='citytr']//td[1]//a//text()").all();
        List<String> cityNames = html.xpath("//tr[@class='citytr']//td[2]//a//text()").all();
        page.addTargetRequests(cityUrl);
        String parentStr = cityCode.get(0).substring(0, 2);
        Integer parentCode = fillZero(parentStr);
        List<Integer> codeList = generateAreaCode(cityUrl);
        dealInsert(cityNames, codeList, 2, parentCode);
    }

    /**
     * 生成县区url
     *
     * @param page
     * @return
     */
    private void generateCountyUrl(Page page) {
        Html html = page.getHtml();

        List<String> countyCodes = html.xpath("//tr[@class='countytr']//td[1]//a//text()").all();
        List<String> countyNames = html.xpath("//tr[@class='countytr']//td[2]//a//text()").all();
        List<String> countyUrls = html.xpath("//tr[@class='countytr']//td[2]").links().all();
        String parentStr = "";
        try {
            parentStr = countyCodes.get(0).substring(0, 4);
            Integer parentCode = fillZero(parentStr);
            List<Integer> codeList = generateAreaCode(countyUrls);
            dealInsert(countyNames, codeList, 3, parentCode);
        } catch (Exception e) {
            log.info("父亲的codeStr ： {}", parentStr);
            errorUrlMapper.insert(new ErrorUrl().setUrl(page.getRequest().getUrl()).setFlag(0));
        }
    }

    /**
     * 生成区域code
     *
     * @param provinceUrls
     * @return
     */
    private List<Integer> generateAreaCode(List<String> provinceUrls) {
        ArrayList<Integer> areaCodes = new ArrayList<>();
        for (String url : provinceUrls) {
            String codeStr = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
            Integer code = fillZero(codeStr);
            areaCodes.add(code);
        }
        return areaCodes;
    }

    /**
     * 补0
     *
     * @param code
     * @return
     */
    private Integer fillZero(String code) {
        StringBuilder builder = new StringBuilder(code);
        for (int i = 0; i < 6 - code.length(); i++) {
            builder.append("0");
        }
        return Integer.valueOf(builder.toString());
    }

    /**
     * 处理新增操作
     *
     * @param areaNames
     * @param areaCode
     * @param level
     * @param parentId
     */
    private void dealInsert(List<String> areaNames, List<Integer> areaCode, Integer level, Integer parentId) {
        LinkedList<SysArea> areas = new LinkedList<>();
        for (int i = 0; i < areaNames.size(); i++) {
            try {
                SysArea sysArea = new SysArea().setSysAreaId(areaCode.get(i))
                        .setSysAreaName(areaNames.get(i))
                        .setSysAreaPid(parentId)
                        .setSysAreaType(level);
                areas.add(sysArea);
            } catch (IndexOutOfBoundsException e) {
                log.info("区域 ：{}, code :{}", areaNames, areaCode);
            }
        }
        executor.executeInsertArea(areas);
    }

}
