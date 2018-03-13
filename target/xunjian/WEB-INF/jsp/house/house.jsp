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
    <base href="<%=basePath%>"><!-- jsp文件头和头部 -->
    <%@ include file="../system/admin/top.jsp"%>

</head>
<body>

<div class="container-fluid" id="main-container">
    <div id="page-content" class="clearfix">
            <div class="row-fluid">
                <!-- 检索  -->
                <form action="house/list.do" method="post" name="Form" id="Form">
                    <table>
                        <tr>
                            <td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="enquiry" value="${pd.enquiry }" placeholder="这里输入内容" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
                            </td>
                            <td style="vertical-align:top;">
                                <select class="chzn-select" name="status" id="status" data-placeholder="请选择状态" style="vertical-align:top;width: 120px;">
                                    <option value=""></option>
                                    <option value="">全部</option>
                                    <option value="1" <c:if test="${pd.set_condition==1}">selected</c:if> >待安装</option>
                                    <option value="2" <c:if test="${pd.set_condition==2}">selected</c:if> >安装中</option>
                                    <option value="3" <c:if test="${pd.set_condition==3}">selected</c:if> >已安装</option>
                                </select>
                            </td>
                            <c:if test="${QX.cha == 1 }">
                                <td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
                                <c:if test="${QX.edit == 1 }">
                                    <td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="fromExcel();" title="从EXCEL导入"><i id="nav-search-icon" class="icon-cloud-upload"></i></a></td>
                                    <td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
                                </c:if>
                            </c:if>
                        </tr>
                    </table>


                 <!-- 表格  -->
                    <table id="table_report" class="table table-striped table-bordered table-hover">

                        <thead>
                        <tr>
                            <th class="center">
                                <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
                            </th>
                            <th class="center">序号</th>
                            <th class="center">房源编号</th>
                            <th class="center">地址</th>
                            <th class="center">房源联系人</th>
                            <th class="center">电话</th>
                            <th class="center">锁的类型</th>
                            <th class="center">锁的型号</th>
                            <th class="center">门的类型</th>
                            <th class="center">房源状态</th>
                            <th class="center">操作</th>
                        </tr>
                        </thead>

                        <tbody>

                        <!-- 开始循环 -->
                        <c:choose>
                            <c:when test="${not empty houseList}">
                                <c:if test="${QX.cha == 1 }">
                                    <c:forEach items="${houseList}" var="var" varStatus="vs">
                                        <tr>
                                            <td class='center' style="width: 30px;">
                                                <label><input type='checkbox' name='ids' value="${var.house_id}" /><span class="lbl"></span></label>
                                            </td>
                                            <td class='center' style="width: 30px;">${vs.index+1}</td>
                                            <td style="width: 60px;" class="center"><a onclick="sendtask('${var.house_address}','${var.house_id}','${var.house_owner}','${var.owner_phone}','${var.lock_type}','${var.lock_model}'
                                                    ,'${var.door_type}','${var.set_condition}');"> ${var.house_id}</a></td>
                                            <td style="width: 139px;" class="center">${var.house_address}</td>
                                            <td style="width: 60px;" class="center">${var.house_owner}</td>
                                            <td style="width: 100px;" class="center">${var.owner_phone}</td>
                                            <td style="width: 60px;" class="center">${var.lock_type}</td>
                                            <td style="width: 60px;" class="center">${var.lock_model}</td>
                                            <td style="width: 60px;" class="center">${var.door_type}</td>
                                            <td style="width: 60px;" class="center">
                                                <c:if test="${var.set_condition == '1' }"><span class="label label-important arrowed-in">待安装</span></c:if>
                                                <c:if test="${var.set_condition == '2' }"><span class="label label-primary arrowed">安装中</span></c:if>
                                                <c:if test="${var.set_condition == '3' }"><span class="label label-success arrowed">已安装</span></c:if>
                                            </td>
                                            <td style="width: 60px;" class="center">
                                                <div class='hidden-phone visible-desktop btn-group'>

                                                    <c:if test="${QX.edit == 1 }">
                                                        <c:if test="${user.USERNAME != 'admin'}"><a class='btn btn-mini btn-info' title="编辑" onclick="edit('${var.house_id }');"><i class='icon-edit'></i></a></c:if>
                                                        <c:if test="${user.USERNAME == 'admin'}"><a class='btn btn-mini btn-info' title="您不能编辑"><i class='icon-edit'></i></a></c:if>
                                                    </c:if>
                                                    &nbsp;&nbsp;&nbsp;
                                                    <c:if test="${QX.del == 1 }">
                                                        <c:if test="${user.USERNAME != 'admin'}"><a class='btn btn-mini btn-danger' title="删除" onclick="del('${var.house_id}');"  data-placement="left"><i class="icon-trash"></i> </a></c:if>
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
                                    <a class="btn btn-small btn-success" onclick="add();">新增</a>
                                    </c:if>
                                    <c:if test="${QX.del == 1 }">
                                    <a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='icon-trash'></i></a>
                                    </c:if>
                                </td>
                                <td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
                            </tr>
                        </table>
                    </div>


                    <%-- //bootstrap table格式
                    <div id="toolbar" class="btn-group">
                        <button id="btn_add" type="button" class="btn btn-default">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                        </button>
                        <button id="btn_edit" type="button" class="btn btn-default">
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
                        </button>
                        <button id="btn_delete" type="button" class="btn btn-default">
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                        </button>
                    </div>
                    <table id="house_msg" class="table table-hover"></table>--%>

                </form>
            </div>

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
<%--引入house_msg--%>
<%--<script type="text/javascript" src="static/js/myjs/house_msg.js"></script>--%>
<script type="text/javascript">
    //去下达任务界面页面
    function sendtask(house_address,house_id,house_owner,owner_phone,lock_type,lock_model,door_type,set_condition){

        if (set_condition==1){
            top.jzts();
            var diag = new top.Dialog();
            diag.Drag=true;
            diag.Title ="下达任务";
            diag.URL = '<%=basePath%>house/sendtask.do?house_address='+house_address+'&house_id='+house_id+
                '&house_owner='+house_owner+'&owner_phone='+owner_phone+'&lock_type='+lock_type+'&lock_model='+lock_model+
                '&door_type='+door_type+'&set_condition='+set_condition;
            diag.Width = 660;
            diag.Height = 470;
            diag.CancelEvent = function(){ //关闭事件
                diag.close();
            };
        }
        else {
            alert("");
        }
        diag.show();
    }


    $(top.hangge());

    //检索
    function search(){
        top.jzts();
        $("#Form").submit();
    }

    //新增
    function add(){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="新增";
        diag.URL = '<%=basePath%>house/goAdd.do';
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

    //删除
    function del(Id){
        bootbox.confirm("确定要删除吗?", function(result) {
            if(result) {
                top.jzts();
                var url = "<%=basePath%>house/delete.do?house_id="+Id;
                $.get(url,function(data){
                    nextPage(${page.currentPage});
                });
            }
        });
    }

    //修改
    function edit(Id){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="编辑";
        diag.URL = '<%=basePath%>house/goEdit.do?house_id='+Id;
        diag.Width = 400;
        diag.Height = 600;
        diag.CancelEvent = function(){ //关闭事件
            if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                nextPage(${page.currentPage});
            }
            diag.close();
        };
        diag.show();
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
                            url: '<%=basePath%>house/deleteAll.do?tm='+new Date().getTime(),
                            data: {house_ids:str},
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
    	var enquiry = document.getElementById("nav-search-input").value;
    	var status = document.getElementById("status").value;
        window.location.href='<%=basePath%>house/excel.do?enquiry='+encodeURI(encodeURI(enquiry))+'&status='+status;
    }
    //打开上传excel页面
    function fromExcel(){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="EXCEL 导入到数据库";
        diag.URL = '<%=basePath%>house/goUploadExcel.do';
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

