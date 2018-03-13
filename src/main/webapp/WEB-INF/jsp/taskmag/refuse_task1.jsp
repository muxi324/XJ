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
    <title></title>
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
<form action="taskmag/${msg }.do" name="Form" id="Form" method="post">
    <input type="hidden" name="mission_id" id="mission_id" value="${pd.mission_id}"/>
    <label class="control-label" style="margin-left:45%">任务单审核</label>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover" style="width:100%;">
            <tr>
                <td style="width:90px;text-align: right;padding-top: 13px;">任务联单号:</td>
                <td><input style="width:95%;" type="text" name="flow_number" id="flow_number" value="${pd.flow_number}" maxlength="200" readonly="true" title=""/></td>
                <td style="width:90px;text-align: right;padding-top: 13px;">任务状态:</td>
                <td><input style="width:95%;" type="text" name="mission_condition" id="mission_condition" value="${pd.mission_condition}"
                           <c:if test="${pd.mission_condition==1}">selected</c:if>拒单  maxlength="200" readonly="true" title=""/>
                </td>
            </tr>
            <tr>
                <td style="width:90px;text-align: right;padding-top: 13px;">任务性质:</td>
                <td><input style="width:95%;" type="text" name="mission_type" id="mission_type" value="${pd.mission_type}" maxlength="200" readonly="true" title=""/></td>
                <td style="width:90px;text-align: right;padding-top: 13px;">任务级别:</td>
                <td><input style="width:95%;" type="text" name="mission_level" id="mission_level" value="${pd.mission_level}" maxlength="200" readonly="true" title=""/></td>
            </tr>
            <tr>
                <td style="width:90px;text-align: right;padding-top: 13px;">班组:</td>
                <td><input style="width:95%;" type="text" name="team" id="team" value="${pd.team}" maxlength="200" readonly="true" title=""/></td>
                <td style="width:90px;text-align: right;padding-top: 13px;">安装人员:</td>
                <td><input style="width:95%;" type="text" name="worker_name" id="worker_name" value="${pd.worker_name}" maxlength="200" readonly="true" title=""/></td>
            </tr>
            <tr>
                <td style="width:90px;text-align: right;padding-top: 13px;">手机:</td>
                <td><input style="width:95%;" type="text" name="worker_phone" id="worker_phone" value="${pd.worker_phone}" maxlength="200" readonly="true" title=""/></td>
                <td style="width:90px;text-align: right;padding-top: 13px;">预期开始时间:</td>
                <td><input style="width:95%;" type="text" name="set_start_time" id="set_start_time" value="${pd.set_start_time}" maxlength="200" readonly="true" title=""/></td>
            </tr>
            <tr>
                <td style="width:90px;text-align: right;padding-top: 13px;">预期结束时间:</td>
                <td><input style="width:95%;" type="text" name="set_finish_time" id="set_finish_time" value="${pd.set_finish_time}" maxlength="200" readonly="true" title=""/></td>
                <td style="width:90px;text-align: right;padding-top: 13px;">房源编号:</td>
                <td><input style="width:95%;" type="text" name="house_id" id="house_id" value="${pd.house_id}" maxlength="200" readonly="true" title=""/></td>
            </tr>
            <tr>
                <td style="width:90px;text-align: right;padding-top: 13px;">房源地址:</td>
                <td><input style="width:95%;" type="text" name="house_address" id="house_address" value="${pd.house_address}" maxlength="200" readonly="true" title=""/></td>
                <td style="width:90px;text-align: right;padding-top: 13px;">门锁号:</td>
                <td><input style="width:95%;" type="text" name="lock_code" id="lock_code" value="${pd.lock_code}" maxlength="200" readonly="true" title=""/></td>
            </tr>
            <tr>
                <td style="width:90px;text-align: right;padding-top: 13px;">锁的类型:</td>
                <td><input style="width:95%;" type="text" name="lock_type" id="lock_type" value="${pd.lock_type}" maxlength="200" readonly="true" title=""/></td>
                <td style="width:90px;text-align: right;padding-top: 13px;">锁的型号:</td>
                <td><input style="width:95%;" type="text" name="lock_model" id="lock_model" value="${pd.lock_model}" maxlength="200" readonly="true" title=""/></td>

            </tr>
            <tr>
                <td style="width:90px;text-align: right;padding-top: 13px;">门的类型:</td>
                <td><input style="width:95%;" type="text" name="door_type" id="door_type" value="${pd.door_type}" maxlength="200" readonly="true" title=""/></td>
                <td style="width:90px;text-align: right;padding-top: 13px;">备注:</td>
                <td><input style="width:95%;" type="text" name="mission_addition" id="mission_addition" value="${pd.mission_addition}" maxlength="200" readonly="true" title=""/></td>
            </tr>

            <tr>
                <td style="width:90px;hight:250px; text-align: right;padding-top: 13px;">门锁照片</td>
                <td ><img style="width:95%;height:250px" src="uploadFiles/uploadImgs/lock_pic"></td>
            </tr>

            <tr>
                <td style="width:90px;text-align: right;padding-top: 13px;">拒单理由</td>
                <td><input style="width:95%;" type="text" name="refuse_reason" id="refuse_reason" value="${pd.refuse_reason}" maxlength="200" readonly="true" title=""/></td>
                <td style="width:90px;text-align: right;padding-top: 13px;">拒单时间</td>
                <td><input style="width:95%;" type="text" name="refuse_time" id="refuse_time" value="${pd.refuse_time}" maxlength="200" readonly="true" title=""/></td>
            </tr>
            <tr>

                <td style="width:90px;text-align: right;padding-top: 13px;">审核人</td>
                <td><input style="width:95%;" type="text" name="auditor" id="auditor" value="${pd.USERNAME}" maxlength="200"  title=""/></td>
                <td style="width:90px;text-align: right;padding-top: 13px;">审核意见</td>
                <td><input style="width:95%;" type="text" name="auditor_opinion" id="auditor_opinion" value="${pd.auditor_opinion}" maxlength="200"  title=""/></td>
            </tr>


            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-mini btn-primary" onclick="resend('${pd.house_id }');">重新下发任务</a>
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
<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
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
<script type="text/javascript">

    //重新下发任务
    function resend(Id){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="下发任务";
        diag.URL = '<%=basePath%>taskmag/refuse.do?house_id='+Id;
        diag.Width = 600;
        diag.Height = 800;
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
