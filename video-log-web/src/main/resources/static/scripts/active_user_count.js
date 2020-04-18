var date = getFormatDate(new Date());


$(function () {
    $('#active_user_li').attr('class', 'nav_active');
    init();
})

/**
 * 初始化活跃用户数
 */
function init() {

    let active_user_count_option = {
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
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        legend: {
            type: 'scroll',
            data: ['日活量'],
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
                axisLine: {
                    lineStyle: {
                        color: '#5793f3'
                    }
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
                name: '日活量',
                position: 'left',
                axisLine: {
                    lineStyle: {
                        color: '#5793f3'
                    }
                },
                axisLabel: {
                    formatter: '{value}'
                }
            }
        ],
        dataZoom: [
            {   // 这个dataZoom组件，默认控制x轴。
                type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
                textStyle: {
                    color: '#fff'
                },
                bottom: 10,
                start: 0,      // 左边在 10% 的位置。
                end: 50         // 右边在 60% 的位置。
            },
            {   // 这个dataZoom组件，也控制x轴。
                type: 'inside', // 这个 dataZoom 组件是 inside 型 dataZoom 组件
                start: 0,      // 左边在 10% 的位置。
                end: 20         // 右边在 60% 的位置。
            }
        ],
        series: [
            {
                name: '日活量',
                type: 'line',
                areaStyle: {
                    color: '#5bc0de' //区域填充风格
                },
                data: [7754, 8068, 7481, 7529, 7461, 8100, 8000, 7608, 7363, 6403, 6479, 3220]
            }
        ]
    };

    $.ajax({
        type: "GET",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        url: "/activeUserCount/queryDayActiveUserCount/",
        success: function (result) {
            console.log(result);
            active_user_count_option.series[0].data = [];
            active_user_count_option.xAxis[0].data = [];
            let rows = result.rows;
            rows.forEach(row => {
                active_user_count_option.xAxis[0].data.push(row.publishDate);
                active_user_count_option.series[0].data.push(row.activeUserCount);
            });
            console.log(active_user_count_option);
            let active_user_count = echarts.init(document.getElementById('active_user_count'));
            active_user_count.setOption(active_user_count_option);
        }
    });

};