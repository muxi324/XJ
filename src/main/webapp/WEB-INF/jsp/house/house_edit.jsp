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
   <form action="house/${msg }.do" name="Form" id="Form" method="post" >
        <input type="hidden" name="house_id" id="house_id" value="${pd.house_id }"/>
        <div id="zhongxin">
            <table id="table_report" class="table table-striped table-bordered table-hover">
                <tr>
                    <td style="width:70px;text-align: right;padding-top: 13px;">房源地址:</td>
                    <td><input style="width:95%;" type="text" name="house_address" id="house_address" value="${pd.house_address}" maxlength="100" placeholder="这里输入房源地址" title=""/></td>
                </tr>
                <tr>
                    <td style="width:70px;text-align: right;padding-top: 13px;">房源联系人:</td>
                    <td><input style="width:95%;" type="text" name="house_owner" id="house_owner" value="${pd.house_owner}" maxlength="100" placeholder="这里输入房源联系人" title=""/></td>
                </tr>
                <tr>
                    <td style="width:70px;text-align: right;padding-top: 13px;">联系电话:</td>
                    <td><input style="width:95%;" type="text" name="owner_phone" id="owner_phone" value="${pd.owner_phone}" maxlength="100" placeholder="这里输入电话" title=""/></td>
                </tr>
                <tr>
                    <td style="width:70px;text-align: right;padding-top: 13px;">门锁号:</td>
                    <td><select id="lock_code" name="lock_code" class="form-control" value="${pd.lock_code}">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select></td>
                </tr>
                <tr>
                    <td style="width:70px;text-align: right;padding-top: 13px;">锁的型号:</td>
                    <td><select  type="text" name="lock_model" id="lock_model" value="${pd.lock_model}"  class="form-control" >
                        <option value="0">选择</option>
                        <c:forEach items="${lockModelList}" var="lockModel">
                            <option value="${lockModel.lock_model }">${lockModel.lock_model}</option>
                        </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="width:70px;text-align: right;padding-top: 13px;">锁的类型:</td>
                    <td><select id="lock_type" name="lock_type" class="form-control" value="${pd.lock_type}">
                        <%--<option >选择</option>
                        <option >电子锁</option>
                        <option >插芯门锁</option>
                        <option >电控锁</option>
                        <option >弹子门锁</option>--%>
                            <option value="0">选择</option>
                            <c:forEach items="${lockTypeList}" var="lockType">
                                <option value="${lockType.lock_type }">${lockType.lock_type}</option>
                            </c:forEach>
                    </select></td>
                </tr>
                <tr>
                    <td style="width:70px;text-align: right;padding-top: 13px;">门的类型:</td>
                    <td><select id="door_type" name="door_type" class="form-control" value="${pd.door_type}">
                        <option >选择</option>
                        <option >木门</option>
                        <option >铁门</option>
                    </select></td>
                </tr>
                <tr>
                    <td style="width:70px;hight:250px; text-align: right;padding-top: 13px;">照 片:</td>
                    <td>
                    
	                    <div id="uploader-demo">
						    <!--用来存放item-->
						    <div id="fileList" class="uploader-list">
						    	<c:if test="${pd != null && pd.lock_pic != '' && pd.lock_pic != null }">
									<a href="<%=basePath%>uploadFiles/uploadImgs/${pd.lock_pic}" target="_blank"><img src="<%=basePath%>uploadFiles/uploadImgs/${pd.lock_pic}" width="210"/></a>
									<div class="file-panel"> 
			                           <a class="btn btn-mini btn-cancel" onclick="removeFile();">删除</a>    
			                        </div>
								</c:if>
						    </div>
						    <input type="hidden" name="lock_pic" id="lock_pic" value="${pd.lock_pic}" />
                            <div id="filePicker" >选择图片</div>
						</div>
                    </td>
                </tr>

                <div style=" clear:both; padding-top: 40px;"></div>
                <tr>
                    <td style="text-align: center;" colspan="10">
                        <a class="btn btn-mini btn-primary" onclick="save();">保存</a>&nbsp;&nbsp;&nbsp;
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
       
    // 初始化Web Uploader
       var uploader = WebUploader.create({

           // 选完文件后，是否自动上传。
           auto: true,

           // swf文件路径
           swf: 'plugins/webuploader/Uploader.swf',

           // 文件接收服务端。
           server: 'house/uploade.do',

           // 选择文件的按钮。可选。
           // 内部根据当前运行是创建，可能是input元素，也可能是flash.
           pick: {
        	   id:'#filePicker',
        	   multiple:false
           },
		   //只允许上传一个图片
           fileNumLimit:1,
           // 只允许选择图片文件。
           accept: {
               title: 'Images',
               extensions: 'gif,jpg,jpeg,bmp,png',
               mimeTypes: 'image/*'
           }
       });

    
    // 文件上传成功
       uploader.on( 'uploadSuccess', function( file,reponse ) {
    	   
           $("#lock_pic").val(reponse._raw);
       });
    
    // 文件上传失败，显示上传出错。
       uploader.on( 'uploadError', function( file ) {
    	   alert("失败");
       });
       
    // 当有文件添加进来的时候
       uploader.on( 'fileQueued', function( file ) {
    	   $("#fileList").empty();
           var $li = $(
                   '<div id="' + file.id + '" class="file-item thumbnail">' +
                       '<img>' +
                       '<div class="info">' + file.name + '</div>' +
                   '</div>'
                   ),
                   $btns = $('<div class="file-panel">' +  
                           '<a class="btn btn-mini btn-cancel">删除</a>' +    
                           '</div>').appendTo( $li ),
               $img = $li.find('img');


           // $list为容器jQuery实例
           $("#fileList").append( $li );
           $btns.on( 'click', 'a', function(){
        	   removeFile(file);
           });

           // 创建缩略图
           // 如果为非图片文件，可以不用调用此方法。
           uploader.makeThumb( file, function( error, src ) {
               if ( error ) {
                   $img.replaceWith('<span>不能预览</span>');
                   return;
               }

               $img.attr( 'src', src );
           }, 100, 100 );
       });
    
    // 负责view的销毁  
       function removeFile(file) {  
            //view的显示删除  
            if(typeof(file)!="undefined"){
            	uploader.removeFile( file );
            }
           
           $("#fileList").empty();  
            var lock_pic = $("#lock_pic").val();
            var house_id = $("#house_id").val();
            $("#lock_pic").val("");
           //后台删除文件  
           $.ajax({  
               url:'house/deltp.do?lock_pic='+ lock_pic+'&house_id='+house_id,  
               type:'POST',  
               async:false,  
               success:function(result){  
            	   if(result=="success"){
   					alert("删除成功!");
   					//document.location.reload();
   				}
               }  
           });  
       }


   </script>
</body>
</html>

