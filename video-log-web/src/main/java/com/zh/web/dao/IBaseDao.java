package com.zh.web.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/2/25 13:34
 * @Description
 */
public interface IBaseDao<T> extends Mapper<T>, MySqlMapper<T> {
}
