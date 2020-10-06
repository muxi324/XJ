package com.wp.service.yunxing;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("yunxingService")
public class YunXingService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public List<PageData> listYunxing(Page page)throws Exception{
        return (List<PageData>)dao.findForList("YunxingMapper.xunxinglistPage", page);
    }

    public List<PageData> listAll(PageData page) throws Exception {
        return (List<PageData>)dao.findForList("YunxingMapper.listAll",page);
    }

//    public List<PageData> listAll(PageData pd) {
//    }
}
