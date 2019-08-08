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
	;

	final String name;
	final int category;

}
