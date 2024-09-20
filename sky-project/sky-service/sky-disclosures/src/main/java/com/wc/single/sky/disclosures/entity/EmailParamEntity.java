package com.wc.single.sky.disclosures.entity;

import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 邮件发送内容参数
 * @Author shirui
 * @Date 2022-11-09 10:54:02
 */
@Data
public class EmailParamEntity {

    private String service;
    private String token;
    private String subject;
    private String content;
    /**
     * 收件人列表，最大支持1000人
     */
    private List<String> to;
    /**
     * 抄送收件人列表，最大支持1000人
     */
    private List<String> cc;
    /**
     * 自定义发件人
     */
    private String from;

    private List<File> fileList;

    private String subStatus;

    private String importance; //Low,Normal,High

    private boolean hasAttachment;
    private ArrayList<String> attachmentPath;
    private ArrayList<String> attachmentNames;
    private String archiveFolderPath;
    private int attachmentCount;

    private String category;
}
