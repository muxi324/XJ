<%--
  Created by IntelliJ IDEA.
  User: wp
  Date: 2017/12/22
  Time: 9:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <style type="text/css">
        body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
    </style>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=tkTgXO7ycraPnPgGGejoV8CZp58Nd559"></script>
</head>
<body>

<div id="allmap" style="height: 85%"></div>

<div id="r-result">
    <input type="button" onclick="showAll()" value="展示全部员工位置" />
    <input type="button" onclick="showSome()" value="点击选择员工展示位置">
    <input type="button" onclick="showError()" value="展示异常位置" />
</div>
</body>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    请选择需要展示位置的员工
                </h4>
            </div>
            <div class="modal-body">
                <table id="worker" class="table table-striped table-bordered table-hover">

                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="showWorkers()">确定</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</html>


<script type="text/javascript">
    // 百度地图API功能
    $(top.hangge());
    var points = [];
    $.ajax({
        type : "post",
        async : true,
        url :"<%=basePath%>elecmap/workerPosition.do",
        dataType : "json",		//返回数据形式为json
        success : function(result) {
            if (result != null && result.length > 0) {
                console.info(result);
                $.each(result, function(index, content) {
                    var str = "<tr>" +
                        "<td><input type='checkbox'name='workers' value='"+content.name+"'/></td>" +
                        "<td>"+content.name+"</td>" +
                        "</tr>";
                    $("#worker").append(str);
                    var point = {"lng":content.longitude,"lat":content.latitude,"url":"<%=basePath%>elecmap/detailPath.do?workerId="+content.id ,"id":content.id,"name":content.name,"phoneNum":content.phone};
                    points.push(point);
                })
            }
            else {
            }
        },
        error : function(errorMsg) {
            alert("员工位置数据获取失败，可能是服务器开小差了");
        }
    })
    function showSome() {
        $("#myModal").modal('show');
    }

    function showWorkers() {
        map.clearOverlays();
        var chk_value =[];
        $('input[name="workers"]:checked').each(function(){
            chk_value.push($(this).val());
        });
        for (var j =0; j<chk_value.length; j++) {
            var name = chk_value[j];
            for(var i=0, pointsLen = points.length; i<pointsLen; i++) {
                if (name == points[i].name) {
                    var point = new BMap.Point(points[i].lng, points[i].lat);
                    var marker = new BMap.Marker(point);
                    map.addOverlay(marker);
                    (function() {
                        var thePoint = points[i];
                        marker.addEventListener("mouseover",
                            function() {
                                showInfo(this,thePoint);
                            });
                    })();
                    break;
                }
            }
        }
        $("#myModal").modal('hide');
    }
    var flag = 0;
    function showAll() {
        flag = addMarker(points,flag);
    }
    //创建标注点并添加到地图中
    function addMarker(points,flag) {
        if (flag == 0) {
            //循环建立标注点
            for(var i=0, pointsLen = points.length; i<pointsLen; i++) {
                var point = new BMap.Point(points[i].lng, points[i].lat); //将标注点转化成地图上的点
                /*            var myIcon = new BMap.Icon("http://7xic1p.com1.z0.glb.clouddn.com/markers.png", new BMap.Size(23, 25), {
                 // 指定定位位置
                 offset: new BMap.Size(10, 25),
                 // 当需要从一幅较大的图片中截取某部分作为标注图标时，需要指定大图的偏移位置
                 imageOffset: new BMap.Size(0, 0 - i * 25) // 设置图片偏移
                 });*/
                var marker = new BMap.Marker(point); //将点转化成标注点
                map.addOverlay(marker);  //将标注点添加到地图上
                //添加监听事件
                (function() {
                    var thePoint = points[i];
                    marker.addEventListener("mouseover",
                        function() {
                            showInfo(this,thePoint);
                        });
                })();
            }
            flag = 1;
        } else {
            map.clearOverlays();
            flag = 0;
        }
        return flag;
    }
    var errorIcon = new BMap.Icon('static/img/90.png', new BMap.Size(20, 32), {
        anchor: new BMap.Size(10, 30)
    });
    var exceptionPoints = [];
    $.ajax({	//使用JQuery内置的Ajax方法
        type: "post",		//post请求方式
        async: true,		//异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "<%=basePath%>elecmap/exceptionPosition.do",	//请求发送到ShowInfoIndexServlet处
        dataType: "json",		//返回数据形式为json
        success: function (exceptionList) {
            if (exceptionList != null && exceptionList.length > 0) {
                console.info(exceptionList);
                $.each(exceptionList, function(index, content) {
                    var point = {"jingdu":content.jingdu,"weidu":content.weidu,"report_worker":content.report_worker,
                        "workshop":content.workshop,"description":content.description,"report_time":content.report_time,
                        "level":content.level,"url":"exception/getExceptionDetail.do?exceptionId="+content.id};
                    exceptionPoints.push(point);
                })
            }
            else {
            }
            console.log(exceptionPoints);
        },
        error : function(errorMsg) {
            alert("异常数据获取失败，可能是服务器开小差了");
        }
    })
    var errorFlag = 0;
    function showError() {
        errorFlag = addMarker1(exceptionPoints,errorFlag);
    }
    function addMarker1(exceptionPoints,errorFlag) {
        if (errorFlag == 0) {
            //循环建立标注点
            for(var i=0, pointsLen = exceptionPoints.length; i<pointsLen; i++) {
                var point = new BMap.Point(exceptionPoints[i].jingdu, exceptionPoints[i].weidu); //将标注点转化成地图上的点
                var marker = new BMap.Marker(point,{icon: errorIcon}); //将点转化成标注点
                map.addOverlay(marker);  //将标注点添加到地图上
                //添加监听事件
                (function() {
                    var thePoint = exceptionPoints[i];
                    marker.addEventListener("mouseover",
                        function() {
                            showInfo1(this,thePoint);
                        });
                })();
            }
            errorFlag = 1;
        } else {
            map.clearOverlays();
            errorFlag = 0;
        }
        return errorFlag;
    }
    function showInfo(thisMarker,point) {
        //获取点的信息
        var sContent =
            '<ul style="margin:0 0 5px 0;padding:0.2em 0">'
            +'<li style="line-height: 26px;font-size: 15px;">'
            +'<span style="width: 50px;display: inline-block;">工号：</span>' + point.id + '</li>'
            +'<li style="line-height: 26px;font-size: 15px;">'
            +'<span style="width: 50px;display: inline-block;">姓名：</span>' + point.name + '</li>'
            +'<li style="line-height: 26px;font-size: 15px;">'
            +'<span style="width: 50px;display: inline-block;">电话：</span>' + point.phoneNum + '</li>'
            +'<li style="line-height: 26px;font-size: 15px;"><span style="width: 50px;display: inline-block;">查看：</span><a href="'+point.url+'">移动路径</a></li>'
            +'</ul>';
        var infoWindow = new BMap.InfoWindow(sContent); //创建信息窗口对象
        thisMarker.openInfoWindow(infoWindow); //图片加载完后重绘infoWindow
    }
    function showInfo1(thisMarker,point) {
        //获取点的信息
        var sContent =
            '<ul style="margin:0 0 5px 0;padding:0.2em 0">'
            +'<li style="line-height: 26px;font-size: 15px;">'
            +'<span style="width: 100px;display: inline-block;">异常所属车间：</span>' + point.workshop + '</li>'
            +'<li style="line-height: 26px;font-size: 15px;">'
            +'<span style="width: 100px;display: inline-block;">异常描述：</span>' + point.description + '</li>'
            +'<li style="line-height: 26px;font-size: 15px;">'
            +'<span style="width: 100px;display: inline-block;">异常上报人：</span>' + point.report_worker + '</li>'
            +'<span style="width: 100px;display: inline-block;">上报时间：</span>' + point.report_time + '</li>'
            +'<li style="line-height: 26px;font-size: 15px;"><span style="width: 50px;display: inline-block;">查看：</span><a href="'+point.url+'">异常详情</a></li>'
            +'</ul>';
        var infoWindow = new BMap.InfoWindow(sContent); //创建信息窗口对象
        thisMarker.openInfoWindow(infoWindow); //图片加载完后重绘infoWindow
    }
    var map = new BMap.Map("allmap");    // 创建Map实例
    map.centerAndZoom(new BMap.Point(116.056167,39.496554), 11);  // 初始化地图,设置中心点坐标和地图级别
    map.setCurrentCity("阔丹凌云厂");// 设置地图显示的城市 此项是必须设置的
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    //添加地图控件
    var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
    var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
    var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角
    map.addControl(top_left_control);
    map.addControl(top_left_navigation);
    map.addControl(top_right_navigation);
</script>