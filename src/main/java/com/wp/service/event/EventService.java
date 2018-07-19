package com.wp.service.event;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("eventService")
public class EventService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public List<PageData> list(Page page) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("EventMapper.datalistPage",page);
        return list;
    }

    public void save(PageData pd)throws Exception{
        dao.save("EventMapper.save", pd);
    }

    /*
   *列表(全部)dd
   */
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("EventMapper.listAll", pd);
    }


    public void delete(PageData pd)throws Exception{
        dao.delete("EventMapper.delete", pd);
    }

    /*
    * 批量删除
    */
    public void deleteAll(String[] event_ids)throws Exception{
        dao.delete("EventMapper.deleteAll", event_ids);
    }

    public PageData getEventById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("EventMapper.getEventById",pd);
    }

    public void saveWorkContent(String content, String eventName) throws Exception {
        Map<String,String> paraMap = new HashMap();
        paraMap.put("content",content);
        paraMap.put("eventName",eventName);
        dao.update("EventMapper.updateWorkContent",paraMap);
    }

    public String getAdditionByName(String eventName) throws Exception {
        return (String) dao.findForObject("EventMapper.getAdditionByName",eventName);
    }

    public String getEventByName(String event_name) throws Exception {
        return (String) dao.findForObject("EventMapper.getEventByName",event_name);
    }

    public void update(PageData pd) throws Exception {
        dao.update("EventMapper.update",pd);
    }

    public List<PageData> listByIds(List<String> list) throws Exception {
          return  (List<PageData>)dao.findForList("EventMapper.listByIds",list);
    }

    public PageData getEventByNameForPageData(String eventName) throws Exception {
        return (PageData) dao.findForObject("EventMapper.getEventByNameForPageData",eventName);
    }

    public List<PageData> list1(Page page) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("EventMapper.list1",page);
        return list;
    }
}
