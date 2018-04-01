package com.wp.controller.materialmag;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.service.materialmag.PartsMagService;
import com.wp.service.system.role.RoleService;
import com.wp.util.*;
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
@RequestMapping(value="/partsmag")
public class PartsMagController extends BaseController {
    String menuUrl = "partsmag/list.do"; //菜单地址(权限用)
    @Resource(name="partsMagService")
    private PartsMagService partsMagService;

    @Resource(name="roleService")
    private RoleService roleService;

    /**
     * 列表
     */
    @RequestMapping(value="/list")
    public ModelAndView list(Page page){
        logBefore(logger, "配件列表");
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

            page.setPd(pd);
            List<PageData> varList = partsMagService.list(page);	//列出${objectName}列表
            mv.setViewName("materialmag/parts_mag");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去入库页面
     */
    @RequestMapping(value="/goAdd")
    public ModelAndView goAdd(){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("materialmag/parts_add");
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
            mv.setViewName("materialmag/parts_decrease");
            mv.addObject("msg", "save");
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
            pd.put("is_consume",1 );	//添加入库状态
            pd.put("time",  Tools.date2Str(new Date()));	//添加时间
            int ID = Integer.parseInt(pd.getString("material_id"));
            pd.put("material_id", "ID");	//物资ID string改为int
            int stock1 = partsMagService.selectStock(pd);
            int num = Integer.parseInt(pd.getString("material_num"));
            int stock = stock1+num;
            pd.put("stock",stock);
            partsMagService.editStock(pd);
            partsMagService.save(pd);
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
        pd.put("material_id", "");	//物资ID
        pd.put("is_consume",0 );	//添加出库状态
        pd.put("time",  Tools.date2Str(new Date()));	//添加时间
        int stock1 = partsMagService.selectStock(pd);
        int num = Integer.parseInt(pd.getString("material_num"));
        int stock = stock1-num;
        pd.put("stock",stock);
        partsMagService.editStock1(pd);
        partsMagService.save(pd);
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
            pd = partsMagService.findById1(pd);								//根据ID读取
            mv.setViewName("materialmag/parts_output_history");
            mv.addObject("msg", "history");
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
            pd = partsMagService.findById(pd);								//根据ID读取
            mv.setViewName("materialmag/parts_input_history");
            mv.addObject("msg", "Input_history");
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
                String Arrayhouse_ids[] = set_ids.split(",");
                if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){partsMagService.deleteAll(Arrayhouse_ids);}
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