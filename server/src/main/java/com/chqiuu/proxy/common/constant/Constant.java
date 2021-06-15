package com.chqiuu.proxy.common.constant;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

import java.time.LocalDateTime;

/**
 * 系统全局变量
 *
 * @author chqiu
 */
public class Constant {

    /**
     * 系统默认值
     */
    public static class DefaultValues {
        /**
         * 系统默认密码
         */
        public static final String DEFAULT_PASSWORD = "ithe123456";
        /**
         * 加密后的默认密码
         */
        public static final String DEFAULT_PASSWORD_ENCODE = "$2a$04$1E0JRkhnCqC8GsFqAla2fOljSR2iaIpZfTWRNjCE5zlJGMmZflh5S";
    }

    /**
     * 系统全局变量
     */
    public static class Public {
        /**
         * 设备验证码缓存前缀
         */
        public final static String VERIFICATION_CODE_REDIS_PREFIX = "proxy-vc:";
        /**
         * 微信二维码登录唯一值
         */
        public final static String WECHAT_LOGIN_QR_CODE_REDIS_PREFIX = "proxy-login:";
    }

    /**
     * Session 属性名公共变量
     */
    public static class SessionAttr {

        /**
         * 当前登陆用户信息
         */
        public static final String INFO = "INFO";

        public SessionAttr() {
        }
    }

    /**
     * 资源文件路径
     */
    public static class ResourcePath {
        /**
         * 静态资源跟路径（不带resource）
         */
        public static String LOCAL_RESOURCE_PATH;
        /**
         * 上传文件临时存储路径
         */
        public static String FILE_DIR = "";
        /**
         * 临时文件存储路径
         */
        public static String FILE_DIR_TMP = "";

        public ResourcePath() {
        }
    }
}
