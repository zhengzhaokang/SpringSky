package com.wc.single.sky.common.utils.wso2;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "wso2")
@Data
public class Wso2UtilProperties {

    private Map<String, Wso2UtilProperties.AccountInfo> maps = new HashMap<>();

    @Data
    public static class AccountInfo {
        private String url;
        private String method;
        private String consumerKey;
        private String consumerSecret;
    }
}
