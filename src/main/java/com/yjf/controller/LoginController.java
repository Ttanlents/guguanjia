package com.yjf.controller;

import com.baomidou.kaptcha.Kaptcha;
import com.yjf.entity.Result;
import com.yjf.entity.SysResource;
import com.yjf.entity.SysUser;
import com.yjf.services.SysResourceService;
import com.yjf.services.SysRoleService;
import com.yjf.services.SysUserService;
import com.yjf.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/11/3 10:18
 * @Description
 */
@RequestMapping("user")
@Controller
public class LoginController {

    @Autowired
    private Kaptcha kaptcha;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysResourceService sysResourceService;

    @RequestMapping(value = "toLogin")
    public String toLogin(){
        return "/login.html";
    }

    @RequestMapping(value = "doLogin")
    @ResponseBody
    public Result doLogin(String username, String password, String code, HttpSession session){
        String message="";
        if (kaptcha.validate(code)){
            SysUser sysUser=new SysUser();
            sysUser.setUsername(username);
            sysUser.setPassword(EncryptUtils.MD5_HEX(EncryptUtils.MD5_HEX(password)+username)); //这里加密
            SysUser loginUser = sysUserService.selectOne(sysUser);
            if (loginUser!=null){
                session.setAttribute("loginUser",loginUser);
                List<SysResource> resources = sysResourceService.SelectByUserId(loginUser.getId());
                session.setAttribute("resources",resources);
                Map<String,Object> map = new HashMap<>();
                loginUser.setPassword(null);
                map.put("loginUser",loginUser);
                map.put("resources",resources);
                Result result = new Result(map);
                result.setMsg("登录成功");
                return result;
            }else {
                message="用户账号或者密码错误！";
            }
        }
        return new Result(false,message,null);
    }

    @RequestMapping(value = "logout")
    @ResponseBody
    public Result logout(HttpSession session){
        session.invalidate();//清空session信息
        return new Result(true,"退出登录成功",null);
    }


}
