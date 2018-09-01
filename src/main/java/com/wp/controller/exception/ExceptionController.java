package com.wp.controller.exception;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.entity.exception.ExceptionInfo;
import com.wp.service.exception.ExceptionService;
import com.wp.util.Const;
import com.wp.util.FactoryUtil;
import com.wp.util.Jurisdiction;
import com.wp.util.PageData;
import org.apache.commons.lang.StringUtils;
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

    String menuUrl = "exception/exceptionList.do"; //菜单地址(权限用)
    @Resource(name="exceptionService")
    private ExceptionService exceptionService;
    //   未处理异常
    @RequestMapping("/exceptionInfo")
    public ModelAndView getExceptionInfo(Page page) {
        logBefore(logger, "异常列表");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String  enquiry = pd.getString("enquiry");
            if(null != enquiry && !"".equals(enquiry)){
                pd.put("enquiry", enquiry.trim());
            }else {
                pd.put("enquiry", "");
            }
            String sendTimeStart = pd.getString("reportTimeStart");
            String sendTimeEnd = pd.getString("reportTimeEnd");

            if(sendTimeStart != null && !"".equals(sendTimeStart)){
                sendTimeStart = sendTimeStart+" 00:00:00";
                pd.put("reportTimeStart", sendTimeStart);
            }
            if(sendTimeEnd != null && !"".equals(sendTimeEnd)){
                sendTimeEnd = sendTimeEnd+" 00:00:00";
                pd.put("reportTimeEnd", sendTimeEnd);
            }
            String  level = pd.getString("level");
            pd.put("level", level);
            String loginUserName = FactoryUtil.getLoginUserName();
            if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
                pd.put("factory_id",FactoryUtil.getFactoryId());
            }
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
//    所有异常
    @RequestMapping("/exceptionList")
    public ModelAndView getExceptionList(Page page) {
        logBefore(logger, "异常列表");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String  enquiry = pd.getString("enquiry");
            if(null != enquiry && !"".equals(enquiry)){
                pd.put("enquiry", enquiry.trim());
            }else {
                pd.put("enquiry", "");
            }
            String sendTimeStart = pd.getString("reportTimeStart");
            String sendTimeEnd = pd.getString("reportTimeEnd");

            if(sendTimeStart != null && !"".equals(sendTimeStart)){
                sendTimeStart = sendTimeStart+" 00:00:00";
                pd.put("reportTimeStart", sendTimeStart);
            }
            if(sendTimeEnd != null && !"".equals(sendTimeEnd)){
                sendTimeEnd = sendTimeEnd+" 00:00:00";
                pd.put("reportTimeEnd", sendTimeEnd);
            }
            String  level = pd.getString("level");
            pd.put("level", level);
            String loginUserName = FactoryUtil.getLoginUserName();
            if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
                pd.put("factory_id",FactoryUtil.getFactoryId());
            }
            page.setPd(pd);
            List<PageData> exceptionList = exceptionService.listAllException(page);
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
            mv.setViewName("exception/exceptionDetail");
            mv.addObject("pd", pd);
            mv.addObject("result",result);
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
