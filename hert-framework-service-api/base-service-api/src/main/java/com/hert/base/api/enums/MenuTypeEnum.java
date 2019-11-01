package com.hert.base.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型枚举
 *
 * @author hert
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {

	/**
	 * router
	 */
	ROUTER("router", 1),

	/**
	 * button
	 */
	BUTTON("button", 2),

	/**
	 * all
	 */
	ALL("all", 0),
	;

	final String name;
	final Integer value;

}
