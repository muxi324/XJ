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
            if($("#area_name").val()==""){
                $("#area_name").tips({
                    side:3,
                    msg:'请输入巡检区域名称',
                    bg:'#AE81FF',
                    time:2
                });
                $("#area_name").focus();
                return false;
            }
            if($("#area_filed").val()==""){
                $("#area_filed").tips({
                    side:3,
                    msg:'请输入巡检区域范围',
                    bg:'#AE81FF',
                    time:2
                });
                $("#area_filed").focus();
                return false;
            }
            if($("#workshop").val()==""){
                $("#workshop").tips({
                    side:3,
                    msg:'请输入所属车间名称',
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
<form action="inspt_area/${msg }.do" id="Form"   method="post">
    <input type="hidden" name="id" id="id" value="${pd.id }"/>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">所属车间:</td>
                <td><input style="width:95%;" type="text" name="workshop" id="workshop" value="${pd.workshop}" maxlength="100" placeholder="这里输入所属车间" title=""/></td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">巡检区域名称:</td>
                <td><input style="width:95%;" type="text" name="area_name" id="area_name" value="${pd.area_name}" maxlength="100" placeholder="这里输入巡检区域名称" title=""/></td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">巡检区域范围:</td>
                <td><input style="width:95%;" type="text" name="area_field" id="area_field" value="${pd.area_field}" maxlength="150" placeholder="这里输入巡检区域范围" title=""/></td>
            </tr>

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

