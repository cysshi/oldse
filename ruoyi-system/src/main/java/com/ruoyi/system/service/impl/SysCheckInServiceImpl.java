package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.mapper.SysCheckInMapper;
import com.ruoyi.system.domain.SysCheckIn;
import com.ruoyi.system.service.ISysCheckInService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * 入住信息Service业务层处理
 */
@Service
public class SysCheckInServiceImpl implements ISysCheckInService 
{
    @Autowired
    private SysCheckInMapper sysCheckInMapper;

    /**
     * 查询入住信息列表
     * 
     * @param sysCheckIn 入住信息
     * @return 入住信息
     */
    @Override
    public List<SysCheckIn> selectSysCheckInList(SysCheckIn sysCheckIn)
    {
        return sysCheckInMapper.selectSysCheckInList(sysCheckIn);
    }

    /**
     * 查询入住信息
     * 
     * @param checkInId 入住ID
     * @return 入住信息
     */
    @Override
    public SysCheckIn selectSysCheckInById(Long checkInId)
    {
        return sysCheckInMapper.selectSysCheckInById(checkInId);
    }

    /**
     * 新增入住信息
     * 
     * @param sysCheckIn 入住信息
     * @return 结果
     */
    @Override
    public int insertSysCheckIn(SysCheckIn sysCheckIn)
    {
        sysCheckIn.setCreateTime(DateUtils.getNowDate());
        sysCheckIn.setCreateBy(SecurityUtils.getUsername());
        return sysCheckInMapper.insertSysCheckIn(sysCheckIn);
    }

    /**
     * 修改入住信息
     * 
     * @param sysCheckIn 入住信息
     * @return 结果
     */
    @Override
    public int updateSysCheckIn(SysCheckIn sysCheckIn)
    {
        sysCheckIn.setUpdateTime(DateUtils.getNowDate());
        sysCheckIn.setUpdateBy(SecurityUtils.getUsername());
        return sysCheckInMapper.updateSysCheckIn(sysCheckIn);
    }

    /**
     * 查询老人当前的入住记录
     * 
     * @param elderId 老人ID
     * @return 入住信息
     */
    @Override
    public SysCheckIn selectCurrentCheckInByElderId(Long elderId)
    {
        return sysCheckInMapper.selectCurrentCheckInByElderId(elderId);
    }

    /**
     * 登记入住信息
     * 
     * @param elderId 老人ID
     * @param bedId 床位ID
     * @return 结果
     */
    @Override
    @Transactional
    public int checkIn(Long elderId, Long bedId)
    {
        SysCheckIn checkIn = new SysCheckIn();
        checkIn.setElderId(elderId);
        checkIn.setBedId(bedId);
        checkIn.setCheckInTime(DateUtils.getNowDate());
        checkIn.setStatus("1"); // 设置状态为在住
        return insertSysCheckIn(checkIn);
    }

    /**
     * 登记退住信息
     * 
     * @param elderId 老人ID
     * @return 结果
     */
    @Override
    @Transactional
    public int checkOut(Long elderId)
    {
        // 查询当前入住记录
        SysCheckIn currentCheckIn = selectCurrentCheckInByElderId(elderId);
        if (currentCheckIn != null)
        {
            // 更新退住时间和状态
            currentCheckIn.setCheckOutTime(DateUtils.getNowDate());
            currentCheckIn.setStatus("2"); // 设置状态为已退住
            return updateSysCheckIn(currentCheckIn);
        }
        return 0;
    }
} 