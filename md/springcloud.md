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

CA - 单点集群，满足一致性，可用性，通常在可拓展性上不太强大：往节点 A 插入新数据，但是由于分区故障导致数据无法同步，此时节点 A 和节点 B 数据不一致，为了保证一致性。客户端查询时只能返回 error，违背了 Availability
CP - 满足一致性，分区容错性的系统，通常性能不是特别的高
AP - 满足可用性，分区容错性，通过对数据一致性要求低一些

CAP 定理的内容是：对于分布式系统，网络环境相对是不可控的，出现网络分区是不可避免的，因此系统必须具备分区容错性。但系统不能同时保证一致性与可用性。即要么 CP，要么 AP。

#### **CAP中Eureka** **与** **Zookeeper** **对比**

> https://www.cnblogs.com/wei57960/p/12260228.html

Eureka：AP，eureka各个节点是平等的，几个节点的挂掉并不会影响正常节点的工作，剩余节点还是提供注册和查询服务，如果客户端向服务端注册时发现服务不可用会自动切换到其他服务节点。如果只有一台eureka服务存在，就可以保证注册服务可用，只不过查到的信息可能不是最新的。

 zk：CP，主从架构，在选举过程中会停止服务，知道选举成功后才会再次对外提供服务，优先保持一致性，才会顾及可用性。

#### CAP为什么不能同时满足？

因为网络本身无法做到 100% 可靠，有可能出故障，所以分区是一个必然的现象。如果我们选择了 CA 而放弃了 P，那么当发生分区现象时，为了保证 C，系统需要禁止写入，当有写入请求时，系统返回 error（例如，当前系统不允许写入），这又和 A 冲突了，因为 A 要求返回 no error 和 no timeout。因此，分布式系统理论上不可能选择 CA 架构，只能选择 CP 或者 AP 架构。

### eureka自我保护机制

> https://zhuanlan.zhihu.com/p/63653210
>
> https://www.cnblogs.com/xishuai/p/spring-cloud-eureka-safe.html

默认情况下，EurekaServer 在 90 秒内没有检测到服务列表中的某微服务，则会自动将该微服务从服务列表中删除。但很多情况下并不是该微服务节点（主机）出了问题，而是由于网络抖动等原因使该微服务无法被 EurekaServer 发现，即无法检测到该微服务主机的心跳。若在短暂时间内网络恢复正常，但由于 EurekaServer 的服务列表中已经没有该微服务，所以该微服务已经无法提供服务了。

在短时间内若 EurekaServer 丢失较多微服务，即 EurekaServer 收到的心跳数量小于阈值，为了保证系统的可用性（AP），给那些由于网络抖动而被认为宕机的客户端“重新复活”的机会，Eureka 会自动进入自我保护模式：服务列表只可读取、写入，不可执行删除操作。当EurekaServer 收到的心跳数量恢复到阈值以上时，其会自动退出 Self Preservation 模式。

默认15min如果85%的节点没有正常心跳，那么eureka就会任务客户端与注册中心之间的网络出现异常

`eureka.server.renewal-percent-threshold=0.85`默认85% 

`eureka.client.register-with-eureka=true`默认开启自我保护模式

![image](https://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/382972C29F3B45749F553D488BB594D5/17324)

```
【原文】Emergency (紧急情况) ! Eureka may be incorrectly claiming(判断) instances(指微服务主机) are up when they're not. Renewals(续约，指收到的微服务主机的心跳) are lesser than threshold(阈值) and hence(从此) the instances are not being expired(失效) just to be(只是为了)safe.

【翻译】紧急情况！当微服务主机联系不上时，Eureka 不能够正确判断它们是否处于 up 状态。当续约数量（指收到的微服务主机的心跳）小于阈值时，为了安全，微服务主机将不再失效。85%
核心代码
@Override
    public InstanceInfo getInstanceByAppAndId(String appName, String id, boolean includeRemoteRegions) {
        Map<String, Lease<InstanceInfo>> leaseMap = registry.get(appName);
        Lease<InstanceInfo> lease = null;
        if (leaseMap != null) {
            lease = leaseMap.get(id);
        }
        if (lease != null
                && (!isLeaseExpirationEnabled() || !lease.isExpired())) {
            return decorateInstanceInfo(lease);
        } else if (includeRemoteRegions) {
            for (RemoteRegionRegistry remoteRegistry : this.regionNameVSRemoteRegistry.values()) {
                Application application = remoteRegistry.getApplication(appName);
                if (application != null) {
                    return application.getByInstanceId(id);
                }
            }
        }
        return null;
    }
    @Override
    public boolean isLeaseExpirationEnabled() {
        if (!isSelfPreservationModeEnabled()) {
            // The self preservation mode is disabled, hence allowing the instances to expire.
            return true;
        }
        return numberOfRenewsPerMinThreshold > 0 && getNumOfRenewsInLastMin() > numberOfRenewsPerMinThreshold;
    }
```

```
每分钟请求数=客户端数量*（60/请求时间间隔）*0.85
```

### FeignClient
```
feign.client.config.default.connectTimeout=5000 #指定Feign连接提供者的超时时间
feign.client.config.default.readTimeout=5000 #指定Feign从请求到获取提供者响应的超时时间
feign.compression.request.enabled=true #开启对请求的压缩
feign.compression.request.mime-types=["text/xml","application/xml","application/json"] #指定对哪些类型的文件进行压缩
feign.compression.request.min-request-size=2048 # 指定启用压缩的最小文件大小（字节）
feign.compression.response.enabled=true #开启对客户端响应的压缩
```
@FeignClient

@EnableFeignClients

#### 各种配置
>http://blog.csdn.net/tongtong_use/article/details/78611225

### Ribbon负载均衡

- （1） RoundRobinRule
轮询策略。Ribbon 默认采用的策略。若经过一轮轮询没有找到可用的 provider，其最多
轮询 10 轮。若最终还没有找到，则返回 null。
- （2） RandomRule
随机策略，从所有可用的 provider 中随机选择一个。
- （3） RetryRule
重试策略。先按照 RoundRobinRule 策略获取 provider，若获取失败，则在指定的时限内
重试。默认的时限为 500 毫秒。
- （4） BestAvailableRule
最可用策略。选择并发量最小的 provider，即连接的消费者数量最少的 provider。
- （5） AvailabilityFilteringRule
可用过滤算法。该算法规则是：先采用轮询方式选择一个 Server，然后判断其是否处于
熔断状态，是否已经超过连接极限。若没有，则直接选择。否则再重新按照相同的方式进行
再选择。最多重试 10 次。
若 10 次后仍没有找到，则重新将所有 Server 进行判断，挑选出所有未熔断，未超过连
接极限的 Server，然后再采用轮询方式选择一个。若还没有符合条件的，则返回 null。
- （6） ZoneAvoidanceRule
zone 回避策略。根据 provider 所在 zone 及 provider 的可用性，对 provider 进行选择。 
- （7） WeightedResponseTimeRule
“权重响应时间”策略。根据每个 provider 的平均响应时间计算其权重，响应时间越快

**默认时轮询策略**
```
# 指定使用的负载均衡策略
abcmsc-provider-depart.ribbon.NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
```
## 限流算法

> https://www.cnblogs.com/cjsblog/p/9379516.html
>
> https://www.cnblogs.com/xuwc/p/9123078.html（好文章）

一般开发高并发系统常见的限流有：限制总并发数（比如数据库连接池、线程池）、限制瞬时并发数（如nginx的limit_conn模块，用来限制瞬时并发连接数）、限制时间窗口内的平均速率（如Guava的RateLimiter、nginx的limit_req模块，限制每秒的平均速率）；其他还有如限制远程接口调用速率、限制MQ的消费速率。另外还可以根据网络连接数、网络流量、CPU或内存负载等来限流。

### 令牌桶算法

原理是系统会以一个恒定的速率往桶里放令牌，而如果请求需要被处理，需要先从桶中获取一个令牌，当桶里面没有令牌的时候会拒绝服务。



![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/0335628729024CE99E44EF4F81125C48/17286)

```java
//com.netflix.discovery.util; springcloud中eureka在监听
public class RateLimiter {

    private final long rateToMsConversion;

    private final AtomicInteger consumedTokens = new AtomicInteger();
    private final AtomicLong lastRefillTime = new AtomicLong(0);

    @Deprecated
    public RateLimiter() {
        this(TimeUnit.SECONDS);
    }

    public RateLimiter(TimeUnit averageRateUnit) {
        switch (averageRateUnit) {
            case SECONDS:
                rateToMsConversion = 1000;
                break;
            case MINUTES:
                rateToMsConversion = 60 * 1000;
                break;
            default:
                throw new IllegalArgumentException("TimeUnit of " + averageRateUnit + " is not supported");
        }
    }

    public boolean acquire(int burstSize, long averageRate) {
        return acquire(burstSize, averageRate, System.currentTimeMillis());
    }

    public boolean acquire(int burstSize, long averageRate, long currentTimeMillis) {
        if (burstSize <= 0 || averageRate <= 0) { // Instead of throwing exception, we just let all the traffic go
            return true;
        }

        refillToken(burstSize, averageRate, currentTimeMillis);
        return consumeToken(burstSize);
    }

  //生成token
    private void refillToken(int burstSize, long averageRate, long currentTimeMillis) {
        long refillTime = lastRefillTime.get();//最后填充时间
        long timeDelta = currentTimeMillis - refillTime;//增量时间

        long newTokens = timeDelta * averageRate / rateToMsConversion;//生成的令牌数量
        if (newTokens > 0) {
            long newRefillTime = refillTime == 0
                    ? currentTimeMillis
                    : refillTime + newTokens * rateToMsConversion / averageRate;//填充时间
            if (lastRefillTime.compareAndSet(refillTime, newRefillTime)) {
                while (true) {
                    int currentLevel = consumedTokens.get();//当前使用令牌数量
                    int adjustedLevel = Math.min(currentLevel, burstSize); // In case burstSize decreased //防止意外情况桶满
                    int newLevel = (int) Math.max(0, adjustedLevel - newTokens);//调整令牌使用数量
                    if (consumedTokens.compareAndSet(currentLevel, newLevel)) {
                        return;
                    }
                }
            }
        }
    }

    private boolean consumeToken(int burstSize) {
        while (true) {
            int currentLevel = consumedTokens.get();
            if (currentLevel >= burstSize) {
                return false;
            }
            if (consumedTokens.compareAndSet(currentLevel, currentLevel + 1)) {
                return true;
            }
        }
    }

    public void reset() {
        consumedTokens.set(0);
        lastRefillTime.set(0);
    }
}
```

### 漏桶算法

主要目的是控制数据注入到网络的速率，平滑网络上的突发流量。漏桶算法提供了一种机制，通过它，突发流量可以被整形以便为网络提供一个稳定的流，多余流量会被丢弃掉

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/1CAD883AC62642DF8950505B145FABF3/17288)

### 两个算法的区别？

两者主要区别在于“漏桶算法”能够强行限制数据的传输速率，而“令牌桶算法”在能够限制数据的平均传输速率外，还允许某种程度的突发传输。在“令牌桶算法”中，只要令牌桶中存在令牌，那么就允许突发地传输数据直到达到用户配置的门限，所以它适合于具有突发特性的流量。

# Hystrix

## HystrixCommand

### 注解参数

```
1：commandKey：配置全局唯一标识服务的名称，比如，库存系统有一个获取库存服务，那么就可以为这个服务起一个名字来唯一识别该服务，如果不配置，则默认是@HystrixCommand注解修饰的函数的函数名。

2：groupKey：一个比较重要的注解，配置全局唯一标识服务分组的名称，比如，库存系统就是一个服务分组。通过设置分组，Hystrix会根据组来组织和统计命令的告、仪表盘等信息。Hystrix命令默认的线程划分也是根据命令组来实现。默认情况下，Hystrix会让相同组名的命令使用同一个线程池，所以我们需要在创建Hystrix命令时为其指定命令组来实现默认的线程池划分。此外，Hystrix还提供了通过设置threadPoolKey来对线程池进行设置。建议最好设置该参数，使用threadPoolKey来控制线程池组。

3：threadPoolKey：对线程池进行设定，细粒度的配置，相当于对单个服务的线程池信息进行设置，也可多个服务设置同一个threadPoolKey构成线程组。

4：fallbackMethod：@HystrixCommand注解修饰的函数的回调函数，@HystrixCommand修饰的函数必须和这个回调函数定义在同一个类中，因为定义在了同一个类中，所以fackback method可以是public/private均可。

5：commandProperties：配置该命令的一些参数，如executionIsolationStrategy配置执行隔离策略，默认是使用线程隔离，此处我们配置为THREAD，即线程池隔离。参见：com.netflix.hystrix.HystrixCommandProperties中各个参数的定义。

6：threadPoolProperties：线程池相关参数设置，具体可以设置哪些参数请见：com.netflix.hystrix.HystrixThreadPoolProperties

7：ignoreExceptions：调用服务时，除了HystrixBadRequestException之外，其他@HystrixCommand修饰的函数抛出的异常均会被Hystrix认为命令执行失败而触发服务降级的处理逻辑（调用fallbackMethod指定的回调函数），所以当需要在命令执行中抛出不触发降级的异常时来使用它，通过这个参数指定，哪些异常抛出时不触发降级（不去调用fallbackMethod），而是将异常向上抛出。

8：observableExecutionMode：定义hystrix observable command的模式；

9：raiseHystrixExceptions：任何不可忽略的异常都包含在HystrixRuntimeException中；

10：defaultFallback：默认的回调函数，该函数的函数体不能有入参，返回值类型与@HystrixCommand修饰的函数体的返回值一致。如果指定了fallbackMethod，则fallbackMethod优先级更高。
```

### 基本配置

> https://blog.csdn.net/tongtong_use/article/details/78611225
>
> https://blog.csdn.net/WYA1993/article/details/82352890?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-5.control&dist_request_id=&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-5.control=

配置原理：https://my.oschina.net/u/2529405/blog/903806?utm_medium=referral

### 原理

https://www.jianshu.com/p/e1a4d3bdf7c4

### 配置属性类型

- Execution：控制HystrixCommand.run() 的如何执行
- Fallback: 控制HystrixCommand.getFallback() 如何执行
- Circuit Breaker： 控制断路器的行为
- Metrics: 捕获和HystrixCommand 和 HystrixObservableCommand 执行信息相关的配置属性
- Request Context：设置请求上下文的属性
- Collapser Properties：设置请求合并的属性
- Thread Pool Properties：设置线程池的属性

### 参数的覆盖优先级

每个Hystrix参数都有4个地方可以配置，优先级从低到高如下，如果每个地方都配置相同的属性，则优先级高的值会覆盖优先级低的值

1. 内置全局默认值：写死在代码里的值
2.  动态全局默认属性：通过属性文件配置全局的值
3.  内置实例默认值：写死在代码里的实例的值
4.  动态配置实例属性：通过属性文件配置特定实例的值

# 源码分析

## eureka client源码

### InstanceInfo

两个重要的时间戳

- lastDitdyTimestamp：记录在客户端被修改的时间， Client 端对 Instance 的修改

  主要是 Instance 的续约信息被修改。该修改会被传递到 Server 端

- lastUpdatedTimestamp：记录在服务端修改的时间

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

#### InstanceResource的statusUpdate()处理client的状态修改请求

```java
		@PUT
    @Path("status")
    public Response statusUpdate(
            @QueryParam("value") String newStatus,
            @HeaderParam(PeerEurekaNode.HEADER_REPLICATION) String isReplication,
            @QueryParam("lastDirtyTimestamp") String lastDirtyTimestamp) {
        try {
            if (registry.getInstanceByAppAndId(app.getName(), id) == null) {
                logger.warn("Instance not found: {}/{}", app.getName(), id);
                return Response.status(Status.NOT_FOUND).build();
            }
            boolean isSuccess = registry.statusUpdate(app.getName(), id,
                    InstanceStatus.valueOf(newStatus), lastDirtyTimestamp,
                    "true".equals(isReplication));

            if (isSuccess) {
                logger.info("Status updated: {} - {} - {}", app.getName(), id, newStatus);
                return Response.ok().build();
            } else {
                logger.warn("Unable to update status: {} - {} - {}", app.getName(), id, newStatus);
                return Response.serverError().build();
            }
        } catch (Throwable e) {
            logger.error("Error updating instance {} for status {}", id,
                    newStatus);
            return Response.serverError().build();
        }
    }

@Override
    public InstanceInfo getInstanceByAppAndId(String appName, String id, boolean includeRemoteRegions) {
        Map<String, Lease<InstanceInfo>> leaseMap = registry.get(appName);
        Lease<InstanceInfo> lease = null;
        if (leaseMap != null) {
            lease = leaseMap.get(id);
        }
        if (lease != null
                && (!isLeaseExpirationEnabled() || !lease.isExpired())) {
            return decorateInstanceInfo(lease);
        } else if (includeRemoteRegions) {
            for (RemoteRegionRegistry remoteRegistry : this.regionNameVSRemoteRegistry.values()) {
                Application application = remoteRegistry.getApplication(appName);
                if (application != null) {
                    return application.getByInstanceId(id);
                }
            }
        }
        return null;
    }
//自我保护开启关键代码
@Override
    public boolean isLeaseExpirationEnabled() {
        if (!isSelfPreservationModeEnabled()) {
            // The self preservation mode is disabled, hence allowing the instances to expire.
            return true;
        }
        return numberOfRenewsPerMinThreshold > 0 && getNumOfRenewsInLastMin() > numberOfRenewsPerMinThreshold;
    }
//责任链设计模式上层处理更新，本层处理复制
@Override
    public boolean statusUpdate(final String appName, final String id,
                                final InstanceStatus newStatus, String lastDirtyTimestamp,
                                final boolean isReplication) {
        if (super.statusUpdate(appName, id, newStatus, lastDirtyTimestamp, isReplication)) {
            replicateToPeers(Action.StatusUpdate, appName, id, null, newStatus, isReplication);
            return true;
        }
        return false;
    }
```

#### ApplicationResource类的addInstance()方法处理微服务注册请求

3个地方可以请求注册。1.主动进行注册、2.定时续约、3.定时检测Client更新

```java
 @POST
    @Consumes({"application/json", "application/xml"})
    public Response addInstance(InstanceInfo info,
                                @HeaderParam(PeerEurekaNode.HEADER_REPLICATION) String isReplication) {
        logger.debug("Registering instance {} (replication={})", info.getId(), isReplication);
        // validate that the instanceinfo contains all the necessary required fields
        if (isBlank(info.getId())) {
            return Response.status(400).entity("Missing instanceId").build();
        } else if (isBlank(info.getHostName())) {
            return Response.status(400).entity("Missing hostname").build();
        } else if (isBlank(info.getIPAddr())) {
            return Response.status(400).entity("Missing ip address").build();
        } else if (isBlank(info.getAppName())) {
            return Response.status(400).entity("Missing appName").build();
        } else if (!appName.equals(info.getAppName())) {
            return Response.status(400).entity("Mismatched appName, expecting " + appName + " but was " + info.getAppName()).build();
        } else if (info.getDataCenterInfo() == null) {
            return Response.status(400).entity("Missing dataCenterInfo").build();
        } else if (info.getDataCenterInfo().getName() == null) {
            return Response.status(400).entity("Missing dataCenterInfo Name").build();
        }

        // handle cases where clients may be registering with bad DataCenterInfo with missing data
        DataCenterInfo dataCenterInfo = info.getDataCenterInfo();
        if (dataCenterInfo instanceof UniqueIdentifier) {
            String dataCenterInfoId = ((UniqueIdentifier) dataCenterInfo).getId();
            if (isBlank(dataCenterInfoId)) {
                boolean experimental = "true".equalsIgnoreCase(serverConfig.getExperimental("registration.validation.dataCenterInfoId"));
                if (experimental) {
                    String entity = "DataCenterInfo of type " + dataCenterInfo.getClass() + " must contain a valid id";
                    return Response.status(400).entity(entity).build();
                } else if (dataCenterInfo instanceof AmazonInfo) {
                    AmazonInfo amazonInfo = (AmazonInfo) dataCenterInfo;
                    String effectiveId = amazonInfo.get(AmazonInfo.MetaDataKey.instanceId);
                    if (effectiveId == null) {
                        amazonInfo.getMetadata().put(AmazonInfo.MetaDataKey.instanceId.getName(), info.getId());
                    }
                } else {
                    logger.warn("Registering DataCenterInfo of type {} without an appropriate id", dataCenterInfo.getClass());
                }
            }
        }

        registry.register(info, "true".equals(isReplication));
        return Response.status(204).build();  // 204 to be backwards compatible
    }
//关键为什么注册修改状态加了读锁？
public void register(InstanceInfo registrant, int leaseDuration, boolean isReplication) {
        try {
            read.lock();
            Map<String, Lease<InstanceInfo>> gMap = registry.get(registrant.getAppName());
            REGISTER.increment(isReplication);
            if (gMap == null) {
                final ConcurrentHashMap<String, Lease<InstanceInfo>> gNewMap = new ConcurrentHashMap<String, Lease<InstanceInfo>>();
                gMap = registry.putIfAbsent(registrant.getAppName(), gNewMap);
                if (gMap == null) {
                    gMap = gNewMap;
                }
            }
            Lease<InstanceInfo> existingLease = gMap.get(registrant.getId());
            // Retain the last dirty timestamp without overwriting it, if there is already a lease
            if (existingLease != null && (existingLease.getHolder() != null)) {
                Long existingLastDirtyTimestamp = existingLease.getHolder().getLastDirtyTimestamp();
                Long registrationLastDirtyTimestamp = registrant.getLastDirtyTimestamp();
                logger.debug("Existing lease found (existing={}, provided={}", existingLastDirtyTimestamp, registrationLastDirtyTimestamp);

                // this is a > instead of a >= because if the timestamps are equal, we still take the remote transmitted
                // InstanceInfo instead of the server local copy.
                if (existingLastDirtyTimestamp > registrationLastDirtyTimestamp) {
                    logger.warn("There is an existing lease and the existing lease's dirty timestamp {} is greater" +
                            " than the one that is being registered {}", existingLastDirtyTimestamp, registrationLastDirtyTimestamp);
                    logger.warn("Using the existing instanceInfo instead of the new instanceInfo as the registrant");
                    registrant = existingLease.getHolder();
                }
            } else {...
```

#### InstanceResource类的renewLease()方法处理Client的续约请求

```java
 		@PUT
    public Response renewLease(
            @HeaderParam(PeerEurekaNode.HEADER_REPLICATION) String isReplication,
            @QueryParam("overriddenstatus") String overriddenStatus,
            @QueryParam("status") String status,
            @QueryParam("lastDirtyTimestamp") String lastDirtyTimestamp) {
        boolean isFromReplicaNode = "true".equals(isReplication);
        boolean isSuccess = registry.renew(app.getName(), id, isFromReplicaNode);

        // Not found in the registry, immediately ask for a register
        if (!isSuccess) {
            logger.warn("Not Found (Renew): {} - {}", app.getName(), id);
            return Response.status(Status.NOT_FOUND).build();
        }
        // Check if we need to sync based on dirty time stamp, the client
        // instance might have changed some value
        Response response;
        if (lastDirtyTimestamp != null && serverConfig.shouldSyncWhenTimestampDiffers()) {
            response = this.validateDirtyTimestamp(Long.valueOf(lastDirtyTimestamp), isFromReplicaNode);
            // Store the overridden status since the validation found out the node that replicates wins
            if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()
                    && (overriddenStatus != null)
                    && !(InstanceStatus.UNKNOWN.name().equals(overriddenStatus))
                    && isFromReplicaNode) {
                registry.storeOverriddenStatusIfRequired(app.getAppName(), id, InstanceStatus.valueOf(overriddenStatus));
            }
        } else {
            response = Response.ok().build();
        }
        logger.debug("Found (Renew): {} - {}; reply status={}", app.getName(), id, response.getStatus());
        return response;
    }

public boolean renew(String appName, String id, boolean isReplication) {
        RENEW.increment(isReplication);
        Map<String, Lease<InstanceInfo>> gMap = registry.get(appName);
        Lease<InstanceInfo> leaseToRenew = null;
        if (gMap != null) {
            leaseToRenew = gMap.get(id);
        }
        if (leaseToRenew == null) {
            RENEW_NOT_FOUND.increment(isReplication);
            logger.warn("DS: Registry: lease doesn't exist, registering resource: {} - {}", appName, id);
            return false;
        } else {
            InstanceInfo instanceInfo = leaseToRenew.getHolder();
            if (instanceInfo != null) {
                // touchASGCache(instanceInfo.getASGName());
                InstanceStatus overriddenInstanceStatus = this.getOverriddenInstanceStatus(
                        instanceInfo, leaseToRenew, isReplication);
                if (overriddenInstanceStatus == InstanceStatus.UNKNOWN) {
                    logger.info("Instance status UNKNOWN possibly due to deleted override for instance {}"
                            + "; re-register required", instanceInfo.getId());
                    RENEW_NOT_FOUND.increment(isReplication);
                    return false;
                }
                if (!instanceInfo.getStatus().equals(overriddenInstanceStatus)) {
                    logger.info(
                            "The instance status {} is different from overridden instance status {} for instance {}. "
                                    + "Hence setting the status to overridden status", instanceInfo.getStatus().name(),
                                    instanceInfo.getOverriddenStatus().name(),
                                    instanceInfo.getId());
                    instanceInfo.setStatusWithoutDirty(overriddenInstanceStatus);

                }
            }
            renewsLastMin.increment();
            leaseToRenew.renew();
            return true;
        }
    }
```

#### InstanceResource类中的cancelLease()方法处理Client的下架请求

```java
@DELETE
    public Response cancelLease(
            @HeaderParam(PeerEurekaNode.HEADER_REPLICATION) String isReplication) {
        try {
            boolean isSuccess = registry.cancel(app.getName(), id,
                "true".equals(isReplication));

            if (isSuccess) {
                logger.debug("Found (Cancel): {} - {}", app.getName(), id);
                return Response.ok().build();
            } else {
                logger.info("Not Found (Cancel): {} - {}", app.getName(), id);
                return Response.status(Status.NOT_FOUND).build();
            }
        } catch (Throwable e) {
            logger.error("Error (cancel): {} - {}", app.getName(), id, e);
            return Response.serverError().build();
        }
    }

protected boolean internalCancel(String appName, String id, boolean isReplication) {
        try {
            read.lock();
            CANCEL.increment(isReplication);
            Map<String, Lease<InstanceInfo>> gMap = registry.get(appName);
            Lease<InstanceInfo> leaseToCancel = null;
            if (gMap != null) {
                leaseToCancel = gMap.remove(id);
            }
            recentCanceledQueue.add(new Pair<Long, String>(System.currentTimeMillis(), appName + "(" + id + ")"));
            InstanceStatus instanceStatus = overriddenInstanceStatusMap.remove(id);
            if (instanceStatus != null) {
                logger.debug("Removed instance id {} from the overridden map which has value {}", id, instanceStatus.name());
            }
            if (leaseToCancel == null) {
                CANCEL_NOT_FOUND.increment(isReplication);
                logger.warn("DS: Registry: cancel failed because Lease is not registered for: {}/{}", appName, id);
                return false;
            } else {
                leaseToCancel.cancel();
                InstanceInfo instanceInfo = leaseToCancel.getHolder();
                String vip = null;
                String svip = null;
                if (instanceInfo != null) {
                    instanceInfo.setActionType(ActionType.DELETED);
                    recentlyChangedQueue.add(new RecentlyChangedItem(leaseToCancel));
                    instanceInfo.setLastUpdatedTimestamp();
                    vip = instanceInfo.getVIPAddress();
                    svip = instanceInfo.getSecureVipAddress();
                }
                invalidateCache(appName, vip, svip);
                logger.info("Cancelled instance {}/{} (replication={})", appName, id, isReplication);
            }
        } finally {
            read.unlock();
        }

        synchronized (lock) {
            if (this.expectedNumberOfClientsSendingRenews > 0) {
                // Since the client wants to cancel it, reduce the number of clients to send renews.
                this.expectedNumberOfClientsSendingRenews = this.expectedNumberOfClientsSendingRenews - 1;
                updateRenewsPerMinThreshold();
            }
        }

        return true;
    }
```

#### ApplicationsResource的getContainers处理Client全下载请求

#### ApplicationsResource的getContainerDifferential处理Client增量下载请求