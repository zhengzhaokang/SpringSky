package com.wc.single.sky.disclosures.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wc.single.sky.common.exception.message.DefaultErrorMessage;
import com.wc.single.sky.disclosures.entity.EmailEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author lianght1
 * @date 2020/1/3
 */
@SuppressWarnings("deprecation")
public class EmailUtils {

    private static final Logger log = LoggerFactory.getLogger(EmailUtils.class);

    private EmailUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static final String UTF_8 = "UTF-8";

    /**
     * 通过wso2方式调用需要提前获取token
     * @return 通过接口得到的token值
     */
    public static String getToken(String basicAuthUserName, String basicAuthPassWord, String tokenHost) {

        RestTemplate restTemplate = new RestTemplate();
        String grantTypePrefix = "Basic ";
        ResponseEntity<String> responseEntity = null;

        try {
            String grantType = grantTypePrefix + java.util.Base64.getEncoder().encodeToString((basicAuthUserName + ":" + basicAuthPassWord).getBytes(StandardCharsets.UTF_8));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("Authorization", grantType);

            LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("grant_type", "client_credentials");

            HttpEntity<LinkedMultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

            responseEntity = restTemplate.postForEntity(tokenHost, entity, String.class);
            String token = (String) ((JSONObject) JSON.toJSON(responseEntity)).getJSONObject("body").get("access_token");

            return "Bearer "+token;
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 邮件发送，支持附件和文本
     * @author liquan
     * 2020-2-11 15:30:08
     * @param emailEntity
     */
    public static boolean sendEmail(EmailEntity emailEntity) throws KeyStoreException, NoSuchAlgorithmException, IOException, KeyManagementException {
        HttpPost post = null;
        CloseableHttpResponse response = null;

        try {
            if(!ObjectUtils.isEmpty(emailEntity.getEmailParamEntity().getFileList())){
                post = sendAttchment(emailEntity);
            }else {
                post = sendText(emailEntity);
            }

            //jdk1.8用此代码
            SSLContext sslContext = SSLContextBuilder.create().useProtocol(SSLConnectionSocketFactory.SSL).loadTrustMaterial((x, y) -> true).build();
            RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
            CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).setSSLContext(sslContext).build();

            response = httpClient.execute(post);
            return getEmailResponseInfo(response);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 文本发送
     * @author liquan
     * 2020-2-11 15:30:08
     * @param emailEntity
     */
    private static HttpPost sendText(EmailEntity emailEntity){
        HttpPost post = new HttpPost(emailEntity.getTextSendURL());
        post.addHeader(HttpHeaders.CONTENT_TYPE, (MediaType.APPLICATION_JSON_UTF8_VALUE));
        post.addHeader(HttpHeaders.CACHE_CONTROL, "no-cache");

        StringEntity stringEntity = new StringEntity(JSON.toJSONString(emailEntity.getEmailParamEntity()), UTF_8);
        post.setEntity(stringEntity);
        return post;
    }

    /**
     * 附件发送
     * @author liquan
     * 2020-2-11 15:30:08
     * @param emailEntity
     */
    private static HttpPost sendAttchment(EmailEntity emailEntity){
        HttpPost post = new HttpPost(emailEntity.getAttachmentSendURL());
        post.addHeader(HttpHeaders.CACHE_CONTROL, "no-cache");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setContentType(ContentType.MULTIPART_FORM_DATA.withCharset(UTF_8));
        log.debug("sendAttchment content => {}", JSON.toJSON(emailEntity.getEmailParamEntity()));
        builder.addTextBody("email", JSON.toJSONString(emailEntity.getEmailParamEntity()), ContentType.TEXT_PLAIN.withCharset(UTF_8));
        List<File> fileList = emailEntity.getEmailParamEntity().getFileList();
        for(int i=0;i<fileList.size();i++) {
            File file = fileList.get(i);
            builder.addBinaryBody("files", file, ContentType.APPLICATION_OCTET_STREAM, file.getName());
        }
        builder.addTextBody("inline", "0");
        post.setEntity(builder.build());
        return post;
    }

    /**
     * 打印返回的信息
     * @author liquan
     * 2020-2-11 15:30:08
     * @param response
     */
    private static boolean getEmailResponseInfo(CloseableHttpResponse response) {
        boolean resultVal = false;
            if (!ObjectUtils.isEmpty(response)){
                int statusCode = response.getStatusLine().getStatusCode();
                if(200 == statusCode){
                    resultVal = true;
                }
                if(400==statusCode){
                    log.error("### EmailUtils getEmailResponseInfo 400 error => {}", DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EMAIL_SEND_FAIL));
                }
            }
            StringBuilder entityStringBuilder = new StringBuilder();
            try (BufferedReader bufferedReader =
                         new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8), 8 * 1024)) { // no need to name intermediate resources if you don't want to

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    entityStringBuilder.append(line);
                }
                JSONObject resultJsonObject = JSON.parseObject(entityStringBuilder.toString());
                log.info("resultJsonObject:{}",resultJsonObject);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("getResponseInfo exception => ", e);
            }

        return resultVal;
    }

}
