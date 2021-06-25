package com.chqiuu.proxy.config.converter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

/**
 * @author chqiu
 */
public class StringToMapConverter implements Converter<String, Map> {

    @Override
    public Map convert(String stringMap) {
        return (Map) JSONObject.parse(stringMap);
    }
}