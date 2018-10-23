<%--
  Created by IntelliJ IDEA.
  User: wp
  Date: 2017/12/28
  Time: 11:18
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
    <link rel="stylesheet" href="static/css/bootstrap-datetimepicker.min.css" /><!-- 日期框 -->
    <link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" />
    <link rel="stylesheet" type="text/css" href="plugins/webuploader/style.css" />
</head>
<body>
<form action="taskset/${msg}.do" id="Form"   method="post">
    <input type="hidden" name="set_id" id="set_id" value="${pd.set_id }"/>
    <input type="hidden" name="select_eventId" id="select_eventId"/>
    <input type="hidden" name="material" id="material" value="0"/>   <%--配件--%>
    <input type="hidden" name="detail_info" id="detail_info"value="0"/>  <%--工具--%>
    <div id="zhongxin">
        <table  class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务名称:</td>
                <td><input style="width:90%;" type="text" name="mission" id="mission" value="${pd.mission}" maxlength="200"  title=""/></td>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务类型:</td>
                <td><select name="mission_type" id="mission_type" class="form-control" value="${pd.mission_type}">
                    <option value="日常巡检">日常巡检</option>
                    <option value="临时巡检">临时巡检</option>
                    <option value="维修任务">维修任务</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务级别:</td>
                <td><select name="mission_level" id="mission_level" class="form-control" value="${pd.mission_level}">
                    <option value="1级">1级</option>
                    <option value="2级">2级</option>
                    <option value="3级">3级</option>
                </select>
                </td>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务来源:</td>
                <td><input style="width:90%;" type="text" name="mission_source" id="mission_source" value="${pd.mission_source}" maxlength="200"  title=""/></td>
            </tr>
          <%--  <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">所覆盖范围:</td>
                <td><input style="width:90%;" type="text" name="cover_fields" id="cover_fields" value="${pd.cover_fields}" maxlength="200"  title=""/></td>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务描述:</td>
                <td><input style="width:90%;" type="text" name="mission_description" id="mission_description" value="${pd.mission_description}" maxlength="200"  title=""/>
                </td>
            </tr>--%>

            <tr>
              <%--  <td style="width:110px;text-align: right;padding-top: 13px;">巡检周期:</td>
                <td><input style="width:80%;" type="text" name="cycle_time" id="cycle_time" value="${pd.cycle_time}" maxlength="150"  title=""/>小时/次</td>--%>
               <%-- <td style="width:110px;text-align: right;padding-top: 13px;">检修员认证方式:</td>
                <td><select name="authen_method" id="authen_method" class="form-control" value="${pd.authen_method}" >
                    <option value="自拍">自拍</option>
                    <option value="指纹">指纹</option>
                    <option value="签名">签名</option>
                </select></td>--%>
                  <td style="width:110px;text-align: right;padding-top: 13px;">任务描述:</td>
                  <td><input style="width:90%;" type="text" name="mission_description" id="mission_description" value="${pd.mission_description}" maxlength="200"  title=""/>
                  </td>
                  <td style="width:110px;text-align: right;padding-top: 13px;">备注:</td>
                  <td><textarea cols="40" rows="6" name="task_addition" id="task_addition" value="${pd.task_addition}" ></textarea></td>
            </tr>
           <%-- <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务审核级别</td>
                <td><select name="auditor_level" id="auditor_level" class="form-control" value="${pd.auditor_level}" >
                    <option value="1级">1级</option>
                    <option value="2级">2级</option>
                    <option value="3级">3级</option>
                </select></td>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务审核人</td>
                <td><select name="auditor" id="auditor" class="form-control" value="${pd.auditor}" >
                    <option value="王一">王一</option>
                    <option value="张三">张三</option>
                    <option value="王旺">王旺</option>
                </select></td>
            </tr>--%>
        </table>
        <h3 style="padding-left:20px;padding-top: 13px;">选择事件</h3>
        <div id="page-content" class="clearfix">
            <div class="row-fluid">
                    <!-- 表格  -->
                    <table id="table_report" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th class="center">
                                <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
                            </th>
                            <th class="center">序号</th>
                            <th class="center">所属车间</th>
                            <%--<th class="center">所属巡检区域</th>
                            <th class="center">所属巡检点</th>--%>
                            <th class="center">事件名称</th>
                            <th class="center">具体位置</th>
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
                                                <label><input type='checkbox' id="${var.event_id}" name='ids' value="${var.event_id}"/><span class="lbl"></span></label>
                                            </td>
                                            <td class='center' style="width: 30px;">${vs.index+1}</td>
                                            <td style="width: 60px;" class="center"> ${var.workshop}</td>
                                            <%--<td style="width: 139px;" class="center">${var.check_scope}</td>
                                            <td style="width: 60px;" class="center">${var.check_point}</td>--%>
                                            <td style="width: 100px;" class="center">${var.event_name}</td>
                                            <td style="width: 150px;" class="center">${var.instrument_place}</td>
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
            </div>
        </div>
       <%-- <h3 style="padding-left:20px;padding-top: 13px;">所用物资</h3>
        <div class="page-header position-relative">
            <table style="width:100%;">
                <tr>
                    <td style="vertical-align:top;">
                        <a class="btn btn-small btn-success" onclick="selectMaterial();">选择物资</a>
                    </td>
                </tr>
            </table>
        </div>
        <table  border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;" width="40%">
            <tr>
                <th class="center">序号</th>
                <th class="center">物资名称</th>
                <th class="center">所用数量</th>
            </tr>
        </table>--%>
        <table class="table table-striped table-bordered table-hover">
            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-small btn-primary" onclick="save();">保存</a>&nbsp;&nbsp;&nbsp;
                    <a class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
                </td>
            </tr>
        </table>
    </div>
    <div style="padding-bottom: 30px;">
    </div>
    <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
</form>
<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="static/js/bootstrap-datetimepicker.min.js"></script><!-- 日期框 -->
<script type="text/javascript" src="plugins/webuploader/webuploader.js"></script>
<script type="text/javascript" src="plugins/webuploader/upload.js"></script>

<script type="text/javascript">
    $(top.hangge());

    //页面加载时执行,读取任务绑定的事件，并在编辑页面的事件表格中选中
    $(document).ready(function(){
        var eventIds = [];
        <c:forEach items="${eventIdList}" var="var" varStatus="vs">
             eventIds.push(${var});
        </c:forEach>
        for (var i = 0; i<eventIds.length; i++) {
            $("#"+eventIds[i]).attr("checked","checked");
        }
    });

    $(function() {

        //单选框
        $(".chzn-select").chosen();
        $(".chzn-select-deselect").chosen({allow_single_deselect:true});

        //日期框
        $('.datetimepicker').datetimepicker();

    });


    //保存
    function save(){
        if($("#mission_level").val()==""){
            $("#mission_level").tips({
                side:3,
                msg:'请选择任务级别',
                bg:'#AE81FF',
                time:2
            });
            $("#mission_level").focus();
            return false;
        }
        if($("#mission").val()==""){
            $("#mission").tips({
                side:3,
                msg:'请输入任务名称',
                bg:'#AE81FF',
                time:2
            });
            $("#mission").focus();
            return false;
        }

        var chk_value =[];
        $('input[name="ids"]:checked').each(function(){
            chk_value.push($(this).val());
        });
        var selectEventIds = "";
        for (var i = 0; i<chk_value.length; i++) {
            selectEventIds += chk_value[i] + ",";
        }
        $("#select_eventId").val(selectEventIds.substring(0,selectEventIds.length-1));

        $("#Form").submit();
        $("#zhongxin").hide();
        $("#zhongxin2").show();

    }

    function selectMaterial(){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="选择物资";
        diag.URL = '<%=basePath%>sendtask/goselectMaterial.do';
        diag.Width = 400;
        diag.Height = 450;
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


</body>
</html>
