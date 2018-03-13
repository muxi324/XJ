package com.wp.controller.house;


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

import com.wp.entity.house.House;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wp.util.DateUtil;
import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.service.house.HouseService;
import com.wp.service.system.role.RoleService;
import com.wp.util.AppUtil;
import com.wp.util.Const;
import com.wp.util.DelAllFile;
import com.wp.util.FileDownload;
import com.wp.util.FileUpload;
import com.wp.util.Jurisdiction;
import com.wp.util.ObjectExcelRead;
import com.wp.util.ObjectExcelView;
import com.wp.util.PageData;
import com.wp.util.PathUtil;
import com.wp.util.Tools;

/**
 * Created by wp on 2017/12/12.
 */
@Controller
@RequestMapping(value="/house")
public class HouseController extends BaseController{
    String menuUrl = "house/list.do"; //菜单地址(权限用)
    @Resource(name = "HouseService")
    private HouseService houseService;
    @Resource(name="roleService")
    private RoleService roleService;
    /**
     * 保存房源
     */
    @RequestMapping(value="/save")
    public ModelAndView save(PrintWriter out) throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        pd.put("house_id", "");	//ID
        pd.put("add_time",  Tools.date2Str(new Date()));	//添加时间
        pd.put("set_condition", "1");		//状态为1，待安装
        houseService.save(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }
    
    @RequestMapping(value="/uploade")
    @ResponseBody
    public String uploade(HttpServletRequest request,
    		@RequestParam(value = "file", required = false) MultipartFile file) {
    	String  ffile = DateUtil.getDays(), fileName = "";
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG+ ffile;		//文件上传路径
			fileName = FileUpload.fileUp(file, filePath, this.get32UUID());				//执行上传
		}
    	return ffile + "/" + fileName;
    }

    /**
     * 修改
     */
    @RequestMapping(value="/edit")
    public ModelAndView edit(PrintWriter out) throws Exception{
        logBefore(logger, "修改house");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        
        houseService.edit(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }
    /**
     * 删除房源
     */
    @RequestMapping(value="/delete")
    public void delete(PrintWriter out){
        logBefore(logger, "删除房源");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            houseService.delete(pd);
            out.write("success");
            out.close();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
    }


    /**
     * 列表
     */
    @RequestMapping(value="/list")
    public ModelAndView list(Page page){
        logBefore(logger, "列表house");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            String  enquiry = pd.getString("enquiry");
            String  set_condition = pd.getString("status");
            if(null != enquiry && !"".equals(enquiry)){
                pd.put("enquiry", enquiry.trim());
            }else {
            	pd.put("enquiry", "");
            }
            pd.put("set_condition", set_condition);

            page.setPd(pd);
            List<PageData>	houseList = houseService.listPdPageHouse(page);	//列出house列表
            mv.setViewName("house/house");
            mv.addObject("houseList", houseList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去新增房源页面
     */
    @RequestMapping(value="/goAdd")
    public ModelAndView goAddU(){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<House> lockTypeList = houseService.listLockType();			//列出所有锁的类型
            List<House> lockModelList = houseService.listLockModel();			//列出所有锁的型号
            mv.addObject("lockTypeList", lockTypeList);
            mv.addObject("lockModelList", lockModelList);
            mv.setViewName("house/house_edit");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }
    /**
     * 去修改房源页面
     */
    @RequestMapping(value="/goEdit")
    public ModelAndView goEdit(){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = houseService.findById(pd);								//根据ID读取
            mv.setViewName("house/house_edit");
            mv.addObject("msg", "edit");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }
    /**
     * 下发任务
     */
    @RequestMapping(value="/sendtask")
    public ModelAndView goSendTask() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        mv.setViewName("house/sendtask");
        mv.addObject("pd", pd);
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
            String house_ids = pd.getString("house_ids");

            if(null != house_ids && !"".equals(house_ids)){
                String Arrayhouse_ids[] = house_ids.split(",");
                if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){houseService.deleteAll(Arrayhouse_ids);}
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
	 * 导出房源信息到EXCEL
	 * @return
	 */
    @RequestMapping(value="/excel")
    public ModelAndView exportExcel(){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try{
            if(Jurisdiction.buttonJurisdiction(menuUrl, "cha")){
                //检索条件===
            	String  enquiry = URLDecoder.decode(pd.getString("enquiry"),"UTF-8");
                String  set_condition = pd.getString("status");
                if(null != enquiry && !"".equals(enquiry)){
                    pd.put("enquiry", enquiry.trim());
                }else {
                	pd.put("enquiry", "");
                }
                pd.put("set_condition", set_condition);

                //检索条件===

                Map<String,Object> dataMap = new HashMap<String,Object>();
                List<String> titles = new ArrayList<String>();

                titles.add("房源编号"); 		//1
                titles.add("地址");  		//2
                titles.add("房源联系人");			//3
                titles.add("电话");			//4
                titles.add("锁的类型");			//5
                titles.add("锁的型号");			//6
                titles.add("门的类型");		//7
                /*titles.add("经度");	//8
                titles.add("纬度");	//9
                titles.add("门锁照片");	//10*/
                titles.add("房源状态");	//11

                dataMap.put("titles", titles);

                List<PageData> houseList = houseService.listAllHouse(pd);
                List<PageData> varList = new ArrayList<PageData>();
                for(int i=0;i<houseList.size();i++){
                    PageData vpd = new PageData();
                    vpd.put("var1", houseList.get(i).getString("house_id"));		//1
                    vpd.put("var2", houseList.get(i).getString("house_address"));		//2
                    vpd.put("var3", houseList.get(i).getString("house_owner"));			//3
                    vpd.put("var4", houseList.get(i).getString("owner_phone"));	//4
                    vpd.put("var5", houseList.get(i).getString("lock_type"));		//5
                    vpd.put("var6", houseList.get(i).getString("lock_model"));		//6
                    vpd.put("var7", houseList.get(i).getString("door_type"));	//7
                  /*  vpd.put("var8", houseList.get(i).getString("longitude"));			//8
                    vpd.put("var9", houseList.get(i).getString("latitude"));			//9
                    vpd.put("var10", houseList.get(i).getString("lock_pic"));	*/		//10
                    vpd.put("var8", houseList.get(i).getString("set_condition"));		//11

                    varList.add(vpd);
                }
                dataMap.put("varList", varList);
                ObjectExcelView erv = new ObjectExcelView();					//执行excel操作
                mv = new ModelAndView(erv,dataMap);
            }
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
        mv.setViewName("house/uploadexcel");
        return mv;
    }

    /**
     * 下载模版
     */
    @RequestMapping(value="/downExcel")
    public void downExcel(HttpServletResponse response)throws Exception{

        FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Houses.xls", "Houses.xls");

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
			String fileName =  FileUpload.fileUp(file, filePath, "houseexcel");							//执行上传
			
			List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);
			
			for(PageData pds : listPd) {

				pd.put("house_address", pds.getString("var0"));
				pd.put("house_owner", pds.getString("var1"));
				pd.put("owner_phone", pds.getString("var2"));
				pd.put("lock_code", pds.getString("var3"));
				pd.put("lock_type", pds.getString("var4"));
				pd.put("lock_model", pds.getString("var5"));
				pd.put("door_type", pds.getString("var6"));
				pd.put("lock_pic", pds.getString("var7"));
				pd.put("longitude", pds.getString("var8"));
				pd.put("latitude", pds.getString("var9"));
                pd.put("set_condition", "1");
                pd.put("add_time", new Date());
				houseService.save(pd);
			}
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
		return mv;
    }
    
    
  //删除图片
  	@RequestMapping(value="/deltp")
  	public void deltp(PrintWriter out) {
  		logBefore(logger, "删除图片");
  		try{
  			PageData pd = new PageData();
  			pd = this.getPageData();
  			String house_id = pd.getString("house_id");
  			String lock_pic = pd.getString("lock_pic");													 		//图片路径
  			DelAllFile.delFolder(PathUtil.getClasspath()+ Const.FILEPATHIMG + lock_pic); 	//删除图片
  			pd.put("lock_pic", "");
  			if(house_id != null){
  				houseService.editPic(pd);														//删除数据中图片数据
  			}	
  			out.write("success");
  			out.close();
  		}catch(Exception e){
  			logger.error(e.toString(), e);
  		}
  	}


    @InitBinder
    public void initBinder(WebDataBinder binder){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
    }
    /* ===============================权限================================== */
    public Map<String, String> getHC(){
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
    }
	/* ===============================权限================================== */







}
