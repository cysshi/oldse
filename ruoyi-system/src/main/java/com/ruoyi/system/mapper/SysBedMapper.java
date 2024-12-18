package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysBed;

/**
 * 床位信息Mapper接口
 */
public interface SysBedMapper 
{
    /**
     * 查询床位信息列表
     * 
     * @param sysBed 床位信息
     * @return 床位信息集合
     */
    public List<SysBed> selectSysBedList(SysBed sysBed);

    /**
     * 查询床位信息
     * 
     * @param bedId 床位ID
     * @return 床位信息
     */
    public SysBed selectSysBedById(Long bedId);

    /**
     * 新增床位信息
     * 
     * @param sysBed 床位信息
     * @return 结果
     */
    public int insertSysBed(SysBed sysBed);

    /**
     * 修改床位信息
     * 
     * @param sysBed 床位信息
     * @return 结果
     */
    public int updateSysBed(SysBed sysBed);

    /**
     * 删除床位信息
     * 
     * @param bedId 床位ID
     * @return 结果
     */
    public int deleteSysBedById(Long bedId);

    /**
     * 批量删除床位信息
     * 
     * @param bedIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysBedByIds(Long[] bedIds);
} 