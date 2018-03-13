
package com.wp.controller.echarts;


import com.wp.controller.base.BaseController;
import com.wp.entity.echarts.Record;
import com.wp.service.echarts.RecordService;
import com.wp.util.PageData;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wp on 2017/12/30.
 */

@Controller
@RequestMapping(value = "/echarts")
public class RecordController extends BaseController {

    String menuUrl = "echarts/record.do"; //菜单地址(权限用)
    @Resource(name="recordService")
    private RecordService recordService;


    /**
     * 进入首页后的默认页面
     * @return
     */
    @RequestMapping(value="/login_default")
    @ResponseBody
    public Object defaultPage() throws Exception{
        //ModelAndView mv = this.getModelAndView();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        //List<String> xlist = new ArrayList<String>();//横坐标轴数据
        List<PageData> pdlist = recordService.listAll(this.getPageData());
//        for(PageData pd : pdlist){
//            xlist.add((String)pd.get("month"));
//        }
//
//
//        List<Long> datalist = new ArrayList<Long>();//图表中显示的数据
//        for(PageData pd : pdlist){
//            datalist.add((Long)pd.get("sum"));
//        }
//
//
//        returnMap.put("xlist", xlist);
//        returnMap.put("datalist", datalist);
//
//        JSONArray jsondata = JSONArray.fromObject(returnMap);

        return pdlist;
    }
}

