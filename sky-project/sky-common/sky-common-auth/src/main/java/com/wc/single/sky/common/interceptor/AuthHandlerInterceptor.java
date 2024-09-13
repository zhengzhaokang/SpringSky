package com.wc.single.sky.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.wc.single.sky.common.constant.Constants;
import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.common.utils.WebUtil;
import com.wc.single.sky.common.redis.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.MimeHeaders;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 * @description 用户登录拦截
 */
@Slf4j
@Component
public class AuthHandlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisUtils redis;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 12小时后过期
     */
    private static final long   EXPIRE        = 12L * 60L * 60L;

    private static final String ACCESS_TOKEN  = Constants.ACCESS_TOKEN;

    private static final String ACCESS_USERID = Constants.ACCESS_USERID;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String url = WebUtil.getUri(request, false);
        log.info("url:{}", url);
        String token = request.getHeader(Constants.TOKEN);


//
//        //增加开关判断
//        if (sw) {
//            if (StringUtils.isBlank(token)){
//                log.error("token can't null or empty string");
//                response.sendRedirect(request.getContextPath() + "/adfs/index");
//                return false;
//            }
//            String userStr = ops.get(Constants.ACCESS_TOKEN + token);
//            if (StringUtils.isBlank(userStr)) {
//                log.error("token verify error, access_token_---> {}", Constants.ACCESS_TOKEN + token);
//                response.sendRedirect(request.getContextPath() + "/adfs/index");
//                return false;
//            }
//            JSONObject jo = JSONObject.parseObject(userStr);
//            String userId = jo.getString("userId");
//            // 查询token信息
//            if (StringUtils.isBlank(userId)) {
//                log.error("token verify error, userId ---> {}", userId);
//                response.sendRedirect(request.getContextPath() + "/adfs/index");
//                return false;
//            }
//
//            // 设置userId到request里，后续根据userId，获取用户信息
//            if(request instanceof RequestFacade) {
//                MimeHeaders mimeHeaders = reflect2GetHeader(request);
//                handleRequestHeaderMap(mimeHeaders,Constants.CURRENT_ID, userId);
//                handleRequestHeaderMap(mimeHeaders,Constants.CURRENT_USERNAME, jo.getString("loginName"));
//            }
//            return super.preHandle(request, response, handler);
//        }

        // token为空
        if (StringUtils.isBlank(token)) {
            setUnauthorizedResponse(response, "token can't null or empty string");
            return false;
        }

        RBucket<String> rBucket = redissonClient.getBucket(Constants.ACCESS_TOKEN + token, StringCodec.INSTANCE);
        if (rBucket == null || StringUtils.isBlank(rBucket.get())) {
            setUnauthorizedResponse(response, "token verify error");
            return false;
        }
        JSONObject jo = JSONObject.parseObject(rBucket.get(), Feature.IgnoreAutoType);
        String userId = jo.getString("userId");
        // 查询token信息
        if (StringUtils.isBlank(userId)) {
            setUnauthorizedResponse(response, "token verify error");
            return false;
        }


//        String userStr = ops.get(Constants.ACCESS_TOKEN + token);
//        if (StringUtils.isBlank(userStr)) {
//            setUnauthorizedResponse(response, "token verify error");
//            return false;
//        }
//        JSONObject jo = JSONObject.parseObject(userStr);
//        String userId = jo.getString("userId");
//        // 查询token信息
//        if (StringUtils.isBlank(userId)) {
//            setUnauthorizedResponse(response, "token verify error");
//            return false;
//        }

        //登录续期
        refreshToken(token, userId);

        // 设置userId到request里，后续根据userId，获取用户信息
        if (request instanceof RequestFacade) {
            MimeHeaders mimeHeaders = reflect2GetHeader(request);
            handleRequestHeaderMap(mimeHeaders, Constants.CURRENT_ID, userId);
            handleRequestHeaderMap(mimeHeaders, Constants.CURRENT_USERNAME, jo.getString("loginName"));
        }
        return super.preHandle(request, response, handler);
    }

    private void refreshToken(String token, String userId) {
        log.info("### AuthHandlerInterceptor refreshToken token");
        redisTemplate.expire(ACCESS_TOKEN + token, EXPIRE, TimeUnit.SECONDS);
        redisTemplate.expire(ACCESS_USERID + userId, EXPIRE, TimeUnit.SECONDS);
    }

    private void setUnauthorizedResponse(HttpServletResponse response, String msg) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.addHeader("Content-Type", "application/json;charset=UTF-8");
        String responseInfo = JSON.toJSONString(ResultDTO.fail(401, msg));
        response.getWriter().write(responseInfo);
    }

    /**
     * 获取request的header信息
     *
     * @param request
     */
    private MimeHeaders reflect2GetHeader(HttpServletRequest request) throws IllegalAccessException {
        // 从 RequestFacade 中获取 org.apache.catalina.connector.Request
        Field connectorField = ReflectionUtils.findField(RequestFacade.class, "request", Request.class);
        connectorField.setAccessible(true);
        Request connectorRequest = (Request) connectorField.get(request);

        // 从 org.apache.catalina.connector.Request 中获取 org.apache.coyote.Request
        Field coyoteField = ReflectionUtils.findField(Request.class, "coyoteRequest", org.apache.coyote.Request.class);
        coyoteField.setAccessible(true);
        org.apache.coyote.Request coyoteRequest = (org.apache.coyote.Request) coyoteField.get(connectorRequest);

        // 从 org.apache.coyote.Request 中获取 MimeHeaders
        Field mimeHeadersField = ReflectionUtils.findField(org.apache.coyote.Request.class, "headers", MimeHeaders.class);
        mimeHeadersField.setAccessible(true);
        MimeHeaders mimeHeaders = (MimeHeaders) mimeHeadersField.get(coyoteRequest);
        return mimeHeaders;
    }

    private void handleRequestHeaderMap(MimeHeaders mimeHeaders, String key, String value) {
        // 添加Header
        mimeHeaders.addValue(key).setString(value);
    }
}
