var date = getFormatDate(new Date());


$(function () {
    $('#video_type_count_li').attr('class', 'nav_active');
    init();
})

/**
 * 初始化各分类采集视频信息数
 */
function init() {

    let video_day_type_count_option = {
        textStyle: {
            color: '#fff'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross'
            }
        },
        grid: {
            top: '10%',
            left: '13%',
            right: '5%',
            bottom: '10%',
            containLabel: true
        },
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        legend: {
            type: 'scroll',
            orient: 'vertical',
            left: '2%',
            top: '8%',
            data: ['动画', '科技'],
            textStyle: {
                color: '#fff'
            }
        },
        xAxis: [
            {
                type: 'category',
                axisTick: {
                    alignWithLabel: true
                },
                axisLabel: {
                    interval: 0,//横轴信息全部显示
                    rotate: -30,//-15度角倾斜显示
                },
                data: ['2020-01-01', '2020-01-02', '2020-01-03', '2020-01-04', '2020-01-05', '2020-01-06', '2020-01-07', '2020-01-08', '2020-01-09', '2020-01-10', '2020-01-11', '2020-01-12']
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '采集数',
                position: 'right',
                axisLine: {
                    lineStyle: {
                        color: '#5793f3'
                    }
                },
                axisLabel: {
                    formatter: '{value} 个'
                }
            }
        ],
        dataZoom: [
            {   // 这个dataZoom组件，默认控制x轴。
                type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
                bottom: 10,
                start: 0,      // 左边在 10% 的位置。
                end: 20         // 右边在 60% 的位置。
            },
            {   // 这个dataZoom组件，也控制x轴。
                type: 'inside', // 这个 dataZoom 组件是 inside 型 dataZoom 组件
                start: 0,      // 左边在 10% 的位置。
                end: 20         // 右边在 60% 的位置。
            }
        ],
        series: [
            {
                name: '动画',
                type: 'line',
                data: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
            },
            {
                name: '科技',
                type: 'line',
                data: [1.0, 5.9, 8.0, 3.2, 2.6, 6.7, 35.6, 62.2, 2.6, 2.0, 66.4, 3.3]
            }
        ]
    };
    $.ajax({
        type: "GET",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        url: "/dayVideoTypeCount/getALLPublishDate",
        success: function (rows) {
            console.log(rows);
            video_day_type_count_option.legend.data = [];
            video_day_type_count_option.series = [];
            video_day_type_count_option.xAxis[0].data = [];
            rows.forEach(row => {
                //视频发布日期
                let publishDate = row.publishDate;
                video_day_type_count_option.xAxis[0].data.push(publishDate);
                $.ajax({
                    async: false,
                    type: "GET",
                    contentType: "application/json;charset=UTF-8",
                    dataType: "json",
                    url: "/dayVideoTypeCount/queryAllByPublishDate/" + publishDate,
                    success: function (result) {
                        let rows = result.rows;
                        if (video_day_type_count_option.series.length > 0) {
                            let videoCounts = [];
                            rows.forEach(row => {
                                //视频数
                                videoCounts.push(row.videoCount);
                            });

                            for (let i = 0; i < video_day_type_count_option.series.length; i++) {
                                video_day_type_count_option.series[i].data.push(videoCounts[i]);
                            }
                        } else {
                            rows.forEach(row => {
                                let seriesData = {
                                    name: '', type: 'line', data: [],
                                    smooth: true,
                                    lineStyle: {
                                        width: 1
                                    }
                                };
                                //视频类型名字
                                let videoTypeName = row.videoTypeName;
                                //视频数
                                let videoCount = row.videoCount;
                                seriesData.name = videoTypeName;
                                seriesData.data.push(videoCount);
                                video_day_type_count_option.legend.data.push(videoTypeName);
                                video_day_type_count_option.series.push(seriesData);
                            });
                        }

                    }
                });
            });
            console.log(video_day_type_count_option);
            //获取每天 各分类视频信息采集概况
            let video_day_type_count = echarts.init(document.getElementById('video_day_type_count'));
            video_day_type_count.setOption(video_day_type_count_option);
        }
    });

};