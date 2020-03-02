package com.zh.web.service;

import com.zh.web.entity.DayVideoTypeCount;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/3/1 15:38
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DayVideoTypeCountServiceTest {

    @Resource
    private DayVideoTypeCountService dayVideoTypeCountServiceImpl;

    @Test
    public void getALLPublishDate(){
        List<DayVideoTypeCount> allPublishDate = dayVideoTypeCountServiceImpl.getALLPublishDate();
        log.info(allPublishDate.toString());
    }
}
