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
    <%--<link rel="stylesheet" href="static/laydate/need/laydate.css" /><!-- 日期框 -->--%>
</head>
<body>
<form action="sendtask/sendPeriodTask.do" id="Form"   method="post">
    <input type="hidden" name="set_id" id="set_id" value="${pd.set_id }"/>
    <input type="hidden" name="event" id="event" value="${pd.event }"/>
    <input type="hidden" name="mission_condition" id="mission_condition" value="${pd.mission_condition }"/>
    <input type="hidden" name="setTime" id="setTime" value="${pd.set_time }"/>
    <label class="control-label" style="margin-left:45%;margin-top: 10px;margin-bottom: 20px">下发日常巡检任务</label>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务名称:</td>
                <td><input style="width:90%;" readonly = "readonly" type="text" name="mission_name" id="mission_name" value="${pd.mission_name}" maxlength="200" /></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务类型:</td>
                <td><input style="width:90%;" readonly = "readonly"  type="text" name="mission_type" id="mission_type" value="日常巡检任务" maxlength="200" placeholder="日常巡检任务" /></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务级别:</td>
                <td><select name="mission_level" id="mission_level" class="form-control" readonly = "readonly" value="${pd.mission_level}">
                    <option value="1级">1级</option>
                    <option value="2级">2级</option>
                    <option value="3级">3级</option>
                </select>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务来源:</td>
                <td><input style="width:90%;" readonly = "readonly"  type="text" name="mission_source" id="mission_source" value="${pd.mission_source}" maxlength="200" /></td>
            </tr>
            <%--三级联动--%>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">班组:</td>
                <td>
                    <select  name="team_id" id="team" value="${pd.team_id}" class="form-control" data-placeholder="请选择班组" onchange="groupchoose()" >
                    <option value="">请选择</option>
                    <c:forEach items="${teamList}" var="W">
                        <option value="${W.id }" <c:if test="${W.id == pd.team_id}">selected</c:if>>${W.team}</option>
                    </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">检修员工:</td>
                <td>
                    <%--<select name="worker_id" id="worker" class="form-control" value="${pd.worker_id}" onchange="phonechoose()">--%>
                    <select name="worker_id" id="worker" class="form-control" value="${pd.worker_id}">
                        <option value="">请先选择班组</option>
                    </select>
                </td>
            </tr>
           <%-- <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">手机:</td>
                <td>
                    <input  type="text"  name="worker_phone" id="worker_phone"  value="${pd.worker_phone}" />
                </td>
            </tr>--%>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务启动方式:</td>
                <td>
                    <select  name="misson_start_type" id="misson_start_type" class="form-control">
                        <option value="period">周期启动</option>
                        <option value="startEnd">按开始结束时间启动</option>
                    </select>
                </td>
            </tr>
            <tr id="p1">
                <td style="width:110px;text-align: right;padding-top: 13px;">请选择任务执行的周期:</td>
                <td>
                <select name="cron" id="cron" class="form-control">
                    <option value="">请选择</option>
                    <option value="0 0 9 * * ? * ">每天一次(每天九点下发任务)</option>
                    <option value="0 0 9,15 * * ? *">每天两次（早九点，下午三点下发任务）</option>
                    <option value="0 0 9 ? * MON-FRI ">工作日一天一次(每天九点下发任务)</option>
                    <option value="0 0 9,15 ? * MON-FRI ">工作日半天一次（早九点，下午三点下发任务）</option>
                    <option value="0 0 9 ? * SAT,SUN">周末一天一次(每天九点下发任务)</option>
                    <option value="0 0 9,15 ? * SAT,SUN">周末半天一次（早九点，下午三点下发任务）</option>
                </select>
                </td>
            </tr>
            <tr id="s1" hidden="hidden">
                <td style="width:110px;text-align: right;padding-top: 13px;">开始时间:</td>
                <td><input  type="text" class="laydate-icon-danlan" name="period_start_time" id="period_start_time" value=""  title=""/></td>
            </tr>
            <tr id="s2" hidden="hidden">
                <td style="width:110px;text-align: right;padding-top: 13px;">结束时间:</td>
                <td><input  type="text" class="laydate-icon-danlan" name="period_end_time" id="period_end_time" value=""   title=""/></td>
            </tr>
            <tr  hidden="hidden">
                <td style="width:110px;text-align: right;padding-top: 13px;">时间偏差:</td>
                <td><input style="width:30%;" type="text" name="time_dev" id="time_dev" value="${pd.time_dev}" maxlength="150"  title=""/>小时</td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">备注:</td>
                <td><textarea cols="100" rows="3" name="mission_addition" id="mission_addition" value="${pd.mission_addition}" ></textarea></td>
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
<script type="text/javascript" src="static/laydate/laydate.js"></script>


<script type="text/javascript">

    $(top.hangge());

    $(function() {

        //单选框
        $(".chzn-select").chosen();
        $(".chzn-select-deselect").chosen({allow_single_deselect:true});

        //日期框
        var start = {
            elem: '#period_start_time',
            format: 'YYYY-MM-DD hh:mm:ss',
            min: laydate.now(0,"YYYY-MM-DD hh:mm:ss"), //设定最小日期为当前日期
            max: '2099-06-16 23:59:59', //最大日期
            istime: true,
            istoday: false,
            choose: function(datas){
                end.min = datas; //开始日选好后，重置结束日的最小日期
            }
        };
        var end = {
            elem: '#period_end_time',
            format: 'YYYY-MM-DD hh:mm:ss',
            //  min: laydate.now(),
            max: '2099-06-16 23:59:59',
            istime: true,
            istoday: false,
            choose: function(datas){
                start.max = datas; //结束日选好后，重置开始日的最大日期
            }
        };
        laydate(start);
        laydate(end);
        laydate.skin('danlan');
    });


    $('#misson_start_type').change(function(){
        var startType=$(this).children('option:selected').val();
        if (startType == "period") {
            $('#s1').attr("hidden","hidden");
            $('#s2').attr("hidden","hidden");
            $('#p1').removeAttr("hidden");
        } else if (startType == "startEnd") {
            $('#p1').attr("hidden","hidden");
            $('#s1').removeAttr("hidden");
            $('#s2').removeAttr("hidden");
        }
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
        $("#zhongxin").hide();
        $("#zhongxin2").show();
      /*  $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            url: "<%=basePath%>sendtask/sendPeriodTask.do" ,//url
            data: $('#Form').serialize(),
            success: function (result) {
                //打印服务端返回的数据(调试用)
                alert("下发任务成功！");
            },
            error : function() {
                alert("出现异常！");
            }
        });*/

    }
    //根据班组选择员工
    function groupchoose(){
        var  teamId = $("#team").children('option:selected').val();
        var url ='<%=basePath%>sendtask/groupchoose.do';
        $.ajax({
            type:'POST',
            url: url,
            dataType: 'json',
            data:{ 'team_id':teamId
            },
            success: function (data) {
                var workers = document.getElementById('worker');
                workers.options.length=0;
                var datalength = data.length;
                for(var i=0;i<datalength;i++){
                    workers.options.add(new Option(data[i].NAME+":"+data[i].PHONE,data[i].USER_ID));
                }

            },
            error :function(){
                alert("未知错误！");
            }

        })
    };

    //通过员工获取手机号
    function phonechoose(){
        var worker = document.getElementById('worker');
        var index = worker.selectedIndex;
        var senddata = worker.options[index].value;
        //alert(senddata);
        var url ='<%=basePath%>sendtask/phonechoose.do';
        $.ajax({
            type:'POST',
            url: url,
            dataType: 'json',
            data:{'workerdata':senddata
            },
            success: function (data) {
                $('#worker_phone').val(data[0].PHONE);
            },
            error :function(){
                alert("未知错误！");
            }

        })
    };

</script>


</body>
</html>
