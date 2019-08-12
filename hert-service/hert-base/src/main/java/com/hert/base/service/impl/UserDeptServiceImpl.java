package com.hert.base.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hert.base.api.dto.UserDTO;
import com.hert.base.api.entity.Dept;
import com.hert.base.api.entity.Menu;
import com.hert.base.api.entity.Role;
import com.hert.base.api.entity.User;
import com.hert.base.api.entity.UserDept;
import com.hert.base.api.enums.AccountTypeEnum;
import com.hert.base.api.enums.MenuTypeEnum;
import com.hert.base.api.form.edit.UserForm;
import com.hert.base.mapper.MenuMapper;
import com.hert.base.mapper.RoleMapper;
import com.hert.base.mapper.UserDeptMapper;
import com.hert.base.mapper.UserMapper;
import com.hert.base.mapper.UserRoleMapper;
import com.hert.base.service.IDeptService;
import com.hert.base.service.IMenuService;
import com.hert.base.service.IRoleService;
import com.hert.base.service.IUserDeptService;
import com.hert.base.service.IUserService;
import com.hert.common.constant.CommonConstant;
import com.hert.core.mp.base.BaseServiceImpl;
import com.hert.core.tool.utils.DigestUtil;
import com.hert.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class UserDeptServiceImpl extends ServiceImpl<UserDeptMapper, UserDept> implements IUserDeptService {


}
