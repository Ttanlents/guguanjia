package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Result;
import com.yjf.entity.SysArea;
import com.yjf.services.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/30 13:21
 * @Description
 */
@Controller
@RequestMapping("sysArea")
public class SysAreaController {
    @Autowired
    SysAreaService sysAreaService;

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @RequestParam Map<String, Object> map) {
        Result result = new Result();
        PageInfo<SysArea> pageInfo = sysAreaService.selectPage(pageNum, pageSize, map);
        result.setObj(pageInfo);
        return result;
    }

    @RequestMapping(value = "toIndex")
    public String toIndex() {
        return "/area/area.html";
    }

    @RequestMapping(value = "toUpdate")
    public String toUpdate() {
        return "/area/update.html";
    }

    @RequestMapping(value = "toSelect")
    public String toSelect() {
        return "/area/select.html";
    }

    @RequestMapping(value = "toModule")
    public String toModule() {
        return "/modules/module.html";
    }

    @RequestMapping("selectAll")
    @ResponseBody
    public Result selectAll() {
        Result result = new Result();
        List<SysArea> sysAreas = sysAreaService.selectAll();
        result.setObj(sysAreas);
        return result;
    }

    @RequestMapping(value = "doUpdate", method = RequestMethod.PUT)
    @ResponseBody
    public Result doUpdate(@RequestBody SysArea sysArea) {
        Result result = new Result();
        int i = sysAreaService.updateByPrimaryKeySelective(sysArea);
        if (i > 0) {
            return result;
        }
        result.setSuccess(false);
        result.setMsg("修改失败");
        return result;

    }
}
