package com.wp.service.house;
import com.wp.dao.DaoSupport;
import com.wp.entity.house.House;
import com.wp.entity.system.Menu;
import com.wp.util.PageData;
import com.wp.entity.Page;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.annotation.Resource;

/**
 * Created by wp on 2017/12/12.
 */
@Service("HouseService")
public class HouseService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
	* 新增
	*/
    public void save(PageData pd)throws Exception{
        dao.save("HouseMapper.save", pd);
    }

    /*
    * 删除
    */
    public void delete(PageData pd)throws Exception{
        dao.delete("HouseMapper.delete", pd);
    }

    /*
    * 修改
    */
    public void edit(PageData pd)throws Exception{
        dao.update("HouseMapper.edit", pd);
    }
//    修改图片
    public void editPic(PageData pd)throws Exception{
    	dao.update("HouseMapper.editpic", pd);
    }
//    取出所有锁的类型
    public List<House> listLockType() throws Exception {
        return (List<House>) dao.findForList("HouseMapper.listLockType", null);

    }
    //    取出所有锁的型号
    public List<House> listLockModel() throws Exception {
        return (List<House>) dao.findForList("HouseMapper.listLockModel", null);

    }

    /*
    *列表(全部)dd
    */
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("HouseMapper.listAll", pd);
    }

    /*
    * 通过id获取数据
    */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("HouseMapper.findById", pd);
    }

    /*
	* 房源列表
	*/
    public List<PageData> listAllHouse(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("HouseMapper.listAllHouse", pd);
    }
//    通过enquiry获取数据
    public List<PageData> listPdPageHouse(Page page)throws Exception{
        return (List<PageData>) dao.findForList("HouseMapper.houselistPage", page);
    }

    /*
    * 批量删除
    */
    public void deleteAll(String[] house_ids)throws Exception{
        dao.delete("HouseMapper.deleteAll", house_ids);
    }

    /*
   * 修改房源状态
   */
    public void editStatus(PageData pd)throws Exception{
        dao.update("HouseMapper.editStatus", pd);
    }

    /*
   * 下达任务后修改房源状态为安装中
   */
    public void editStatus2(PageData pd)throws Exception{
        dao.update("HouseMapper.editStatus2", pd);
    }



}

