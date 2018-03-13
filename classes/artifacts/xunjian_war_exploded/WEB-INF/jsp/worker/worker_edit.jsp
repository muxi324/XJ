<%--
  Created by IntelliJ IDEA.
  User: wp
  Date: 2017/12/18
  Time: 9:17
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
            if($("#name").val()==""){
                $("#name").tips({
                    side:3,
                    msg:'请输入姓名',
                    bg:'#AE81FF',
                    time:2
                });
                $("#name").focus();
                return false;
            }
            if($("#phone").val()==""){
                $("#phone").tips({
                    side:3,
                    msg:'请输入手机号',
                    bg:'#AE81FF',
                    time:2
                });
                $("#phone").focus();
                return false;
            }
            if($("#id_number").val()==""){
                $("#id_number").tips({
                    side:3,
                    msg:'请输入身份证号',
                    bg:'#AE81FF',
                    time:2
                });
                $("#id_number").focus();
                return false;
            }
            if($("#team").val()==""){
                $("#team").tips({
                    side:3,
                    msg:'请输入班组',
                    bg:'#AE81FF',
                    time:2
                });
                $("#team").focus();
                return false;
            }
            $("#Form").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
           // hasW();
        }

        //判断员工是否存在
        function hasW(){
            var worker = $("#name").val();
            var ID = "${pd.id}";
            $.ajax({
                type: "POST",
                url: '<%=basePath%>worker/hasW.do',
                data: {name:worker,tm:new Date().getTime()},
                dataType:'json',
                cache: false,
                success: function(data){
                    if("success" == data.result){
                       // $("#Form").submit();
                       // $("#zhongxin").hide();
                       // $("#zhongxin2").show();
                    }else{
                        $("#name").tips({
                            side:3,
                            msg:'此关键词已存在(全局)!',
                            bg:'#AE81FF',
                            time:3
                        });
                        return false;
                    }
                }
            });
        }


    </script>
</head>
<body>
     <form action="worker/${msg }.do" id="Form"   method="post">
         <input type="hidden" name="id" id="id" value="${pd.id }"/>
         <div id="zhongxin">
             <table id="table_report" class="table table-striped table-bordered table-hover">
                 <tr>
                     <td style="width:100px;text-align: right;padding-top: 13px;">姓名:</td>
                     <td><input style="width:95%;" type="text" name="name" id="name" value="${pd.name}" maxlength="100" placeholder="这里输入姓名" title=""/></td>
                 </tr>
                 <tr>
                     <td style="width:100px;text-align: right;padding-top: 13px;">性别:</td>
                     <td><select id="sex" name="sex" class="form-control" value="${pd.sex}">
                         <option value="男">男</option>
                         <option value="女">女</option>
                     </select></td>
                 </tr>
                 <tr>
                     <td style="width:100px;text-align: right;padding-top: 13px;">联系电话:</td>
                     <td><input style="width:95%;" type="text" name="phone" id="phone" value="${pd.phone}" maxlength="150" placeholder="这里输入电话" title=""/></td>
                 </tr>
                 <tr>
                     <td style="width:100px;text-align: right;padding-top: 13px;">班组:</td>
                     <td><input style="width:95%;" type="text" name="team" id="team" value="${pd.team}" maxlength="150" placeholder="这里输入班组" title=""/></td>
                 </tr>
                 <tr>
                     <td style="width:100px;text-align: right;padding-top: 13px;">职位:</td>
                     <td><input style="width:95%;" type="text" name="post" id="post" value="${pd.post}" maxlength="150" placeholder="这里输入职位" title=""/></td>
                 </tr>
                 <tr>
                     <td style="width:100px;text-align: right;padding-top: 13px;">住址:</td>
                     <td><input style="width:95%;" type="text" name="address" id="address" value="${pd.address}" maxlength="200" placeholder="这里输入住址" title=""/></td>
                 </tr>
                 <tr>
                     <td style="width:100px;text-align: right;padding-top: 13px;">身份证号:</td>
                     <td><input style="width:95%;" type="text" name="id_number" id="id_number" value="${pd.id_number}" maxlength="200" placeholder="这里输入身份证号" title=""/></td>
                 </tr>
                 <tr>
                     <td style="width:100px;hight:250px; text-align: right;padding-top: 13px;">照 片</td>
                     <td >
                         <div id="uploader-demo">
                             <!--用来存放item-->
                             <div id="fileList" class="uploader-list">
                                 <c:if test="${pd != null && pd.head_pic != '' && pd.head_pic != null }">
                                     <a href="<%=basePath%>uploadFiles/uploadImgs/${pd.head_pic}" target="_blank"><img src="<%=basePath%>uploadFiles/uploadImgs/${pd.head_pic}" width="210"/></a>
                                     <div class="file-panel">
                                         <a class="btn btn-mini btn-cancel" onclick="removeFile();">删除</a>
                                     </div>
                                 </c:if>
                             </div>
                             <input type="hidden" name="head_pic" id="head_pic" value="${pd.head_pic}" />
                             <div id="filePicker"  >选择图片</div>
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


         <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
     </form>



        <!-- 引入 -->
        <script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
        <script src="static/js/bootstrap.min.js"></script>
        <script src="static/js/ace-elements.min.js"></script>
        <script src="static/js/ace.min.js"></script>

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
                server: 'worker/uploade.do',

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

                $("#head_pic").val(reponse._raw);
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
                var head_pic = $("#head_pic").val();
                var id = $("#id").val();
                $("#head_pic").val("");
                //后台删除文件
                $.ajax({
                    url:'worker/deltp.do?head_pic='+ head_pic+'&id='+id,
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
