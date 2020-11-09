package com.yjf.aspect;

import com.yjf.entity.SysLog;
import com.yjf.entity.SysUser;
import com.yjf.services.SysLogService;
import com.yjf.utils.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * @author 余俊锋
 * @date 2020/9/30 11:41
 * @Description
 */
@Component
@Aspect
public class SysLogAspect {
    @Autowired
    SysLogService sysLogService;
    @Autowired
    HttpServletRequest request;
    //within 精确到类中的全部方法
    //execution 精确到某个方法
    @Pointcut("execution(* com..services.*.*(..))")
    public void point(){

    }

   //正常操作日志
    @AfterReturning("point()")
    public void afterReturning(JoinPoint joinPoint){
        String className = joinPoint.getTarget().getClass().getSimpleName();
        if (!Objects.equals(className,"SysLogServiceImpl")){
            insert(null,joinPoint);   //sysLog在service层，避免递归调用，跳过SysLogService
        }

    }


    //异常操作日志    JoinPoint代表连接点（切入的方法点）
    @AfterThrowing(value = "point()",throwing = "e")
    public void afterThrowing(JoinPoint joinPoint,Exception e){
        String className = joinPoint.getTarget().getClass().getSimpleName();
        if (!Objects.equals(className,"SysLogServiceImpl")){
            insert(e,joinPoint);   //sysLog在service层，避免递归调用，跳过SysLogService
        }

    }

     //不加入到其他事务中，使用自己的事务
    public  void insert(Exception e,JoinPoint joinPoint){
        SysLog sysLog=new SysLog();
        sysLog.setType(e==null?"1":"2");
        if (request!=null){
            SysUser loginUser =(SysUser) request.getSession().getAttribute("loginUser");
            if (loginUser!=null){
                sysLog.setCreateBy(loginUser.getName());
            }
            sysLog.setCreateDate(new Date());
            sysLog.setRemoteAddr(IPUtils.getClientAddress(request));
            sysLog.setUserAgent(request.getHeader("User-Agent"));
            sysLog.setRequestUri(request.getRequestURI());
            sysLog.setMethod(request.getMethod());  //post  get
        }
        Object[] args = joinPoint.getArgs();
        if (args!=null&&args.length>0){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                sb.append("[参数:").append(i+1).
                        append(",类型:").append(args[i].getClass().getSimpleName()).
                        append(",值:").append(args[i]+"],");
            }
            sb.deleteCharAt(args.length-1);
            sysLog.setParams(sb.toString());
        }
        sysLog.setException(e==null?"":e.toString());
        sysLog.setDescription("日志记录.....");
        sysLogService.insert(sysLog);

    }
}
