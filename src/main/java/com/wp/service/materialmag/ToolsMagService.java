package com.wp.service.materialmag;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wp on 2018/3/28.
 */
@Service("toolsMagService")
public class ToolsMagService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
	* 新增
	*/
    public void save(PageData pd)throws Exception{
        dao.save("ToolsMagMapper.save", pd);
    }

    public void firstsave(PageData pd)throws Exception{
        dao.save("ToolsMagMapper.firstsave", pd);
    }
    /* 修改物资入库当前库存 */
    public void editStock(PageData pd)throws Exception{
        dao.update("ToolsMagMapper.editStock", pd);
    }
    /*
   * 通过id获取入库当前库存数据
   */
    public Integer selectStock(PageData pd)throws Exception{
        return (Integer)dao.findForObject("ToolsMagMapper.selectStock", pd);
    }

    /*
   * 删除
   */
    public void delete(PageData pd)throws Exception{
        dao.delete("ToolsMagMapper.delete", pd);
    }

    /*
    *列表(全部)dd
    */
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("ToolsMagMapper.listAll", pd);
    }

    /*
    * 通过id获取入库数据
    */
    public List<PageData> findById(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("ToolsMagMapper.findById", pd);
    }
    /*
  * 通过id获取出库数据
  */
    public List<PageData> findById1(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("ToolsMagMapper.findById1", pd);
    }

    /*
	* 任务列表
	*/
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("ToolsMagMapper.datalistPage", page);
    }
    /*
    * 批量删除
    */
    public void deleteAll(String[] set_ids)throws Exception{
        dao.delete("ToolsMagMapper.deleteAll", set_ids);
    }
}
