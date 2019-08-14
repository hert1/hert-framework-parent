package com.hert.base.controller;

import com.hert.base.api.enums.MenuTypeEnum;
import com.hert.base.api.form.edit.MenuForm;
import com.hert.base.service.IMenuService;
import com.hert.base.wrapper.MenuWrapper;
import com.hert.core.boot.ctrl.HertController;
import com.hert.core.secure.LoginUser;
import com.hert.core.secure.annotation.PreAuth;
import com.hert.core.tool.api.R;
import com.hert.core.tool.constant.RoleConstant;
import com.hert.core.tool.support.Kv;
import com.hert.core.tool.utils.Func;
import com.hert.base.api.entity.Menu;
import com.hert.base.api.vo.MenuVO;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/menu")
@Api(value = "菜单", tags = "菜单")
public class MenuController extends HertController {

	private IMenuService menuService;

	@GetMapping("/detail")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入menuForm")
	public R<MenuVO> detail(@ApiIgnore @RequestParam Integer menuId) {
		Menu detail = menuService.getById(menuId);
		return R.data(MenuWrapper.build().entityVO(detail));
	}

	@PostMapping("/submit")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "新增或修改", notes = "传入menuForm")
	public R submit(@Valid @RequestBody MenuForm form) {
		return R.status(menuService.saveOrUpdate(Func.copy(form, Menu.class)));
	}


	@PostMapping("/remove")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(menuService.removeByIds(Func.toIntList(ids)));
	}

	@GetMapping("/tree")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "前端全部数据", notes = "前端全部数据")
	public R<List<MenuVO>> tree(@ApiIgnore @RequestParam(required = false) List<Integer> roleIds, LoginUser user) {
		List<Menu> list = menuService.list(MenuTypeEnum.ALL.getValue(), Func.isNotEmpty(roleIds) ? roleIds : user.getRoleId());
		return R.data(MenuWrapper.build().listNodeVO(list));
	}

	/**
	 * 获取菜单树形结构
	 */
	@GetMapping("/router")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<MenuVO>> router(@ApiIgnore @RequestParam(required = false) List<Integer> roleIds, LoginUser user) {
		List<Menu> tree = menuService.routerList(MenuTypeEnum.ROUTER.getValue(), Func.isNotEmpty(roleIds) ? roleIds : user.getRoleId());
		return R.data(MenuWrapper.build().listNodeVO(tree));
	}


}
