package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Result;
import com.yjf.entity.SysOffice;
import com.yjf.services.SysOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/27 17:11
 * @Description
 */
@Controller
@RequestMapping(value = "sysOffice")
public class SysOfficeController {
    @Autowired
    SysOfficeService sysOfficeService;

    /**
     *@Description TODO:zTree树装结构数据 核心查询
     *@author 余俊锋
     *@date 2020/11/2 19:06
     *@params
     *@return com.yjf.entity.Result
     */
    @ResponseBody
    @RequestMapping(value = "select")
    public Result select(){
        Result result = new Result();
        List<SysOffice> list = sysOfficeService.select(new SysOffice());
        result.setObj(list);
        return result;
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SysOffice sysOffice) {
        Result result = new Result();
        PageInfo<SysOffice> pageInfo = sysOfficeService.selectPage(pageNum, pageSize,sysOffice);
        result.setObj(pageInfo);
        return result;
    }

    @RequestMapping(value = "doUpdate",method = RequestMethod.PUT)
    @ResponseBody
    public Result doUpdate(@RequestBody SysOffice sysOffice) {
        Result result = new Result();
        int i = sysOfficeService.updateByPrimaryKeySelective(sysOffice);
        if (i>0){
            return result;
        }
        result.setSuccess(false);
        result.setMsg("修改失败");
        return result;
    }
    
    @RequestMapping(value = "toIndex")
    public String toIndex() {
        return "/office/office.html";
    }

    @RequestMapping(value = "toUpdate")
    public String toUpdate() {
        return "/office/update.html";
    }

    @RequestMapping(value = "toSelect")
    public String toSelect() {
        return "/office/select.html";
    }

    @RequestMapping(value = "toDetail")
    public String toDetail() {
        return "/office/detail.html";
    }

    @RequestMapping(value = "doDelete")
    @ResponseBody
    public Result doDelete(Integer id) {
        Result result = new Result();
        SysOffice sysOffice = new SysOffice();
        sysOffice.setId(id);
        sysOffice.setDelFlag("1");
        int i = sysOfficeService.updateByPrimaryKeySelective(sysOffice);
        if (i>0){
            return result;
        }
        result.setSuccess(false);
        result.setMsg("修改失败");
        return result;
    }

    @RequestMapping(value = "toAdd")
    public String toAdd() {
        return "/office/add.html";
    }

    @RequestMapping(value = "doInsert",method = RequestMethod.PUT)
    @ResponseBody
    public Result doInsert(@RequestBody SysOffice sysOffice) {
        sysOffice.setParentId(0);
        sysOffice.setParentIds("0,");
        Result result = new Result();
        int i = sysOfficeService.insertSelective(sysOffice);
        if (i>0){
            return result;
        }
        result.setSuccess(false);
        result.setMsg("添加失败");
        return result;
    }



}
