package com.yjf.Interceptor;

import com.yjf.entity.SysUser;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 余俊锋
 * @date 2020/11/3 15:26
 * @Description
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        SysUser loginUser =(SysUser) session.getAttribute("loginUser");
        if (loginUser==null){
            response.sendRedirect(request.getContextPath()+"/login.html");
            return  false;  //如果没登录就false
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //渲染视图前
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //渲染视图后
    }
}
