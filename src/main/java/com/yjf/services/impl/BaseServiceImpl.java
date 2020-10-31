package com.yjf.services.impl;

import com.yjf.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/22 15:21
 * @Description
 */

public abstract class BaseServiceImpl<T,ID> implements BaseService<T,ID> {
    @Autowired
    Mapper<T> mapper;

    @Override
    public int deleteByPrimaryKey(ID o) {
      return mapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(T t) {
        return mapper.delete(t);
    }

    @Override
    public int insert(T t){
        return mapper.insert(t);
    }

    @Override
   public int insertSelective(T t){
      return   mapper.insertSelective(t);
    }

    @Override
   public boolean existsWithPrimaryKey(ID o){
        return mapper.existsWithPrimaryKey(o);
    }

    @Override
    public List<T> selectAll(){
        return mapper.selectAll();
    }

    @Override
    public T selectByPrimaryKey(ID o){
        return mapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(T t){
        return mapper.selectCount(t);
    }

    @Override
    public List<T> select(T t){
        return mapper.select(t);
    }

    @Override
    public T selectOne(T t){
        return mapper.selectOne(t);
    }

    @Override
    public int updateByPrimaryKey(T t){
     return    mapper.updateByPrimaryKey(t);
    }

    @Override
    public int updateByPrimaryKeySelective(T t){
     return    mapper.updateByPrimaryKeySelective(t);
    }
}
