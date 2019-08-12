/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hert.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hert.base.api.enums.AccountTypeEnum;
import com.hert.base.mapper.DeptMapper;
import com.hert.base.service.IDeptService;
import com.hert.core.secure.utils.SecureUtil;
import com.hert.core.tool.node.ForestNodeMerger;
import com.hert.base.api.entity.Dept;
import com.hert.base.api.vo.DeptVO;
import com.hert.core.tool.utils.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {


	@Override
	public List<Dept> selectDeptByUserId(Integer userId) {
		List<Dept> depts = Arrays.asList();
		if(SecureUtil.getAccountType() == AccountTypeEnum.SU_ADMIN.getValue()) {
			depts = baseMapper.selectList(new QueryWrapper<Dept>());
		} else {
			depts = baseMapper.selectDeptByUserId(userId);
		}
		Set<Dept> childrenDept = selectChildren(depts);
		childrenDept.addAll(depts);
		depts.clear();
		depts.addAll(childrenDept);
		return depts;
	}

	@Override
	public List<String> getDeptName(String deptIds) {
		List<Dept> listDept = this.list(new QueryWrapper<Dept>().lambda().in(Dept::getId, deptIds));
		return listDept.stream().map(item -> item.getDeptName()).collect(Collectors.toList());
	}

	private Set<Dept> selectChildren(List<Dept> listDept) {
		Set<Dept> childrenDept = new HashSet<>();
		if (CollectionUtil.isEmpty(listDept)) {
			return childrenDept;
		}
		for (Dept role : listDept) {
			List<Dept> children = baseMapper.selectList(new QueryWrapper<Dept>().eq("parent_id", role.getId()));
			if (CollectionUtil.isNotEmpty(children)) {
				childrenDept.addAll(children);
			}
			Set<Dept> nextChildren = selectChildren(children);
			if (CollectionUtil.isNotEmpty(nextChildren)) {
				childrenDept.addAll(nextChildren);
			}
		}
		return childrenDept;
	}
}
