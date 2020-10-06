package com.wp.controller.materialmag;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.service.materialmag.ToolsMagService;
import com.wp.service.system.role.RoleService;
import com.wp.util.*;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.test.JSONAssert;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wp on 2018/3/28.
 */
@Controller
@RequestMapping(value="/toolsmag")
public class ToolsMagController extends BaseController {
    String menuUrl = "toolsmag/list.do"; //菜单地址(权限用)
    @Resource(name="toolsMagService")
    private ToolsMagService toolsMagService;

    @Resource(name="roleService")
    private RoleService roleService;

    /**
     * 列表
     */
    @RequestMapping(value="/list")
    public ModelAndView list(Page page){
        logBefore(logger, "工具列表");
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
            String  description = pd.getString("description");
            pd.put("description", description);
            String loginUserName = FactoryUtil.getLoginUserName();
            pd.put("USERNAME",loginUserName);
            if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
                pd.put("factory_id",FactoryUtil.getFactoryId());
            }
            page.setPd(pd);
         //   System.out.println(pd);
            List<PageData> varList = toolsMagService.list(page);	//列出${objectName}列表
            mv.setViewName("materialmag/tools_mag");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value="/delete")
    public void delete(PrintWriter out){
        logBefore(logger, "删除工具");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            toolsMagService.delete(pd);
            out.write("success");
            out.close();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
    }

    /**
     * 去入库页面
     */
    @RequestMapping(value="/goInputStorage")
    public ModelAndView goAdd(){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("materialmag/tools_input");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }
    /**
     * 去新增页面
     */

    @RequestMapping(value="/goNewAdd")
    public ModelAndView goNewAdd(){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("materialmag/tools_add");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }
    /**
     * 去出库页面
     */
    @RequestMapping(value="/goDecrease")
    public ModelAndView goDecrease(){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            Integer ID = Integer.parseInt(pd.getString("material_id"));
            pd.put("material_id", ID);	//物资ID string改为int
            pd.put("factory_id",FactoryUtil.getFactoryId());
            Integer stock = toolsMagService.selectStock(pd);
            pd.put("stock",stock);
            mv.setViewName("materialmag/tools_decrease");
            mv.addObject("msg", "save1");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 入库保存
     */
    @RequestMapping(value="/save")
    public ModelAndView save(PrintWriter out) throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        try {
            pd.put("factory_id",FactoryUtil.getFactoryId());
            pd.put("workshop_id",FactoryUtil.getWorkshopId());
            pd.put("is_consume",1 );	//添加入库状态
            pd.put("time",  Tools.date2Str(new Date()));	//添加时间
            int ID = Integer.parseInt(pd.getString("material_id"));
          //  System.out.println("工具的ID是"+ID);
            pd.put("material_id", ID);	//物资ID string改为int
            Integer stock1 = toolsMagService.selectStock(pd);
          //  System.out.println("第一次入库的数量是:"+stock1);
            if (stock1 == null) {
                //插入materail；
                String  descrp = pd.getString("description");
                String  name = pd.getString("name");
                Integer  num = Integer.parseInt(pd.getString("material_num"));
                pd.put("type",2 );	//添加物资种类工具
                pd.put("description",descrp);
                pd.put("name",name);
                pd.put("stock",num);
                pd.put("material_id", ID);
                toolsMagService.firstsave(pd);
                pd.put("type",2 );	//添加工具种类配件
                pd.put("material_name",name);
                toolsMagService.save(pd);
            }

            int num = Integer.parseInt(pd.getString("material_num"));
           // System.out.println("这一次入库的数量是:"+num);
            int stock = stock1+num;
           // System.out.println("加在一起的的数量是:"+stock);
            pd.put("stock",stock);
            toolsMagService.editStock(pd);
            pd.put("type",2 );	//添加工具种类配件
            String  name = pd.getString("name");
            pd.put("material_name",name);
          //  System.out.println("----------------------------");
           // System.out.println(pd);
            toolsMagService.save(pd);
        } catch (Exception e ){
            e.printStackTrace();
        }
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }
    /**
     * 出库保存
     */
    @RequestMapping(value="/save1")
    public ModelAndView save1(PrintWriter out) throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("is_consume",0 );	//添加出库状态
        pd.put("time",  Tools.date2Str(new Date()));	//添加时间
        Integer ID = Integer.parseInt(pd.getString("material_id"));
        pd.put("material_id", ID);	//物资ID string改为int
        pd.put("factory_id",FactoryUtil.getFactoryId());
        Integer stock1 = toolsMagService.selectStock(pd);
        int num = Integer.parseInt(pd.getString("material_num"));
        int stock = stock1-num;
        pd.put("stock",stock);
        toolsMagService.editStock(pd);
        pd.put("type",2 );	//添加工具种类配件
        String  name = pd.getString("name");
        pd.put("material_name",name);

        pd.put("workshop_id",FactoryUtil.getWorkshopId());
        toolsMagService.save(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 去出库记录页面
     */
    @RequestMapping(value="/goOutput")
    public ModelAndView goOutput(){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("factory_id",FactoryUtil.getFactoryId());
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
         //   System.out.println(pd);
            List<PageData> varList = toolsMagService.findById1(pd);								//根据ID读取
          //  System.out.println("-------------------------------");
          //  System.out.println(JSONArray.fromObject(varList));
            mv.setViewName("materialmag/tools_output_history");
            mv.addObject("msg", "out_history");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }
    /**
     * 去入库记录页面
     */
    @RequestMapping(value="/goInput")
    public ModelAndView goInput(){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("factory_id",FactoryUtil.getFactoryId());
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

            List<PageData> varList = toolsMagService.findById(pd);								//根据ID读取
          //  System.out.println("-------------------------------");
           // System.out.println(JSONArray.fromObject(varList));
            mv.setViewName("materialmag/tools_input_history");
            mv.addObject("msg", "Input_history");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
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
                String Array_ids[] = set_ids.split(",");
                if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){toolsMagService.deleteAll(Array_ids);}
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
