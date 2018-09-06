package com.wp.service.exception;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("exceptionService")
public class ExceptionService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public List<PageData> listException(Page page)throws Exception{
        return (List<PageData>)dao.findForList("ExceptionMapper.listException", page);
    }

    public List<PageData> listAllException(Page page)throws Exception{
        return (List<PageData>)dao.findForList("ExceptionMapper.datalistPage", page);
    }

    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("ExceptionMapper.findExceptionById", pd);
    }

    public void editState(PageData pd)throws Exception{
        dao.update("ExceptionMapper.editState", pd);
    }
}
