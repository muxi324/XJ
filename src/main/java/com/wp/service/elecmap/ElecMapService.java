package com.wp.service.elecmap;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.entity.map.MapPoint;
import com.wp.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wp on 2017/12/25.
 */
@Service("elecMapService")
public class ElecMapService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
   * 通过员工获取数据
   */
    public PageData findByW(PageData pd)throws Exception{
        return (PageData)dao.findForObject("ElecMapMapper.findByW", pd);
    }

    /*
  *员工列表
  */
    public List<PageData> listW(Page page)throws Exception{
        return (List<PageData>)dao.findForList("ElecMapMapper.datalistWorker", page);
    }

    public List<PageData> listException(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("ElecMapMapper.findException",pd);
    }


    public List<MapPoint> getPointById(String workerId) throws Exception {
        return (List<MapPoint>) dao.findForList("ElecMapMapper.getPointById",workerId);
    }
}
