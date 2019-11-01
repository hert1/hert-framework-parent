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
@ApiModel(value = "ClientQuery对象", description = "ClientQuery对象")
public class ClientQuery {


    /**
     * 客户端id
     */
    private String clientId_equal;
    /**
     * 客户端密钥
     */
    private String clientSecret_like;

}
