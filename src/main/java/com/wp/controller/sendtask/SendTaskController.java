package com.wp.controller.sendtask;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.service.databank.TeamService;
import com.wp.service.databank.WorkshopService;
import com.wp.service.event.EventService;
import com.wp.service.exception.ExceptionService;
import com.wp.service.sendtask.SendTaskService;
import com.wp.service.system.user.UserService;
import com.wp.service.taskmag.TaskSetService;
import com.wp.util.*;
import com.wp.util.quartz.QuartzManager;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wp on 2017/12/17.
 */
@Controller
@RequestMapping(value="/sendtask")
public class SendTaskController extends BaseController {
    String menuUrl = "sendtask/sendTask.do"; //菜单地址(权限用)
    @Resource(name = "sendTaskService")
    private SendTaskService sendTaskService;
    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "exceptionService")
    private ExceptionService exceptionService;
    @Resource(name="taskSetService")
    private TaskSetService taskSetService;
    @Resource(name="teamService")
    private TeamService teamService;
    @Resource(name = "eventService")
    private EventService eventService;
    @Resource(name="workshopService")
    private WorkshopService workshopService;

    /**
     * 去发送日常巡检任务
     */
    @RequestMapping(value="/goSendTask")
    public ModelAndView goSendTask() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = taskSetService.findById(pd);//根据ID读取
            String  mission_name = pd.getString("mission");
            pd.put("mission_name", mission_name);
            String  setTime = pd.getString("set_time");
            pd.put("set_time", setTime);
            mv.addObject("pd", pd);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.setViewName("sendtask/sendtask");
        return mv;
    }
    /**
     * 去发送临时巡检任务
     */
    @RequestMapping(value="/goSendTask1")
    public ModelAndView goSendTask1(Page page) throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData result = exceptionService.findById(pd);
        String factory_id = FactoryUtil.getFactoryId();
        String workshop_id = FactoryUtil.getWorkshopId();
        List<PageData> workshopList = null;
        //List<PageData> teamList = new ArrayList<PageData>();
        if(StringUtils.isNotEmpty(factory_id)) {
            PageData data = new PageData();
            data.put("factory_id",factory_id);
            page.setPd(data);
            if(StringUtils.isNotEmpty(workshop_id)){
                data.put("workshop_id", workshop_id);
                page.setPd(data);
                workshopList = workshopService.listWorkshopAll(page);
            }else{
                page.setPd(data);
                workshopList = workshopService.listWorkshopAll(page);
            }
            //List<PageData> teamList = new ArrayList<PageData>();
            //teamList = teamService.findTeamByW(data);

        }
        //mv.addObject("teamList",teamList);
      // System.out.println("workshopList:"+workshopList);
        mv.addObject("workshopList", workshopList);
        mv.setViewName("sendtask/sendtask1");
        mv.addObject("pd", result);
        return mv;
    }
    /**
     * 去发送维修任务
     *
     */
    @RequestMapping(value="/goSendTask2")
    public ModelAndView goSendTask2(Page page) throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
    //    System.out.println("iii--------------"+pd);
        PageData result = exceptionService.findById(pd); //通过异常id查询

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
            page.setPd(data);
            List<PageData> varList = eventService.list1(page);

            mv.addObject("teamList",teamList);
            mv.addObject("varList",varList);
        }
        mv.setViewName("sendtask/sendtask2");
        mv.addObject("pd", result);
        return mv;
    }
    /**
     * 忽略异常，不用下发任务
     *
     */
    @RequestMapping(value="/goSendTask3")
    public ModelAndView goSendTask3(Page page) throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
    //    System.out.println("iii--------------"+pd);

        if(StringUtils.isNotEmpty(pd.getString("exceptionId"))){   //异常处理任务
                String exceptionId =pd.getString("exceptionId");
                pd.put("id",exceptionId);
                pd.put("state",2);
                exceptionService.editState(pd);  //修改异常状态
        }
        //System.out.println("--------------"+pd);
        mv.setViewName("sendtask/sendtask3");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 去选择物资页面
     */
    @RequestMapping(value="/goselectMaterial")
    public ModelAndView goAdd(){
        logBefore(logger, "去选择物资页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("sendtask/select_material");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 发送周期任务
     */
    @RequestMapping(value="/sendPeriodTask")
    @ResponseBody
    public ModelAndView sendPeriodTask() throws Exception{
       // if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
       // pd.put("send_time",  Tools.date2Str(new Date()));
        pd.put("mission_condition", 1);
        if (StringUtils.isEmpty(pd.getString("time_dev"))) {
            pd.put("time_dev","0");
        }
        String workerId = pd.getString("worker_id");   //通过worker_id获取NMAE
        PageData worker = new PageData();
        worker.put("USER_ID", workerId);
        PageData user = userService.findByUiId(worker);
        String userName = user.getString("NAME");
        pd.put("worker_name",userName);
        String worker_phone =user.getString("PHONE");
        pd.put("worker_phone",worker_phone);
        String teamId = pd.getString("team_id");   //通过team_id获取team
        PageData team = new PageData();
        team.put("id", teamId);
        PageData data = teamService.findById(team);
        String teamName = data.getString("team");
        pd.put("team",teamName);
        pd.put("factory_id",FactoryUtil.getFactoryId());
        pd.put("workshop_id",FactoryUtil.getWorkshopId());
        pd.put("set_name",FactoryUtil.getLoginName());

        /*if(pd.getString("mission_condition").equals(2)){   //处理拒单重发任务修改拒单状态
                taskMagService.refuse(pd);
            }*/

        //周期任务
        if (StringUtils.isNotEmpty(pd.getString("cron"))) {
            String cron = pd.getString("cron");
            QuartzManager.addJob("xunjian_period"+pd.getString("mission_name")+pd.getString("setTime"),"xunjian_period","xunjian_period","xunjian_period"+pd.getString("mission_name")+pd.getString("setTime"),
                                SendPeriodTaskJob.class,cron,pd);
        } else if (StringUtils.isNotEmpty(pd.getString("period_start_time")) && StringUtils.isNotEmpty(pd.getString("period_end_time"))) {
            //定时任务
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date startTime = sdf.parse(pd.getString("period_start_time"));
            Date endTime = sdf.parse(pd.getString("period_end_time"));

            //开始任务
            QuartzManager.addStartAndEndJob("xujian_start"+pd.getString("mission_name")+pd.getString("setTime"),
                    "xujian_start","xujian_start"+pd.getString("mission_name")+pd.getString("setTime"),
                    "xujian_start",
                    SendPeriodTaskJob.class,startTime,endTime,pd);
        }
        pd.put("status",2);
        taskSetService.editStatus(pd);
        mv.setViewName("save_result");
        return mv;
    }

    /**
     *  下发异常处理后特殊任务--维修任务和临时巡检任务
     */
    @RequestMapping(value="/sendTask")
    @ResponseBody
    public ModelAndView save() throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("send_time",  Tools.date2Str(new Date()));	//添加时间
        pd.put("mission_condition", 1);
        if (StringUtils.isEmpty(pd.getString("time_dev"))) {
            pd.put("time_dev","0");
        }

        String workerId = pd.getString("worker_id");   //通过worker_id获取NMAE和phone
        PageData worker = new PageData();
        worker.put("USER_ID", workerId);
        PageData user = userService.findByUiId(worker);
        String userName = user.getString("NAME");
        pd.put("worker_name",userName);
        String worker_phone =user.getString("PHONE");
        pd.put("worker_phone",worker_phone);
        String teamId = pd.getString("team_id");   //通过team_id获取team
        PageData team = new PageData();
        team.put("id", teamId);
        PageData data = teamService.findById(team);
        String teamName = data.getString("team");
        pd.put("team",teamName);
        pd.put("factory_id",FactoryUtil.getFactoryId());
        pd.put("workshop_id",FactoryUtil.getWorkshopId());
        pd.put("set_name",FactoryUtil.getLoginName());
        if(StringUtils.isNotEmpty(pd.getString("exceptionId"))){   //异常处理任务
            String missionType=pd.getString("mission_type");
            if(missionType.equals("维修任务") || missionType.equals("临时巡检任务") ){
                String exceptionId =pd.getString("exceptionId");
                pd.put("id",exceptionId);
                pd.put("state",2);
                exceptionService.editState(pd);  //修改异常状态
            }
        }
        /*if(pd.getString("mission_condition").equals(2)){   //处理拒单重发任务修改拒单状态
                taskMagService.refuse(pd);
            }*/

        //制定的临时下发任务保存到set_mission
        pd.put("set_time",Tools.date2Str(new Date()));//set_mission表添加时间
        pd.put("status",1);//set_mission表中status状态为1

        taskSetService.save1(pd);
       // System.out.println(pd);

        PageData data1 = taskSetService.findById1(pd);
        String set_id = data1.getString("set_id");
        pd.put("set_id",set_id);

        sendTaskService.save1(pd);//保存到mission表
        String phonenumber = pd.getString("worker_phone");   //发送短信提醒
        String Content = "您有一条新任务，请注意查收。";
        SendMessage.sendMessage(phonenumber, Content);
        mv.setViewName("save_result");
        return mv;
    }

    //    暂停周期任务
    @RequestMapping(value="/stopTask")
    public void stopTask(PrintWriter out){
        logBefore(logger, "暂停周期任务");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            pd = taskSetService.findById(pd);//根据ID读取
            QuartzManager.removeJob("xunjian_period"+pd.getString("mission")+pd.getString("set_time"),"xunjian_period","xunjian_period","xunjian_period"+pd.getString("mission")+pd.getString("set_time"));
            pd.put("status",3);
            taskSetService.editStatus(pd);
            out.write("success");
            out.close();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
    }
    //选择班组
    @RequestMapping(value="/chooseTeam")
    public void chooseTeam(HttpServletRequest request, HttpServletResponse response) throws Exception{
       // System.out.println("跳转进来了");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String workshop_id = pd.getString("workshop_id");
        pd.put("workshop_id", workshop_id);
        String loginUserName = FactoryUtil.getLoginUserName();
        if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
            pd.put("factory_id",FactoryUtil.getFactoryId());
        }
        List<PageData> teamList = new ArrayList<PageData>();
        teamList = teamService.findTeamByW(pd);
     //   System.out.println("======"+ JSONArray.fromObject(teamList)+"====");
        try {
            HttpHandler.send(response, teamList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //选择员工
    @RequestMapping(value="/groupchoose")
    public void goGroup(HttpServletRequest request, HttpServletResponse response) throws Exception{
      //  System.out.println("跳转进来了");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String team_id = pd.getString("team_id");
     //   System.out.println("team_id hh" + team_id);
        pd.put("team_id", team_id);
        String loginUserName = FactoryUtil.getLoginUserName();
   //     System.out.println(loginUserName);
        if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
            pd.put("factory_id",FactoryUtil.getFactoryId());
        }
    //    System.out.println("pd------"+pd);
        List<PageData> list = userService.listWorkerByTeam(pd);
      //  System.out.println("人员"+list.toString());
      //  System.out.println("======"+ JSONArray.fromObject(list)+"====");
        try {
            HttpHandler.send(response, list);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
//  手机
    @RequestMapping(value="/phonechoose")
    public void goPhone(HttpServletRequest request, HttpServletResponse response) throws Exception{
       // System.out.println("跳转进来了");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String workerId = pd.getString("workerdata");
        pd.put("USER_ID", workerId);
        PageData user = userService.findByUiId(pd);
       // System.out.println("======"+ JSONArray.fromObject(user)+"====");
        try {
            HttpHandler.send(response,user);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value="/uploade")
    @ResponseBody
    public String uploade(HttpServletRequest request,
                          @RequestParam(value = "file", required = false) MultipartFile file) {
        String  ffile = DateUtil.getDays(), fileName = "";
        if (null != file && !file.isEmpty()) {
            String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG+ ffile;		//文件上传路径
            fileName = FileUpload.fileUp(file, filePath, this.get32UUID());				//执行上传
        }
        return ffile + "/" + fileName;
    }

    //删除图片
    @RequestMapping(value="/deltp")
    public void deltp(PrintWriter out) {
        logBefore(logger, "删除图片");
        try{
            PageData pd = new PageData();
            pd = this.getPageData();
            String id = pd.getString("id");
            String exp_pic = pd.getString("exp_pic");													 		//图片路径
            DelAllFile.delFolder(PathUtil.getClasspath()+ Const.FILEPATHIMG + exp_pic); 	//删除图片
            pd.put("lock_pic", "");
            if(id != null){
                sendTaskService.editPic(pd);														//删除数据中图片数据
            }
            out.write("success");
            out.close();
        }catch(Exception e){
            logger.error(e.toString(), e);
        }
    }



    @InitBinder
    public void initBinder(WebDataBinder binder){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
    }


    /* ===============================权限================================== */
    public Map<String, String> getHC(){
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
    }

    public String getUserName() {
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (String) session.getAttribute(Const.SESSION_USERNAME);
    }
	/* ===============================权限================================== */
}

