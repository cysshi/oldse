package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysElder;

/**
 * 老人信息Mapper接口
 */
public interface SysElderMapper 
{
    /**
     * 查询老人信息列表
     * 
     * @param sysElder 老人信息
     * @return 老人信息集合
     */
    public List<SysElder> selectSysElderList(SysElder sysElder);

    /**
     * 查询老人信息
     * 
     * @param elderId 老人ID
     * @return 老人信息
     */
    public SysElder selectSysElderById(Long elderId);

    /**
     * 新增老人信息
     * 
     * @param sysElder 老人信息
     * @return 结果
     */
    public int insertSysElder(SysElder sysElder);

    /**
     * 修改老人信息
     * 
     * @param sysElder 老人信息
     * @return 结果
     */
    public int updateSysElder(SysElder sysElder);

    /**
     * 删除老人信息
     * 
     * @param elderId 老人ID
     * @return 结果
     */
    public int deleteSysElderById(Long elderId);

    /**
     * 批量删除老人信息
     * 
     * @param elderIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysElderByIds(Long[] elderIds);

    /**
     * 查询未分配床位的老人列表
     *
     * @param sysElder 查询条件
     * @return 未分配床位的老人列表
     */
    public List<SysElder> selectUnassignedElders(SysElder sysElder);
} 