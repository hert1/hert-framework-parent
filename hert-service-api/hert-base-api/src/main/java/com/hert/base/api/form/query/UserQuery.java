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
public class UserQuery {


    /**
     * id
     */
    private Integer id;

    /**
     * 账号
     */
    private String account_equal;
    /**
     * 名字
     */
    private String name_like;
    /**
     * 电话
     */
    private String phone_like;

}
