package com.wp.service.system.role;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.entity.system.Role;
import com.wp.entity.system.Roles;
import com.wp.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("rolesService")
public class RolesService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public List<Role> listAllERRoles() throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllERRoles", null);
		
	}
	
	
	public List<Role> listAllappERRoles() throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllappERRoles", null);
		
	}
	

	public List<Roles> listAllRoles(PageData pd) throws Exception {
		return (List<Roles>) dao.findForList("RolesMapper.listAllRoles", pd);
		
	}
	
	//通过当前登录用的角色id获取管理权限数据
	public PageData findGLbyrid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RoleMapper.findGLbyrid", pd);
	}
	
	//通过当前登录用的角色id获取用户权限数据
	public PageData findYHbyrid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RoleMapper.findYHbyrid", pd);
	}

   //删除
	public List<PageData> listAllUByRid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RolesMapper.listAllUByRid", pd);
		
	}
	
	//列出此角色下的所有会员
	public List<PageData> listAllAppUByRid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RoleMapper.listAllAppUByRid", pd);
		
	}
	
   // --删除
	public List<Roles> listAllRolesByPId(PageData pd) throws Exception {
		return (List<Roles>) dao.findForList("RolesMapper.listAllRolesByPId", pd);
		
	}


	public List<PageData> listAllkefu(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RolesMapper.listAllkefu", pd);
	}
	

	public List<PageData> listAllGysQX(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RolesMapper.listAllGysQX", pd);
	}

	

	 //--删除
	public void deleteRoleById(String ROLE_ID) throws Exception {
		dao.delete("RolesMapper.deleteRoleById", ROLE_ID);
		
	}

//--
	public Roles getRoleById(String PHONE) throws Exception {
		return (Roles) dao.findForObject("RolesMapper.getRoleById", PHONE);
		
	}

	public void updateRoleRights(Roles roles) throws Exception {
		dao.update("RolesMapper.updateRoleRights", roles);
	}

	//  --分页
	public List<PageData> listPdPageUser(Page page)throws Exception{
		return (List<PageData>) dao.findForList("RolesMapper.userlistPage", page);
	}




	/**
	 * --权限(增删改查)
	 */
	public void updateQx(String msg,PageData pd) throws Exception {
		dao.update("RolesMapper."+msg, pd);
	}
	
	/**
	 *  备用1
	 */
	public void updateKFQx(String msg,PageData pd) throws Exception {
		dao.update("RolesMapper."+msg, pd);
	}


	/**
	 * Gc权限
	 */
	public void gysqxc(String msg,PageData pd) throws Exception {
		dao.update("RolesMapper."+msg, pd);
	}


	/**
	 * 给全部子职位加菜单权限
	 */
	public void setAllRights(PageData pd) throws Exception {
		dao.update("RolesMapper.setAllRights", pd);
		
	}
	
	/**
	 * 添加
	 */
	public void add(PageData pd) throws Exception {
		dao.findForList("RoleMapper.insert", pd);
	}
	
	/**
	 * 保存客服权限
	 */
	public void saveKeFu(PageData pd) throws Exception {
		dao.findForList("RoleMapper.saveKeFu", pd);
	}
	
	/**
	 * 保存G权限
	 */
	public void saveGYSQX(PageData pd) throws Exception {
		dao.findForList("RoleMapper.saveGYSQX", pd);
	}
	
	/**
	 * --通过id查找
	 */
	public PageData findObjectById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("RolesMapper.findObjectById", pd);
	}
	
	/**
	 * -- 编辑角色
	 */
	public PageData edit(PageData pd) throws Exception {
		return (PageData)dao.findForObject("RolesMapper.edit", pd);
	}

}
