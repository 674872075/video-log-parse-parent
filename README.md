# 基于Hadoop的视频收视率分析

​	本项目分为四个模块，分别为爬虫模块、离线数据分析模块、公共基础模块、web展示模块。首先用WebMagic爬取的B站视频数据作为数据源，构建以离线分析为基础囊括大数据主要离线技术的架构进行数据分析。针对系统的处理速度与实际生产环境的所需性，对分布式架构进行了研究。在搭建技术架构的必要基础之上采用了分布式的项目部署方式，保证在生产环境下的实际生产项目的容灾性和可扩展性，提高了系统的应用性和效率。最终完成了基于大数据技术的视频收视率分析设计与实现。具体的内容有：

（1） 采用三台虚拟机进行分布式环境的搭建，模拟生产环境

（2） 使用WebMagic爬虫技术爬取B站视频数据信息，将采集到的数据导入kafka中，使用Flume框架技术从kafka采集数据到Hdfs并将数据导入到Hive中,然后使用Hue操作Hive进行离线数据分析，最后利用sqoop导入mysql,整个流程采用azkaban进行脚本定时调度,达到系统需要具备的视频收视分析功能

（3） 大数据分析展示系统采用企业中现今流行的SpringBoot+Mybatis框架进行系统的后台服务层的搭建，系统首先需要具备用户登录注册等基础功能，前台采用Bootstrap+Echarts技术实现数据展示模块。

## 爬虫模块 

video-log-spider:进行数据爬取,爬取B站视频信息

## 离线数据分析模块

video-log-analysis:对数据进行预处理,用Hive进行离线分析

## 公共基础模块

video-log-common:封装全局异常处理和常用工具

## web展示模块

video-log-web:用于离线分析后的web展示,已完成的功能有每日采集概况、总采集概况、各分类播放排行、总播放排行、活跃用户分析、总收藏排行、总弹幕排行





