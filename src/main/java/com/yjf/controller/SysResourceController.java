package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Result;
import com.yjf.entity.SysOffice;
import com.yjf.entity.SysResource;
import com.yjf.entity.SysUser;
import com.yjf.services.SysOfficeService;
import com.yjf.services.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("toUpdate")
    public String toUpdate() {
        return "/menu/menu-update.html";
    }

    @RequestMapping("toAdd")
    public String toAdd() {
        return "/menu/menu-add.html";
    }

    @RequestMapping("toDetail")
    public String toDetail() {
        return "/menu/menu-detail.html";
    }

    @RequestMapping("initMenu")
    @ResponseBody
    public Result initMenu() {
        Result result = new Result();
        result.setObj(new SysResource());
        return result;
    }

    @RequestMapping("toSelect")
    public String toSelect() {
        return "/menu/menu-select.html";
    }

    @RequestMapping("toIcon")
    public String toIcon() {
        return "/modules/module.html";
    }

    @RequestMapping(value = "doUpdate",method = RequestMethod.PUT)
    @ResponseBody
    public Result doUpdate(@RequestBody Map<String,Object> map, HttpSession session) {
        Result result = new Result();
        int i = sysResourceService.updateByParentId(map);
        if (i>0){
            return result;
        }
        result.setSuccess(false);
        result.setMsg("更新失败");
        return  result;

    }


    @RequestMapping(value = "doInsert",method = RequestMethod.PUT)
    @ResponseBody
    public Result doInsert(@RequestBody SysResource sysResource, HttpSession session) {
        SysUser loginUser =(SysUser) session.getAttribute("loginUser");
       if (loginUser!=null){
           sysResource.setCreateBy(loginUser.getName());
       }
        sysResource.setCreateDate(new Date());
        sysResource.setUpdateDate(new Date());
        sysResource.setDelFlag("0");
        if (StringUtils.isEmpty(sysResource.getParentId())){
            sysResource.setParentId(0);
            sysResource.setParentIds("0,");
        }
        Result result = new Result();
        int i = sysResourceService.insertSelective(sysResource);
        if (i>0){
            return result;
        }
        result.setSuccess(false);
        result.setMsg("更新失败");
        return  result;
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, String name) {
        Result result = new Result();
        PageInfo<SysResource> pageInfo = sysResourceService.selectPage(pageNum, pageSize,name);
        result.setObj(pageInfo);
        return result;
    }

    @RequestMapping(value = "doDelete",method = RequestMethod.PUT)
    @ResponseBody
    public Result doDelete(@RequestBody SysResource sysResource) {
        Result result = new Result();
        sysResource.setDelFlag("1");
        int i = sysResourceService.updateByPrimaryKeySelective(sysResource);
        if (i>0){
            return  result;
        }
        result.setMsg("删除失败");
        result.setSuccess(false);
        return result;
    }

}
