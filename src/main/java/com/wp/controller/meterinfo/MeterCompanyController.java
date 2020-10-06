package com.wp.controller.meterinfo;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.service.meterinfo.MeterCompanyService;
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


@Controller
@RequestMapping(value ="/meter_com")
public class MeterCompanyController extends BaseController {

    String menuUrl = "meter_com/list.do"; //菜单地址(权限用)
    @Resource(name="meterCompanyService")
    private MeterCompanyService meterCompanyService;

    /**
     *
     * @param page  输入查询关键字，没有查询关键字默认返回所有信息
     * @return
     */
    @RequestMapping(value="/list")
    public ModelAndView getMeterCompanyList(Page page) {
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        logBefore(logger,"仪表管理");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String  enquiry = pd.getString("enquiry");
            if(null != enquiry && !"".equals(enquiry)){
                pd.put("enquiry", enquiry.trim());
            }else {
                pd.put("enquiry", "");
            }
            pd.put("factory_id",FactoryUtil.getFactoryId());
            page.setPd(pd);
            List<PageData> varList = meterCompanyService.list(page);
            mv.setViewName("meterinfo/metercompany");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject("user",SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER));
//            修改权限
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     *  删除水表公司
     * @param page 水表公司的id
     */
    @RequestMapping(value="/delete")
//    参数有误
    public void goDelete(PrintWriter out){
        logBefore(logger, "删除工厂");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try{
            pd=this.getPageData();
            meterCompanyService.delete(pd);
            out.write("success");
            out.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  去新增水表公司页面
     * @return
     */
    @RequestMapping(value="/goAdd")
    public ModelAndView goAdd( ){
        logBefore(logger, "去新增水表工厂页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("meterinfo/metercom_edit");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     *   保存结果
     * @param out
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/save")
    public ModelAndView save(PrintWriter out) throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("checktime",  Tools.date2Str(new Date()));	//添加时间
        pd.put("factory_id",FactoryUtil.getFactoryId());
        meterCompanyService.save(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     *  去修改水表公司信息页面
     * @return
     */
    @RequestMapping(value="/goEdit")
    public ModelAndView goEdit(){
        logBefore(logger, "去修改水表公司页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = meterCompanyService.findById(pd);	//根据ID读取
            pd.put("factory_id", FactoryUtil.getFactoryId());
            mv.setViewName("meterinfo/metercom_edit");
            mv.addObject("msg", "edit");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     *  修改信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/edit")
    public ModelAndView edit() throws Exception {
        logBefore(logger, "修改水表公司信息");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd=this.getPageData();
        meterCompanyService.edit(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    /*
     * 导出到excel
     * @return
     */
    @RequestMapping(value="/excel")
    public ModelAndView exportExcel(){
        logBefore(logger, "导出异常列表到excel");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try{
            String  enquiry = pd.getString("enquiry");
            if(null != enquiry && !"".equals(enquiry)){
                pd.put("enquiry", enquiry.trim());
            }else {
                pd.put("enquiry", "");
            }
            String sendTimeStart = pd.getString("reportTimeStart");
            String sendTimeEnd = pd.getString("reportTimeEnd");

            if(sendTimeStart != null && !"".equals(sendTimeStart)){
                sendTimeStart = sendTimeStart+" 00:00:00";
                pd.put("reportTimeStart", sendTimeStart);
            }
            if(sendTimeEnd != null && !"".equals(sendTimeEnd)){
                sendTimeEnd = sendTimeEnd+" 00:00:00";
                pd.put("reportTimeEnd", sendTimeEnd);
            }
            String  level = pd.getString("level");
            pd.put("level", level);
            String loginUserName = FactoryUtil.getLoginUserName();
            if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
                pd.put("factory_id",FactoryUtil.getFactoryId());
                pd.put("workshop_id",FactoryUtil.getWorkshopId());
                pd.put("team_id",FactoryUtil.getTeamId());
            }
            //检索条件===
            Map<String,Object> dataMap = new HashMap<String,Object>();
            //文件名
            String filename = "水表公司列表-"+new SimpleDateFormat("yyyyMMddhhmm").format(new Date())+".xls";
            dataMap.put("filename",filename);
            //标题
            String headerName = "水表公司列表";
            dataMap.put("headerName",headerName);

            //文件列表头
            List<String> titles = new ArrayList<String>();
            titles.add("公司名称");
            titles.add("公司地址");
            titles.add("代理人");
            titles.add("联系方式");
            titles.add("创建时间");

            dataMap.put("titles", titles);
            List<PageData> varOList = meterCompanyService.listAll(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for(int i=0;i<varOList.size();i++){
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("factory_name"));
                vpd.put("var2", varOList.get(i).getString("address"));
                vpd.put("var3", varOList.get(i).getString("representative"));
                vpd.put("var4", varOList.get(i).getString("phone"));
                vpd.put("var5", varOList.get(i).getString("checktime"));

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


    /* ===============================权限================================== */
    public Map<String, String> getHC(){
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
    }
    /* ===============================权限================================== */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }

}