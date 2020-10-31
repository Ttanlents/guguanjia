package com.yjf.services.impl;

import com.yjf.dao.TransferDao;
import com.yjf.entity.Transfer;
import com.yjf.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/28 17:46
 * @Description
 */
@Service
@Transactional
public class TransferServiceImpl extends BaseServiceImpl<Transfer,Integer> implements TransferService {
    @Autowired
    TransferDao transferDao;
    @Override
    public List<Map<String, String>> selectTransfer(Integer id) {
        return transferDao.selectTransfer(id);
    }
}
