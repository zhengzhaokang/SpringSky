package com.wc.single.sky.disclosures.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisclosureItemAttachmentVO {

    private Long id;

    private String module;

    private String business;

    private String comments;

    private String status;

    private String attachmentRole;

    private String attachmentNumber;

    private String attachmentName;

    private String address;

    private String size;

    private String createUser;

    private String createDate;

    private String serialNo;

    private String batchNo;
}
