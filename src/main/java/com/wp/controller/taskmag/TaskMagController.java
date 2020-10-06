package com.wp.controller.taskmag;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.entity.map.MapPoint;
import com.wp.service.databank.TeamService;
import com.wp.service.event.EventService;
import com.wp.service.exception.ExceptionService;
import com.wp.service.house.HouseService;
import com.wp.service.system.role.RoleService;
import com.wp.service.taskmag.TaskMagService;
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
    @Resource(name="teamService")
    private TeamService teamService;
    @Resource(name="exceptionService")
    private ExceptionService exceptionService;

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
                pd.put("workshop_id",FactoryUtil.getWorkshopId());
            }
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
    /*@RequestMapping(value="/goCheck")
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
    }*/

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
        pd.put("mission_condition",8);
        String mission_id= pd.getString("missionId");
        pd.put("mission_id",mission_id);
        taskMagService.refuse(pd);
        pd  = taskMagService.findById(pd);
        String missionType= pd.getString("mission_type");
        String factory_id = FactoryUtil.getFactoryId();
        String workshop_id = FactoryUtil.getWorkshopId();
        if(StringUtils.isNotEmpty(factory_id)) {
            PageData data = new PageData();
            data.put("factory_id",factory_id);
            List<PageData> teamList = new ArrayList<PageData>();
            if(StringUtils.isNotEmpty(workshop_id)){
                data.put("workshop_id", workshop_id);
            }
            teamList = teamService.findTeamByW(data);
            mv.addObject("teamList",teamList);
        }

        String set_id= pd.getString("set_id");
        String mission_name= pd.getString("mission_name");
        String event = pd.getString("event");
        pd.put("set_id",set_id);
        pd.put("mission_name",mission_name);
        pd.put("event",event);
        mv.addObject("pd",pd);  //把pd灌输到前端pd
        if(missionType.equals("日常巡检任务")){
           mv.setViewName("sendtask/sendtask");
        }else if(missionType.equals("维修任务")){
            mv.setViewName("sendtask/sendtask2");
        }else if(missionType.equals("临时巡检任务")){
            mv.setViewName("sendtask/sendtask1");
        }
        return mv;
    }

    /**
     * 处理审核未通过
     */
    @RequestMapping(value="/noPass")
    public ModelAndView noPass() throws Exception{
        logBefore(logger, "处理审核未通过信息");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String mission_id= pd.getString("missionId");
        pd.put("mission_id",mission_id);
        pd = taskMagService.findById(pd);
        String event = pd.getString("event");
        pd.put("event",event);
      //  pd.put("startTime",Tools.date2Str(new Date()));
        String missionType= pd.getString("mission_type");
        String factory_id = FactoryUtil.getFactoryId();
        String workshop_id = FactoryUtil.getWorkshopId();
        if(StringUtils.isNotEmpty(factory_id)) {
            PageData data = new PageData();
            data.put("factory_id",factory_id);
            List<PageData> teamList = new ArrayList<PageData>();
            if(StringUtils.isNotEmpty(workshop_id)){
                data.put("workshop_id", workshop_id);
            }
            teamList = teamService.findTeamByW(data);
            mv.addObject("teamList",teamList);
        }
        if(missionType.equals("日常巡检任务")){
            mv.setViewName("sendtask/sendtask");
        }else if(missionType.equals("维修任务")){
            mv.setViewName("sendtask/sendtask2");
        }else if(missionType.equals("临时巡检任务")){
            mv.setViewName("sendtask/sendtask1");
        }
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
        String work_name = pd.getString("work_name");
        try {
            pd = taskMagService.findPicById(pd);	//根据ID读取
            mv.addObject("pd", pd);
            mv.setViewName("taskmag/taskPhoto");
            } catch (Exception e) {
                logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     *  去审核页面
     * @return
     */
    @RequestMapping("/goAuditTask")     //去审核页面
    public ModelAndView auditTask() {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();

        String missionId = pd.getString("mission_id");
        List<PageData> eventList = null;
        try {
            pd = taskMagService.findById(pd);
            String  missionType = pd.getString("mission_type");

            if(missionType.equals("日常巡检任务") || missionType.equals("临时巡检任务") ){
                PageData setMissionData = taskSetService.findById(pd);    //任务数据

                if (setMissionData == null) {
                    mv.addObject("errorMsg","此任务数据不存在");
                    mv.setViewName("taskmag/auditTask");
                    return mv;
                }

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
               // System.out. println("eventList---------------"+eventList);
                String status = pd.getString("mission_condition");
                //System.out.println(status+"=====");
                if(status.indexOf("2") != -1) {
                    mv.setViewName("taskmag/refuse_task1");  // 判断当mission_condition=2时返回refuse_task1；
                }else{
                    mv.setViewName("taskmag/auditTask");                                          // mission_condition=4时返回finish_task1；
                }
                mv.addObject("varList",eventList);
            }else if(missionType.equals("维修任务")){
                String  exceptionId = pd.getString("exception_id");
                PageData data = new PageData();
                data.put("exceptionId",exceptionId);
                PageData exceptionDetail = exceptionService.findById(data);
                PageData content = taskMagService.getWorkContentById(pd);   //得到维修后的照片

               // System.out.println("--------------------"+pd);
                //println("--------------------"+content);

                mv.setViewName("taskmag/auditRepairTask");
                mv.addObject("exp",exceptionDetail);
                mv.addObject("con",content);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.addObject("pd",pd);

        mv.addObject("missionId",missionId);
      //  mv.setViewName("taskmag/auditTask");
        mv.addObject(Const.SESSION_QX,this.getHC());
        mv.addObject("NAME",getName());
        return mv;
    }


    //查看任务详情
    @RequestMapping("/getWorkContentDetail")
    public ModelAndView getWorkContentDetail() {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        List<PageData> list = null;
        String mission_id = pd.getString("mission_id");
        String event_id = pd.getString("event_id");
        try {
            list = taskMagService.getWorkContent(pd);     // 事件反馈的详情

          //  System.out.println( "---------"+list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.addObject("varList",list);          // 事件反馈详情
        mv.addObject("mission_id",mission_id);
        mv.addObject("event_id",event_id);
        mv.setViewName("taskmag/workcontentdetail");
        return mv;
    }

//    //下载文件
//    @RequestMapping(value="/fileDownload",method=RequestMethod.GET)
//    public void download(@RequestParam(value="filename")String filename,
//                         HttpServletResponse response) throws Exception {
//        String filepath = "F:\\img\\upload\\";
//        FileDownload.fileDownload(response,filepath,filename);
//    }

    /**
     *  通过审核
     * @param out
     * @return
     * @throws Exception
     */
    @RequestMapping("/auditMisson.do")    //通过审核,修改状态等
    public ModelAndView auditMisson(PrintWriter out) throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        pd.put("auditor_time",  Tools.date2Str(new Date()));	//审核时间
        taskMagService.auditTask(pd);
       /* mv.setViewName("taskmag/task_list");
        Page page = new Page();
        page.setPd(new PageData());
        List<PageData> varList = taskMagService.list(page);	//列出${objectName}列表
        mv.addObject("varList", varList);
        mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        return mv;*/
        mv.setViewName("save_result");
        return mv;
    }

    /* ===============================权限================================== */
    public Map<String, String> getHC(){
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
    }

    public String getName() {
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (String) session.getAttribute(Const.SESSION_NAME);
    }

    public String getUserName() {
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (String) session.getAttribute(Const.SESSION_USERNAME);
    }
	/* ===============================权限================================== */

    @InitBinder
    public void initBinder(WebDataBinder binder){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
    }
}