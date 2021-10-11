var highlight_part = document.getElementById("data-analyze");
highlight_part.setAttribute("style","" +
    "box-shadow: 1px 1px 10px gray;\n" +
    "background-color: #f5f5f5;");

// 基于准备好的dom，初始化echarts实例
var label_num_analyze = echarts.init(document.getElementById('label-num-analyze-pie'));
// 指定图表的配置项和数据
var num_analyze = {
    polar: {
        radius: [30, '80%']
    },
    angleAxis: {
        max: 4,
        startAngle: 75
    },
    radiusAxis: {
        type: 'category',
        data: label_num_analyze_pie_x
    },
    tooltip: {},
    series: {
        type: 'bar',
        data: label_num_analyze_pie_val,
        coordinateSystem: 'polar',
        label: {
            show: true,
            position: 'middle',
            formatter: '{b}: {c}'
        }
    }
};
// 使用刚指定的配置项和数据显示图表。
label_num_analyze.setOption(num_analyze);

var label_num_analyze_straight = echarts.init(document.getElementById('label-num-analyze-straight'));

// 指定图表的配置项和数据
var option = {
    tooltip: {},
    legend: {
        data:['销量']
    },
    xAxis: {
        data: label_num_analyze_straight_x
    },
    yAxis: {},
    series: [{
        name: '销量',
        type: 'bar',
        data: label_num_analyze_straight_data
    }]
};

// 使用刚指定的配置项和数据显示图表。
label_num_analyze_straight.setOption(option);


var upload_num_each_day = echarts.init(document.getElementById('upload-num-each-day'));

// 指定图表的配置项和数据
var upload_num = {
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: upload_num_each_day_x
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            data: upload_num_each_day_data,
            type: 'line',
            areaStyle: {}
        }
    ]
};
// 使用刚指定的配置项和数据显示图表。
upload_num_each_day.setOption(upload_num);


var upload_num_each_week = echarts.init(document.getElementById('upload-num-each-week'));

// 指定图表的配置项和数据
var upload_num_week = {
    xAxis: {
        type: 'category',
        data: upload_num_each_week_x
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            data: upload_num_each_week_data,
            type: 'line',
            symbol: 'triangle',
            symbolSize: 20,
            lineStyle: {
                color: '#5470C6',
                width: 4,
                type: 'dashed'
            },
            itemStyle: {
                borderWidth: 3,
                borderColor: '#EE6666',
                color: 'yellow'
            }
        }
    ]
};
// 使用刚指定的配置项和数据显示图表。
upload_num_each_week.setOption(upload_num_week);
