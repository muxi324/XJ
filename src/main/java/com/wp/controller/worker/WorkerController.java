package com.wp.controller.worker;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wp.entity.databank.Workshop;
import com.wp.service.databank.WorkshopService;
import com.wp.service.system.role.RoleService;
import com.wp.service.system.user.UserService;
import com.wp.service.worker.WorkerService;
import com.wp.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;


/**
 * Created by wp on 2017/12/20.
 */
@Controller
@RequestMapping(value="/worker")
public class WorkerController extends BaseController {
    String menuUrl = "worker/list.do"; //菜单地址(权限用)
    @Resource(name="workerService")
    private WorkerService workerService;
    @Resource(name="workshopService")
    private WorkshopService workshopService;
    @Resource(name="roleService")
    private RoleService roleService;
    @Resource(name="userService")
    private UserService userService;

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
        String post = pd.getString("post");
        String p = "车间主任";  //如果职位是车间主任则将信息保存在workshop表中
        if(post.equals(p)){
            workerService.editWorkshop(pd);
        }

        //添加sysuser字段
      /*  PageData pageData = new PageData();
        pageData.put("USERNAME",pd.getString("name"));
        pageData.put("NAME",pd.getString("name"));
        pageData.put("USER_ID", this.get32UUID());	//ID
        pageData.put("RIGHTS", "");					//权限
        pageData.put("LAST_LOGIN", "");				//最后登录时间
        pageData.put("IP", "");						//IP
        pageData.put("STATUS", "0");					//状态
        pageData.put("SKIN", "default");
        pageData.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("name"), "1"));
        pageData.put("PHONE",pd.getString("phone"));
        pageData.put("NUMBER","");
        pageData.put("EMAIL","");
        pageData.put("BZ","");
        pageData.put("workshop",pd.getString("workshop"));
        userService.saveU(pageData);*/
        pd.put("factory_id",FactoryUtil.getFactoryId());
        workerService.save(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }
//    上传图片
    @RequestMapping(value="/uploade")
    @ResponseBody
    public String uploade(HttpServletRequest request,
                          @RequestParam(value = "file", required = false) MultipartFile file) {
        String  ffile = DateUtil.getDays(), fileName = "";
        if (null != file && !file.isEmpty()) {
            String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG1 + ffile;		//文件上传路径
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
            String head_pic = pd.getString("head_pic");													 		//图片路径
            DelAllFile.delFolder(PathUtil.getClasspath()+ Const.FILEPATHIMG1 + head_pic); 	//删除图片
            pd.put("head_pic", "");
            if(id != null){
                workerService.editPic(pd);														//删除数据中图片数据
            }
            out.write("success");
            out.close();
        }catch(Exception e){
            logger.error(e.toString(), e);
        }
    }


    /**
     * 删除
     */
    @RequestMapping(value="/delete")
    public void delete(PrintWriter out){
        logBefore(logger, "删除员工");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            workerService.delete(pd);
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
        logBefore(logger, "修改员工信息");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        workerService.edit(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }
    /**
     * 列表
     */
    @RequestMapping(value="/list")
    public ModelAndView list(Page page){
        logBefore(logger, "员工列表");
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
            String loginUserName = FactoryUtil.getLoginUserName();
            if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
                pd.put("factory_id",FactoryUtil.getFactoryId());
            }
            page.setPd(pd);
            List<PageData>	varList = workerService.list(page);	//列出${objectName}列表
            mv.setViewName("worker/worker_list");
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
        logBefore(logger, "去新增员工页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<Workshop> workshopList = workshopService.listWorkshop();
            mv.addObject("workshopList",workshopList);
            mv.setViewName("worker/worker_edit");
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
        logBefore(logger, "去修改员工页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = workerService.findById(pd);	//根据ID读取
            mv.addObject("pd", pd);
            List<Workshop> workshopList = workshopService.listWorkshop();
            mv.addObject("workshopList",workshopList);
            mv.setViewName("worker/worker_edit");
            mv.addObject("msg", "edit");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }
    /**
     * 判断用户名是否存在
     */
    @RequestMapping(value="/hasW")
    @ResponseBody
    public Object hasW(){
        Map<String,String> map = new HashMap<String,String>();
        String errInfo = "success";
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            if(workerService.findById(pd) != null){
                errInfo = "error";
            }
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);				//返回结果
        return AppUtil.returnObject(new PageData(), map);
    }
    /**
     * 批量删除
     */
    @RequestMapping(value="/deleteAll")
    @ResponseBody
    public Object deleteAll() {
        logBefore(logger, "批量删除员工");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
        PageData pd = new PageData();
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String DATA_IDS = pd.getString("DATA_IDS");
            if(null != DATA_IDS && !"".equals(DATA_IDS)){
                String ArrayDATA_IDS[] = DATA_IDS.split(",");
                workerService.deleteAll(ArrayDATA_IDS);
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
        logBefore(logger, "导出员工到excel");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {return null;}
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try{
                Map<String, Object> dataMap = new HashMap<String, Object>();
                List<String> titles = new ArrayList<String>();
                titles.add("姓名");    //${var_index+1}
                titles.add("手机号");
                titles.add("班组");
                titles.add("职位");
                titles.add("工种");
                titles.add("所属车间");
                dataMap.put("titles", titles);
                List<PageData> varOList = workerService.listAll(pd);
                List<PageData> varList = new ArrayList<PageData>();
                for (int i = 0; i < varOList.size(); i++) {
                    PageData vpd = new PageData();
                    vpd.put("var1", varOList.get(i).getString("name"));        //1
                    vpd.put("var2", varOList.get(i).getString("phone"));
                    vpd.put("var3", varOList.get(i).getString("team"));
                    vpd.put("var4", varOList.get(i).getString("post"));
                    vpd.put("var5", varOList.get(i).getString("work_type"));
                    vpd.put("var6", varOList.get(i).getString("workshop"));
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

        FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Workers.xls", "Workers.xls");

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
            String fileName =  FileUpload.fileUp(file, filePath, "workerexcel");							//执行上传

            List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);

            for(PageData pds : listPd) {

                pd.put("name", pds.getString("var0"));
                pd.put("phone", pds.getString("var1"));
                pd.put("post", pds.getString("var2"));
                pd.put("team", pds.getString("var3"));
                pd.put("work_type", pds.getString("var4"));
               // pd.put("head_pic", pds.getString("var5"));
                pd.put("workshop", pds.getString("var6"));
                workerService.save(pd);
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
