package com.wp.service.event;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("eventService")
public class EventService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public List<PageData> list(Page page) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("EventMapper.listEvent",page);
        return list;
    }
}
