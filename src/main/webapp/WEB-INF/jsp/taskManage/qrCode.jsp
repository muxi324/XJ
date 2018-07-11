<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <%@ include file="../system/admin/top.jsp"%>
</head>
<body>
<%-- <img src="uploadFiles/uploadImgs/check.jpg">--%>
<div id="zhongxin" class="center padding-top:20px" >
    <img style="width:350px;height:400px;padding-top:15px;" src="../../qrupload/${picPath}" width="210">
</div>
</body>
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<!-- 引入 -->
<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
<script type="text/javascript">

    $(top.hangge());
</script>
</html>