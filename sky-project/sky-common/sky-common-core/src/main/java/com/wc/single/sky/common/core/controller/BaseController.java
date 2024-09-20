package com.wc.single.sky.common.core.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wc.single.sky.common.constant.Constants;
import com.wc.single.sky.common.core.domain.AjaxResult;
import com.wc.single.sky.common.core.domain.R;
import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.common.core.page.PageDomain;
import com.wc.single.sky.common.core.page.TableDataInfo;
import com.wc.single.sky.common.core.page.TableSupport;
import com.wc.single.sky.common.utils.ServletUtils;
import com.wc.single.sky.common.utils.sql.SqlUtil;
import com.wc.single.sky.common.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * web层通用数据处理
 *
 * @author lovefamily
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(StringUtils.isEmpty(text) ? null : text));
            }
        });
    }

    /**
     * 设置请求分页数据
     *
     * @return
     */
    protected Page<Object> startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (null == pageNum) {
            pageNum = Constants.DEFAULT_PAGE_NUM;
        }
        if (null == pageSize) {
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        return PageHelper.startPage(pageNum, pageSize, orderBy);
    }

    /**
     * 获取request
     */
    public HttpServletRequest getRequest() {
        return ServletUtils.getRequest();
    }

    /**
     * 获取response
     */
    public HttpServletResponse getResponse() {
        return ServletUtils.getResponse();
    }

    /**
     * 获取session
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }

    public long getCurrentUserId() {
        String currentId = getRequest().getHeader(Constants.CURRENT_ID);
        if (StringUtils.isNotBlank(currentId)) {
            return Long.parseLong(currentId);
        }
        return 0L;
    }

    public String getToken() {
        return getRequest().getHeader(Constants.TOKEN);

    }

    public String getLoginName() {
        logger.info("用户ITCode" + getRequest().getHeader(Constants.CURRENT_USERNAME));
        return getRequest().getHeader(Constants.CURRENT_USERNAME);
    }

    /**
     * 响应请求分页数据 第一个参数：返回的数据，第二个参数是返回的总数
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?>... list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list[0]);
        if (list.length > 1) {
            PageInfo<?> of = PageInfo.of(list[1]);
            rspData.setTotal(of.getTotal());
        } else {
            rspData.setTotal(new PageInfo(list[0]).getTotal());
        }
        return rspData;
    }

    protected TableDataInfo getDataTable(List<?> list, long total) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setTotal(total);
        return rspData;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected R result(List<?> list) {
        PageInfo<?> pageInfo = new PageInfo(list);
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("rows", list);
        m.put("pageNum", pageInfo.getPageNum());
        m.put("total", pageInfo.getTotal());
        return R.ok(m);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected R toAjax(int rows) {
        return rows > 0 ? R.ok() : R.error();
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected ResultDTO<Integer> toResultDTO(int rows, boolean isNew) {
        return rows > 0 ? ResultDTO.success(rows) : ResultDTO.fail(rows);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows, boolean isNew) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected R toAjax(boolean result) {
        return result ? R.ok() : R.error();
    }

    protected R resultOne(Object obj) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("data", obj);
        return R.ok(m);
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected AjaxResult toAjax(boolean result, boolean isNew) {
        return result ? AjaxResult.success() : AjaxResult.error();
    }


    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(PageInfo pageInfo) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(pageInfo.getList());
        rspData.setTotal(pageInfo.getTotal());
        return rspData;
    }

    protected TableDataInfo getDataDefaultTable() {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(null);
        rspData.setTotal(0L);
        return rspData;
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected ResultDTO<Boolean> handleResultData(boolean result) {
        return result ? ResultDTO.success(true) : ResultDTO.fail(false);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected ResultDTO<Integer> handleResultData(int rows) {
        return rows > 0 ? ResultDTO.success(rows) : ResultDTO.fail(rows);
    }

    /**
     * 响应返回结果
     *
     * @param data 结果数据
     * @return 操作结果
     */
    protected <T> ResultDTO<T> handleResultData(T data) {
        return ResultDTO.success(data);
    }

    protected R result(PageInfo pageInfo) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("rows", pageInfo.getList());
        m.put("pageNum", pageInfo.getPageNum());
        m.put("total", pageInfo.getTotal());
        return R.ok(m);
    }
}
