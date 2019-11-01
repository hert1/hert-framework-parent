hert-framework-parent
===

所使用相关技术：
---

注册中心：Nacos

网关中心：Gateway

服务配置：Nacos

数据库连接：Druid、MybatisPlus

服务鉴权：Spring Oauth2(参考)+JWT

数据库：mysql

服务之间调用： Feign

熔断机制：Hystrix+Sentinel


系统各模块介绍
---

hert-framework-parent：项目父模块，所有以下子模块依赖该父模块（可在该pom文件加入所需要的依赖）

hert-framework-core: 系统核心配置
    
    hert-framework-core-boot: 系统中的所有配置(包含redis， mybatis， swagger)
    hert-framework-core-log: 系统中收集日志包
    hert-framework-core-mybatis: 自定义关于mybatis以及和数据库相关的配置
    hert-framework-core-secure: 安全模块
    hert-framework-core-swagger: api 接口文档
    hert-framework-core-tool: 自己封装的工具
hert-framework-auth: 有关授权登录相关

hert-framework-common: 系统中公共配置

hert-framework-gateway: 网关层，对接口的初步鉴权，以及文档的封装

hert-framework-service: 业务服务层

    base-service: 基础权限登录模块
hert-framework-service-api: feign远程调用api接口

    base-service-api:基础权限登录模块feign远程调用api接口

未完待续...

占用的端口
---

hert-framework-gateway： 80

base-service： 8200

hert-framework-auth : 8100


