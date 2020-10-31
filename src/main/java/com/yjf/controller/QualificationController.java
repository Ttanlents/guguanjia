package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Qualification;
import com.yjf.entity.QualificationCondition;
import com.yjf.entity.Result;
import com.yjf.services.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author 余俊锋
 * @date 2020/10/26 12:24
 * @Description
 */
@Controller
@RequestMapping("qualification")
public class QualificationController {
    @Autowired
    QualificationService qualificationService;

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum,@PathVariable Integer pageSize, QualificationCondition condition){
        PageInfo<Qualification> pageInfo = qualificationService.selectPage(pageNum, pageSize, condition);
        Result result = new Result();
        result.setObj(pageInfo);
        return result;
    }

    @RequestMapping("index")
    public String toIndex(){
        return "/qualification/index.html";
    }

    @RequestMapping("selectOne/{id}")
    @ResponseBody
    public Result selectOne(@PathVariable Integer id){
        Qualification qualification = qualificationService.selectByPrimaryKey(id);
        Result result = new Result();
        result.setObj(qualification);
        return result;
    }

    @RequestMapping("toUpdate")
    public String toUpdate(){
        return "/qualification/update.html";
    }

    @RequestMapping(value = "doUpdate",method = RequestMethod.PUT)
    @ResponseBody
    public Result doUpdate(@RequestBody Qualification qualification){
        int i = qualificationService.updateByPrimaryKeySelective(qualification);
        Result result = new Result();
        if (i>0){
            return result;
        }
        result.setSuccess(false);
        result.setMsg("操作失败");
        return result;
    }
}
