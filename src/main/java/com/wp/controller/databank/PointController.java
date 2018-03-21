package com.wp.controller.databank;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.service.databank.AreaService;
import com.wp.service.databank.PointService;
import com.wp.service.system.role.RoleService;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wp on 2018/3/16.
 */
@Controller
@RequestMapping(value="/inspt_point")
public class PointController extends BaseController {

    String menuUrl = "inspt_point/list.do"; //菜单地址(权限用)
    @Resource(name="pointService")
    private PointService pointService;
    @Resource(name="roleService")
    private RoleService roleService;

    /**
     * 保存
     */
    @RequestMapping(value="/save")
    public ModelAndView save(PrintWriter out) throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        // pd.put("id", "");	//ID
        pd.put("add_time",  Tools.date2Str(new Date()));	//添加时间
        pointService.save(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value="/delete")
    public void delete(PrintWriter out){
        logBefore(logger, "删除巡检点");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            pointService.delete(pd);
            out.write("success");
            out.close();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value="/edit")
    public ModelAndView edit() throws Exception{
        logBefore(logger, "修改巡检点信息");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pointService.edit(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;

    }
    /**
     * 列表
     */
    @RequestMapping(value="/list")
    public ModelAndView list(Page page){
        logBefore(logger, "巡检区域列表");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
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
            page.setPd(pd);
            List<PageData> varList = pointService.list(page);	//列出${objectName}列表
            mv.setViewName("databank/inspt_point");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }
    /**
     * 去新增页面
     */
    @RequestMapping(value="/goAdd")
    public ModelAndView goAdd(){
        logBefore(logger, "去新增页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("databank/point_edit");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value="/goEdit")
    public ModelAndView goEdit(){
        logBefore(logger, "去修改页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = pointService.findById(pd);	//根据ID读取
            mv.setViewName("databank/point_edit");
            mv.addObject("msg", "edit");
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
        logBefore(logger, "批量删除车间");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
        PageData pd = new PageData();
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String DATA_IDS = pd.getString("DATA_IDS");
            if(null != DATA_IDS && !"".equals(DATA_IDS)){
                String ArrayDATA_IDS[] = DATA_IDS.split(",");
                pointService.deleteAll(ArrayDATA_IDS);
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
        logBefore(logger, "导出到excel");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {return null;}
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try{
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> titles = new ArrayList<String>();
            titles.add("所属车间");    //${var_index+1}
            titles.add("巡检区域");
            titles.add("巡检点名称");
            titles.add("巡检点范围");
            titles.add("创建时间");
            dataMap.put("titles", titles);
            List<PageData> varOList = pointService.listAll(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for (int i = 0; i < varOList.size(); i++) {
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("workshop"));        //1
                vpd.put("var2", varOList.get(i).getString("area_name"));
                vpd.put("var3", varOList.get(i).getString("point_name"));
                vpd.put("var4", varOList.get(i).getString("point_filed"));
                vpd.put("var5", varOList.get(i).getString("add_time"));
                varList.add(vpd);
            }
            dataMap.put("varList", varList);
            ObjectExcelView erv = new ObjectExcelView();
            mv = new ModelAndView(erv, dataMap);
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

        FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "inspt_point.xls", "inspt_point.xls");

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
            String fileName =  FileUpload.fileUp(file, filePath, "inspt_pointexcel");							//执行上传

            List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);

            for(PageData pds : listPd) {

                pd.put("workshop", pds.getString("var0"));
                pd.put("area_name", pds.getString("var1"));
                pd.put("point_name", pds.getString("var2"));
                pd.put("point_filed", pds.getString("var3"));
                pd.put("add_time", pds.getString("var4"));
                pointService.save(pd);
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
