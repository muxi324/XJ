package com.wp.service.querytask;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.util.PageData;
/**
 * Created by wp on 2017/12/20.
 */
@Service("queryTaskService")
public class QueryTaskService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
	*列表
	*/
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("QueryTaskMapper.datalistPage", page);
    }

    /*
     *列表(全部)
     */
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("QueryTaskMapper.listAll", pd);
    }

    /*
    *详情
    */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("QueryTaskMapper.findById", pd);
    }
}
