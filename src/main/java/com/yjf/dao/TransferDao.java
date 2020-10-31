package com.yjf.dao;

import com.yjf.entity.Transfer;
import com.yjf.provider.TransferProvider;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/28 17:31
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface TransferDao extends Mapper<Transfer> {

    @SelectProvider(value = TransferProvider.class,method = "selectTransfer")
    public List<Map<String,String>> selectTransfer(Integer id);
}
