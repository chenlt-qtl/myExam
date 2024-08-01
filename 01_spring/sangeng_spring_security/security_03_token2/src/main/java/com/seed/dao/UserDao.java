package com.seed.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seed.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<SysUser> {

}
