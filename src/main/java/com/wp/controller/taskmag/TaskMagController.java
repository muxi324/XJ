package com.wp.controller.taskmag;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.entity.map.MapPoint;
import com.wp.service.event.EventService;
import com.wp.service.house.HouseService;
import com.wp.service.querytask.QueryTaskService;
import com.wp.service.system.role.RoleService;
import com.wp.service.taskmag.TaskMagService;
import com.wp.service.taskmag.TaskSetService;
import com.wp.util.*;
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
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wp on 2017/12/22.
 */
@Controller
@RequestMapping(value="/taskmag")
public class TaskMagController extends BaseController {
    String menuUrl = "taskmag/list.do"; //菜单地址(权限用)
    @Resource(name="taskMagService")
    private TaskMagService taskMagService;
    @Resource(name="roleService")
    private RoleService roleService;
    @Resource(name="HouseService")
    private HouseService houseService;
    @Resource(name="taskSetService")
    private TaskSetService taskSetService;
    @Resource(name = "eventService")
    private EventService eventService;

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
            List<PageData> varList = taskMagService.list(page);	//列出${objectName}列表
            mv.setViewName("taskmag/task_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去审核页面
     */
    @RequestMapping(value="/goCheck")
    public ModelAndView goCheck(){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = taskMagService.findById(pd);								//根据ID读取
            String status = pd.getString("mission_condition");
            //System.out.println(status+"=====");
            if(status.indexOf("1") != -1) {
                mv.setViewName("taskmag/refuse_task1");  // 判断当mission_condition=1时返回refuse_task1；
            }else{
                mv.setViewName("taskmag/finish_task1");                                          // mission_condition=4时返回finish_task1；
            }
            mv.addObject("msg", "check");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 审核已完成
     */
    @RequestMapping(value="/check")
    public ModelAndView check() throws Exception{
        logBefore(logger, "保存审核信息");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("auditor_time",  Tools.date2Str(new Date()));	//审核时间

        taskMagService.check(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }
    /**
     * 处理拒单
     */
    @RequestMapping(value="/refuse")
    public ModelAndView refuse() throws Exception{
        logBefore(logger, "处理拒单信息");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("auditor_time",  Tools.date2Str(new Date()));	//审核时间
        pd.put("mission_condition",7);
        taskMagService.refuse(pd);
        pd = houseService.findById(pd);
        mv.setViewName("house/sendtask");
        mv.addObject("pd",pd);  //把pd灌输到前端pd
        return mv;
    }

    /*
	 * 导出到excel
	 * @return
	 */
    @RequestMapping(value="/excel")
    public ModelAndView exportExcel(){
        logBefore(logger, "导出任务列表到excel");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try{
            Map<String,Object> dataMap = new HashMap<String,Object>();
            List<String> titles = new ArrayList<String>();

            titles.add("任务联单号");
            titles.add("安装员工");
            titles.add("任务下达时间");
            titles.add("房源地址");
            titles.add("房源联系人");
            titles.add("联系人电话");
           // titles.add("任务状态");
            dataMap.put("titles", titles);
            List<PageData> varOList = taskMagService.listAll(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for(int i=0;i<varOList.size();i++){
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
            mv = new ModelAndView(erv,dataMap);
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping("/getTaskLine")
    public ModelAndView getDetailPath() {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        String taskId = pd.getString("mission_id");
        //临时验证，以后连数据库取数
        List<MapPoint> pointList = new ArrayList<MapPoint>();
        if (null != taskId) {
            MapPoint p1 = new MapPoint("116.3479614258","40.0270886473");
            MapPoint p2 = new MapPoint("116.3585186005","40.0170321295");
            MapPoint p3 = new MapPoint("116.3653850555","40.0078287883");
            MapPoint p4 = new MapPoint("116.3742256165","39.9890238203");
            MapPoint p5 = new MapPoint("116.3532829285","39.9859987616");
            MapPoint p6 = new MapPoint("116.3556861877","39.9515957403");
            pointList.add(p1);
            pointList.add(p2);
            pointList.add(p3);
            pointList.add(p4);
            pointList.add(p5);
            pointList.add(p6);
        }
        mv.setViewName("elecmap/detailPath");
        mv.addObject("pointList",pointList);
        mv.addObject("pd", pd);
        mv.addObject(Const.SESSION_QX,this.getHC());
        return mv;
    }

    @RequestMapping("/getTaskPhoto")
    public ModelAndView getTaskPhoto() {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        String taskId = pd.getString("taskId");
        mv.setViewName("taskmag/taskPhoto");
        return mv;
    }

    @RequestMapping("/goAuditTask")
    public ModelAndView auditTask() {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        String missionId = pd.getString("mission_id");
        List<PageData> eventList = null;
        try {
            pd = taskMagService.findById(pd);
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
        mv.addObject("missionId",missionId);
        mv.setViewName("taskmag/auditTask");
        mv.addObject(Const.SESSION_QX,this.getHC());
        return mv;
    }

    @RequestMapping("/getWorkContentDetail")
    public ModelAndView getWorkContentDetail() {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        List<PageData> list = null;
        try {
            list = taskMagService.getWorkContent(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.addObject("varList",list);
        mv.setViewName("taskmag/workcontentdetail");
        return mv;
    }

    @RequestMapping("/auditMisson.do")
    public ModelAndView auditMisson(PrintWriter out){
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        out.write("success");
        mv.setViewName("taskmag/auditResult");
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
