<%--
  Created by IntelliJ IDEA.
  User: wp
  Date: 2017/12/18
  Time: 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8" />
    <meta name="description" content="overview & stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="static/css/bootstrap.min.css" rel="stylesheet" />
    <link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="static/css/font-awesome.min.css" />
    <!-- 下拉框 -->
    <link rel="stylesheet" href="static/css/chosen.css" />
    <link rel="stylesheet" href="static/css/ace.min.css" />
    <link rel="stylesheet" href="static/css/ace-responsive.min.css" />
    <link rel="stylesheet" href="static/css/ace-skins.min.css" />
    <script type="text/javascript" src="static/js/jquery-3.1.1.js"></script>
    <!--提示框-->
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="querytask/${msg }.do" name="Form" id="Form" method="post">
    <%--<input type="hidden" name="id" id="id" value="${pd.id}"/>--%>
    <label class="control-label" style="margin-left:45%">任务单详情</label>
    <div id="zhongxin">
        <table  id="table_report" class="table table-striped table-bordered table-hover" style="width:100%;">
            <tr>
                <td style="width:90px;text-align: right;padding-top: 13px;">任务名称:</td>
                <td><input style="width:95%;" type="text" name="mission_name" id="mission_name" value="${pd.mission_name}" maxlength="200" readonly="true" title=""/></td>
                <td style="width:90px;text-align: right;padding-top: 13px;">任务id:</td>
                <td><input style="width:95%;" type="text" name="id" id="id" value="${pd.id}" maxlength="200" readonly="true" title=""/></td>
            </tr>
            <tr>
                <td style="width:90px;text-align: right;padding-top: 13px;">任务状态:</td>
                <td><input style="width:95%;" type="text" name="mission_condition" id="mission_condition" value="${pd.mission_condition}" maxlength="200" readonly="true" title=""
                    <c:if test="${var.mission_condition == '1' }"><span class="label label-info   arrowed-in">任务已下发</span></c:if>
                    <c:if test="${var.mission_condition == '2' }"><span class="label label-warning   arrowed-in">拒收</span></c:if>
                    <c:if test="${var.mission_condition == '3' }"><span class="label label-info   arrowed-in">接收未执行</span></c:if>
                    <c:if test="${var.mission_condition == '4' }"><span class="label label-info      arrowed-in">任务执行中</span></c:if>
                    <c:if test="${var.mission_condition == '5' }"><span class="label label-warning   arrowed-in">任务完成待审核</span></c:if>
                    <c:if test="${var.mission_condition == '6' }"><span class="label label-success   arrowed-in">审核通过</span></c:if>
                    <c:if test="${var.mission_condition == '7' }"><span class="label label-success      arrowed-in">审核未通过</span></c:if>
                    <c:if test="${var.mission_condition == '8' }"><span class="label label-primary     arrowed-in">拒收已处理</span></c:if>
                </td>
                <td style="width:90px;text-align: right;padding-top: 13px;">任务类型:</td>
                <td><input style="width:95%;" type="text" name="mission_type" id="mission_type" value="${pd.mission_type}" maxlength="200" readonly="true" title=""/></td>
            </tr>
            <tr>
                <td style="width:90px;text-align: right;padding-top: 13px;">任务级别:</td>
                <td><input style="width:95%;" type="text" name="mission_level" id="mission_level" value="${pd.mission_level}" maxlength="200" readonly="true" title=""/></td>
                <td style="width:90px;text-align: right;padding-top: 13px;">任务描述:</td>
                <td><input style="width:95%;" type="text" name="mission_description" id="mission_description" value="${pd.mission_description}" maxlength="200" readonly="true" title=""/></td>
            </tr>
            <tr>
                <td style="width:90px;text-align: right;padding-top: 13px;">班组:</td>
                <td><input style="width:95%;" type="text" name="team" id="team" value="${pd.team}" maxlength="200" readonly="true" title=""/></td>
                <td style="width:90px;text-align: right;padding-top: 13px;">巡检人员:</td>
                <td><input style="width:95%;" type="text" name="worker_name" id="worker_name" value="${pd.worker_name}" maxlength="200" readonly="true" title=""/></td>
            </tr>
            <tr>
                <td style="width:90px;text-align: right;padding-top: 13px;">手机:</td>
                <td><input style="width:95%;" type="text" name="worker_phone" id="worker_phone" value="${pd.worker_phone}" maxlength="200" readonly="true" title=""/></td>
                <td style="width:90px;text-align: right;padding-top: 13px;"></td>
                <td><input style="width:95%;" type="text" name="" id="" value="" maxlength="200" readonly="true" title=""/></td>
            </tr>
            <tr>
                <td style="width:90px;text-align: right;padding-top: 13px;">开始时间:</td>
                <td><input style="width:95%;" type="text" name="start_time" id="start_time" value="${pd.start_time}" maxlength="200" readonly="true" title=""/></td>
                <td style="width:90px;text-align: right;padding-top: 13px;">结束时间:</td>
                <td><input style="width:95%;" type="text" name="finish_time" id="finish_time" value="${pd.finish_time}" maxlength="200" readonly="true" title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务审核人:</td>
                <td><input style="width:90%;" type="text" name="auditor" id="auditor" value="${pd.auditor}" size="18" maxlength="200"  title=""></td>
                <td style="width:90px;text-align: right;padding-top: 13px;">审核时间</td>
                <td><input style="width:95%;" type="text" name="auditor_time" id="auditor_time" value="${pd.auditor_time}" maxlength="200" readonly="true" title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">审核意见:</td>
                <td>
                    <textarea cols="50" rows="10" name="auditor_opinion" id="auditor_opinion" value="${pd.auditor_opinion}"></textarea>
                </td>
            </tr>
            <tr>
                <td style="width:90px;text-align: right;padding-top: 13px;">拒单理由</td>
                <td><input style="width:95%;" type="text" name="refuse_reason" id="refuse_reason" value="${pd.refuse_reason}" maxlength="200" readonly="true" title=""/></td>
                <td style="width:90px;text-align: right;padding-top: 13px;">拒单时间</td>
                <td><input style="width:95%;" type="text" name="refuse_time" id="refuse_time" value="${pd.refuse_time}" maxlength="200" readonly="true" title=""/></td>
            </tr>
            <tr>
                <td style="width:90px;text-align: right;padding-top: 13px;">下达时间</td>
                <td><input style="width:95%;" type="text" name="send_time" id="send_time" value="${pd.send_time}" maxlength="200" readonly="true" title=""/></td>
                <td style="width:90px;text-align: right;padding-top: 13px;">接收时间</td>
                <td><input style="width:95%;" type="text" name="accept_time" id="accept_time" value="${pd.accept_time}" maxlength="200" readonly="true" title=""/></td>
            </tr>
        </table>
    </div>

</form>
<tr>
    <label class="control-label" style="margin-left:45%;margin-top: 5px;margin-bottom: 5px">该任务所包含事件</label>
</tr>
<table id="event" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
      <%--  <th class="center">
            <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
        </th>--%>
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
                        <%--<td class='center' style="width: 30px;">
                            <label><input type='checkbox' id="${var.event_id}" name='ids' value="${var.event_id}"/><span class="lbl"></span></label>
                        </td>--%>
                        <td class='center' style="width: 30px;">${vs.index+1}</td>
                        <td style="width: 60px;" class="center"> ${var.workshop}</td>
                        <td style="width: 139px;" class="center">${var.check_scope}</td>
                        <td style="width: 60px;" class="center">${var.check_point}</td>
                        <td style="width: 100px;" class="center">${var.event_name}</td>
                        <td style="width: 60px;" class="center">${var.instrument_place}</td>
                        <td style="width: 60px;" class="center"><a href="<%=basePath%>taskmag/getWorkContentDetail.do?event_id=${var.event_id}&mission_id=${id}">查看详情</a></td>
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

<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>

<script type="text/javascript">
    $(top.hangge());  //清除加载进度,关闭遮罩层
    $(function() {

        //单选框
        $(".chzn-select").chosen();
        $(".chzn-select-deselect").chosen({allow_single_deselect:true});

        //日期框
        $('.date-picker').datepicker();

    });
</script>

</body>
</html>
