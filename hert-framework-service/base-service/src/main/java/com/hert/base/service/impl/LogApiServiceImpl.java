package com.hert.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hert.base.mapper.LogApiMapper;
import com.hert.base.service.ILogApiService;
import com.hert.core.log.model.LogApi;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class LogApiServiceImpl extends ServiceImpl<LogApiMapper, LogApi> implements ILogApiService {


}
