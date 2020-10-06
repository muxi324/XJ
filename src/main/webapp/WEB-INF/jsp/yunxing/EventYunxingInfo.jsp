<%--
  Created by IntelliJ IDEA.
  User: yeliang99
  Date: 2019/3/20
  Time: 17:47
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
            <form action="yunxing/list.do" method="post" name="Form" id="Form">

               <%--搜索--%>
                <table>
                    <tr>
                        <%--<td>--%>
						<%--<span class="input-icon">--%>
							<%--<input autocomplete="off" id="nav-search-input" type="text" name="enquiry" value="" placeholder="这里输入内容" />--%>
							<%--<i id="nav-input-icon" class="icon-search"></i>--%>
						<%--</span>--%>
                        <%--</td>--%>
                        <%--<td><input class="span10 date-picker" name="reportTimeStart" id="reportTimeStart" value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期"/></td>--%>
                        <%--<td><input class="span10 date-picker" name="reportTimeEnd" id="reportTimeEnd" value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期"/></td>--%>
                        <%--<td style="vertical-align:top;">--%>
                            <%--<select class="chzn-select" name="level" id="level" placeholder="请选择异常级别" style="vertical-align:top;width: 120px;">--%>
                                <%--<option value="">全部</option>--%>
                                <%--<option value="1" <c:if test="${pd.level==1}">selected</c:if> >问题型</option>--%>
                                <%--<option value="2" <c:if test="${pd.level==2}">selected</c:if> >隐患型</option>--%>
                                <%--<option value="3" <c:if test="${pd.level==1}">selected</c:if> >报警型</option>--%>
                            <%--</select>--%>
                        <%--</td>--%>
                        <%--<c:if test="${QX.cha == 1 }">--%>
                        <%--<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>--%>
                        <%--<c:if test="${QX.edit == 1 }">--%>
                            <%--<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="fromExcel();" title="从EXCEL导入"><i id="nav-search-icon" class="icon-cloud-upload"></i></a></td>--%>

                            <td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL">点击下载该表格<i id="nav-search-icon" class="icon-download-alt"></i></a></td>
                        <%--</c:if>--%>
                        <%--</c:if>--%>
                    </tr>
                </table>

                <table id="table_report" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>


                        <%--&lt;%&ndash;  <th class="center">所属巡检点</th>&ndash;%&gt;--%>
                        <%--<th class="center">排放指数</th>--%>
                        <%--<th class="center">日排放吨数</th>--%>
                        <%--<th class="center">余氯量</th>--%>
                        <%--<th class="center">运行情况</th>--%>
                        <%--<th class="center">操作</th>--%>
                        <c:choose>
                            <c:when test="${not empty contentList}">
                                <th class="center">日期</th>
                                <th class="center">值班时间</th>
                                <c:forEach items="${contentList}" var="var" varStatus="vs">
                                    <th class="center">${var}</th>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <th class="center">该事件下展未添加工作内容！</th>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    </thead>

                    <tbody>
                    <!-- 开始循环 -->
                    <c:choose>
                        <c:when test="${not empty resultmap}">
                            <%--<c:if test="${QX.cha == 1 }">--%>
                            <c:forEach items="${resultmap}" var="result" varStatus="vs">
                                <tr>
                                    <td class='center' style="width: 20px;">${fn:split(result.key,"_")[0]}</td>
                                    <td class='center' style="width: 20px;">${fn:split(result.key,"_")[1]}</td>
                                    <%--<td class='center' style="width: 20px;">${result.value}</td>--%>
                                <c:forEach items="${result.value}" var="sresult" varStatus="vs">
                                    <%--<td class='center' style="width: 20px;">${sresult.work_name}</td>--%>
                                    <td class='center' style="width: 20px;">${sresult.data}</td>
                                </c:forEach>

                                    <%--<td class='center' style="width: 20px;">${var.time}</td>--%>
                                    <%--<td class='center' style="width: 20px;">${var.time}</td>--%>
                                    <%--<td style="width: 60px;" class="center">${var.work_name}</td>--%>
                                    <%--<td style="width: 139px;" class="center">${var.data}</td>--%>
                                    <%--<td style="width: 60px;" class="center">${var.YuLvLiang}</td>--%>
                                    <%--<td style="width: 60px;" class="center">${var.YunXingQK}</td>--%>
                                    <%--<td style="width: 60px;" class="center">--%>
                                        <%--<a class='btn btn-mini btn-info' title="详情"  onclick="check('${var.id}');"><i class='icon-edit'></i></a>--%>
                                            <%--&lt;%&ndash;<a  href="<%=basePath%>exception/getExceptionDetail.do?exceptionId=${var.id}">查看详情</a>&ndash;%&gt;--%>
                                    <%--</td>--%>
                                </tr>
                            </c:forEach>
                            <%--</c:if>--%>
                            <%--<c:if test="${QX.cha == 0 }">--%>
                                <%--<tr>--%>
                                    <%--<td colspan="100" class="center">您无权查看</td>--%>
                                <%--</tr>--%>
                            <%--</c:if>--%>
                        </c:when>
                        <c:otherwise>
                            <tr class="main_info">
                                <td colspan="100" class="center" >该事件尚无运行记录！</td>
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

    //检索
    function search(){
        top.jzts();
        $("#Form").submit();
    }



    $(function() {

        // var url = document.forms[0].getAttribute("action");
        // alert(url);
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

    <%--function check(Id){--%>
        <%--top.jzts();--%>
        <%--var diag = new top.Dialog();--%>
        <%--diag.Drag=true;--%>
        <%--diag.Title ="详情";--%>
        <%--diag.URL = '<%=basePath%>exception/getExceptionDetail.do?exceptionId='+Id;--%>
        <%--diag.Width = 700;--%>
        <%--diag.Height = 800;--%>
        <%--diag.CancelEvent = function(){ //关闭事件--%>
            <%--if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){--%>
                <%--if('${page.currentPage}' == '0'){--%>
                    <%--top.jzts();--%>
                    <%--setTimeout("self.location=self.location",100);--%>
                <%--}else{--%>
                    <%--nextPage(${page.currentPage});--%>
                <%--}--%>
            <%--}--%>
            <%--diag.close();--%>
        <%--};--%>
        <%--diag.show();--%>
    <%--}--%>

    //导出excel
    function toExcel(){
        window.location.href='<%=basePath%>yunxing/excel.do?eventId='+'${pd.eventId}';
    }


</script>
</body>
</html>

