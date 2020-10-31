package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Demand;
import com.yjf.entity.Result;
import com.yjf.services.impl.DemandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author 余俊锋
 * @date 2020/10/24 10:09
 * @Description
 */
@Controller
@RequestMapping("demand")
public class DemandController {
    @Autowired
    DemandServiceImpl demandService;

    @RequestMapping("toIndex")
    public String toIndex() {
        return "/demand/index.html";
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Result result = new Result();
        PageInfo<Demand> pageInfo = demandService.selectPage(pageNum, pageSize);
        result.setObj(pageInfo);
        return result;
    }

    @RequestMapping(value = "toUpdate")
    public String toUpdate() {
        return "/demand/update.html";
    }

    @RequestMapping(value = "doUpdate",method = RequestMethod.PUT)
    @ResponseBody
    public Result doUpdate(@RequestBody Demand demand) {
        Result result = new Result();
        int i = demandService.updateByPrimaryKeySelective(demand);
        if (i > 0) {
            return result;
        }
        result.setSuccess(false);
        result.setMsg("修改失败！");
        return result;
    }

    @RequestMapping("toDetail")
    public String toDetail(){
        return "/demand/detail.html";
    }
}
