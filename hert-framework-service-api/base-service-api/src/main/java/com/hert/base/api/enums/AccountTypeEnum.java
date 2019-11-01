package com.hert.base.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账号类型枚举
 *
 * @author hert
 */
@Getter
@AllArgsConstructor
public enum AccountTypeEnum {

	/**
	 * su_admin
	 */
	SU_ADMIN("su_admin", 1),

	/**
	 * admin
	 */
	ADMIN("admin", 2),

	/**
	 * user
	 */
	USER("user", 3),
	;

	final String name;
	final Integer value;

}
