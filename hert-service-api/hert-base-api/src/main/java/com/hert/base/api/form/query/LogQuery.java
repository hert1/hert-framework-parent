package com.hert.base.api.form.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hert.core.mp.support.SqlKeyword;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    @JsonProperty("serverHost"+ SqlKeyword.EQUAL)
    private String serverHost;
    /**
     * 服务器IP地址
     */
    private String serverIp;
    /**
     * 系统环境
     */
    private String env;

}
