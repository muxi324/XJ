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
<form action="sendtask/sendTask.do" id="Form"   method="post">
    <%--<input type="hidden" name="house_id" id="house_id" value="${pd.house_id }"/>--%>
    <label class="control-label" style="margin-left:45%">下发任务</label>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务性质:</td>
                <td><select name="mission_type" id="mission_type" class="form-control" value="${pd.mission_type}">
                    <option value="日常任务">日常任务</option>
                    <option value="临时任务">临时任务</option>
                </select></td>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务级别:</td>
                <td><select name="mission_level" id="mission_level" class="form-control" value="${pd.mission_level}">
                    <option value="1级">1级</option>
                    <option value="2级">2级</option>
                    <option value="3级">3级</option>
                </select>
                </td>
            </tr>
            <%--三级联动--%>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">班组:</td>
                <td><select name="team" id="team" class="form-control" value="${pd.team}" onchange="groupchoose()">
                    <%--<option value="1班组">1班组</option>
                    <option value="2班组">2班组</option>--%>
                        <option value="0">选择</option>
                        <c:forEach items="${teamList}" var="List">
                            <option value="${List.team }">${List.team}</option>
                        </c:forEach>
                </select>
                </td>
                <td style="width:110px;text-align: right;padding-top: 13px;">安装员工:</td>
                <td><select name="worker_name" id="worker_name" class="form-control" value="${pd.worker_name}" onchange="phonechoose()">
                    <option value="0">请先选择班组</option>
                </select>
                </td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">手机:</td>
                <td>
                    <%--<input style="width:90%;" type="text" name="worker_phone" id="worker_phone" value="${pd.worker_phone}" maxlength="200"  title=""/>--%>
                        <select name="worker_phone" id="worker_phone" class="form-control" value="${pd.worker_phone}" >
                            <option value="0">请先选择安装员工</option>
                        </select>
                </td>
                <td style="width:110px;text-align: right;padding-top: 13px;">预期开始时间:</td>
                <td><input style="width:90%;" type="text" class="datetimepicker" name="set_start_time" id="set_start_time" value="${pd.set_start_time}" maxlength="200" data-date-format="yyyy-mm-dd  hh:mm" title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">预期结束时间:</td>
                <td><input style="width:90%;" type="text" class="datetimepicker" name="set_finish_time" id="set_finish_time" value="${pd.set_finish_time}" maxlength="200" data-date-format="yyyy-mm-dd  hh:mm" title=""/></td>
                <td style="width:110px;text-align: right;padding-top: 13px;">房源编号:</td>
                <td><input style="width:90%;" type="text" name="house_id" id="house_id" value="${pd.house_id}" maxlength="200"  title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">房源地址:</td>
                <td><input style="width:90%;" type="text" name="house_address" id="house_address" value="${pd.house_address}" maxlength="200"  title=""/></td>
                <td style="width:110px;text-align: right;padding-top: 13px;">门锁号:</td>
                <td><input style="width:90%;" type="text" name="lock_code" id="lock_code" value="${pd.lock_code}" maxlength="200" title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">锁的类型:</td>
                <td><select style="width:90%;" name="lock_type" id="lock_type" value="${pd.lock_type}" class="form-control" type="text" >
                    <option >${pd.lock_type}</option>
                    <option >挂锁</option>
                    <option >抽斗锁</option>
                    <option >插芯门锁</option>
                    <option >球型门锁</option>
                    <option >电控锁</option>
                    <option >弹子门锁</option>
                </select>
                </td>
                <td style="width:110px;text-align: right;padding-top: 13px;">锁的型号:</td>
                <td><input style="width:90%;" type="text" name="lock_model" id="lock_model" value="${pd.lock_model}" maxlength="200"  title=""/></td>

            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">门的类型:</td>
                <td><select style="width:90%;" name="door_type" id="door_type" value="${pd.door_type}" class="form-control" type="text" >
                    <option >${pd.door_type}</option>
                    <option >木门</option>
                    <option >铁门</option>
                </select>
                </td>
                <td style="width:110px;text-align: right;padding-top: 13px;">备注:</td>
                <td><input style="width:90%;" type="text" name="mission_addition" id="mission_addition" value="${pd.mission_addition}" maxlength="200"  title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;hight:250px; text-align: right;padding-top: 13px;">门锁照片</td>
                <td >
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
            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-mini btn-primary" onclick="save();">下发</a>&nbsp;&nbsp;&nbsp;
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
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="static/js/bootstrap-datetimepicker.min.js"></script><!-- 日期框 -->
<script type="text/javascript" src="plugins/webuploader/webuploader.js"></script>
<script type="text/javascript" src="plugins/webuploader/upload.js"></script>

<script type="text/javascript">
    $(top.hangge());
    $(function() {

        //单选框
        $(".chzn-select").chosen();
        $(".chzn-select-deselect").chosen({allow_single_deselect:true});

        //日期框
        $('.datetimepicker').datetimepicker();

    });




    //下来选择的触发事件
    function groupchoose(){
        var group = document.getElementById('team');
        var index = group.selectedIndex;
        var senddata = group.options[index].value;
        //alert(senddata);
        var url ='<%=basePath%>sendtask/groupchoose.do';
        // alert(url);
        $.ajax({
            type:'POST',
            url: url,
            dataType: 'json',
            data:{'groupdata':senddata
            },
            success: function (data) {
                var workers = document.getElementById('worker_name');
                workers.options.length=0;
                var datalength = data.length;
                for(i=0;i<datalength;i++){
                    workers.options.add(new Option(data[i].name));
                }
                /* alert(data.length);
                 alert(data[0].name);*/
                //console.log(val.calls);
            },
            error :function(){
                alert("未知错误！");
            }

        })
    };

    function phonechoose(){
        var worker = document.getElementById('worker_name');
        var index = worker.selectedIndex;
        var senddata = worker.options[index].value;
        //alert(senddata);
        var url ='<%=basePath%>sendtask/phonechoose.do';
        // alert(url);
        $.ajax({
            type:'POST',
            url: url,
            dataType: 'json',
            data:{'workerdata':senddata
            },
            success: function (data) {
                var phones = document.getElementById('worker_phone');
                phones.options.length=0;
                var datalength = data.length;
                for(i=0;i<datalength;i++){
                    phones.options.add(new Option(data[i].phone))
                }
                /* alert(data.length);
                 alert(data[0].name);*/
                //console.log(val.calls);
            },
            error :function(){
                alert("未知错误！");
            }

        })
    };

    //保存
    function save(){
        if($("#worker_name").val()==""){
            $("#worker_name").tips({
                side:3,
                msg:'请选择安装员工姓名',
                bg:'#AE81FF',
                time:2
            });
            $("#worker_name").focus();
            return false;
        }
        if($("#house_address").val()==""){
            $("#house_address").tips({
                side:3,
                msg:'请输入输入房源地址',
                bg:'#AE81FF',
                time:2
            });
            $("#house_address").focus();
            return false;
        }

        $("#Form").submit();
        $("#zhongxin").hide();
        $("#zhongxin2").show();

    }
    // 初始化Web Uploader
    var uploader = WebUploader.create({

        // 选完文件后，是否自动上传。
        auto: true,

        // swf文件路径
        swf: 'plugins/webuploader/Uploader.swf',

        // 文件接收服务端。
        server: 'sendtask/uploade.do',

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
        if (typeof(file) != "undefined") {
            uploader.removeFile(file);
        }

        $("#fileList").empty();
        var lock_pic = $("#lock_pic").val();
        var mission_id = $("#mission_id").val();
        $("#lock_pic").val("");
        //后台删除文件
        $.ajax({
            url: 'sendtask/deltp.do?lock_pic=' + lock_pic + '&mission_id=' + mission_id,
            type: 'POST',
            async: false,
            success: function (result) {
                if (result == "success") {
                    alert("删除成功!");
                    //document.location.reload();
                }
            }
        });
    }
</script>


</body>
</html>
