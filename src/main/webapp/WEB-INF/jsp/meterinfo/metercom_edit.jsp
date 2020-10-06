<%--
  Created by IntelliJ IDEA.
  User: LHF
  Date: 2019/10/25
  Time: 20:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
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
            // if($("#workshop").val()==""){
            //     $("#workshop").tips({
            //         side:3,
            //         msg:'请输入工厂名称',
            //         bg:'#AE81FF',
            //         time:2
            //     });
            //     $("#workshop").focus();
            //     return false;
            // }

            if($("#factory_name").val()==""){
                $("#factory_name").tips({
                    side:3,
                    msg:'请输入公司名称',
                    bg:'#AE81FF',
                    time:2
                });
                $("#factory_name").focus();
                return false;
            }
            if($("#address").val()==""){
                $("#address").tips({
                    side:3,
                    msg:'请输入公司地址',
                    bg:'#AE81FF',
                    time:2
                });
                $("#address").focus();
                return false;
            }


            $("#Form").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }
    </script>
</head>
<body>
<form action="meter_com/${msg}.do" id="Form"   method="post">
    <label class="control-label" style="margin-left:40%;margin-top: 20px;margin-bottom: 10px">添加公司列表</label>
<%--    <input type="hidden" name="id" id="id" value="${pd.id}"/>--%>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <input type="hidden" name="id" id="id" value="${pd.id}"/>
            <tr>
            <td style="width:100px;text-align: right;padding-top: 13px;">公司名称:</td>
            <td><input style="width:95%;" type="text" name="factory_name" id="factory_name" value="${pd.factory_name}" maxlength="150" placeholder="这里输入公司名称" title=""/></td>
          </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">公司地址:</td>
                <td><input style="width:95%;" type="text" name="address" id="address" value="${pd.address}" maxlength="150" placeholder="这里输入公司地址" title=""/></td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">代理人:</td>
                <td><input style="width:95%;" type="text" name="representative" id="representative" value="${pd.representative}" maxlength="150" placeholder="这里输入代理人的名字" title=""/></td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">联系电话:</td>
                <td><input style="width:95%;" type="text" name="phone" id="phone" value="${pd.phone}" maxlength="150" placeholder="这里代理人的联系电话" title=""/></td>
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
