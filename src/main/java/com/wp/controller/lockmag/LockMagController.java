package com.wp.controller.lockmag;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.entity.system.User;
import com.wp.service.lockmag.LockMagService;
import com.wp.service.system.role.RoleService;
import com.wp.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wp on 2017/12/21.
 */
@Controller
@RequestMapping(value="/lockmag")
public class LockMagController extends BaseController {
    String menuUrl = "lockmag/list.do"; //菜单地址(权限用)
    @Resource(name="lockMagService")
    private LockMagService lockMagService;
    @Resource(name="roleService")
    private RoleService roleService;

    /**
     * 验证取锁码
     */
    @RequestMapping(value="/verify" ,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String verify()throws Exception{
        Map<String,String> map = new HashMap<String,String>();
        PageData pd = this.getPageData();
        String flow_number = pd.getString("mission");
        String random_code = pd.getString("code");
        String result = new String();

        pd.put("flow_number", flow_number);
        pd.put("random_code", random_code);
        pd = lockMagService.findByFlow(pd);
        if(pd == null){
            result = "0";
        }else{
            result = "1";
        }

        return result;
    }


    /**
     * 详情
     */
    @RequestMapping(value="/goTaskInfo")
    public ModelAndView goDetail(){
        logBefore(logger, "去任务信息页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = lockMagService.findById(pd);	//根据ID读取
            mv.setViewName("lockmag/task_info");
            mv.addObject("msg", "TaskInfo");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 列表
     */
    @RequestMapping(value="/list")
    public ModelAndView list(Page page){
        logBefore(logger, "列表任务单");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            String  enquiry = pd.getString("enquiry");
            if(null != enquiry && !"".equals(enquiry)){
                pd.put("enquiry", enquiry.trim());
            }else {
                pd.put("enquiry", "");
            }
            String sendTimeStart = pd.getString("sendTimeStart");
            String sendTimeEnd = pd.getString("sendTimeEnd");

            if(sendTimeStart != null && !"".equals(sendTimeStart)){
                sendTimeStart = sendTimeStart+" 00:00:00";
                pd.put("sendTimeStart", sendTimeStart);
            }
            if(sendTimeEnd != null && !"".equals(sendTimeEnd)){
                sendTimeEnd = sendTimeEnd+" 00:00:00";
                pd.put("sendTimeEnd", sendTimeEnd);
            }
            String  mission_condition = pd.getString("status");
            pd.put("mission_condition", mission_condition);
            page.setPd(pd);
            List<PageData> varList = lockMagService.list(page);	//列出${objectName}列表
            mv.setViewName("lockmag/lockmag");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /* ===============================权限================================== */
    public Map<String, String> getHC(){
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
    }
	/* ===============================权限================================== */

    @InitBinder
    public void initBinder(WebDataBinder binder){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
    }
}
