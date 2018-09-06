<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 2018/3/12
  Time: 17:30
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
    <base href="<%=basePath%>">
    <%@ include file="../system/admin/top.jsp"%>
</head>
<body>
<div class="container-fluid" id="main-container">
    <div id="page-content" class="clearfix">
        <div class="row-fluid">
            <!-- 检索  -->
            <form action="exception/exceptionList.do" method="post" name="Form" id="Form">
                <table>
                    <tr>
                        <td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="enquiry" value="${pd.enquiry }" placeholder="这里输入内容" />
							<i id="nav-input-icon" class="icon-search"></i>
						</span>
                        </td>
                        <td><input class="span10 date-picker" name="reportTimeStart" id="reportTimeStart" value="${pd.reportTimeStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期"/></td>
                        <td><input class="span10 date-picker" name="reportTimeEnd" id="reportTimeEnd" value="${pd.reportTimeEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期"/></td>
                        <td style="vertical-align:top;">
                            <select class="chzn-select" name="level" id="level" placeholder="请选择异常级别" style="vertical-align:top;width: 120px;">
                                <option value="">全部</option>
                                <option value="1" <c:if test="${pd.level==1}">selected</c:if> >问题型</option>
                                <option value="2" <c:if test="${pd.level==2}">selected</c:if> >隐患型</option>
                                <option value="3" <c:if test="${pd.level==1}">selected</c:if> >报警型</option>
                            </select>
                        </td>
                        <%--<c:if test="${QX.cha == 1 }">--%>
                            <td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
                           <%-- <c:if test="${QX.edit == 1 }">
                                <td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="fromExcel();" title="从EXCEL导入"><i id="nav-search-icon" class="icon-cloud-upload"></i></a></td>
                                <td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
                            </c:if>--%>
                        <%--</c:if>--%>
                    </tr>
                </table>
            <table id="table_report" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>

                    <th class="center">序号</th>
                    <th class="center">所属车间</th>
                  <%--  <th class="center">所属巡检点</th>--%>
                    <th class="center">异常级别</th>
                    <th class="center">描述</th>
                    <th class="center">异常上报人</th>
                    <th class="center">上报时间</th>
                    <th class="center">异常状态</th>
                    <%--<th class="center">是否已读</th>--%>
                    <th class="center">操作</th>
                </tr>
                </thead>

                <tbody>
                <!-- 开始循环 -->
                <c:choose>
                    <c:when test="${not empty exceptionList}">
                        <%--<c:if test="${QX.cha == 1 }">--%>
                            <c:forEach items="${exceptionList}" var="var" varStatus="vs">
                                <tr>
                                    <td class='center' style="width: 20px;">${vs.index+1}</td>
                                    <td style="width: 60px;" class="center">${var.workshop}</td>
                                <%--    <td style="width: 60px;" class="center">${var.checkpoint}</td>--%>
                                    <td style="width: 30px;" class="center">
                                        <c:if test="${var.level==1}"> 问题型</c:if>
                                        <c:if test="${var.level==2}"> 隐患型</c:if>
                                        <c:if test="${var.level==3}"> 报警型</c:if>
                                    </td>
                                    <td style="width: 139px;" class="center">${var.description}</td>
                                    <td style="width: 60px;" class="center">${var.report_worker}</td>
                                    <td style="width: 100px;" class="center">${var.report_time}</td>
                                    <td style="width: 50px;" class="center">
                                        <c:if test="${var.status == '1' }"><span class="label label-warning   arrowed-in">未处理</span></c:if>
                                        <c:if test="${var.status == '2' }"><span class="label label-success   arrowed-in">已处理</span></c:if>
                                    </td>
                                   <%-- <td style="width: 20px;" class="center">
                                        <c:if test="${var.is_read == 0}">否</c:if>
                                        <c:if test="${var.is_read == 1}">是</c:if>
                                    </td>--%>
                                    <td style="width: 60px;" class="center">
                                        <a target="mainFrame" href="<%=basePath%>exception/getExceptionDetail.do?exceptionId=${var.id}">查看详情</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        <%--</c:if>--%>
                        <c:if test="${QX.cha == 0 }">
                            <tr>
                                <td colspan="100" class="center">您无权查看</td>
                            </tr>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <tr class="main_info">
                            <td colspan="100" class="center" >没有相关数据</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
            <div class="page-header position-relative">
                <table style="width:100%;">
                    <tr>
                        <td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
                    </tr>
                </table>
            </div>
            </form>
        </div>
    </div>
</div>
<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>

<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
<!-- 引入 -->
<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
<script type="text/javascript">
    $(top.hangge());
<%--console.log(${exceptionList});--%>
    //检索
    function search(){
        top.jzts();
        $("#Form").submit();
    }



    $(function() {
        //下拉框
        $(".chzn-select").chosen();
        $(".chzn-select-deselect").chosen({allow_single_deselect:true});

        //日期框
        $('.date-picker').datepicker();

        //复选框
        $('table th input:checkbox').on('click' , function(){
            var that = this;
            $(this).closest('table').find('tr > td:first-child input:checkbox')
                .each(function(){
                    this.checked = that.checked;
                    $(this).closest('tr').toggleClass('selected');
                });
        });
    });


</script>
</body>
</html>
