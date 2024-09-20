package com.wc.single.sky.common;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
//import com.lenovo.qdp.domain.ImportResult;

import java.util.List;

public class ImportResultUtil<T> {

    public int errorCount(List<T> list) {
        int errorCount = 0;
        for (T item : list) {
            if (null == item) {
                continue;
            }
            String errorMsg = ReflectUtil.getFieldValue(item, "errorMsg") == null ? "" : ReflectUtil.getFieldValue(item, "errorMsg").toString();
            if (StrUtil.isNotBlank(errorMsg)) {
                errorCount++;
            }
        }
        return errorCount;
    }

//    public ImportResult<List<T>> getErrorImportResult(List<T> list, String url) {
//        ImportResult<List<T>> importResult = new  ImportResult<>(false, "Error：Pls download to check the errors ", list);
//        importResult.setErrorTotal(errorCount(list));
//        importResult.setUrl(url);
//        return importResult;
//    }
}
