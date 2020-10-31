package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Result;
import com.yjf.entity.Statute;
import com.yjf.services.StatuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author 余俊锋
 * @date 2020/10/29 15:44
 * @Description
 */
@Controller
@RequestMapping("statute")
public class StatuteController {
    @Autowired
    StatuteService statuteService;
    @RequestMapping(value = "toIndex")
    public String toIndex() {
        return "/statute/index.html";
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, Statute statute) {
        Result result = new Result();
        PageInfo<Statute> pageInfo = statuteService.selectPage(pageNum, pageSize,statute);
        result.setObj(pageInfo);
        return result;
    }

    @RequestMapping("doDelete")
    @ResponseBody
    public Result doDelete(Integer id) {
        Result result = new Result();
        Statute statute = new Statute();
        statute.setDelFlag("1");
        statute.setId(id);
        int i = statuteService.updateByPrimaryKeySelective(statute);
        if (i>0){
            return result;
        }
        result.setSuccess(false);
        result.setMsg("删除失败");
        return  result;
    }


    @RequestMapping("toUpdate")
    public String toUpdate() {
        return "/statute/update.html";
    }

    @RequestMapping(value = "toAdd",method = RequestMethod.PUT)
    @ResponseBody
    public Result toAdd(@RequestBody  Statute statute) {
        Result result = new Result();
        statute.setCreateDate(new Date());
        statute.setUpdateDate(new Date());
        statute.setDelFlag("0");
        int i = statuteService.insertSelective(statute);
        if (i>0){
            return result;
        }else {
            result.setMsg("保存失败");
            result.setSuccess(false);
        }
        return null;
    }
}
