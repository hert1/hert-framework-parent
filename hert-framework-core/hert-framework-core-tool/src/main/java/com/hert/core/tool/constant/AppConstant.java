package com.hert.core.tool.constant;

/**
 * 系统常量
 *
 * @author hert
 */
public interface AppConstant {

	/**
	 * 应用版本
	 */
	String APPLICATION_VERSION = "1.0";

	/**
	 * 基础包
	 */
	String BASE_PACKAGES = "com.hert";

	/**
	 * 应用名前缀
	 */
	String APPLICATION_NAME_PREFIX = "hert-";
	/**
	 * 网关模块名称
	 */
	String APPLICATION_GATEWAY_NAME = APPLICATION_NAME_PREFIX + "gateway";
	/**
	 * 授权模块名称
	 */
	String APPLICATION_AUTH_NAME = APPLICATION_NAME_PREFIX + "auth";
	/**
	 * 业务基础功能模块名称
	 */
	String APPLICATION_BASE_NAME = APPLICATION_NAME_PREFIX + "base";

	/**
	 * 开发环境
	 */
	String DEV_CODE = "dev";
	/**
	 * 生产环境
	 */
	String PROD_CODE = "prod";
	/**
	 * 测试环境
	 */
	String TEST_CODE = "test";

	/**
	 * 代码部署于 linux 上，工作默认为 mac 和 Windows
	 */
	String OS_NAME_LINUX = "LINUX";

}
