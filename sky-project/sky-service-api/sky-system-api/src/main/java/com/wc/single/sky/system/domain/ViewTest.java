package com.wc.single.sky.system.domain;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 测试：测试动态展示字段对象 view_test
 * 
 * @author sdms
 * @date 2022-02-23
 */
public class ViewTest extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Name */
    @Excel(name = "Name")
    private String name;

    /** Age */
    @Excel(name = "Age")
    private Integer age;

    /** Birthday */
    @Excel(name = "Birthday", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /** Sex */
    @Excel(name = "Sex")
    private Integer sex;

    /** School */
    @Excel(name = "School")
    private String school;

    /** Class */
    @Excel(name = "Class")
    private String class_;

    /** province */
    @Excel(name = "province")
    private String province;

    /** city */
    @Excel(name = "city")
    private String city;

    /** area */
    @Excel(name = "area")
    private String area;

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setAge(Integer age) 
    {
        this.age = age;
    }

    public Integer getAge() 
    {
        return age;
    }
    public void setBirthday(Date birthday) 
    {
        this.birthday = birthday;
    }

    public Date getBirthday() 
    {
        return birthday;
    }
    public void setSex(Integer sex) 
    {
        this.sex = sex;
    }

    public Integer getSex() 
    {
        return sex;
    }
    public void setSchool(String school) 
    {
        this.school = school;
    }

    public String getSchool() 
    {
        return school;
    }
    public void setClass(String class_)
    {
        this.class_ = class_;
    }

    public String getClass_()
    {
        return class_;
    }
    public void setProvince(String province) 
    {
        this.province = province;
    }

    public String getProvince() 
    {
        return province;
    }
    public void setCity(String city) 
    {
        this.city = city;
    }

    public String getCity() 
    {
        return city;
    }
    public void setArea(String area) 
    {
        this.area = area;
    }

    public String getArea() 
    {
        return area;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("name", getName())
            .append("age", getAge())
            .append("birthday", getBirthday())
            .append("createTime", getCreateTime())
            .append("sex", getSex())
            .append("school", getSchool())
            .append("class", getClass())
            .append("province", getProvince())
            .append("city", getCity())
            .append("area", getArea())
            .toString();
    }
}
