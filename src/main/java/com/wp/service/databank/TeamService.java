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
@Service("teamService")
public class TeamService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    /*
	* 新增
	*/
    public void save(PageData pd)throws Exception{
        dao.save("TeamMapper.save", pd);
    }

    /*
    * 删除
    */
    public void delete(PageData pd)throws Exception{
        dao.delete("TeamMapper.delete", pd);
    }

    /*
    * 修改
    */
    public void edit(PageData pd)throws Exception{
        dao.update("TeamMapper.edit", pd);
    }
    //   修改图片
    public void editPic(PageData pd)throws Exception{
        dao.update("TeamMapper.editpic", pd);
    }
    //    取出所有车间
    public List<Workshop> listWorkshop() throws Exception {
        return (List<Workshop>) dao.findForList("TeamMapper.listWorkshop", null);

    }

    /*
    *列表
    */
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("TeamMapper.datalistPage", page);
    }

    /*
    *列出所有班组(全部)
    */
    public List<PageData> listAll()throws Exception{
        return (List<PageData>)dao.findForList("TeamMapper.listAll",null);
    }

    public List<PageData> findTeamByW(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("TeamMapper.findTeamByW", pd);
    }
    /*
    * 通过id获取数据
    */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("TeamMapper.findById", pd);
    }

    /*
    * 批量删除
    */
    public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
        dao.delete("TeamMapper.deleteAll", ArrayDATA_IDS);
    }

}
