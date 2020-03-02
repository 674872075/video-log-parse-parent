package com.zh.web.service.impl;

import com.zh.common.dto.DataGridResult;
import com.zh.web.dao.DayVideoTypeCountMapper;
import com.zh.web.entity.DayVideoTypeCount;
import com.zh.web.service.DayVideoTypeCountService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/3/1 15:32
 * @Description
 */
@Service
public class DayVideoTypeCountServiceImpl implements DayVideoTypeCountService {

    @Resource
    private DayVideoTypeCountMapper dayVideoTypeCountMapper;

    @Override
    public List<DayVideoTypeCount> getALLPublishDate() {
        Example example=new Example(DayVideoTypeCount.class,false);
        example.setDistinct(true);
        example.selectProperties("publishDate");
        example.setOrderByClause("publish_date asc");
        return dayVideoTypeCountMapper.selectByExample(example);
    }

    @Override
    public DataGridResult<DayVideoTypeCount> queryAllByPublishDate(String publishDate) {
        Example example=new Example(DayVideoTypeCount.class,false);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("publishDate",publishDate);
        List<DayVideoTypeCount> dayVideoTypeCounts = dayVideoTypeCountMapper.selectByExample(example);
        return new DataGridResult<DayVideoTypeCount>(Long.valueOf(dayVideoTypeCounts.size()),dayVideoTypeCounts);
    }

}
