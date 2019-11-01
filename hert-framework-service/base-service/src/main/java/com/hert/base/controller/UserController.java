package com.hert.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hert.base.api.form.edit.DeleteForm;
import com.hert.base.api.form.edit.UserForm;
import com.hert.base.api.form.query.UserQuery;
import com.hert.base.service.IUserService;
import com.hert.core.log.model.LogError;
import com.hert.core.mp.support.Condition;
import com.hert.core.mp.support.Query;
import com.hert.core.secure.LoginUser;
import com.hert.core.tool.api.R;
import com.hert.core.tool.utils.Func;
import com.hert.base.api.entity.User;
import com.hert.base.api.vo.UserVO;
import com.hert.base.wrapper.UserWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@RequestMapping("user")
@AllArgsConstructor
@Api(value = "用户", tags = "用户")
public class UserController {

	private IUserService userService;

	/**
	 * 查询单条
	 */
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查看详情", notes = "传入id")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "账号id", paramType = "query", dataType = "Integer"),
	})
	@GetMapping("/detail")
	public R<UserVO> detail(@ApiIgnore @RequestParam(required = false) Integer id, LoginUser loginUser) {
		User detail = userService.getById(Func.toInt(id, loginUser.getUserId()));
		return R.data(UserWrapper.build().entityVO(detail));
	}

	/**
	 * 用户列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "账号id", paramType = "query", dataType = "Integer"),
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "账号id")
	public R<IPage<UserVO>> list(UserQuery userQuery, Query query, LoginUser loginUser) {
		IPage<User> pages = userService.page(Condition.getPage(query), Condition.getQueryWrapper(userQuery, User.class).eq("parent_id", Func.toInt(userQuery.getId(), loginUser.getUserId())));
		return R.data(UserWrapper.build().pageVO(pages));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增或修改", notes = "传入UserForm")
	public R submit(@Valid @RequestBody UserForm form) {
		return R.status(userService.submit(form));
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "删除", notes = "传入id集合")
	public R remove(@RequestBody DeleteForm form) {
		return R.status(userService.deleteLogic(form.getIdList()));
	}


	@PostMapping("/reset-password")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "初始化密码", notes = "传入userId集合")
	public R resetPassword(@RequestBody DeleteForm form) {
		boolean temp = userService.resetPassword(form.getIdList());
		return R.status(temp);
	}

}
