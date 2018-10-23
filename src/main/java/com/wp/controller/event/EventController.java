package com.wp.controller.event;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.entity.eventInfo.Event;
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
                pd.put("workshop_id",FactoryUtil.getWorkshopId());
            }
            page.setPd(pd);
            List<PageData> varList = eventService.list(page);	//列出${objectName}
            for (PageData p : varList) {
                String qrcode = p.getString("qrcode");
                //二维码不存在补充二维码
                if (StringUtils.isEmpty(qrcode)) {
                    String qrContent = "事件ID: " + p.getString("event_id") +" 事件名: " + p.getString("event_name") + "  所属车间: " + p.getString("workshop");
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
     * 删除:修改事件的状态：0为删除，用户不可见，1用户可见
     */
    @RequestMapping(value="/delete")
    public void delete(PrintWriter out){
        logBefore(logger, "删除事件");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            PageData result = eventService.getEventById(pd);
            result.put("status",0);
            eventService.update(result);
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
            String factory_id = FactoryUtil.getFactoryId();
            String workshop_id = FactoryUtil.getWorkshopId();
            List<PageData> workshopList = new ArrayList<PageData>();
            if(StringUtils.isNotEmpty(factory_id)) {
                PageData factory = new PageData();
                factory.put("factory_id",factory_id);
                if(StringUtils.isNotEmpty(workshop_id)){
                    PageData workshop = new PageData();
                    workshop.put("id", workshop_id);
                    PageData data = workshopService.findById(workshop);
                    String workshopName = data.getString("workshop");
                    pd.put("workshop",workshopName);
                }else{
                    workshopList = workshopService.listWorkshopByFac(factory);
                    mv.addObject("workshopList",workshopList);
                }
            }
            mv.setViewName("taskManage/addEvent");
            mv.addObject("pd", pd);
            mv.addObject("workshopId", workshop_id);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = "addEvent1")
    @ResponseBody
    public String addEvent1() throws Exception {
        String result = "";
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("factory_id",FactoryUtil.getFactoryId());
        pd.put("workshop_id",FactoryUtil.getWorkshopId());
        pd.put("status", 1);
        if(StringUtils.isEmpty(pd.getString("workshop"))){
            String workshopId = pd.getString("workshop_id");
            PageData workshop = new PageData();
            workshop.put("id", workshopId);
            PageData data = workshopService.findById(workshop);
            String workshopName = data.getString("workshop");
            pd.put("workshop",workshopName);
        }
        String eventId = pd.getString("event_id");
        if(StringUtils.isNotEmpty(eventId)){  //修改
            String additions = eventService.getAdditionById(eventId);
            pd.put("additions",additions);
            eventService.update(pd);
        }else{            //新增
            Event e = new Event();
            e.setCreate_time(Tools.date2Str(new Date()));
            e.setEvent_name(pd.getString("event_name"));
            e.setInstrument_place(pd.getString("instrument_place"));
            e.setWorkshop(pd.getString("workshop"));
            e.setEvent_level(pd.getString("event_level"));
            e.setFont_color(pd.getString("font_color"));
            e.setFont_size(pd.getString("font_size"));
            e.setFactory_id(pd.getString("factory_id"));
            e.setStatus(pd.getString("status"));
            e.setWorkshop_id(pd.getString("workshop_id"));
            eventService.insertAndGetId(e);
            String event_id = e.getEvent_id();
            return event_id;
        }

      //  String eventName = eventService.getEventByName(pd.getString("event_name"));
      //  if (eventName == null || "".equals(eventName)) {
/*            String qrContent = "事件名: " + pd.getString("event_name") + "具体位置: " + pd.getString("instrument_place");
            String encoderImgId = pd.getString("event_name") + Tools.date2Str(new Date())  + ".png";
            try {
                String filePath = "C:/apache-tomcat-8.5.23/webapps/qrupload/qrImg/" + encoderImgId;  //存放路径
                TwoDimensionCode.encoderQRCode(qrContent, filePath, "png");							//执行生成二维码
                pd.put("qrcode",filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
       //     eventService.save(pd);
      //  } else {
      //      eventService.update(pd);
      //  }
          return result;
    }

    @RequestMapping(value = "goEditEvent")          //编辑处理逻辑：先取出数据后保存，然后将新增的id取出给addEvent页面
    public ModelAndView goEditEvent() throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData result = eventService.getEventById(pd);
        result.put("status",0);
        eventService.update(result);   //将要编辑的事件状态改为 0使用户不可见
//        插入记录返回该记录的id
        Event e = new Event();
        e.setEvent_name(result.getString("event_name"));
        e.setInstrument_place(result.getString("instrument_place"));
        e.setWorkshop(result.getString("workshop"));
        e.setAdditions(result.getString("additions"));
        e.setFont_color(result.getString("font_color"));
        e.setFont_size(result.getString("font_size"));
        e.setStatus("1");
        e.setQrcode(result.getString("qrcode"));
        e.setCreate_time(result.getString("create_time"));
        e.setEvent_level(result.getString("event_level"));
        e.setFactory_id(result.getString("factory_id"));
        e.setWorkshop_id(result.getString("workshop_id"));
        e.setTeam_id(result.getString("team_id"));
        eventService.insertAndGetId(e);
        String event_id = e.getEvent_id();    //得到返回的新事件id

        PageData event = new PageData();
        event.put("eventId",event_id);
        PageData result1 = eventService.getEventById(event);

        String factory_id = FactoryUtil.getFactoryId();
        String workshop_id = FactoryUtil.getWorkshopId();
        List<PageData> workshopList = new ArrayList<PageData>();
        if(StringUtils.isNotEmpty(factory_id)) {
            PageData factory = new PageData();
            factory.put("factory_id",factory_id);
            if(StringUtils.isEmpty(workshop_id)){
                workshopList = workshopService.listWorkshopByFac(factory);
                mv.addObject("workshopList",workshopList);
            }
        }

        mv.setViewName("taskManage/addEvent");
        mv.addObject("pd", result1);
        mv.addObject("workshopId", workshop_id);
        List<String> workcontentList = new ArrayList<String>();
        String eventId = result1.getString("event_id");
        String additions = eventService.getAdditionById(eventId);
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
   // 复制
    @RequestMapping(value = "goCopyEvent")          //编辑处理逻辑：先取出数据后保存，然后将新增的id取出给addEvent页面
    public ModelAndView goCopyEvent() throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData result = eventService.getEventById(pd);
//        插入记录返回该记录的id
        Event e = new Event();
        e.setEvent_name(result.getString("event_name"));
        e.setInstrument_place(result.getString("instrument_place"));
        e.setWorkshop(result.getString("workshop"));
        e.setAdditions(result.getString("additions"));
        e.setFont_color(result.getString("font_color"));
        e.setFont_size(result.getString("font_size"));
        e.setQrcode(result.getString("qrcode"));
        e.setCreate_time(result.getString("create_time"));
        e.setEvent_level(result.getString("event_level"));
        e.setFactory_id(result.getString("factory_id"));
        e.setWorkshop_id(result.getString("workshop_id"));
        e.setTeam_id(result.getString("team_id"));
        eventService.insertAndGetId(e);
        String event_id = e.getEvent_id();

        PageData event = new PageData();
        event.put("eventId",event_id);
        PageData result1 = eventService.getEventById(event);

        String factory_id = FactoryUtil.getFactoryId();
        String workshop_id = FactoryUtil.getWorkshopId();
        List<PageData> workshopList = new ArrayList<PageData>();
        if(StringUtils.isNotEmpty(factory_id)) {
            PageData factory = new PageData();
            factory.put("factory_id",factory_id);
            if(StringUtils.isEmpty(workshop_id)){
                workshopList = workshopService.listWorkshopByFac(factory);
                mv.addObject("workshopList",workshopList);
            }
        }

        mv.setViewName("taskManage/addEvent");
        mv.addObject("pd", result1);
        mv.addObject("workshopId", workshop_id);
        List<String> workcontentList = new ArrayList<String>();
        String eventId = result1.getString("event_id");
        String additions = eventService.getAdditionById(eventId);
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

    @RequestMapping(value = "goAddWorkContent", method = {RequestMethod.GET})
    public ModelAndView addWorkContent() {
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        mv.setViewName("taskManage/addWorkContent");
        mv.addObject("event_id",pd.getString("event_id"));
        mv.addObject("eventName",pd.getString("eventName"));
        return mv;
    }

    @RequestMapping(value = "addWorkContent", method = {RequestMethod.POST})
    public ModelAndView addWorkContent1() throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
       /* String eventName = pd.getString("eventName");
        String additions = eventService.getAdditionByName(eventName);*/
        String eventId = pd.getString("event_id");   //通过event_id取additions
        String additions = eventService.getAdditionById(eventId);
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
        note2.put("font_size",Integer.parseInt(pd.getString("exceptionFontSize")));
        noteArray.add(note2);
        JSONObject note3= new JSONObject();
        note3.put("note_name","特殊提示");
        note3.put("note_content", pd.getString("notice"));
        note3.put("font_color",pd.getString("noticeFontColor"));
        note3.put("font_size",Integer.parseInt(pd.getString("noticeFontSize")));
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
        eventService.saveWorkContent(workArray.toString(),pd.getString("event_id"));
        mv.setViewName("taskManage/addEvent");
        mv.addObject("pd",getEventById(eventId));
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
        String eventId = pd.getString("eventId");
        String contentName = pd.getString("workName");
        String additions = eventService.getAdditionById(eventId);
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
                result.put("content_name",contentName);
                result.put("font_color",jsonObject.getString("font_color"));
                result.put("font_size",jsonObject.getString("font_size"));
                String str = jsonObject.getString("work_note");
                JSONArray json = JSONArray.fromObject(str ); // 首先把字符串转成 JSONArray  对象
                if(json.size()>0){
                        JSONObject job = json.getJSONObject(0);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        result.put("numFontColor",job.getString("font_color"));
                        result.put("numFontSize",job.getString("font_size"));
                        String strNote = job.getString("note_content");
                        String[] parts = strNote.split("-");
                        if(parts.length>0){
                            String min = parts[0];
                            String max = parts[1];
                            result.put("backNum_downLimit",min);
                            result.put("backNum_upLimit",max);
                        }

                        //异常标准
                        JSONObject job1 = json.getJSONObject(1);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        result.put("exceptionFontColor",job1.getString("font_color"));
                        result.put("exceptionFontSize",job1.getString("font_size"));
                        result.put("exception",job1.getString("note_content"));
                        // 特殊提示
                        JSONObject job2 = json.getJSONObject(1);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        result.put("noticeFontColor",job2.getString("font_color"));
                        result.put("noticeFontSize",job2.getString("font_size"));
                        result.put("notice",job2.getString("note_content"));

                }
                String str1 = jsonObject.getString("view");
                JSONArray json1 = JSONArray.fromObject(str1 );
                if(json1.size()>0){
                    for(int k=0;k<json1.size();k++){
                        JSONObject view = json1.getJSONObject(k);
                        String type = view.getString("view_class");
                        if(type.equals("拍照")){
                            result.put("is_takePhoto",1);
                        }else if (type.equals("输入框")){
                            result.put("is_backText",1);
                        }
                    }
                }

            }
        }
        mv.addObject("pd",result);
        mv.setViewName("taskManage/WorkContentDetail");
        return mv;
    }

    @RequestMapping(value = "eventDetail")
    public ModelAndView eventDetail() throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData result = eventService.getEventById(pd);

        mv.setViewName("taskManage/eventDetail");
        mv.addObject("pd", result);
        List<String> workcontentList = new ArrayList<String>();
        String additions = result.getString("additions");
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


    public PageData getEventByName(String eventName) {
        PageData result = new PageData();
        try {
           result = eventService.getEventByNameForPageData(eventName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public PageData getEventById(String eventId) {
        PageData result = new PageData();
        try {
            result = eventService.getEventByIdForPageData(eventId);
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
