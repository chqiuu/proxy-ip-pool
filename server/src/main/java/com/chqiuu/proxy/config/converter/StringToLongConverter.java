package com.chqiuu.proxy.config.converter;


import cn.hutool.core.util.StrUtil;
import org.springframework.core.convert.converter.Converter;


/**
 * Controller中接收Long格式参数
 *
 * @author CHENQUAN
 * @date 2018年6月21日
 */
public class StringToLongConverter implements Converter<String, Long> {

    @Override
    public Long convert(String stringLong) {
        try {
            if (StrUtil.isNotBlank(stringLong)) {
                return Long.parseLong(stringLong);
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            //  e.printStackTrace();
        }
        return null;
    }
}