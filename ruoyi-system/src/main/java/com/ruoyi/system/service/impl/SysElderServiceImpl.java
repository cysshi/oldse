package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysElderMapper;
import com.ruoyi.system.domain.SysElder;
import com.ruoyi.system.service.ISysElderService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.mapper.SysBedMapper;
import com.ruoyi.system.domain.SysBed;
import com.ruoyi.common.exception.ServiceException;

/**
 * 老人信息Service业务层处理
 */
@Service
public class SysElderServiceImpl implements ISysElderService 
{
    @Autowired
    private SysElderMapper sysElderMapper;

    @Autowired
    private SysBedMapper sysBedMapper;

    /**
     * 查询老人信息列表
     * 
     * @param sysElder 老人信息
     * @return 老人信息
     */
    @Override
    public List<SysElder> selectSysElderList(SysElder sysElder)
    {
        return sysElderMapper.selectSysElderList(sysElder);
    }

    /**
     * 查询老人信息
     * 
     * @param elderId 老人ID
     * @return 老人信息
     */
    @Override
    public SysElder selectSysElderById(Long elderId)
    {
        return sysElderMapper.selectSysElderById(elderId);
    }

    /**
     * 新增老人信息
     * 
     * @param sysElder 老人信息
     * @return 结果
     */
    @Override
    public int insertSysElder(SysElder sysElder)
    {
        sysElder.setCreateTime(DateUtils.getNowDate());
        sysElder.setCreateBy(SecurityUtils.getUsername());
        return sysElderMapper.insertSysElder(sysElder);
    }

    /**
     * 修改老人信息
     * 
     * @param sysElder 老人信息
     * @return 结果
     */
    @Override
    public int updateSysElder(SysElder sysElder)
    {
        sysElder.setUpdateTime(DateUtils.getNowDate());
        sysElder.setUpdateBy(SecurityUtils.getUsername());
        return sysElderMapper.updateSysElder(sysElder);
    }

    /**
     * 批量删除老人信息
     * 
     * @param elderIds 需要删除的老人ID
     * @return 结果
     */
    @Override
    public int deleteSysElderByIds(Long[] elderIds)
    {
        // 检查老人是否已分配床位
        for (Long elderId : elderIds)
        {
            // 查询老人是否有床位分配
            SysBed query = new SysBed();
            query.setElderId(elderId);
            List<SysBed> beds = sysBedMapper.selectSysBedList(query);
            if (!beds.isEmpty())
            {
                SysElder elder = selectSysElderById(elderId);
                throw new ServiceException(String.format("老人 %s 已分配床位，不能删除", elder.getElderName()));
            }
        }
        return sysElderMapper.deleteSysElderByIds(elderIds);
    }

    /**
     * 删除老人信息信息
     * 
     * @param elderId 老人ID
     * @return 结果
     */
    @Override
    public int deleteSysElderById(Long elderId)
    {
        return sysElderMapper.deleteSysElderById(elderId);
    }

    /**
     * 查询未分配床位的老人列表
     *
     * @param sysElder 查询条件
     * @return 未分配床位的老人列表
     */
    @Override
    public List<SysElder> selectUnassignedElders(SysElder sysElder)
    {
        return sysElderMapper.selectUnassignedElders(sysElder);
    }

    @Override
    public Map<String, Long> selectElderAgeDistribution() {
        List<SysElder> elders = sysElderMapper.selectSysElderList(new SysElder());
        Map<String, Long> distribution = new LinkedHashMap<>();
        
        // 初始化年龄段
        distribution.put("60岁以下", 0L);
        distribution.put("60-69岁", 0L);
        distribution.put("70-79岁", 0L);
        distribution.put("80-89岁", 0L);
        distribution.put("90岁以上", 0L);
        
        // 统计各年龄段人数
        for (SysElder elder : elders) {
            Integer age = elder.getAge();
            if (age == null) continue;
            
            if (age < 60) {
                distribution.merge("60岁以下", 1L, Long::sum);
            } else if (age < 70) {
                distribution.merge("60-69岁", 1L, Long::sum);
            } else if (age < 80) {
                distribution.merge("70-79岁", 1L, Long::sum);
            } else if (age < 90) {
                distribution.merge("80-89岁", 1L, Long::sum);
            } else {
                distribution.merge("90岁以上", 1L, Long::sum);
            }
        }
        
        return distribution;
    }

    @Override
    public Map<String, Long> selectElderRegionDistribution() {
        List<SysElder> elders = sysElderMapper.selectSysElderList(new SysElder());
        Map<String, Long> distribution = new LinkedHashMap<>();
        
        // 初始化主要省份
        distribution.put("北京", 0L);
        distribution.put("天津", 0L);
        distribution.put("河北", 0L);
        distribution.put("山西", 0L);
        distribution.put("内蒙古", 0L);
        distribution.put("辽宁", 0L);
        distribution.put("吉林", 0L);
        distribution.put("黑龙江", 0L);
        distribution.put("上海", 0L);
        distribution.put("江苏", 0L);
        distribution.put("浙江", 0L);
        distribution.put("安徽", 0L);
        distribution.put("福���", 0L);
        distribution.put("江西", 0L);
        distribution.put("山东", 0L);
        distribution.put("河南", 0L);
        distribution.put("湖北", 0L);
        distribution.put("湖南", 0L);
        distribution.put("广东", 0L);
        distribution.put("广西", 0L);
        distribution.put("海南", 0L);
        distribution.put("重庆", 0L);
        distribution.put("四川", 0L);
        distribution.put("贵州", 0L);
        distribution.put("云南", 0L);
        distribution.put("西藏", 0L);
        distribution.put("陕西", 0L);
        distribution.put("甘肃", 0L);
        distribution.put("青海", 0L);
        distribution.put("宁夏", 0L);
        distribution.put("新疆", 0L);
        
        // 统计各地区人数
        Pattern pattern = Pattern.compile("^(北京|天津|河北|山西|内蒙古|辽宁|吉林|黑龙江|上海|江苏|浙江|安徽|福建|江西|山东|河南|湖北|湖南|广东|广西|海南|重庆|四川|贵州|云南|西藏|陕西|甘肃|青海|宁夏|新疆)");
        
        for (SysElder elder : elders) {
            String address = elder.getAddress();
            if (address != null && !address.isEmpty()) {
                Matcher matcher = pattern.matcher(address);
                if (matcher.find()) {
                    String province = matcher.group(1);
                    distribution.merge(province, 1L, Long::sum);
                }
            }
        }
        
        return distribution;
    }

    @Override
    public Map<String, Long> selectElderStatistics() {
        Map<String, Long> statistics = new LinkedHashMap<>();
        
        // 获取所有老人数量
        SysElder elderQuery = new SysElder();
        long totalElders = sysElderMapper.selectSysElderList(elderQuery).size();
        statistics.put("totalElders", totalElders);
        
        // 获取所有床位数量
        SysBed bedQuery = new SysBed();
        List<SysBed> beds = sysBedMapper.selectSysBedList(bedQuery);
        long totalBeds = beds.size();
        
        // 获取空余床位数量
        long availableBeds = beds.stream()
            .filter(bed -> "0".equals(bed.getStatus()))
            .count();
            
        statistics.put("availableBeds", availableBeds);
        statistics.put("totalBeds", totalBeds);
        
        return statistics;
    }
} 