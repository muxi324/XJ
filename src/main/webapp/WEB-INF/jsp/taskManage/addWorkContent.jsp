<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <%@ include file="../system/admin/top.jsp"%>
    <link rel="stylesheet" href="static/css/bootstrap-datetimepicker.min.css" /><!-- 日期框 -->
    <link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" />
    <link rel="stylesheet" type="text/css" href="plugins/webuploader/style.css" />
</head>
<body>
<form action="/eventManage/addWorkContent.do" id="Form"   method="post">
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

                </td>
                <td style="width:110px;text-align: right;padding-top: 13px;">数值类正常范围:</td>
                <td>
                    <input style="width:90%;" type="text" name="backNum_upLimit" id="backNum_upLimit" placeholder="数值上限" maxlength="100"  title=""/>
                    <input style="width:90%;" type="text" name="backNum_downLimit" id="backNum_downLimit" placeholder="数值下限"  maxlength="100"  title=""/>
                </td>
                <td style="width:110px;text-align: right;padding-top: 13px;">正常范围数值类的字体、大小、颜色:</td>
                <td>
                    <select name="fontClass" id="fontClass" class="form-control">
                        <option value="宋体">宋体</option>
                        <option value="楷体">楷体</option>
                    </select>
                    <select name="fontColor" id="fontColor" class="form-control">
                        <option value="red">红色</option>
                        <option value="blue">蓝色</option>
                    </select>
                    <select name="fontSize" id="fontSize" class="form-control">
                        <option value="14">14</option>
                        <option value="15">15</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">是否需要拍照:</td>
                <td>
                    <input style="width:90%;" type="text" name="is_takePhoto" id="is_takePhoto"  maxlength="200"  title=""/>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">是否需要反馈文字:</td>
                <td>
                    <input style="width:90%;" type="text" name="is_backText" id="is_backText"  maxlength="200"  title=""/>
                </td>
            </tr>
            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-small btn-primary" onclick="save();">保存</a>&nbsp;&nbsp;&nbsp;
                    <a class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
                </td>
            </tr>
        </table>
    </div>
    <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
</form>
</body>
<%@ include file="../system/admin/bottom.jsp"%>
</html>
