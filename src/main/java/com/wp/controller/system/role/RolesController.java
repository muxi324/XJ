package com.wp.controller.system.role;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.entity.system.Menu;
import com.wp.entity.system.Role;
import com.wp.entity.system.Roles;
import com.wp.service.databank.FactoryService;
import com.wp.service.system.menu.MenuService;
import com.wp.service.system.role.RoleService;
import com.wp.service.system.role.RolesService;
import com.wp.util.*;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名称：RolesController
 * 创建人：FH 
 * 创建时间：2014年6月30日
 * @version
 */
@Controller
@RequestMapping(value="/roles")
public class RolesController extends BaseController {
	
	String menuUrl = "roles/list.do"; //菜单地址(权限用)
	@Resource(name="menuService")
	private MenuService menuService;
	@Resource(name="roleService")
	private RoleService roleService;
	@Resource(name="rolesService")
	private RolesService rolesService;
	@Resource(name="factoryService")
	private FactoryService factoryService;
	
	/**
	 * 权限(增删改查)
	 */
	@RequestMapping(value="/qx")
	public ModelAndView qx()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String msg = pd.getString("msg");
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){rolesService.updateQx(msg,pd);}
			mv.setViewName("save_result");
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 *  备用1 的接口
	 */
	@RequestMapping(value="/kfqx")
	public ModelAndView kfqx()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd =  this.getPageData();;
		try{
			String msg = pd.getString("msg");
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){rolesService.updateKFQx(msg,pd);}
			mv.setViewName("save_result");
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * c权限
	 */
	@RequestMapping(value="/gysqxc")
	public ModelAndView gysqxc()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();;
		try{
			String msg = pd.getString("msg");
			//System.out.println("msg"+msg);

			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){rolesService.gysqxc(msg,pd);}
			mv.setViewName("save_result");
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * --列表
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page)throws Exception{
		logBefore(logger, "roles列表");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
			ModelAndView mv = this.getModelAndView();
			PageData pd = this.getPageData();
		//System.out.println("9999999999999999");
       try {
		   String NAME = pd.getString("NAME");

		   if (null != NAME && !"".equals(NAME)) {
			   NAME = NAME.trim();
			   pd.put("PHONE", NAME);
		   } else {
			   pd.put("PHONE", "");
		   }

		   String user_id = pd.getString("USER_ID");
		   if (user_id == null || "".equals(user_id)) {
			   pd.put("user_id", "1");
		   }

		   String loginUserName = FactoryUtil.getLoginUserName();
		   if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
			   pd.put("factory_id", FactoryUtil.getFactoryId());
			   pd.put("workshop_id", FactoryUtil.getWorkshopId());
			   pd.put("team_id", FactoryUtil.getTeamId());
		   }else{
			   pd.put("factory_id", "");
		   }

		   page.setPd(pd);
		   List<PageData> rolesList_z = rolesService.listPdPageUser(page);
		   for (PageData p : rolesList_z) {
			   String factoryId = p.getString("factory_id");
			   if (StringUtils.isNotEmpty(factoryId)) {
				   PageData find = new PageData();
				   find.put("id", factoryId);
				   String factoryName = factoryService.findById(find).getString("factory");
				   p.put("factory", null);
			   }
		   }
		  // System.out.println(pd.getString("factory_id"));

		  /* List<Roles> rolesList = null;
		   if(loginUserName.equals("admin")){
			   rolesList  = rolesService.listAllRoles(pd);
		   }else{
			   rolesList  = rolesService.listAllRoles2(pd.getString("factory_id"));
		   }*/

		   List<Roles> rolesList =rolesService.listAllRoles(pd);

		   List<Role> roleList = roleService.listAllERRoles();
		   //List<Roles> rolesList_z = rolesService.listAllRolesByPId(pd);

		   List<PageData> kefuqxlist = rolesService.listAllkefu(pd);

		   List<PageData> gysqxlist = rolesService.listAllGysQX(pd);

		   pd = rolesService.findObjectById(pd);


		   mv.addObject("kefuqxlist", kefuqxlist);
		   mv.addObject("gysqxlist", gysqxlist);
		   mv.addObject("rolesList", rolesList);
		   mv.addObject("roleList", roleList);
		   mv.addObject("rolesList_z", rolesList_z);
		   mv.addObject("pd", pd);
		   mv.setViewName("system/role/roles_list");
		   mv.addObject(Const.SESSION_QX, this.getHC());    //按钮权限

	   }catch (Exception e){
		   logger.error(e.toString(), e);
	   }
		return mv;
	}

	/**
	 * 保存新增信息
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			
			String parent_id = pd.getString("PARENT_ID");		//父类角色id
			//System.out.println(parent_id+"---------------");
			pd.put("ROLE_ID", parent_id);			
			if("0".equals(parent_id)){
				pd.put("RIGHTS", "");
			}else{
				String rights = rolesService.findObjectById(pd).getString("RIGHTS");
				//System.out.println();
				pd.put("RIGHTS", (null == rights)?"":rights);
			}

			pd.put("QX_ID", "");
			
			String UUID = this.get32UUID();
			
				pd.put("GL_ID", UUID);
				pd.put("FX_QX", 0);				//发信权限
				pd.put("FW_QX", 0);				//服务权限
				pd.put("QX1", 0);				//操作权限
				pd.put("QX2", 0);				//产品权限
				pd.put("QX3", 0);				//预留权限
				pd.put("QX4", 0);				//预留权限
				if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){rolesService.saveKeFu(pd);}//保存到K权限表
				
				pd.put("U_ID", UUID);
				pd.put("C1", 0);
				pd.put("C2", 0);
				pd.put("C3", 0);
				pd.put("C4", 0);
				pd.put("Q1", 0);				//权限1
				pd.put("Q2", 0);				//权限2
				pd.put("Q3", 0);
				pd.put("Q4", 0);
				if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){rolesService.saveGYSQX(pd);}//保存到G权限表
				pd.put("QX_ID", UUID);
			
			pd.put("ROLE_ID", UUID);
			pd.put("ADD_QX", "0");
			pd.put("DEL_QX", "0");
			pd.put("EDIT_QX", "0");
			pd.put("CHA_QX", "0");
			if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){rolesService.add(pd);}
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * -- 编辑操作中的 编辑
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit( String PHONE )throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();;
		try{
			pd.put("PHONE", PHONE);
			pd = rolesService.findObjectById(pd);
			mv.setViewName("system/role/roles_edit");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * -- 编辑
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();;
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){pd = rolesService.edit(pd);}
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * --编辑操作中的菜单权限
	 */
	@RequestMapping(value="/auth")
	public String auth(@RequestParam String PHONE,Model model)throws Exception{
		
		try{
			//列出所有的菜单
			List<Menu> menuList = menuService.listAllMenu();
			//得到role
			Roles role = rolesService.getRoleById(PHONE);

			String roleRights = role.getRIGHTS();

			helpers(roleRights, menuList);

			JSONArray arr = JSONArray.fromObject(menuList);

			String json = arr.toString();
			json = json.replaceAll("MENU_ID", "id").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodes", json);
			model.addAttribute("PHONE", PHONE);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return "authorization_s";
	}


	
	/**
	 *   --增删改查 按钮权限
	 */
	@RequestMapping(value="/button")
	public ModelAndView button(@RequestParam String PHONE,@RequestParam String msg,Model model)throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
			List<Menu> menuList = menuService.listAllMenu();//列出所有层级的菜单
			//System.out.println("menuList:"+menuList);
			Roles role = rolesService.getRoleById(PHONE);       //获取当前用户的角色
			String roleRights ="";
			//判断是否有增删改查的权限
			if("add_qx".equals(msg)){
				roleRights = role.getADD_QX();
			}else if("del_qx".equals(msg)){
				roleRights = role.getDEL_QX();
			}else if("edit_qx".equals(msg)){
				roleRights = role.getEDIT_QX();
			}else if("cha_qx".equals(msg)){
				roleRights = role.getCHA_QX();
			}
     // 数据库权限表的值的由来 ，
			helpers(roleRights, menuList);
			JSONArray arr = JSONArray.fromObject(menuList);
			String json = arr.toString();
			//System.out.println("json1:"+json);
			json = json.replaceAll("MENU_ID", "id").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			//System.out.println("json2:"+json);

			mv.addObject("zTreeNodes", json);
			mv.addObject("PHONE", PHONE);
			mv.addObject("msg", msg);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.setViewName("system/role/roles_button");
		return mv;
	}
	
	/**
	 * 保存菜单权限
	 */
	@RequestMapping(value="/auth/save")
	public void saveAuth(@RequestParam String PHONE,@RequestParam String menuIds,PrintWriter out)throws Exception{
		PageData pd = new PageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
				if(null != menuIds && !"".equals(menuIds.trim())){
					BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
					Roles role = rolesService.getRoleById(PHONE);
					role.setRIGHTS(rights.toString());
					rolesService.updateRoleRights(role);
					pd.put("rights",rights.toString());
				}else{
					Roles role = new Roles();
					role.setRIGHTS("");
					role.setPHONE(PHONE);
					rolesService.updateRoleRights(role);
					pd.put("rights","");
				}
					
					pd.put("PHONE", PHONE);
					rolesService.setAllRights(pd);
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * --增删改查保存
	 */
	@RequestMapping(value="/roleButton/save")
	public void roleButton(@RequestParam String PHONE,@RequestParam String menuIds,@RequestParam String msg,PrintWriter out)throws Exception{
		PageData pd = this.getPageData();
		try{

			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
				if(null != menuIds && !"".equals(menuIds.trim())){        //如果有选中的单选框
					BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
					pd.put("value",rights.toString());
				}else{
					pd.put("value","");
				}
				pd.put("PHONE", PHONE);
				rolesService.updateQx(msg,pd);
			}
			//System.out.println(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	

	//5.将roleRights 与 menuList 进行比较
	private void helpers(String roleRights,List<Menu> menuList){
		if(Tools.notEmpty(roleRights)){
			for(Menu menu : menuList){
				menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
				if(menu.isHasMenu()){
					List<Menu> subMenuList = menu.getSubMenu();
					for(Menu sub : subMenuList){
						sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
					}
				}
			}
		}
	}
	/* ===============================权限================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	

}
