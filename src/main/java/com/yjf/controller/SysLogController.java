package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Result;
import com.yjf.entity.SysLog;
import com.yjf.services.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author 余俊锋
 * @date 2020/11/11 19:31
 * @Description
 */
@Controller
@RequestMapping("sysLog")
public class SysLogController {
    @Autowired
    SysLogService sysLogService;

    @RequestMapping(value = "toIndex")
    public String toIndex() {
        return "/log/log.html";
    }

    @RequestMapping(value = "toUpdate")
    public String toUpdate() {
        return "/log/log-detail.html";
    }


    @RequestMapping(value = "selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SysLog sysLog) {
        Result result = new Result();
        PageInfo<SysLog> pageInfo = sysLogService.selectPage(pageNum, pageSize, sysLog);
        result.setObj(pageInfo);
        return result;
    }

    @RequestMapping(value = "doDelete",method = RequestMethod.POST)
    @ResponseBody
    public Result doDelete(@RequestBody SysLog sysLog) {
        Result result = new Result();
        int i = sysLogService.delete(sysLog);
        if (i > 0) {
            return result;
        }
        result.setSuccess(false);
        result.setMsg("删除失败");
        return result;
    }


}
