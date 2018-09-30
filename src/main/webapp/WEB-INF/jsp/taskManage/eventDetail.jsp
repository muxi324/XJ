<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <base href="<%=basePath%>">
    <%@ include file="../system/admin/top.jsp"%>
</head>
<body>
<form  id="Form">
    <label class="control-label" style="margin-left:45%;margin-top: 10px;margin-bottom: 20px">添加巡检事件</label>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">事件id:</td>
                <td><input style="width:90%;" type="text" name="event_id" id="event_id" value="${pd.event_id}" maxlength="200"  readonly/></td>

            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">事件名称:</td>
                <td><input style="width:90%;" type="text" name="event_name" id="event_name" value="${pd.event_name}" maxlength="200" readonly/></td>
                <input type="hidden" name="font_color" id="font_color" value="#000000"/>
                <input type="hidden" name="font_size" id="font_size" value="20"/>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">所属车间:</td>
                <td>
                    <input style="width:90%;" type="text" name="workshop"   maxlength="200" value="${pd.workshop}" readonly/>
                </td>
            </tr>
           <%-- <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">所属巡检点:</td>
                <td>
                    <input style="width:90%;" type="text" name="check_point" id="check_point"  maxlength="200" value="${pd.check_point}" data-placeholder="事件所在的设备名称（可以不填）" >
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">所属巡检范围:</td>
                <td>
                    <input style="width:90%;" type="text" name="check_scope" id="check_scope"  maxlength="200" value="${pd.check_scope}" data-placeholder="事件所在的生产区域（可以不填）">
                </td>
            </tr>--%>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">具体位置:</td>
                <td>
                <input style="width:90%;" type="text" name="instrument_place" id="instrument_place"  maxlength="200" value="${pd.instrument_place}"  readonly/>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">事件级别:</td>
                <td>
                    <input style="width:90%;" type="text" name="event_level" id="event_level"  maxlength="200" value="${pd.event_level}" readonly />
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">创建时间:</td>
                <td>
                    <input style="width:90%;" type="text" name="create_time" id="create_time"  maxlength="200" value="${pd.create_time}" readonly />
                </td>
            </tr>
        <%--    <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">建议巡检周期:</td>
                <td>
                    <input style="width:90%;" type="text" name="check_period" id="check_period"  maxlength="200" value="${pd.check_period}" title=""/>小时/次
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">巡检间隔:</td>
                <td>
                    大于<input style="width:90%;" type="text" name="check_interval" id="check_interval"  maxlength="200" value="${pd.check_interval}" title=""/>小时
                </td>
                </td>
            </tr>--%>

            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">与该事件绑定的工作内容:</td>
                <td>
                <c:choose>
                    <c:when test="${not empty contentList}">
                        <c:forEach items="${contentList}" var="var" varStatus="vs">
                          ${var}&nbsp&nbsp&nbsp
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                         无
                    </c:otherwise>
                </c:choose>
                </td>
            </tr>

        </table>
        <div style="padding-bottom: 15px;">
        </div>
    </div>
    <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
</form>
</body>
<script type="text/javascript">


</script>
</html>


