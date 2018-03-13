<%--
  Created by IntelliJ IDEA.
  User: wp
  Date: 2017/12/21
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <!-- jsp文件头和头部 -->
    <%@ include file="../system/admin/top.jsp"%>
</head>
<body>


<div class="container-fluid" id="main-container">
    <div id="page-content" class="clearfix">
        <div class="row-fluid">
            <div class="row-fluid">
                 <form action="lockmag/verify.do" name="Form" id="Form1" method="post">
                    <div id="zhongxin">
                        <table id="table_report1" class="table table-striped table-bordered table-hover">
                            <tr>
                                <td style="width:120px;text-align: right;padding-top: 13px;">任务联单号:</td>
                                <td><input style="width:85%;" type="text" name="mission" id="flow_number" value="${pd.flow_number}" maxlength="100" placeholder="这里输入任务联单号" title=""/></td>
                            </tr>
                            <tr>
                                <td style="width:120px;text-align: right;padding-top: 13px;">取锁码:</td>
                                <td><input style="width:85%;" type="text" name="code" id="random_code" value="${pd.random_code}" maxlength="100" placeholder="这里输入取锁码" title=""/></td>
                            </tr>
                            <div style=" padding-top: 40px;">
                                <tr>
                                    <td style="text-align: center;" colspan="10">
                                        <a class="btn btn-mini btn-primary" onclick="save();">提交</a>
                                    </td>
                                </tr>
                            </div>
                        </table>
                    </div>
                </form>
                <!-- 检索  -->
                <form action="lockmag/list.do" method="post" name="Form" id="Form">
                    <table border='0'>
                        <tr>
                            <td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="enquiry" value="${pd.enquiry }" placeholder="这里输入关键词" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
                            </td>

                            <td><input class="span10 date-picker" name="sendTimeStart" id="sendTimeStart" value="${pd.sendTimeStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期"/></td>
                            <td><input class="span10 date-picker" name="sendTimeEnd" id="sendTimeEnd" value="${pd.sendTimeEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期"/></td>

                            <td style="vertical-align:top;">
                                <select class="chzn-select" name="status" id="status" data-placeholder="状态" style="vertical-align:top;width: 120px;">
                                    <option value=""></option>
                                    <option value="">全部</option>
                                    <option value="2" <c:if test="${pd.mission_condition == '2' }">selected</c:if> >接收未取锁</option>
                                    <option value="3" <c:if test="${pd.mission_condition == '3' }">selected</c:if> >已取锁执行中</option>
                                </select>
                            </td>
                            <td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
                           <%-- <c:if test="${QX.cha == 1 }">
                                <td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
                            </c:if>--%>
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
                            <th class="center">任务联单号</th>
                            <th class="center">安装员工</th>
                            <th class="center">任务下达时间</th>
                            <th class="center">房源地址</th>
                            <th class="center">房源联系人</th>
                            <th class="center">联系人电话</th>
                            <th class="center">任务状态</th>
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
                                                <label><input type='checkbox' name='ids' value="${var.mission_id}" /><span class="lbl"></span></label>
                                            </td>
                                            <td class='center' style="width: 30px;">${vs.index+1}</td>
                                            <td style="width: 60px;" class="center">${var.flow_number}</td>
                                            <td style="width: 60px;" class="center">${var.worker_name}</td>
                                            <td style="width: 100px;" class="center">${var.send_time}</td>
                                            <td style="width: 139px;" class="center">${var.house_address}</td>
                                            <td style="width: 60px;" class="center">${var.house_owner_name}</td>
                                            <td style="width: 60px;" class="center">${var.house_owner_phone}</td>
                                            <td style="width: 60px;" class="center">
                                                <c:if test="${var.mission_condition == '2' }"><span class="label label-info      arrowed-in">接收未取锁</span></c:if>
                                                <c:if test="${var.mission_condition == '3' }"><span class="label label-success      arrowed-in">已取锁执行中</span></c:if>

                                            </td>
                                            <td style="width: 30px;" class="center">
                                                <div class='hidden-phone visible-desktop btn-group'>

                                                    <c:if test="${QX.cha == 1 }">
                                                        <c:if test="${user.USERNAME != 'admin'}"><a class='btn btn-mini btn-info' title="查看详情" onclick="detail('${var.mission_id }');"><i class='icon-eye-open'></i></a></c:if>
                                                        <c:if test="${user.USERNAME == 'admin'}"><a class='btn btn-mini btn-info' title="您不能查看"><i class='icon-eye-open'></i></a></c:if>
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
                                <td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
                            </tr>
                        </table>
                    </div>
                </form>
            </div>

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

    //保存
    function save(){
        var flow_number = $("#flow_number").val();
        var random_code = $("#random_code").val();
        if(flow_number==""){
            $("#flow_number").tips({
                side:3,
                msg:'请输入任务联单号',
                bg:'#AE81FF',
                time:2
            });
            $("#flow_number").focus();
            return false;
        }
        if(random_code==""){
            $("#random_code").tips({
                side:3,
                msg:'请输入验证码',
                bg:'#AE81FF',
                time:2
            });
            $("#random_code").focus();
            return false;
        }
        $.ajax({	//使用JQuery内置的Ajax方法
            type : "post",		//post请求方式
            async : true,		//异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url :"<%=basePath%>lockmag/verify.do",	//请求发送到ShowInfoIndexServlet处
            data : {mission:flow_number,code:random_code},		//请求内包含一个key为name，value为A0001的参数；服务器接收到客户端请求时通过request.getParameter方法获取该参数值
            dataType : "json",		//返回数据形式为json
            success : function(result) {
                //请求成功时执行该函数内容，result即为服务器返回的json对象
                if(result == '1'){
                    alert("成功");
                }else{
                    alert("失败");
                }


            },
            error : function(errorMsg) {
                //请求失败时执行该函数
                alert("");

            }
        })

    }



    //详情
    function detail(Id){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="任务信息";
        diag.URL = '<%=basePath%>lockmag/goTaskInfo.do?mission_id='+Id;
        diag.Width = 600;
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



</script>


</body>
</html>
