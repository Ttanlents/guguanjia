package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.AppVersion;
import com.yjf.entity.Result;
import com.yjf.services.impl.AppVersionServiceImpl;
import com.yjf.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * @author 余俊锋
 * @date 2020/10/22 15:54
 * @Description
 */
@Controller
public class AppVersionController {
    @Autowired
    AppVersionServiceImpl appVersionService;



    @RequestMapping(value = "/manager/app/selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize){
        Result result = new Result();
        PageInfo<AppVersion> pageInfo = appVersionService.selectPage(pageNum, pageSize);
        result.setObj(pageInfo);
        return result;
    }

    @RequestMapping("index")
    public ModelAndView toindex(){
        return new ModelAndView("/app/index.html");
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/app/update.html");
    }

    @RequestMapping(value = "doUpdate",method = RequestMethod.PUT)
    @ResponseBody
    public Result doUpdate(@RequestBody AppVersion appVersion){
        Result result = new Result();
        int i = appVersionService.updateByPrimaryKeySelective(appVersion);
        System.out.println(i);
        if (i>0){
            return result;
        }
        result.setSuccess(false);
        result.setMsg("操作失败");
        return result;
    }
    @RequestMapping(value = "doInsert",method = RequestMethod.POST)
    @ResponseBody
    public Result doInsert(@RequestBody AppVersion appVersion){
        Result result = new Result();
        appVersion.setCreateDate(DateUtils.parseDate(new Date()));
        appVersion.setDelFlag("0");
        appVersion.setUpdateDate(DateUtils.parseDate(new Date()));
        appVersion.setCreateBy("管理员");
        int i = appVersionService.insert(appVersion);
        if (i>0){
            return result;
        }
        result.setSuccess(false);
        result.setMsg("操作失败");
        return result;
    }

    @RequestMapping(value = "doDelete",method = RequestMethod.POST)
    @ResponseBody
    public Result doDelete(@RequestBody AppVersion appVersion){
        Result result = new Result();
        appVersion.setDelFlag("1");
        int i = appVersionService.updateByPrimaryKey(appVersion);
        if (i>0){
            return result;
        }
        result.setSuccess(false);
        result.setMsg("操作失败");
        return result;
    }

}
