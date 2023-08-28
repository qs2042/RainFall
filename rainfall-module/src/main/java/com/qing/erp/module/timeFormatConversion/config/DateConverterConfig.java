package com.qing.erp.module.timeFormatConversion.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 转换器
 * 将controller接收到的, 参数格式为字符串的日期转为日期类
 */
@Configuration
@Slf4j
public class DateConverterConfig implements Converter<String, Date> {

    private static final List<String> FORMATTER_LIST = new ArrayList<>(4);

    // 只会转换符合这里格式的日期
    static {
        FORMATTER_LIST.add("yyyy-MM");
        FORMATTER_LIST.add("yyyy-MM-dd");
        FORMATTER_LIST.add("yyyy-MM-dd hh:mm");
        FORMATTER_LIST.add("yyyy-MM-dd hh:mm:ss");
    }

    @Override
    public Date convert(String source) {
        String value = source.trim();
        if ("".equals(value)) {
            return null;
        }
        if (source.matches("^\\d{4}-\\d{1,2}$")) {
            return parseDate(source, FORMATTER_LIST.get(0), 0);
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return parseDate(source, FORMATTER_LIST.get(1), 0);
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            return parseDate(source, FORMATTER_LIST.get(2), 0);
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return parseDate(source, FORMATTER_LIST.get(3), 0);
        } else if (source.matches("^(?i)\\w{3},\\s\\d{1,2}\\s\\w{3}\\s\\d{4}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\sGMT$")) {
            return parseDate(source, FORMATTER_LIST.get(3), 1);
        } else {
            log.error("日期解析出错");
        }
        return null;
    }

    /**
     * 日期转换方法
     *
     * @param dateStr   日期字符串
     * @param formatter 日期格式
     * @return 转换后的Date类型日期
     */
    public Date parseDate(String dateStr, String formatter, int mode) {
        Date date = null;
        try {
            DateFormat dateFormat = mode == 0 ?
                    new SimpleDateFormat(formatter)
                    :
                    new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

}
