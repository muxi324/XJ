package com.wp.service.databank;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wp on 2018/3/16.
 */
@Service("areaService")
public class AreaService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;
    /*
	* 新增
	*/
    public void save(PageData pd)throws Exception{
        dao.save("AreaMapper.save", pd);
    }

    /*
    * 删除
    */
    public void delete(PageData pd)throws Exception{
        dao.delete("AreaMapper.delete", pd);
    }

    /*
    * 修改
    */
    public void edit(PageData pd)throws Exception{
        dao.update("AreaMapper.edit", pd);
    }


    /*
    *列表
    */
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("AreaMapper.datalistPage", page);
    }

    /*
    *列表(全部)
    */
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("AreaMapper.listAll", pd);
    }


    /*
    * 通过id获取数据
    */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("AreaMapper.findById", pd);
    }

    /*
    * 批量删除
    */
    public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
        dao.delete("AreaMapper.deleteAll", ArrayDATA_IDS);
    }
}
