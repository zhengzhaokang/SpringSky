package com.wc.single.sky.common;

import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * 解析Excel的实体需继承此类
 * @author daihuaicai
 */
@Data
public class BaseImportDTO extends BaseDTO {
    /**
     * 错误信息
     */
    protected String errorMsg;

    private Long pageKey;


    public  String getAmount(BaseImportDTO baseImportDTO,String filedName,String amountT) {
        String amount = "";
        if (StrUtil.isNotBlank(amountT) && amountT.contains(",")) {
            try {
                String doubleValue
                        = new DecimalFormat().parse(amountT).toString();
                amount = new BigDecimal(doubleValue).toString();
            } catch (ParseException e) {
                ValidField.setErrorMsg(baseImportDTO, ValidField.buildReturnStr(filedName, "is not input in the correct format"));
            }
        }
        return amount;
    }

    public void appendErrorMsg(String errorMsg) {
        if (StrUtil.isNotBlank(this.errorMsg)) {
            if (!this.errorMsg.contains(errorMsg)) {
                this.errorMsg = this.errorMsg + ";" + errorMsg;
            }
        }else{
            this.errorMsg = errorMsg;
        }

    }

    public boolean hasError(String excelName) {
        if (StrUtil.isBlank(this.errorMsg)) {
            return Boolean.FALSE;
        }
        return this.errorMsg.contains(excelName);
    }
}
