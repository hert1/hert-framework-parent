package com.hert.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hert.base.api.entity.RoleMenu;
import com.hert.base.mapper.RoleMenuMapper;
import com.hert.base.service.IRoleMenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 服务实现类
 *
 * @author hert
 */
@Service
@Validated
@AllArgsConstructor
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

}
