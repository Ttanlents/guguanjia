package com.yjf.services.impl;

import com.yjf.dao.DetailDao;
import com.yjf.entity.Detail;
import com.yjf.services.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/28 17:44
 * @Description
 */
@Service
@Transactional
public class DetailsServiceImpl extends BaseServiceImpl<Detail,Integer> implements DetailsService {
    @Autowired
    DetailDao detailDao;
    @Override
    public List<Map<String, String>> selectDetail(Integer id) {
        return detailDao.selectDetail(id);
    }
}
