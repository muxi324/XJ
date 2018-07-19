package com.wp.controller.querytask;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.service.event.EventService;
import com.wp.service.querytask.QueryTaskService;
import com.wp.service.system.role.RoleService;
import com.wp.service.taskmag.TaskSetService;
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

/**
 * Created by wp on 2017/12/26.
 */
@Controller
@RequestMapping(value="/querytask")
public class QueryTaskController extends BaseController {
        String menuUrl = "querytask/list.do"; //菜单地址(权限用)
        @Resource(name="queryTaskService")
        private QueryTaskService queryTaskService;
        @Resource(name="roleService")
        private RoleService roleService;
        @Resource(name="taskSetService")
        private TaskSetService taskSetService;
        @Resource(name = "eventService")
        private EventService eventService;

        /**
         * 详情
         */
        @RequestMapping(value="/goDetail")
        public ModelAndView goDetail(){
            logBefore(logger, "去任务详情页面");
            ModelAndView mv = this.getModelAndView();
            PageData pd = this.getPageData();
            String missionId = pd.getString("mission_id");
            List<PageData> eventList = null;
            try {
                pd = queryTaskService.findById(pd);	//根据ID读取
                PageData setMissionData = taskSetService.findById(pd);
                String eventIds = setMissionData.getString("event");
                String[] idArr = new String[0];
                if (eventIds != null && !eventIds.equals("")) {
                    idArr = eventIds.split(",");
                }
                List<String> idList = new ArrayList<String>();
                for (int i=0; i<idArr.length; i++) {
                    idList.add(idArr[i]);
                }
                eventList = eventService.listByIds(idList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mv.addObject("pd",pd);
            mv.addObject("varList",eventList);
            mv.addObject("id",missionId);
            mv.setViewName("querytask/task_detail");
            mv.addObject("msg", "detail");
            mv.addObject("pd", pd);
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
                String loginUserName = FactoryUtil.getLoginUserName();
                if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
                    pd.put("factory_id",FactoryUtil.getFactoryId());
                }
                page.setPd(pd);
                List<PageData> varList = queryTaskService.list(page);	//列出${objectName}列表
                mv.setViewName("querytask/querytask");
                mv.addObject("varList", varList);
                mv.addObject("pd", pd);
                mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
            } catch(Exception e){
                logger.error(e.toString(), e);
            }
            return mv;
        }

        /*
         * 导出到excel
         * @return
         */
        @RequestMapping(value="/excel")
        public ModelAndView exportExcel(){
            logBefore(logger, "导出任务列表到excel");
            if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {return null;}
            ModelAndView mv = this.getModelAndView();
            PageData pd = new PageData();
            pd = this.getPageData();
            try{

                    //检索条件===
                    String enquiry = pd.getString("enquiry");
                    if (null != enquiry && !"".equals(enquiry)) {
                        pd.put("enquiry", enquiry.trim());
                    }
                    String sendTimeStart = pd.getString("sendTimeStart");
                    String sendTimeEnd = pd.getString("sendTimeEnd");
                    if (sendTimeStart != null && !"".equals(sendTimeStart)) {
                        sendTimeStart = sendTimeStart + " 00:00:00";
                        pd.put("sendTimeStart", sendTimeStart);
                    }
                    if (sendTimeEnd != null && !"".equals(sendTimeEnd)) {
                        sendTimeEnd = sendTimeEnd + " 00:00:00";
                        pd.put("sendTimeEnd", sendTimeEnd);
                    }
                    String mission_condition = pd.getString("status");
                    pd.put("mission_condition", mission_condition);

                    //检索条件===

                    Map<String, Object> dataMap = new HashMap<String, Object>();
                    List<String> titles = new ArrayList<String>();

                    titles.add("任务联单号");
                    titles.add("安装员工");
                    titles.add("任务下达时间");
                    titles.add("房源地址");
                    titles.add("房源联系人");
                    titles.add("联系人电话");
                   // titles.add("任务状态");
                    dataMap.put("titles", titles);
                    List<PageData> varOList = queryTaskService.listAll(pd);
                    List<PageData> varList = new ArrayList<PageData>();
                    for (int i = 0; i < varOList.size(); i++) {
                        PageData vpd = new PageData();
                        vpd.put("var1", varOList.get(i).getString("flow_number"));
                        vpd.put("var2", varOList.get(i).getString("worker_name"));
                        vpd.put("var3", varOList.get(i).getString("send_time"));
                        vpd.put("var4", varOList.get(i).getString("house_address"));
                        vpd.put("var5", varOList.get(i).getString("house_owner_name"));
                        vpd.put("var6", varOList.get(i).getString("house_owner_phone"));
                       // vpd.put("var7", varOList.get(i).getString("mission_condition"));
                        varList.add(vpd);
                    }
                    dataMap.put("varList", varList);
                    ObjectExcelView erv = new ObjectExcelView();
                    mv = new ModelAndView(erv, dataMap);
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
