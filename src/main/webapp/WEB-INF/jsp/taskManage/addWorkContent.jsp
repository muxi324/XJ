<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <%@ include file="../system/admin/top.jsp"%>
    <link rel="stylesheet" href="static/css/bootstrap-datetimepicker.min.css" /><!-- 日期框 -->
    <link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" />
    <link rel="stylesheet" type="text/css" href="plugins/webuploader/style.css" />
</head>
<body>
<form action="eventManage/addWorkContent.do" id="Form"   method="post">
    <label class="control-label" style="margin-left:45%">添加工作内容</label>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">工作内容名称:</td>
                <td><input style="width:90%;" type="text" name="content_name" id="content_name"  maxlength="200"  title=""/></td>
                <td style="width:110px;text-align: right;padding-top: 13px;">工作内容属性:</td>
                <td>
                    <select name="content_type" id="content_type" class="form-control">
                        <option value="检查">检查</option>
                        <option value="紧固">紧固</option>
                        <option value="调整">调整</option>
                        <option value="调整">润滑</option>
                        <option value="调整">卫生</option>
                    </select>
                </td>
            </tr>
            <tr >
                <td style="text-align: center;" colspan="10">
                    反馈类型
                </td>
            </tr>
            <%--三级联动--%>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">是否反馈数值:</td>
                <td>
                    <select name="is_backNum" id="is_backNum" class="form-control"  >
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </td>
                <td style="width:110px;text-align: right;padding-top: 13px;">数值类正常范围:</td>
                <td>
                    <input style="width:90%;" type="text" name="backNum_upLimit" id="backNum_upLimit" placeholder="数值上限" maxlength="100"  title=""/>
                    <input style="width:90%;" type="text" name="backNum_downLimit" id="backNum_downLimit" placeholder="数值下限"  maxlength="100"  title=""/>
                </td>
                <td style="width:110px;text-align: right;padding-top: 13px;">正常范围数值类的字体大小、颜色:</td>
                <td>
                    <%--   <select name="numFontClass" id="numFontClass" class="form-control">
                           <option value="宋体">宋体</option>
                           <option value="楷体">楷体</option>
                       </select>--%>
                    <select name="numFontColor" id="numFontColor" class="form-control">
                        <option value="#000000">黑色</option>
                        <option value="#FF0000">红色</option>
                        <option value="#0000FF">蓝色</option>
                    </select>
                    <select name="numFontSize" id="numFontSize" class="form-control">
                        <option value="18">18</option>
                        <option value="20">20</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">是否需要拍照:</td>
                <td>
                    <select name="is_takePhoto" id="is_takePhoto" class="form-control"  >
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">是否需要反馈文字:</td>
                <td>
                    <select name="is_backText" id="is_backText" class="form-control"  >
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </td>
            </tr>
           <%-- <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">是否需要反馈选项类:</td>
                <td>
                    <select name="is_backSelect" id="is_backSelect" class="form-control"  >
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </td>
                <td style="width:110px;text-align: right;padding-top: 13px;">选项标准:</td>
                <td><input style="width:90%;" type="text" name="select_content" id="select_content"  maxlength="200"  title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">是否需要反馈百分比:</td>
                <td>
                    <select name="is_backPercent" id="is_backPercent" class="form-control"  >
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </td>
                <td style="width:110px;text-align: right;padding-top: 13px;">百分比标准:</td>
                <td><input style="width:90%;" type="text" name="percent_content" id="percent_content"  maxlength="200"  title=""/></td>
            </tr>--%>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">特殊提示:</td>
                <td><input style="width:90%;" type="text" name="notice" id="notice"  maxlength="200"  title=""/></td>
                <td style="width:110px;text-align: right;padding-top: 13px;">特殊提示字体大小、颜色:</td>
                <td>
                    <%--  <select name="noticeFontClass" id="noticeFontClass" class="form-control">
                          <option value="宋体">宋体</option>
                          <option value="楷体">楷体</option>
                      </select>--%>
                    <select name="noticeFontColor" id="noticeFontColor" class="form-control">
                        <option value="#000000">黑色</option>
                        <option value="#FF0000">红色</option>
                        <option value="#0000FF">蓝色</option>
                    </select>
                    <select name="noticeFontSize" id="noticeFontSize" class="form-control">
                        <option value="18">18</option>
                        <option value="20">20</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">异常级别:</td>
                <td>
                    <select name="exceptionLevel" id="nexceptionLevel" class="form-control">
                        <option value="1">问题型</option>
                        <option value="2">隐患型</option>
                        <option value="3">报警型</option>
                    </select>
                </td>
                <td style="width:110px;text-align: right;padding-top: 13px;">异常标准:</td>
                <td><input style="width:90%;" type="text" name="exception" id="exception"  maxlength="200"  title=""/></td>
                <td style="width:110px;text-align: right;padding-top: 13px;">异常标准大小、颜色:</td>
                <td>
                    <%--<select name="exceptionFontClass" id="exceptionFontClass" class="form-control">
                        <option value="宋体">宋体</option>
                        <option value="楷体">楷体</option>
                    </select>--%>
                    <select name="exceptionFontColor" id="exceptionFontColor" class="form-control">
                        <option value="#000000">黑色</option>
                        <option value="#FF0000">红色</option>
                        <option value="#0000FF">蓝色</option>
                    </select>
                    <select name="exceptionFontSize" id="exceptionFontSize" class="form-control">
                        <option value="18">18</option>
                        <option value="20">20</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">建议措施:</td>
                <td><input style="width:90%;" type="text" name="advice" id="advice"  maxlength="200"  title=""/></td>
                <td style="width:110px;text-align: right;padding-top: 13px;">建议措施字体大小、颜色:</td>
                <td>
                    <%-- <select name="adviceFontClass" id="adviceFontClass" class="form-control">
                         <option value="宋体">宋体</option>
                         <option value="楷体">楷体</option>
                     </select>--%>
                    <select name="adviceFontColor" id="adviceFontColor" class="form-control">
                        <option value="#000000">黑色</option>
                        <option value="#FF0000">红色</option>
                        <option value="#0000FF">蓝色</option>
                    </select>
                    <select name="adviceFontSize" id="adviceFontSize" class="form-control">
                        <option value="18">18</option>
                        <option value="20">20</option>
                    </select>
                </td>
            </tr>
          <%--  <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">是否及时上报:</td>
                <td>
                    <select name="isTimelyReport" id="isTimelyReport" class="form-control">
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </td>
                <td style="width:110px;text-align: right;padding-top: 13px;">上报对象:</td>
                <td>
                    <select name="reportObject" id="reportObject" class="form-control">
                        <option value="班组长">班组长</option>
                        <option value="车间负责人">车间负责人</option>
                        <option value="工厂负责人">工厂负责人</option>
                    </select>
                </td>
            </tr>--%>
            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-small btn-primary" onclick="save();">保存</a>&nbsp;&nbsp;&nbsp;
                    <a class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
                </td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="eventName" id="eventName" value="${eventName}">
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

    //保存
    function save(){
        if($("#content_name").val()==""){
            $("#content_name").tips({
                side:3,
                msg:'请填写工作内容名称',
                bg:'#AE81FF',
                time:2
            });
            $("#content_name").focus();
            return false;
        }
        $("#Form").submit();
        $("#zhongxin").hide();
        $("#zhongxin2").show();

    }
</script>
</body>
</html>
