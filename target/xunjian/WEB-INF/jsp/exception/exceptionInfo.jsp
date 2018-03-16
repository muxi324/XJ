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

            <thead>
            <tr>

                <th class="center">序号</th>
                <th class="center">所属车间</th>
                <th class="center">所属巡检点</th>
                <th class="center">异常级别</th>
                <th class="center">描述</th>
                <th class="center">汇报工人id</th>
                <th class="center">汇报时间</th>
                <th class="center">异常状态</th>
                <th class="center">是否已读</th>
                <th class="center">操作</th>
            </tr>
            </thead>

            <tbody>

            <!-- 开始循环 -->
            <c:choose>
                <c:when test="${not empty exceptionList}">
                        <c:forEach items="${exceptionList}" var="var" varStatus="vs">
                            <tr>
                                <td class='center' style="width: 30px;">${vs.index+1}</td>
                                <td style="width: 60px;" class="center">${var.workshop}</td>
                                <td style="width: 60px;" class="center">${var.checkpoint}</td>
                                <td style="width: 100px;" class="center">${var.level}</td>
                                <td style="width: 100px;" class="center">${var.description}</td>
                                <td style="width: 139px;" class="center">${var.report_woker}</td>
                                <td style="width: 60px;" class="center">${var.report_time}</td>
                                <td style="width: 60px;" class="center">${var.status}</td>
                                <td style="width: 139px;" class="center">${var.is_read}</td>
                                <td style="width: 60px;" class="center">
                                    <div class='hidden-phone visible-desktop btn-group'>
                                        操作
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr class="main_info">
                        <td colspan="100" class="center" >没有相关数据</td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
</body>
<%@ include file="../system/admin/bottom.jsp"%>
</html>
