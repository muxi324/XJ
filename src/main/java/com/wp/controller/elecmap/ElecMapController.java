package com.wp.controller.elecmap;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.entity.map.MapPoint;
import com.wp.service.elecmap.ElecMapService;
import com.wp.service.system.role.RoleService;
import com.wp.util.Const;
import com.wp.util.FactoryUtil;
import com.wp.util.PageData;
import com.wp.util.StringUtil;
import net.sf.json.JSONObject;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wp on 2017/12/25.
 */
@Controller
@RequestMapping(value="/elecmap")
public class ElecMapController extends BaseController {
    String menuUrl = "elecmap/map.do"; //菜单地址(权限用)
    @Resource(name="elecMapService")
    private ElecMapService elecMapService;
    @Resource(name="roleService")
    private RoleService roleService;

    /**
     * 地图页面
     */
    @RequestMapping(value="/map")
    public ModelAndView map() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        mv.setViewName("elecmap/elecmap");
        mv.addObject("pd", pd);
        return mv;
    }
    /**
     * 所有安装员工位置
     */
    @RequestMapping(value="/listW")
    public ModelAndView listW(Page page){
        logBefore(logger, "员工列表");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
           /* String  enquiry = pd.getString("enquiry");
            if(null != enquiry && !"".equals(enquiry)){
                pd.put("enquiry", enquiry.trim());
            }else {
                pd.put("enquiry", "");
            }*/
            page.setPd(pd);
            List<PageData> varList = elecMapService.listW(page);	//列出${objectName}列表
            mv.setViewName("elecmap/elecmap");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 所有已安装房源位置
     */
    @RequestMapping(value="/listH")
    public ModelAndView listH(Page page){
        logBefore(logger, "房源列表");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
           /* String  enquiry = pd.getString("enquiry");
            if(null != enquiry && !"".equals(enquiry)){
                pd.put("enquiry", enquiry.trim());
            }else {
                pd.put("enquiry", "");
            }*/
            page.setPd(pd);
            List<PageData> varList = elecMapService.listH(page);	//列出${objectName}列表
            mv.setViewName("elecmap/elecmap");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }


    @RequestMapping(value = "/detailPath")
    public ModelAndView getDetailPath() {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        String workerId = pd.getString("workerId");
        List<MapPoint> pointList = null;
        try {
            pointList = elecMapService.getPointById(workerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //临时验证，以后连数据库取数
        //List<MapPoint> pointList = new ArrayList<MapPoint>();
/*        if (null != workerId && workerId.equals("1")) {
            MapPoint p1 = new MapPoint("116.350658","39.938285");
            MapPoint p2 = new MapPoint("116.386446","39.939281");
            MapPoint p3 = new MapPoint("116.389034","39.913828");
            MapPoint p4 = new MapPoint("116.442501","39.914603");
            pointList.add(p1);
            pointList.add(p2);
            pointList.add(p3);
            pointList.add(p4);
        }*/
        mv.setViewName("elecmap/detailPath");
        mv.addObject("pointList",pointList);
        mv.addObject("pd", pd);
        mv.addObject(Const.SESSION_QX,this.getHC());
        return mv;
    }

    @RequestMapping(value = "/errorDetail")
    public ModelAndView getErrorDetail() {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        String errorId = pd.getString("errorId");
        String errInfo = "还未获取到故障信息";
        String errorTime = "未知时间";
        String errorAdvice = "无";
        if (null != errorId && errorId.equals("1")) {
            errInfo = "车间温度过高";
            errorTime = "2018-01-02 18:08:56";
            errorAdvice = "开启通风设备";
        }
        mv.setViewName("elecmap/errorDetail");
        mv.addObject("errInfo",errInfo);
        mv.addObject("errorTime",errorTime);
        mv.addObject("errorAdvice",errorAdvice);
        mv.addObject("pd", pd);
        mv.addObject(Const.SESSION_QX,this.getHC());
        return mv;
    }


    @RequestMapping(value = "/workerPosition")
    @ResponseBody
    public Object getWorkerPosition(Page page){
        List<PageData> wokerList = null;
        try {
            PageData pd = new PageData();
            String loginUserName = FactoryUtil.getLoginUserName();
            if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
                pd.put("factory_id",FactoryUtil.getFactoryId());
            }
            page.setPd(pd);
            wokerList = elecMapService.listW(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wokerList;
    }

    //异常位置
    @RequestMapping(value = "/exceptionPosition")
    @ResponseBody
    public Object getExceptionPosition(Page page){
        List<PageData> exceptionList = null;
        try {
            exceptionList = elecMapService.listException(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exceptionList;
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
