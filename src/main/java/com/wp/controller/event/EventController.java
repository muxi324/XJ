package com.wp.controller.event;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.entity.databank.Workshop;
import com.wp.service.databank.WorkshopService;
import com.wp.service.event.EventService;
import com.wp.util.Const;
import com.wp.util.PageData;
import com.wp.util.StringUtil;
import com.wp.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/eventManage")
public class EventController extends BaseController{
    String menuUrl = "eventManage/list.do"; //菜单地址(权限用)
    @Resource(name = "eventService")
    private EventService eventService;
    @Resource(name="workshopService")
    private WorkshopService workshopService;

    @RequestMapping(value = "list")
    public ModelAndView listEvent(Page page) {
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            String  enquiry = pd.getString("enquiry");
            if(null != enquiry && !"".equals(enquiry)){
                pd.put("enquiry", enquiry.trim());
            }else {
                pd.put("enquiry", "");
            }
            page.setPd(pd);
            List<PageData> varList = eventService.list(page);	//列出${objectName}列表
            mv.setViewName("taskManage/eventManage");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
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
        String eventName = eventService.getEventByName(pd.getString("event_name"));
        if (eventName == null || "".equals(eventName)) {
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
        work.put("font_color",pd.getString("numFontColor"));
        work.put("font_size",pd.getString("numFontSize"));
        //构造note子json数组字符串
        JSONArray noteArray = new JSONArray();
        JSONObject note1 = new JSONObject();
        note1.put("note_name","正常范围");
        note1.put("note_content",pd.getString("backNum_downLimit") + "-" + pd.getString("backNum_upLimit"));
        note1.put("font_color","numFontColor");
        note1.put("font_size","numFontSize");
        noteArray.add(note1);
        JSONObject note2 = new JSONObject();
        note2.put("note_name","异常标准");
        note2.put("note_content", pd.getString("exception"));
        note2.put("font_color","exceptionFontColor");
        note2.put("font_size","exceptionFontSize");
        noteArray.add(note2);
        JSONObject note3= new JSONObject();
        note3.put("note_name","特殊提示");
        note3.put("note_content", pd.getString("notice"));
        note3.put("font_color","noticeFontColor");
        note3.put("font_size","noticeFontSize");
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
