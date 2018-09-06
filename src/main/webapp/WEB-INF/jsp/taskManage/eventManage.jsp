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
<div class="container-fluid" id="main-container">
    <div id="page-content" class="clearfix">
        <div class="row-fluid">
            <!-- 检索  -->
            <form action="eventManage/list.do" method="post" name="Form" id="Form">
                <table>
                    <tr>
                        <td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="enquiry" value="" placeholder="这里输入关键词" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
                        </td>
                        <c:if test="${QX.cha == 1 }">
                            <td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
                            <%--<c:if test="${QX.edit == 1 }">
                                <td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="fromExcel();" title="从EXCEL导入"><i id="nav-search-icon" class="icon-cloud-upload"></i></a></td>
                                <td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
                            </c:if>--%>
                        </c:if>
                    </tr>
                </table>
                <!-- 检索  -->
                <table id="table_report" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="center">
                            <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
                        </th>
                        <th class="center">序号</th>
                        <th class="center">所属车间</th>
                        <th class="center">所属巡检区域</th>
                        <th class="center">所属巡检点</th>
                        <th class="center">事件名称</th>
                        <th class="center">具体位置</th>
                        <th class="center">创建时间</th>
                        <th class="center">二维码</th>
                     <%--   <th class="center">任务记录</th>--%>
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
                                        <td class='center' style="width: 30px;">
                                            <label><input type='checkbox' name='ids' value="${var.event_id}" /><span class="lbl"></span></label>
                                        </td>
                                        <td class='center' style="width: 30px;">${vs.index+1}</td>
                                        <td style="width: 60px;" class="center">${var.workshop}</td>
                                        <td style="width: 100px;" class="center">${var.check_scope}</td>
                                        <td style="width: 100px;" class="center">${var.check_point}</td>
                                        <td style="width: 100px;" class="center">${var.event_name}</td>
                                        <td style="width: 100px;" class="center">${var.instrument_place}</td>
                                        <td style="width: 100px;" class="center">${var.create_time}</td>
                                        <td style="width: 100px;" class="center"><a class='btn btn-mini btn-info' title="查看" onclick="showQrCode('${var.qrcode}');"><i class='icon-edit'></i></a></td>
                                       <%-- <td style="width: 100px;" class="center">查看操作记录</td>--%>
                                        <td style="width: 60px;" class="center">
                                            <div class='hidden-phone visible-desktop btn-group'>

                                                <c:if test="${QX.edit == 1 }">
                                                    <c:if test="${user.USERNAME != 'admin'}"><a class='btn btn-mini btn-info' title="编辑" onclick="location.href='<%=basePath%>eventManage/editEvent.do?eventId=${var.event_id}'"><i class='icon-edit'></i></a></c:if>
                                                    <c:if test="${user.USERNAME == 'admin'}"><a class='btn btn-mini btn-info' title="您不能编辑"><i class='icon-edit'></i></a></c:if>
                                                </c:if>
                                                &nbsp;&nbsp;&nbsp;
                                                <c:if test="${QX.del == 1 }">
                                                    <c:if test="${user.USERNAME != 'admin'}"><a class='btn btn-mini btn-danger' title="删除" onclick="del('${var.event_id}');"  data-placement="left"><i class="icon-trash"></i> </a></c:if>
                                                    <c:if test="${user.USERNAME == 'admin'}"><a class='btn btn-mini btn-danger' title="您不能编辑"><i class='icon-trash'></i></a></c:if>
                                                </c:if>

                                            </div>
                                        </td>
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
                                    <a class="btn btn-small btn-success" onclick="location.href='<%=basePath%>eventManage/addEvent.do'">新增</a>
                                </c:if>
                                <c:if test="${QX.del == 1 }">
                                    <a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='icon-trash'></i></a>
                                </c:if>
                            </td>
                            <td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
                        </tr>
                    </table>
                </div>
            </form>
            <!-- PAGE CONTENT ENDS HERE -->
        </div><!--/row-->

    </div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->

<!-- 返回顶部  -->
<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
    <i class="icon-double-angle-up icon-only"></i>
</a>

<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
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

    function showQrCode(url) {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="新增";
        diag.URL = '<%=basePath%>eventManage/qrcode.do?path='+url;
        diag.Width = 400;
        diag.Height = 600;
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

    //新增
    function add(){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="新增";
        diag.URL = '<%=basePath%>eventManage/addEvent.do';
        diag.Width = 400;
        diag.Height = 600;
        diag.CancelEvent = function(){ //关闭事件
            if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                if('${page.currentPage}' == '0'){
                    top.jzts();
                    setTimeout("self.location=self.location",100);
                }else{
                    prototype.nextPage(${page.currentPage});
                }
            }
            diag.close();
        };
        diag.show();
    }

    //删除
    function del(Id){
        bootbox.confirm("确定要删除吗?", function(result) {
            if(result) {
                top.jzts();
                var url = "<%=basePath%>eventManage/delete.do?event_id="+Id;
                $.get(url,function(data){
                   nextPage(${page.currentPage});
                });
            }
        });
    }

</script>

<script type="text/javascript">

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


    //批量操作
    function makeAll(msg){
        bootbox.confirm(msg, function(result) {
            if(result) {
                var str = '';
                for(var i=0;i < document.getElementsByName('ids').length;i++)
                {
                    if(document.getElementsByName('ids')[i].checked){
                        if(str=='') str += document.getElementsByName('ids')[i].value;
                        else str += ',' + document.getElementsByName('ids')[i].value;
                    }
                }
                if(str==''){
                    bootbox.dialog("您没有选择任何内容!",
                        [
                            {
                                "label" : "关闭",
                                "class" : "btn-small btn-success",
                                "callback": function() {
                                    //Example.show("great success");
                                }
                            }
                        ]
                    );

                    $("#zcheckbox").tips({
                        side:3,
                        msg:'点这里全选',
                        bg:'#AE81FF',
                        time:8
                    });

                    return;
                }else{
                    if(msg == '确定要删除选中的数据吗?'){
                        top.jzts();
                        $.ajax({
                            type: "POST",
                            url: '<%=basePath%>eventManage/deleteAll.do?tm='+new Date().getTime(),
                            data: {DATA_IDS:str},
                            dataType:'json',
                            //beforeSend: validateData,
                            cache: false,
                            success: function(data){
                                $.each(data.list, function(i, list){
                                    nextPage(${page.currentPage});
                                });
                            }
                        });
                    }
                }
            }
        });
    }

    //导出excel
    function toExcel(){
        window.location.href='<%=basePath%>worker/excel.do';
    }
    //打开上传excel页面
    function fromExcel(){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="EXCEL 导入到数据库";
        diag.URL = '<%=basePath%>eventManage/goUploadExcel.do';
        diag.Width = 300;
        diag.Height = 150;
        diag.CancelEvent = function(){ //关闭事件
            if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                if('${page.currentPage}' == '0'){
                    top.jzts();
                    setTimeout("self.location.reload()",100);
                }else{
                    nextPage(${page.currentPage});
                }
            }
            diag.close();
        };
        diag.show();
    }
</script>

</body>
</html>
