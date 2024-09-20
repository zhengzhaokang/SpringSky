package com.wc.single.sky.common.utils.wso2;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * 如果一个系统多个不同账号，这种情况需要修改参数结构
 *
 * @author qiaodf2
 */
@Log4j2
@Component
public class Wso2Util {
    @Autowired
    private Wso2UtilProperties wso2UtilProperties;

    /**
     * 通过wso2方式调用需要提前获取token
     *
     * @return 通过接口得到的token值
     */
    public String getToken(String type) {

        RestTemplate restTemplate = new RestTemplate();
        String grantTypePrefix = "Basic ";
        ResponseEntity<String> responseEntity;

        try {
            Wso2UtilProperties.AccountInfo accountInfo = wso2UtilProperties.getMaps().get(type);
            String grantType = grantTypePrefix + java.util.Base64.getEncoder().encodeToString((accountInfo.getConsumerKey() + ":" + accountInfo.getConsumerSecret()).
                    getBytes(StandardCharsets.UTF_8));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("Authorization", grantType);

            LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("grant_type", "client_credentials");
            log.info("请求token参数：{}", JSONObject.toJSONString(headers));
            HttpEntity<LinkedMultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

            responseEntity = restTemplate.postForEntity(accountInfo.getUrl() + accountInfo.getMethod(), entity, String.class);
            String token = (String) ((JSONObject) JSONObject.toJSON(responseEntity)).getJSONObject("body").get("access_token");

            return "Bearer " + token;
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
    }

    @Data
    public static class AccountInfo {
        private String url;
        private String method;
        private String consumerKey;
        private String consumerSecret;
    }
}
