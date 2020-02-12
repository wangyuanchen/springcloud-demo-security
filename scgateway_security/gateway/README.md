# 网关模块
基于spring cloud gateway的API网关服务。提供了一套微服务架构下，网关服务：路由、鉴权和授权认证的项目案例。


## change logs

### 2019.10.22
初版，当前版本升级Spring Cloud的版本到`Greenwich.SR2`。本次功能如下：

- 增加网关路由日志
- 增加网关限流
- 增加网关授权认证、并增强请求头信息

## 项目使用方法

- 路由日志remoteAddrKeyResolver

在配置文件中，打开日志开关（gateway.log.enable=true）

- 限流

在配置文件中，开启限流过滤器RequestRateLimiter，指定一个KeyResolver接口的实现类，它实际上指定了限流的条件。
具体说明见：<https://windmt.com/2018/05/09/spring-cloud-15-spring-cloud-gateway-ratelimiter/>
```
spring:
  cloud:
    gateway:
      default-filters:
        - name: RequestRateLimiter
          args:
            key-resolver: "#{@remoteAddrKeyResolver}"
            redis-rate-limiter.replenishRate: 5
            redis-rate-limiter.burstCapacity: 10
```
样例RemoteAddrKeyResolver类，提供了根据调用侧IP地址生成Key的实现

- 鉴权

在配置文件中，打开鉴权开关（gateway.authorization.enable=true），并且，向容器注入TokenChecker接口的实现类（参见AuthorizationConfig）。

DefaultTokenChecker是TokenChecker接口的默认实现，根据配置项gateway.authorization.checkTokenServiceName和gateway.authorization.checkTokenEndpointUrl请求鉴权中心进行鉴权。本实现还提供服务接口白名单（例如：登陆接口），配置gateway.authorization.whitelist即可。

### 涉及到的组件与服务

微服务架构基于Spring Cloud，用到了部分Spring Cloud提供的组件。

- eureka 服务发现组件，所有的服务注册到该组件
- ribbon 客户端负载均衡组件，通过eureka进行服务发现，在调用侧（客户端）进行负载均衡
- spring cloud gateway API网关组件，提供可配置的路由转发、请求过滤、限流




