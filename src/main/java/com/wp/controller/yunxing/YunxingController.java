package com.wp.controller.yunxing;

import com.wp.controller.base.BaseController;
import com.wp.entity.Page;
import com.wp.service.event.EventService;
import com.wp.service.taskmag.TaskMagService;
import com.wp.service.yunxing.YunXingService;
import com.wp.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value="/yunxing")
public class YunxingController extends BaseController {

    String menuUrl = "list.do"; //菜单地址(权限用)
    @Resource(name="yunxingService")
    private YunXingService yunXingService;

    @Resource(name = "eventService")
    private EventService eventService;

    @Resource(name="taskMagService")
    private TaskMagService taskMagService;

    @RequestMapping("/list2")
    public ModelAndView getExceptionInfo(Page page) {
        logBefore(logger, "查看运行情况");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
//            String  enquiry = pd.getString("enquiry");

//            if(null != enquiry && !"".equals(enquiry)){
//                pd.put("enquiry", enquiry.trim());
//            }else {
//                pd.put("enquiry", "");
//            }
//            String sendTimeStart = pd.getString("reportTimeStart");
//            String sendTimeEnd = pd.getString("reportTimeEnd");

//            if(sendTimeStart != null && !"".equals(sendTimeStart)){
//                sendTimeStart = sendTimeStart+" 00:00:00";
//                pd.put("reportTimeStart", sendTimeStart);
//            }
//            if(sendTimeEnd != null && !"".equals(sendTimeEnd)){
//                sendTimeEnd = sendTimeEnd+" 00:00:00";
//                pd.put("reportTimeEnd", sendTimeEnd);
//            }
//            String  level = pd.getString("level");
//            pd.put("level", level);
            String loginUserName = FactoryUtil.getLoginUserName();
            if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
                pd.put("factory_id",FactoryUtil.getFactoryId());
                pd.put("workshop_id",FactoryUtil.getWorkshopId());
            }
            page.setPd(pd);
            List<PageData> YunXingList = yunXingService.listYunxing(page);
            mv.setViewName("yunxing/yunxingInfo");
//            System.out.println(page.getPageStr());
            mv.addObject("yunxingList", YunXingList);
            mv.addObject("pd", pd);
            mv.addObject("fenye", page);

//            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    @RequestMapping("/list")
    public ModelAndView getYunxingInfo(Page page) throws Exception {
        logBefore(logger, "查看运行情况");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String eventId = pd.getString("eventId");
      //  System.out.println("事件id:"+eventId);

        String additions = eventService.getAdditionById(eventId);  //工作内容
        JSONArray workArray;
        List<String> workcontentList = new ArrayList<String>();

        //将工作内容的大json字符串转换成list
        if (additions == null || additions.equals("")) {
            workArray = new JSONArray();
        } else {
            workArray = JSONArray.fromObject(additions);
        }
        for (int i = 0; i<workArray.size(); i++) {
            JSONObject jsonObject = workArray.getJSONObject(i);
            String contentName = jsonObject.getString("work_name");      //获取work_name;
            workcontentList.add(contentName);
        }



        try {

            String loginUserName = FactoryUtil.getLoginUserName();
            if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
                pd.put("factory_id",FactoryUtil.getFactoryId());
                pd.put("workshop_id",FactoryUtil.getWorkshopId());
            }
            page.setPd(pd);
//            List<PageData> YunXingList = yunXingService.listYunxing(page);
            List<PageData> event_feedbacklist = taskMagService.getEventYunxing(page);     // 事件反馈的详情
            List<PageData> list = null;
            String time ="time";
            Map<String, List<PageData>> resultMap = new HashMap<String, List<PageData>>();
            resultMap = groupYunxing.get(event_feedbacklist,time);
            mv.setViewName("yunxing/EventYunxingInfo");
//            mv.addObject("yunxingList", YunXingList);
//            mv.addObject("event_feedbacklist",event_feedbacklist);
            mv.addObject("contentList",workcontentList);  //表头
            mv.addObject("resultmap",resultMap);
            mv.addObject("pd", pd);

//            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }
    /*
     * 导出到excel  原版
     * @return
     */
    @RequestMapping(value="/excel1")
    public ModelAndView exportExcel(){
        logBefore(logger, "导出运行情况到excel");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try{

            String loginUserName = FactoryUtil.getLoginUserName();
            if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
                pd.put("factory_id",FactoryUtil.getFactoryId());
                pd.put("workshop_id",FactoryUtil.getWorkshopId());
                pd.put("team_id",FactoryUtil.getTeamId());
            }

            //检索条件===
            Map<String,Object> dataMap = new HashMap<String,Object>();

            //文件名
            String filename = "医院污水运行记录表-"+new SimpleDateFormat("yyyyMM").format(new Date())+".xls";
            dataMap.put("filename",filename);

            //标题
            String headerName = "医院污水运行记录表(_____年_____日)";

            dataMap.put("headerName",headerName);
            List<PageData> varOList = yunXingService.listAll(pd);  //运行情况
            List<PageData> varList = new ArrayList<PageData>();

//            PageData shijian = new PageData();
//            shijian.put("var5", "_____年");
//            shijian.put("var6", "_____日");
//            varList.add(shijian);

            //文件列表头
            List<String> titles = new ArrayList<String>();
            titles.add("日期");
            titles.add("值班时间");
            titles.add("排放指数");

            titles.add("日排放吨数");
            titles.add("余氯量");
            titles.add("运行情况");
            dataMap.put("titles", titles);




            for(int i=0; i < varOList.size(); i++){

                PageData vpd = new PageData();

                vpd.put("var1", varOList.get(i).getString("Date"));
                vpd.put("var2", varOList.get(i).getString("zhiBanTime"));
                vpd.put("var3", varOList.get(i).getString("paiFangZhiShu"));

                vpd.put("var4", varOList.get(i).getString("riPaiFangDunShu"));
                vpd.put("var5", varOList.get(i).getString("YuLvLiang"));
                vpd.put("var6", varOList.get(i).getString("YunXingQK"));


                varList.add(vpd);
            }
            PageData qianzi = new PageData();
            qianzi.put("var3", "盖章");
            qianzi.put("var4", "___________________");
            qianzi.put("var5", "负责人");
            qianzi.put("var6", "___________________");
            varList.add(qianzi);

            dataMap.put("varList", varList);
            ObjectExcelView erv = new ObjectExcelView();
            mv = new ModelAndView(erv,dataMap);
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }


    /*
     * 导出到excel  后来的
     * @return
     */
    @RequestMapping(value="/excel")
    public ModelAndView exporttoExcel(Page page) throws Exception {
        logBefore(logger, "导出运行情况到excel");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}

        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String eventId = pd.getString("eventId");
      //  System.out.println("下载的是事件id"+eventId);
        String additions = eventService.getAdditionById(eventId);  //事件表里的大json工作内容
        JSONArray workArray;
        List<String> workcontentList = new ArrayList<String>();

        //将工作内容的大json字符串转换成list
        if (additions == null || additions.equals("")) {
            workArray = new JSONArray();
        } else {
            workArray = JSONArray.fromObject(additions);
        }
        for (int i = 0; i<workArray.size(); i++) {
            JSONObject jsonObject = workArray.getJSONObject(i);
            String contentName = jsonObject.getString("work_name");      //获取work_name;
            workcontentList.add(contentName);     //存储着该事件所有的work_name
        }

        try{

            String loginUserName = FactoryUtil.getLoginUserName();
            if (StringUtils.isNotEmpty(loginUserName) && !loginUserName.equals("admin")) {
                pd.put("factory_id",FactoryUtil.getFactoryId());
                pd.put("workshop_id",FactoryUtil.getWorkshopId());
                pd.put("team_id",FactoryUtil.getTeamId());
            }

            //检索条件===
            Map<String,Object> dataMap = new HashMap<String,Object>();

            //文件名
            String filename = "医院污水运行记录表-"+new SimpleDateFormat("yyyyMM").format(new Date())+".xls";
            dataMap.put("filename",filename);

            //标题
            String headerName = "医院污水运行记录表(_____年_____月)";

            dataMap.put("headerName",headerName);
            List<PageData> varOList = yunXingService.listAll(pd);  //运行情况
            List<PageData> varList = new ArrayList<PageData>();

//            PageData shijian = new PageData();
//            shijian.put("var5", "_____年");
//            shijian.put("var6", "_____日");
//            varList.add(shijian);

            //文件列表头
            List<String> titles = new ArrayList<String>();
            titles.add("日期");
            titles.add("值班时间");   //前两个固定
            for (String title: workcontentList) {
                titles.add(title);        //后面的表头不固定
            }

            dataMap.put("titles", titles);


            // 事件反馈的详情(查询mission_feedback表)
            page.setPd(pd);
            List<PageData> event_feedbacklist = taskMagService.getEventYunxing(page);
            List<PageData> list = null;
            String time ="time";
            Map<String, List<PageData>> resultMap = new HashMap<String, List<PageData>>();
//            // 将time相同的包含mission_feedback表中行数据的PageData提取出来，以time作为key值存储在map中
            resultMap = groupYunxing.get(event_feedbacklist,time);
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
//            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//            }

            for (Map.Entry<String,List<PageData>> result : resultMap.entrySet()) {

                PageData hang = new PageData();
            //    System.out.print("第1列值是：");
            //    System.out.println(result.getKey().split("_")[0]);
             //   System.out.print("第2列值是：");
             //   System.out.println(result.getKey());

                hang.put("var1",result.getKey().split("_")[0]);
                hang.put("var2",result.getKey().split("_")[1]);
                int i =3;
                List<PageData> value =result.getValue();      //包含好几个相同时间行的list
                for (PageData s: value) {
                    String x = "var"+i;
                 //   System.out.print("现在是第"+x+"列:");
                    hang.put(x,s.getString("data"));
                //    System.out.println(s.getString("data"));
                    i++;
                }

//                hang.clear(); //清空行容器
                varList.add(hang);
            }

            PageData qianzi = new PageData();
            qianzi.put("var1", "单位公章：_____________负责人签字：____________ ___月___日");
            varList.add(qianzi);
            dataMap.put("varList", varList);
            ObjectExcelView erv = new ObjectExcelView();
            mv = new ModelAndView(erv,dataMap);
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

}
