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
    <link rel="stylesheet" href="static/css/bootstrap-datetimepicker.min.css" /><!-- 日期框 -->
    <link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" />
    <link rel="stylesheet" type="text/css" href="plugins/webuploader/style.css" />
</head>
<body>
<form action="taskManage/addEvent.do" id="Form"   method="post">
    <label class="control-label" style="margin-left:45%">添加巡检事件</label>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">事件名称:</td>
                <td><input style="width:90%;" type="text" name="event_name" id="event_name"  maxlength="200"  title=""/></td>
                <td style="width:110px;text-align: right;padding-top: 13px;">所属车间:</td>
                <td>
                    <select name="workshop" id="workshop" class="form-control">
                    <option value="1车间">1车间</option>
                    <option value="2车间">2车间</option>
                    <option value="3车间">3车间</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">所属巡检点:</td>
                <td>
                    <select name="check_point" id="check_point" class="form-control">
                    <option value="巡检点1">巡检点1</option>
                    <option value="巡检点2">巡检点2</option>
                    <option value="巡检点3">巡检点3</option>
                    </select>
                </td>
                <td style="width:110px;text-align: right;padding-top: 13px;">所属巡检范围:</td>
                <td>
                    <select name="check_scope" id="check_scope" class="form-control">
                        <option value="范围1">范围1</option>
                        <option value="范围2">范围2</option>
                        <option value="范围3">范围3</option>
                    </select>
                </td>
            </tr>
            <%--三级联动--%>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">具体位置:</td>
                <td>
                <input style="width:90%;" type="text" name="instrument_place" id="instrument_place"  maxlength="200"  title=""/>
                </td>
                <td style="width:110px;text-align: right;padding-top: 13px;">检修员工:</td>
                <td><select name="worker_name" id="worker_name" class="form-control" value="${pd.worker_name}" onchange="phonechoose()">
                    <option value="张三">张三</option>
                    <option value="张思">张思</option>
                    <option value="孙武">孙武</option>
                </select>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">手机:</td>
                <td><input style="width:90%;" type="text" name="worker_phone" id="worker_phone" value="${pd.worker_phone}" maxlength="200"  title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">开始时间:</td>
                <td><input style="width:90%;" type="text" class="datetimepicker" name="period_start_time" id="period_start_time" value="${pd.period_start_time}" maxlength="200" data-date-format="yyyy-mm-dd  hh:mm" title=""/></td>
                <td style="width:110px;text-align: right;padding-top: 13px;">结束时间:</td>
                <td><input style="width:90%;" type="text" class="datetimepicker" name="period_end_time" id="period_end_time" value="${pd.period_end_time}" maxlength="200" data-date-format="yyyy-mm-dd  hh:mm" title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">时间偏差:</td>
                <td><input style="width:90%;" type="text" name="time_dev" id="time_dev" value="${pd.time_dev}" maxlength="150"  title=""/>小时</td>
                <td style="width:110px;text-align: right;padding-top: 13px;">备注:</td>
                <td><textarea cols="40" rows="6" name="mission_addition" id="mission_addition" value="${pd.mission_addition}" ></textarea></td>
            </tr>

            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-small btn-primary" onclick="save();">下发</a>&nbsp;&nbsp;&nbsp;
                    <a class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
                </td>
            </tr>
        </table>
    </div>
    <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
</form>
</body>
<%@ include file="../system/admin/bottom.jsp"%>
</html>
