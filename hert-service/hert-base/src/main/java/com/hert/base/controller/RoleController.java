package com.hert.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hert.base.service.IRoleService;
import com.hert.base.wrapper.RoleWrapper;
import com.hert.core.boot.ctrl.HertController;
import com.hert.core.mp.support.Condition;
import com.hert.core.secure.HertUser;
import com.hert.core.tool.api.R;
import com.hert.core.tool.constant.HertConstant;
import com.hert.core.tool.node.INode;
import com.hert.core.tool.utils.Func;
import com.hert.base.api.entity.Role;
import com.hert.base.api.vo.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入roleId")
	public R<RoleVO> detail(@ApiIgnore @RequestParam Integer roleId) {
		Role detail = roleService.getById(roleId);
		return R.data(RoleWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "参数名称", paramType = "query", dataType = "Integer"),
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入role")
	public R<List<INode>> list(@ApiIgnore @RequestParam Integer userId, HertUser hertUser) {
		userId = null == userId ? hertUser.getUserId() : userId;
		List<Role> list = roleService.selectRoleByUserId(userId);
		return R.data(RoleWrapper.build().listNodeVO(list));
	}

	/**
	 * 获取角色树形结构
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<INode>> tree(@ApiIgnore @RequestParam(required = false) Integer userId, HertUser hertUser) {
		List<INode> tree = roleService.tree(Func.toInt(userId, hertUser.getUserId()));
		return R.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入role")
	public R submit(@Valid Role role, HertUser user) {
		/*if (Func.isEmpty(role.getId())) {
			role.setTenantCode(user.getTenantCode());
		}*/
		return R.status(roleService.saveOrUpdate(role));
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

	/**
	 * 设置菜单权限
	 *
	 * @param roleIds
	 * @param menuIds
	 * @return
	 */
	@PostMapping("/grant")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	public R grant(@ApiParam(value = "roleId集合", required = true) @RequestParam String roleIds,
				   @ApiParam(value = "menuId集合", required = true) @RequestParam String menuIds) {
		boolean temp = roleService.grant(Func.toIntList(roleIds), Func.toIntList(menuIds));
		return R.status(temp);
	}

}
