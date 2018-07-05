package com.wp.service.databank;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.entity.databank.Factory;
import com.wp.entity.databank.Workshop;
import com.wp.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wp on 2018/5/14.
 */
@Service("factoryService")
public class FactoryService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    /*
	* 新增
	*/
    public void save(PageData pd)throws Exception{
        dao.save("FactoryMapper.save", pd);
    }

    /*
    * 删除
    */
    public void delete(PageData pd)throws Exception{
        dao.delete("FactoryMapper.delete", pd);
    }

    /*
    * 修改
    */
    public void edit(PageData pd)throws Exception{
        dao.update("FactoryMapper.edit", pd);
    }
    //   修改图片
    public void editPic(PageData pd)throws Exception{
        dao.update("FactoryMapper.editpic", pd);
    }
    //    取出所有工厂
    public List<Factory> listFactory() throws Exception {
        return (List<Factory>) dao.findForList("FactoryMapper.listFactory", null);

    }

    /*
    *列表
    */
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("FactoryMapper.datalistPage", page);
    }

    /*
    *列表(全部)
    */
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("FactoryMapper.listAll", pd);
    }


    /*
    * 通过id获取数据
    */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("FactoryMapper.findById", pd);
    }

    /*
    * 批量删除
    */
    public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
        dao.delete("FactoryMapper.deleteAll", ArrayDATA_IDS);
    }

    public List<PageData> listAllFac() throws Exception {
        return  (List<PageData>)dao.findForList("FactoryMapper.listAllFac",null);
    }
}
