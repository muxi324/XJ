package com.wp.controller.taskmag;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.wp.controller.base.BaseController;
import com.wp.entity.Page;

import com.wp.entity.worker.Worker;
import com.wp.service.event.EventService;
import com.wp.service.system.role.RoleService;
import com.wp.service.taskmag.TaskSetService;
import com.wp.service.worker.WorkerService;
import com.wp.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wp on 2018/3/19.
 */
@Controller
@RequestMapping(value="/taskset")
public class TaskSetController extends BaseController {
    String menuUrl = "taskset/list.do"; //菜单地址(权限用)
    @Resource(name="taskSetService")
    private TaskSetService taskSetService;
    @Resource(name="roleService")
    private RoleService roleService;
    @Resource(name = "eventService")
    private EventService eventService;
    @Resource(name = "workerService")
    private WorkerService workerService;

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
            String setTimeStart = pd.getString("setTimeStart");
            String setTimeEnd = pd.getString("setTimeEnd");

            if(setTimeStart != null && !"".equals(setTimeStart)){
                setTimeStart = setTimeStart+" 00:00:00";
                pd.put("setTimeStart", setTimeStart);
            }
            if(setTimeEnd != null && !"".equals(setTimeEnd)){
                setTimeEnd = setTimeEnd+" 00:00:00";
                pd.put("setTimeEnd", setTimeEnd);
            }
            String  mission_level = pd.getString("mission_level");
            pd.put("mission_level", mission_level);
            String  mission_type = pd.getString("mission_type");
            pd.put("mission_type", mission_type);
            page.setPd(pd);
            List<PageData> varList = taskSetService.list(page);	//列出${objectName}列表
            mv.setViewName("taskmag/task_set");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value="/goSend")
    public ModelAndView goSend() {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        try {
            pd = taskSetService.findById(pd);//根据ID读取
            mv.addObject("pd", pd);
            List<Worker> teamList = workerService.listTeam();//列出所有班组
            mv.addObject("teamList",teamList);
            mv.setViewName("sendtask/sendtask");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * 去新增任务页面
     */
    @RequestMapping(value="/goAdd")
    public ModelAndView goAdd(Page page){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> varList = eventService.list(page);
            mv.addObject("varList", varList);
            mv.setViewName("taskmag/task_edit");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }
    /**
     * 去修改任务页面
     */
    @RequestMapping(value="/goEdit")
    public ModelAndView goEdit(Page page){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = taskSetService.findById(pd);//根据ID读取
            String eventIds = pd.getString("event");
            String[] arr = new String[0];
            if (eventIds != null && !("".equals(eventIds))){
                arr = eventIds.split(",");
            }
            List<String> list = new ArrayList<String>();
            for (int i = 0; i<arr.length; i++) {
                list.add(arr[i]);
            }
            List<PageData> varList = eventService.list(page);
            mv.addObject("varList", varList);
            mv.addObject("eventIdList", list);
            mv.setViewName("taskmag/task_edit");
            mv.addObject("msg", "edit");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 保存任务
     */
    @RequestMapping(value="/save")
    public ModelAndView save(PrintWriter out) throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("set_time",  Tools.date2Str(new Date()));	//添加时间
        taskSetService.save(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }
    /**
     * 修改
     */
    @RequestMapping(value="/edit")
    public ModelAndView edit(PrintWriter out) throws Exception{
        logBefore(logger, "修改任务");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        taskSetService.edit(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }
    /**
     * 删除
     */
    @RequestMapping(value="/delete")
    public void delete(PrintWriter out){
        logBefore(logger, "删除任务");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            taskSetService.delete(pd);
            out.write("success");
            out.close();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
    }
    /**
     * 批量删除
     */
    @RequestMapping(value="/deleteAll")
    @ResponseBody
    public Object deleteAll() {
        PageData pd = new PageData();
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String set_ids = pd.getString("set_ids");

            if(null != set_ids && !"".equals(set_ids)){
                String Arrayhouse_ids[] = set_ids.split(",");
                if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){taskSetService.deleteAll(Arrayhouse_ids);}
                pd.put("msg", "ok");
            }else{
                pd.put("msg", "no");
            }

            pdList.add(pd);
            map.put("list", pdList);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
            logAfter(logger);
        }
        return AppUtil.returnObject(pd, map);
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
            //检索条件===
            String  enquiry = URLDecoder.decode(pd.getString("enquiry"),"UTF-8");
            if(null != enquiry && !"".equals(enquiry)){
                pd.put("enquiry", enquiry.trim());
            }else {
                pd.put("enquiry", "");
            }
            String setTimeStart = pd.getString("setTimeStart");
            String setTimeEnd = pd.getString("setTimeEnd");
            if(setTimeStart != null && !"".equals(setTimeStart)){
                setTimeStart = setTimeStart+" 00:00:00";
                pd.put("setTimeStart", setTimeStart);
            }
            if(setTimeEnd != null && !"".equals(setTimeEnd)){
                setTimeEnd = setTimeEnd+" 00:00:00";
                pd.put("setTimeEnd", setTimeEnd);
            }
            String  mission_level = pd.getString("mission_level");
            pd.put("mission_level", mission_level);
            String  mission_type = pd.getString("mission_type");
            pd.put("mission_type", mission_type);

            //检索条件===
            Map<String,Object> dataMap = new HashMap<String,Object>();
            List<String> titles = new ArrayList<String>();

            titles.add("任务名称");
            titles.add("任务级别");
            titles.add("任务类型");
            titles.add("任务描述");
            titles.add("创建时间");
            dataMap.put("titles", titles);
            List<PageData> varOList = taskSetService.listAll(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for(int i=0;i<varOList.size();i++){
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("mission"));
                vpd.put("var2", varOList.get(i).getString("mission_level"));
                vpd.put("var3", varOList.get(i).getString("mission_type"));
                vpd.put("var4", varOList.get(i).getString("mission_description"));
                vpd.put("var5", varOList.get(i).getString("set_time"));
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

    /**
     * 打开上传EXCEL页面
     */
    @RequestMapping(value="/goUploadExcel")
    public ModelAndView goUploadExcel()throws Exception{
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("worker/uploadexcel");
        return mv;
    }

    /**
     * 下载模版
     */
    @RequestMapping(value="/downExcel")
    public void downExcel(HttpServletResponse response)throws Exception{

        FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Taskset.xls", "Taskset.xls");

    }

    /**
     * 从EXCEL导入到数据库
     */
    @SuppressWarnings("unused")
    @RequestMapping(value="/readExcel",method = RequestMethod.POST)
    public ModelAndView readExcel(
            @RequestParam(value="excel",required=false) MultipartFile file
    ) throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        if (null != file && !file.isEmpty()) {
            String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
            String fileName =  FileUpload.fileUp(file, filePath, "taskexcel");							//执行上传

            List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);

            for(PageData pds : listPd) {

                pd.put("mission", pds.getString("var0"));
                pd.put("mission_type", pds.getString("var1"));
                pd.put("mission_level", pds.getString("var2"));
                pd.put("mission_source", pds.getString("var3"));
                pd.put("cover_fields", pds.getString("var4"));
                pd.put("mission_description", pds.getString("var5"));
                pd.put("cycle_time", pds.getString("var6"));
                pd.put("set_time", new Date());
                taskSetService.save(pd);
            }
            mv.addObject("msg","success");
        }
        mv.setViewName("save_result");
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
