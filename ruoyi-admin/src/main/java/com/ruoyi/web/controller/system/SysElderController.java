package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysElder;
import com.ruoyi.system.service.ISysElderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 老人信息Controller
 */
@RestController
@RequestMapping("/system/elder")
public class SysElderController extends BaseController
{
    @Autowired
    private ISysElderService sysElderService;

    /**
     * 查询老人信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:elder:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysElder sysElder)
    {
        startPage();
        List<SysElder> list = sysElderService.selectSysElderList(sysElder);
        return getDataTable(list);
    }

    /**
     * 导出老人信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:elder:export')")
    @Log(title = "老人信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysElder sysElder)
    {
        List<SysElder> list = sysElderService.selectSysElderList(sysElder);
        ExcelUtil<SysElder> util = new ExcelUtil<SysElder>(SysElder.class);
        util.exportExcel(response, list, "老人信息数据");
    }

    /**
     * 获取老人信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:elder:query')")
    @GetMapping(value = "/{elderId}")
    public AjaxResult getInfo(@PathVariable("elderId") Long elderId)
    {
        return success(sysElderService.selectSysElderById(elderId));
    }

    /**
     * 新增老人信息
     */
    @PreAuthorize("@ss.hasPermi('system:elder:add')")
    @Log(title = "老人信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysElder sysElder)
    {
        return toAjax(sysElderService.insertSysElder(sysElder));
    }

    /**
     * 修改老人信息
     */
    @PreAuthorize("@ss.hasPermi('system:elder:edit')")
    @Log(title = "老人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysElder sysElder)
    {
        return toAjax(sysElderService.updateSysElder(sysElder));
    }

    /**
     * 删除老人信息
     */
    @PreAuthorize("@ss.hasPermi('system:elder:remove')")
    @Log(title = "老人信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{elderIds}")
    public AjaxResult remove(@PathVariable Long[] elderIds)
    {
        return toAjax(sysElderService.deleteSysElderByIds(elderIds));
    }

    /**
     * 获取未分配床位的老人列表
     */
    @PreAuthorize("@ss.hasPermi('system:elder:list')")
    @GetMapping("/unassigned")
    public AjaxResult listUnassigned(SysElder sysElder)
    {
        List<SysElder> list = sysElderService.selectUnassignedElders(sysElder);
        return success(list);
    }

    /**
     * 获取老人年龄分布统计
     */
    @PreAuthorize("@ss.hasPermi('system:elder:list')")
    @GetMapping("/ageDistribution")
    public AjaxResult getAgeDistribution()
    {
        return success(sysElderService.selectElderAgeDistribution());
    }

    /**
     * 获取老人地区分布统计
     */
    @PreAuthorize("@ss.hasPermi('system:elder:list')")
    @GetMapping("/regionDistribution")
    public AjaxResult getRegionDistribution()
    {
        return success(sysElderService.selectElderRegionDistribution());
    }

    /**
     * 获取老人和床位统计数据
     */
    @PreAuthorize("@ss.hasPermi('system:elder:list')")
    @GetMapping("/statistics")
    public AjaxResult getStatistics()
    {
        return success(sysElderService.selectElderStatistics());
    }
} 