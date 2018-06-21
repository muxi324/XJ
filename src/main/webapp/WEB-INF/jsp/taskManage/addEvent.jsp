<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <link rel="stylesheet" href="static/css/bootstrap-datetimepicker.min.css" /><!-- 日期框 -->
    <link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" />
    <link rel="stylesheet" type="text/css" href="plugins/webuploader/style.css" />
</head>
<body>
<form  id="Form">
    <label class="control-label" style="margin-left:45%;margin-top: 10px;margin-bottom: 20px">添加巡检事件</label>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">事件名称:</td>
                <td><input style="width:90%;" type="text" name="event_name" id="event_name" value="${pd.event_name}" maxlength="200"  title=""/></td>
                <input type="hidden" name="font_color" id="font_color" value="#000000"/>
                <input type="hidden" name="font_size" id="font_size" value="20"/>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">所属车间:</td>
                <td>
                    <select name="workshop" id="workshop" class="form-control" value="${pd.workshop}">
                        <option value="0">选择</option>
                        <c:forEach items="${workshopList}" var="W">
                            <option value="${W.workshop }">${W.workshop }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">所属巡检点:</td>
                <td>
                    <input style="width:90%;" type="text" name="check_point" id="check_point"  maxlength="200" value="${pd.check_point}" >
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">所属巡检范围:</td>
                <td>
                    <input style="width:90%;" type="text" name="check_scope" id="check_scope"  maxlength="200" value="${pd.check_scope}" >
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">具体位置:</td>
                <td>
                <input style="width:90%;" type="text" name="instrument_place" id="instrument_place"  maxlength="200" value="${pd.instrument_place}" title=""/>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">事件级别:</td>
                <td>
                    <input style="width:90%;" type="text" name="event_level" id="event_level"  maxlength="200"  value="${pd.event_level}" title=""/>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">建议巡检周期:</td>
                <td>
                    <input style="width:90%;" type="text" name="check_period" id="check_period"  maxlength="200" value="${pd.check_period}" title=""/>小时/次
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">巡检间隔:</td>
                <td>
                    大于<input style="width:90%;" type="text" name="check_interval" id="check_interval"  maxlength="200" value="${pd.check_interval}" title=""/>小时
                </td>
                </td>
            </tr>
            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-small btn-primary" onclick="save();">保存并添加工作内容</a>&nbsp;&nbsp;&nbsp;
                    <a class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">与该事件绑定的工作内容:</td>
                <td>
                <c:choose>
                    <c:when test="${not empty contentList}">
                        <c:forEach items="${contentList}" var="var" varStatus="vs">
                          ${var}&nbsp&nbsp&nbsp
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                         无
                    </c:otherwise>
                </c:choose>
                </td>
            </tr>

        </table>
        <div style="padding-bottom: 15px;">
        </div>
    </div>
    <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
</form>
</body>
<script type="text/javascript">

    $(document).ready(function(){
        if($("#event_name").val() != "") {
            var trHtml = "<tr>" +
                "<td id = 'addContent'>" +
                "<a class='btn btn-small btn-primary' onclick='addWorkContent()'>新增工作内容</a>" +
                "</td>" +
                "</tr>";
            $("#table_report").append(trHtml);
        }
    });
    //保存
    function save(){
        if($("#event_name").val()==""){
            $("#event_name").tips({
                side:3,
                msg:'请填写事件名称',
                bg:'#AE81FF',
                time:2
            });
            $("#event_name").focus();
            return false;
        }
/*        if($("#mission").val()==""){
            $("#mission").tips({
                side:3,
                msg:'请输入任务名称',
                bg:'#AE81FF',
                time:2
            });
            $("#mission").focus();
            return false;
        }*/
        //$("#Form").submit();
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            url: "<%=basePath%>eventManage/addEvent1.do" ,//url
            data: $('#Form').serialize(),
            success: function (result) {
                //打印服务端返回的数据(调试用)
                alert("保存事件成功，请为事件添加工作内容！");
            },
            error : function() {
                alert("出现异常！");
            }
        });
        if ($('#addContent').length <= 0) {
            var trHtml = "<tr>" +
                "<td id = 'addContent'>" +
                "<a class='btn btn-small btn-primary' onclick='addWorkContent()'>新增工作内容</a>" +
                "</td>" +
                "</tr>";
            $("#table_report").append(trHtml);
        }
        //$("#zhongxin").hide();
       // $("#zhongxin2").show();
    }

    function addWorkContent() {
        if($("#event_name").val()==""){
            $("#event_name").tips({
                side:3,
                msg:'请填写事件名称',
                bg:'#AE81FF',
                time:2
            });
            $("#event_name").focus();
            return false;
        }
        var eventName = $("#event_name").val();
        location.href = "<%=basePath%>eventManage/addWorkContent.do?eventName="+eventName;
    }
</script>
<%@ include file="../system/admin/bottom.jsp"%>
</html>


