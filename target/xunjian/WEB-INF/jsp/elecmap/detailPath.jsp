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
        <!--<div id="date">
            <table>
                <tr id="s1">
                    <td style="width:110px;text-align: right;padding-top: 13px;">开始时间:</td>
                    <td><input style="width:90%;" type="text" class="datetimepicker" name="start_time" id="start_time"  maxlength="200" data-date-format="yyyy-mm-dd  hh:mm" title=""/></td>
                    <td style="width:110px;text-align: right;padding-top: 13px;">结束时间:</td>
                    <td><input style="width:90%;" type="text" class="datetimepicker" name="end_time" id="end_time"  maxlength="200" data-date-format="yyyy-mm-dd  hh:mm" title=""/></td>
                    <td><input type="button" id="query" class="btn btn-small btn-primary">查询</td>
                </tr>
            </table>
        </div>-->
    </body>

</html>


<script type="text/javascript">
    // 百度地图API功能
    $(top.hangge());


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

    //画线
    var sy = new BMap.Symbol(BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW, {
        scale: 0.6,//图标缩放大小
        strokeColor:'#fff',//设置矢量图标的线填充颜色
        strokeWeight: '2',//设置线宽
    });
    var icons = new BMap.IconSequence(sy, '10', '30');
    var points = [];

    <c:choose>
        <c:when test="${not empty pointList}">
              <c:forEach var="point" items="${pointList}">
                    points.push(new BMap.Point(${point.longitude},${point.latitude}));
              </c:forEach>
        </c:when>
        <c:otherwise>
             alert("无经纬度数据！")
        </c:otherwise>
    </c:choose>

    var polyline =new BMap.Polyline(points, {
        enableEditing: false,//是否启用线编辑，默认为false
        enableClicking: true,//是否响应点击事件，默认为true
        icons:[icons],
        strokeWeight:'8',//折线的宽度，以像素为单位
        strokeOpacity: 0.8,//折线的透明度，取值范围0 - 1
        strokeColor:"red" //折线颜色
    });

    function addMarker(points) {
        for(var i=0, pointsLen = points.length; i<pointsLen; i++) {
            var marker = new BMap.Marker(points[i]);
            map.addOverlay(marker);
            if (i == 0) {
                marker.addEventListener("mouseover",
                    function() {
                        showInfo(this,thePoint,"起点");
                    });
            } else if (i == points.length - 1) {
                marker.addEventListener("mouseover",
                    function() {
                        showInfo(this,thePoint,"终点");
                    });
            }
        }
    }

    function showInfo(thisMarker,point,sContent) {
        //获取点的信息
        var infoWindow = new BMap.InfoWindow(sContent); //创建信息窗口对象
        thisMarker.openInfoWindow(infoWindow); //图片加载完后重绘infoWindow
    }

    addMarker(points);  //增加点
    map.addOverlay(polyline);          //增加折线

</script>