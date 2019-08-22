package com.hert.common.constant;


import com.hert.core.launch.constant.AppConstant;

/**
 * 通用常量
 *
 * @author Chill
 */
public interface CommonConstant {


	// 分组
	String NACOS_BASE_GROUP = "BASE_CONFIG";
	String NACOS_SENTINEL_FLOW_GROUP = "SENTINEL_FLOW_GROUP";
	String NACOS_DEFAULT_GROUP = "DEFAULT_CONFIG";

	// 流控规则空间
	String SENTINEL_ROLE = "7616d86e-3660-4673-9a05-6caf42060307";
	/**
	 * nacos dev 地址
	 */
	String NACOS_DEV_ADDR = "47.101.172.209:8848";

	/**
	 * nacos prod 地址
	 */
	String NACOS_PROD_ADDR = "47.101.172.209:8848";

	/**
	 * nacos test 地址
	 */
	String NACOS_TEST_ADDR = "47.101.172.209:8848";

	/**
	 * sentinel dev 地址
	 */
	String SENTINEL_DEV_ADDR = "localhost:8080";

	/**
	 * sentinel prod 地址
	 */
	String SENTINEL_PROD_ADDR = "47.101.172.209:8080";

	/**
	 * sentinel test 地址
	 */
	String SENTINEL_TEST_ADDR = "47.101.172.209:8080";


	/**
	 * 顶级父节点id
	 */
	Integer TOP_PARENT_ID = 0;

	/**
	 * 顶级父节点名称
	 */
	String TOP_PARENT_NAME = "顶级";


	/**
	 * 默认密码
	 */
	String DEFAULT_PASSWORD = "123456";



	/**
	 * 动态获取nacos地址
	 *
	 * @param profile 环境变量
	 * @return addr
	 */
	static String nacosAddr(String profile) {
		switch (profile) {
			case (AppConstant.PROD_CODE):
				return NACOS_PROD_ADDR;
			case (AppConstant.TEST_CODE):
				return NACOS_TEST_ADDR;
			default:
				return NACOS_DEV_ADDR;
		}
	}

	/**
	 * 动态获取sentinel地址
	 *
	 * @param profile 环境变量
	 * @return addr
	 */
	static String sentinelAddr(String profile) {
		switch (profile) {
			case (AppConstant.PROD_CODE):
				return SENTINEL_PROD_ADDR;
			case (AppConstant.TEST_CODE):
				return SENTINEL_TEST_ADDR;
			default:
				return SENTINEL_DEV_ADDR;
		}
	}


}
