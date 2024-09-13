package com.wc.single.sky.common.enums;

public enum BatchStatusEnum {

    /***银行返回后状态**/

    FILE_DOWNLOAD_COMPLETED("Downloaded Completed"),
    FILE_DECRYPTION_COMPLETED("Decryption Completed"),
    /**
     * 对应invoice-> Financing
     */
    SUBMITTED("Submitted"),
    /**
     * 对应invoice-> Rejected
     */
    REJECTED("Rejected"),
    /**
     * 对应invoice-> Financed
     */
    ACCEPTED("Accepted");


    public String code;

    BatchStatusEnum(String code) {
        this.code = code;
    }
}
