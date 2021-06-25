package com.chqiuu.proxy.config.converter;

import cn.hutool.core.date.DateUtil;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * Controller中接收时间格式参数
 *
 * @author CHENQUAN
 * @date 2017-06-20
 */
public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String stringDate) {
        return DateUtil.parse(stringDate);
    }
}