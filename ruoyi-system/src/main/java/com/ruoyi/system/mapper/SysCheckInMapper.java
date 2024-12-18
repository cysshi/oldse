package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysCheckIn;

/**
 * 入住信息Mapper接口
 */
public interface SysCheckInMapper 
{
    /**
     * 查询入住信息列表
     * 
     * @param sysCheckIn 入住信息
     * @return 入住信息集合
     */
    public List<SysCheckIn> selectSysCheckInList(SysCheckIn sysCheckIn);

    /**
     * 查询入住信息
     * 
     * @param checkInId 入住ID
     * @return 入住信息
     */
    public SysCheckIn selectSysCheckInById(Long checkInId);

    /**
     * 新增入住信息
     * 
     * @param sysCheckIn 入住信息
     * @return 结果
     */
    public int insertSysCheckIn(SysCheckIn sysCheckIn);

    /**
     * 修改入住信息
     * 
     * @param sysCheckIn 入住信息
     * @return 结果
     */
    public int updateSysCheckIn(SysCheckIn sysCheckIn);

    /**
     * 查询老人当前的入住记录
     * 
     * @param elderId 老人ID
     * @return 入住信息
     */
    public SysCheckIn selectCurrentCheckInByElderId(Long elderId);
} 