package com.hert.base.controller;

import com.hert.base.api.entity.Dept;
import com.hert.base.api.form.edit.EditForm;
import com.hert.base.api.form.edit.DeptForm;
import com.hert.base.service.IDeptService;
import com.hert.base.wrapper.DeptWrapper;
import com.hert.core.boot.ctrl.HertController;
import com.hert.core.secure.LoginUser;
import com.hert.core.tool.api.R;
import com.hert.core.tool.node.INode;
import com.hert.core.tool.utils.Func;
import io.swagger.annotations.Api;
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
import java.util.List;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dept")
@Api(value = "部门", tags = "部门")
public class DeptController extends HertController {

	private IDeptService deptService;

	/**
	 * 获取部门树形结构
	 *
	 * @return
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<INode>> tree(@ApiIgnore @RequestParam(required = false) Integer userId, LoginUser loginUser) {
		List<Dept> list = deptService.selectDeptByUserId(Func.toInt(userId, loginUser.getUserId()));
		return R.data(DeptWrapper.build().listNodeVO(list));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入dept")
	public R submit(@Valid @RequestBody DeptForm form, LoginUser user) {
		return R.status(deptService.saveOrUpdate(Func.copy(form, Dept.class)));
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@Valid @RequestBody EditForm form) {
		return R.status(deptService.removeByIds(form.getIdList()));
	}


}
