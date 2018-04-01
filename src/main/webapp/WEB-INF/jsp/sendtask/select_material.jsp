<%--
  Created by IntelliJ IDEA.
  User: wp
  Date: 2017/12/14
  Time: 17:22
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
    <link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" />
    <link rel="stylesheet" type="text/css" href="plugins/webuploader/style.css" />

    <script type="text/javascript">


        //保存
        function save(){
            if($("#house_address").val()==""){
                $("#house_address").tips({
                    side:3,
                    msg:'请输入房源地址',
                    bg:'#AE81FF',
                    time:2
                });
                $("#house_address").focus();
                return false;
            }
            if($("#house_owner").val()==""){
                $("#house_owner").tips({
                    side:3,
                    msg:'请输入房源联系人',
                    bg:'#AE81FF',
                    time:2
                });
                $("#house_owner").focus();
                return false;
            }
            if($("#owner_phone").val()==""){
                $("#owner_phone").tips({
                    side:3,
                    msg:'请输入联系人电话',
                    bg:'#AE81FF',
                    time:2
                });
                $("#owner_phone").focus();
                return false;
            }

            $("#Form").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }
     
    </script>

</head>
<body>
   <form action="sendtask/${msg }.do" name="Form" id="Form" method="post" >
        <input type="hidden" name="mission_id" id="mission_id" value="${pd.mission_id }"/>
        <div id="zhongxin">
            <table id="table_report" class="table table-striped table-bordered table-hover">
                <tr>
                    <td style="width:70px;text-align: right;padding-top: 13px;">物资类别:</td>
                    <td><select id="type" name="type" class="form-control" value="${pd.type}">
                        <option value="配件">配件</option>
                        <option value="工具">工具</option>
                    </select></td>
                </tr>
                <tr>
                    <td style="width:70px;text-align: right;padding-top: 13px;">所属分类:</td>
                    <td><select  type="text" name="description" id="description" value="${pd.description}"  class="form-control" >
                        <option value="绳索">绳索</option>
                        <option value="扳手">扳手</option>
                        <option value="螺栓">螺栓</option>
                        <option value="测量计">测量计</option>
                        <option value="阀门开关">阀门开关</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="width:70px;text-align: right;padding-top: 13px;">物资名称:</td>
                    <td><select id="name" name="name" class="form-control" value="${pd.name}">
                        <option >选择</option>
                        <option >听针</option>
                        <option >EMT220系列袖珍式测振仪</option>
                        <option >DT-8819H手持式测温仪 </option>
                        <option >万能电工用表</option>
                        <option >6寸扳手</option>
                    </select></td>
                </tr>
                <tr>
                    <td style="width:70px;text-align: right;padding-top: 13px;">物资数量:</td>
                    <td><select id="material_num" name="material_num" class="form-control" value="${pd.material_num}">
                        <option >选择</option>
                        <option >1</option>
                        <option >2</option>
                        <option >3 </option>
                        <option >4</option>
                        <option >5</option>
                        <option >6</option>
                    </select></td>
                </tr>


                <tr>
                    <td style="text-align: center;" colspan="10">
                        <a class="btn btn-mini btn-primary" onclick="save();">确定</a>&nbsp;&nbsp;&nbsp;
                        <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                    </td>
                </tr>
            </table>
            </div>
        </div>
       <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
   </form>
   <!-- 引入 -->
   <script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
   <script src="static/js/bootstrap.min.js"></script>
   <script src="static/js/ace-elements.min.js"></script>
   <script src="static/js/ace.min.js"></script>
   <script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
   <script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
   <script type="text/javascript" src="plugins/webuploader/webuploader.js"></script>
   
   <script type="text/javascript">
       $(top.hangge());
       $(function() {

           //单选框
           $(".chzn-select").chosen();
           $(".chzn-select-deselect").chosen({allow_single_deselect:true});

           //日期框
           $('.date-picker').datepicker();

       });
       



   </script>
</body>
</html>

