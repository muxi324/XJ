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
<label class="control-label" style="margin-left:45%;margin-top: 10px;margin-bottom: 20px">工作内容</label>
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
                        <td style="width: 139px;" class="center"><a onclick="pic('${mission_id}','${event_id}','${var.work_name }')"> <img style="width:80px;height:80px" src="/imgFile/${var.pic}" width="100"></a></td>
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
<div class="page-header position-relative">
    <table style="width:100%;">
        <tr>
            <td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
        </tr>
    </table>
</div>

<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<!-- 引入 -->
<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
<script type="text/javascript">

    $(top.hangge());

    function pic(mission_id,event_id,work_name){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="查看照片";
        diag.URL = '<%=basePath%>taskmag/getTaskPhoto.do?mission_id='+mission_id+'&event_id='+event_id+'&work_name='+work_name;
        diag.Width = 400;
        diag.Height = 450;
        diag.CancelEvent = function(){ //关闭事件
            if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                nextPage(${page.currentPage});
            }
            diag.close();
        };
        diag.show();
    }
</script>

</body>
<%@ include file="../system/admin/bottom.jsp"%>
</html>

