<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 2018/3/1
  Time: 13:56
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
        table, tr, td {height: 50px;border:1px solid #ccc;padding:20px;font-family:"微软雅黑";}
    </style>
</head>
<body>
    <table>
        <tr>
            <td>故障情况</td>
            <td>${errInfo}</td>
        </tr>
        <tr>
            <td>故障发生时间</td>
            <td>${errorTime}</td>
        </tr>
        <tr>
            <td>故障解决建议</td>
            <td>${errorAdvice}</td>
        </tr>
    </table>
</body>
</html>
