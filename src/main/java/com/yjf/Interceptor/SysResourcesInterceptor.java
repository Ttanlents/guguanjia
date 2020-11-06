package com.yjf.Interceptor;

import com.yjf.entity.SysResource;
import com.yjf.services.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/11/6 14:59
 * @Description
 */
@Component
public class SysResourcesInterceptor implements HandlerInterceptor {
    @Autowired
    SysResourceService sysResourceService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<SysResource> ComResources = sysResourceService.selectCommonResources();
        List<SysResource> authResources = ( List<SysResource>)request.getSession().getAttribute("resources");
        String uri = request.getRequestURI();
        uri=uri.replace(request.getContextPath()+"/","");
        //判断是否需要授权
        boolean need=false;
        for (SysResource comResource : ComResources) {
            if (comResource.getUrl().equals(uri)){
                need=true;
                break;
            }
        }
        //判断是否已经授权
        if (need){
            //已经授权
            for (SysResource authResource : authResources) {
                if (authResource.getUrl().equals(uri)){
                    return true;
                }
            }
            //无权限访问
            response.sendRedirect(request.getContextPath()+"/notauth.html");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
