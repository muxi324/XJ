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
    <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
    <%@ include file="../system/admin/top.jsp"%>
</head>
<body>
<table id="table_report" class="table table-striped table-bordered table-hover">
    <input type="hidden" id="mission_id" name="mission_id" value="${mission_id}">
    <input type="hidden" id="event_id" name="event_id" value="${event_id}">
    <thead>
    <tr>
        <th class="center">序号</th>
        <th class="center">工作内容名称</th>
        <th class="center">工作内容数值或（描述）</th>
        <th class="center">工作内容照片</th>
    </tr>
    </thead>
    <c:choose>
        <c:when test="${not empty varList}">
            <c:if test="${QX.cha == 1 }">
                <c:forEach items="${varList}" var="var" varStatus="vs">
                    <tr>
                        <td class='center' style="width: 30px;">${vs.index+1}</td>
                        <td style="width: 60px;" class="center"> ${var.work_name}</td>
                        <td style="width: 100px;" class="center">${var.data}</td>
                        <td style="width: 139px;" class="center"><a href="<%=basePath%>taskmag/getTaskPhoto.do?mission_id=${mission_id}&event_id=${event_id}&work_name=${var.work_name}">查看图片</a></td>
                       <%-- <td style="width: 100px;" class="center">
                            <img style="width:100px;height:100px" src="C:\apache-tomcat-8.5.23\webapps\water${var.pic}" width="210"></td>--%>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${QX.cha == 0 }">
                <tr>
                    <td colspan="100" class="center">您无权查看</td>
                </tr>
            </c:if>
        </c:when>
        <c:otherwise>
            <tr class="main_info">
                <td colspan="100" class="center" >没有相关数据</td>
            </tr>
        </c:otherwise>
    </c:choose>
</table>
</body>
<%@ include file="../system/admin/bottom.jsp"%>
</html>
