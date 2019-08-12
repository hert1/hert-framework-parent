package com.hert.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hert.base.api.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


}
