package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Result;
import com.yjf.entity.SysRole;
import com.yjf.entity.SysUser;
import com.yjf.services.SysRoleService;
import com.yjf.services.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/11/4 10:55
 * @Description
 */
@Controller
@RequestMapping("sysRole")
public class SysRoleController {
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysUserService sysUserService;


    @RequestMapping("toSelect")
    public String toSelect() {
        return "/role/role-select.html";
    }

    @RequestMapping("toDetail")
    public String toDetail() {
        return "/role/role-detail.html";
    }

    @RequestMapping("getAuthority")
    @ResponseBody
    public Result getAuthority(Integer id) {
        Result result = new Result();
        List<String> resources = sysRoleService.selectAuthorityByRoleId(id);
        result.setObj(resources);
        return result;
    }

    @RequestMapping("getAssignment")
    @ResponseBody
    public Result getAssignment(Integer id) {
        Result result = new Result();
        List<String> resources = sysRoleService.selectAssignmentUserByRoleId(id);
        result.setObj(resources);
        return result;
    }

    @RequestMapping("toIndex")
    public String toIndex() {
        return "/role/role.html";
    }

    @RequestMapping("toUpdate")
    public String toUpdate() {
        return "/role/role-update.html";
    }

    @RequestMapping("toAssignmentRole")
    public String toAssignmentRole() {
        return "/role/role-user.html";
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @RequestParam Map<String, Object> map) {
        Result result = new Result();
        PageInfo<SysRole> pageInfo = sysRoleService.selectPage(pageNum, pageSize, map);
        result.setObj(pageInfo);
        return result;
    }

    @RequestMapping("selectRole")
    @ResponseBody
    public Result selectRole(SysUser sysUser) {
        Result result = new Result();
        List<SysUser> sysUserList = sysUserService.selectRole(sysUser);
        result.setObj(sysUserList);
        return result;
    }

    @RequestMapping("doDelete")
    @ResponseBody
    public Result doDelete(int roleId, int[] ids) {
        Result result = new Result();
        int i = sysRoleService.deleteBatch(roleId, ids);
        if (i > 0) {
            return result;
        }
        result.setMsg("删除失败");
        result.setSuccess(false);
        return result;
    }

    @RequestMapping("selectNoRole")
    @ResponseBody
    public Result selectNoRole(Integer officeId, Integer roleId) {
        Result result = new Result();
        List<SysUser> sysUserList = sysUserService.selectNoRole(officeId, roleId);
        result.setObj(sysUserList);
        return result;
    }

    @RequestMapping("insertNoRole")
    @ResponseBody
    public Result insertNoRole(@RequestParam("roleId") Integer roleId, @RequestParam("userIds") List<Integer> userIds) {
        Result result = new Result();
        int i = sysRoleService.insertNoRole(roleId, userIds);
        if (i > 0) {
            return result;
        }
        result.setMsg("操作失败");
        result.setSuccess(false);
        return result;
    }

    @RequestMapping(value = "doUpdate", method = RequestMethod.PUT)
    @ResponseBody
    public Result doUpdate(@RequestBody Map<String, Object> map) {
        Result result = new Result();
        int i = sysRoleService.updateByPrimaryKeySelective(map);
        if (i > 0) {
            return result;
        }
        result.setSuccess(false);
        result.setMsg("更新失败！");
        return result;
    }

    @RequestMapping(value = "doInsert", method = RequestMethod.POST)
    @ResponseBody
    public Result doInsert(@RequestBody Map<String, Object> map) {
        Result result = new Result();
        int i = sysRoleService.insertSelective(map);
        if (i > 0) {
            return result;
        }
        result.setSuccess(false);
        result.setObj("添加失败");
        return result;
    }

}
