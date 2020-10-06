package com.wp.controller.exception;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.service.exception.ExceptionService;
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
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
        //System.out.println("Const.SESSION_QX:-------"+Const.SESSION_QX);
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
                pd.put("workshop_id",FactoryUtil.getWorkshopId());
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

    /**
     *  异常查询--展示异常信息
     * @param page  exceptionList
     * @return
     */
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
                pd.put("workshop_id",FactoryUtil.getWorkshopId());
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
    /*
     *异常详情
     */
    @RequestMapping("/getExceptionDetail")
    public ModelAndView getExceptionDetail(Page page) {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String roleName = FactoryUtil.getRoleId();
            pd.put("ROLENAME",roleName);
            PageData result = exceptionService.findById(pd);//得到异常信息，${result.id}是exception表id
            mv.setViewName("exception/exceptionDetail");
            mv.addObject("pd", pd);
            mv.addObject("result",result);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

       /*
     * 导出到excel
     * @return
     */
    @RequestMapping(value="/excel")
    public ModelAndView exportExcel(){
        logBefore(logger, "导出异常列表到excel");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try{
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
                pd.put("workshop_id",FactoryUtil.getWorkshopId());
                pd.put("team_id",FactoryUtil.getTeamId());
            }

            //检索条件===
            Map<String,Object> dataMap = new HashMap<String,Object>();
            //文件名
            String filename = "异常表-"+new SimpleDateFormat("yyyyMMddhhmm").format(new Date())+".xls";
            dataMap.put("filename",filename);
            //标题
            String headerName = "异常表";
            dataMap.put("headerName",headerName);

            //文件列表头
            List<String> titles = new ArrayList<String>();
            titles.add("所属车间");
            titles.add("异常级别");
            titles.add("描述");
            titles.add("异常上报人");
            titles.add("上报时间");
            titles.add("异常状态");
            dataMap.put("titles", titles);
            List<PageData> varOList = exceptionService.listAll(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for(int i=0;i<varOList.size();i++){
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("workshop"));
                vpd.put("var2", varOList.get(i).getString("level"));
                vpd.put("var3", varOList.get(i).getString("description"));
                vpd.put("var4", varOList.get(i).getString("report_worker"));
                vpd.put("var5", varOList.get(i).getString("report_time"));
                if(varOList.get(i).getString("status").equals(1)){
                    vpd.put("var6", "未处理");
                }else {
                    vpd.put("var6", "已处理");
                }
                varList.add(vpd);
            }
            dataMap.put("varList", varList);
            ObjectExcelView erv = new ObjectExcelView();
            mv = new ModelAndView(erv,dataMap);
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
    public void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }
}
