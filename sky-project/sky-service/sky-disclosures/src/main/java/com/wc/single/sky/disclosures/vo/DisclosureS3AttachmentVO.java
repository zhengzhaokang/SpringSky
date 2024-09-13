package com.wc.single.sky.disclosures.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisclosureS3AttachmentVO {

    private Long id;

    //用于区分主文件、Supporting、file-changed等
    private String role;

    //文件key，在S3的唯一标识 命名规则：S3FileTypeEnum+雪花ID。如果疑问，咨询fulei或者wangsh
    private String fileKey;

    //原始文件名
    private String fileName;

    //所属桶名称，按模块分
    private String bucket;

    //header的ticketNumber
    private String ticketNumber;

    //按照等级查询，以/区分层级 如：xxxx/xxxx/xxxx。如果疑问，咨询fulei或者wangsh
    private String dataId;

    private String size;

    //逻辑删除标识
    private String delFlag;

    //上传人itcode
    private String createUser;

    //更新人itcode
    private String updateUser;

    //上传时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    //更新时间（即删除时间）
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    private String downloadUrl;

    private String attachmentRole;

    private String attachmentNumber;

    private String attachmentName;
}
