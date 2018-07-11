package com.wp.controller.event;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.entity.databank.Workshop;
import com.wp.service.databank.WorkshopService;
import com.wp.service.event.EventService;
import com.wp.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
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

@Controller
@RequestMapping(value="/eventManage")
public class EventController extends BaseController{
    String menuUrl = "eventManage/list.do"; //菜单地址(权限用)
    @Resource(name = "eventService")
    private EventService eventService;
    @Resource(name="workshopService")
    private WorkshopService workshopService;

    @RequestMapping(value = "/list")
    public ModelAndView listEvent(Page page) {
        logBefore(logger, "事件列表");
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
            String loginUserName = FactoryUtil.getLoginUserName();
            if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
                pd.put("factory_id",FactoryUtil.getFactoryId());
            }
            page.setPd(pd);
            List<PageData> varList = eventService.list(page);	//列出${objectName}
            for (PageData p : varList) {
                String qrcode = p.getString("qrcode");
                //二维码不存在补充二维码
                if (StringUtils.isEmpty(qrcode)) {
                    String qrContent = "事件名: " + p.getString("event_name") + "  具体位置: " + p.getString("instrument_place");
                    String encoderImgId = p.getString("event_name") + ".png";
                    try {
                        String filePath = "C:/apache-tomcat-8.5.23/webapps/qrupload/qrImg/" + encoderImgId;  //存放路径
                        TwoDimensionCode.encoderQRCode(qrContent, filePath, "png");							//执行生成二维码
                        p.put("qrcode",filePath);
                        eventService.update(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            mv.setViewName("taskManage/eventManage");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }
    @RequestMapping(value = "/qrcode")
    public ModelAndView qrCode() {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String path = pd.getString("path");
        int x = path.indexOf("qrImg");
        String picPath = path.substring(x,path.length());
        mv.addObject("picPath",picPath);
        mv.setViewName("taskManage/qrCode");
        return mv;
    }
    /**
     * 删除
     */
    @RequestMapping(value="/delete")
    public void delete(PrintWriter out){
        logBefore(logger, "删除事件");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            eventService.delete(pd);
            out.write("success");
            out.close();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
    }

    @RequestMapping(value = "/addEvent")
    public ModelAndView addEvent() {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<Workshop> workshopList = workshopService.listWorkshop();
            mv.addObject("workshopList",workshopList);
            mv.setViewName("taskManage/addEvent");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = "addEvent1")
    @ResponseBody
    public String addEvent1() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("create_time",  Tools.date2Str(new Date()));
        pd.put("factory_id",FactoryUtil.getFactoryId());
        String eventName = eventService.getEventByName(pd.getString("event_name"));
        if (eventName == null || "".equals(eventName)) {
            String qrContent = "事件名: " + pd.getString("event_name") + "具体位置: " + pd.getString("instrument_place");
            String encoderImgId = pd.getString("event_name") + Tools.date2Str(new Date())  + ".png";
            try {
                String filePath = "C:/apache-tomcat-8.5.23/webapps/qrupload/qrImg/" + encoderImgId;  //存放路径
                TwoDimensionCode.encoderQRCode(qrContent, filePath, "png");							//执行生成二维码
                pd.put("qrcode",filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            eventService.save(pd);
        } else {
            eventService.update(pd);
        }
        String result = "保存事件成功，请为事件添加工作内容！";
        return result;
    }

    @RequestMapping(value = "editEvent")
    public ModelAndView editEvent() throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData result = eventService.getEventById(pd);
        mv.setViewName("taskManage/addEvent");
        mv.addObject("pd", result);
        List<String> workcontentList = new ArrayList<String>();
        String eventName = result.getString("event_name");
        String additions = eventService.getAdditionByName(eventName);
        JSONArray workArray;
        if (additions == null || additions.equals("")) {
            workArray = new JSONArray();
        } else {
            workArray = JSONArray.fromObject(additions);
        }
        for (int i = 0; i<workArray.size(); i++) {
            JSONObject jsonObject = workArray.getJSONObject(i);
            String contentName = jsonObject.getString("work_name");
            workcontentList.add(contentName);
        }
        mv.addObject("contentList",workcontentList);
        return mv;
    }

    @RequestMapping(value = "addWorkContent", method = {RequestMethod.GET})
    public ModelAndView addWorkContent() {
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        mv.setViewName("taskManage/addWorkContent");
        mv.addObject("eventName",pd.getString("eventName"));
        return mv;
    }

    @RequestMapping(value = "addWorkContent", method = {RequestMethod.POST})
    public ModelAndView addWorkContent1() throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        String eventName = pd.getString("eventName");
        String additions = eventService.getAdditionByName(eventName);
        JSONArray workArray;
        if (additions == null || additions.equals("")) {
            workArray = new JSONArray();
        } else {
            workArray = JSONArray.fromObject(additions);
        }
        //构造新的工作内容json对象
        JSONObject work = new JSONObject();
        work.put("work_name",pd.getString("content_name"));
       /* work.put("font_color",pd.getString("numFontColor"));
        work.put("font_size",pd.getString("numFontSize"));*/
        work.put("font_color","#000000");
        work.put("font_size",20);
        //构造note子json数组字符串
        JSONArray noteArray = new JSONArray();
        JSONObject note1 = new JSONObject();
        note1.put("note_name","正常范围");
        note1.put("note_content",pd.getString("backNum_downLimit") + "-" + pd.getString("backNum_upLimit"));
        note1.put("font_color",pd.getString("numFontColor"));
        note1.put("font_size",pd.getString("numFontSize"));
        noteArray.add(note1);
        JSONObject note2 = new JSONObject();
        note2.put("note_name","异常标准");
        note2.put("note_content", pd.getString("exception"));
        note2.put("font_color",pd.getString("exceptionFontColor"));
        note2.put("font_size",pd.getString("exceptionFontSize"));
        noteArray.add(note2);
        JSONObject note3= new JSONObject();
        note3.put("note_name","特殊提示");
        note3.put("note_content", pd.getString("notice"));
        note3.put("font_color",pd.getString("noticeFontColor"));
        note3.put("font_size",pd.getString("noticeFontSize"));
        noteArray.add(note3);
        work.put("work_note",noteArray);
        //构造view子json数组字符串
        JSONArray viewArray = new JSONArray();
        if ("1".equals(pd.getString("is_takePhoto"))){
            JSONObject view = new JSONObject();
            view.put("view_class","拍照");
            view.put("view_name",pd.getString("content_name") + "拍照");
            view.put("font_size",20);
            view.put("font_color","#000000");
            viewArray.add(view);
        }

        if ("1".equals(pd.getString("is_backNum"))) {
            JSONObject view = new JSONObject();
            view.put("view_class","输入框");
            view.put("view_name","输入" + pd.getString("content_name"));
            view.put("font_size",20);
            view.put("font_color","#000000");
            viewArray.add(view);
        }
        if ("1".equals(pd.getString("is_backText"))) {
            JSONObject view = new JSONObject();
            view.put("view_class","输入框");
            view.put("view_name","输入" + pd.getString("content_name"));
            view.put("font_size",20);
            view.put("font_color","#000000");
            viewArray.add(view);
        }
        work.put("view",viewArray);
        workArray.add(work);
        eventService.saveWorkContent(workArray.toString(),pd.getString("eventName"));
        mv.setViewName("taskManage/addEvent");
        mv.addObject("pd",getEventByName(eventName));
        List<String> workcontentList = new ArrayList<String>();
        for (int i = 0; i<workArray.size(); i++) {
            JSONObject jsonObject = workArray.getJSONObject(i);
            String contentName = jsonObject.getString("work_name");
            workcontentList.add(contentName);
        }
        mv.addObject("contentList",workcontentList);
        return mv;
    }

    @RequestMapping(value = "goEditWorkContent")
    public ModelAndView goEditWorkContent() throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String eventName = pd.getString("eventName");
        String contentName = pd.getString("contentName");
        String additions = eventService.getAdditionByName(eventName);
        JSONArray workArray;
        if (additions == null || additions.equals("")) {
            workArray = new JSONArray();
        } else {
            workArray = JSONArray.fromObject(additions);
        }
        PageData result = new PageData();
        for (int i = 0; i<workArray.size(); i++) {
            JSONObject jsonObject = workArray.getJSONObject(i);
            if(contentName.equals(jsonObject.getString("work_name"))) {
                result.put("work_name",contentName);
                result.put("font_color",jsonObject.getString("font_color"));
            }
        }
        return mv;
    }


    public PageData getEventByName(String eventName) {
        PageData result = new PageData();
        try {
           result = eventService.getEventByNameForPageData(eventName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
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
            String set_ids = pd.getString("event_ids");

            if(null != set_ids && !"".equals(set_ids)){
                String Arrayhouse_ids[] = set_ids.split(",");
                if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){eventService.deleteAll(Arrayhouse_ids);}
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

            titles.add("所属车间");
            titles.add("所属巡检区域");
            titles.add("所属巡检点");
            titles.add("事件名称");
            titles.add("具体位置");
            titles.add("创建时间");
            dataMap.put("titles", titles);
            List<PageData> varOList = eventService.listAll(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for(int i=0;i<varOList.size();i++){
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("workshop"));
                vpd.put("var2", varOList.get(i).getString("check_scope"));
                vpd.put("var3", varOList.get(i).getString("check_point"));
                vpd.put("var4", varOList.get(i).getString("event_name"));
                vpd.put("var5", varOList.get(i).getString("instrument_place"));
                vpd.put("var6", varOList.get(i).getString("create_time"));
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

                pd.put("workshop", pds.getString("var0"));
                pd.put("check_scope", pds.getString("var1"));
                pd.put("check_point", pds.getString("var2"));
                pd.put("event_name", pds.getString("var3"));
               // pd.put("instrument_place", pds.getString("var4"));
               // pd.put("mission_description", pds.getString("var5"));
                //pd.put("cycle_time", pds.getString("var6"));
                pd.put("create_time", new Date());
                eventService.save(pd);
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
