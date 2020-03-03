var date = getFormatDate(new Date());


$(function () {
    $('#top_play_li').attr('class', 'nav_active');
    init();
})

/**
 * 初始化top10排行榜
 */
function init() {

    let video_top_rank_option = {
        color: ['#5bc0de'],
        textStyle: {
            color: '#fff'
        },
        title: {
            text: '视频top10播放排行',
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
                    let videoName = params[i].name;
                    if (String(videoName).length > 25) {
                        let index = Math.ceil(String(videoName).length / 2);
                        params[i].name = String(videoName).substr(0, index) + "<br/>" + String(videoName).substr(index);
                    }
                    if (params[i].seriesName == '播放量') {
                        let playCount = params[i].value;
                        if (playCount >= 10000) {
                            playCount = Math.round(playCount / 1000) / 10;
                            playCount = playCount + '万';
                        }
                        list.push(
                            params[i].name +
                            '<br/>' +
                            '<i style="display: inline-block;width: 10px;height: 10px;background: ' +
                            params[i].color +
                            ';margin-right: 5px;border-radius: 50%;}"></i><span style="display:inline-block;">' +
                            params[i].seriesName + ' : ' +
                            playCount +
                            '</span>'
                        );
                    } else {
                        list.push(
                            '<i style="display: inline-block;width: 10px;height: 10px;background: ' +
                            params[i].color +
                            ';margin-right: 5px;border-radius: 50%;"></i><span style="display:inline-block;">' +
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
            data: ['播放量'],
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
            boundaryGap: [0, 0.01],
            axisLabel:{
                interval:0,//横轴信息全部显示
                rotate:-15,//-15度角倾斜显示
            }
        },
        yAxis: {
            type: 'category',
            data: [],
            axisLabel: {
                formatter: function (yData) {
                    if (String(yData).length > 25) {
                        yData = String(yData).substr(0, 25) + "...";
                    }
                    return yData;
                }
            }
        },
        series: [
            {
                name: '播放量',
                type: 'bar',
                data: []
            },
            {
                name: '视频类型',
                type: 'line',
                data: []
            }
        ]
    };

    let video_type_count_option = {
        title: {
            text: '视频top10播放排行各分类占比',
            left: 'center',
            textStyle: {
                color: '#ff6100'
            }
        },
        tooltip: {
            trigger: 'item',
            formatter: function (params) {
                //  '{a} <br/>{b} : {c}个<br/>占比 : {d}%'
                let item = ''
                item = params.name +
                    '<br/>' +
                    '<i style="display: inline-block;width: 10px;height: 10px;background: ' +
                    params.color +
                    ';margin-right: 5px;border-radius: 50%;"></i><span style="display:inline-block;">' +
                    '总计 : ' +
                    params.value + '个' +
                    '</span>' +
                    '<br/>' +
                    '<i style="display: inline-block;width: 10px;height: 10px;background: ' +
                    params.color +
                    ';margin-right: 5px;border-radius: 50%;}"></i><span style="display:inline-block;">' +
                    '占比 : ' +
                    params.percent + '%' +
                    '</span>';
                return '<div class="showBox">' + item + '</div>'
            }
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['趣味科普人文', '搞笑', '健身'],
            textStyle: {
                color: '#fff'
            },
            top: '10%'
        },
        series: [
            {
                name: '视频分类',
                type: 'pie',
                radius: '55%',
                center: ['58%', '58%'],
                data: [
                    {value: 3, name: '趣味科普人文'},
                    {value: 2, name: '搞笑'},
                    {value: 5, name: '健身'},

                ],
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
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
        url: "/dwVideoTopPlayCount/queryAll",
        //data : JSON.stringify(list),
        success: function (result) {
            console.log(result);
            let rows = result.rows;
            video_top_rank_option.xAxis.data = [];
            rows.forEach(row => {
                //视频名字
                let videoName = row.videoName;
                //播放量
                let playCount = row.playCount;
                //视频类型
                let videoTypeId = row.videoTypeId;
                let videoTypeName = row.videoTypeName;
                video_top_rank_option.yAxis.data.push(videoName);
                video_top_rank_option.series[0].data.push(playCount);
                video_top_rank_option.series[1].data.push(videoTypeName);
            });
            console.log(video_top_rank_option);
            //视频top10播放排行
            var video_top_rank = echarts.init(document.getElementById('video_top_rank'));
            video_top_rank.setOption(video_top_rank_option);
        },
        error: function (e) {
            console.log('error:' + e);
        }
    });

    $.ajax({
        //请求方式
        type: "GET",
        //请求的媒体类型
        contentType: "application/json;charset=UTF-8",
        //希望返回的类型
        dataType: "json",
        //请求地址
        url: "/dwVideoTopPlayCount/getVideoCategoryCount",
        //data : JSON.stringify(list),
        success: function (result) {
            let rows = result.rows;
            video_type_count_option.legend.data = [];
            video_type_count_option.series[0].data = [];
            rows.forEach(row => {
                let resultData = {};
                let videoTypeName = row.videoTypeName;
                let videoCount = row.videoCount;
                let videoTypeId = row.videoTypeId;
                video_type_count_option.legend.data.push(videoTypeName);
                resultData.value = videoCount;
                resultData.name = videoTypeName;
                video_type_count_option.series[0].data.push(resultData);
            });
            //视频top10播放排行各分类占比
            var video_type_count = echarts.init(document.getElementById('video_type_count'));
            video_type_count.setOption(video_type_count_option);
        }
    });

}
