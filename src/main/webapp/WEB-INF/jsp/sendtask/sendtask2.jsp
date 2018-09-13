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
    <input type="hidden" name="mission_id" id="mission_id" value="${pd.mission_id}"/>
    <input type="hidden" name="id" id="exceptionId" value="${pd.id}"/> <!--异常id-->
    <input type="hidden" name="mission_condition" id="mission_condition" value="${pd.mission_condition }"/>
    <label class="control-label" style="margin-left:45%;margin-top: 10px;margin-bottom: 20px">下发维修任务</label>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务名称:</td>
                <td><input style="width:90%;" type="text" name="mission_name" id="mission_name" value="${pd.mission_name}" maxlength="200"  title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务类型:</td>
                <td><input style="width:90%;" type="text" name="mission_type" id="mission_type" value="维修任务" maxlength="200" placeholder="维修任务" title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务级别:</td>
                <td><select name="mission_level" id="mission_level" class="form-control" value="${pd.mission_level}">
                    <option value="1级">1级</option>
                    <option value="2级">2级</option>
                    <option value="3级">3级</option>
                </select></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务来源:</td>
                <td><input style="width:90%;" type="text" name="mission_source" id="mission_source" value="${pd.mission_source}" maxlength="200"  title=""/></td>
            </tr>
            <%--三级联动--%>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">班组:</td>
                <td><select name="team_id" id="team" class="form-control" value="${pd.team_id}" onchange="groupchoose()">
                    <option value="0">选择</option>
                    <c:forEach items="${teamList}" var="t">
                        <option value="${t.id }">${t.team }</option>
                    </c:forEach>
                </select></td>
            </tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">检修员工:</td>
                <td><select name="worker_id" id="worker" class="form-control" value="${pd.worker_id}" onchange="phonechoose()">
                    <option value="">请先选择班组</option>
                </select></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">手机:</td>
                <td> <input  type="text" name="worker_phone" id="worker_phone"  value="${pd.worker_phone}" /></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">预期任务开始时间:</td>
                <td><input  type="text" class="laydate-icon-danlan" name="mission_set_start_time" id="mission_set_start_time" value="" maxlength="200"  title=""/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">预期任务结束时间:</td>
                <td><input  type="text" class="laydate-icon-danlan" name="mission_set_finish_time" id="mission_set_finish_time" value="" maxlength="200"  title=""/></td>
            </tr>
            <%--<tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">时间偏差:</td>
                <td><input  type="text" name="time_dev" id="time_dev" value="${pd.time_dev}" maxlength="150"  title=""/>小时</td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">检修员认证方式:</td>
                <td><select name="authen_method" id="authen_method" class="form-control" value="${pd.authen_method}" >
                    <option value="自拍">自拍</option>
                    <option value="指纹">指纹</option>
                    <option value="签名">签名</option>
                </select></td>
            </tr>--%>
 <%--           <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务审核级别</td>
                <td><select name="auditor_level" id="auditor_level" class="form-control" value="${pd.auditor_level}" >
                    <option value="1级">1级</option>
                    <option value="2级">2级</option>
                    <option value="3级">3级</option>
                </select></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">任务审核人</td>
                <td><select name="auditor" id="auditor" class="form-control" value="${pd.auditor}" >
                    <option value="王一">王一</option>
                    <option value="张三">张三</option>
                    <option value="王旺">王旺</option>
                </select></td>
            </tr>--%>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">备注:</td>
                <td><textarea cols="100" rows="3" name="mission_addition" id="mission_addition" value="${pd.mission_addition}" ></textarea></td>
            </tr>
        </table>
            <h3 style="padding-left:20px;padding-top: 13px;">任务内容</h3>

                <table style="width:100%;"class="table table-striped table-bordered table-hover">
                   <%-- <tr>
                        <td style="width:110px;text-align: right;padding-top: 13px;">巡检区域:</td>
                        <td><input style="width:90%;" type="text" name="region" id="region" value="${pd.region}" maxlength="200"  title=""/></td>
                    </tr>
                    <tr>
                        <td style="width:110px;text-align: right;padding-top: 13px;">巡检点:</td>
                        <td><input style="width:90%;" type="text" name="check_point" id="check_point" value="${pd.check_point}" maxlength="200"  title=""/></td>
                    </tr>--%>
                    <tr>
                        <td style="width:110px;text-align: right;padding-top: 13px;">异常巡检事件:</td>
                        <td><input style="width:90%;" type="text" name="event" id="event" value="${pd.event}" maxlength="200"  title=""/></td>
                    </tr>
                   <tr>
                        <td style="width:110px;text-align: right;padding-top: 13px;">具体位置:</td>
                        <td><input style="width:90%;" type="text" name="instrument_place" id="instrument_place" value="${pd.instrument_place}" maxlength="200"  title=""/></td>
                   </tr>
                    <tr>
                        <td style="width:110px;text-align: right;padding-top: 13px;">异常上报人:</td>
                        <td><input style="width:90%;" type="text" name="report_worker" id="report_worker" value="${pd.report_worker}" maxlength="200"  title=""/></td>
                    </tr>
                    <tr>
                        <td style="width:110px;text-align: right;padding-top: 13px;">上报时间:</td>
                        <td><input style="width:90%;" type="text" name="report_time" id="report_time" value="${pd.report_time}" maxlength="200"  title=""/></td>
                    </tr>
                    <tr>
                        <td style="width:110px;text-align: right;padding-top: 13px;">异常级别:</td>
                        <td><input style="width:90%;" type="text" name="level" id="level"  maxlength="200"
                            <c:if test="${pd.level==1}"> 问题型</c:if>
                            <c:if test="${pd.level==2}"> 隐患型</c:if>
                            <c:if test="${pd.level==3}"> 报警型</c:if> />
                        </td>
                    </tr>
                    <tr>
                        <td style="width:110px;text-align: right;padding-top: 13px;">异常描述:</td>
                        <td><input style="width:90%;" type="text" name="description" id="description" value="${pd.description}" maxlength="200"  title=""/></td>
                    </tr>
                   <%-- <tr>
                        <td style="width:110px;text-align: right;padding-top: 13px;">异常工作内容:</td>
                        <td><input style="width:90%;" type="text" name="content" id="content" value="${pd.content}" maxlength="200"  title=""/></td>
                    </tr>--%>
                   <%-- <tr>
                        <td style="width:140px;text-align: right;padding-top: 13px;">下次巡检是否关注:</td>
                        <td>
                            <select name="attention" id="attention" class="form-control" value="${pd.attention}" >
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select>
                        </td>
                    </tr>--%>
                    <tr>
                        <td style="width:110px;text-align: right;padding-top: 13px;">异常照片:</td>
                        <%--<td><img style="height:250px" src="/imgFile/${pd.pic}" width="210"></td>--%>
                        <td><div id="uploader-demo">
                                <!--用来存放item-->
                                <div id="fileList" class="uploader-list">
                                    <c:if test="${pd != null && pd.pic != '' && pd.pic != null }">
                                        <a href="/imgFile/${pd.pic}" target="_blank"><img src="/imgFile/${pd.pic}" width="210"/></a>
                                        <div class="file-panel">
                                            <a class="btn btn-mini btn-cancel" onclick="removeFile();">删除</a>
                                        </div>
                                    </c:if>
                                </div>
                                <input type="hidden" name="exp_pic" id="exp_pic" value="${pd.pic}" />
                                <div id="filePicker" >选择图片</div>
                            </div>
                        </td>
                    </tr>
                </table>
         <%--  <h3 style="padding-left:20px;padding-top: 13px;">所用物资</h3>
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
                        <a class="btn btn-small btn-primary" onclick="save();">下发</a>&nbsp;&nbsp;&nbsp;
                        <a class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
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
<script type="text/javascript" src="static/laydate/laydate.js"></script>
<script type="text/javascript" src="plugins/webuploader/webuploader.js"></script>
<script type="text/javascript" src="plugins/webuploader/upload.js"></script>

<script type="text/javascript">
    $(top.hangge());

    $(function() {
        //单选框
        $(".chzn-select").chosen();
        $(".chzn-select-deselect").chosen({allow_single_deselect:true});

        //日期框
        var start = {
            elem: '#mission_set_start_time',
            format: 'YYYY-MM-DD hh:mm:ss',
            min: laydate.now(0,"YYYY-MM-DD hh:mm:ss"), //设定最小日期为当前日期
            max: '2099-06-16 23:59:59', //最大日期
            istime: true,
            istoday: false,
            choose: function(datas){
                end.min = datas; //开始日选好后，重置结束日的最小日期
            }
        };
        var end = {
            elem: '#mission_set_finish_time',
            format: 'YYYY-MM-DD hh:mm:ss',
            //  min: laydate.now(),
            max: '2099-06-16 23:59:59',
            istime: true,
            istoday: false,
            choose: function(datas){
                start.max = datas; //结束日选好后，重置开始日的最大日期
            }
        };
        laydate(start);
        laydate(end);
        laydate.skin('danlan');

    });
    //保存
    function save(){
        if($("#worker_name").val()==""){
            $("#worker_name").tips({
                side:3,
                msg:'请选择检修员工姓名',
                bg:'#AE81FF',
                time:2
            });
            $("#worker_name").focus();
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
/*        $("#Form").submit();
        $("#zhongxin").hide();
        $("#zhongxin2").show();*/
        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            url: "<%=basePath%>sendtask/sendTask.do" ,//url
            data: $('#Form').serialize(),
            success: function (result) {
                //打印服务端返回的数据(调试用)
                alert("下发维修任务成功！");
            },
            error : function() {
                alert("出现异常！");
            }
        });
    }

    //根据班组选择员工
    function groupchoose(){
        var  teamId = $("#team").children('option:selected').val();
        var url ='<%=basePath%>sendtask/groupchoose.do';
        $.ajax({
            type:'POST',
            url: url,
            dataType: 'json',
            data:{ 'team_id':teamId
            },
            success: function (data) {
                var workers = document.getElementById('worker');
                workers.options.length=0;
                var datalength = data.length;
                for(var i=0;i<datalength;i++){
                    workers.options.add(new Option(data[i].NAME,data[i].USER_ID));
                }

            },
            error :function(){
                alert("未知错误！");
            }

        })
    };

    //通过员工获取手机号
    function phonechoose(){
        var worker = document.getElementById('worker');
        var index = worker.selectedIndex;
        var senddata = worker.options[index].value;
        //alert(senddata);
        var url ='<%=basePath%>sendtask/phonechoose.do';
        $.ajax({
            type:'POST',
            url: url,
            dataType: 'json',
            data:{'workerdata':senddata
            },
            success: function (data) {
                $('#worker_phone').val(data[0].PHONE);
            },
            error :function(){
                alert("未知错误！");
            }

        })
    };

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
        $("#exp_pic").val(reponse._raw);
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
        var exp_pic = $("#exp_pic").val();
        var exp_id = $("#exceptionId").val();
        $("#exp_pic").val("");
        //后台删除文件
        $.ajax({
            url:'sendtask/deltp.do?exp_pic='+ exp_pic+'&id='+exp_id,
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
