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
    <script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
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
          <!--    根据任务的状态显示不同的参数-->
          <!--     当任务状态mission_condition=1时-->
          <div class="row" style="padding-top:10px;padding-left:15px;max-width:calc(100% - 15px);height:20%">
              <div ><text>【拒单理由】${pd.refuse_reason}</text></div>
              <div ><text>【拒单时间】${pd.refuse_time}</text></div>
          </div>

          <!--     当任务状态mission_condition=2时-->
          <div class="row" style="padding-top:10px;padding-left:15px;max-width:calc(100% - 15px);height:20%">
              <div ><text>【接单时间】${pd.accept_time}</text></div>
          </div>
          <!--     当任务状态mission_condition=3时-->
          <div class="row" style="padding-top:10px;padding-left:15px;max-width:calc(100% - 15px);height:20%">
              <div ><text>【接单时间】${pd.accept_time}</text></div>
              <div ><text>【取锁时间】${pd.get_lock_time}</text></div>
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
          <!--     当任务状态mission_condition=5或6时-->
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
              <div ><text>【 说  明 】 ${pd.note}</text></div>
              <div ><text>【房源经纬度】 ${pd.longitude},${pd.latitude}</text></div>
          </div>
          <div class="row" style="padding-top:10px;padding-left:15px;max-width:calc(100% - 15px);height:20%">
              <div ><text>【审 核 人】${pd.auditor}</text></div>
              <div ><text>【审核意见】${pd.auditor_opinion}</text></div>
          </div>

          <div class="row" style="padding-top:10px;padding-left:15px;max-width:calc(100% - 15px);height:20%">
              <div ><text>【下达时间】${pd.send_time}</text></div>
              <div ><text>【接收时间】${pd.accept_time}</text></div>
              <div ><text>【取锁时间】${pd.get_lock_time}</text></div>
              <div ><text>【完成时间】${pd.finish_time}</text></div>
              <div ><text>【审核时间】${pd.auditor_time}</text></div>
          </div>


  </div>
</body>
</html>
