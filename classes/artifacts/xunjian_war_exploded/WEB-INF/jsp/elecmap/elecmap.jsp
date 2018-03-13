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
    <title>Title</title>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
        body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
    </style>
    <script type="text/javascript" src="static/js/jquery-3.1.1.js"></script>

    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=tkTgXO7ycraPnPgGGejoV8CZp58Nd559"></script>
</head>
<script type="text/javascript">
var markers = [];
$(document).ready(function() {

    getWorker();
    initMap();
    setInterval("frash()",3000);
    });

    function frash(){
    map.clearOverlays();//清空地图上标注点
    getdata();
    addMapOverlay();
    markers.splice(0,markers.length);//清空数组
}

//安装人员信息
var map = new BMap.Map("container");
function getWorker() {
    $.ajax({
        url:"<%=basePath%>elecmap/listW.do",
        type:"post",
        success: getlonandlat

    });

}
</script>
<body>
   <div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
    // 百度地图API功能
    function initMap(){
        //alert("111"+markers[1].position.lat);
        createMap();//创建地图
        setMapEvent();//设置地图事件
        addMapControl();//向地图添加控件
        addMapOverlay();//向地图添加覆盖物
    }

    function createMap() {
        var map = new BMap.Map("allmap");    // 创建Map实例
        map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
    }
    function setMapEvent(){
        map.enableScrollWheelZoom();//启用地图滚轮放大缩小
        map.enableKeyboard();//启用键盘上下左右键移动地图
        map.enableDragging(); //启用地图拖拽事件，默认启用(可不写)
        map.enableDoubleClickZoom()//启用鼠标双击放大，默认启用(可不写)
    }
    function addClickHandler(target,window){
        target.addEventListener("click",function(){
            target.openInfoWindow(window);
        });
    }

    //设置点Icon
    function addMapOverlay(){
        //alert(markers.length+"22222");
        //alert(markers[1].position.lat+"33333");
        /*  var markers = [
         {position:{lat:36.79,lng:118.06}},
         {position:{lat:36.81,lng:118.06}},
         {position:{lat:36.81,lng:118.04}},
         {position:{lat:36.81,lng:118.05}}
         ];
         alert(markers);*/
        for(var index = 0; index < markers.length; index++ ){
            var point = new BMap.Point(markers[index].position.lng,markers[index].position.lat);
            var marker = new BMap.Marker(point,{icon:new BMap.Icon("http://api.map.baidu.com/lbsapi/createmap/images/icon.png",new BMap.Size(20,25)
                // imageOffset: new BMap.Size(markers[index].imageOffset.width,markers[index].imageOffset.height)
            )});
            //  var label = new BMap.Label(markers[index].title,{offset: new BMap.Size(25,5)});
            // var opts = {
            //  width: 200,
            //   title: markers[index].title,
            //    enableMessage: false
            //   };
            // var infoWindow = new BMap.InfoWindow(markers[index].content,opts);
            // marker.setLabel(label);//显示地理名称
            marker.setLabel();//不显示地理名称
            // addClickHandler(marker,infoWindow);
            map.addOverlay(marker);
        };
    }

    //向地图添加控件
    function addMapControl(){
        var scaleControl = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
        scaleControl.setUnit(BMAP_UNIT_IMPERIAL);
        map.addControl(scaleControl);
        var navControl = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
        map.addControl(navControl);
        var overviewControl = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:true});
        map.addControl(overviewControl);
    }
    var map;

    function ZoomControl(){
        // 默认停靠位置和偏移量
        this.defaultAnchor = BMAP_ANCHOR_BOTTOM_RIGHT;
        this.defaultOffset = new BMap.Size(10, 10); // 距离左上角位置
    }

    ZoomControl.prototype = new BMap.Control();
    ZoomControl.prototype.initialize = function(map){
        var div = document.createElement("div");
        var img1=document.createElement("img");
        img1.src="static/images/serviceman.png";
        div.appendChild(img1);
        div.appendChild(document.createTextNode(" 安装人员"));
        var img2=document.createElement("img");
        img2.src="static/images/home_01.png";
        div.appendChild(img2);
        div.appendChild(document.createTextNode(" 已安装房源"));
        var img3=document.createElement("img");
        img3.src="static/images/home.png";
        div.appendChild(img3);
        div.appendChild(document.createTextNode(" 待安装房源"));

        div.style.cursor = "pointer";
        div.style.width ="150px";
        div.style.border = "1px solid gray";
        div.style.backgroundColor = "gray";
        map.getContainer().appendChild(div);
        return div;
    }







</script>
