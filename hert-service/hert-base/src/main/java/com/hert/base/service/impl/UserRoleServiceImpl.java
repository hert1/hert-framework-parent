package com.hert.base.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hert.base.api.entity.UserDept;
import com.hert.base.api.entity.UserRole;
import com.hert.base.mapper.UserRoleMapper;
import com.hert.base.service.IUserDeptService;
import com.hert.base.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {


}
