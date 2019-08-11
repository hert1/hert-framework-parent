package com.hert.base.controller;

import com.hert.base.api.form.edit.RoleForm;
import com.hert.base.service.IRoleService;
import com.hert.base.wrapper.RoleWrapper;
import com.hert.core.boot.ctrl.HertController;
import com.hert.core.secure.LoginUser;
import com.hert.core.tool.api.R;
import com.hert.core.tool.node.INode;
import com.hert.core.tool.utils.Func;
import com.hert.base.api.entity.Role;
import com.hert.base.api.vo.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Api(value = "角色", tags = "角色")
public class RoleController extends HertController {

	private IRoleService roleService;

	/**
	 * 获取角色树形结构
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<INode>> tree(@ApiIgnore @RequestParam(required = false) Integer userId, LoginUser loginUser) {
		List<Role> list = roleService.selectRoleByUserId(Func.toInt(userId, loginUser.getUserId()));
		return R.data(RoleWrapper.build().listNodeVO(list));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入role")
	public R submit(@Valid RoleForm form, LoginUser user) {
		return R.status(roleService.saveOrUpdate(form));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(roleService.removeByIds(Func.toIntList(ids)));
	}

}
