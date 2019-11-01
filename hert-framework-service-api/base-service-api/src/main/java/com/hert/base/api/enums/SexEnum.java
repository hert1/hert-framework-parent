package com.hert.base.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * sex枚举
 *
 * @author hert
 */
@Getter
@AllArgsConstructor
public enum SexEnum {

	/**
	 * 男
	 */
	man("男", 1),

	/**
	 * 女
	 */
	women("女", 2),
	/**
	 * 保密
	 */
	other("保密", 3),
	;

	final String name;
	final Integer value;

	public static String getName(Integer value) {
		SexEnum[] sexEnums = values();
		for (SexEnum sexEnum : sexEnums) {
			if (sexEnum.value == value) {
				return sexEnum.name;
			}
		}
		return null;
	}

}
