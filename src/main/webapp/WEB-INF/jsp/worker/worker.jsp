<%--
  Created by IntelliJ IDEA.
  User: wp
  Date: 2017/12/17
  Time: 15:34
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
<body>
<div class="panel-body" style="padding-bottom:0px;">
    <div class="panel panel-default">
        <div class="panel-body">
            <form id="formSearch" class="form-horizontal">
                <div  class="form-group" style="margin-top:15px ">
                    <label class="control-label col-xs-2" style="margin-left:15%">员工姓名</label>
                    <div class="col-xs-3" style="margin-left:2%">
                        <input type="text" id="worker" class="form-control">
                    </div>
                    <div class="col-xs-3"><button type="button" style="margin-left:10%" id="Find" class="btn btn-primary">开 始 查 询</button></div>

                </div>
            </form>
        </div>
        <div id="toolbar" class="btn-group">
            <button id="btn_add" type="button" class="btn btn-default" onclick="" >
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
            </button>
            <button id="btn_edit" type="button" class="btn btn-default">
                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
            </button>
            <button id="btn_delete" type="button" class="btn btn-default">
                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                删除
            </button>
        </div>
        <table id="worker_msg"></table>
    </div>
</div>

</body>
<script type="text/javascript">
    $(function () {
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();
        //2.初始化Button的点击事件
        var oButtonInit = new ButtonInit();
        oButtonInit.Init();
        getWorker();

    });
    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#worker_msg').bootstrapTable({
                url: '',         //请求后台的URL（*）
                method: 'get',                      //请求方式（*）
                toolbar: '#toolbar',                //工具按钮用哪个容器
                striped: true,                      //是否显示行间隔色
                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,                   //是否显示分页（*）
                sortable: false,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                queryParams: oTableInit.queryParams,//传递参数（*）
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                pageNumber: 1,                       //初始化加载第一页，默认第一页
                pageSize: 10,                       //每页的记录行数（*）
                pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
                search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                strictSearch: true,
                showColumns: true,                  //是否显示所有的列
                showRefresh: true,                  //是否显示刷新按钮
                minimumCountColumns: 2,             //最少允许的列数
                clickToSelect: true,                //是否启用点击选中行
                // height: 550,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id",                     //每一行的唯一标识，一般为主键列
                showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
                cardView: false,                    //是否显示详细视图
                detailView: false,                   //是否显示父子表
                columns: [
                    {
                        field: 'checkbox', title: '序号', formatter: function (value, row, index) {
                        return index + 1;
                    }
                    },
                    {field: 'name', title: '姓名', align: 'center', valign: 'middle'},
                    {field: 'phone', title: '手机号', align: 'center', valign: 'middle'},
                    {field: 'id_number', title: '身份证号', align: 'center', valign: 'middle'},
                    {field: 'post', title: '职位', align: 'center', valign: 'middle'},
                    {field: 'team', title: '班组', align: 'center', valign: 'middle'},
                    {field: 'address', title: '地址', align: 'center', valign: 'middle'},
                    {
                        field: 'operate',
                        title: '操作',
                        align: 'center',
                        valign: 'middle',
                        events: operateEvents,
                        formatter: function () {
                            return [
                                '<a class="del" href="#" title="manage">',
                                '<i class="icon-trash bigger-130"></i> 删除',
                                '</a>'
                            ].join('');
                        }
                    }
                ]


            });
        };
    }
</script>
</html>
