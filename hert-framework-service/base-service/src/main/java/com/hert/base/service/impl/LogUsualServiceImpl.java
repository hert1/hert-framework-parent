package com.hert.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hert.base.mapper.LogUsualMapper;
import com.hert.base.service.ILogUsualService;
import com.hert.core.log.model.LogUsual;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author Chill
 * @since 2018-10-12
 */
@Service
public class LogUsualServiceImpl extends ServiceImpl<LogUsualMapper, LogUsual> implements ILogUsualService {

}
