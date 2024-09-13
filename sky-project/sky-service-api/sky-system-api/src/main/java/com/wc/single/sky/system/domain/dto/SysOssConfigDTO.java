package com.wc.single.sky.system.domain.dto;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * SysOssConfig对象 sys_oss_config
 * 
 * @author lovefamily
 * @date 2022-08-04
 */
public class SysOssConfigDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Excel(name = "主键")
    private Long ossConfigId;

    /** 配置key */
    @Excel(name = "配置key")
    private String configKey;

    /** accessKey */
    @Excel(name = "accessKey")
    private String accessKey;

    /** 秘钥 */
    @Excel(name = "秘钥")
    private String secretKey;

    /** 桶名称 */
    @Excel(name = "桶名称")
    private String bucketName;

    /** 前缀 */
    @Excel(name = "前缀")
    private String prefix;

    /** 访问站点 */
    @Excel(name = "访问站点")
    private String endpoint;

    /** 自定义域名 */
    @Excel(name = "自定义域名")
    private String domain;

    /** 是否https（Y=是,N=否） */
    @Excel(name = "是否https", readConverterExp = "Y==是,N=否")
    private String isHttps;

    /** 域 */
    @Excel(name = "域")
    private String region;

    /** 扩展字段 */
    @Excel(name = "扩展字段")
    private String ext1;

    /** 状态（0=正常,1=停用） */
    @Excel(name = "状态", readConverterExp = "0==正常,1=停用")
    private String status;

    public void setOssConfigId(Long ossConfigId) 
    {
        this.ossConfigId = ossConfigId;
    }

    public Long getOssConfigId() 
    {
        return ossConfigId;
    }
    public void setConfigKey(String configKey) 
    {
        this.configKey = configKey;
    }

    public String getConfigKey() 
    {
        return configKey;
    }
    public void setAccessKey(String accessKey) 
    {
        this.accessKey = accessKey;
    }

    public String getAccessKey() 
    {
        return accessKey;
    }
    public void setSecretKey(String secretKey) 
    {
        this.secretKey = secretKey;
    }

    public String getSecretKey() 
    {
        return secretKey;
    }
    public void setBucketName(String bucketName) 
    {
        this.bucketName = bucketName;
    }

    public String getBucketName() 
    {
        return bucketName;
    }
    public void setPrefix(String prefix) 
    {
        this.prefix = prefix;
    }

    public String getPrefix() 
    {
        return prefix;
    }
    public void setEndpoint(String endpoint) 
    {
        this.endpoint = endpoint;
    }

    public String getEndpoint() 
    {
        return endpoint;
    }
    public void setDomain(String domain) 
    {
        this.domain = domain;
    }

    public String getDomain() 
    {
        return domain;
    }
    public void setIsHttps(String isHttps) 
    {
        this.isHttps = isHttps;
    }

    public String getIsHttps() 
    {
        return isHttps;
    }
    public void setRegion(String region) 
    {
        this.region = region;
    }

    public String getRegion() 
    {
        return region;
    }
    public void setExt1(String ext1) 
    {
        this.ext1 = ext1;
    }

    public String getExt1() 
    {
        return ext1;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("ossConfigId", getOssConfigId())
            .append("configKey", getConfigKey())
            .append("accessKey", getAccessKey())
            .append("secretKey", getSecretKey())
            .append("bucketName", getBucketName())
            .append("prefix", getPrefix())
            .append("endpoint", getEndpoint())
            .append("domain", getDomain())
            .append("isHttps", getIsHttps())
            .append("region", getRegion())
            .append("ext1", getExt1())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
