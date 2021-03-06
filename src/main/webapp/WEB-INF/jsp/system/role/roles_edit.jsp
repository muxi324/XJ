<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		
<script type="text/javascript">
	
	top.hangge();
	
	//保存
	function save(){
		if($("post").val()==""){
			$("#post").focus();
			return false;
		}
			$("#form1").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
		<form action="roles/edit.do" name="form1" id="form1"  method="post">
		<input type="hidden" name="PHONE" id="id" value="${pd.PHONE}"/>
			<div id="zhongxin">
			<table>
				<tr>
					<td><input type="text" name="post" id="post" value="${pd.post}" placeholder="这里输入角色" title="名称" /></td>
				</tr>
				<tr>
					<td>
						<%--<input type="text" name="level" id="level" value="${pd.level}" placeholder="这里输入角色级别" title="名称" />--%>
						<select class="chzn-select" name="level" id="level" placeholder="这里选择角色级别" >
							<option value="">全部</option>
							<option value="0" <c:if test="${pd.level==0}">selected</c:if>>无权使用app</option>
							<option value="1" <c:if test="${pd.level==1}">selected</c:if>>管理级别（使用app管理功能）</option>
							<option value="2" <c:if test="${pd.level==2}">selected</c:if>>执行任务级别（使用app执行任务功能）</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;">
						<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
						<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
					</td>
				</tr>
			</table>
			</div>
		</form>
	
	<div id="zhongxin2" class="center" style="display:none"><img src="static/images/jzx.gif"  style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>
		<!-- 引入 -->
		<script src="static/1.9.1/jquery.min.js"></script>
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
</body>
</html>
