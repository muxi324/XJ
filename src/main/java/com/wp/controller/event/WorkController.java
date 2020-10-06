package com.wp.controller.event;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.entity.eventInfo.Event;
import com.wp.service.databank.WorkshopService;
import com.wp.service.event.EventService;
import com.wp.service.event.WorkService;
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
@RequestMapping(value="/workset")
public class WorkController extends BaseController{

    String menuUrl = "workset/list.do"; //菜单地址(权限用)
    @Resource(name = "workService")
    private WorkService workService;
    @Resource(name="workshopService")
    private WorkshopService workshopService;
    @Resource(name = "eventService")
    private EventService eventService;
    /**
     * 工作内容管理页面
     * @param page
     * @return
     */
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

         //   System.out.println(pd +"66666666666");
            List<PageData> varList = workService.list(page);	//列出${objectName} 事件对象

          //  System.out.println(varList+"66666666666");
            mv.setViewName("workManage/workManage");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }
    /**
     * 去增加工作内容页面
     * @return
     */
    @RequestMapping(value = "goAddWorkContent", method = {RequestMethod.GET})
    public ModelAndView goAddWorkContent() {
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        mv.setViewName("workManage/addWorkContent");
        return mv;
    }
    /**
     * 接收数据增加工作内容
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "addWorkContent", method = {RequestMethod.POST})
    public ModelAndView addWorkContent1() throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
       /* String eventName = pd.getString("eventName");
        String additions = eventService.getAdditionByName(eventName);*/
        String eventId = pd.getString("event_id");   //通过event_id取additions
        String additions = eventService.getAdditionById(eventId);   //获取工作内容
        JSONArray workArray;
        if (additions == null || additions.equals("")) {
            workArray = new JSONArray();
        } else {
            workArray = JSONArray.fromObject(additions);    //将工作内容转成json
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
            view.put("view_name","输入数字，" + pd.getString("content_name"));
            view.put("font_size",20);
            view.put("font_color","#000000");
            viewArray.add(view);
        }
        if ("1".equals(pd.getString("is_backText"))) {
            JSONObject view = new JSONObject();
            view.put("view_class","输入框");
            view.put("view_name","输入，" + pd.getString("content_name"));
            view.put("font_size",20);
            view.put("font_color","#000000");
            viewArray.add(view);
        }
        //是否需要反馈文件
        if ("1".equals(pd.getString("is_backFile"))) {
            JSONObject view = new JSONObject();
            view.put("view_class","文件");
            view.put("view_name","上传" + pd.getString("content_name"));
            view.put("font_size",20);
            view.put("font_color","#000000");
            viewArray.add(view);
        }
        work.put("view",viewArray);
        workArray.add(work);
        eventService.saveWorkContent(workArray.toString(),pd.getString("event_id"));
        mv.setViewName("taskManage/addEvent");
        //mv.addObject("pd",getEventById(eventId));
        List<String> workcontentList = new ArrayList<String>();
        for (int i = 0; i<workArray.size(); i++) {
            JSONObject jsonObject = workArray.getJSONObject(i);
            String contentName = jsonObject.getString("work_name");
            workcontentList.add(contentName);
        }

        mv.addObject("contentList",workcontentList);
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

