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
@Service("partsMagService")
public class PartsMagService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
	* 新增
	*/
    public void save(PageData pd)throws Exception{
        dao.save("PartsMagMapper.save", pd);
    }
    /* 修改物资入库当前库存 */
    public void editStock(PageData pd)throws Exception{
        dao.update("PartsMagMapper.editStock", pd);
    }
    /*
   * 通过id获取入库当前库存数据
   */
    public Integer selectStock(PageData pd)throws Exception{
        System.out.println("------------------------------------------");
        System.out.println(pd.getString("material_id"));
        System.out.println("------------------------------------------");
        return (Integer)dao.findForObject("PartsMagMapper.selectStock", pd);
    }
      /* 修改物资出库当前库存 */
    public void editStock1(PageData pd)throws Exception{
        dao.update("PartsMagMapper.editStock1", pd);
    }


    /*
    *列表(全部)dd
    */
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("PartsMagMapper.listAll", pd);
    }

    /*
    * 通过id获取入库数据
    */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("PartsMagMapper.findById", pd);
    }
    /*
  * 通过id获取出库数据
  */
    public PageData findById1(PageData pd)throws Exception{
        return (PageData)dao.findForObject("PartsMagMapper.findById1", pd);
    }

    /*
	* 任务列表
	*/
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("PartsMagMapper.datalistPage", page);
    }
    /*
    * 批量删除
    */
    public void deleteAll(String[] set_ids)throws Exception{
        dao.delete("PartsMagMapper.deleteAll", set_ids);
    }
}
