<%--
  Created by IntelliJ IDEA.
  User: wp
  Date: 2018/3/28
  Time: 11:31
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

            <form action="toolsmag/goInput.do" method="post" name="Form" id="Form">
                <input type="hidden" name="material_id" id="material_id" value="${pd.material_id }"/>
                <input type="hidden" name="material_name" id="material_name" value="${pd.material_name }"/>
                <div id="zhongxin">
                <table>
                    <tr>
                        <c:if test="${QX.cha == 1 }">
                            <td><input class="span10 date-picker" name="sendTimeStart" id="sendTimeStart" value="${pd.sendTimeStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期"/></td>
                            <td><input class="span10 date-picker" name="sendTimeEnd" id="sendTimeEnd" value="${pd.sendTimeEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期"/></td>
                            <td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
                        </c:if>
                    </tr>
                </table>
                <%--检索--%>
                <table id="table_report" class="table table-striped table-bordered table-hover">

                    <thead>
                    <tr>
                        <%--<th class="center">
                            <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
                        </th>--%>
                        <th class="center">序号</th>
                        <th class="center">入库数量</th>
                        <th class="center">入库时间</th>
                        <th class="center">负责人</th>
                        <th class="center">操作人</th>
                    </tr>
                    </thead>

                    <tbody>

                    <!-- 开始循环 -->
                    <c:choose>
                        <c:when test="${not empty varList}">
                            <c:if test="${QX.cha == 1 }">
                                <c:forEach items="${varList}" var="var" varStatus="vs">
                                    <tr>
                                       <%-- <td class='center' style="width: 30px;">
                                            <label><input type='checkbox' name='ids' value="${var.material_id}" /><span class="lbl"></span></label>
                                        </td>--%>
                                        <td class='center' style="width: 30px;">${vs.index+1}</td>
                                        <td style="width: 60px;" class="center">${var.material_num}</td>
                                        <td style="width: 100px;" class="center">${var.time}</td>
                                        <td style="width: 60px;" class="center">${var.worker_name}</td>
                                        <td style="width: 100px;" class="center">${var.operater}</td>
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
                            <td style="vertical-align:top;">
                                <c:if test="${QX.add == 1 }">
                                    <a class="btn btn-small btn-success" onclick="add('${pd.material_id}','${pd.material_name}');">入库</a>
                                </c:if>
                            </td>
                            <td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
                        </tr>
                    </table>
                </div>
                </div>
            </form>

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

    //入库
    function add(material_id,material_name){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="入库";
        diag.URL = '<%=basePath%>toolsmag/goInputStorage.do?material_id='+material_id+'&material_name='+material_name;
        diag.Width = 400;
        diag.Height = 300;
        diag.CancelEvent = function(){ //关闭事件
            if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
               if('${page.currentPage}' == '0'){
                    top.jzts();
                    setTimeout("self.location=self.location",100);
                }else{
                    nextPage(${page.currentPage});
                }
            }

            window.location.href='<%=basePath%>toolsmag/goInput.do?material_id='+material_id;
            diag.close();
        };
        diag.show();
    }

</script>
</body>
</html>
