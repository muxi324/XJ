<%--
  Created by IntelliJ IDEA.
  User: 帥哥无限叼
  Date: 2019/10/27
  Time: 19:54
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
<form action="meter/${msg}.do" id="Form"   method="post">
    <input type="hidden" name="id" id="id" value="${pd.id}"/>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">仪表名称:</td>
                <td><input style="width:95%;" type="text" name="meter_name" id="meter_name" value="${pd.meter_name}" maxlength="100" placeholder="这里输入水表名称" title=""/></td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">仪表位置:</td>
                <td><input style="width:95%;" type="text" name="location" id="location" value="${pd.location}" maxlength="100" placeholder="这里输入水表位置" title=""/></td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">仪表编号:</td>
                <td><input style="width:95%;" type="text" name="meter_id" id="meter_id" value="${pd.meter_id}" maxlength="100" placeholder="这里输入水表编号" title=""/></td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">公司名称:</td>
                <td>
                    <select  name="com_id" id="com_id" value="${pd.id}" class="form-control" data-placeholder="请选择水表公司" onchange="groupchoose()" >
                        <option value="">请选择</option>
                        <c:forEach items="${comList}" var="W">
                            <option value="${W.id }" <c:if test="${W.id == pd.id}">selected</c:if>>${W.factory_name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">是否开启:</td>
                <td><select name="state" id="state" class="form-control" value="${pd.state}">
                    <option value="0">不开启</option>
                    <option value="1">开启</option>
                </select></td>
            </tr>
            <div style=" clear:both; padding-top: 40px;"></div>
            <tr>
                <td style="text-align: center;" colspan="10">
                    <c:if test="${QX.cha == 1 }">
                        <c:if test="${user.USERNAME != 'admin'}">
                            <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                            <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                        </c:if>
                        <c:if test="${user.USERNAME == 'admin'}">
                            <a class="btn btn-mini btn-primary" title="您不能使用" >保存</a>
                            <a class="btn btn-mini btn-danger"title="您不能使用">取消</a>
                        </c:if>
                    </c:if>
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
