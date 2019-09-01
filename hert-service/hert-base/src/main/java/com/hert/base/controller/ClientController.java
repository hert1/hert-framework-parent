package com.hert.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hert.base.api.entity.Client;
import com.hert.base.api.entity.User;
import com.hert.base.api.form.edit.ClientForm;
import com.hert.base.api.form.edit.DeleteForm;
import com.hert.base.api.form.edit.UserForm;
import com.hert.base.api.form.query.ClientQuery;
import com.hert.base.api.form.query.UserQuery;
import com.hert.base.api.vo.ClientVO;
import com.hert.base.api.vo.UserVO;
import com.hert.base.service.IClientService;
import com.hert.base.service.IUserService;
import com.hert.base.wrapper.ClientWrapper;
import com.hert.base.wrapper.UserWrapper;
import com.hert.core.mp.support.Condition;
import com.hert.core.mp.support.Query;
import com.hert.core.secure.LoginUser;
import com.hert.core.tool.api.R;
import com.hert.core.tool.utils.Func;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@RequestMapping("client")
@AllArgsConstructor
@Api(value = "客户端", tags = "客户端")
public class ClientController {

	private IClientService clientService;

	/**
	 * 客户端列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "账号id", paramType = "query", dataType = "Integer"),
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "客户端", notes = "客户端id, 客户端密钥")
	public R<IPage<ClientVO>> list(ClientQuery clientQuery, Query query) {
		IPage<Client> pages = clientService.page(Condition.getPage(query), Condition.getQueryWrapper(clientQuery, Client.class));
		return R.data(ClientWrapper.build().pageVO(pages));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增或修改", notes = "传入UserForm")
	public R submit(@Valid @RequestBody ClientForm form) {
		return R.status(clientService.submit(form));
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "删除", notes = "传入id集合")
	public R remove(@RequestBody DeleteForm form) {
		return R.status(clientService.deleteLogic(form.getIdList()));
	}



}
