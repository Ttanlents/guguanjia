package com.yjf.services;

import com.yjf.entity.Transfer;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/28 17:45
 * @Description
 */
public interface TransferService extends BaseService<Transfer,Integer> {
    public List<Map<String,String>> selectTransfer(Integer id);
}
