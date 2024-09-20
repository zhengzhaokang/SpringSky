package com.wc.single.sky.common.core.domain;

import lombok.Data;

/**
 * @author qiaocan
 */
@Data
public class AsyncTaskPageDTO extends BaseDTO {

    private Integer pageNum;
    private Integer pageSize;
}
