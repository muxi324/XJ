package com.wp.controller.meterinfo;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.entity.echarts.echartsModel;
import com.wp.service.meterinfo.MeterService;
import com.wp.util.*;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value ="/meter")
public class MeterController extends BaseController {
    String menuUrl = "meter/list.do"; //菜单地址(权限用)
    @Resource(name = "meterService")
    private MeterService meterService;

    @RequestMapping(value="/list")
    public ModelAndView getMeterList(Page page) {
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        logBefore(logger,"水表管理");
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
            pd.put("factory_id", FactoryUtil.getFactoryId());
            page.setPd(pd);
            List<PageData> varList = meterService.list(page);
            mv.addObject("user",SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER));
            mv.setViewName("meterinfo/meter");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value ="goAdd")
    public ModelAndView goAdd() {
        logBefore(logger, "去新增水表页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData data = new PageData();
        data.put("factory_id", FactoryUtil.getFactoryId());
        try {
            List<PageData> comList=meterService.findMeterCom(data);
          //  System.out.println(comList+"999999999999");
            mv.addObject("user",SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER));
            mv.setViewName("meterinfo/meter_edit");
            mv.addObject("msg", "save");
            mv.addObject("comList", comList);
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     *  删除
     *
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
            meterService.delete(pd);
            out.write("success");
            out.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
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
        pd.put("add_time",  Tools.date2Str(new Date()));	//添加时间
        pd.put("factory_id",FactoryUtil.getFactoryId());
        String com_id = pd.getString("com_id");   //通过team_id获取team
        PageData meterCompany = new PageData();
        meterCompany.put("com_id", com_id);

        PageData data = meterService.findById(meterCompany);
        if(null!=data){
            String metercom_name = data.getString("factory_name");
            pd.put("metercom_name",metercom_name);
        }

        meterService.save(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     *  去修改水表信息页面
     * @return
     */
    @RequestMapping(value="/goEdit")
    public ModelAndView goEdit(){
        logBefore(logger, "去修改水表页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = meterService.findByMeterId(pd);	//根据ID读取
            pd.put("factory_id", FactoryUtil.getFactoryId());
            List<PageData> comList = meterService.findMeterCom(pd);
            mv.setViewName("meterinfo/meter_edit");
            mv.addObject("msg", "edit");
            mv.addObject("pd", pd);
            mv.addObject("comList",comList);
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
        logBefore(logger, "修改水表信息");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd=this.getPageData();
        String com_id = pd.getString("com_id");   //通过team_id获取team
        PageData meterCompany = new PageData();
        meterCompany.put("com_id", com_id);
        PageData data = meterService.findById(meterCompany);
        String metercom_name = data.getString("factory_name");
        pd.put("metercom_name",metercom_name);
     //   System.out.println(pd+"8888");
        meterService.edit(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     *   得到水表详情
     * @param
     * @return
     */
    @RequestMapping(value="/getWorkContentDetail")
    public ModelAndView getWorkContentDetail(){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        String meter_id = pd.getString("meter_id");
       // System.out.println("9999"+pd);
        List<PageData> list = null;

        try {
            list = meterService.getWorkContent(pd);     // 事件反馈的详情
          //  System.out.println( "---------"+list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String mission_id = null;
        String event_id = null;
        for(PageData map : list){
            mission_id = map.get("mission_id").toString();
            event_id = map.get("event_id").toString();
        }
       // System.out.println(mission_id);
        mv.addObject("varList",list);          // 事件反馈详情
        mv.addObject("mission_id",mission_id);
        mv.addObject("event_id",event_id);
        mv.addObject("meter_id",meter_id);
        mv.setViewName("meterinfo/meterdetail");

        return mv;
    }
    @RequestMapping(value="/view")
    public ModelAndView view(Page page){
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        String biaoshu = pd.getString("meter_id");   //通过team_id获取team
       // System.out.println(biaoshu+"0000000666666666");
        String type = pd.getString("type");
      //  System.out.println(type+"0000000");
        mv.addObject("biaoshu",biaoshu);
        mv.addObject("type",type);
        mv.setViewName("meterinfo/zhexian");
        return mv;
    }
    @RequestMapping(value="/shu")
    public void shu(HttpServletResponse response,HttpServletRequest request) throws Exception {
        String biaoshu = request.getParameter("biaoshu");
        String type = request.getParameter("type");
        JSONObject result = new JSONObject();
        List<PageData> s = meterService.listMeter(biaoshu);
      //  System.out.println(s+"000000");
//        List<PageData> shuModel = s.stream().filter(c -> c.getType().equals("1")).collect(Collectors.toList());
        List<echartsModel> a = new ArrayList<echartsModel>();
        for(PageData i:s){
            a.add(new echartsModel(i.get("time").toString(),i.get("data").toString()));
        }
        if(a.size()<=500){
            result.put("result",a);
        }else{
            while(true){
                if(a.size()>500){
                    a.remove(0);
                }else{
                    break;
                }
            }
            result.put("result",a);
        }
    //    System.out.println(result);
        HttpOutUtil.outData(response,com.alibaba.fastjson.JSONObject.toJSONString(result));
    }

    /* ===============================权限================================== */
    public Map<String, String> getHC(){
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
    }
    /* ===============================权限================================== */
}
