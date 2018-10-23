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
    <label class="control-label" style="margin-left:45%;margin-top: 10px;margin-bottom: 20px">编辑巡检事件</label>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <input type="hidden" name="event_id" id="event_id" value="${pd.event_id}"/>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">事件名称:</td>
                <td><input style="width:90%;" type="text" name="event_name" id="event_name" value="${pd.event_name}" maxlength="200"  title="" data-placeholder="请填写事件名称（不能重复）"/></td>
                <input type="hidden" name="font_color" id="font_color" value="#000000"/>
                <input type="hidden" name="font_size" id="font_size" value="20"/>
            </tr>
            <tr>
                <input type="hidden" name="workshop_id" id="workshop" value="${workshopId }"/>
                <td style="width:110px;text-align: right;padding-top: 13px;">所属车间:</td>
                <td id="p1">
                    <input style="width:90%;" type="text" name="workshop"   maxlength="200" value="${pd.workshop}" readonly>
                </td>
                <td id="p2" hidden="hidden">
                   <select name="workshop_id" id="workshop1" class="form-control" >
                        <option value="">请选择</option>
                        <c:forEach items="${workshopList}" var="W">
                            <option value="${W.id}" <c:if test="${w.id == pd.workshop_id}">selected</c:if>> ${W.workshop }</option>
                        </c:forEach>
                    </select>
                    <input type="hidden" name="workshop"  value="${pd.workshop}"/>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">具体位置:</td>
                <td>
                <input style="width:90%;" type="text" name="instrument_place" id="instrument_place"  maxlength="200" value="${pd.instrument_place}"  data-placeholder="事件所在的车间的具体位置（可以不填）"/>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">事件级别:</td>
                <td>
                    <select name="event_level" id="event_level" class="form-control" value="${pd.event_level}">
                        <option value="1级">1级</option>
                        <option value="2级">2级</option>
                        <option value="3级">3级</option>
                    </select>
                </td>
            </tr>
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
        <tr>
            <td style="text-align: center;" colspan="10">
                <a class="btn btn-small btn-primary" onclick="save();">保存</a>&nbsp;&nbsp;&nbsp;
                <a class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
            </td>
        </tr>
        <div style="padding-bottom: 15px;">
        </div>
    </div>
    <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
</form>
</body>
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript">
    $(top.hangge());

    $(function() {
        var w = $("#workshop").val();
       if(w == ""|| w == null){
           $('#p1').attr("hidden","hidden");
           $('#p2').removeAttr("hidden");
        }else{
           $('#p2').attr("hidden","hidden");
           $('#p1').removeAttr("hidden");
       }

    });

    $(document).ready(function(){
        if($("#event_name").val() != "") {
            var trHtml = "<tr>" +
                "<td id = 'addContent'>" +
                "<a class='btn btn-small btn-primary' onclick='addWorkContent()'>新增工作内容</a>" +
                "</td>" +
                "</tr>";
            $("#table_report").append(trHtml);
        }
    });
    //保存
    function save(){
        if($("#event_name").val()==""){
            $("#event_name").tips({
                side:3,
                msg:'请填写事件名称',
                bg:'#AE81FF',
                time:2
            });
            $("#event_name").focus();
            return false;
        }
/*        if($("#mission").val()==""){
            $("#mission").tips({
                side:3,
                msg:'请输入任务名称',
                bg:'#AE81FF',
                time:2
            });
            $("#mission").focus();
            return false;
        }*/
        //$("#Form").submit();
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            url: "<%=basePath%>eventManage/addEvent1.do" ,//url
            data: $('#Form').serialize(),
            success: function (result) {
                //打印服务端返回的数据(调试用)
                alert("保存事件成功！");
                $(this).val(""); //清空上次input框里的数据
                $('#event_id').val(result);
                console.log("result="+result);
                var eventid = document.getElementById("event_id").value;
                console.log("eventId="+eventid);
            },
            error : function() {
                alert("出现异常！");
            }
        });
        if ($('#addContent').length <= 0) {
            var trHtml = "<tr>" +
                "<td id = 'addContent'>" +
                "<a class='btn btn-small btn-primary' onclick='addWorkContent()'>新增工作内容</a>" +
                "</td>" +
                "</tr>";
            $("#table_report").append(trHtml);
        }
        //$("#zhongxin").hide();
       // $("#zhongxin2").show();
    }

    function addWorkContent() {
        if($("#event_name").val()==""){
            $("#event_name").tips({
                side:3,
                msg:'请填写事件名称',
                bg:'#AE81FF',
                time:2
            });
            $("#event_name").focus();
            return false;
        }
       // var eventId = $("#event_id").val();
        var eventId = document.getElementById("event_id").value;
        console.log("event_id="+eventId);
       // location.href = "<%=basePath%>eventManage/goAddWorkContent.do?event_id="+eventId;

            top.jzts();
            var diag = new top.Dialog();
            diag.Drag=true;
            diag.Title ="编辑工作内容";
            diag.URL = "<%=basePath%>eventManage/goAddWorkContent.do?event_id="+eventId;
            diag.Width = 1000;
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
</html>


