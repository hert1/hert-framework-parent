package com.hert.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hert.base.service.ILogErrorService;
import com.hert.base.mapper.LogErrorMapper;
import com.hert.core.log.model.LogError;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author Chill
 * @since 2018-09-26
 */
@Service
public class LogErrorServiceImpl extends ServiceImpl<LogErrorMapper, LogError> implements ILogErrorService {

}
