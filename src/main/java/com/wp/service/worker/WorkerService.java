package com.wp.service.worker;
import java.util.List;

import javax.annotation.Resource;

import com.wp.entity.worker.Worker;
import org.springframework.stereotype.Service;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.util.PageData;
/**
 * Created by wp on 2017/12/20.
 */
@Service("workerService")
public class WorkerService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    /*
	* 新增
	*/
    public void save(PageData pd)throws Exception{
        dao.save("WorkerMapper.save", pd);
    }

    /*
   * 修改
   */
    public void editWorkshop(PageData pd)throws Exception{
        dao.update("WorkerMapper.updataWorkshop", pd);
    }

    /*
    * 删除
    */
    public void delete(PageData pd)throws Exception{
        dao.delete("WorkerMapper.delete", pd);
    }

    /*
    * 修改
    */
    public void edit(PageData pd)throws Exception{
        dao.update("WorkerMapper.edit", pd);
    }
//   修改图片
    public void editPic(PageData pd)throws Exception{
        dao.update("WorkerMapper.editpic", pd);
    }

    /*
    *列表
    */
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("WorkerMapper.datalistPage", page);
    }

    /*
    *列表(全部)
    */
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("WorkerMapper.listAll", pd);
    }

    //    取出所有班组
    public List<Worker> listTeam() throws Exception {
        return (List<Worker>) dao.findForList("WorkerMapper.listTeam", null);

    }

    /*
    *班组员工列表
    */
    public List<PageData> listWorker(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("WorkerMapper.listWorker", pd);
    }

    /*
  *班组员工电话列表
  */
    public List<PageData> findPhoneByName(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("WorkerMapper.listPhone", pd);
    }

    /*
    * 通过id获取数据
    */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("WorkerMapper.findById", pd);
    }

    /*
    * 批量删除
    */
    public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
        dao.delete("WorkerMapper.deleteAll", ArrayDATA_IDS);
    }
}
