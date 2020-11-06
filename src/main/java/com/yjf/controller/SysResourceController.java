package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Result;
import com.yjf.entity.SysOffice;
import com.yjf.entity.SysResource;
import com.yjf.services.SysOfficeService;
import com.yjf.services.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/11/5 14:11
 * @Description
 */
@Controller
@RequestMapping("sysResource")
public class SysResourceController {

    @Autowired
    SysResourceService sysResourceService;
    @Autowired
    SysOfficeService sysOfficeService;
    @ResponseBody
    @RequestMapping(value = "selectAll")
    public Result selectAll(){
        Result result = new Result();
        List<SysResource> sysResources = sysResourceService.selectAll();
        result.setObj(sysResources);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "selectResourceByRoleId")
    public Result selectResourceByRoleId(Integer roleId){
        Result result = new Result();
        List<SysResource> sysResources = sysResourceService.SelectByRoleId(roleId);
        result.setObj(sysResources);
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "selectOfficeByRoleId")
    public Result selectOfficeByRoleId(Integer roleId){
        Result result = new Result();
        List<SysOffice> sysOffices = sysOfficeService.selectByRoleId(roleId);
        result.setObj(sysOffices);
        return result;
    }


    @RequestMapping("toIndex")
    public String toIndex() {
        return "/menu/menu.html";
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, String name) {
        Result result = new Result();
        PageInfo<SysResource> pageInfo = sysResourceService.selectPage(pageNum, pageSize,name);
        result.setObj(pageInfo);
        return result;
    }

}
