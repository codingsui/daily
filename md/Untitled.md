[TOC]

# 基本问题

### 在源码中，这个@Bean方法上同时出现了@RefreshScope、@ConditionalOnMissionBean，与@Lazy 注解是否存在矛盾呢

- @RefreshScope 注解是 Spring Cloud 中定义的一个注解。其表示的意思是，该@Bean方法会以多例的形式生成会自动刷新的 Bean 实例

- @ConditionalOnMissionBean 注解表示的意思是，只有当容器中没有@Bean 要创建的实

  例时才会创建新的实例，即这里创建的@Bean 实例是单例的

- @Lazy 注解表示延迟实例化。即在当前配置类被实例化时并不会调用这里的@Bean 方法去创建实例，而是在代码执行过程中，真正需要这个@Bean 方法的实例时才会创建

这三个注解的联用不存在矛盾，其要表达的意思是，这个@Bean 会以延迟实例化的形式创建一个单例的对象，而该对象具有自动刷新功能

### Spring Cloud 中 Eureka Client 与 Eureka Server 的通信，及 Eureka Server 间的通信是如何实现的？请简单介绍一下

Spring Cloud 中 Eureka Client 与 Eureka Server 的通信，及 Eureka Server 间的通信，均采用的是 Jersey 框架。Jersey 框架是一个开源的 RESTful 框架，实现了 JAX-RS 规范。该框架的作用与 SpringMVC是相同的，其也是用户提交 URI 后，在处理器中进行路由匹配，路由到指定的后台业务。这个路由功能同样也是通过处理器完成的，只不过这里的处理器不叫Controller，而叫Resource。

### CAP定理了解么？

CAP 定理指的是在一个分布式系统中，Consistency（一致性）、 Availability（可用性）、Partition tolerance（分区容错性），三者不可兼得。

- 一致性（C）：分布式系统中多个主机之间是否能够保持数据一致的特性。即，当系统数据发生更新操作后，各个主机中的数据仍然处于一致的状态。

- 可用性（A）：系统提供的服务必须一直处于可用的状态，即对于用户的每一个请求，系统总是可以在有限的时间内对用户做出响应。
- 分区容错性（P）：分布式系统在遇到任何网络分区故障时，仍能够保证对外提供满足一致性和可用性的服务。

CAP 定理的内容是：对于分布式系统，网络环境相对是不可控的，出现网络分区是不可避免的，因此系统必须具备分区容错性。但系统不能同时保证一致性与可用性。即要么 CP，要么 AP。

### **Eureka** **与** **Zookeeper** **对比**

Eureka：AP， zk：CP

# 源码分析

## eureka client源码

### InstanceInfo

两个重要的时间戳

- lastDitdyTimestamp：记录在客户端被修改的时间， Client 端对 Instance 的修改

  主要是 Instance 的续约信息被修改。该修改会被传递到 Server 端

- lastUpdateTimestamp：记录在服务端杯修改的时间

- overriddenStatus：客户端状态,有三个方法可以修改改值

  - setOverriddenStatus()：该方法用于在 Server 端修改 instance 的 OverriddenStatus 可覆盖

    状态的。这个状态的修改一般是用户通过 Actuator 提交的 POST 请求到 Eureka Server，对其

    相应的 instance 的 OverriddenStatus 进行修改的。该状态的修改会引发 setStatusWithoutDirty()

    方法的执行

  - setStatusWithoutDirty()：该方法用于在 Server 端修改 instance 的 Status 状态，但在修改

    instance的status时不记录修改时间。这也是这个方法名中withoutDirty的意思。但这个Server

    端 Status 的修改会被发送给 Client 端。Client 端在接收到这个修改过的状态后，会引发 Client

    端调用 setStatus()方法的执行

  - setStatus()：该方法用于在Client端修改instance的status状态，并记录lastDirtyTimestamp。

    这个状态的修改是这样的：用户通过 Actuator 修改了 Server 端 instance 的 OverriddenStatus

    与 Status。Server 将修改后的 Status 发送给 Client，Client 在接收到后会调用这个 setStatus()

    方法修改 instance 的 status 状态

三个状态修改方法

- setOverridenStatus()：发生在服务端

- setStatusWithOutDirty()：发生在服务端

- setStatus()：发生在客户端

重写了equals方法

### Applications

包含了eureka server的所有注册信息，客户端获取的注册表，是一个Map

- appNameApplicationMap：key是微服务名称，value是Application
- Application：维护了一个相同名称的微服务列表

Application 类中封装了一个 Set 集合，集合元素为“可以提供该微服务的所有主机的InstanceInfo。也就是说，Applications 中封装着所有微服务的所有提供者信息。

### 心跳检测、定时更新续约信息、注册表更新

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/DC04F165B0A44C8B94B03D0F58EAD880/17155)

- 注册表更新：注册表更新分为全量更新和增量更新

### 续约关键

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/50780A8AB78943A1AF6377526CC289C5/17148)

### 基本配置（yml）

```
server:
  port: 8081
eureka:
  instance:
    instance-id: client1-8081
  client:
    register-with-eureka: true #指定是否向注册中心注册自己
    fetch-registry: true #指定客户端是否可以获取eureka注册信息
    service-url: #暴露服务中心地址
      defaultZone: http://localhost:8888/eureka
    registry-fetch-interval-seconds: 30
    cache-refresh-executor-exponential-back-off-bound: 10
spring:
  application:
    name: client1

management:
  endpoints:
    web:
      exposure:
        include: "*" #开启所有监控终端
  endpoint:
    shutdown:
      enabled: true # 开启shutdown监控终端
```

### 启动类配置

```java
@EnableDiscoveryClient
@SpringBootApplication
public class Eurekaclient1Application {
    public static void main(String[] args) {
        SpringApplication.run(Eurekaclient1Application.class,args);
    }
}
```

关键就是`EurekaClientAutoConfiguration`配置生效

## eureka server

### 基本配置

```
server:
  port: 8888

eureka:
  instance:
    hostname: localhost #指定eureka主机
  client:
    register-with-eureka: false #指定是否向注册中心注册自己
    fetch-registry: false #指定客户端是否可以获取eureka注册信息
    service-url: #暴露服务中心地址
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka
    registry-fetch-interval-seconds: 30 #定时更新间隔 默认30s
    cache-refresh-executor-exponential-back-off-bound: 10 #更新膨胀系数 默认10
spring:
  application:
    name: eureka
```

### 启动类配置

```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaTestApplication.class,args);
    }
}

@Configuration(
    proxyBeanMethods = false
)

//服务端重要配置类
@Import({EurekaServerInitializerConfiguration.class})
@ConditionalOnBean({Marker.class})
@EnableConfigurationProperties({EurekaDashboardProperties.class, InstanceRegistryProperties.class})
@PropertySource({"classpath:/eureka/server.properties"})
```

关键配置`@EnableEurekaServer`是为了服务端自动配置生效`EurekaServerAutoConfiguration`，

### InstanceResource状态变更处理器

 #### 服务端注册表

```
ConcurrentHashMap<String, Map<String, Lease<InstanceInfo>>> registry
        = new ConcurrentHashMap<String, Map<String, Lease<InstanceInfo>>>();
```

外层key：微服务名称

外层value：该服务名称下的所有机器

内层key：instanceId

内层value：续约对象



