package com.yjf.dao;

import com.yjf.entity.SysArea;
import com.yjf.provider.SysAreaProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/30 12:42
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface SysAreaDao extends Mapper<SysArea> {
    @SelectProvider(value = SysAreaProvider.class,method = "selectPage")
     List<SysArea> selectPage(Map<String,Object> map);

    void insertForeach(List<SysArea> list);
    
    /*
     mybatis的Mapper方法：
     根据方法生成一个对应的处理对象mapperStatement对象，对象名是全限定名+方法名
     所以不可以重载方法，不然无法识别
     *@return
     */
    @Update("UPDATE sys_area \n" +
            "SET parent_ids = REPLACE ( parent_ids, #{oldParentIds}, #{parentIds} ) \n" +
            "WHERE\n" +
            "\tFIND_IN_SET( #{parentId}, parent_ids );")
     int updateByParentId(@Param("oldParentIds") String oldParentIds,@Param("parentIds") String parentIds,@Param("parentId") String parentId);

    @InsertProvider(value = SysAreaProvider.class,method = "insertBatch")
    void insertBatch(List<SysArea> list);
    
}
