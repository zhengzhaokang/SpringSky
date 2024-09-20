package com.wc.single.sky.common.utils;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wc.single.sky.common.core.text.Convert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 客户端工具类
 * 
 * @author lovefamily
 */
public class ServletUtils
{
    private ServletUtils() {}
    /**
     * 获取String参数
     */
    public static String getParameter(String name)
    {
        HttpServletRequest request = getRequest();
        if (request != null) {
            return request.getParameter(name);
        }
        return null;
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name, String defaultValue)
    {
        HttpServletRequest request = getRequest();
        if(request != null) {
            return Convert.toStr(request.getParameter(name), defaultValue);
        }
        return null;
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name)
    {
        HttpServletRequest request = getRequest();
        if(request!=null){
            return Convert.toInt(request.getParameter(name));
        }
        return null;
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name, Integer defaultValue)
    {
        HttpServletRequest request = getRequest();
        if(request != null){
            return Convert.toInt(request.getParameter(name), defaultValue);
        }
        return null;
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest()
    {
        ServletRequestAttributes requestAttributes = getRequestAttributes();
        if(requestAttributes != null){
            return requestAttributes.getRequest();
        }
        return null;
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse()
    {
        ServletRequestAttributes requestAttributes = getRequestAttributes();
        if(requestAttributes != null){
            return requestAttributes.getResponse();
        }
        return null;
    }

    /**
     * 获取session
     */
    public static HttpSession getSession()
    {
        HttpServletRequest request = getRequest();
        if(request != null){
            return request.getSession();
        }
        return null;
    }

    public static ServletRequestAttributes getRequestAttributes()
    {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 将字符串渲染到客户端
     * 
     * @param response 渲染对象
     * @param string 待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string)
    {
        try
        {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否是Ajax异步请求
     * 
     * @param request 请求信息
     */
    public static boolean isAjaxRequest(HttpServletRequest request)
    {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json"))
        {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest"))
        {
            return true;
        }

        String uri = request.getRequestURI();
        if (StringUtils.inStringIgnoreCase(uri, ".json", ".xml"))
        {
            return true;
        }

        String ajax = request.getParameter("__ajax");
        if (StringUtils.inStringIgnoreCase(ajax, "json", "xml"))
        {
            return true;
        }
        return false;
    }
}
