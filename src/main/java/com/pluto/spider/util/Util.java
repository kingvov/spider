package com.pluto.spider.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工具类
 */
public class Util {

    public static final String CN_FORMAT_PATTERN = "yyyy年MM月dd日";

    public static final String EN_FORMAT_PATTERN = "yyyy-MM-dd";

    public static final String EN_SECONDS_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String EN_SPRIT_FORMAT_PATTERN = "yyyy/MM/dd";

    /**
     * String to BigDecimal, 当字符串为空时返回0
     *
     * @param str
     * @return
     */
    public static BigDecimal string2Decimal(String str) {
        String s = str.trim();
        if (StringUtils.isBlank(s)) {
            return new BigDecimal(0);
        }
        return new BigDecimal(s);
    }

    /**
     * String list to BigDecimal list
     * 当字符串为空时返回0
     */
    public static List<BigDecimal> string2Decimal(List<String> list) {
        List<BigDecimal> dList = new ArrayList<>();
        for (String str : list) {
            if (str == null || StringUtils.isBlank(str)) {
                dList.add(new BigDecimal(0));
            } else {
                try {
                    dList.add(new BigDecimal(str.trim()));
                } catch (NumberFormatException e) {
                    dList.add(new BigDecimal(0));
                }
            }
        }
        return dList;
    }

    /**
     * String to Date ，当字符串为空或者格式错误时，返回null
     *
     * @param dateStr
     * @param formatPattern
     * @return
     */
    public static Date stringToDate(String dateStr, String formatPattern) {
        if (dateStr == null || StringUtils.isBlank(dateStr)) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat(formatPattern);
        Date date = null;
        try {
            date = dateFormat.parse(dateStr.trim());
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    /**
     * String list to Date List
     *
     * @param dateStrList
     * @param formatPattern
     * @return
     */
    public static List<Date> stringToDate(List<String> dateStrList, String formatPattern) {
        List<Date> dateList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat(formatPattern);
        for (String str : dateStrList) {
            if (str == null || StringUtils.isBlank(str)) {
                dateList.add(null);
                continue;
            }
            Date date = null;
            try {
                date = dateFormat.parse(str.trim());
            } catch (ParseException e) {
                dateList.add(null);
                continue;
            }
            dateList.add(date);
        }
        return dateList;
    }

    /**
     * 比较两日期相差天数
     *
     * @param before
     * @param last
     * @return
     */
    public static int dateDiffer(Date before, Date last) {
        return Math.abs((int) (last.getTime() - before.getTime()) / (1000 * 3600 * 24));
    }

}
