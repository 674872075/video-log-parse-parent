package com.zh.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @date 2019/11/19 17:18
 * @Description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataGridResult<T> {

    /**
     * 总记录数
     */
    private Long total;
    /**
     * 每页查询到的记录
     */
    private List<T> rows;

}
