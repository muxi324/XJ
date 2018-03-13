<%--
  Created by IntelliJ IDEA.
  User: wp
  Date: 2017/12/18
  Time: 11:30
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
    <meta charset="utf-8" />
    <title></title>
    <meta name="description" content="overview & stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="static/css/bootstrap.min.css" rel="stylesheet" />
    <link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="static/css/font-awesome.min.css" />
    <!-- 下拉框 -->
    <link rel="stylesheet" href="static/css/chosen.css" />
    <link rel="stylesheet" href="static/css/ace.min.css" />
    <link rel="stylesheet" href="static/css/ace-responsive.min.css" />
    <link rel="stylesheet" href="static/css/ace-skins.min.css" />
    <script type="text/javascript" src="static/js/jquery-3.1.1.js"></script>
    <!--提示框-->
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <div class="container" id="Print">
      <input type="hidden" name="mission_id" id="mission_id" value="${pd.mission_id}"/>
      <label class="control-label" style="margin-left:45%">任务单详情</label>
      <div class="row" style="padding-top:15px;padding-left:15px;max-width:calc(100% - 15px);" id="zhongxin">
        <div class="col-xs-4" >
            <div ><text>【任务单号】${pd.flow_number}</text></div>
            <div ><text>【任务状态】${pd.mission_condition}</text></div>
            <div ><text>【任务性质】${pd.mission_type}</text></div>
            <div ><text>【任务级别】${pd.mission_level}</text></div>
            <div ><text>【 班  组 】${pd.team}</text></div>
            <div ><text>【安装人员】${pd.worker_name}</text></div>
            <div ><text>【 手  机 】${pd.worker_phone}</text></div>
            <div ><text>【 预期开始时间 】  ${pd.set_start_time}</text></div>
            <div ><text>【 预期结束时间 】  ${pd.set_finish_time}</text></div>
        </div>
          <div class="col-xs-4">
              <div ><text>【房源编号】${pd.house_id}</text></div>
              <div ><text>【房源地址】${pd.house_address}</text></div>
              <div ><text>【门 锁 号】${pd.lock_code}</text></div>
          </div>
          <div class="col-xs-4">
              <div ><text>【锁的类型】${pd.lock_type}</text></div>
              <div ><text>【门的类型】${pd.door_type}</text></div>
              <div ><text>【锁的型号】${pd.lock_model}</text></div>
              <div ><text>【备   注 】${pd.mission_addition}</text></div>
              <div class="row" style="padding-top:10px;padding-left:15px;max-width:calc(100% - 15px)">
                  <div ><<label class="control-label">门锁照片</label></div>
                  <div class="col-xs-1" style="margin-left:10%">
                      <img style="height:100px" src="<%=basePath%>">
                  </div>
              </div>
          </div>
      </div>


          <!--     当任务状态mission_condition=4时-->
          <div class="row" style="padding-top:10px;padding-left:15px;max-width:calc(100% - 15px)">
              <div class="row" style="padding-top:10px;padding-left:15px;max-width:calc(100% - 15px)">
                  <div><label class="control-label">执行前照片</label></div>
                  <div class="col-xs-1" style="margin-left:10%">
                      <img style="height:100px" src="">
                  </div>
              </div>
              <div class="row" style="padding-top:10px;padding-left:15px;max-width:calc(100% - 15px)">
                  <div><label class="control-label">执行后照片</label></div>
                  <div class="col-xs-1" style="margin-left:10%">
                      <img style="height:100px" src="">
                  </div>
              </div>
              <%--从history_location表中提取--%>
              <div ><text>【 说  明 】 ${pd.note}</text></div>
              <div ><text>【房源经纬度】 ${pd.longitude},${pd.latitude}</text></div>
          </div>
      <div  class="form-group" style="margin-top:15px;float:left;width:40%">
          <label class="control-label col-xs-4"style="margin-right:10px;margin-left:22px;float:left">审核人</label>
          <!--            审核人默认为登录用户-->
          <div class="col-xs-5">
              <textarea id="auditor1" cols="20" rows="4" placeholder="">${pd.USERNAME}</textarea>
          </div>
          <label class="control-label col-xs-4"style="margin-right:10px;margin-left:22px;float:left">审核意见</label>
          <div class="col-xs-5">
              <textarea id="auditor_opinion" cols="20" rows="4" placeholder=""></textarea>
          </div>
      </div>
      <div class=" col-md-4">
          <button class="btn btn-info btn-md" type="button" id="pass">
              <i class="icon-ok bigger-40"></i>
              审核通过
          </button>
          <button class="btn btn-info btn-md" type="button" id="remission">
              <i class="icon-ok bigger-40"><a href="<%=basePath%>sendtask/sendtask"></a></i>
              审核不通过重新下发任务
          </button>

      </div>



  </div>
</body>
</html>
