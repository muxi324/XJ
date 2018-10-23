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
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <%@ include file="../system/admin/top.jsp"%>
</head>
<body>
<div id="zhongxin">
<table id="table_report" class="table table-striped table-bordered table-hover">
    <tr>
        <td style="width:110px;text-align: right;padding-top: 13px;">异常id:</td>
        <td>${result.id}</td>
    </tr>
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
        <td style="width:110px;text-align: right;padding-top: 13px;">建议措施:</td>
        <td>${result.suggestion}</td>
    </tr>
    <tr>
        <td style="width:110px;text-align: right;padding-top: 13px;">异常级别:</td>
        <td>
            <c:if test="${result.level==1}"> 问题型</c:if>
            <c:if test="${result.level==2}"> 隐患型</c:if>
            <c:if test="${result.level==3}"> 报警型</c:if>
        </td>
    </tr>
    <tr>
        <td style="width:110px;text-align: right;padding-top: 13px;">异常描述:</td>
        <td>${result.description}</td>
    </tr>
    <tr>
        <td style="width:110px;text-align: right;padding-top: 13px;">异常照片</td>
        <td><img src="/imgFile/${result.pic}" alt="异常图片"width="210"></td>
    </tr>
</table>
<a class="btn btn-mid btn-success" href="${basePath}sendtask/goSendTask2.do?exceptionId=${result.id}">下发维修任务</a>
<a class="btn btn-mid btn-success" href="${basePath}sendtask/goSendTask1.do?exceptionId=${result.id}">下发临时巡检任务</a>
<div style="padding-bottom: 30px;">
</div>
</div>
<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
</body>
<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript">
    $(top.hangge());

</script>
</html>

