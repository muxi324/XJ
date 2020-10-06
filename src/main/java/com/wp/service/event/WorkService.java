package com.wp.service.event;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.entity.eventInfo.Event;
import com.wp.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("workService")
public class WorkService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public List<PageData> list(Page page) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("WorkContentMapper.datalistPage",page);
        return list;
    }
}
