package com.zh.spider.videodetail.entity;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/2/5 18:59
 * @Description
 * bilibili全站分类视频url列表
 */
public class Constants {

    /**
     * 分类视频url列表
     */
    public static final String[] VIDEO_CATEGORY_URL = {
            //动画--MAD·AMV
            "https://s.search.bilibili.com/cate/search?callback=callback&main_ver=v3&search_type=video&view_type=hot_rank&order=click&copy_right=-1&cate_id=24&jsonp=jsonp&_=1582032132311&time_from=20200401&time_to=20200423&pagesize=100",
            //番剧---资讯
            "https://s.search.bilibili.com/cate/search?callback=callback&main_ver=v3&search_type=video&view_type=hot_rank&order=click&copy_right=-1&cate_id=51&jsonp=jsonp&_=1582032132311&time_from=20200401&time_to=20200423&pagesize=100",
            //国创--国产原创
            "https://s.search.bilibili.com/cate/search?callback=callback&main_ver=v3&search_type=video&view_type=hot_rank&order=click&copy_right=-1&cate_id=168&jsonp=jsonp&_=1582032132311&time_from=20200401&time_to=20200423&pagesize=100",
            //音乐--翻唱
            "https://s.search.bilibili.com/cate/search?callback=callback&main_ver=v3&search_type=video&view_type=hot_rank&order=click&copy_right=-1&cate_id=31&jsonp=jsonp&_=1582032132311&time_from=20200401&time_to=20200423&pagesize=100",
            //舞蹈--明星舞蹈
            "https://s.search.bilibili.com/cate/search?callback=callback&main_ver=v3&search_type=video&view_type=hot_rank&order=click&copy_right=-1&cate_id=199&jsonp=jsonp&_=1582032132311&time_from=20200401&time_to=20200423&pagesize=100",
            //游戏--单机游戏
            "https://s.search.bilibili.com/cate/search?callback=callback&main_ver=v3&search_type=video&view_type=hot_rank&order=click&copy_right=-1&cate_id=17&jsonp=jsonp&_=1582032132311&time_from=20200401&time_to=20200423&pagesize=100",
            //科普--趣味科普人文
            "https://s.search.bilibili.com/cate/search?callback=callback&main_ver=v3&search_type=video&view_type=hot_rank&order=click&copy_right=-1&cate_id=124&jsonp=jsonp&_=1582032132311&time_from=20200401&time_to=20200423&pagesize=100",
            //数码--手机平板
            "https://s.search.bilibili.com/cate/search?callback=callback&main_ver=v3&search_type=video&view_type=hot_rank&order=click&copy_right=-1&cate_id=95&jsonp=jsonp&_=1582032132311&time_from=20200401&time_to=20200423&pagesize=100",
            //生活----搞笑
            "https://s.search.bilibili.com/cate/search?callback=callback&main_ver=v3&search_type=video&view_type=hot_rank&order=click&copy_right=-1&cate_id=138&jsonp=jsonp&_=1582032132311&time_from=20200401&time_to=20200423&pagesize=100",
            //鬼畜---鬼畜调教
            "https://s.search.bilibili.com/cate/search?callback=callback&main_ver=v3&search_type=video&view_type=hot_rank&order=click&copy_right=-1&cate_id=22&jsonp=jsonp&_=1582032132311&time_from=20200401&time_to=20200423&pagesize=100",
            //时尚--健身
            "https://s.search.bilibili.com/cate/search?callback=callback&main_ver=v3&search_type=video&view_type=hot_rank&order=click&copy_right=-1&cate_id=164&jsonp=jsonp&_=1582032132311&time_from=20200401&time_to=20200423&pagesize=100",
            //广告
            "https://s.search.bilibili.com/cate/search?callback=callback&main_ver=v3&search_type=video&view_type=hot_rank&order=click&copy_right=-1&cate_id=166&jsonp=jsonp&_=1582032132311&time_from=20200401&time_to=20200423&pagesize=100",
            //娱乐--综艺
            "https://s.search.bilibili.com/cate/search?callback=callback&main_ver=v3&search_type=video&view_type=hot_rank&order=click&copy_right=-1&cate_id=71&jsonp=jsonp&_=1582032132311&time_from=20200401&time_to=20200423&pagesize=100",
            //影视--影视杂谈
            "https://s.search.bilibili.com/cate/search?callback=callback&main_ver=v3&search_type=video&view_type=hot_rank&order=click&copy_right=-1&cate_id=182&jsonp=jsonp&_=1582032132311&time_from=20200401&time_to=20200423&pagesize=100"
    };


    /**
     * 视频详情页接口
     */
    public static final String URL_DETAIL_PREFIX = "https://api.bilibili.com/x/web-interface/view?aid=";


}
