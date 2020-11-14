package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Result;
import com.yjf.entity.SysUser;
import com.yjf.services.SysUserService;
import com.yjf.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

/**
 * @author 余俊锋
 * @date 2020/11/3 16:33
 * @Description
 */
@RequestMapping("sysUser")
@Controller
public class SysUserController {
    @Autowired
    SysUserService sysUserService;
    @RequestMapping("toIndex")
    public String toIndex(){
        return "/user/user-list.html";
    }

    @RequestMapping("toUpdate")
    public String toUpdate(){
        return "/user/update.html";
    }

    @RequestMapping("toDetail")
    public String toDetail(){
        return "/user/detail.html";
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SysUser sysUser) {
        Result result = new Result();
        PageInfo<SysUser> pageInfo = sysUserService.selectPage(pageNum, pageSize,sysUser);
        result.setObj(pageInfo);
        return result;
    }

    @RequestMapping(value = "doSave",method = RequestMethod.PUT)
    @ResponseBody
    public Result doSave(@RequestBody SysUser sysUser, HttpSession session) {
        Result result = new Result( );
        int i = sysUserService.insertSelective(sysUser,session);
        if (i>=2){
            return result;
        }
        result.setSuccess(false);
        result.setMsg("添加失败");
        return result;
    }

    @RequestMapping(value = "doUpdate",method = RequestMethod.PUT)
    @ResponseBody
    public Result doUpdate(@RequestBody SysUser sysUser,HttpSession session) {
        if (sysUser.getId()!=null){
            SysUser   oldUser = sysUserService.selectByPrimaryKey(sysUser.getId());
            if (!Objects.equals(oldUser.getPassword(),sysUser.getPassword())){
                sysUser.setPassword(EncryptUtils.MD5_HEX(EncryptUtils.MD5_HEX(sysUser.getPassword())+sysUser.getUsername())); //这里加密
            }
        }
        Result result = new Result( );
        int i = sysUserService.updateByPrimaryKeySelective(sysUser,session);
        if (i>=2){
            return result;
        }
        result.setSuccess(false);
        result.setMsg("添加失败");
        return result;
    }

    @RequestMapping(value = "doDelete")
    @ResponseBody
    public Result doDelete(Integer id){
        Result result = new Result( );
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setUpdateDate(new Date());
        sysUser.setDelFlag("1");
        int i = sysUserService.updateByPrimaryKeySelective(sysUser);
        if (i>0){
            return result;
        }
        result.setSuccess(false);
        result.setMsg("删除失败");
        return result;
    }
}
