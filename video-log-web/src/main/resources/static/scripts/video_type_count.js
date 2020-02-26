var date = getFormatDate(new Date());


$(function () {
    //选中video_type_count_li
    $('#video_type_count_li').attr('class', 'nav_active');
    init();
})

/**
 * 初始化各分类采集视频信息数
 */
function init() {

    let video_type_count_option = {
        color: ['#5bc0de'],
        textStyle: {
            color: '#fff'
        },
        title: {
            text: '各分类视频信息采集概况',
            subtext: '数据来自B站,采集日期 : ' + date,
            textStyle: {
                color: '#ff6100'
            },
            subtextStyle: {
                color: '#5bc0de'
            }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            },
        },
        legend: {
            data: ['采集数'],
            textStyle: {
                color: '#fff'
            }
        },
        grid: {
            left: '5%',
            right: '5%',
            bottom: '5%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                axisTick: {
                    alignWithLabel: true
                },
                axisLabel:{
                    interval:0,//横轴信息全部显示
                    rotate:-15,//-15度角倾斜显示
                }
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '采集数',
                type: 'bar',
                barWidth: '60%',
                data: []
            }
        ]
    }

    $.ajax({
        //请求方式
        type: "GET",
        //请求的媒体类型
        contentType: "application/json;charset=UTF-8",
        //希望返回的类型
        dataType: "json",
        //请求地址
        url: "/dwVideoTypeCount/queryAll",
        success: function (result) {
            console.log(result);
            let rows = result.rows;
            video_type_count_option.xAxis[0].data = [];
            rows.forEach(row => {
                //视频类型名字
                let videoTypeName = row.videoTypeName;
                //视频数
                let videoCount = row.videoCount;
                /* if (playCount > 10000) {
                     playCount = Math.round(playCount / 1000) / 10;
                 }*/
                video_type_count_option.xAxis[0].data.push(videoTypeName);
                video_type_count_option.series[0].data.push(videoCount);
            });
            console.log(video_type_count_option);
            //各分类视频信息采集概况
            var video_top_rank = echarts.init(document.getElementById('video_type_count'));
            video_top_rank.setOption(video_type_count_option);
        },
        error: function (e) {
            console.log('error:' + e);
        }
    });
};