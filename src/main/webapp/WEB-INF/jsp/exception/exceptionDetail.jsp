<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 2018/3/12
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <div style="">
        <h2>异常照片</h2>
        <div id="exceptionPic">
            <<img src="static/images/exception.jpg" alt="异常图片">>
        </div>
        <a href="${basePath}sendtask/goSendTask2.do"><h2>解决异常</h2></a>
    </div>
</body>
<%@ include file="../system/admin/bottom.jsp"%>
</html>
