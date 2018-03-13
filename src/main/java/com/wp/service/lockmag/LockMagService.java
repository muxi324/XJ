package com.wp.service.lockmag;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wp on 2017/12/21.
 */
@Service("lockMagService")
public class LockMagService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
  *列表
  */
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("LockMagMapper.datalistPage", page);
    }
    /*
     *列表(全部)
     */
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("LockMagMapper.listAll", pd);
    }

    /*
   *详情
   */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("LockMagMapper.findById", pd);
    }
    /*
  *详情
  */
    public PageData findByFlow(PageData pd)throws Exception{
        return (PageData)dao.findForObject("LockMagMapper.findByFlow", pd);
    }


}
