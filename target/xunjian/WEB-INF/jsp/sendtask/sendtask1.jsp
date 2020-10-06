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
</head>
<body>
<form action="sendtask/sendTask.do" id="Form"   method="post">
    <input type="hidden" name="mission_id" id="mission_id" value="${pd.mission_id }"/>
    <input type="hidden" name="exceptionId" id="exceptionId" value="${pd.id}"/> <!--异常id-->
    <input type="hidden" name="mission_condition" id="mission_condition" value="${pd.mission_condition }"/>
    <label class="control-label" style="margin-left:45%;margin-top: 10px;margin-bottom: 20px">下发临时巡检任务</label>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务名称:</td>
                <td><input style="width:90%;" type="text" name="mission_name" id="mission_name" value="${pd.mission_name}" maxlength="200"  title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务类型:</td>
                <td><input style="width:90%;" type="text" name="mission_type" id="mission_type" value="临时巡检任务" maxlength="200" placeholder="临时巡检任务" title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务级别:</td>
                <td><select name="mission_level" id="mission_level" class="form-control" value="${pd.mission_level}">
                    <option value="1级">1级</option>
                    <option value="2级">2级</option>
                    <option value="3级">3级</option>
                </select></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务来源:</td>
                <td><input style="width:90%;" type="text" name="mission_source" id="mission_source" value="${pd.mission_source}" maxlength="200"  title=""/></td>
            </tr>
            <%--三级联动--%>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">车间:</td>
                <td><select name="workshop_id" id="workshop_id" class="form-control"  onchange="chooseTeam()">
                    <option value="0">选择</option>
                    <c:forEach items="${workshopList}" var="w">
                        <option value="${w.id }">${w.workshop}</option>
                    </c:forEach>
                </select></td>
            </tr>
            <%--<tr>--%>
                <%--<td style="width:110px;text-align: right;padding-top: 13px;">班组:</td>--%>
                <%--<td><select name="team_id" id="team" class="form-control" value="${pd.team_id}" onchange="groupchoose()">--%>
                    <%--<option value="">请先选择车间</option>--%>
                    <%----%>
                <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">班组:</td>
                <td><select name="team_id" id="team_id" class="form-control"  onchange="groupchoose()">
                    <option value="0">选择</option>
                </select></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">检修员工:</td>
                <td><select name="worker_id" id="worker" class="form-control">
                    <option value="">请先选择班组</option>
                </select>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">预期任务开始时间:</td>
                <td><input  type="text" class="laydate-icon-danlan" name="mission_set_start_time" id="mission_set_start_time" value="" maxlength="200" title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">预期任务结束时间:</td>
                <td><input  type="text" class="laydate-icon-danlan" name="mission_set_finish_time" id="mission_set_finish_time" value="" maxlength="200"  title=""/></td>
            </tr>
            <tr hidden="hidden">
                <td style="width:110px;text-align: right;padding-top: 13px;">时间偏差:</td>
                <td><input style="width:30%;" type="text" name="time_dev" id="time_dev" value="${pd.time_dev}" maxlength="150"  title=""/>小时</td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">备注:</td>
                <td><textarea cols="100" rows="3" name="mission_addition" id="mission_addition" value="${pd.mission_addition}" ></textarea></td>
            </tr>
        </table>
            <h3 style="padding-left:20px;padding-top: 13px;">任务内容</h3>
            <div  class="form-group" >
                <table style="width:100%;"class="table table-striped table-bordered table-hover">
                    <tr>
                        <td style="width:110px;text-align: right;padding-top: 13px;">巡检事件:</td>
                        <td><input style="width:90%;" type="text" name="event" id="event" value="${pd.event}" maxlength="200"  title=""data-placeholder="请填写巡检事件"/></td>
                    </tr>
                    <tr>
                        <td style="width:110px;text-align: right;padding-top: 13px;">具体位置:</td>
                        <td><input style="width:90%;" type="text" name="instrument_place" id="instrument_place" value="${pd.instrument_place}" maxlength="200"  title=""/></td>
                    </tr>
                </table>
            </div>
        <table class="table table-striped table-bordered table-hover">
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
            elem: '#mission_set_start_time',
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
            elem: '#mission_set_finish_time',
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


    //保存
    function save(){
        if($("#team_id").val()==""){
            $("#team_id").tips({
                side:3,
                msg:'请选择检修员工姓名',
                bg:'#AE81FF',
                time:2
            });
            $("#team_id").focus();
            return false;
        }
        if($("#mission_name").val()==""){
            $("#mission_name").tips({
                side:3,
                msg:'请输入任务名称',
                bg:'#AE81FF',
                time:2
            });
            $("#mission_name").focus();
            return false;
        }

        $("#Form").submit();
        $("#zhongxin").hide();
        $("#zhongxin2").show();
       /* $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            url: "<%=basePath%>sendtask/sendTask.do" ,//url
            data: $('#Form').serialize(),
            success: function (result) {
                //打印服务端返回的数据(调试用)
                alert("下发临时任务成功！");
            },
            error : function() {
                alert("出现异常！");
            }
        });*/

    }
    //根据车间选择班组
    function chooseTeam(){
        var  workshop_id = $("#workshop_id").children('option:selected').val();
        var url ='<%=basePath%>sendtask/chooseTeam.do';
        $.ajax({
            type:'POST',
            url: url,
            dataType: 'json',
            data:{ 'workshop_id':workshop_id
            },
            success: function (data) {
                var teamList = document.getElementById('team_id');
                //teamList.options.length=0;
                var datalength = data.length;
                for(var i=0;i<datalength;i++){
                    teamList.options.add(new Option(data[i].team, data[i].id));
                }

            },
            error :function(){
                alert("未知错误！");
            }

        })
    };
    //根据班组选择员工
    function groupchoose(){
        var  teamId = $("#team_id").children('option:selected').val();
        var url ='<%=basePath%>sendtask/groupchoose.do';
        console.log("teamId"+teamId);
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
                    // $("#worker").append("<option value=data[i].id>data[i].team</option>")
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

    function selectMaterial(){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="选择物资";
        diag.URL = '<%=basePath%>sendtask/goselectMaterial.do';
        diag.Width = 400;
        diag.Height = 450;
        diag.CancelEvent = function(){ //关闭事件
            if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                if('${page.currentPage}' == '0'){
                    top.jzts();
                    setTimeout("self.location=self.location",100);
                }else{
                    nextPage(${page.currentPage});
                }
            }
            diag.close();
        };
        diag.show();
    }


</script>


</body>
</html>
