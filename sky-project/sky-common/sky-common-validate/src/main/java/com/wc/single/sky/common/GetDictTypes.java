package com.wc.single.sky.common;

import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.common.annotation.ImportValid;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;

public class GetDictTypes {

    private GetDictTypes() {
    }

    public  static  Set<String> getDictTypes(BaseImportDTO baseImportDTO) {
        Field[] fields = baseImportDTO.getClass().getDeclaredFields();
        Set<String> types = new LinkedHashSet<>();
        for (Field field : fields) {
            ImportValid validPool = field.getAnnotation(ImportValid.class);
            if (null != validPool) {
                String dicType = validPool.dicType();
                if (StrUtil.isNotBlank(dicType)) {
                    types.add(dicType);
                }
            }
        }
        return types;
    }
}
