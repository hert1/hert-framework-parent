package com.hert.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hert.base.api.form.query.LogQuery;
import com.hert.base.service.ILogApiService;
import com.hert.core.log.model.LogApi;
import com.hert.core.log.model.LogApiVo;
import com.hert.core.mp.support.Condition;
import com.hert.core.mp.support.Query;
import com.hert.core.tool.api.R;
import com.hert.core.tool.utils.BeanUtil;
import com.hert.core.tool.utils.Func;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 控制器
 *
 * @author Chill
 * @since 2018-09-26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class LogApiController {

	private ILogApiService logService;

	/**
	 * 查询单条
	 */
	@GetMapping("/detail")
	public R<LogApi> detail(LogApi log) {
		return R.data(logService.getOne(Condition.getQueryWrapper(log)));
	}

	/**
	 * 查询多条(分页)
	 */
	@GetMapping("/list")
	public R<IPage<LogApiVo>> list( LogQuery logQuery, Query query) {
		IPage<LogApi> pages = logService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(logQuery, LogApi.class));
		List<LogApiVo> records = pages.getRecords().stream().map(logApi -> {
			LogApiVo vo = BeanUtil.copy(logApi, LogApiVo.class);
			vo.setStrId(Func.toStr(logApi.getId()));
			return vo;
		}).collect(Collectors.toList());
		IPage<LogApiVo> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
		pageVo.setRecords(records);
		return R.data(pageVo);
	}

}
