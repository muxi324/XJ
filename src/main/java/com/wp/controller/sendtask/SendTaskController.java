package com.wp.controller.sendtask;

import com.wp.controller.base.BaseController;
import com.wp.entity.worker.Worker;
import com.wp.service.databank.TeamService;
import com.wp.service.exception.ExceptionService;
import com.wp.service.sendtask.SendTaskService;
import com.wp.service.system.user.UserService;
import com.wp.service.taskmag.TaskMagService;
import com.wp.service.taskmag.TaskSetService;
import com.wp.service.worker.WorkerService;
import com.wp.util.*;
import com.wp.util.mail.SimpleMailSender;
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
import java.util.*;

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
    public ModelAndView goSendTask1() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData result = exceptionService.findById(pd);
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
        mv.setViewName("sendtask/sendtask1");
        mv.addObject("pd", result);
        return mv;
    }
    /**
     * 去发送维修任务
     */
    @RequestMapping(value="/goSendTask2")
    public ModelAndView goSendTask2() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData result = exceptionService.findById(pd);

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
        mv.setViewName("sendtask/sendtask2");
        mv.addObject("pd", result);
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
    public String sendPeriodTask() throws Exception{
       // if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("send_time",  Tools.date2Str(new Date()));	//添加时间
        pd.put("mission_condition", 1);
        pd.put("set_name",getUserName());
        if (StringUtils.isEmpty(pd.getString("time_dev"))) {
            pd.put("time_dev","0");
        }
        String workerId = pd.getString("worker_id");   //通过worker_id获取NMAE
        PageData worker = new PageData();
        worker.put("USER_ID", workerId);
        PageData user = userService.findByUiId(worker);
        String userName = user.getString("NAME");
        pd.put("worker_name",userName);
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
            QuartzManager.addJob("xunjian_period"+pd.getString("send_time"),"xunjian_period","xunjian_period","xunjian_period"+pd.getString("send_time"),
                                SendPeriodTaskJob.class,cron,pd);
        } else if (StringUtils.isNotEmpty(pd.getString("period_start_time")) && StringUtils.isNotEmpty(pd.getString("period_end_time"))) {
            //定时任务
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date startTime = sdf.parse(pd.getString("period_start_time"));
            Date endTime = sdf.parse(pd.getString("period_end_time"));
            QuartzManager.addStartAndEndJob("xujian_start"+pd.getString("send_time"),"xujian_start","xujian_start"+pd.getString("send_time"),"xujian_start",
                    SendPeriodTaskJob.class,startTime,endTime,pd);
        }
        sendTaskService.save(pd);
        String result = "下发任务成功！";
        return result;
    }

    /**
     * 发送临时任务
     */
    @RequestMapping(value="/sendTask")
    @ResponseBody
    public String save() throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("send_time",  Tools.date2Str(new Date()));	//添加时间
        pd.put("mission_condition", 1);
        pd.put("set_name",getUserName());
        if (StringUtils.isEmpty(pd.getString("time_dev"))) {
            pd.put("time_dev","0");
        }
        String workerId = pd.getString("worker_id");   //通过worker_id获取NMAE
        PageData worker = new PageData();
        worker.put("USER_ID", workerId);
        PageData user = userService.findByUiId(worker);
        String userName = user.getString("NAME");
        pd.put("worker_name",userName);
        String teamId = pd.getString("team_id");   //通过team_id获取team
        PageData team = new PageData();
        team.put("id", teamId);
        PageData data = teamService.findById(team);
        String teamName = data.getString("team");
        pd.put("team",teamName);
        pd.put("factory_id",FactoryUtil.getFactoryId());
        pd.put("workshop_id",FactoryUtil.getWorkshopId());
        pd.put("set_name",FactoryUtil.getLoginName());
        if(StringUtils.isNotEmpty(pd.getString("id"))){   //异常处理任务
            String missionType=pd.getString("mission_type");
            if(missionType.equals("维修任务") || missionType.equals("临时巡检任务") ){
                String exceptionId =pd.getString("id");
                pd.put("id",exceptionId);
                pd.put("state",2);
                exceptionService.editState(pd);  //修改异常状态
            }
        }
        /*if(pd.getString("mission_condition").equals(2)){   //处理拒单重发任务修改拒单状态
                taskMagService.refuse(pd);
            }*/

        sendTaskService.save(pd);
        String phonenumber = pd.getString("worker_phone");   //发送短信提醒
        String Content = "您有一条新任务，请注意查收。";
        SendMessage.sendMessage(phonenumber, Content);
        String result = "下发任务成功！";
        return result;
    }



    //选择员工
    @RequestMapping(value="/groupchoose")
    public void goGroup(HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println("跳转进来了");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String team_id = pd.getString("team_id");
        pd.put("team_id", team_id);
        String loginUserName = FactoryUtil.getLoginUserName();
        if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
            pd.put("factory_id",FactoryUtil.getFactoryId());
            pd.put("workshop_id",FactoryUtil.getWorkshopId());
        }
        List<PageData> list = userService.listWorkerByTeam(pd);
        System.out.println("======"+ JSONArray.fromObject(list)+"====");
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
        System.out.println("跳转进来了");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String workerId = pd.getString("workerdata");
        pd.put("USER_ID", workerId);
        PageData user = userService.findByUiId(pd);
        System.out.println("======"+ JSONArray.fromObject(user)+"====");
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

