package com.chqiuu.proxy.config.converter;

import cn.hutool.core.util.StrUtil;
import org.springframework.core.convert.converter.Converter;

/**
 * Controller中接收Long格式参数
 *
 * @author CHENQUAN
 * @date 2018年6月21日
 */
public class StringToIntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String stringInteger) {
        try {
            if (StrUtil.isNotBlank(stringInteger)) {
                return Integer.parseInt(stringInteger);
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            //  e.printStackTrace();
        }
        return null;
    }
}