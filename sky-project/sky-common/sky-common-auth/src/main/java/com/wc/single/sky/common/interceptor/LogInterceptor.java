package com.wc.single.sky.common.interceptor;

import com.wc.single.sky.common.constant.Constants;
import com.wc.single.sky.common.utils.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果有上层调用就用上层的ID
        String traceId = request.getHeader(Constants.TRACE_ID);
        if (traceId == null) {
            traceId = StringUtils.getNextRandomId();
        }
        MDC.put(Constants.TRACE_ID, traceId);

        return true;
    }

    /**
     * *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws UnsupportedOperationException
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws UnsupportedOperationException {
        //暂时未使用
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        MDC.remove(Constants.TRACE_ID);
    }
}