<%--
  Created by IntelliJ IDEA.
  User: wp
  Date: 2017/12/16
  Time: 20:32
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
        <div class="panel-heading">下达任务</div>
        <div class="panel-body">
            <form action="sendtask/sendtask.do" method="post" class="form-horizontal"style="">

                <div class="form-group" style="margin-top:15px;float:left;width:35%;">
                    <label class="control-label col-xs-4"style="margin-right:10px;float:left">任务性质</label>
                    <div class="col-xs-5">
                        <select id="mission_type" class="form-control">
                            <option>日常任务</option>
                            <option>临时任务</option>
                        </select>
                    </div>
                </div>
                <div  class="form-group" style="margin-top:15px;float:left;width:35%;">
                    <label class="control-label col-xs-4" style="margin-right:10px;float:left">任务级别</label>
                    <div class="col-xs-5">
                        <select id="mission_level" class="form-control">
                            <option>1级</option>
                            <option>2级</option>
                            <option>3级</option>
                        </select>
                    </div>
                </div>

                <div style=" clear:both;"></div>
                <div  class="form-group" style="margin-top:15px;float:left;width:35%;">
                    <label class="control-label col-xs-4"style="margin-right:10px;float:left">班    组</label>
                    <div class="col-xs-5">
                        <select id="team" class="form-control" type="text" ></select>
                    </div>
                </div>

                <div  class="form-group" style="margin-top:15px;float:left;width:35%;">
                    <label class="control-label col-xs-4"style="margin-right:10px;float:left">安装人员</label>
                    <div class="col-xs-5">
                        <select id="worker_name" class="form-control" type="text"></select>
                    </div>
                </div>
                <div  class="form-group" style="margin-top:15px;float:left;width:35%;">
                    <label class="control-label col-xs-4" style="margin-right:10px;float:left">手机号码</label>
                    <div class="col-xs-5">
                        <select id="worker_phone" class="form-control"></select>
                    </div>
                </div>

                <div style=" clear:both;"></div>
                <div  class="form-group" style="margin-top:15px;float:left;width:35%;">
                    <label class="control-label col-xs-4" style="margin-right:10px;float:left">预期开始时间</label>
                    <div class="col-xs-5">
                        <input type="datetime-local" id="set_start_time"  style="" onchange="setEndDisabled()"/>
                    </div>
                </div>
                <div  class="form-group" style="margin-top:15px;float:left;width:35%">
                    <label class="control-label col-xs-4" style="margin-right:10px;float:left">预期完成时间</label>
                    <div class="col-xs-5">
                        <input type="datetime-local" id="set_finish_time"  style="" onchange="setEndDisabled()"/>
                    </div>
                </div>

                <div style=" clear:both;"></div>
                <div  class="form-group" style="margin-top:15px;float:left;width:35%">
                    <label class="control-label col-xs-4"style="margin-right:10px;float:left">房源编号</label>
                    <div class="col-xs-5">
                        <input id="house_id" class="form-control" type="text" placeholder="" value=""/>
                    </div>
                </div>
                <div  class="form-group" style="margin-top:15px;float:left;width:35%">
                    <label class="control-label col-xs-4"style="margin-right:10px;float:left">房源地址</label>
                    <div class="col-xs-7">
                        <input id="house_address" class="form-control" type="text" placeholder="" />
                    </div>
                </div>

                <div style=" clear:both;"></div>
                <div  class="form-group" style="margin-top:15px;float:left;width:35%">
                    <label class="control-label col-xs-4"style="margin-right:10px;float:left">门锁号</label>
                    <div class="col-xs-5">
                        <select id="lock_code" class="form-control" type="text" >
                            <option >1</option>
                            <option >2</option>
                            <option >3</option>
                            <option >4</option>
                            <option >5</option>
                        </select>
                    </div>
                </div>
                <div  class="form-group" style="margin-top:15px;float:left;width:35%">
                    <div class="col-xs-4" style="">
                        <button type="button"  style="" id="" class="btn btn-primary">新增门锁任务下发</button>
                    </div>
                </div>
                <div style=" clear:both;"></div>
                <div  class="form-group" style="margin-top:10px;border: 1px solid #B1D1CE;width:80%;high:600px;">
                    <div  class="form-group" style="margin-top:15px;float:left;width:40%">
                        <label class="control-label col-xs-4"style="margin-right:10px;margin-left:22px;float:left">锁的类型</label>
                        <div class="col-xs-5">
                            <select id="lock_type" class="form-control" type="text" >
                                <option >选择</option>
                                <option >电子锁</option>
                                <option >插芯门锁</option>
                                <option >电控锁</option>
                                <option >弹子门锁</option>
                            </select>
                        </div>
                    </div>
                    <div  class="form-group" style="margin-top:15px;margin-left:40px;float:left;width:40%">
                        <label class="control-label col-xs-4"style="margin-right:10px;float:left">门的类型</label>
                        <div class="col-xs-5">
                            <select id="door_type" class="form-control" type="text" >
                                <option >选择</option>
                                <option >木门</option>
                                <option >铁门</option>
                            </select>
                        </div>
                    </div>
                    <div  class="form-group" style="margin-top:15px;float:left;width:40%">
                        <label class="control-label col-xs-4"style="margin-right:10px;margin-left:22px;float:left">备   注</label>
                        <div class="col-xs-5">
                            <textarea id="mission_addition" cols="20" rows="4" placeholder="此处键入说明内容"></textarea>
                        </div>
                    </div>
                    <div  class="form-group" style="margin-top:15px;margin-left:40px;float:left;width:40%">
                        <label class="control-label col-xs-4"style="margin-right:10px;float:left">锁的型号</label>
                        <div class="col-xs-5">
                            <input type="text" id="lock_model" class="form-control">
                            <!--<select id="drivernumber" class="form-control" type="text" >
                                <option >选择</option>
                                <option ></option>
                                <option ></option>
                            </select>-->
                        </div>
                    </div>
                    <div  class="form-group" style="margin-top:15px;margin-left:40px;float:left;">
                        <label class="control-label col-xs-4"style="margin-right:10px;float:left">门锁照片</label>
                        <div class="col-xs-5"style="float:right;border: 1px solid #B1D1CE;width:60%;height: 320px;width: 240px;">
                            <div style="margin-top:5px;display:none" id="six_link">
                                <img style="max-height:100%;max-width:100%;align:center" src="" alt="无门锁图片"/>
                            </div></div>
                    </div>
                    <div  class="form-group" style="margin-top:15px;float:left;">
                        <div class="col-xs-5" style="margin-left:400px;">
                            <input type="file" name="f_file[]" multiple="multiple" size="20" />
                            <br><br>
                            <input type="submit" value="upload"/>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<div style=" clear:both;"></div>
<div class="clearfix form-actions"style ="padding-left:50px">
    <div class=" col-md-4">
        <button class="btn btn-info btn-md" type="button" onclick="save();">
            <i class="icon-ok bigger-40"></i>
            提交
        </button>

        <button class="btn btn-info btn-md" type="reset" style="margin-left:100px;">
            <i class="icon-undo bigger-40"></i>
            重置
        </button>
    </div>
</div>
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
    //去发送电子邮件页面
    function submit(){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="下发任务";
        diag.URL = '<%=basePath%>sendtask/sendTask.do';
        diag.Width = 660;
        diag.Height = 470;
        diag.CancelEvent = function(){ //关闭事件
            diag.close();
        };
        diag.show();
    }


</script>
</body>
</html>
