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
<table id="table_report" class="table table-striped table-bordered table-hover">
    <tr>
        <td style="width:110px;text-align: right;padding-top: 13px;">异常上报人:</td>
        <td>${result.report_worker}</td>
    </tr>
    <tr>
        <td style="width:110px;text-align: right;padding-top: 13px;">异常上报时间:</td>
        <td>${result.report_time}</td>
    </tr>
    <tr>
        <td style="width:110px;text-align: right;padding-top: 13px;">所属车间:</td>
        <td>${result.workshop}</td>
    </tr>
    <tr>
        <td style="width:110px;text-align: right;padding-top: 13px;">所属巡检点:</td>
        <td>${result.checkpoint}</td>
    </tr>
    <tr>
        <td style="width:110px;text-align: right;padding-top: 13px;">异常级别:</td>
        <td>${result.level}</td>
    </tr>
    <tr>
        <td style="width:110px;text-align: right;padding-top: 13px;">异常描述:</td>
        <td>${result.description}</td>
    </tr>
    <tr colspan="10">
        <td>异常照片</td>
        <td><img src="/exceptionpic/${result.pic}" alt="异常图片"></td>
    </tr>
</table>
<a href="${basePath}sendtask/goSendTask2.do">解决异常</a>

</body>
<%@ include file="../system/admin/bottom.jsp"%>
</html>
