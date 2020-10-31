package com.yjf.services;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/22 15:21
 * @Description
 */

public interface BaseService<T,ID> {
    
    int deleteByPrimaryKey(ID o);

    
    int delete(T t);

    
    int insert(T t);

    
    int insertSelective(T t);

    
    boolean existsWithPrimaryKey(ID o);

    
    List<T> selectAll();

    
    T selectByPrimaryKey(ID o);

    
    int selectCount(T t);

    
    List<T> select(T t);

    
    T selectOne(T t);

    
    int updateByPrimaryKey(T t);

    
    int updateByPrimaryKeySelective(T t);

    

}
