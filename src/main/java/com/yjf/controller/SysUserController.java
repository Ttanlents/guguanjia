package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Result;
import com.yjf.entity.SysUser;
import com.yjf.services.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SysUser sysUser) {
        Result result = new Result();
        PageInfo<SysUser> pageInfo = sysUserService.selectPage(pageNum, pageSize,sysUser);
        result.setObj(pageInfo);
        return result;
    }
}
