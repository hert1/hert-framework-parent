package com.hert.base.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.hert.base.api.dto.UserDTO;
import com.hert.base.api.entity.*;
import com.hert.base.api.enums.AccountTypeEnum;
import com.hert.base.api.enums.MenuTypeEnum;
import com.hert.base.api.form.edit.ClientForm;
import com.hert.base.api.form.edit.UserForm;
import com.hert.base.mapper.ClientMapper;
import com.hert.base.mapper.UserMapper;
import com.hert.base.service.*;
import com.hert.common.constant.CommonConstant;
import com.hert.core.mp.base.BaseServiceImpl;
import com.hert.core.tool.utils.DigestUtil;
import com.hert.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class ClientServiceImpl extends BaseServiceImpl<ClientMapper, Client> implements IClientService {


	@Override
    @Transactional
	public boolean submit(ClientForm form) {
		Client client = Func.copy(form, Client.class);
        Boolean clientSubmit = true;
        if(null == client.getId()) {
			Integer cnt = baseMapper.selectCount(Wrappers.<Client>query().lambda().eq(Client::getClientId, form.getClientId()));
			if (cnt > 0) {
				throw new ApiException("当前客户端已存在!");
			}
			clientSubmit = this.save(client);
        } else {
			clientSubmit = this.updateById(client);
        }
        return clientSubmit;
	}




}
