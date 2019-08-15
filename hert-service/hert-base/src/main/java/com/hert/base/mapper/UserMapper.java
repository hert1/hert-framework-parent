package com.hert.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hert.base.api.entity.User;
import com.hert.base.api.provider.UserProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @SelectProvider(type = UserProvider.class, method = "selectByQuery")
    List<User> selectUserByQuery(Page page, @Param("query") UserQuery query);


}
