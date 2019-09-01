package com.hert.gateway.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求地址白名单,无需校验token
 *
 */
@Configuration
public class UrlWhileList implements InitializingBean {

    private final static List<String> URL_LIST = new ArrayList<String>();

    @Override
    public void afterPropertiesSet() throws Exception {
        URL_LIST.add("/login/**");
        URL_LIST.add("/checkLogin/**");
        URL_LIST.add("/logout/**");
    }

    public static List<String> getUrlList() {
        return URL_LIST;
    }
}
