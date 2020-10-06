
// 表数曲线
function bianse1() {
    $('button').attr('class','btn');
    $('#button1').addClass('btn btn-success');
    $(".tu").css('display','none');
    $("#echarts_3").css('display','block');
}
// 数据分析
function bianse2(biaohao) {
    $('button').attr('class','btn');
    $('#button2').addClass('btn btn-success');
    $.ajax({
        type:"POST",
        url:finnallpath+"analyse/fenxi",
        data:{biaohao:biaohao},
        dataType:'json',
        cache: false,
        success:function (data) {
            console.log(data);
            if (data.dizengResult!=null && data.dizengResult!="") {
                $("#yibiao").html("");
                if (data.dizengResult.flag==0){
                    console.log(data.dizengResult.msg);
                    var item="<tr><td colspan='6' style='text-align: center'>无异常值</td></tr>";
                    $("#yibiao").append(item);

                } else if (data.dizengResult.flag==1) {
                    console.log(data.dizengResult.msg);
                    $.each(data.dizengResult.excepts,function (index,water) {
                        var temp ="<tr><td>"+(index+1)+"</td><td>"+water.time+"</td><td>"+water.user_id+"</td><td>"
                            +water.jiaozhun+"</td><td>"+water.value+"</td><td>"+water.zzvalue+"</td></tr>";
                        // console.log(temp);
                        $("#yibiao").append(temp);

                    })



                }
                $("#zeng").text(data.dizengResult.msg);
            }
            //解析幅度模型的数据
            if (data.fuduResult!=null && data.fuduResult!="") {
                $("#fuyivalue").html("");
                if (data.fuduResult.flag==0){
                    console.log(data.fuduResult.msg);
                    var item="<tr><td colspan='6' style='text-align: center'>无异常值</td></tr>";
                    $("#fuyivalue").append(item);

                } else if (data.fuduResult.flag==1) {
                    console.log(data.fuduResult.msg);

                    $.each(data.fuduResult.excepts,function (index,water) {
                        var temp ="<tr><td>"+(index+1)+"</td><td>"+water.time+"</td><td>"+water.user_id+"</td><td>"
                            +water.jiaozhun+"</td><td>"+water.value+"</td><td>"+water.zzvalue+"</td></tr>";
                        // console.log(temp);
                        $("#fuyivalue").append(temp);

                    })



                }
                $("#fu").text(data.fuduResult.msg);
            }

        },
        error:function () {
            alert("分析失败")
        }
    });

    $(".tu").css('display','none');
    $("#echarts_4").css('display','block');

}

// 全局开始执行的函数
function load(a,b) {

    var aa=a;
    var bb=b;

    // 绘制表数图-------------------------------
    var myChart = echarts.init(document.getElementById('echarts_3'));
    myChart.showLoading();     //加载动画
    // 指定图表的配置项和数据
    var option = {
        title: {	//图表标题
            text: a+'水表数据图'
        },
        tooltip: {
            trigger: 'axis', //坐标轴触发提示框，多用于柱状、折线图中
        },
        dataZoom: [
            {
                type: 'slider',	//支持鼠标滚轮缩放
                start: 0,			//默认数据初始缩放范围为10%到90%
                end: 100
            },
            {
                type: 'inside',	//支持单独的滑动条缩放
                start: 0,			//默认数据初始缩放范围为10%到90%
                end: 100
            }
        ],
        legend: {	//图表上方的类别显示
            orient: 'horizontal',
            left: 'center',
            top: '30',
            show:true,
            data:['表数']
        },
        // color:[
        //     'red'	//总任务单数曲线颜色
        // ],
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        xAxis:  {	//X轴
            type : 'category',
            data : []	//先设置数据值为空，后面用Ajax获取动态数据填入
        },
        yAxis : [	//Y轴（这里我设置了两个Y轴，左右各一个）
            {
                //第一个（左边）Y轴，yAxisIndex为0
                type : 'value',
                name : '表数',
                axisLabel : {
                    formatter: '{value}'	//控制输出格式
                },
                splitLine: {//控制网格的线条样式
                    show:true,
                    lineStyle:{
                        color: '#FFA500',
                        width: 1,
                        type: 'solid'
                    }
                },

            }
        ],
        series : [	//系列（内容）列表
            {
                name:'表数',
                type:'line',	//折线图表示（生成温度曲线）
                symbol:'emptycircle',	//设置折线图中表示每个坐标点的符号；emptycircle：空心圆；emptyrect：空心矩形；circle：实心圆；emptydiamond：菱形
                data:[],		//数据值通过Ajax动态获取
                markPoint : {
                    data :
                        [
                            {type : 'max', name: '最大值'},
                            {type : 'min', name: '最小值'}
                        ]
                }
            }
        ]
    };

    var sum2=[];
    var dates2=[];
    $.ajax({	//使用JQuery内置的Ajax方法
        type : "POST",		//post请求方式
        async : true,		//异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url :finnallpath+'analyse/shu',	//请求发送到该处
        data : {biaohao:aa,type:bb},		//请求内包含的参数；
        dataType : 'json',		//返回数据形式为json
        success : function(data) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            if (data.result != null && data.result.length > 0) {
                console.info(data.result);
                for(var i=0;i<data.result.length;i++){
                    sum2.push(data.result[i].value);
                    dates2.push(data.result[i].day);
                }
                myChart.hideLoading();	//隐藏加载动画
                myChart.setOption({		//载入数据
                    xAxis: {
                        data: dates2	//填入X轴数据
                    },
                    series: [	//填入系列（内容）数据
                        {
                            // 根据名字对应到相应的系列
                            name: '表数',
                            data: sum2
                        }
                    ]
                });


            }
            else {
                //返回的数据为空时显示提示信息
                alert("图表请求数据为空，可能服务器暂未录入观测数据，您可以稍后再试！");
                myChart.hideLoading();
                $('#echarts_4').html("数据为空")
            }
        },
        error : function(errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败，可能是服务器开小差了");
            myChart.hideLoading();
        }
    })
    myChart.setOption(option);	//载入图表

    // 绘制第二个图以分钟为横坐标--------------------------------------
    document.getElementById("echarts_4").style.display = "block";




}