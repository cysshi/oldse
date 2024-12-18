package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.SysElder;

/**
 * 老人信息Service接口
 */
public interface ISysElderService 
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
     * 批量删除老人信息
     * 
     * @param elderIds 需要删除的老人ID
     * @return 结果
     */
    public int deleteSysElderByIds(Long[] elderIds);

    /**
     * 删除老人信息信息
     * 
     * @param elderId 老人ID
     * @return 结果
     */
    public int deleteSysElderById(Long elderId);

    /**
     * 查询未分配床位的老人列表
     *
     * @param sysElder 查询条件
     * @return 未分配床位的老人列表
     */
    public List<SysElder> selectUnassignedElders(SysElder sysElder);

    /**
     * 获取老人年龄分布统计
     * 
     * @return 年龄分布数据
     */
    public Map<String, Long> selectElderAgeDistribution();

    /**
     * 获取老人地区分布统计
     * 
     * @return 地区分布数据
     */
    public Map<String, Long> selectElderRegionDistribution();

    /**
     * 获取老人和床位统计数据
     * 
     * @return 统计数据
     */
    public Map<String, Long> selectElderStatistics();
} 