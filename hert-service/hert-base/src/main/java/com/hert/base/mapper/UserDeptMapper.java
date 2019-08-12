package com.hert.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hert.base.api.entity.User;
import com.hert.base.api.entity.UserDept;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper 接口
 *
 * @author Chill
 */
@Mapper
public interface UserDeptMapper extends BaseMapper<UserDept> {


}
