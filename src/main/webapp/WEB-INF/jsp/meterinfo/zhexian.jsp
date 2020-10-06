<%--
  Created by IntelliJ IDEA.
  User: 帥哥无限叼
  Date: 2019/11/17
  Time: 11:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="static/assets/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="static/assets/css/font-awesome.css" />

    <link rel="stylesheet" href="static/assets/css/ace.css" />
    <link rel="stylesheet" href="static/assets/css/ace-rtl.min.css" />
    <link rel="stylesheet" href="static/assets/css/ace-skins.min.css" />

    <link rel="stylesheet" href="static/css/style.css"/>
    <script src="static/js/jquery-1.9.1.js"></script>
    <script src="static/js/jquery.tips.js"></script>
    <script src="static/assets/js/ace-extra.min.js"></script>
    <script src="static/assets/layer/layer.js" type="text/javascript"></script>
    <style>
        .active{
            background:yellow;}
        .tukuang{overflow:hidden;} /*只显示一个图超过大小则隐藏*/
        .tukuang div:first-Child{display: block;}
        /*#yichang{border: solid 1px #ccc;}*/
        #yichang th,td{ height:30px; padding: 5px;border: solid 1px #ccc;}
        #fuyi th,td{ height:30px; padding: 5px;border: solid 1px #ccc;}
    </style>
    <script src="static/js/echarts.js"></script>
    <script>
        var finnallpath = "<%=basePath%>"; // 获取当前页面的地址，给js文件用
    </script>
    <script src ="static/js/use/zhexian.js"></script>
</head>


<body onload="load('${biaohao}','${type}')">
<div class="top-nav">
    <button class="btn btn-success" id="button1" onclick="bianse1()">表数曲线</button>
    <button id="button2" class="btn" onclick="bianse2('${biaohao}')">数据分析</button>
</div>
<div class="tukuang" style="width:600px;height:400px">
    <div id="echarts_3" class="tu" style="width:600px;height:400px"></div>
    <div id="echarts_4" class="tu" style="margin: 15px;" >

        <div style="margin: 5px">
            <h3>是否单调非减判断</h3>
            <div id="zeng">水表数据单调非减，正常</div>
            <div id="zengshu" >
                <div >异常值:</div>
                <table id="yichang">
                    <thread>
                        <tr>
                            <th>序号</th>
                            <th>时间</th>
                            <th>拍照人</th>
                            <th>核对人</th>
                            <th>校对数据</th>
                            <th>自动识别</th>

                        </tr>
                        <tbody id="yibiao"></tbody>
                    </thread>
                </table>
            </div>
        </div>

        <div style="margin: 5px;">
            <h3>增长幅度分析</h3>
            <div id="fu">水表数据单调非减，正常</div>
            <div id="zengyi">
                <div >异常值:</div>
                <table id="fuyi">
                    <thread>
                        <tr>
                            <th>序号</th>
                            <th>时间</th>
                            <th>拍照人</th>
                            <th>核对人</th>
                            <th>校对数据</th>
                            <th>自动识别</th>

                        </tr>
                        <tbody id="fuyivalue"></tbody>
                    </thread>
                </table>
            </div>
        </div>
    </div>
</div>

</body>
</html>