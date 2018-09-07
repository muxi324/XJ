<%--
  Created by IntelliJ IDEA.
  User: wp
  Date: 2018/4/2
  Time: 9:29
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
    <script type="text/javascript">


        //保存
        function save(){
            if($("#material_num").val()==""){
                $("#material_num").tips({
                    side:3,
                    msg:'请输入数量',
                    bg:'#AE81FF',
                    time:2
                });
                $("#material_num").focus();
                return false;
            }
            if($("#worker_name").val()==""){
                $("#worker_name").tips({
                    side:3,
                    msg:'请输入领取人姓名',
                    bg:'#AE81FF',
                    time:2
                });
                $("#worker_name").focus();
                return false;
            }
            if(parseInt($("#material_num").val()) > parseInt($("#stock").val())){
                $("#material_num").tips({
                    side:3,
                    msg:'当前库存不足',
                    bg:'#AE81FF',
                    time:2
                });
                $("#material_num").focus();
                return false;
            }

            $.ajax({
                //几个参数需要注意一下
                type: "POST",//方法类型
                url: "<%=basePath%>toolsmag/save1.do" ,//url
                data: $('#Form').serialize(),
                success: function (result) {
                    //打印服务端返回的数据(调试用)
                    alert("出库成功！");
                    window.location.href='<%=basePath%>toolsmag/goOutput.do';
                },
                error : function() {
                    alert("出现异常！");
                }
            });

            $("#zhongxin").hide();
            $("#zhongxin2").show();

        }

    </script>
</head>
<body>
<form action="toolsmag/${msg}.do" id="Form"   method="post">
    <input type="hidden" name="material_id" id="material_id" value="${pd.material_id }"/>
    <input type="hidden" name="material_name" id="material_name" value="${pd.material_name }"/>
    <input type="hidden" name="stock" id="stock" value="${pd.stock}"/>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">

            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">数量:</td>
                <td><input style="width:95%;" type="text" name="material_num" id="material_num" value="${pd.material_num}" maxlength="150" placeholder="这里输入数量" title=""/></td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">领取人:</td>
                <td><input style="width:95%;" type="text" name="worker_name" id="worker_name" value="${pd.worker_name}" maxlength="150" placeholder="这里输入负责人" title=""/></td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">用途:</td>
                <td><input style="width:95%;" type="text" name="aim" id="aim" value="${pd.aim}" maxlength="150" placeholder="这里输入用途" /></td>
            </tr>
          <%--  <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">所用于任务:</td>
                <td><input style="width:95%;" type="text" name="mission_id" id="mission_id" value="${pd.mission_id}" maxlength="150" placeholder="这里输入所用于任务编号" title=""/></td>
            </tr>--%>
           <%-- <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">所用于事件:</td>
                <td><input style="width:95%;" type="text" name="event_id" id="event_id" value="${pd.event_id}" maxlength="150" placeholder="这里输入所用于事件编号" title=""/></td>
            </tr>--%>

            <div style=" clear:both; padding-top: 40px;"></div>
            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-mini btn-primary" onclick="save();">保存</a>&nbsp;&nbsp;&nbsp;
                    <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
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



<script type="text/javascript">
    $(top.hangge());
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

