var date = getFormatDate(new Date());


$(function () {
    //选中top_li
    $('#video_type_max_count_li').attr('class', 'nav_active');
    init();
})

/**
 * 初始化各分类最高收视排行
 */
function init() {

    let video_type_max_option = {
        color: ['#5bc0de'],
        textStyle: {
            color: '#fff'
        },
        title: {
            text: '各分类最高收视排行',
            subtext: '数据来自B站',
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
            formatter: function (params) {
                //console.log(params);
                let list = []
                let listItem = ''
                for (var i = 0; i < params.length; i++) {
                    if (params[i].seriesName == '该分类视频最高播放量') {
                        let maxPlayCount = params[i].value;
                        if (maxPlayCount > 10000) {
                            maxPlayCount = Math.round(maxPlayCount / 1000) / 10;
                            maxPlayCount = maxPlayCount + '万';
                        }
                        list.push(
                            params[i].name +
                            '<br/>' +
                            '<i style="display: inline-block;width: 10px;height: 10px;background: ' +
                            params[i].color +
                            ';margin-right: 5px;border-radius: 50%;"></i><span style="display:inline-block;">' +
                            params[i].seriesName + ' : ' +
                            maxPlayCount +
                            '</span>'
                        );
                    } else {
                        list.push(
                            '<i style="display: inline-block;width: 10px;height: 10px;background: ' +
                            params[i].color +
                            ';margin-right: 5px;border-radius: 50%;}"></i><span style="display:inline-block;">' +
                            params[i].seriesName + ' : ' +
                            params[i].value +
                            '</span>');
                    }
                }
                listItem = list.join('<br>')
                return '<div class="showBox">' + listItem + '</div>'
            }
        },
        legend: {
            data: ['该分类视频最高播放量'],
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
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            data: []
        },
        series: [
            {
                name: '该分类视频最高播放量',
                type: 'bar',
                data: []
            },
            {
                name: '视频名字',
                type: 'line',
                data: []
            }
        ]
    };

    $.ajax({
        //请求方式
        type: "GET",
        //请求的媒体类型
        contentType: "application/json;charset=UTF-8",
        //希望返回的类型
        dataType: "json",
        //请求地址
        url: "/video_type_max_play_count/queryAll",
        //data : JSON.stringify(list),
        success: function (result) {
            console.log(result);
            let rows = result.rows;
            video_type_max_option.xAxis.data = [];
            rows.forEach(row => {
                //视频名字
                let videoName = row.videoName;
                //视频类型
                let videoTypeName = row.videoTypeName;
                //播放量
                let maxPlayCount = row.maxPlayCount;

                video_type_max_option.yAxis.data.push(videoTypeName);
                video_type_max_option.series[0].data.push(maxPlayCount);
                video_type_max_option.series[1].data.push(videoName);
            });
            console.log(video_type_max_option);
            //视频top10收视排行
            var video_top_rank = echarts.init(document.getElementById('video_type_max_count'));
            video_top_rank.setOption(video_type_max_option);
        },
        error: function (e) {
            console.log('error:' + e);
        }
    });

}
