package com.wh.mcp_demo.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyConfig {
    private static final String PROXY_HOST = "127.0.0.1";
    private static final int PROXY_PORT = 10080;

    @PostConstruct
    public void setSystemProxy() {
        // 設置系統代理屬性，會影響Spring Boot 自動配置的HTTP客戶端
//        System.setProperty("http.proxyHost", PROXY_HOST);
//        System.setProperty("http.proxyPort", String.valueOf(PROXY_PORT));
//        System.setProperty("https.proxyHost", PROXY_HOST);
//        System.setProperty("https.proxyPort", String.valueOf(PROXY_PORT));

        System.out.println("System proxy configured: http://" + PROXY_HOST + ":" + PROXY_PORT);


    }
}
