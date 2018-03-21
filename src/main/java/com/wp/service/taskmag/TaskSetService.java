package com.wp.service.taskmag;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.entity.house.House;
import com.wp.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wp on 2017/12/12.
 */
@Service("taskSetService")
public class TaskSetService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
	* 新增
	*/
    public void save(PageData pd)throws Exception{
        dao.save("TaskSetMapper.save", pd);
    }

    /*
    * 删除
    */
    public void delete(PageData pd)throws Exception{
        dao.delete("TaskSetMapper.delete", pd);
    }
    /*
    * 修改
    */
    public void edit(PageData pd)throws Exception{
        dao.update("TaskSetMapper.edit", pd);
    }

    /*
    *列表(全部)dd
    */
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("TaskSetMapper.listAll", pd);
    }

    /*
    * 通过id获取数据
    */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("TaskSetMapper.findById", pd);
    }

    /*
	* 任务列表
	*/
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("TaskSetMapper.datalistPage", page);
    }
    /*
    * 批量删除
    */
    public void deleteAll(String[] set_ids)throws Exception{
        dao.delete("TaskSetMapper.deleteAll", set_ids);
    }




}

