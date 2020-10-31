package com.yjf.dao;


import com.yjf.entity.AppVersion;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author 余俊锋
 * @date 2020/10/22 13:30
 * @Description
 */

//加注解默认开启驼峰式
@org.apache.ibatis.annotations.Mapper
public interface AppVersionDao extends Mapper<AppVersion> {
    //继承通用mapper,会生成代理子类，是tkmapper提供的代理子类
    //实现的基本的crud
    //查看接口，其实是基于注解的provide动态sql来实现的

}
