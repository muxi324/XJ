<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 2018/3/22
  Time: 20:43
  To change this template use File | Settings | File Templates.
--%>
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
  <div id="zhongxin" class="center" >
    <img style="width:300px;height:300px" src="C:\apache-tomcat-8.5.23\webapps\water${pd.pic}" width="210">
  </div>
</body>
</html>
