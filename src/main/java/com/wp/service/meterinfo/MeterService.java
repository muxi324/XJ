package com.wp.service.meterinfo;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MeterService {
    @Resource(name ="daoSupport")
    private DaoSupport dao;
    /*
     *列表
     */
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("MeterMapper.datalistPage", page);
    }

    public List<PageData> findMeterCom(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("MeterMapper.findMeterCom", pd);
    }

    /*
     * 通过id获取水表工厂信息
     */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("MeterMapper.findById", pd);
    }

    /*
     * 通过id获取水表工厂信息
     */
    public PageData findByMeterId(PageData pd)throws Exception{
        return (PageData)dao.findForObject("MeterMapper.findByMeterId", pd);
    }

    /*
     * 删除
     */
    public void delete(PageData pd)throws Exception{
        dao.delete("MeterMapper.delete", pd);
    }
    /*
     * 保存结果
     */
    public void save(PageData pd)throws Exception{
        dao.save("MeterMapper.save", pd);
    }

    /*
     * 修改
     */
    public void edit(PageData pd)throws Exception{
        dao.update("MeterMapper.edit", pd);
    }

    /*
     * 查询水表的信息
     */
    public List<PageData> getWorkContent(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("MeterMapper.getWorkContent",pd);
    }

    /*
     *列表
     */
    public List<PageData> listMeter(String pd)throws Exception{
        return (List<PageData>)dao.findForList("MeterMapper.listMeter",pd);
    }
}
