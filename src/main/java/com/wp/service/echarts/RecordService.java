package com.wp.service.echarts;

import com.wp.dao.DaoSupport;
import com.wp.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wp on 2017/12/31.
 */
@Service("recordService")
public class RecordService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
    *列表(全部)
    */
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("RecordMapper.listAll", pd);
    }
//    全厂每月临时巡检任务
    public List<PageData> listTempTask(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("RecordMapper.listTempTask", pd);
    }
    //    全厂每月维修任务
    public List<PageData> listRepairTask(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("RecordMapper.listRepairTask", pd);
    }


    //本厂异常各类型统计
    public List<PageData> listProblemException(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("RecordMapper.listProblemException", pd);
    }
    public List<PageData> listDangerException(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("RecordMapper.listDangerException", pd);
    }
    public List<PageData> listAlarmException(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("RecordMapper.listAlarmException", pd);
    }


}
