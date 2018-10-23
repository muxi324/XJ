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
    <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
    <%@ include file="../system/admin/top.jsp"%>
</head>
<body>
<c:if test="${not empty errorMsg}">
    ${errorMsg}
</c:if>
<div id="zhongxin">
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
                <td><input style="width:90%;" type="text" name="auditor" id="auditor" value="${NAME}" size="18" maxlength="200"  title=""></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">审核意见:</td>
                <td>
                    <textarea cols="50" rows="10" name="opinion" id="opinion" placeholder="在这里输入内容..."></textarea>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">审核结果:</td>
                <td>
                    <select class="form-control" name="mission_condition" id="mission_condition" value="">
                        <option value="6">审核通过</option>
                        <option value="7">审核未通过</option>
                    </select>
                </td>
            </tr>
            <tr id="p1">
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-small btn-primary" onclick="save();">保存</a>
                </td>
            </tr>
            <tr id="p2" hidden="hidden">
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-small btn-primary" onclick="resend();">保存并重新下发任务</a>
                </td>
            </tr>
        </table>
    </form>
    <h3 style="padding-left:20px;padding-top: 13px;">任务内容</h3>
    <table style="width:100%;"class="table table-striped table-bordered table-hover">
        <tr>
            <td style="width:110px;text-align: right;padding-top: 13px;">异常id:</td>
            <td><input type="text" name="exceptionId" id="exceptionId" value="${exp.id}"/> <!--异常id--></td>
        </tr>
        <tr>
            <td style="width:110px;text-align: right;padding-top: 13px;">异常巡检事件id:</td>
            <td><input style="width:90%;" type="text" name="event" id="event" value="${exp.event}" maxlength="200"  title=""/></td>
        </tr>
        <tr>
            <td style="width:110px;text-align: right;padding-top: 13px;">具体位置:</td>
            <td><input style="width:90%;" type="text" name="instrument_place" id="instrument_place" value="${exp.instrument_place}" maxlength="200"  title=""/></td>
        </tr>
        <tr>
            <td style="width:110px;text-align: right;padding-top: 13px;">异常上报人:</td>
            <td><input style="width:90%;" type="text" name="report_worker" id="report_worker" value="${exp.report_worker}" maxlength="200"  title=""/></td>
        </tr>
        <tr>
            <td style="width:110px;text-align: right;padding-top: 13px;">上报时间:</td>
            <td><input style="width:90%;" type="text" name="report_time" id="report_time" value="${exp.report_time}" maxlength="200"  title=""/></td>
        </tr>
        <tr>
            <td style="width:110px;text-align: right;padding-top: 13px;">异常级别:</td>
            <td><input style="width:90%;" type="text" name="level" id="level"  maxlength="200"
                    <c:if test="${pd.level==1}"> 问题型</c:if>
                    <c:if test="${pd.level==2}"> 隐患型</c:if>
                    <c:if test="${pd.level==3}"> 报警型</c:if> />
            </td>
        </tr>
        <tr>
            <td style="width:110px;text-align: right;padding-top: 13px;">异常描述:</td>
            <td><input style="width:90%;" type="text" name="description" id="description" value="${exp.description}" maxlength="200"  title=""/></td>
        </tr>
        <tr>
            <td style="width:110px;text-align: right;padding-top: 13px;">建议措施:</td>
            <td><input style="width:90%;" type="text" name="suggestion" id="suggestion" value="${exp.suggestion}" maxlength="200"  title=""/></td>
        </tr>
        <tr>
            <td style="width:110px;text-align: right;padding-top: 13px;">异常照片:</td>
            <td><img style="height:250px" src="/imgFile/${exp.pic}" width="210"></td>
        </tr>

        <tr>
            <td style="width:110px;text-align: right;padding-top: 13px;">维修后照片:</td>
            <td><img style="height:250px" src="/imgFile/${con.pic}" width="210"></td>
        </tr>
        <tr>
            <td style="width:110px;text-align: right;padding-top: 13px;">备注:</td>
            <td><input style="width:90%;" type="text" name="data"  value="${con.data}" maxlength="200"  title=""/></td>
        </tr>
    </table>
</table>
</div>
<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
</body>
</html>
<script type="text/javascript">
    $(top.hangge());

    $("#mission_condition").change(function(){
        var options = $(this).children("option:selected").val(); //获取选中的项的值
        if(options == "6") {
            $("#p1").removeAttr("hidden");
            $("#p2").attr("hidden","hidden");
        }else if(options == "7"){
            $("#p2").removeAttr("hidden");
            $("#p1").attr("hidden","hidden");
        }
    });
    //重新下发任务
    function resend(){
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            url: "<%=basePath%>taskmag/auditMisson.do" ,//url
            data: $('#Form').serialize(),
            success: function (result) {
            },
            error : function() {
                alert("出现异常！");
            }
        });
        var missionId= $("#missionId").val();
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="下发任务";
        diag.URL = '<%=basePath%>taskmag/noPass.do?missionId='+missionId;
        diag.Width = 700;
        diag.Height = 800;
        diag.CancelEvent = function(){ //关闭事件
            if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                /*if('${page.currentPage}' == '0'){
                    top.jzts();
                    setTimeout("self.location=self.location",100);
                }else{
                    window.location.href='<%=basePath%>taskmag/list.do';
                }*/
            }
            diag.close();
        };
        diag.show();
    }



    function save(){
       // $("#Form").submit();
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            url: "<%=basePath%>taskmag/auditMisson.do" ,//url
            data: $('#Form').serialize(),
            success: function (result) {
                //打印服务端返回的数据(调试用)
                alert("任务审核成功！");

            },
            error : function() {
                alert("出现异常！");
            }
        });
    }
</script>