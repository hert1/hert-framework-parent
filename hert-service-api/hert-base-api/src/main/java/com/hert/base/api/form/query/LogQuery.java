package com.hert.base.api.form.query;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hert
 * @create 2019/8/10
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "LogQuery对象", description = "LogQuery对象")
public class LogQuery {


    /**
     * 服务器名
     */
    private String serverHost_equal;
    /**
     * 服务器IP地址
     */
    private String serverIp_equal;
    /**
     * 系统环境
     */
    private String env_equal;

}
