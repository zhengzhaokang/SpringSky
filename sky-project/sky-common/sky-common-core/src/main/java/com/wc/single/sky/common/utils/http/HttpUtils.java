package com.wc.single.sky.common.utils.http;

import com.wc.single.sky.common.interceptor.HttpClientTraceIdInterceptor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;

/**
 * 通用http发送方法
 *
 * @author lovefamily
 */
public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 字符编码
     */
    private final static String UTF8 = "utf-8";
    /**
     * 字节流数组大小（1MB）
     */
    private final static int BYTE_ARRAY_LENGTH = 1024 * 1024;

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            log.info("sendGet - {}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("recv - {}", result);
        } catch (ConnectException e) {
            log.error("调用HttpUtils.sendGet ConnectException, url=" + url + ",param=" + param, e);
        } catch (SocketTimeoutException e) {
            log.error("调用HttpUtils.sendGet SocketTimeoutException, url=" + url + ",param=" + param, e);
        } catch (IOException e) {
            log.error("调用HttpUtils.sendGet IOException, url=" + url + ",param=" + param, e);
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendGet Exception, url=" + url + ",param=" + param, e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
            }
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            String urlNameString = url + "?" + param;
            log.info("sendPost - {}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("recv - {}", result);
        } catch (ConnectException e) {
            log.error("调用HttpUtils.sendPost ConnectException, url=" + url + ",param=" + param, e);
        } catch (SocketTimeoutException e) {
            log.error("调用HttpUtils.sendPost SocketTimeoutException, url=" + url + ",param=" + param, e);
        } catch (IOException e) {
            log.error("调用HttpUtils.sendPost IOException, url=" + url + ",param=" + param, e);
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendPost Exception, url=" + url + ",param=" + param, e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
            }
        }
        return result.toString();
    }

    public static String sendSSLPost(String url, String param) {
        StringBuilder result = new StringBuilder();
        String urlNameString = url + "?" + param;
        try {
            log.info("sendSSLPost - {}", urlNameString);
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            URL console = new URL(urlNameString);
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String ret = "";
            while ((ret = br.readLine()) != null) {
                if (ret != null && !ret.trim().equals("")) {
                    result.append(new String(ret.getBytes("ISO-8859-1"), "utf-8"));
                }
            }
            log.info("recv - {}", result);
            conn.disconnect();
            br.close();
        } catch (ConnectException e) {
            log.error("调用HttpUtils.sendSSLPost ConnectException, url=" + url + ",param=" + param, e);
        } catch (SocketTimeoutException e) {
            log.error("调用HttpUtils.sendSSLPost SocketTimeoutException, url=" + url + ",param=" + param, e);
        } catch (IOException e) {
            log.error("调用HttpUtils.sendSSLPost IOException, url=" + url + ",param=" + param, e);
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendSSLPost Exception, url=" + url + ",param=" + param, e);
        }
        return result.toString();
    }


    /**
     * 执行get请求获取响应
     *
     * @param url 请求地址
     * @return 响应内容
     */
    public static String get(String url) {
        return get(url, null);
    }

    /**
     * 执行get请求获取响应
     *
     * @param url     请求地址
     * @param headers 请求头参数
     * @return 响应内容
     */
    public static String get(String url, Map<String, String> headers) {
        HttpGet get = new HttpGet(url);
        return getRespString(get, headers);
    }

    /**
     * 执行post请求获取响应
     *
     * @param url 请求地址
     * @return 响应内容
     */
    public static String post(String url) {
        return post(url, null, null);
    }

    /**
     * 执行post请求获取响应
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应内容
     */
    public static String post(String url, Map<String, String> params) {
        return post(url, null, params);
    }

    /**
     * 执行post请求获取响应
     *
     * @param url     请求地址
     * @param headers 请求头参数
     * @param params  请求参数
     * @return 响应内容
     */
    public static String post(String url, Map<String, String> headers, Map<String, String> params) {
        HttpPost post = new HttpPost(url);
        post.setEntity(getHttpEntity(params));
        return getRespString(post, headers);
    }

    /**
     * 执行post请求获取响应（请求体为JOSN数据）
     *
     * @param url  请求地址
     * @param json 请求的JSON数据
     * @return 响应内容
     */
    public static String postJson(String url, String json) {
        return postJson(url, null, json);
    }

    /**
     * 执行post请求获取响应（请求体为JOSN数据）
     *
     * @param url     请求地址
     * @param headers 请求头参数
     * @param json    请求的JSON数据
     * @return 响应内容
     */
    public static String postJson(String url, Map<String, String> headers, String json) {
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-type", "application/json");
        post.setEntity(new StringEntity(json, UTF8));
        return getRespString(post, headers);
    }

    /**
     * 执行post请求获取响应（请求体包含文件）
     *
     * @param url    请求地址
     * @param params 请求参数（文件对应的value传File对象）
     * @return 响应内容
     */
    public static String postFile(String url, Map<String, Object> params) {
        return postFile(url, null, params);
    }

    /**
     * 执行post请求获取响应（请求体包含文件）
     *
     * @param url     请求地址
     * @param headers 请求头参数
     * @param params  请求参数（文件对应的value传File对象）
     * @return 响应内容
     */
    public static String postFile(String url, Map<String, String> headers, Map<String, Object> params) {
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        if (Objects.nonNull(params) && !params.isEmpty()) {
            for (Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (Objects.isNull(value)) {
                    builder.addPart(key, new StringBody("", ContentType.TEXT_PLAIN));
                } else {
                    if (value instanceof File) {
                        builder.addPart(key, new FileBody((File) value));
                    } else {
                        builder.addPart(key, new StringBody(value.toString(), ContentType.TEXT_PLAIN));
                    }
                }
            }
        }
        HttpEntity entity = builder.build();
        post.setEntity(entity);
        return getRespString(post, headers);
    }

    /**
     * 下载文件
     *
     * @param url      下载地址
     * @param path     保存路径（如：D:/images，不传默认当前工程根目录）
     * @param fileName 文件名称（如：hello.jpg）
     */
    public static void download(String url, String path, String fileName) {
        HttpGet get = new HttpGet(url);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = null;
        if (Objects.isNull(path) || path.isEmpty()) {
            filePath = fileName;
        } else {
            if (path.endsWith("/")) {
                filePath = path + fileName;
            } else {
                filePath += path + "/" + fileName;
            }
        }
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileOutputStream fos = new FileOutputStream(file); InputStream in = getRespInputStream(get, null)) {
            if (Objects.isNull(in)) {
                return;
            }
            byte[] bytes = new byte[BYTE_ARRAY_LENGTH];
            int len = 0;
            while ((len = in.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取请求体HttpEntity
     *
     * @param params 请求参数
     * @return HttpEntity
     */
    private static HttpEntity getHttpEntity(Map<String, String> params) {
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        for (Entry<String, String> entry : params.entrySet()) {
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        HttpEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(pairs, UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return entity;
    }

    /**
     * 设置请求头
     *
     * @param request 请求对象
     * @param headers 请求头参数
     */
    private static void setHeaders(HttpUriRequest request, Map<String, String> headers) {
        if (Objects.nonNull(headers) && !headers.isEmpty()) {
            // 请求头不为空，则设置对应请求头
            for (Entry<String, String> entry : headers.entrySet()) {
                request.setHeader(entry.getKey(), entry.getValue());
            }
        } else {
            // 请求为空时，设置默认请求头
            request.setHeader("Connection", "keep-alive");
            request.setHeader("Accept-Encoding", "gzip, deflate, br");
            request.setHeader("Accept", "*/*");
            request.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36");
        }
    }

    /**
     * 执行请求，获取响应流
     *
     * @param request 请求对象
     * @return 响应内容
     */
    private static InputStream getRespInputStream(HttpUriRequest request, Map<String, String> headers) {
        // 设置请求头
        setHeaders(request, headers);
        // 获取响应对象
        HttpResponse response = null;
        try {
            response = HttpClients.createDefault().execute(request);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        // 获取Entity对象
        HttpEntity entity = response.getEntity();
        // 获取响应信息流
        InputStream in = null;
        if (Objects.nonNull(entity)) {
            try {
                in = entity.getContent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return in;
    }

    /**
     * 执行请求，获取响应内容
     *
     * @param request 请求对象
     * @return 响应内容
     */
    private static String getRespString(HttpUriRequest request, Map<String, String> headers) {
        byte[] bytes = new byte[BYTE_ARRAY_LENGTH];
        int len = 0;
        try (InputStream in = getRespInputStream(request, headers);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            if (Objects.isNull(in)) {
                return "";
            }
            while ((len = in.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            return bos.toString(UTF8);
        } catch (Exception e) {
            log.error("GetRespString Exception:" + e);
        }
        return "";
    }


    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    public static String doPost(String url, String jsonstr, String username, String password) {
        String result = null;
        try {
            String body = "";
            //采用绕过验证的方式处理https请求
            SSLContext sslcontext = createIgnoreVerifySSL();

            // 设置协议http和https对应的处理socket链接工厂的对象
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslcontext))
                    .build();
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            HttpClients.custom().setConnectionManager(connManager);

            //创建自定义的httpclient对象
            //CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(connManager).build();
            CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().setConnectionManager(connManager)
                    .addInterceptorFirst(new HttpClientTraceIdInterceptor())
                    .build();

            //	CloseableHttpClient client = HttpClients.createDefault();

            //创建post方式请求对象
            HttpPost httpPost = null;
            result = null;
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");

            //  basic auth认证

            httpPost.addHeader("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString((username + ":" + password).getBytes()));

            StringEntity se = new StringEntity(jsonstr);
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            httpPost.setEntity(se);
            CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
            // HttpResponse response = httpClient.execute(httpPost);

            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "utf-8");
                }
            }

            // 关闭连接
            closeableHttpClient.close();
        } catch (NoSuchAlgorithmException | KeyManagementException | IOException | ParseException e) {
            e.printStackTrace();
        }

        return result;
    }


    private static class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}