<%--
  Created by IntelliJ IDEA.
  User: wp
  Date: 2018/3/14
  Time: 16:34
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
            /*if($("#name").val()==""){
                $("#name").tips({
                    side:3,
                    msg:'请输入姓名',
                    bg:'#AE81FF',
                    time:2
                });
                $("#name").focus();
                return false;
            }
            if($("#phone").val()==""){
                $("#phone").tips({
                    side:3,
                    msg:'请输入手机号',
                    bg:'#AE81FF',
                    time:2
                });
                $("#phone").focus();
                return false;
            }*/
            if($("#workshop").val()==""){
                $("#workshop").tips({
                    side:3,
                    msg:'请输入车间名称',
                    bg:'#AE81FF',
                    time:2
                });
                $("#workshop").focus();
                return false;
            }

            $("#Form").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }




    </script>
</head>
<body>
<form action="factory/${msg }.do" id="Form"   method="post">
    <input type="hidden" name="id" id="id" value="${pd.id }"/>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">

            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">工厂名称:</td>
                <td><input style="width:95%;" type="text" name="factory" id="factory" value="${pd.factory}" maxlength="100" placeholder="这里输入工厂名称" title=""/></td>
            </tr>
           <%-- <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">姓名:</td>
                <td><input style="width:95%;" type="text" name="name" id="name" value="${pd.name}" maxlength="100" placeholder="这里输入姓名" title=""/></td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">联系电话:</td>
                <td><input style="width:95%;" type="text" name="phone" id="phone" value="${pd.phone}" maxlength="150" placeholder="这里输入电话" title=""/></td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">职位:</td>
                <td><input style="width:95%;" type="text" name="post" id="post" value="${pd.post}" maxlength="150" placeholder="这里输入职位" title=""/></td>
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

