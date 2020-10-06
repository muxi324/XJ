<%--
  Created by IntelliJ IDEA.
  User: wp
  Date: 2017/12/18
  Time: 10:31
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

</head>
</body>
<div class="container-fluid" id="main-container">
    <div id="page-content" class="clearfix">
        <div class="row-fluid">
            <!-- 检索  -->
            <form action="taskmag/list.do" method="post" name="Form" id="Form">
                <table>
                    <tr>
                        <td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="enquiry" placeholder="这里输入内容" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
                        </td>
                        <td><input class="span10 date-picker" name="sendTimeStart" id="sendTimeStart"  type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期"/></td>
                        <td><input class="span10 date-picker" name="sendTimeEnd" id="sendTimeEnd"  type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期"/></td>
                        <td style="vertical-align:top;">
                            <select class="chzn-select" name="status" id="status" data-placeholder="请选择状态" style="vertical-align:top;width: 120px;">
                                <option value="">全部</option>
                                <option value="1" <c:if test="${pd.mission_condition==1}">selected</c:if>>任务已下发</option>
                                <option value="3" <c:if test="${pd.mission_condition==3}">selected</c:if>>接收未执行</option>
                                <option value="2" <c:if test="${pd.mission_condition==2}">selected</c:if>>拒收待处理</option>
                                <option value="4" <c:if test="${pd.mission_condition==4}">selected</c:if>>任务执行中</option>
                                <option value="5" <c:if test="${pd.mission_condition==5}">selected</c:if>>完成待审核</option>
                            </select>
                        </td>
                        <c:if test="${QX.cha == 1 }">
                            <td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
                           <%-- <c:if test="${QX.edit == 1 }">
                                <td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="fromExcel();" title="从EXCEL导入"><i id="nav-search-icon" class="icon-cloud-upload"></i></a></td>
                                <td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
                            </c:if>--%>
                        </c:if>
                    </tr>
                </table>
                <%--检索--%>
                <table id="table_report" class="table table-striped table-bordered table-hover">

                    <thead>
                    <tr>
                       <%-- <th class="center">
                            <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
                        </th>--%>
                        <th class="center">序号</th>
                        <th class="center">任务名称</th>
                        <th class="center">任务单号</th>
                        <th class="center">任务状态</th>
                        <th class="center">任务下达时间</th>
                        <th class="center">任务执行人</th>
                        <th class="center">任务执行人电话</th>
                        <th class="center">任务下达人</th>
                        <th class="center">任务级别</th>
                        <th class="center">任务类型</th>
                        <th class="center">操作</th>
                    </tr>
                    </thead>

                    <tbody>

                    <!-- 开始循环 -->
                    <c:choose>
                        <c:when test="${not empty varList}">
                            <c:if test="${QX.cha == 1 }">
                                <c:forEach items="${varList}" var="var" varStatus="vs">
                                    <tr>
                                        <%--<td class='center' style="width: 30px;">
                                            <label><input type='checkbox' name='ids' value="${var.mission_id}" /><span class="lbl"></span></label>
                                        </td>--%>
                                        <td class='center' style="width: 30px;">${vs.index+1}</td>
                                        <td style="width: 100px;" class="center">${var.mission_name}</td>
                                        <td style="width: 50px;" class="center">${var.id}</td>
                                        <td style="width: 50px;" class="center">
                                            <c:if test="${var.mission_condition == '1' }"><span class="label label-default   arrowed-in">任务待接收</span></c:if>
                                            <c:if test="${var.mission_condition == '2' }"><span class="label label-warning   arrowed-in">拒收待处理</span></c:if>
                                            <c:if test="${var.mission_condition == '3' }"><span class="label label-info   arrowed-in">接收未执行</span></c:if>
                                            <c:if test="${var.mission_condition == '4' }"><span class="label label-info      arrowed-in">任务执行中</span></c:if>
                                            <c:if test="${var.mission_condition == '5' }"><span class="label label-warning   arrowed-in">完成待审核</span></c:if>
                                            <c:if test="${var.mission_condition == '6' }"><span class="label label-success   arrowed-in">审核通过</span></c:if>
                                            <c:if test="${var.mission_condition == '7' }"><span class="label label-success      arrowed-in">审核未通过</span></c:if>
                                            <c:if test="${var.mission_condition == '8' }"><span class="label label-primary     arrowed-in">拒收已处理</span></c:if>
                                        </td>
                                        <td style="width: 100px;" class="center">${var.send_time}</td>
                                        <td style="width: 60px;" class="center">${var.worker_name}</td>
                                        <td style="width: 60px;" class="center">${var.worker_phone}</td>
                                        <td style="width: 60px;" class="center">${var.set_name}</td>
                                        <td style="width: 40px;" class="center">${var.mission_level}</td>
                                        <td style="width: 60px;" class="center">${var.mission_type}</td>
                                        <td style="width: 60px;" class="center">
                                            <div class='hidden-phone visible-desktop btn-group'>
                                                <c:if test="${QX.edit == 1 }">
                                                <c:if test="${user.USERNAME != 'admin'}"><a class='btn btn-mini btn-info' title="审核"  onclick="check('${var.id}');"><i class='icon-edit'></i></a></c:if>
                                                <c:if test="${user.USERNAME == 'admin'}"><a class='btn btn-mini btn-info' title="您不能审核"><i class='icon-edit'></i></a></c:if>
                                                </c:if>
                                            </div>
                                        </td>
                                        <!--
                                        <td style="width: 30px;" class="center">
                                            <a href="taskmag/getTaskLine.do?mission_id=">查看巡检路径</a>
                                        </td>-->
                                    </tr>
                                </c:forEach>
                            </c:if>
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
<!-- 返回顶部  -->
<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
    <i class="icon-double-angle-up icon-only"></i>
</a>
<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>

<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
<!-- 引入 -->
<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
<%--引入house_msg--%>
<script type="text/javascript" src="static/js/myjs/mission_msg.js"></script>
<script type="text/javascript">
    $(top.hangge());

    //检索
    function search(){
        top.jzts();
        $("#Form").submit();
    }
    //详情
    function check(Id){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="审核";
        diag.URL = '<%=basePath%>taskmag/goAuditTask.do?mission_id='+Id;
        diag.Width = 800;
        diag.Height = 800;
        diag.CancelEvent = function(){ //关闭事件
            if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                if('${page.currentPage}' == '0'){
                    top.jzts();
                    setTimeout("self.location=self.location",100);
                }else{
                    nextPage(${page.currentPage});
                }
            }
            diag.close();
        };
        diag.show();
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
