package com.wc.single.sky.masterdata.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * BizBaseSelfInvoice对象 biz_base_self_invoice
 * 
 * @author xiaozy8
 * @date 2022-09-28
 */
@Data
public class BizBaseSelfInvoiceDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Excel(name = "ID")
    private Long id;

    /** Seller Country */
    @Excel(name = "Seller Country")
    private String sellerCountry;

    /** GTN type */
    @Excel(name = "GTN Category L1")
    private String gtnCategoryL1;

    /** Partner-signed SBA */
    @Excel(name = "CRM ID")
    private String crmId;

    /** Remake */
    @Excel(name = "Remark")
    private String remark;

    @Excel(name = "Status")
    private String status;

    private Date createTime;

    /** * 创建者 */
    @Excel(name = "Applicant", width = 25)
    private String createBy;

    /** * 创建时间 */
    @Excel(name = "Application Date", width = 25, dateFormat = "MM/dd/yyyy")
    private Date createDate;

    /** * 创建时间 */
    @Excel(name = "Application Time", width = 25, dateFormat = "HH:mm:ss")
    private Date createSecond;

    private String ids;

    private List<Long> idsList;

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        this.createDate = createTime;
        this.createSecond = createTime;
    }

    public void setSellerCountry(String sellerCountry) {
        if(StringUtils.isNotBlank(sellerCountry)) {
            this.sellerCountry = sellerCountry.trim();
        }
    }

    public void setCrmId(String crmId) {
        if(StringUtils.isNotBlank(crmId)) {
            this.crmId = crmId.trim();
        }
    }

    @Override
    public void setRemark(String remark) {
        if(StringUtils.isNotBlank(remark)) {
            this.remark = remark.trim();
        }

    }
}
