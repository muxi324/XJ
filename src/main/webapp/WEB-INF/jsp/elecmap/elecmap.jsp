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
        <%@ include file="../system/admin/top.jsp"%>
        <style type="text/css">
            body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
        </style>
        <script type="text/javascript" src="static/js/jquery-3.1.1.js"></script>

        <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=tkTgXO7ycraPnPgGGejoV8CZp58Nd559"></script>
    </head>
    <body>

        <div id="allmap" style="height: 75%"></div>

        <div id="r-result">
            <input type="button" onclick="addMarker(points);" value="展示员工位置" />
            <input type="button" onclick="addMarker1(errorPoints);" value="展示故障位置" />
        </div>
        <%@ include file="../system/admin/bottom.jsp"%>
    </body>
</html>


<script type="text/javascript">
    // 百度地图API功能
    $(top.hangge());

    var points = [
        {"lng":116.3588619232,"lat":39.9589645746,"url":"<%=basePath%>elecmap/detailPath.do?workerId=1","id":1,"name":"汪鹏","phoneNum":"13112345678"},
        {"lng":116.3726806641,"lat":39.9767254644,"url":"<%=basePath%>elecmap/detailPath.do?workerId=2","id":2,"name":"宋胜男","phoneNum":"13212345678"},
        {"lng":116.3446140289,"lat":39.9787643824,"url":"<%=basePath%>elecmap/detailPath.do?workerId=3","id":3,"name":"王子良","phoneNum":"13312345678"}
    ];

    var errorPoints = [
        {"lng":116.3828086853,"lat":39.9417255221,"url":"<%=basePath%>elecmap/errorDetail.do?errorId=1","id":1,"name":"故障点1","phoneNum":"010-123456"},
        {"lng":116.4228057861,"lat":39.9750153573,"url":"<%=basePath%>elecmap/errorDetail.do?errorId=2","id":2,"name":"故障点2","phoneNum":"010-234567"},
        {"lng":116.3077926636,"lat":40.0439119241,"url":"<%=basePath%>elecmap/errorDetail.do?errorId=3","id":3,"name":"故障点3","phoneNum":"010-345678"}
    ];

    $.ajax({
        type : "post",
        async : true,
        url :"<%=basePath%>elecmap/workerPosition.do",
        dataType : "json",		//返回数据形式为json
        success : function(result) {
            if (result != null && result.length > 0) {
                console.info(result);
                $.each(result, function(index, content) {
                    var point = {"lng":content.longitude,"lat":content.latitude,"url":"<%=basePath%>elecmap/detailPath.do?workerId=1","id":content.id,"name":content.name,"phoneNum":content.phone};
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

    //创建标注点并添加到地图中
    function addMarker(points) {
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
                marker.addEventListener("click",
                    function() {
                        showInfo(this,thePoint);
                    });
            })();
        }
    }

    var errorIcon = new BMap.Icon('static/img/errorIcon.png', new BMap.Size(20, 32), {
        anchor: new BMap.Size(10, 30)
    });

    function addMarker1(points) {
        //循环建立标注点
        for(var i=0, pointsLen = points.length; i<pointsLen; i++) {
            var point = new BMap.Point(points[i].lng, points[i].lat); //将标注点转化成地图上的点
            var marker = new BMap.Marker(point,{icon: errorIcon}); //将点转化成标注点
            map.addOverlay(marker);  //将标注点添加到地图上
            //添加监听事件
            (function() {
                var thePoint = points[i];
                marker.addEventListener("click",
                    function() {
                        showInfo1(this,thePoint);
                    });
            })();
        }
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
            +'<span style="width: 100px;display: inline-block;">故障点编号：</span>' + point.id + '</li>'
            +'<li style="line-height: 26px;font-size: 15px;">'
            +'<span style="width: 100px;display: inline-block;">故障点名称：</span>' + point.name + '</li>'
            +'<li style="line-height: 26px;font-size: 15px;">'
            +'<span style="width: 100px;display: inline-block;">故障点座机：</span>' + point.phoneNum + '</li>'
            +'<li style="line-height: 26px;font-size: 15px;"><span style="width: 50px;display: inline-block;">查看：</span><a href="'+point.url+'">故障详情</a></li>'
            +'</ul>';
        var infoWindow = new BMap.InfoWindow(sContent); //创建信息窗口对象
        thisMarker.openInfoWindow(infoWindow); //图片加载完后重绘infoWindow
    }

    var map = new BMap.Map("allmap");    // 创建Map实例
    map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
    map.setCurrentCity("北京");// 设置地图显示的城市 此项是必须设置的
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放


    //添加地图控件
    var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
    var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
    var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角
    map.addControl(top_left_control);
    map.addControl(top_left_navigation);
    map.addControl(top_right_navigation);

</script>