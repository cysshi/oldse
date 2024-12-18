package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysBedMapper;
import com.ruoyi.system.domain.SysBed;
import com.ruoyi.system.service.ISysBedService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.exception.ServiceException;

/**
 * 床位信息Service业务层处理
 */
@Service
public class SysBedServiceImpl implements ISysBedService 
{
    @Autowired
    private SysBedMapper sysBedMapper;

    /**
     * 查询床位信息列表
     * 
     * @param sysBed 床位信息
     * @return 床位信息
     */
    @Override
    public List<SysBed> selectSysBedList(SysBed sysBed)
    {
        return sysBedMapper.selectSysBedList(sysBed);
    }

    /**
     * 查询床位信息
     * 
     * @param bedId 床位ID
     * @return 床位信息
     */
    @Override
    public SysBed selectSysBedById(Long bedId)
    {
        return sysBedMapper.selectSysBedById(bedId);
    }

    /**
     * 新增床位信息
     * 
     * @param sysBed 床位信息
     * @return 结果
     */
    @Override
    public int insertSysBed(SysBed sysBed)
    {
        sysBed.setCreateTime(DateUtils.getNowDate());
        sysBed.setCreateBy(SecurityUtils.getUsername());
        return sysBedMapper.insertSysBed(sysBed);
    }

    /**
     * 修改床位信息
     * 
     * @param sysBed 床位信息
     * @return 结果
     */
    @Override
    public int updateSysBed(SysBed sysBed)
    {
        sysBed.setUpdateTime(DateUtils.getNowDate());
        sysBed.setUpdateBy(SecurityUtils.getUsername());
        return sysBedMapper.updateSysBed(sysBed);
    }

    /**
     * 批量删除床位信息
     * 
     * @param bedIds 需要删除的床位ID
     * @return 结果
     */
    @Override
    public int deleteSysBedByIds(Long[] bedIds)
    {
        // 检查床位是否有老人入住
        for (Long bedId : bedIds)
        {
            SysBed bed = selectSysBedById(bedId);
            if (bed != null && bed.getElderId() != null)
            {
                throw new ServiceException(String.format("床位编号 %s 已有老人入住，不能删除", bed.getBedNumber()));
            }
        }
        return sysBedMapper.deleteSysBedByIds(bedIds);
    }

    /**
     * 删除床位信息信息
     * 
     * @param bedId 床位ID
     * @return 结果
     */
    @Override
    public int deleteSysBedById(Long bedId)
    {
        return sysBedMapper.deleteSysBedById(bedId);
    }
} 