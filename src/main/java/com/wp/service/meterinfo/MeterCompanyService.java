package com.wp.service.meterinfo;

import com.wp.dao.DaoSupport;
import com.wp.entity.Page;
import com.wp.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("meterCompanyService")
public class MeterCompanyService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
     * 新增
     */
    public void save(PageData pd)throws Exception{
        dao.save("MeterCompanyMapper.save", pd);
    }
    /*
     * 删除
     */
    public void delete(PageData pd)throws Exception{
        dao.delete("MeterCompanyMapper.delete", pd);
    }
    /*
     * 修改
     */
    public void edit(PageData pd)throws Exception{
        dao.update("MeterCompanyMapper.edit", pd);
    }
    /*
     *列表
     */
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("MeterCompanyMapper.datalistPage", page);
    }
    /*
     * 通过id获取数据
     */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("MeterCompanyMapper.findById", pd);
    }

    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("MeterCompanyMapper.listAll", pd);
    }
}
