package com.zh.web;

import com.zh.web.dao.IBaseDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/2/21 20:29
 * @Description
 */
@SpringBootApplication
/*显式定义ComponentScan之后会覆盖SpringBootApplication中的ComponentScan*/
@ComponentScan("com.zh.common")
@ComponentScan("com.zh.web")
@MapperScan(basePackages = {"com.zh.web.dao"},markerInterface = IBaseDao.class)
public class VideoLogWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoLogWebApplication.class,args);
    }

}
