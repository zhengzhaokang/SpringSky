package com.wc.single.sky.disclosures.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "disclosure_item_attachment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisclosureItemAttachmentDto {

    @Id
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

    private Date createDate;

    private String serialNo;

    private String batchNo;
}
