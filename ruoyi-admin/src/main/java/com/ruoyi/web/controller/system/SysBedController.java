package com.ruoyi.web.controller.system;

import java.util.List;
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
import com.ruoyi.system.domain.SysBed;
import com.ruoyi.system.domain.SysCheckIn;
import com.ruoyi.system.service.ISysBedService;
import com.ruoyi.system.service.ISysCheckInService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * 床位信息Controller
 */
@RestController
@RequestMapping("/system/bed")
public class SysBedController extends BaseController
{
    @Autowired
    private ISysBedService sysBedService;

    @Autowired
    private ISysCheckInService sysCheckInService;

    /**
     * 查询床位信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:bed:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysBed sysBed)
    {
        startPage();
        List<SysBed> list = sysBedService.selectSysBedList(sysBed);
        return getDataTable(list);
    }

    /**
     * 导出床位信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:bed:export')")
    @Log(title = "床位信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysBed sysBed)
    {
        List<SysBed> list = sysBedService.selectSysBedList(sysBed);
        ExcelUtil<SysBed> util = new ExcelUtil<SysBed>(SysBed.class);
        util.exportExcel(response, list, "床位信息数据");
    }

    /**
     * 获取床位信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:bed:query')")
    @GetMapping(value = "/{bedId}")
    public AjaxResult getInfo(@PathVariable("bedId") Long bedId)
    {
        return success(sysBedService.selectSysBedById(bedId));
    }

    /**
     * 新增床位信息
     */
    @PreAuthorize("@ss.hasPermi('system:bed:add')")
    @Log(title = "床位信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysBed sysBed)
    {
        return toAjax(sysBedService.insertSysBed(sysBed));
    }

    /**
     * 修改床位信息
     */
    @PreAuthorize("@ss.hasPermi('system:bed:edit')")
    @Log(title = "床位信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysBed sysBed)
    {
        return toAjax(sysBedService.updateSysBed(sysBed));
    }

    /**
     * 删除床位信息
     */
    @PreAuthorize("@ss.hasPermi('system:bed:remove')")
    @Log(title = "床位信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bedIds}")
    public AjaxResult remove(@PathVariable Long[] bedIds)
    {
        return toAjax(sysBedService.deleteSysBedByIds(bedIds));
    }

    /**
     * 分配床位给老人
     */
    @PreAuthorize("@ss.hasPermi('system:bed:assign')")
    @Log(title = "床位分配", businessType = BusinessType.UPDATE)
    @PutMapping("/assign")
    @Transactional
    public AjaxResult assignBed(@RequestBody SysBed sysBed)
    {
        if (sysBed.getBedId() == null || sysBed.getElderId() == null)
        {
            return error("床位ID和老人ID不能为空");
        }

        try
        {
            // 1. 更新床位信息
            sysBed.setStatus("1"); // 设置床位状态为已入住
            sysBed.setUpdateTime(DateUtils.getNowDate());
            sysBed.setUpdateBy(SecurityUtils.getUsername());
            int rows = sysBedService.updateSysBed(sysBed);

            if (rows > 0)
            {
                // 2. 创建入住记录
                sysCheckInService.checkIn(sysBed.getElderId(), sysBed.getBedId());
                return success();
            }
            return error("分配床位失败");
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 取消床位分配
     */
    @PreAuthorize("@ss.hasPermi('system:bed:assign')")
    @Log(title = "取消床位分配", businessType = BusinessType.UPDATE)
    @PutMapping("/unassign/{bedId}")
    @Transactional
    public AjaxResult unassignBed(@PathVariable Long bedId)
    {
        try
        {
            // 1. 获取原床位信息
            SysBed existingBed = sysBedService.selectSysBedById(bedId);
            if (existingBed == null)
            {
                return error("床位不存在");
            }

            // 2. 登记退住信息
            if (existingBed.getElderId() != null)
            {
                // 查找当前入住记录
                SysCheckIn currentCheckIn = sysCheckInService.selectCurrentCheckInByElderId(existingBed.getElderId());
                if (currentCheckIn != null)
                {
                    // 更新退住时间和状态
                    currentCheckIn.setCheckOutTime(DateUtils.getNowDate());
                    currentCheckIn.setStatus("2"); // 设置状态为已退住
                    sysCheckInService.updateSysCheckIn(currentCheckIn);
                }
            }

            // 3. 更新床位信息
            SysBed sysBed = new SysBed();
            sysBed.setBedId(bedId);
            sysBed.setElderId(null);
            sysBed.setStatus("0"); // 设置床位状态为空闲
            sysBed.setUpdateTime(DateUtils.getNowDate());
            sysBed.setUpdateBy(SecurityUtils.getUsername());
            
            int rows = sysBedService.updateSysBed(sysBed);
            return toAjax(rows);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }
} 