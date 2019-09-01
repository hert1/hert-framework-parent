package com.hert.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hert.base.api.entity.Client;
import com.hert.base.api.entity.User;
import com.hert.base.api.provider.UserProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
@Mapper
public interface ClientMapper extends BaseMapper<Client> {

}
