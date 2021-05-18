[TOC]

# 基本概念

## 一致性和算法

### 两阶段提交

第一阶段：当要执行一个分布式事务的时候，事务发起者首先向协调者发起事务请求，然后协调者会给所有参与者发送 `prepare` 请求（其中包括事务内容）告诉参与者你们需要执行事务了，如果能执行我发的事务内容那么就先执行但不提交，执行后请给我回复。然后参与者收到 `prepare` 消息后，他们会开始执行事务（但不提交），并将 `Undo` 和 `Redo` 信息记入事务日志中，之后参与者就向协调者反馈是否准备好了。

第二阶段：第二阶段主要是协调者根据参与者反馈的情况来决定接下来是否可以进行事务的提交操作，即提交事务或者回滚事务。

比如这个时候 **所有的参与者** 都返回了准备好了的消息，这个时候就进行事务的提交，协调者此时会给所有的参与者发送 **`Commit` 请求** ，当参与者收到 `Commit` 请求的时候会执行前面执行的事务的 **提交操作** ，提交完毕之后将给协调者发送提交成功的响应。

而如果在第一阶段并不是所有参与者都返回了准备好了的消息，那么此时协调者将会给所有参与者发送 **回滚事务的 `rollback` 请求**，参与者收到之后将会 **回滚它在第一阶段所做的事务处理** ，然后再将处理情况返回给协调者，最终协调者收到响应后便给事务发起者返回处理失败的结果。

![image-20210412131849953](/Users/suidada/Library/Application Support/typora-user-images/image-20210412131849953.png)

### 两阶段缺点

- **单点故障问题**，如果协调者挂了那么整个系统都处于不可用的状态了。
- **阻塞问题**，即当协调者发送 `prepare` 请求，参与者收到之后如果能处理那么它将会进行事务的处理但并不提交，这个时候会一直占用着资源不释放，如果此时协调者挂了，那么这些资源都不会再释放了，这会极大影响性能。
- **数据不一致问题**，比如当第二阶段，协调者只发送了一部分的 `commit` 请求就挂了，那么也就意味着，收到消息的参与者会进行事务的提交，而后面没收到的则不会进行事务提交，那么这时候就会产生数据不一致性问题。

### 3PC（ phase-commit三阶段提交）

1. **CanCommit阶段**：协调者向所有参与者发送 `CanCommit` 请求，参与者收到请求后会根据自身情况查看是否能执行事务，如果可以则返回 YES 响应并进入预备状态，否则返回 NO 。
2. **PreCommit阶段**：协调者根据参与者返回的响应来决定是否可以进行下面的 `PreCommit` 操作。如果上面参与者返回的都是 YES，那么协调者将向所有参与者发送 `PreCommit` 预提交请求，**参与者收到预提交请求后，会进行事务的执行操作，并将 `Undo` 和 `Redo` 信息写入事务日志中** ，最后如果参与者顺利执行了事务则给协调者返回成功的响应。如果在第一阶段协调者收到了 **任何一个 NO** 的信息，或者 **在一定时间内** 并没有收到全部的参与者的响应，那么就会中断事务，它会向所有参与者发送中断请求（abort），参与者收到中断请求之后会立即中断事务，或者在一定时间内没有收到协调者的请求，它也会中断事务。
3. **DoCommit阶段**：这个阶段其实和 `2PC` 的第二阶段差不多，如果协调者收到了所有参与者在 `PreCommit` 阶段的 YES 响应，那么协调者将会给所有参与者发送 `DoCommit` 请求，**参与者收到 `DoCommit` 请求后则会进行事务的提交工作**，完成后则会给协调者返回响应，协调者收到所有参与者返回的事务提交成功的响应之后则完成事务。若协调者在 `PreCommit` 阶段 **收到了任何一个 NO 或者在一定时间内没有收到所有参与者的响应** ，那么就会进行中断请求的发送，参与者收到中断请求后则会 **通过上面记录的回滚日志** 来进行事务的回滚操作，并向协调者反馈回滚状况，协调者收到参与者返回的消息后，中断事务。

![image-20210412132350049](/Users/suidada/Library/Application Support/typora-user-images/image-20210412132350049.png)



## CAP理论

- **数据一致性（consistency）**：如果系统对一个写数据返回成功，那么之后的读请求都必须读到这个新数据，如果返回失败，那么所有的读操作都不能读到这个数据。

- 系统可用性（availability）：所有的读写请求在一定时间内都可以得到响应，不会一直等待

- 分区容忍性（partition tolerance）：在网络分区的情况下，被分割的节点仍能对外提供服务

### 为什么CAP，只能存在CP或者AP？

在分布式系统的前提下，P是必然存在的，因为要保证分布式的分区容错性。

假设选择了CA，放弃P。那么当发生分区现象时，为了保证C数据一致性，那么必然要禁止写入，当有写入的话系统返回error，这又和A冲突了。

假设选择CAP同时满足，由于P的存在，服务间的网络时不稳定的，存在一定概率的丢包，C就无法保证，节点可能在1上写成功，在2上失败，此时读取该数据的话节点1和2就出现了C数据不一致情况，为了保持数据一致性那么必然要所有的写请求失败，也保证不了A可用性了，所以只能选择AP或CP。

## BASE理论

cap只能在ca之间二取其一，base理论降低了发生分区容错时对ca的要求

1. 基本可用：允许可用性降低（响应延长，服务降级（只保证核心的服务，例如首页图片展示默认图片等））
2. 软状态：允许系统中存在中间状态，中间状态不会影响系统的整体可用性（eg：支付中，付款中）
3. 最终一致性：节点数据同步可以存在时延，但在一定的期限后必须达到数据的一致性，状态变为最终状态。

## 数据一致性模型

- 强一致性：更新操作后后续任何进程都会读取到最新的值
- 弱一致性：数据写入成功后，不保证立即可以读取到最新的值。
- 最终一致性：是弱一致性的特例，在一定的不一致时间窗口后才能读到最新的值，最终都达到一个一致的状态
  - 因果一致性：请求A在系统1中更新的数据，在返回系统2中也可以读取到系统1中更新的数据，eg：你发布朋友圈，别人才能评论，你才能对别人的评论进行回复，是一个有前后顺序的过程
  - 会话一致性：将数据的访问定在一个会话中，约定了系统能保证在同一个有效会话中实现读写一致性

## 选举算法quorum、waro机制

- waro：简单的副本控制协议，只有所有的副本都更新成功，这次写操作才算成功。在kafak中其实对其进行变种，在ISR中的副本全部写入即可。
- quorum：假设有 N 个副本，更新操作 w在 W 个副本中更新成功之后，则认为此次更新操作 wi 成功，把这次成功提交的更新操作对应的数据叫做：“成功提交的数据”。对于读操作而言，至少需要读 R 个副本，其中，W+R>N ，即 W 和 R 有重叠，一般，W+R=N+1。Zookeeper的选举机制是遵循了Quorum的，这也是为什么我们部署Zookeeper必须要求有奇数个Cluster可用的原因。这样一是能保证Leader选举时不会出现平票的情况，避免出现脑裂。二是Leader在向Follower同步数据的时候，必须要超过半数的Follower同步成功，才会认为数据写入成功。

## paxos算法

> https://www.cnblogs.com/linbingdong/p/6253479.html 由浅入深 讲解原理
>
> https://blog.csdn.net/u013679744/article/details/79222103 原理深入话

paxos算法解决的事在分布式系统中就某个值达成一致。Paxos是基于消息传递的具有高度容错性的分布式一致性算法。Paxos算法引入了过半的概念，解决了2PC，3PC的太过保守的缺点，且使算法具有了很好的容错性，另外Paxos算法支持分布式节点角色之间的轮换，这极大避免了分布式单点的出现，因此Paxos算法既解决了无限等待问题，也解决了脑裂问题，是目前来说最优秀的分布式一致性算法。其中，Zookeeper的ZAB算法和Raft一致性算法都是基于Paxos的。

### 角色

- Proposer提议者：负责提出议案，只要Propose发的提案被半数以上的Acceptor接受，那么就认为该提案的value被选定
- Acceptor接受者：负责对议案进行裁决，接受或否定，只要Acceptor接受了某个提案，那么就认为该提案的value被选定
- Leaner记录员：负责学习提案的结果，Acceptor高速Leaner哪个valye被选定没那么learner就认为哪个value被选定

### 算法过程

- 阶段一（prepare）：
  - Proposer选择一个提案编号N，然后向半数以上的Acceptor发送编号为N的Prepare请求。Pareper（N）
  - 如果一个Acceptor收到一个编号为N的Prepare请求，如果小于它已经响应过的请求，则拒绝，不回应或回复error。若N大于该Acceptor已经响应过的所有Prepare请求的编号（maxN），那么它就会将它已经接受过（已经经过第二阶段accept的提案）的编号最大的提案（如果有的话，如果还没有的accept提案的话返回{pok，null，null}）作为响应反馈给Proposer，同时该Acceptor承诺不再接受任何编号小于N的提案。
- 阶段二（accept）：
  - 如果一个Proposer收到半数以上Acceptor对其发出的编号为N的Prepare请求的响应，那么它就会发送一个针对[N,V]提案的Accept请求给半数以上的Acceptor。注意：V就是收到的响应中编号最大的提案的value（某个acceptor响应的它已经通过的{acceptN，acceptV}），如果响应中不包含任何提案，那么V就由Proposer自己决定。
  -  如果Acceptor收到一个针对编号为N的提案的Accept请求，只要该Acceptor没有对编号大于N的Prepare请求做出过响应，它就接受该提案。如果N小于Acceptor已经响应的prepare请求，则拒绝，不回应或回复error（当proposer没有收到过半的回应，那么他会重新进入第一阶段，递增提案号，重新提出prepare请求）。

在上面的运行过程中，每一个Proposer都有可能会产生多个提案。但只要每个Proposer都遵循如上述算法运行，就一定能保证算法执行的正确性。

![image-20210411231432377](/Users/suidada/Library/Application Support/typora-user-images/image-20210411231432377.png)

eg：

具体实例理解：
问题背景：假设我们有下图的系统，想要在server1，server2，server3选一个master。
![image-20210411225503454](/Users/suidada/Library/Application Support/typora-user-images/image-20210411225503454.png)

- prepare阶段
  -  每个server向proposer发送消息，表示自己要当leader，假设proposer收到消息的时间不一样，顺序是： proposer2 -> proposer1 -> proposer3，消息编号依次为1、2、3。
        紧接着，proposer将消息发给acceptor中超过半数的子成员(这里选择两个)，如图所示，proposer2向acceptor2和acceptor3发送编号为1的消息，proposer1向acceptor1和accepto2发送编号为2的消息，proposer3向acceptor2和acceptor3发送编号为3的消息。
  - 假设这时proposer1发送的消息先到达acceptor1和acceptor2，它们都没有接收过请求，所以接收该请求并返回【pok，null，null】给proposer1，同时acceptor1和acceptor2承诺不再接受编号小于2的请求；
      紧接着，proposer2的消息到达acceptor2和acceptor3，acceptor3没有接受过请求，所以返回proposer2 【pok，null，null】，acceptor3并承诺不再接受编号小于1的消息。而acceptor2已经接受proposer1的请求并承诺不再接收编号小于2的请求，所以acceptor2拒绝proposer2的请求；
        最后，proposer3的消息到达acceptor2和acceptor3，它们都接受过提议，但编号3的消息大于acceptor2已接受的2和acceptor3已接受的1，所以他们都接受该提议，并返回proposer3 【pok，null，null】；
        此时，proposer2没有收到过半的回复，所以重新取得编号4，并发送给acceptor2和acceptor3，此时编号4大于它们已接受的提案编号3，所以接受该提案，并返回proposer2 【pok，null，null】。
- accept阶段
  - Proposer3收到半数以上（两个）的回复，并且返回的value为null，所以，proposer3提交了【3，server3】的提案。
      Proposer1也收到过半回复，返回的value为null，所以proposer1提交了【2，server1】的提案。
        Proposer2也收到过半回复，返回的value为null，所以proposer2提交了【4，server2】的提案。
    （这里要注意，并不是所有的proposer都达到过半了才进行第二阶段，这里只是一种特殊情况）
  - Acceptor1和acceptor2接收到proposer1的提案【2，server1】，acceptor1通过该请求，acceptor2承诺不再接受编号小于4的提案，所以拒绝；
      Acceptor2和acceptor3接收到proposer2的提案【4，server2】，都通过该提案；
        Acceptor2和acceptor3接收到proposer3的提案【3，server3】，它们都承诺不再接受编号小于4的提案，所以都拒绝。
    所以proposer1和proposer3会再次进入第一阶段，但这时候 Acceptor2和acceptor3已经通过了提案（AcceptN = 4，AcceptV=server2），并达成了多数，所以proposer会递增提案编号，并最终改变其值为server2。最后所有的proposer都肯定会达成一致，这就迅速的达成了一致。
      此时，过半的acceptor（acceptor2和acceptor3）都接受了提案【4，server2】，learner感知到提案的通过，learner开始学习提案，所以server2成为最终的leader。

### 如何保证Paxos算法的活性（活锁）

![image-20210411230855842](/Users/suidada/Library/Application Support/typora-user-images/image-20210411230855842.png)

通过选取**主Proposer**，就可以保证Paxos算法的活性。至此，我们得到一个**既能保证安全性，又能保证活性**的**分布式一致性算法**——**Paxos算法**。

### paxos 算法的死循环问题



### Learner学习的三种方案

![image-20210411230937829](/Users/suidada/Library/Application Support/typora-user-images/image-20210411230937829.png)



## raft算法



## eureka与zk的区别

![image-20210412162324155](/Users/suidada/Library/Application Support/typora-user-images/image-20210412162324155.png)

## 什么是zk

> https://dbaplus.cn/news-141-1875-1.html

ZooKeeper 是一个分布式协调服务的开源框架。主要用来解决分布式集群中应用系统的一致性的问题，例如怎样避免同时操作同一数据造成脏读的问题。ZooKeeper 本质上是一个分布式的小文件存储系统。提供基于类似于文件系统的目录树方式的数据存储，并且可以对树种 的节点进行有效管理。从而来维护和监控你存储的数据的状态变化。将通过监控这些数据状态的变化，从而可以达到基于数据的集群管理。诸如：统一命名服务（dubbo）、分布式配置管理（solr的配置集中管理）、分布式消息队列（sub/pub）、分布式锁、分布式协调等功能。

## zk数据模型

ZooKeeper 数据模型采用层次化的多叉树形结构，每个节点上都可以存储数据，这些数据可以是数字、字符串或者是二级制序列。并且。每个节点还可以拥有 N 个子节点，最上层是根节点以“/”来代表。每个数据节点在 ZooKeeper 中被称为 **znode**，它是 ZooKeeper 中数据的最小单元。并且，每个 znode 都一个唯一的路径标识。

强调一句：**ZooKeeper 主要是用来协调服务的，而不是用来存储业务数据的，所以不要放比较大的数据在 znode 上，ZooKeeper 给出的上限是每个结点的数据大小最大是 1M。**

## ZooKeeper 提供了什么？

- 文件系统：Zookeeper 提供一个多层级的节点命名空间（节点称为 znode）。与文件系统不同的是，这些节点都可以设置关联的数据，而文件系统中只有文件节点可以存放数据而目录节点不行。Zookeeper 为了保证高吞吐和低延迟，在内存中维护了这个树状的目录结构，这种特性使得 Zookeeper 不能用于存放大量的数据，每个节点的存放数据上限为1M。
- 通知机制

## zk缺点

1. zookeeper master就只能照顾一个机房，其他机房运行的业务模块由于没有master都只能停掉，对网络抖动非常敏感。
2. 选举过程速度很慢且zk选举期间无法对外提供服务。
3. zk的性能有限:典型的zookeeper的tps大概是一万多，无法覆盖系统内部每天动辄几十亿次的调用。因此每次请求都去zookeeper获取业务系统master信息是不可能的。因此zookeeper的client必须自己缓存业务系统的master地址。
4. zk本身的权限控制非常薄弱.
5. **羊群效应**: 所有的客户端都尝试对一个临时节点去加锁，当一个锁被占有的时候，其他的客户端都会监听这个临时节点。一旦锁被释放，Zookeeper反向通知添加监听的客户端，然后大量的客户端都尝试去对同一个临时节点创建锁，最后也只有一个客户端能获得锁，但是大量的请求造成了很大的网络开销，加重了网络的负载，影响Zookeeper的性能.
   1.  **解决方法**:是获取锁时创建一个临时顺序节点，顺序最小的那个才能获取到锁，之后尝试加锁的客户端就监听自己的上一个顺序节点，当上一个顺序节点释放锁之后，自己尝试加锁，其余的客户端都对上一个临时顺序节点监听，不会一窝蜂的去尝试给同一个节点加锁导致羊群效应。
6. zk进行读取操作，读取到的数据可能是过期的旧数据，不是最新的数据。如果一个zk集群有10000台节点，当进行写入的时候，如果已经有6K个节点写入成功，zk就认为本次写请求成功。但是这时候如果一个客户端读取的刚好是另外4K个节点的数据，那么读取到的就是旧的过期数据。

## zk应用场景

- 维护配置信息：一个**集群动辄上千台**服务器，此时如果再一台台服务器逐个修改配置文件那将是非常繁琐且危险的的操作，因此就**需要一种服务**，**能够高效快速且可靠地完成配置项的更改等操作**，并能够保证各配置项在每台服务器上的数据一致性。**`zookeeper`就可以提供这样一种服务**，其使用`Zab`这种一致性协议来保证一致性。现在有很多开源项目使用`zookeeper`来维护配置，如在 `hbase`中，客户端就是连接一个 `zookeeper`，获得必要的 `hbase`集群的配置信息，然后才可以进一步操作。还有在开源的消息队列 `kafka`中，也便用`zookeeper`来维护 `brokers`的信息。在 `alibaba`开源的`soa`框架`dubbo`中也广泛的使用`zookeeper`管理一些配置来实现服务治理。

- 分布式锁管理：独占锁，获取数据之前要求所有的应用去zk集群的指定目录去创建一个临时非序列化的节点。谁创建成功谁就能获得锁，操作完成后断开节点。其它应用如果需要操作这个文件就可去监听这个目录是否存在。

- 分布式唯一id:在过去的单库单表型系统中，通常可以使用数据库字段自带的`auto_ increment`属性来自动为每条记录生成一个唯一的`ID`。但是分库分表后，就无法在依靠数据库的`auto_ Increment`属性来唯一标识一条记录了。此时我们就可以用`zookeeper`在分布式环境下生成全局唯一`ID`。

  做法如下:每次要生成一个新`id`时，创建一个持久顺序节点，创建操作返回的节点序号，即为新`id`，然后把比自己节点小的删除即可

- 集群管理:一个集群有时会因为各种软硬件故障或者网络故障，出现棊些服务器挂掉而被移除集群，而某些服务器加入到集群中的情况，`zookeeper`会将这些服务器加入/移出的情况通知给集群中的其他正常工作的服务器，以及时调整存储和计算等任务的分配和执行等。此外`zookeeper`还会对故障的服务器做出诊断并尝试修复

- **控制时序**：通过创建一个临时序列化节点来控制时序性。

## Znode节点是什么？

`zookeeper`的数据结点可以视为树状结构(或目录)，树中的各个结点被称为`znode `(即`zookeeper node`)，一个`znode`可以由多个子结点。`zookeeper`结点在结构上表现为树状。Znode兼具文件和目录两种特点。既像文件一样维护着数据长度、元信息、ACL、时间戳等数据结构，又像目录一样可以作为路径标识的一部分。每个Znode由三个部分组成：

- 节点状态**stat**：此为状态信息，描述该Znode版本、权限等信息。
- 节点数据**data**：与该Znode关联的数据
- 节点的子节点c**hildren**：该Znode下的节点



- **版本号** - 每个znode都有版本号，这意味着每当与znode相关联的数据发生变化时，其对应的版本号也会增加。当多个zookeeper客户端尝试在同一znode上执行操作时，版本号的使用就很重要。
- **操作控制列表(ACL)** - ACL基本上是访问znode的认证机制。它管理所有znode读取和写入操作。
- **时间戳** - 时间戳表示创建和修改znode所经过的时间。它通常以毫秒为单位。ZooKeeper从“事务ID"(zxid)标识znode的每个更改。**Zxid** 是唯一的，并且为每个事务保留时间，以便你可以轻松地确定从一个请求到另一个请求所经过的时间。
- **数据长度** - 存储在znode中的数据总量是数据长度。你最多可以存储1MB的数据。

### 节点详细信息

- `cZxid`数据结点创建时的事务ID——针对于`zookeeper`数据结点的管理：我们对结点数据的一些写操作都会导致`zookeeper`自动地为我们去开启一个事务，并且自动地去为每一个事务维护一个事务`ID`
- `ctime`数据结点创建时的时间
- `mZxid`数据结点最后一次更新时的事务ID
- `mtime`数据结点最后一次更新时的时间
- `pZxid`数据节点最后一次修改此`znode`子节点更改的`zxid`
- `cversion`子结点的更改次数
- `dataVersion`结点数据的更改次数
- `aclVersion`结点的ACL更改次数——类似`linux`的权限列表，维护的是当前结点的权限列表被修改的次数
- `ephemeralOwner`如果结点是临时结点，则表示创建该结点的会话的`SessionID`；如果是持久结点，该属性值为0
- `dataLength`数据内容的长度
- `numChildren`数据结点当前的子结点个数



### Znode的类型

- **持久节点** - 即使在创建该特定znode的客户端断开连接后，持久节点仍然存在。默认情况下，除非另有说明，否则所有znode都是持久的。
- **临时节点** - 客户端活跃时，临时节点就是有效的。当客户端与ZooKeeper集合断开连接时，临时节点会自动删除。因此，只有临时节点不允许有子节点。如果临时节点被删除，则下一个合适的节点将填充其位置。临时节点在leader选举中起着重要作用。
- **顺序节点** - 顺序节点可以是持久的或临时的。当一个新的znode被创建为一个顺序节点时，ZooKeeper通过将10位的序列号附加到原始名称来设置znode的路径。例如，如果将具有路径 **/myapp** 的znode创建为顺序节点，则ZooKeeper会将路径更改为 **/myapp0000000001** ，并将下一个序列号设置为0000000002。如果两个顺序节点是同时创建的，那么ZooKeeper不会对每个znode使用相同的数字。顺序节点在锁定和同步中起重要作用

### 四种类型的数据节点 Znode

（1）PERSISTENT-持久节点：除非手动删除，否则节点一直存在于 Zookeeper 上

（2）EPHEMERAL-临时节点：临时节点的生命周期与客户端会话绑定，一旦客户端会话失效（客户端与zookeeper 连接断开不一定会话失效），那么这个客户端创建的所有临时节点都会被移除。

（3）PERSISTENT_SEQUENTIAL-持久顺序节点：基本特性同持久节点，只是增加了顺序属性，节点名后边会追加一个由父节点维护的自增整型数字。

（4）EPHEMERAL_SEQUENTIAL-临时顺序节点：基本特性同临时节点，增加了顺序属性，节点名后边会追加一个由父节点维护的自增整型数字。

## ACL 权限控制机制

`acl`权限控制，使用`scheme：id：permission `来标识，子结点不会继承父结点的权限，客户端无权访问某结点，但可能可以访问它的子结点,主要涵盖3个方面：

- 权限模式(`scheme`)：授权的策略

  - 

    | 方案   | 描述                                                         |
    | ------ | ------------------------------------------------------------ |
    | world  | 只有一个用户：`anyone`，代表登录`zookeeper`所有人(默认) `setAcl /node world:anyone:drwa` |
    | ip     | 对客户端使用IP地址认证 `setAcl /hadoop ip:192.168.133.133:drwa` |
    | auth   | 使用已添加认证的用户认证addauth digest itcast:123456 setAcl /hadoop auth:itcast:cdrwa |
    | digest | 使用"用户名：密码"方式认证 **`setAcl <path> digest:<user>:<password>:<acl>`** |

- 授权对象(`id`)：授权的对象

  - 给谁授予权限
  - 授权对象ID是指，权限赋予的实体，例如：IP地址或用户

- 权限(`permission`)：授予的权限

  - | 权限   | ACL简写 | 描述                               |
    | ------ | ------- | ---------------------------------- |
    | create | c       | 可以创建子结点                     |
    | delete | d       | 可以删除子结点(仅下一级结点)       |
    | read   | r       | 可以读取结点数据以及显示子结点列表 |
    | write  | w       | 可以设置结点数据                   |
    | admin  | a       | 可以设置结点访问控制权限列表       |

eg：

```
setAcl /test2 ip:192.168.133.133:crwda  // 将结点权限设置为Ip：192.168.133.133 的客户端可以对节点进行
增删改查和管理权限
```

#### acl 超级管理员

- `zookeeper`的权限管理模式有一种叫做`super`，该模式提供一个超管，可以方便的访问任何权限的节点

  假设这个超管是`supper:admin`，需要为超管生产密码的密文

  ```shell
  echo -n super:admin | openssl dgst -binary -sha1 | openssl base64
  ```

- 那么打开`zookeeper`目录下`/bin/zkServer.sh`服务器脚本文件，找到如下一行：

  ```shell
   /nohup # 快速查找，可以看到如下
   nohup "$JAVA" "-Dzookeeper.log.dir=${ZOO_LOG_DIR}" "-Dzookeeper.root.logger=${ZOO_LOG4J_PROP}"
  ```

- 这个就算脚本中启动`zookeeper`的命令，默认只有以上两个配置项，我们需要添加一个超管的配置项

  ```
  "-Dzookeeper.DigestAuthenticationProvider.superDigest=super:xQJmxLMiHGwaqBvst5y6rkB6HQs="
  ```

- 修改后命令变成如下

  ```shell
  nohup "$JAVA" "-Dzookeeper.log.dir=${ZOO_LOG_DIR}" "-Dzookeeper.root.logger=${ZOO_LOG4J_PROP}" "-Dzookeeper.DigestAuthenticationProvider.superDigest=super:xQJmxLMiHGwaqBvst5y6rkB6HQs="
  ```

- ``` shell
  # 重起后，现在随便对任意节点添加权限限制
  setAcl /hadoop ip:192.168.1.1:cdrwa # 这个ip并非本机
  # 现在当前用户没有权限了
  getAcl /hadoop
  # 登录超管
  addauth digest super:admin
  # 强行操作节点
  get /hadoop
  ```

  

## 什么是Sessions（会话）

会话对于ZooKeeper的操作非常重要。会话中的请求按FIFO顺序执行。一旦客户端连接到服务器，将建立会话并向客户端分配**会话ID** 。客户端以特定的时间间隔发送**心跳**以保持会话有效。如果ZooKeeper集合在超过服务器开启时指定的期间（会话超时）都没有从客户端接收到心跳，则它会判定客户端死机。会话超时通常以毫秒为单位。当会话由于任何原因结束时，在该会话期间创建的临时节点也会被删除。

客户端以特定的时间间隔发送**心跳**以保持会话有效。如果ZooKeeper集合在超过服务器开启时指定的期间（会话超时）都没有从客户端接收到心跳，则它会判定客户端死机。

会话超时通常以毫秒为单位。当会话由于任何原因结束时，在该会话期间创建的临时节点也会被删除。

## zk角色

- Leader： ZooKeeper 集群工作的核心 事务请求（写操作）的唯一调度和处理者，保证集群事务处理的顺序性；集群内部各个服务的调度者。 对于 create，setData，delete 等有写操作的请求，则需要统一转发给 leader 处理，leader 需要决定编号、执行操作，这个过程称为一个事务。
-  Follower： 处理客户端非事务（读操作）请求 转发事务请求给 Leader 参与集群 leader 选举投票2n-1台可以做集群投票 此外，针对访问量比较大的 zookeeper 集群，还可以新增观察者角色。
- Observer： 观察者角色，观察ZooKeeper集群的最新状态变化并将这些状态同步过来，其对于非事务请求可以进行独立处理，对于事务请求，则会转发给Leader服务器处理 不会参与任何形式的投票只提供服务，通常用于在不影响集群事务处理能力的前提下提升集群的非事务处理能力 扯淡：说白了就是增加并发的请求

## 事务

1. 事务：ZooKeeper中，能改变ZooKeeper服务器状态的操作称为事务操作。一般包括数据节点创建与删除、数据内容更新和客户端会话创建与失效等操作。对应每一个事务请求，ZooKeeper 都会为其分配一个全局唯一的事务ID，用 ZXID 表示，通常是一个64位的数字。每一个ZXID对应一次更新操作，从这些ZXID中可以间接地识别出ZooKeeper处理这些事务操作请求的全局顺序。
2. 事务日志：所有事务操作都是需要记录到日志文件中的，可通过 dataLogDir配置文件目录，文件是以写入的第一条事务zxid为后缀，方便后续的定位查找。zk会采取“磁盘空间预分配”的策略，来避免磁盘Seek频率，提升zk服务器对事务请求的影响能力。默认设置下，每次事务日志写入操作都会实时刷入磁盘，也可以设置成非实时（写到内存文件流，定时批量写入磁盘），但那样断电时会带来丢失数据的风险。事务日志记录的次数达到一定数量后，就会将内存数据库序列化一次，使其持久化保存到磁盘上，序列化后的文件称为"快照文件"。有了事务日志和快照，就可以让任意节点恢复到任意时间点
3. **数据快照**：数据快照是zk数据存储中另一个非常核心的运行机制。数据快照用来记录zk服务器上某一时刻的全量内存数据内容，并将其写入到指定的磁盘文件中，可通过dataDir配置文件目录。可配置参数snapCount，设置两次快照之间的事务操作个数，zk节点记录完事务日志时，会统计判断是否需要做数据快照（距离上次快照，事务操作次数等于[snapCount/2~snapCount] 中的某个值时，会触发快照生成操作，随机值是为了避免所有节点同时生成快照，导致集群影响缓慢）。

## leader选举

### 节点状态

- LOOKING：寻找 Leader 状态，处于该状态需要进入选举流程
- LEADING：领导者状态，处于该状态的节点说明是角色已经是 Leader
- FOLLOWING：跟随者状态，表示 Leader 已经选举出来，当前节点角色是 follower
- OBSERVER：观察者状态，表明当前节点角色是 observer

### 事务ID

ZooKeeper 状态的每次变化都接收一个 ZXID（ZooKeeper 事务 id）形式的标记。ZXID 是一个 64 位的数字，由 Leader 统一分配，全局唯一，不断递增。ZXID 展示了所有的ZooKeeper 的变更顺序。每次变更会有一个唯一的 zxid，如果 zxid1 小于 zxid2 说明 zxid1 在 zxid2 之前发生。

### 集群初始化leader选举

(1) 每个Server发出一个投票。由于是初始情况，ZK1 和 ZK2 都会将自己作为 Leader 服务器来进行投票，每次投票会包含所推举的服务器的 myid 和 ZXID，使用(myid, ZXID)来表示，此时 ZK1 的投票为(1, 0)，ZK2 的投票为(2, 0)，然后各自将这个投票发给集群中其他机器。　　

(2) 接受来自各个服务器的投票。集群的每个服务器收到投票后，首先判断该投票的有效性，如检查是否是本轮投票、是否来自 LOOKING 状态的服务器。　　

(3) 处理投票。针对每一个投票，服务器都需要将别人的投票和自己的投票进行比较，规则如下　　　　

- 优先检查 ZXID。ZXID 比较大的服务器优先作为 Leader。
- 如果 ZXID 相同，那么就比较 myid。myid 较大的服务器作为Leader服务器。

  对于 ZK1 而言，它的投票是(1, 0)，接收 ZK2 的投票为(2, 0)，首先会比较两者的 ZXID，均为 0，再比较 myid，此时 ZK2 的 myid 最大，于是 ZK2 胜。ZK1 更新自己的投票为(2, 0)，并将投票重新发送给 ZK2。　　

(4) 统计投票。每次投票后，服务器都会统计投票信息，判断是否已经有过半机器接受到相同的投票信息，对于 ZK1、ZK2 而言，都统计出集群中已经有两台机器接受了(2, 0)的投票信息，此时便认为已经选出 ZK2 作为Leader。　　

(5) 改变服务器状态。一旦确定了 Leader，每个服务器就会更新自己的状态，如果是Follower，那么就变更为 FOLLOWING，如果是 Leader，就变更为 LEADING。当新的 Zookeeper 节点 ZK3 启动时，发现已经有 Leader 了，不再选举，直接将直接的状态从 LOOKING 改为 FOLLOWING。



\1. 服务器1启动，给自己投票，然后发投票信息，由于其它机器还没有启动所以它收不到反馈信息，服务器1的状态一直属于Looking。

\2. 服务器2启动，给自己投票，同时与之前启动的服务器1交换结果，由于服务器2的编号大所以服务器2胜出，但此时投票数没有大于半数，所以两个服务器的状态依然是LOOKING。

\3. 服务器3启动，给自己投票，同时与之前启动的服务器1,2交换信息，由于服务器3的编号最大所以服务器3胜出，此时投票数正好大于半数，所以服务器3成为leader，服务器1,2成为follower。

\4. 服务器4启动，给自己投票，同时与之前启动的服务器1,2,3交换信息，尽管服务器4的编号大，但之前服务器3已经胜出，所以服务器4只能成为follower。

\5. 服务器5启动，后面的逻辑同服务器4成为follower。

### 集群运行期间 Leader 重新选

(1) 变更状态。Leader 挂后，余下的非 Observer 服务器都会讲自己的服务器状态变更为 LOOKING，然后开始进入 Leader 选举过程。　　

(2) 每个Server会发出一个投票。在运行期间，每个服务器上的 ZXID 可能不同，此时假定 ZK1 的 ZXID 为 124，ZK3 的 ZXID 为 123；在第一轮投票中，ZK1 和 ZK3 都会投自己，产生投票(1, 124)，(3, 123)，然后各自将投票发送给集群中所有机器。　　

(3) 接收来自各个服务器的投票。与启动时过程相同。　　

(4) 处理投票。与启动时过程相同，由于 ZK1 事务 ID 大，ZK1 将会成为 Leader。　　

(5) 统计投票。与启动时过程相同。　　

(6) 改变服务器的状态。与启动时过程相同。

## Zk 怎么保证主从节点的数据同步？

数据同步流程，是要以Leader数据为基础，让集群数据达到一致状态。

1. 新Leader把本地快照加载到内存，并通过日志应用快照之后的所有事务，确保Leader数据库是最新的。
2. Follower和Observer把自身的ZXID和Leader的ZXID进行比较，确定每个节点的同步策略
3. 根据同步策略，Leader把数据同步到各节点
4. 每个节点同步结束后，Leader向节点发送NEWLEADER指令
5. 同步完成的Follower节点返回ACK
6. 当Leader收到过半节点反馈的ACK时，认为同步完成

Leader向Follower节点发送UPTODATE指令，通知集群同步完成，开始对外服务。

## 监听器原理

`zookeeper`采用了 `Watcher`机制实现数据的发布订阅功能。该机制在被订阅对象发生变化时会异步通知客户端，因此客户端不必在 `Watcher`注册后轮询阻塞，从而减轻了客户端压力。客户端**首先将 `Watcher`注册到服务端**，同时将 `Watcher`对象**保存到客户端的`watch`管理器中**。当`Zookeeper`服务端监听的数据状态发生变化时，服务端会**主动通知客户端**，接着客户端的 `Watch`管理器会**触发相关 `Watcher`**来回调相应处理逻辑，从而完成整体的数据 `发布/订阅`流

![image](https://note.youdao.com/yws/public/resource/128bec5e2028fdd77a8da4db2231a1f6/xmlnote/AB9879F425A44DBBAB09C8A1B359861A/17677)

### Zookeeper对节点的watch监听通知是永久的吗？

不是。官方声明：一个Watch事件是一个一次性的触发器，当被设置了Watch的数据发生了改变的时候，则服务器将这个改变发送给设置了Watch的客户端，以便通知它们。
为什么不是永久的，举个例子，如果服务端变动频繁，而监听的客户端很多情况下，每次变动都要通知到所有的客户端，这太消耗性能了。
一般是客户端执行getData(“/节点A”,true)，如果节点A发生了变更或删除，客户端会得到它的watch事件，但是在之后节点A又发生了变更，而客户端又没有设置watch事件，就不再给客户端发送。
在实际应用中，很多情况下，我们的客户端不需要知道服务端的每一次变动，我只要最新的数据即可。

### 特性

1. 一次性：一旦一个Wather触发之后，Zookeeper就会将它从存储中移除
2. 客户端串行：客户端的Wather回调处理是串行同步的过程，不要因为一个Wather的逻辑阻塞整个客户端
3. 轻量：Wather通知的单位是WathedEvent，只包含通知状态、事件类型和节点路径，不包含具体的事件内容，具体的时间内容需要客户端主动去重新获取数据

### watch接口设计

![image-20210408080547997](/Users/suidada/Library/Application Support/typora-user-images/image-20210408080547997.png)

#### Watcher通知状态(KeeperState)

`KeeperState`是客户端与服务端**连接状态**发生变化时对应的通知类型。路径为`org.apache.zookeeper.Watcher.EventKeeperState`，是一个枚举类，其枚举属性如下：

- | 枚举属性        | 说明                     |
  | --------------- | ------------------------ |
  | `SyncConnected` | 客户端与服务器正常连接时 |
  | `Disconnected`  | 客户端与服务器断开连接时 |
  | `Expired`       | 会话`session`失效时      |
  | `AuthFailed`    | 身份认证失败时           |


#### Watcher事件类型(EventType)

`EventType`是**数据节点`znode`发生变化**时对应的通知类型。**`EventType`变化时`KeeperState`永远处于`SyncConnected`通知状态下**；当`keeperState`发生变化时，`EventType`永远为`None`。其路径为`org.apache.zookeeper.Watcher.Event.EventType`，是一个枚举类，枚举属性如下：

- | 枚举属性              | 说明                                                        |
  | --------------------- | ----------------------------------------------------------- |
  | `None`                | 无                                                          |
  | `NodeCreated`         | `Watcher`监听的数据节点被创建时                             |
  | `NodeDeleted`         | `Watcher`监听的数据节点被删除时                             |
  | `NodeDataChanged`     | `Watcher`监听的数据节点内容发生更改时(无论数据是否真的变化) |
  | `NodeChildrenChanged` | `Watcher`监听的数据节点的子节点列表发生变更时               |

- 注意：客户端接收到的相关事件通知中只包含状态以及类型等信息，不包含节点变化前后的具体内容，变化前的数据需业务自身存储，变化后的数据需要调用`get`等方法重新获取

#### 捕获相应的事件

上面讲到`zookeeper`客户端连接的状态和`zookeeper`对`znode`节点监听的事件类型，下面我们来讲解如何建立`zookeeper`的***`watcher`监听***。在`zookeeper`中采用`zk.getChildren(path,watch)、zk.exists(path,watch)、zk.getData(path,watcher,stat)`这样的方式来为某个`znode`注册监听 。

下表以`node-x`节点为例，说明调用的注册方法和可用监听事件间的关系：

| 注册方式                            | created | childrenChanged | Changed | Deleted |
| ----------------------------------- | ------- | --------------- | ------- | ------- |
| `zk.exists("/node-x",watcher)`      | 可监控  |                 | 可监控  | 可监控  |
| `zk.getData("/node-x",watcher)`     |         |                 | 可监控  | 可监控  |
| `zk.getChildren("/node-x",watcher)` |         | 可监控          |         | 可监控  |

## zk javaAPI

```
public static void main(String[] args) throws IOException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ZooKeeper zookeeper = new ZooKeeper("192.168.133.133:2181", 5000, (WatchedEvent x) -> {
            if (x.getState() == Watcher.Event.KeeperState.SyncConnected) {
                System.out.println("连接成功");
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        System.out.println(zookeeper.getSessionId());
        zookeeper.close();
    }
```

#### 新增节点

- ```java
  // 同步
  create(String path, byte[] data, List<ACL> acl, CreateMode createMode)
  // 异步
  create(String path, byte[] data, List<ACL> acl, CreateMode createMode,
        AsynCallback.StringCallback callBack, Object ctx)
  ```

- 

- | 参数         | 解释                                                         |
  | ------------ | ------------------------------------------------------------ |
  | `path`       | `znode `路径                                                 |
  | `data`       | 数据                                                         |
  | `acl`        | 要创建的节点的访问控制列表。`zookeeper API `提供了一个静态接口 `ZooDefs.Ids` 来获取一些基本的`acl`列表。例如，`ZooDefs.Ids.OPEN_ACL_UNSAFE`返回打开`znode`的`acl`列表 |
  | `createMode` | 节点的类型，这是一个枚举                                     |
  | `callBack`   | 异步回调接口                                                 |
  | `ctx`        | 传递上下文参数                                               |

示例：

- ```java
  // 枚举的方式
      public static void createTest1() throws Exception{
          String str = "node";
          String s = zookeeper.create("/node", str.getBytes(),
                  ZooDefs.Ids.READ_ACL_UNSAFE, CreateMode.PERSISTENT);
          System.out.println(s);
      }
  ```

- ```java
  // 自定义的方式
      public static void createTest2() throws Exception{
          ArrayList<ACL> acls = new ArrayList<>();
          Id id = new Id("ip","192.168.133.133");
          acls.add(new ACL(ZooDefs.Perms.ALL,id));
          zookeeper.create("/create/node4","node4".getBytes(),acls,CreateMode.PERSISTENT);
      }
  ```

- ```java
  // auth
      public static void createTest3() throws  Exception{
          zookeeper.addAuthInfo("digest","itcast:12345".getBytes());
          zookeeper.create("/node5","node5".getBytes(),
                  ZooDefs.Ids.CREATOR_ALL_ACL,CreateMode.PERSISTENT);
      }
  // 自定义的方式
      public static void createTest3() throws  Exception{
  //        zookeeper.addAuthInfo("digest","itcast:12345".getBytes());
  //        zookeeper.create("/node5","node5".getBytes(),
  //                ZooDefs.Ids.CREATOR_ALL_ACL,CreateMode.PERSISTENT);
          zookeeper.addAuthInfo("digest","itcast:12345".getBytes());
          List<ACL> acls = new ArrayList<>();
          Id id = new Id("auth","itcast");
          acls.add(new ACL(ZooDefs.Perms.READ,id));
          zookeeper.create("/create/node6","node6".getBytes(),
                  acls,CreateMode.PERSISTENT);
      }
  ```

- ```java
  // digest 
  public static void createTest3() throws  Exception{
      List<ACL> acls = new ArrayList<>();
      Id id = new Id("digest","itcast:qUFSHxJjItUW/93UHFXFVGlvryY=");
      acls.add(new ACL(ZooDefs.Perms.READ,id));
      zookeeper.create("/create/node7","node7".getBytes(), 	
                       acls,CreateMode.PERSISTENT);
  }
  ```

- ```java
  // 异步
      public static void createTest4() throws  Exception{
          zookeeper.create("/node12", "node12".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new AsyncCallback.StringCallback(){
              /**
               * @param rc 状态，0 则为成功，以下的所有示例都是如此
               * @param path 路径
               * @param ctx 上下文参数
               * @param name 路径
               */
              public void processResult(int rc, String path, Object ctx, String name){
                  System.out.println(rc + " " + path + " " + name +  " " + ctx);
              }
          }, "I am context");
          TimeUnit.SECONDS.sleep(1);
          System.out.println("结束");
      }
  ```

  

#### 修改节点

同样也有两种修改方式(`异步和同步`)

- ```java
  // 同步
  setData(String path, byte[] data, int version)
  // 异步
  setData(String path, byte[] data, int version, StatCallback callBack, Object ctx)
  ```

- 

- | 参数       | 解释                                                         |
  | ---------- | ------------------------------------------------------------ |
  | `path`     | 节点路径                                                     |
  | `data`     | 数据                                                         |
  | `version`  | 数据的版本号， -`1`代表不使用版本号，乐观锁机制              |
  | `callBack` | 异步回调 `AsyncCallback.StatCallback`，和之前的回调方法参数不同，这个可以获取节点状态 |
  | `ctx`      | 传递上下文参数                                               |

- ```java
      public static void setData1() throws Exception{
      	// arg1:节点的路径
          // arg2:修改的数据
          // arg3:数据的版本号 -1 代表版本号不参与更新
          Stat stat = zookeeper.setData("/hadoop","hadoop-1".getBytes(),-1);
      }
  ```

- ```java
      public static void setData2() throws Exception{
          zookeeper.setData("/hadoop", "hadoop-1".getBytes(), 3 ,new AsyncCallback.StatCallback(){
              @Override
              public void processResult(int rc, String path, Object ctx, Stat stat) {
                  // 讲道理，要判空
                  System.out.println(rc + " " + path + " " + stat.getVersion() +  " " + ctx);
              }
          }, "I am context");
      }
  ```

##### 删除节点

异步、同步

- ```java
  // 同步
  delete(String path, int version)
  // 异步
  delete(String path, int version, AsyncCallback.VoidCallback callBack, Object ctx)
  ```

- 

- | 参数       | 解释                                            |
  | ---------- | ----------------------------------------------- |
  | `path`     | 节点路径                                        |
  | `version`  | 版本                                            |
  | `callBack` | 数据的版本号， -`1`代表不使用版本号，乐观锁机制 |
  | `ctx`      | 传递上下文参数                                  |

- ```java
      public static void deleteData1() throws Exception {
          zookeeper.delete("/hadoop", 1);
      }
  
      public static void deleteData2() throws Exception {
          zookeeper.delete("/hadoop", 1, new AsyncCallback.VoidCallback() {
              @Override
              public void processResult(int rc, String path, Object ctx) {
                  System.out.println(rc + " " + path + " " + ctx);
              }
          }, "I am context");
          TimeUnit.SECONDS.sleep(1);
      }
  ```

  

##### 查看节点

同步、异步

- ```java
  // 同步
  getData(String path, boolean watch, Stat stat)
  getData(String path, Watcher watcher, Stat stat)
  // 异步
  getData(String path, boolean watch, DataCallback callBack, Object ctx)
  getData(String path, Watcher watcher, DataCallback callBack, Object ctx)
  ```

- 

- | 参数       | 解释                             |
  | ---------- | -------------------------------- |
  | `path`     | 节点路径                         |
  | `boolean`  | 是否使用连接对象中注册的监听器   |
  | `stat`     | 元数据                           |
  | `callBack` | 异步回调接口，可以获得状态和数据 |
  | `ctx`      | 传递上下文参数                   |

- ```java
      public static void getData1() throws Exception {
          Stat stat = new Stat();
          byte[] data = zookeeper.getData("/hadoop", false, stat);
          System.out.println(new String(data));
          // 判空
          System.out.println(stat.getCtime());
      }
  
      public static void getData2() throws Exception {
          zookeeper.getData("/hadoop", false, new AsyncCallback.DataCallback() {
              @Override
              public void processResult(int rc, String path, Object ctx, byte[] bytes, Stat stat) {
                  // 判空
                  System.out.println(rc + " " + path
                                     + " " + ctx + " " + new String(bytes) + " " + 
                                     stat.getCzxid());
              }
          }, "I am context");
          TimeUnit.SECONDS.sleep(3);
      }
  ```

  

#### 查看子节点

同步、异步

- ```java
  // 同步
  getChildren(String path, boolean watch)
  getChildren(String path, Watcher watcher)
  getChildren(String path, boolean watch, Stat stat)    
  getChildren(String path, Watcher watcher, Stat stat)
  // 异步
  getChildren(String path, boolean watch, ChildrenCallback callBack, Object ctx)    
  getChildren(String path, Watcher watcher, ChildrenCallback callBack, Object ctx)
  getChildren(String path, Watcher watcher, Children2Callback callBack, Object ctx)    
  getChildren(String path, boolean watch, Children2Callback callBack, Object ctx)
  ```

- 

- | 参数       | 解释                       |
  | ---------- | -------------------------- |
  | `path`     | 节点路径                   |
  | `boolean`  |                            |
  | `callBack` | 异步回调，可以获取节点列表 |
  | `ctx`      | 传递上下文参数             |

- ```java
      public static void getChildren_1() throws Exception{
          List<String> hadoop = zookeeper.getChildren("/hadoop", false);
          hadoop.forEach(System.out::println);
      }
  
      public static void getChildren_2() throws Exception {
          zookeeper.getChildren("/hadoop", false, new AsyncCallback.ChildrenCallback() {
              @Override
              public void processResult(int rc, String path, Object ctx, List<String> list) {
                  list.forEach(System.out::println);
                  System.out.println(rc + " " + path + " " + ctx);
              }
          }, "I am children");
          TimeUnit.SECONDS.sleep(3);
      }
  ```



#### 检查节点是否存在

同步、异步

- ```java
  // 同步
  exists(String path, boolean watch)
  exists(String path, Watcher watcher)
  // 异步
  exists(String path, boolean watch, StatCallback cb, Object ctx)
  exists(String path, Watcher watcher, StatCallback cb, Object ctx)
  ```

- 

- | 参数       | 解释                       |
  | ---------- | -------------------------- |
  | `path`     | 节点路径                   |
  | `boolean`  |                            |
  | `callBack` | 异步回调，可以获取节点列表 |
  | `ctx`      | 传递上下文参数             |

- ```java
  public static void exists1() throws Exception{
      Stat exists = zookeeper.exists("/hadoopx", false);
      // 判空
      System.out.println(exists.getVersion() + "成功");
  }
  public static void exists2() throws Exception{
      zookeeper.exists("/hadoopx", false, new AsyncCallback.StatCallback() {
          @Override
          public void processResult(int rc, String path, Object ctx, Stat stat) {
              // 判空
              System.out.println(rc + " " + path + " " + ctx +" " + stat.getVersion());
          }
      }, "I am children");
      TimeUnit.SECONDS.sleep(1);
  }
  ```





# zk使用案例

## 分布式唯一id

在过去的单库单表型系统中，通常第可以使用数据库字段自带的`auto_ increment`属性来自动为每条记录生成个唯一的`ID`。但是分库分表后，就无法在依靠数据库的`auto_ increment`属性来唯一标识一条记录了。此时我们就可以用`zookeeper`在分布式环境下生成全局唯一`ID`

```java
public class IdGenerate {

    private static final String IP = "192.168.133.133:2181";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;

    public static String generateId() throws Exception {
        return zooKeeper.create("/id", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }


    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper(IP, 5000, new ZKWatcher());
        countDownLatch.await();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    System.out.println(generateId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        TimeUnit.SECONDS.sleep(50);
        threadPoolExecutor.shutdown();
    }

    static class ZKWatcher implements Watcher {
        @Override
        public void process(WatchedEvent watchedEvent) {
            countDownLatch.countDown();
            System.out.println("zk的监听器" + watchedEvent.getType());
        }
    }
}
```

## 分布式锁

> https://github.com/doocs/advanced-java/blob/main/docs/distributed-system/distributed-lock-redis-vs-zookeeper.md

![image-20210412160316624](/Users/suidada/Library/Application Support/typora-user-images/image-20210412160316624.png)

分布式锁有多种实现方式，比如通过数据库、redis都可实现。作为分布式协同工具`Zookeeper`，当然也有着标准的实现方式。下面介绍在`zookeeper`中如果实现排他锁

设计思路

1. 每个客户端往`/Locks`下创建临时有序节点`/Locks/Lock_`，创建成功后`/Locks`下面会有每个客户端对应的节点，如`/Locks/Lock_000000001`
2. 客户端取得/Locks下子节点，并进行排序，判断排在前面的是否为自己，如果自己的锁节点在第一位，代表获取锁成功
3. 如果自己的锁节点不在第一位，则监听自己前一位的锁节点。例如，自己锁节点`Lock_000000002`，那么则监听`Lock_000000001`
4. 当前一位锁节点`(Lock_000000001)`对应的客户端执行完成，释放了锁，将会触发监听客户端`(Lock_000000002)`的逻辑
5. 监听客户端重新执行第`2`步逻辑，判断自己是否获得了锁

```java
// 线程测试类
public class ThreadTest {
    public static void delayOperation(){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static interface Runable{
        void run();
    }
    public static void run(Runable runable,int threadNum){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(30, 30,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        for (int i = 0; i < threadNum; i++) {
            threadPoolExecutor.execute(runable::run);
        }
        threadPoolExecutor.shutdown();
    }

    public static void main(String[] args) {
//        DistributedLock distributedLock = new DistributedLock();
//        distributedLock.acquireLock();
//        delayOperation();
//        distributedLock.releaseLock();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 每秒打印信息
        run(() -> {
            for (int i = 0; i < 999999999; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String format = dateTimeFormatter.format(LocalDateTime.now());
                System.out.println(format);
            }
        },1);
        // 线程测试
        run(() -> {
            DistributedLock distributedLock = new DistributedLock();
            distributedLock.acquireLock();
            delayOperation();
            distributedLock.releaseLock();
        },30);
    }
}
public class DistributedLock {
    private String IP = "192.168.133.133:2181";
    private final String ROOT_LOCK = "/Root_Lock";
    private final String LOCK_PREFIX = "/Lock_";
    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private final byte[] DATA = new byte[0];

    private ZooKeeper zookeeper;
    private String path;

    private void init(){
        // 初始化
        try {
            zookeeper = new ZooKeeper(IP, 200000, w -> {
                if(w.getState() == Watcher.Event.KeeperState.SyncConnected){
                    System.out.println("连接成功");
                }
                countDownLatch.countDown();
            });
            countDownLatch.await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 暴露的外部方法，主逻辑
    public void acquireLock(){
        init();
        createLock();
        attemptLock();
    }

    // 暴露的外部方法，主逻辑
    public void releaseLock(){
        try {
            zookeeper.delete(path,-1);
            System.out.println("锁释放了" + path);
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    private void createLock(){
        try {
            // 创建一个目录节点
            Stat root = zookeeper.exists(ROOT_LOCK, false);
            if(root == null)
                zookeeper.create(ROOT_LOCK, DATA, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            // 目录下创建子节点
            path = zookeeper.create(ROOT_LOCK + LOCK_PREFIX, DATA, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    private Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent watchedEvent) {
            if (watchedEvent.getType() == Event.EventType.NodeDeleted){
                synchronized (this){
                    this.notifyAll();
                }
            }
        }
    };

    private void attemptLock(){
        try {
            // 获取正在排队的节点，由于是zookeeper生成的临时节点，不会出错，这里不能加监视器
            // 因为添加了监视器后，任何子节点的变化都会触发监视器
            List<String> nodes = zookeeper.getChildren(ROOT_LOCK,false);
            nodes.sort(String::compareTo);
            // 获取自身节点的排名
            int ranking = nodes.indexOf(path.substring(ROOT_LOCK.length() + 1));
            // 已经是最靠前的节点了，获取锁
            if(ranking == 0){
                return;
            }else {
                // 并不是靠前的锁，监视自身节点的前一个节点
                Stat status = zookeeper.exists(ROOT_LOCK+"/"+nodes.get(ranking - 1), watcher);
                // 有可能这这个判断的瞬间，0号完成了操作(此时我们应该判断成功自旋才对)，但是上面的status变量已经获取了值并且不为空，1号沉睡
                // 但是，请注意自行测试，虽然1号表面上沉睡了，但是实际上watcher.wait()是瞬间唤醒的
                if(status == null){
                    attemptLock();
                }else {
                    synchronized (watcher){
                        watcher.wait();
                    }
                    attemptLock();
                }
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


```

## 分布式读锁

> https://chenjiabing666.github.io/2020/04/19/Zookeeper%E5%AE%9E%E7%8E%B0%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81/

![image-20210412160438385](/Users/suidada/Library/Application Support/typora-user-images/image-20210412160438385.png)

1. 创建临时有序节点（当前线程拥有的`读锁`或称作`读节点`）。
2. 获取路径下所有的子节点，并进行`从小到大`排序
3. 获取当前节点前的临近写节点(写锁)。
4. 如果不存在的临近写节点，则成功获取读锁。
5. 如果存在临近写节点，对其监听删除事件。
6. 一旦监听到删除事件，**重复2,3,4,5的步骤(递归)**

## 分布式写锁

![image-20210412160522676](/Users/suidada/Library/Application Support/typora-user-images/image-20210412160522676.png)

1. 创建临时有序节点（当前线程拥有的`写锁`或称作`写节点`）。

2. 获取路径下的所有子节点，并进行`从小到大`排序。

3. 获取当前节点的临近节点(读节点和写节点)。

4. 如果不存在临近节点，则成功获取锁。

5. 如果存在临近节点，对其进行监听删除事件。

6. 一旦监听到删除事件，**重复2,3,4,5的步骤(递归)**。

   

# zab协议

`zab`协议的全称是 ***`Zookeeper Atomic Broadcast`*** (`zookeeper`原子广播)。`zookeeper`是通过`zab`协议来保证分布式事务的最终一致性

基于`zab`协议。ZK实现了一种主从模式的系统架构来保持集群中各个副本之间的数据一致性，所有的写操作都必须通过Leader完成，Leader写入本地日志后再复制到所有的Follower节点。一旦Leader节点无法工作，ZAB协议能够自动从Follower节点中重新选出一个合适的替代者，即新的Leader，该过程即为领导选举。zookeeper`集群中的角色主要有以下三类，如下所示：

- Leader： ZooKeeper 集群工作的核心 事务请求（写操作）的唯一调度和处理者，保证集群事务处理的顺序性；集群内部各个服务的调度者。 对于 create，setData，delete 等有写操作的请求，则需要统一转发给 leader 处理，leader 需要决定编号、执行操作，这个过程称为一个事务。
- Follower： 处理客户端非事务（读操作）请求 转发事务请求给 Leader 参与集群 leader 选举投票2n-1台可以做集群投票 此外，针对访问量比较大的 zookeeper 集群，还可以新增观察者角色。
- Observer： 观察者角色，观察ZooKeeper集群的最新状态变化并将这些状态同步过来，其对于非事务请求可以进行独立处理，对于事务请求，则会转发给Leader服务器处理 不会参与任何形式的投票只提供服务，通常用于在不影响集群事务处理能力的前提下提升集群的非事务处理能力 扯淡：说白了就是增加并发的请求

## 保证数据一致

`Leader` 这端，它为每个其他的 `zkServer` 准备了一个 **队列** ，采用先进先出的方式发送消息。由于协议是 **通过 `TCP` **来进行网络通信的，保证了消息的发送顺序性，接受顺序性也得到了保证。

除此之外，在 `ZAB` 中还定义了一个 **全局单调递增的事务ID `ZXID`** ，它是一个64位long型，其中高32位表示 `epoch` 年代，低32位表示事务id。`epoch` 是会根据 `Leader` 的变化而变化的，当一个 `Leader` 挂了，新的 `Leader` 上位的时候，年代（`epoch`）就变了。而低32位可以简单理解为递增的事务id。

定义这个的原因也是为了顺序性，每个 `proposal` 在 `Leader` 中生成后需要 **通过其 `ZXID` 来进行排序** ，才能得到处理。

![image-20210412144642190](/Users/suidada/Library/Application Support/typora-user-images/image-20210412144642190.png)

![image-20210412144652294](/Users/suidada/Library/Application Support/typora-user-images/image-20210412144652294.png)

## 数据同步

那实际上Zookeeper在选举之后，Follower和Observer（统称为Learner）就会去向Leader注册，然后就会开始数据同步的过程。数据同步包含3个主要值和4种形式。

PeerLastZxid：Learner服务器最后处理的ZXID

minCommittedLog：Leader提议缓存队列中最小ZXID

maxCommittedLog：Leader提议缓存队列中最大ZXID

### **直接差异化同步 DIFF同步**

如果PeerLastZxid在minCommittedLog和maxCommittedLog之间，那么则说明Learner服务器还没有完全同步最新的数据。

1. 首先Leader向Learner发送DIFF指令，代表开始差异化同步，然后把差异数据（从PeerLastZxid到maxCommittedLog之间的数据）提议proposal发送给Learner
2. 发送完成之后发送一个NEWLEADER命令给Learner，同时Learner返回ACK表示已经完成了同步
3. 接着等待集群中过半的Learner响应了ACK之后，就发送一个UPTODATE命令，Learner返回ACK，同步流程结束

![image-20210412161545356](/Users/suidada/Library/Application Support/typora-user-images/image-20210412161545356.png)

### **先回滚再差异化同步 TRUNC+DIFF同步**

如果Leader刚生成一个proposal，还没有来得及发送出去，此时Leader宕机，重新选举之后作为Follower，但是新的Leader没有这个proposal数据。

### **仅回滚同步 TRUNC同步**

针对PeerLastZxid大于maxCommittedLog的场景，流程和上述一致，事务将会被回滚到maxCommittedLog的记录。

这个其实就更简单了，也就是你可以认为TRUNC+DIFF中的例子，新的Leader B没有处理提议，所以B中minCommittedLog=1，maxCommittedLog=3。

所以A的PeerLastZxid=4就会大于maxCommittedLog了，也就是A只需要回滚就行了，不需要执行差异化同步DIFF了。

### **全量同步 SNAP同步**

适用于两个场景：

1. PeerLastZxid小于minCommittedLog
2. Leader服务器上没有提议缓存队列，并且PeerLastZxid不等于Leader的最大ZXID

这两种场景下，Leader将会发送SNAP命令，把全量的数据都发送给Learner进行同步。

## 数据不一致情景

- **查询不一致**：由于是过半成功则代表成功，那么如果过读取没有成功的，那么就不一致，解决：在读取前使用sync命令
- **leader未发送proposal宕机**：重新选举新leader没有该proposal
- **leader发送proposal成功，发送commit前宕机**：如果发送proposal成功了，但是在将要发送commit命令前宕机了，如果重新进行选举，还是会选择zxid最大的节点作为leader，因此，这个日志并不会被丢弃，会在选举出leader之后重新同步到其他节点当中。

## 数据写入集群流程

![image-20210408082957311](/Users/suidada/Library/Application Support/typora-user-images/image-20210408082957311.png)

1. `leader`从客户端收**到一个写请求**
2. `leader`**生成一个新的事务**并为这个事务生成一个唯一的`ZXID`
3. `leader`**将事务提议(`propose`)发送给所有的`follows`节点**
4. `follower`节点将收到的事务请求加入到本地**历史队列(`history queue`)中，并发送`ack`给`leader`**，表示确认提议
5. 当`leader`收到大多数`follower`(**半数以上节点**)的`ack(acknowledgement)`确认消息，`leader`会本地提交，并发送`commit`请求
6. 当`follower`**收到`commit`请求时，从历史队列中将事务请求`commit`**
7. leader节点将最新数据同步给Observer节点。

### leader选举

**服务器状态**

- **`looking`**：寻找`leader`状态。当服务器处于该状态时，它会认为当前集群中没有`leader`，因此需要进入`leader`选举状态
- **`following`**：跟随着状态。表明当前服务器角色是`follower`
- **`observing`**：观察者状态。表明当前服务器角色是`observer`

分为两种选举，**服务器启动时的选举**和**服务器运行时期的选举**

**服务器启动时期的leader选举**

在集群初始化节点，当有一台服务器`server1`启动时，其**单独无法进行和完成`leader`选举**，当第二台服务器`server2`启动时，此时两台及其可以相互通信，每台及其都试图找到`leader`，**于是进入`leader`选举过程**。选举过程如下：

1. 每个`server`发出一个投票。由于是初始状态，`server1`和`server2`都会将自己作为`leader`服务器来进行投票，每次投票都会包**含所推举的`myid`和`zxid`，使用(`myid，zxid`)**，此时`server1`的投票为(1，0)，`server2`的投票为(2，0)，然后**各自将这个投票发给集群中的其它机器**

2. 集群中的**每台服务器都接收来自集群中各个服务器的投票**

3. **处理投票**。针对每一个投票，服务器都需要将别人的投票和自己的投票进行pk，规则如下

   - 优先检查`zxid`。`zxid`比较大的服务器优先作为`leader`(**`zxid`较大者保存的数据更多**)

   - 如果`zxid`相同。那么就比较`myid`。`myid`较大的服务器作为`leader`服务器

     **对于`Server1`而言，它的投票是(1，0)**，接收`Server2`的投票为(2，0)，**首先会比较两者的`zxid`**，均为0，**再比较`myid`**，此时`server2`的`myid`最大，于是更新自己的投票为(2，0)，然后重新投票，**对于server2而言，无需更新自己的投票**，只是再次向集群中所有机器发出上一次投票信息即可

4. **统计投票**。每次投票后，服务器都会统计投票信息，判断是否已经有**过半机器接受到相同的投票信息**，对于`server1、server2`而言，都统计出集群中已经有两台机器接受了(2，0)的投票信息，此时便认为已经选举出了`leader`

5. **改变服务器状态**。一旦确定了`leader`,每个服务器就会更新自己的状态，如果是`follower`，那么就变更为`following`，如果是`leader`，就变更为`leading`

**举例：如果我们有三个节点的集群，1，2，3，启动 1 和 2 后，2 一定会是 `leader`，3 再加入不会进行选举，而是直接成为`follower`**—— 仔细观察 一台`zk`无法集群，没有`leader`

**服务器运行时期选举**

在`zookeeper`运行期间，`leader`与非`leader`服务器各司其职，即使当有非`leader`服务器宕机或者新加入，此时也不会影响`leader`，但是一旦`leader`服务器挂了，那么整个集群将暂停对外服务，进入新一轮`leader`选举，其过程和启动时期的`leader`选举过程基本一致

假设正在运行的有`server1`、`server2`、`server3`三台服务器，当前`leader`是`server2`，若某一时刻`leader`挂了，此时便开始`Leader`选举。选举过程如下

1. 变更状态。**`leader`挂后，余下的服务器都会将自己的服务器状态变更为`looking`**，然后开始进入`leader`选举过程
2. 每个`server`发出一个投票。在运行期间，**每个服务器上的`zxid`可能不同**，此时假定`server1`的`zxid`为`122`，`server3`的`zxid`为`122`，**在第一轮投票中，server1和server3都会投自己**，产生投票(1，122)，(3，122)，然后**各自将投票发送给集群中所有机器**
3. **接收来自各个服务器的投票**。与启动时过程相同
4. **处理投票**。与启动时过程相同，此时，`server3`将会成为`leader`
5. **统计投票**。与启动时过程相同
6. **改变服务器的状态**。与启动时过程相同

## 选举原则

1. 选举投票必须在同一轮次中进行，如果Follower服务选举轮次不同，不会采纳投票。
2. 数据最新的节点优先成为Leader，数据的新旧使用事务ID判定，事务ID越大认为节点数据约接近Leader的数据，自然应该成为Leader。
3. 如果每个个参与竞选节点事务ID一样，再使用server.id做比较。server.id是节点在集群中唯一的id，myid文件中配置。

## 脑裂问题

**脑裂问题**出现在集群中leader死掉，follower选出了新leader而原leader又复活了的情况下，因为ZK的过半机制是允许损失一定数量的机器而扔能正常提供给服务，当leader死亡判断不一致时就会出现多个leader。

ZK的过半机制一定程度上也减少了脑裂情况的出现，起码不会出现三个leader同时。ZK中的Epoch机制（时钟）每次选举都是递增+1，当通信时需要判断epoch是否一致，小于自己的则抛弃，大于自己则重置自己，等于则选举；

## 选主过程中主要有三个线程在工作

1. 选举线程:主动调用lookForLeader方法的线程，通过阻塞队sendqueue及recvqueue与其它两个线程协作。

2. WorkerReceiver线程:选票接收器，不断获取其它服务器发来的选举消息，筛选后会保存到recvqueue队列中。zk服务器启动时，开始正常工作，不停止
3. WorkerSender线程:选票发送器，会不断地从sendqueue队列中获取待发送的选票，并广播至集群。
4. WorkerReceiver线程一直在工作，即使当前节点处于LEADING或者FOLLOWING状态，它起到了一个过滤的作用，当前节点为LOOKING时，才会将外部投票信息转交给选举线程处理；
5. 如果当前节点处于非LOOKING状态，收到了处于LOOKING状态的节点投票数据（外部节点重启或网络抖动情况下），说明发起投票的节点数据跟集群不一致，这时，当前节点需要向集群广播出最新的内存Vote(id，zxid)，落后的节点收到该Vote后，会及时注册到leader上，并完成数据同步，跟上集群节奏，提供正常服务。

# curator介绍

<https://blog.csdn.net/wo541075754/article/details/68067872> 关于第三方客户端的小介绍

`zkClient `有对`dubbo`的一些操作支持，但是`zkClient`几乎没有文档，下面是`curator`

## **curator简介**

`curator`是`Netflix`公司开源的一个 `zookeeper`客户端，后捐献给 `apache`,，`curator`框架在`zookeeper`原生`API`接口上进行了包装，解决了很多`zooKeeper`客户端非常底层的细节开发。提供`zooKeeper`各种应用场景(比如:分布式锁服务、集群领导选举、共享计数器、缓存机制、分布式队列等的抽象封装，实现了`Fluent`风格的APl接口，是最好用，最流行的`zookeeper`的客户端

## 原生`zookeeperAPI`的不足

- 连接对象异步创建，需要开发人员自行编码等待
- 连接没有自动重连超时机制
- watcher一次注册生效一次
- 不支持递归创建树形节点

## `curator`特点

- 解决`session`会话超时重连
- `watcher`反复注册
- 简化开发`api`
- 遵循`Fluent`风格`API`

```html
    <!-- Zookeeper -->
    <dependency>
        <groupId>org.apache.zookeeper</groupId>
        <artifactId>zookeeper</artifactId>
        <version>3.4.10</version>
    </dependency>
    <dependency>
        <groupId>org.apache.curator</groupId>
        <artifactId>curator-framework</artifactId>
        <version>2.6.0</version>
        <exclustions>
            <exclustion>
               <groupId>org.apache.zookeeper</groupId>
               <artifactId>zookeeper</artifactId>
            </exclustion>
        </exclustions>
    </dependency>
    <dependency>
        <groupId>org.apache.curator</groupId>
        <artifactId>curator-recipes</artifactId>
        <version>2.6.0</version>
    </dependency>
```



## 基础用法

```java
    public static void main(String[] args) {
        // 工厂创建，fluent风格
        CuratorFramework client = CuratorFrameworkFactory.builder()
                // ip端口号
                .connectString("192.168.133.133:2181,192.168.133.133:2182,192.168.133.133:2183")
                // 会话超时
                .sessionTimeoutMs(5000)
                // 重试机制，这里是超时后1000毫秒重试一次
                .retryPolicy(new RetryOneTime(1000))
                // 名称空间，在操作节点的时候，会以这个为父节点
                .namespace("create")
                .build();
        client.start();
        
        System.out.println(client.getState());
        client.close();

    }
```

### `session`重连策略
  - `RetryPolicy retry Policy = new RetryOneTime(3000);`
    - 说明：三秒后重连一次，只重连一次
  - `RetryPolicy retryPolicy = new RetryNTimes(3,3000);`
    - 说明：每三秒重连一次，重连三次
  - `RetryPolicy retryPolicy = new RetryUntilElapsed(1000,3000);`
    - 说明：每三秒重连一次，总等待时间超过个`10`秒后停止重连
  - `RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3)`
    - 说明：这个策略的重试间隔会越来越长
      - 公式：`baseSleepTImeMs * Math.max(1,random.nextInt(1 << (retryCount + 1)))`
        - `baseSleepTimeMs` = `1000` 例子中的值
        - `maxRetries` = `3` 例子中的值

### 创建

```java
public class curatorGettingStart {
    public static CuratorFramework client;

    // ids权限
    public static void create1() throws Exception {
        // 新增节点
        client.create()
                // 节点的类型
                .withMode(CreateMode.EPHEMERAL)
                // 节点的acl权限列表
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                // arg1：节点路径，arg2：节点数据
                .forPath("/node1",new byte[0]);
    }
    // 自定义权限
    public static void create2() throws Exception {
        ArrayList<ACL> acls = new ArrayList<>();
        Id id = new Id("world", "anyone");
        acls.add(new ACL(ZooDefs.Perms.READ,id));
        // 新增节点
        client.create()
                // 节点的类型
                .withMode(CreateMode.EPHEMERAL)
                // 节点的acl权限列表
                .withACL(acls)
                // arg1：节点路径，arg2：节点数据
                .forPath("/node2",new byte[0]);
    }
    // 递归创建
    public static void create3() throws Exception {
        // 新增节点
        client.create()
                // 递归创建
                .creatingParentsIfNeeded()
                // 节点的类型
                .withMode(CreateMode.EPHEMERAL)
                // 节点的acl权限列表
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                // arg1：节点路径，arg2：节点数据
                .forPath("/node2/nodex",new byte[0]);
    }
    // 递归创建
    public static void create4() throws Exception {
        // 新增节点
        System.out.println(1);
        client.create()

                .creatingParentsIfNeeded()
                // 节点的类型
                .withMode(CreateMode.EPHEMERAL)
                // 节点的acl权限列表
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                // 异步
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                        System.out.println("异步创建成功");
                    }
                })
                // arg1：节点路径，arg2：节点数据
                .forPath("/node2/nodex",new byte[0]);
        System.out.println(2);
    }
    public static void main(String[] args) throws Exception {
        // 工厂创建，fluent风格
        CuratorFramework client = CuratorFrameworkFactory.builder()
                // ip端口号
                .connectString("192.168.133.133:2181,192.168.133.133:2182,192.168.133.133:2183")
                // 会话超时
                .sessionTimeoutMs(5000)
                // 重试机制，这里是超时后1000毫秒重试一次
                .retryPolicy(new RetryOneTime(1000))
                // 名称空间，在操作节点的时候，会以这个为父节点
                .namespace("create")
                .build();
        client.start();
//        create1();
//        create2();
//        create3();
        create4();

        System.out.println(client.getState() + "操作完成");
        TimeUnit.SECONDS.sleep(20);
        client.close();
    }
}

```

### 修改

```java
public class curatorGettingStart {
    public static CuratorFramework client;

    public static void set1() throws Exception {
        // 修改节点
        client.setData()
                // 版本
                .withVersion(-1)
                .forPath("/hadoop","hadoop1".getBytes());
    }
    public static void set2() throws Exception {
        // 修改节点
        client.setData()
                .withVersion(1)
                .forPath("/hadoop","hadoop2".getBytes());
    }
    public static void set3() throws Exception {
        // 修改节点
        client.setData()
                .withVersion(1)
                // 异步
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                        if(curatorEvent.getType() == CuratorEventType.SET_DATA)
                            System.out.println(curatorEvent.getPath()+ "    " +curatorEvent.getType());
                    }
                })
                .forPath("/hadoop","hadoop3".getBytes());

    }
    public static void main(String[] args) throws Exception {
        // 工厂创建，fluent风格
        client = CuratorFrameworkFactory.builder()
                // ip端口号
                .connectString("192.168.133.133:2181,192.168.133.133:2182,192.168.133.133:2183")
                // 会话超时
                .sessionTimeoutMs(5000)
                // 重试机制，这里是超时后1000毫秒重试一次
                .retryPolicy(new RetryOneTime(1000))
                // 名称空间，在操作节点的时候，会以这个为父节点,可选操作
                .namespace("update")
                .build();
        client.start();
//        set1();
        set2();
//        set3();
        System.out.println(client.getState() + "操作完成");
        TimeUnit.SECONDS.sleep(20);
        client.close();
    }
}

```

### 删除

```java
public class curatorGettingStart {
    public static CuratorFramework client;
    public static void delete1() throws Exception {
        // 删除节点
        client.delete()
                .forPath("node1");
    }

    public static void delete2() throws Exception {
        // 删除节点
        client.delete()
                // 版本
                .withVersion(1)
                .forPath("node2");
    }

    public static void delete3() throws Exception {
        // 删除节点
        client.delete()
                // 递归删除
                .deletingChildrenIfNeeded()
                .withVersion(-1)
                .forPath("node3");
    }

    public static void delete4() throws Exception {
        // 删除节点
        client.delete()
                .withVersion(-1)
                // 异步
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                        if (curatorEvent.getType() == CuratorEventType.DELETE)
                            System.out.println(curatorEvent.getPath() + "    " + curatorEvent.getType());
                    }
                })
                .forPath("node3");

    }


    public static void main(String[] args) throws Exception {
        // 工厂创建，fluent风格
        client = CuratorFrameworkFactory.builder()
                // ip端口号
                .connectString("192.168.133.133:2181,192.168.133.133:2182,192.168.133.133:2183")
                // 会话超时
                .sessionTimeoutMs(5000)
                // 重试机制，这里是超时后1000毫秒重试一次
                .retryPolicy(new RetryOneTime(1000))
                // 名称空间，在操作节点的时候，会以这个为父节点,可选操作
                .namespace("delete")
                .build();
        client.start();
        //        delete1();
        //        delete2();
        //        delete3();
        // delete4();
        System.out.println(client.getState() + "操作完成");
        TimeUnit.SECONDS.sleep(20);
        client.close();
    }
}

```

### 读取节点

```java
public class curatorGettingStart {
    public static CuratorFramework client; 
    public static void get1() throws  Exception {
        // 获取数据
        byte[] bytes = client.getData()
                .forPath("/node");
        System.out.println(new String((bytes)));
    }
    public static void get2() throws  Exception {
        Stat stat = new Stat();
        // 获取数据
        byte[] bytes = client.getData()
                .storingStatIn(stat)
                .forPath("/node");;
        System.out.println(new String((bytes)));
        System.out.println(stat.getVersion());
        System.out.println(stat.getCzxid());
    }
    public static void get3() throws  Exception {
        System.out.println(1);
        // 获取数据
        client.getData()
                .inBackground((CuratorFramework curatorFramework, CuratorEvent curatorEvent) -> {
                    System.out.println(curatorEvent.getPath() + "  " + curatorEvent.getType());
                })
                .forPath("/node");;
        System.out.println(2);
    }


    public static void main(String[] args) throws Exception {
        // 工厂创建，fluent风格
        client = CuratorFrameworkFactory.builder()
                // ip端口号
                .connectString("192.168.133.133:2181,192.168.133.133:2182,192.168.133.133:2183")
                // 会话超时
                .sessionTimeoutMs(5000)
                // 重试机制，这里是超时后1000毫秒重试一次
                .retryPolicy(new RetryOneTime(1000))
                // 名称空间，在操作节点的时候，会以这个为父节点,可选操作
                .namespace("get")
                .build();
        client.start();
        get1();
        get2();
        get3();

        System.out.println(client.getState() + "操作完成");
        TimeUnit.SECONDS.sleep(20);
        client.close();
    }
}
```



### 读取子节点

```java
 public class curatorGettingStart {
    public static CuratorFramework client;  
    public static void getChildren1() throws  Exception {
        // 获取数据
        List<String> strings = client.getChildren()
                .forPath("/get");
        strings.forEach(System.out::println);
        System.out.println("------------");
    }
    public static void getChildren2() throws  Exception {
        System.out.println(1);
        // 获取数据
        client.getChildren()
                .inBackground((curatorFramework, curatorEvent) -> {
                    curatorEvent.getChildren().forEach(System.out::println);
                    System.out.println("------------");
                })
                .forPath("/get");
        System.out.println(2);
        System.out.println("------------");
    }


    public static void main(String[] args) throws Exception {
        // 工厂创建，fluent风格
        client = CuratorFrameworkFactory.builder()
                // ip端口号
                .connectString("192.168.133.133:2181,192.168.133.133:2182,192.168.133.133:2183")
                // 会话超时
                .sessionTimeoutMs(5000)
                // 重试机制，这里是超时后1000毫秒重试一次
                .retryPolicy(new RetryOneTime(1000))
                // 名称空间，在操作节点的时候，会以这个为父节点,可选操作
//                .namespace("get")
                .build();
        client.start();

        getChildren1();
        getChildren2();

        System.out.println(client.getState() + "操作完成");
        TimeUnit.SECONDS.sleep(20);
        client.close();
    }
}
```



### watcher

```java
public class WatcherTest {
    static CuratorFramework client;

    public static void watcher1() throws Exception {
        // arg1 curator的客户端
        // arg2 监视的路径
        NodeCache nodeCache = new NodeCache(client, "/watcher");
        // 启动
        nodeCache.start();
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            // 节点变化时的回调方法
            public void nodeChanged() throws Exception {
                // 路径
                System.out.println(nodeCache.getCurrentData().getPath() + "  " + nodeCache.getCurrentData().getStat());
                // 输出节点内容
                System.out.println(new String(nodeCache.getCurrentData().getData()));
            }
        });
        System.out.println("注册完成");
        // 时间窗内可以一直监听
        //        TimeUnit.SECONDS.sleep(1000);
        //关 闭
        nodeCache.close();
    }

    public static void watcher2() throws Exception {
        // arg1 客户端
        // arg2 路径
        // arg3 事件钟是否可以获取节点数据
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/watcher", true);
        // 启动
        pathChildrenCache.start();
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            // 节点变化时的回调方法
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                if (pathChildrenCacheEvent != null) {
                    // 获取子节点数据
                    System.out.println(new String(pathChildrenCacheEvent.getData().getData()));
                    // 路径
                    System.out.println(pathChildrenCacheEvent.getData().getPath());
                    // 事件类型
                    System.out.println(pathChildrenCacheEvent.getType());
                }
            }
        });
        // 时间窗内可以一直监听
        TimeUnit.SECONDS.sleep(1000);
        //关 闭
        pathChildrenCache.close();

    }


    public static void main(String[] args) throws Exception {
        // 工厂创建，fluent风格
        client = CuratorFrameworkFactory.builder()
                // ip端口号
                .connectString("192.168.133.133:2181,192.168.133.133:2182,192.168.133.133:2183")
                // 会话超时
                .sessionTimeoutMs(5000)
                // 重试机制，这里是超时后1000毫秒重试一次
                .retryPolicy(new RetryOneTime(1000))
                // 名称空间，在操作节点的时候，会以这个为父节点,可选操作
                //                .namespace("get")
                .build();
        client.start();

//        watcher1();
        watcher2();

        System.out.println(client.getState() + "操作完成");
        TimeUnit.SECONDS.sleep(20);
        client.close();
    }
}

```



### 事务

````java
public class CuratorTransaction {
    static CuratorFramework client;


    public static void transaction() throws Exception{
        /*client.inTransaction()
                .create()
                    .withMode(CreateMode.PERSISTENT)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath("/transaction",new byte[0])
                .and()
                .setData()
                    .forPath("/setData/transaction",new byte[0])
                .and()
                .commit();*/
        client.create()
                .withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath("/transaction",new byte[0]);
        client.setData()
                .forPath("/setData/transaction",new byte[0]);
    }

    public static void main(String[] args) throws Exception {
        // 工厂创建，fluent风格
        client = CuratorFrameworkFactory.builder()
                // ip端口号
                .connectString("192.168.133.133:2181,192.168.133.133:2182,192.168.133.133:2183")
                // 会话超时
                .sessionTimeoutMs(5000)
                // 重试机制，这里是超时后1000毫秒重试一次
                .retryPolicy(new RetryOneTime(1000))
                // 名称空间，在操作节点的时候，会以这个为父节点,可选操作
                //                .namespace("get")
                .build();
        client.start();
        transaction();

        System.out.println(client.getState() + "操作完成");
        TimeUnit.SECONDS.sleep(20);
        client.close();
    }
}
````



### 分布式锁

- `InterProcessMutex`：分布式可重入排它锁
- `InterProcessReadWriteLock`：分布式读写锁

```java
public class CuratorDistributeLock {
    public static CuratorFramework client;

    public static void interProcessMutex() throws Exception {
        System.out.println("排他锁");
        // 获取一个分布式排他锁
        InterProcessMutex lock = new InterProcessMutex(client, "/lock1");
        // 开启两个进程测试，会发现：如果一个分布式排它锁获取了锁，那么直到锁释放为止数据都不会被侵扰
        System.out.println("获取锁中");
        lock.acquire();
        System.out.println("操作中");
        for (int i = 0; i < 10; i++) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(i);
        }
        lock.release();
        System.out.println("释放锁");
    }

    public static void interProcessReadWriteLock1() throws Exception {
        System.out.println("写锁");
        // 分布式读写锁
        InterProcessReadWriteLock lock = new InterProcessReadWriteLock(client, "/lock1");
        // 开启两个进程测试，观察到写写互斥，特性同排它锁
        System.out.println("获取锁中");
        lock.writeLock().acquire();
        System.out.println("操作中");
        for (int i = 0; i < 10; i++) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(i);
        }
        lock.writeLock().release();
        System.out.println("释放锁");
    }

    public static void interProcessReadWriteLock2() throws Exception {
        System.out.println("读锁");
        // 分布式读写锁
        InterProcessReadWriteLock lock = new InterProcessReadWriteLock(client, "/lock1");
        // 开启两个进程测试，观察得到读读共享，两个进程并发进行，注意并发和并行是两个概念，(并发是线程启动时间段不一定一致，并行是时间轴一致的)
        // 再测试两个进程，一个读，一个写，也会出现互斥现象
        System.out.println("获取锁中");
        lock.readLock().acquire();
        System.out.println("操作中");
        for (int i = 0; i < 10; i++) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(i);
        }
        lock.readLock().release();
        System.out.println("释放锁");
    }


    public static void main(String[] args) throws Exception {
        // 工厂创建，fluent风格
        client = CuratorFrameworkFactory.builder()
                // ip端口号
                .connectString("192.168.133.133:2181,192.168.133.133:2182,192.168.133.133:2183")
                // 会话超时
                .sessionTimeoutMs(5000)
                // 重试机制，这里是超时后1000毫秒重试一次
                .retryPolicy(new RetryOneTime(1000))
                // 名称空间，在操作节点的时候，会以这个为父节点,可选操作
                //                .namespace("get")
                .build();
        client.start();
        //        interProcessMutex();
//                interProcessReadWriteLock1();
        interProcessReadWriteLock2();


        System.out.println(client.getState() + "操作完成");
        TimeUnit.SECONDS.sleep(20);
        client.close();
    }
}

```



# 配置

输出相关服务配置的详细信息

| 属性                | 含义                                                         |
| ------------------- | ------------------------------------------------------------ |
| `clientPort`        | 客户端端口号                                                 |
| `dataDir`           | 数据快照文件目录，默认情况下`10w`次事务操作生成一次快照      |
| `dataLogDir`        | 事务日志文件目录，生产环节中放再独立的磁盘上                 |
| `tickTime`          | 服务器之间或客户端与服务器之间维持心跳的时间间隔(以毫秒为单位) |
| `maxClientCnxns`    | 最大连接数                                                   |
| `minSessionTimeout` | 最小`session`超时`minSessionTimeout=tickTime*2` ，即使客户端连接设置了会话超时，也不能打破这个限制 |
| `maxSessionTimeout` | 最大`session`超时`maxSessionTimeout=tickTime*20`，即使客户端连接设置了会话超时，也不能打破这个限制 |
| `serverId`          | 服务器编号                                                   |
| `initLimit`         | 集群中`follower`服务器`(F)`与`leader`服务器`(L)`之间初始连接时能容忍的最多心跳数，实际上以`tickTime`为单位，换算为毫秒数 |
| `syncLimit`         | 集群中`follower`服务器`(F)`与`leader`服务器`(L)`之间请求和应答之间能容忍的最大心跳数，实际上以`tickTime`为单位，换算为毫秒数 |
| `electionAlg`       | 0：基于`UDP`的`LeaderElection`1：基于`UDP`的`FastLeaderElection`2：基于UDP和认证的`FastLeaderElection`3：基于`TCP`的`FastLeaderElection`在`3.4.10`版本中，默认值为3，另外三种算法以及被弃用，并且有计划在之后的版本中将它们彻底删除且不再支持 |
| `electionPort`      | 选举端口                                                     |
| `quorumPort`        | 数据通信端口                                                 |
| `peerType`          | 是否为观察者 1为观察者                                       |

