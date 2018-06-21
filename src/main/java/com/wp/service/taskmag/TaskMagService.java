package com.wp.service.taskmag;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.service.house.HouseService;
import com.wp.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wp on 2017/12/22.
 */
@Service("taskMagService")
public class TaskMagService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Resource(name = "HouseService")
    private HouseService houseService;
    /*
	*列表
	*/
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("TaskMagMapper.datalistPage", page);
    }

    /*
     *列表(全部)
     */
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("TaskMagMapper.listAll", pd);
    }
    /*
   *详情
   */
    public List<PageData> detail(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("TaskMagMapper.detail", pd);
    }

    /*
   *审核
   */
    public void check(PageData pd)throws Exception{
            String mission_condition1 = (String)pd.get("mission_condition1");
            pd.put("mission_condition",mission_condition1);
            if("5".equals(mission_condition1)){
                dao.update("TaskMagMapper.save", pd);
            }else if("6".equals(mission_condition1)){
                dao.update("TaskMagMapper.save", pd);
                houseService.editStatus(pd);
            }

    }

    /*
   * 拒单处理
   */
    public void refuse(PageData pd)throws Exception{
        dao.update("TaskMagMapper.refuseAudit", pd);
    }
    /*
  * 通过id获取数据
  */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("TaskMagMapper.findById", pd);
    }

    public PageData findPicById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("TaskMagMapper.findPicById", pd);
    }


    public List<PageData> getWorkContent(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("TaskMagMapper.getWorkContent",pd);
    }

    public void auditTask(PageData pd) throws Exception {
        dao.update("TaskMagMapper.auditTask",pd);
    }
}
