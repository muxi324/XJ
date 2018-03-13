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


}
