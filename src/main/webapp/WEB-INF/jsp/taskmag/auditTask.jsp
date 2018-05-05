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
    <tr>
        <td style="width:110px;text-align: right;padding-top: 13px;">任务名称:</td>
        <td style="width:110px;text-align: center;padding-top: 13px;">${pd.mission_name}</td>
        <td style="width:110px;text-align: right;padding-top: 13px;">任务类型:</td>
        <td style="width:110px;text-align: center;padding-top: 13px;">${pd.mission_type}</td>
        <td style="width:110px;text-align: right;padding-top: 13px;"></td>
        <td style="width:110px;text-align: right;padding-top: 13px;"></td>
    </tr>
    <tr>
        <td style="width:110px;text-align: right;padding-top: 13px;">任务级别:</td>
        <td style="width:110px;text-align: center;padding-top: 13px;">${pd.mission_level}</td>
        <td style="width:110px;text-align: right;padding-top: 13px;">任务来源:</td>
        <td style="width:110px;text-align: center;padding-top: 13px;">${pd.mission_source}</td>
        <td style="width:110px;text-align: right;padding-top: 13px;"></td>
        <td style="width:110px;text-align: right;padding-top: 13px;"></td>
    </tr>
    <tr>
        <td style="width:110px;text-align: right;padding-top: 13px;">班组:</td>
        <td style="width:110px;text-align: center;padding-top: 13px;">${pd.team}</td>
        <td style="width:110px;text-align: right;padding-top: 13px;">检修员工:</td>
        <td style="width:110px;text-align: center;padding-top: 13px;">${pd.worker_name}</td>
        <td style="width:110px;text-align: right;padding-top: 13px;">电话:</td>
        <td style="width:110px;text-align: center;padding-top: 13px;">${pd.worker_phone}</td>
    </tr>
    <tr>
        <td style="width:110px;text-align: right;padding-top: 13px;">任务开始时间:</td>
        <td style="width:110px;text-align: center;padding-top: 13px;">${pd.start_time}</td>
        <td style="width:110px;text-align: right;padding-top: 13px;">任务结束时间:</td>
        <td style="width:110px;text-align: center;padding-top: 13px;">${pd.finish_time}</td>
        <td style="width:110px;text-align: right;padding-top: 13px;"></td>
        <td style="width:110px;text-align: right;padding-top: 13px;"></td>
    </tr>
    <form action="taskmag/auditMisson.do" id="Form"   method="post">
        <input type="hidden" id="missionId" name="missionId" value="${missionId}">
        <table id="t" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务审核人:</td>
                <td><input style="width:90%;" type="text" name="auditor" id="auditor" value="${USERNAME}" size="18" maxlength="200"  title=""></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">审核意见:</td>
                <td>
                    <textarea cols="50" rows="10" name="opinion" id="opinion">在这里输入内容...</textarea>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">审核结果:</td>
                <td>
                    <select class="form-control" name="mission_condition" id="mission_condition">
                        <option value="6">审核通过</option>
                        <option value="7"  >审核未通过</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-small btn-primary" onclick="save();">保存</a>&nbsp;&nbsp;&nbsp;
                </td>
            </tr>
        </table>
    </form>
    <tr>
        <td style="text-align: center;" colspan="10">该任务所包含事件</td>
    </tr>
    <table id="event" class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th class="center">
                <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
            </th>
            <th class="center">序号</th>
            <th class="center">所属车间</th>
            <th class="center">所属巡检区域</th>
            <th class="center">所属巡检点</th>
            <th class="center">事件名称</th>
            <th class="center">具体位置</th>
            <th class="center">查看详情</th>
        </tr>
        </thead>
    <c:choose>
        <c:when test="${not empty varList}">
            <c:if test="${QX.cha == 1 }">
                <c:forEach items="${varList}" var="var" varStatus="vs">
                    <tr>
                        <td class='center' style="width: 30px;">
                            <label><input type='checkbox' id="${var.event_id}" name='ids' value="${var.event_id}"/><span class="lbl"></span></label>
                        </td>
                        <td class='center' style="width: 30px;">${vs.index+1}</td>
                        <td style="width: 60px;" class="center"> ${var.workshop}</td>
                        <td style="width: 139px;" class="center">${var.check_scope}</td>
                        <td style="width: 60px;" class="center">${var.check_point}</td>
                        <td style="width: 100px;" class="center">${var.event_name}</td>
                        <td style="width: 60px;" class="center">${var.instrument_place}</td>
                        <td style="width: 60px;" class="center"><a href="<%=basePath%>taskmag/getWorkContentDetail.do?event_id=${var.event_id}&mission_id=${missionId}">查看详情</a></td>
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

</table>
</body>
<%@ include file="../system/admin/bottom.jsp"%>
</html>
<script type="text/javascript">
    $(top.hangge());
    function save(){
        $("#Form").submit();
    }
</script>