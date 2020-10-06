package com.wp.controller.system.role;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.entity.system.Menu;
import com.wp.entity.system.Role;
import com.wp.service.system.menu.MenuService;
import com.wp.service.system.role.RoleService;
import com.wp.util.*;
import net.sf.json.JSONArray;
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
 * 类名称：RoleController
 * 创建人：FH 
 * 创建时间：2014年6月30日
 * @version
 */
@Controller
@RequestMapping(value="/role")
public class RoleController extends BaseController {
	
	String menuUrl = "role.do"; //菜单地址(权限用)
	@Resource(name="menuService")
	private MenuService menuService;
	@Resource(name="roleService")
	private RoleService roleService;
	
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
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){roleService.updateQx(msg,pd);}
			mv.setViewName("save_result");
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * K权限
	 */
	@RequestMapping(value="/kfqx")
	public ModelAndView kfqx()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String msg = pd.getString("msg");
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){roleService.updateKFQx(msg,pd);}
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
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String msg = pd.getString("msg");
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){roleService.gysqxc(msg,pd);}
			mv.setViewName("save_result");
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 1. 列表 显示组织管理页面
	 *  Page：分页
	 */
	@RequestMapping
	public ModelAndView list(Page page)throws Exception{
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();

			String roleId = pd.getString("ROLE_ID");
			if(roleId == null || "".equals(roleId)){
				pd.put("ROLE_ID", "1");
			}
			// roleId=1 是超级管理员的角色

		//System.out.println("pd1:"+pd);  //pd1:{ROLE_ID=1}
			List<Role> roleList = roleService.listAllRoles();				//列出所有角色
		//System.out.println("roleList:"+roleList);
          //Role是角色表模型，
          //roleList:[Role{ROLE_ID='1', ROLE_NAME='超级管理员', RIGHTS='1963563355864039526', PARENT_ID='0', ADD_QX='1', DEL_QX='1', EDIT_QX='1', CHA_QX='1', QX_ID='1'}]
			List<Role> roleList_z = roleService.listAllRolesByPId(pd);		//列出此角色的所有下级
		//System.out.println("roleList_z:"+roleList_z);
		  //role表的role的list集合

			List<PageData> kefuqxlist = roleService.listAllkefu(pd);		//管理权限列表
		//System.out.println("kefuqxlist:"+kefuqxlist);
		//{FW_QX=0, FX_QX=0, GL_ID=02f3cf524c874a6aa16121cdee79f781, ROLE_ID=1, QX2=0, QX1=0, QX4=0, QX3=0}。。。。

			List<PageData> gysqxlist = roleService.listAllGysQX(pd);		//用户权限列表
		//System.out.println("gysqxlist:"+gysqxlist);
		//{C3=0, Q1=0, C4=0, Q2=0, Q3=0, Q4=0, U_ID=02f3cf524c874a6aa16121cdee79f781, C1=0, C2=0}, 。。。

			pd = roleService.findObjectById(pd);
		//System.out.println("pd:"+pd);
		//pd:{CHA_QX=1, DEL_QX=1, RIGHTS=1963563355864039526, ROLE_ID=1, ROLE_NAME=超级管理员, ADD_QX=1, PARENT_ID=0, EDIT_QX=1}

			mv.addObject("pd", pd);
			mv.addObject("kefuqxlist", kefuqxlist);
			mv.addObject("gysqxlist", gysqxlist);
			mv.addObject("roleList", roleList);
			mv.addObject("roleList_z", roleList_z);
			mv.setViewName("system/role/role_list");
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		
		return mv;
	}
	
	/**
	 * 2. 新增页面, 超级管理员头部新增组页面接口
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			mv.setViewName("system/role/role_add");
			mv.addObject("pd", pd);
		} catch(Exception e){
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
				String rights = roleService.findObjectById(pd).getString("RIGHTS");
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
				if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){roleService.saveKeFu(pd);}//保存到K权限表
				
				pd.put("U_ID", UUID);
				pd.put("C1", 0);
				pd.put("C2", 0);
				pd.put("C3", 0);
				pd.put("C4", 0);
				pd.put("Q1", 0);				//权限1
				pd.put("Q2", 0);				//权限2
				pd.put("Q3", 0);
				pd.put("Q4", 0);
				if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){roleService.saveGYSQX(pd);}//保存到G权限表
				pd.put("QX_ID", UUID);
			
			pd.put("ROLE_ID", UUID);
			pd.put("ADD_QX", "0");
			pd.put("DEL_QX", "0");
			pd.put("EDIT_QX", "0");
			pd.put("CHA_QX", "0");
			if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){roleService.add(pd);}
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 2.请求编辑本组名称的接口
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit( String ROLE_ID )throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("ROLE_ID", ROLE_ID);
			pd = roleService.findObjectById(pd);
			mv.setViewName("system/role/role_edit");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){pd = roleService.edit(pd);}
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 3. 请求编辑本组的角色菜单授权页面
	 */
	@RequestMapping(value="/auth")
	public String auth(@RequestParam String ROLE_ID,Model model)throws Exception{
		
		try{
			//列出所有的菜单
			List<Menu> menuList = menuService.listAllMenu();
			//得到role
			Role role = roleService.getRoleById(ROLE_ID);

			String roleRights = role.getRIGHTS();

			helpers(roleRights, menuList);

			JSONArray arr = JSONArray.fromObject(menuList);

			String json = arr.toString();
			json = json.replaceAll("MENU_ID", "id").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodes", json);
			model.addAttribute("roleId", ROLE_ID);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return "authorization";
	}


	
	/**
	 * 7.。请求角色按钮授权页面（增删改查），+msg =  roleButton('${var.ROLE_ID }','add_qx')
	 */
	@RequestMapping(value="/button")
	public ModelAndView button(@RequestParam String ROLE_ID,@RequestParam String msg,Model model)throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
			// System.out.println(msg);

			List<Menu> menuList = menuService.listAllMenu();//列出所有层级的菜单
			//System.out.println("menuList:"+menuList);
			Role role = roleService.getRoleById(ROLE_ID);       //获取当前用户的角色
			//System.out.println("role:"+role);
			String roleRights = "";

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
			mv.addObject("roleId", ROLE_ID);
			mv.addObject("msg", msg);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.setViewName("system/role/role_button");
		return mv;
	}
	
	/**
	 * 保存角色菜单权限
	 */
	@RequestMapping(value="/auth/save")
	public void saveAuth(@RequestParam String ROLE_ID,@RequestParam String menuIds,PrintWriter out)throws Exception{
		PageData pd = new PageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
				if(null != menuIds && !"".equals(menuIds.trim())){
					BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
					Role role = roleService.getRoleById(ROLE_ID);
					role.setRIGHTS(rights.toString());
					roleService.updateRoleRights(role);
					pd.put("rights",rights.toString());
				}else{
					Role role = new Role();
					role.setRIGHTS("");
					role.setROLE_ID(ROLE_ID);
					roleService.updateRoleRights(role);
					pd.put("rights","");
				}
					
					pd.put("roleId", ROLE_ID);
					roleService.setAllRights(pd);
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * 保存角色按钮权限 （增删改查的按钮）
	 */
	@RequestMapping(value="/roleButton/save")
	public void roleButton(@RequestParam String ROLE_ID,@RequestParam String menuIds,@RequestParam String msg,PrintWriter out)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
				if(null != menuIds && !"".equals(menuIds.trim())){        //如果有选中的单选框
					BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
					pd.put("value",rights.toString());
				}else{
					pd.put("value","");
				}
				pd.put("ROLE_ID", ROLE_ID);
				roleService.updateQx(msg,pd);
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * 6..删除  操作上的删除接口
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object deleteRole(@RequestParam String ROLE_ID)throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		String errInfo = "";
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
				pd.put("ROLE_ID", ROLE_ID);
				List<Role> roleList_z = roleService.listAllRolesByPId(pd);		//列出此部门的所有下级
				if(roleList_z.size() > 0){
					errInfo = "false";
				}else{
					
					List<PageData> userlist = roleService.listAllUByRid(pd);
				/*	List<PageData> appuserlist = roleService.listAllAppUByRid(pd);
					if(userlist.size() > 0 || appuserlist.size() > 0){*/
					if(userlist.size() > 0 ){
						errInfo = "false2";
					}else{
					roleService.deleteRoleById(ROLE_ID);
					roleService.deleteKeFuById(ROLE_ID);
					roleService.deleteGById(ROLE_ID);
					errInfo = "success";
					}
				}
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
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
