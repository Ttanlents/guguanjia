package com.yjf.services;

import com.yjf.entity.Detail;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/28 17:43
 * @Description
 */
public interface DetailsService extends BaseService<Detail,Integer> {
    public List<Map<String,String>> selectDetail(Integer id);
}
