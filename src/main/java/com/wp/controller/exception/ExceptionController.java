package com.wp.controller.exception;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.entity.exception.ExceptionInfo;
import com.wp.service.exception.ExceptionService;
import com.wp.util.Const;
import com.wp.util.PageData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/exception")
public class ExceptionController extends BaseController{

    String menuUrl = "exception/exceptionInfo.do"; //菜单地址(权限用)
    @Resource(name="exceptionService")
    private ExceptionService exceptionService;

    @RequestMapping("/exceptionInfo")
    public ModelAndView getExceptionInfo(Page page) {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            page.setPd(pd);
            List<PageData> exceptionList = exceptionService.listException(page);
            mv.setViewName("exception/exceptionInfo");
            mv.addObject("exceptionList", exceptionList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping("/getExceptionDetail")
    public ModelAndView getExceptionDetail(Page page) {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            PageData result = exceptionService.findById(pd);
            System.out.println(result.getString("workshop"));
            mv.setViewName("exception/exceptionDetail");
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch (Exception e) {
            e.printStackTrace();
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
    public void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }
}
