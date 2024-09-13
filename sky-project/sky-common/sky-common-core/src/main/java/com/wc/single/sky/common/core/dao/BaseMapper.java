
package com.wc.single.sky.common.core.dao;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * <p>File：BaseMapper.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * @version 1.0
 */
public interface BaseMapper<T> extends Mapper<T>, IdsMapper<T>, InsertListMapper<T>, ConditionMapper<T>
{
}