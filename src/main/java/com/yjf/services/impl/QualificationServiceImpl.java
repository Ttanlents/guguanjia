package com.yjf.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.dao.QualificationDao;
import com.yjf.dao.SysUserDao;
import com.yjf.entity.Qualification;
import com.yjf.entity.QualificationCondition;
import com.yjf.entity.SysUser;
import com.yjf.services.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/26 12:03
 * @Description
 */
@Service
@Transactional
public class QualificationServiceImpl extends BaseServiceImpl<Qualification,Integer> implements QualificationService {
    @Autowired
  private   QualificationDao mapper;
    @Autowired
  private   SysUserDao sysUserDao;
    @Value("${staticPath}")
  private  String staticPath;
    @Override
    public PageInfo<Qualification> selectPage(Integer pageNum, Integer pageSize, QualificationCondition condition) {
        PageHelper.startPage(pageNum,pageSize);
        List<Qualification> qualificationList = mapper.selectpage(condition);
        PageInfo<Qualification> pageInfo = new PageInfo<>(qualificationList);
        return pageInfo;
    }

    @Override
    public Qualification selectByPrimaryKey(Integer o) {
        Qualification qualification = super.selectByPrimaryKey(o);
        SysUser sysUser = sysUserDao.selectByPrimaryKey(qualification.getUploadUserId());
        qualification.setAddress(staticPath+sysUser.getOfficeId()+"/"+qualification.getAddress());
        return qualification;
    }

    @Override
    public int updateByPrimaryKeySelective(Qualification qualification) {
        qualification.setUpdateDate(new Date());
        return super.updateByPrimaryKeySelective(qualification);
    }
}
