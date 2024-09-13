package com.wc.single.sky.system.domain;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * 测试：测试动态展示字段对象 view_test_item
 *
 * @author sdms
 * @date 2022-02-23
 */
@Data
public class ViewTestItem extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 是否成年
     */
    @Excel(name = "是否成年")
    private Long id;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String name;

    /**
     * 主人
     */
    @Excel(name = "主人")
    private String testName;

    /**
     * 关系
     */
    @Excel(name = "关系")
    private String relation;

    /**
     * 年龄
     */
    @Excel(name = "年龄")
    private String age;

    /**
     * 生日
     */
    @Excel(name = "生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private String sex;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String describe;

    /**
     * 是否成年
     */
    @Excel(name = "是否成年")
    private String whetherAdult;

}
