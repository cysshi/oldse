package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysCheckIn;
import com.ruoyi.system.service.ISysCheckInService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 入住记录Controller
 */
@RestController
@RequestMapping("/system/checkin")
public class SysCheckInController extends BaseController
{
    @Autowired
    private ISysCheckInService sysCheckInService;

    /**
     * 查询入住记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:checkin:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCheckIn sysCheckIn)
    {
        startPage();
        List<SysCheckIn> list = sysCheckInService.selectSysCheckInList(sysCheckIn);
        return getDataTable(list);
    }

    /**
     * 获取入住记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:checkin:query')")
    @GetMapping("/{checkInId}")
    public AjaxResult getInfo(@PathVariable Long checkInId)
    {
        return success(sysCheckInService.selectSysCheckInById(checkInId));
    }

    /**
     * 修改入住记录
     */
    @PreAuthorize("@ss.hasPermi('system:checkin:edit')")
    @Log(title = "入住记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCheckIn sysCheckIn)
    {
        return toAjax(sysCheckInService.updateSysCheckIn(sysCheckIn));
    }

    /**
     * 导出入住记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:checkin:export')")
    @Log(title = "入住记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysCheckIn sysCheckIn)
    {
        List<SysCheckIn> list = sysCheckInService.selectSysCheckInList(sysCheckIn);
        ExcelUtil<SysCheckIn> util = new ExcelUtil<SysCheckIn>(SysCheckIn.class);
        util.exportExcel(response, list, "入住记录数据");
    }
} 