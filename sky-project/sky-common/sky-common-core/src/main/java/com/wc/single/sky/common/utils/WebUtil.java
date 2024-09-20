package com.wc.single.sky.common.utils;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author guowb1
 * @description TODO
 * @date 2022/6/26 9:45
 */
public class WebUtil extends WebUtils {

    public static String getUri(HttpServletRequest request, boolean withParams) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        if (StringUtils.isNotBlank(contextPath) && servletPath.startsWith(contextPath))
            servletPath.replaceFirst(contextPath, "");
        String pathInfo = request.getPathInfo();
        if (StringUtils.isBlank(pathInfo)) {
            pathInfo = "";
        }
        String uri = servletPath + pathInfo;
        if (!withParams)
            return uri;
        StringBuilder builder = new StringBuilder(uri).append("?");
        Map<String, String[]> params = request.getParameterMap();
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            if (values == null || values.length <= 0)
                continue;
            for (String value : values) {
                builder.append(key).append("=").append(value).append("&");
            }
        }
        return builder.substring(0, builder.length() - 1).toString();
    }
}
