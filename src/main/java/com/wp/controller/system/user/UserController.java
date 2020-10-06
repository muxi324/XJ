package com.wp.controller.system.user;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.entity.system.Role;
import com.wp.entity.system.User;
import com.wp.service.databank.FactoryService;
import com.wp.service.databank.TeamService;
import com.wp.service.databank.WorkshopService;
import com.wp.service.system.menu.MenuService;
import com.wp.service.system.role.RoleService;
import com.wp.service.system.user.UserService;
import com.wp.service.worker.WorkerService;
import com.wp.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/** 
 * 类名称：UserController
 * 创建人：FH 
 * 创建时间：2014年6月28日
 * @version
 */
@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController {
	
	String menuUrl = "user/listUsers.do"; //菜单地址(权限用)
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="roleService")
	private RoleService roleService;
	@Resource(name="menuService")
	private MenuService menuService;
	@Resource(name = "workerService")
	private WorkerService workerService;
	@Resource(name="teamService")
	private TeamService teamService;
	@Resource(name="workshopService")
	private WorkshopService workshopService;
	@Resource(name="factoryService")
	private FactoryService factoryService;
	
	
	/**
	 * 保存用户
	 */
	@RequestMapping(value="/saveU")
	public ModelAndView saveU(PrintWriter out) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USER_ID", this.get32UUID());	//ID
		pd.put("RIGHTS", "");					//权限
		pd.put("LAST_LOGIN", "");				//最后登录时间
		pd.put("IP", "");						//IP
		pd.put("STATUS", "0");					//状态
		pd.put("SKIN", "default");				//默认皮肤

		pd.put("passw","123");
		String roleId = pd.getString("ROLE_ID");
		String role = userService.findRoleById(roleId);
		pd.put("post",role);
		pd.put("add_time",Tools.date2Str(new Date()));
		//pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		String loginUserName = FactoryUtil.getLoginUserName();
		if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
			pd.put("factory_id",FactoryUtil.getFactoryId());
			String workshopId = FactoryUtil.getWorkshopId();
			if(StringUtils.isNotEmpty(workshopId)){    //操作人为车间以下职位
				pd.put("workshop_id",workshopId);
				String teamId = FactoryUtil.getTeamId();
				if(StringUtils.isNotEmpty(teamId)) {    //操作人为班组长
					pd.put("team_id", teamId);
				}
			}

		}

		userService.saveU(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 判断用户名是否存在
	 */
	@RequestMapping(value="/hasU")
	@ResponseBody
	public Object hasU(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(userService.findByUId(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 判断邮箱是否存在
	 */
	@RequestMapping(value="/hasE")
	@ResponseBody
	public Object hasE(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			
			if(userService.findByUE(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 判断编码是否存在
	 */
	@RequestMapping(value="/hasN")
	@ResponseBody
	public Object hasN(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(userService.findByUN(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	/**
	 * 判断手机号是否存在
	 */
	@RequestMapping(value="/hasP")
	@ResponseBody
	public Object hasP(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(userService.findByUP(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 修改用户
	 */
	@RequestMapping(value="/editU")
	public ModelAndView editU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PrintUtil.print(pd.getString("USER_ID"));
		/*if(pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))){
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		}*/
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			String loginUserName = FactoryUtil.getLoginUserName();
			String roleId = pd.getString("ROLE_ID");
			String role = userService.findRoleById(roleId);
			pd.put("post",role);
			if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
				pd.put("factory_id",FactoryUtil.getFactoryId());
				String workshopId = FactoryUtil.getWorkshopId();
				if(StringUtils.isNotEmpty(workshopId)){    //操作人为车间以下职位
					pd.put("workshop_id",workshopId);
					String teamId = FactoryUtil.getTeamId();
					if(StringUtils.isNotEmpty(teamId)) {    //操作人为班组长
						pd.put("team_id", teamId);
					}
				}
			}

			userService.editU(pd);
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去修改用户页面
	 */
	@RequestMapping(value="/goEditU")
	public ModelAndView goEditU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user= (User) session.getAttribute("sessionUser");
		String user_id = user.getROLE_ID();
		PrintUtil.print(pd.getString("USER_ID"));

		//顶部修改个人资料
		String fx = pd.getString("fx");
		
		//System.out.println(fx);
		
		if("head".equals(fx)){
			mv.addObject("fx", "head");
		}else{
			mv.addObject("fx", "user");
		}
		
		List<Role> roleList = roleService.listAllERRoles();			//列出所有二级角色

		if(user_id.equals("9bb7ce511a6f4b81b48f07ac6ea7c7a0")){  //如果他是车间管理员
			for(int i=0;i<roleList.size();i++){
				if( (roleList.get(i).getROLE_ID().equals("63f2b93025994edf8166c2164cd00bc2")) )
					roleList.remove(i);
				if( (roleList.get(i).getROLE_ID().equals("77af0944668e417eb3f26dad06dc625e")) )
					roleList.remove(i);
				if( (roleList.get(i).getROLE_ID().equals("9bb7ce511a6f4b81b48f07ac6ea7c7a0")) )
					roleList.remove(i);
				if( (roleList.get(i).getROLE_ID().equals("1d759aa37b3f4be6b0f6795561239788")) )//离职
					roleList.remove(i);
			}
			//System.out.println("---------888888888888888888--");
		}

		pd = userService.findByUiId(pd);							//根据ID读取

//		从session中获取两个id
		String factory_id = FactoryUtil.getFactoryId();
		String workshop_id = FactoryUtil.getWorkshopId();

		List<PageData> workshopList = new ArrayList<PageData>();
		if(StringUtils.isNotEmpty(factory_id)) {
			PageData factory = new PageData();
			factory.put("factory_id",factory_id);
			List<PageData> teamList = new ArrayList<PageData>();

			if(StringUtils.isNotEmpty(workshop_id)){
				PageData workshop = new PageData();
				workshop.put("id",workshop_id);
				String workshopName = workshopService.findById(workshop).getString("workshop");
				pd.put("workshop",workshopName);
				pd.put("workshop_id",workshop_id);
				workshop.put("workshop_id", workshop_id);
				workshop.put("factory_id", factory_id);
				teamList = teamService.findTeamByW(workshop);   //找到班组
			}else{
				workshopList = workshopService.listWorkshopByFac(factory);
				mv.addObject("workshopList",workshopList);
			}
			mv.addObject("teamList",teamList);
		}else{
			List<PageData> factoryList =  factoryService.listAllFac();
			mv.addObject("factoryList",factoryList);
		}
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "editU");
		pd.put("userName",FactoryUtil.getLoginUserName());
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		
		return mv;
	}
	
	/**
	 * 去新增用户页面
	 */
	@RequestMapping(value="/goAddU")
	public ModelAndView goAddU()throws Exception{

		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user= (User) session.getAttribute("sessionUser");
		String role_id = user.getROLE_ID();
		//System.out.println(role_id);
				PrintUtil.print("fac id is:" + FactoryUtil.getFactoryId());
				ModelAndView mv = this.getModelAndView();
				PageData pd = new PageData();
				pd = this.getPageData();
				List<Role> roleList;
				roleList = roleService.listAllERRoles();			//列出所有二级角色
				/*for(int i=0;i<roleList.size();i++){
					System.out.println(roleList.get(i).getROLE_NAME());
				}*/
			//System.out.println("---------------------------------");
				if(role_id.equals("9bb7ce511a6f4b81b48f07ac6ea7c7a0")){  //如果他是车间管理员
					for(int i=0;i<roleList.size();i++){
						if( (roleList.get(i).getROLE_ID().equals("63f2b93025994edf8166c2164cd00bc2")) )
							roleList.remove(i);
						if( (roleList.get(i).getROLE_ID().equals("77af0944668e417eb3f26dad06dc625e")) )
							roleList.remove(i);
						if( (roleList.get(i).getROLE_ID().equals("9bb7ce511a6f4b81b48f07ac6ea7c7a0")) )
							roleList.remove(i);
						if( (roleList.get(i).getROLE_ID().equals("1d759aa37b3f4be6b0f6795561239788")) )//离职
							roleList.remove(i);
					}
					//println("---------777--");
				}
				/*for(int i=0;i<roleList.size();i++){
					//System.out.println(roleList.get(i).getROLE_NAME());
				}*/


				String factory_id = FactoryUtil.getFactoryId();
		        String workshop_id = FactoryUtil.getWorkshopId();
		        List<PageData> workshopList = new ArrayList<PageData>();
		        if(StringUtils.isNotEmpty(factory_id)) {
		        	PageData factory = new PageData();
					factory.put("factory_id",factory_id);
					List<PageData> teamList = new ArrayList<PageData>();
					if(StringUtils.isNotEmpty(workshop_id)){
						PageData workshop = new PageData();
						workshop.put("id",workshop_id);
						String workshopName = workshopService.findById(workshop).getString("workshop");
						pd.put("workshop",workshopName);
						pd.put("workshop_id",workshop_id);
						workshop.put("workshop_id", workshop_id);
						workshop.put("factory_id", factory_id);
						teamList = teamService.findTeamByW(workshop);   //找到班组
					}else{
						workshopList = workshopService.listWorkshopByFac(factory);
						mv.addObject("workshopList",workshopList);
					}
					mv.addObject("teamList",teamList);
				}else{
					List<PageData> factoryList =  factoryService.listAllFac();
					mv.addObject("factoryList",factoryList);
				}
				mv.setViewName("system/user/user_add");
				mv.addObject("msg", "saveU");
				pd.put("userName",FactoryUtil.getLoginUserName());
				mv.addObject("pd", pd);
				mv.addObject("roleList", roleList);
				return mv;
			}

			/**
			 * 显示用户列表(用户组)
			 */
			@RequestMapping(value="/listUsers")
			public ModelAndView listUsers(Page page)throws Exception{
				ModelAndView mv = this.getModelAndView();
				PageData pd = new PageData();
				pd = this.getPageData();

				String USERNAME = pd.getString("USERNAME");

				//如果用户已登录
				if(null != USERNAME && !"".equals(USERNAME)){
					USERNAME = USERNAME.trim();
					pd.put("USERNAME", USERNAME);
				}

				String lastLoginStart = pd.getString("lastLoginStart");
				String lastLoginEnd = pd.getString("lastLoginEnd");

				if(lastLoginStart != null && !"".equals(lastLoginStart)){
					lastLoginStart = lastLoginStart+" 00:00:00";
					pd.put("lastLoginStart", lastLoginStart);
				}
				if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
					lastLoginEnd = lastLoginEnd+" 00:00:00";
					pd.put("lastLoginEnd", lastLoginEnd);
				}
				String loginUserName = FactoryUtil.getLoginUserName();
				if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
					pd.put("factory_id",FactoryUtil.getFactoryId());
					pd.put("workshop_id",FactoryUtil.getWorkshopId());
					pd.put("team_id",FactoryUtil.getTeamId());
				}
				page.setPd(pd);
				List<PageData>	userList = userService.listPdPageUser(page);			//列出用户列表
				List<Role> roleList = roleService.listAllERRoles();						//列出所有二级角色
				for (PageData p : userList) {
					String factoryId = p.getString("factory_id");
					if(StringUtils.isNotEmpty(factoryId)){
						PageData find = new PageData();
						find.put("id",factoryId);
						String factoryName = factoryService.findById(find).getString("factory");
						p.put("factory",factoryName);
					}
					String workshopId = p.getString("workshop_id");
					if(StringUtils.isNotEmpty(workshopId)){
						PageData workshop = new PageData();
						workshop.put("id",workshopId);
						String workshopName = workshopService.findById(workshop).getString("workshop");
						p.put("workshop",workshopName);
					}
					String teamId = p.getString("team_id");
					if(StringUtils.isNotEmpty(teamId)){
						PageData team = new PageData();
						team.put("id",teamId);
						String teamName = teamService.findById(team).getString("team");
						p.put("team",teamName);
					}
				}

				mv.setViewName("system/user/user_list");
				mv.addObject("userList", userList);
				mv.addObject("roleList", roleList);
				mv.addObject("pd", pd);

				mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
				return mv;
			}

			/**
			 * 显示用户列表(tab方式)
			 */
			@RequestMapping(value="/listtabUsers")
			public ModelAndView listtabUsers(Page page)throws Exception{
				ModelAndView mv = this.getModelAndView();
				PageData pd = new PageData();
				pd = this.getPageData();
				List<PageData>	userList = userService.listAllUser(pd);			//列出用户列表
				mv.setViewName("system/user/user_tb_list");
				mv.addObject("userList", userList);
				mv.addObject("pd", pd);
				mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
				return mv;
			}

			/**
			 * 删除用户
			 */
			@RequestMapping(value="/deleteU")
			public void deleteU(PrintWriter out){
				PageData pd = new PageData();
				try{
					pd = this.getPageData();
					if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){userService.deleteU(pd);}
					out.write("success");
					out.close();
				} catch(Exception e){
					logger.error(e.toString(), e);
				}

			}

			/**
			 * 批量删除
			 */
			@RequestMapping(value="/deleteAllU")
			@ResponseBody
			public Object deleteAllU() {
				PageData pd = new PageData();
				Map<String,Object> map = new HashMap<String,Object>();
				try {
					pd = this.getPageData();
					List<PageData> pdList = new ArrayList<PageData>();
					String USER_IDS = pd.getString("USER_IDS");

					if(null != USER_IDS && !"".equals(USER_IDS)){
						String ArrayUSER_IDS[] = USER_IDS.split(",");
						if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){userService.deleteAllU(ArrayUSER_IDS);}
						pd.put("msg", "ok");
					}else{
						pd.put("msg", "no");
					}

					pdList.add(pd);
					map.put("list", pdList);
				} catch (Exception e) {
					logger.error(e.toString(), e);
				} finally {
					logAfter(logger);
				}
				return AppUtil.returnObject(pd, map);
			}
			//===================================================================================================



			/*
			 * 导出用户信息到EXCEL
			 * @return
			 */
			@RequestMapping(value="/excel")
			public ModelAndView exportExcel(){
				ModelAndView mv = this.getModelAndView();
				PageData pd = new PageData();
				pd = this.getPageData();
				try{
					if(Jurisdiction.buttonJurisdiction(menuUrl, "cha")){
						//检索条件===
						String USERNAME = pd.getString("USERNAME");
						if(null != USERNAME && !"".equals(USERNAME)){
							USERNAME = USERNAME.trim();
							pd.put("USERNAME", USERNAME);
						}
						String lastLoginStart = pd.getString("lastLoginStart");
						String lastLoginEnd = pd.getString("lastLoginEnd");
						if(lastLoginStart != null && !"".equals(lastLoginStart)){
							lastLoginStart = lastLoginStart+" 00:00:00";
							pd.put("lastLoginStart", lastLoginStart);
						}
						if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
							lastLoginEnd = lastLoginEnd+" 00:00:00";
							pd.put("lastLoginEnd", lastLoginEnd);
						}
						//检索条件===

						Map<String,Object> dataMap = new HashMap<String,Object>();
						List<String> titles = new ArrayList<String>();

						titles.add("用户名"); 		//1
						titles.add("编号");  		//2
						titles.add("姓名");			//3
						titles.add("职位");			//4
						titles.add("手机");			//5
						titles.add("邮箱");			//6
						titles.add("最近登录");		//7
						titles.add("上次登录IP");	//8

						dataMap.put("titles", titles);
				
				List<PageData> userList = userService.listAllUser(pd);
				List<PageData> varList = new ArrayList<PageData>();
				for(int i=0;i<userList.size();i++){
					PageData vpd = new PageData();
					vpd.put("var1", userList.get(i).getString("USERNAME"));		//1
					vpd.put("var2", userList.get(i).getString("NUMBER"));		//2
					vpd.put("var3", userList.get(i).getString("NAME"));			//3
					vpd.put("var4", userList.get(i).getString("ROLE_NAME"));	//4
					vpd.put("var5", userList.get(i).getString("PHONE"));		//5
					vpd.put("var6", userList.get(i).getString("EMAIL"));		//6
					vpd.put("var7", userList.get(i).getString("LAST_LOGIN"));	//7
					vpd.put("var8", userList.get(i).getString("IP"));			//8
					varList.add(vpd);
				}
				dataMap.put("varList", varList);
				ObjectExcelView erv = new ObjectExcelView();					//执行excel操作
				mv = new ModelAndView(erv,dataMap);
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 打开上传EXCEL页面
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/user/uploadexcel");
		return mv;
	}
	
	/**
	 * 下载模版
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Users.xls", "Users.xls");
		
	}
	
	/**
	 * 从EXCEL导入到数据库
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(
			@RequestParam(value="excel",required=false) MultipartFile file
			) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "userexcel");							//执行上传
			
			List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);	//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			
			/*存入数据库操作======================================*/
			pd.put("RIGHTS", "");					//权限
			pd.put("LAST_LOGIN", "");				//最后登录时间
			pd.put("IP", "");						//IP
			pd.put("STATUS", "0");					//状态
			pd.put("SKIN", "default");				//默认皮肤
			
			List<Role> roleList = roleService.listAllERRoles();	//列出所有二级角色
			
			pd.put("ROLE_ID", roleList.get(0).getROLE_ID());	//设置角色ID为随便第一个
			/**
			 * var0 :编号
			 * var1 :姓名
			 * var2 :手机
			 * var3 :邮箱
			 * var4 :备注
			 */
			for(int i=0;i<listPd.size();i++){		
				pd.put("USER_ID", this.get32UUID());										//ID
				pd.put("NAME", listPd.get(i).getString("var1"));							//姓名
				
				String USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var1"));	//根据姓名汉字生成全拼
				pd.put("USERNAME", USERNAME);	
				if(userService.findByUId(pd) != null){										//判断用户名是否重复
					USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var1"))+ Tools.getRandomNum();
					pd.put("USERNAME", USERNAME);
				}
				pd.put("BZ", listPd.get(i).getString("var4"));								//备注
				if(Tools.checkEmail(listPd.get(i).getString("var3"))){						//邮箱格式不对就跳过
					pd.put("EMAIL", listPd.get(i).getString("var3"));						
					if(userService.findByUE(pd) != null){									//邮箱已存在就跳过
						continue;
					}
				}else{
					continue;
				}
				
				pd.put("NUMBER", listPd.get(i).getString("var0"));							//编号已存在就跳过
				pd.put("PHONE", listPd.get(i).getString("var2"));							//手机号
				
				//pd.put("PASSWORD", new SimpleHash("SHA-1", USERNAME, "123").toString());	//默认密码123
				if(userService.findByUN(pd) != null){
					continue;
				}
				userService.saveU(pd);
			}
			/*存入数据库操作======================================*/
			
			mv.addObject("msg","success");
		}
		
		mv.setViewName("save_result");
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	

	/* ===============================权限================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
}
