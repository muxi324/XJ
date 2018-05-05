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
            <input type="button" onclick="showError()" value="展示故障位置" />
        </div>
        <%@ include file="../system/admin/bottom.jsp"%>
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

    var errorIcon = new BMap.Icon('static/img/errorIcon.png', new BMap.Size(20, 32), {
        anchor: new BMap.Size(10, 30)
    });

    errorFlag = 0;
    function showError() {
        errorFlag = addMarker1(errorPoints,errorFlag);
    }
    function addMarker1(points,errorFlag1) {
        if (errorFlag1 == 0) {
            //循环建立标注点
            for(var i=0, pointsLen = points.length; i<pointsLen; i++) {
                var point = new BMap.Point(points[i].lng, points[i].lat); //将标注点转化成地图上的点
                var marker = new BMap.Marker(point,{icon: errorIcon}); //将点转化成标注点
                map.addOverlay(marker);  //将标注点添加到地图上
                //添加监听事件
                (function() {
                    var thePoint = points[i];
                    marker.addEventListener("mouseover",
                        function() {
                            showInfo1(this,thePoint);
                        });
                })();
            }
            errorFlag1 = 1;
        } else {
            map.clearOverlays();
            errorFlag1 = 0;
        }
        return errorFlag1;
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