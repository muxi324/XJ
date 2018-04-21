package com.wp.service.databank;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.entity.databank.Workshop;
import com.wp.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wp on 2018/3/14.
 */
@Service("workshopService")
public class WorkshopService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    /*
	* 新增
	*/
    public void save(PageData pd)throws Exception{
        dao.save("WorkshopMapper.save", pd);
    }

    /*
    * 删除
    */
    public void delete(PageData pd)throws Exception{
        dao.delete("WorkshopMapper.delete", pd);
    }

    /*
    * 修改
    */
    public void edit(PageData pd)throws Exception{
        dao.update("WorkshopMapper.edit", pd);
    }
    //   修改图片
    public void editPic(PageData pd)throws Exception{
        dao.update("WorkshopMapper.editpic", pd);
    }
    //    取出所有车间
    public List<Workshop> listWorkshop() throws Exception {
        return (List<Workshop>) dao.findForList("WorkshopMapper.listWorkshop", null);

    }

    /*
    *列表
    */
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("WorkshopMapper.datalistPage", page);
    }

    /*
    *列表(全部)
    */
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("WorkshopMapper.listAll", pd);
    }


    /*
    * 通过id获取数据
    */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("WorkshopMapper.findById", pd);
    }

    /*
    * 批量删除
    */
    public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
        dao.delete("WorkshopMapper.deleteAll", ArrayDATA_IDS);
    }

}
