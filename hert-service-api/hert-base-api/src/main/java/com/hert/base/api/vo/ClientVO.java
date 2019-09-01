package com.hert.base.api.vo;

import com.hert.base.api.entity.Client;
import com.hert.base.api.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 视图实体类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ClientVO对象", description = "ClientVO对象")
public class ClientVO extends Client {
	private static final long serialVersionUID = 1L;

	/*
	 *创建人
	 * */
	private String createBy;

	/*
	*修改人
	* */
	private String updateBy;

}
