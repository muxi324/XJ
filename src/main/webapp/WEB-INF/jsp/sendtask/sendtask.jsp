<%--
  Created by IntelliJ IDEA.
  User: wp
  Date: 2017/12/28
  Time: 11:18
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
    <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
    <%@ include file="../system/admin/top.jsp"%>
    <link rel="stylesheet" href="static/css/bootstrap-datetimepicker.min.css" /><!-- 日期框 -->
    <link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" />
    <link rel="stylesheet" type="text/css" href="plugins/webuploader/style.css" />
</head>
<body>
<form action="sendtask/sendTask.do" id="Form"   method="post">
    <input type="hidden" name="set_id" id="set_id" value="${pd.set_id }"/>
    <input type="hidden" name="event" id="event" value="${pd.event }"/>
    <label class="control-label" style="margin-left:45%;margin-top: 10px;margin-bottom: 20px">下发日常巡检任务</label>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务名称:</td>
                <td><input style="width:90%;" type="text" name="mission" id="mission" value="${pd.mission}" maxlength="200"  title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务类型:</td>
                <td><input style="width:90%;" type="text" name="mission_type" id="mission_type" value="日常巡检任务" maxlength="200" placeholder="日常巡检任务" title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务级别:</td>
                <td><select name="mission_level" id="mission_level" class="form-control" value="${pd.mission_level}">
                    <option value="1级">1级</option>
                    <option value="2级">2级</option>
                    <option value="3级">3级</option>
                </select>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务来源:</td>
                <td><input style="width:90%;" type="text" name="mission_source" id="mission_source" value="${pd.mission_source}" maxlength="200"  title=""/></td>
            </tr>
            <%--三级联动--%>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">班组:</td>
                <td><select name="team" id="team" class="form-control" value="${pd.team}" onchange="groupchoose()">
                    <option value="0">选择</option>
                    <c:forEach items="${teamList}" var="t">
                        <option value="${t.team }">${t.team }</option>
                    </c:forEach>
                 <%--   <option value="1班组">1班组</option>
                    <option value="2班组">2班组</option>
                    <option value="3班组">3班组</option>--%>
                </select></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">检修员工:</td>
                <td><select name="worker_name" id="worker_name" class="form-control" value="${pd.worker_name}" onchange="phonechoose()">
                        <option value="">请先选择班组</option>

                </select></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">手机:</td>
                <td><select  name="worker_phone" id="worker_phone" class="form-control" value="${pd.worker_phone}" >
                    </select></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">开始时间:</td>
                <td><input style="width:90%;" type="text" class="datetimepicker" name="period_start_time" id="period_start_time" value="${pd.period_start_time}" maxlength="200" data-date-format="yyyy-mm-dd  hh:mm" title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">结束时间:</td>
                <td><input style="width:90%;" type="text" class="datetimepicker" name="period_end_time" id="period_end_time" value="${pd.period_end_time}" maxlength="200" data-date-format="yyyy-mm-dd  hh:mm" title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">时间偏差:</td>
                <td><input style="width:90%;" type="text" name="time_dev" id="time_dev" value="${pd.time_dev}" maxlength="150"  title=""/>小时</td>
            </tr>
            <tr>
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
<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="static/js/bootstrap-datetimepicker.min.js"></script><!-- 日期框 -->
<script type="text/javascript" src="plugins/webuploader/webuploader.js"></script>
<script type="text/javascript" src="plugins/webuploader/upload.js"></script>

<script type="text/javascript">

    $(top.hangge());
    $(function() {

        //单选框
        $(".chzn-select").chosen();
        $(".chzn-select-deselect").chosen({allow_single_deselect:true});

        //日期框
        $('.datetimepicker').datetimepicker();

    });


    //保存
    function save(){
        if($("#worker_name").val()==""){
            $("#worker_name").tips({
                side:3,
                msg:'请选择检修员工姓名',
                bg:'#AE81FF',
                time:2
            });
            $("#worker_name").focus();
            return false;
        }
        if($("#mission").val()==""){
            $("#mission").tips({
                side:3,
                msg:'请输入任务名称',
                bg:'#AE81FF',
                time:2
            });
            $("#mission").focus();
            return false;
        }
        $("#Form").submit();
        //$("#zhongxin").hide();
        $("#zhongxin2").show();

    }
    //根据班组选择员工
    function groupchoose(){
        var group = document.getElementById('team');
        var index = group.selectedIndex;
        var senddata = group.options[index].value;
        //alert(senddata);
        var url ='<%=basePath%>sendtask/groupchoose.do';
        // alert(url);
        $.ajax({
            type:'POST',
            url: url,
            dataType: 'json',
            data:{'groupdata':senddata
            },
            success: function (data) {
                var workers = document.getElementById('worker_name');
                workers.options.length=0;
                var datalength = data.length;
                for(var i=0;i<datalength;i++){
                    workers.options.add(new Option(data[i].name));
                }
                /* alert(data.length);
                 alert(data[0].name);*/
                //console.log(workers);
            },
            error :function(){
                alert("未知错误！");
            }

        })
    };

    function phonechoose(){
        var worker = document.getElementById('worker_name');
        var index = worker.selectedIndex;
        var senddata = worker.options[index].value;
        //alert(senddata);
        var url ='<%=basePath%>sendtask/phonechoose.do';
        // alert(url);
        $.ajax({
            type:'POST',
            url: url,
            dataType: 'json',
            data:{'workerdata':senddata
            },
            success: function (data) {
                var phones = document.getElementById('worker_phone');
                phones.options.length=0;
                var datalength = data.length;
                for(var i=0;i<datalength;i++){
                    phones.options.add(new Option(data[i].phone))
                }
                /* alert(data.length);
                 alert(data[0].name);*/
               // console.log(phones);
            },
            error :function(){
                alert("未知错误！");
            }

        })
    };

</script>


</body>
</html>
