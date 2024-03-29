[TOC]

# 概念

## 什么是kafka

Apache Kafka 是一个快速、可扩展的、高吞吐的、可容错的分布式“发布-订阅”消息系统，使用 Scala 与 Java 语言编写，能够将消息从一个端点传递到另一个端点，较之传统的消息中间件（例如 ActiveMQ、RabbitMQ），Kafka 具有高吞吐量、内置分区、支持消息副本和高容错的特性，非常适合大规模消息处理应用程序。

## **应用场景**

- 消息系统 Messaging
- Web 站点活动追踪 Website Activity Tracking
- 数据监控 Metrics
- 日志聚合 Log Aggregation
- 流处理 Stream Processiong
- 事件源 Event Sourcing
- 提交日志 Commit Log

## **kafka** **高吞吐率实现**

- 顺序读写：Kafka 将消息写入到了分区 partition 中，而分区中消息是顺序读写的。顺序读写要远快于随机读写。
- 零拷贝：生产者、消费者对于 kafka 中消息的操作是采用零拷贝实现的。
- 批量发送：Kafka 允许使用批量消息发送模式。
- 消息压缩：Kafka 支持对消息集合进行压缩。

## 什么是用户态？什么是内核态?如何区分？

内核态：在高执行级别下，代码可以执行特权指令，访问任意的物理地址，这种CPU执行级别就对应着内核态。

**用户态**：只能受限的访问内存, 且不允许访问外围设备. 占用CPU的能力被剥夺, CPU资源可以被其他程序获取

## 什么是零拷贝

> https://zhuanlan.zhihu.com/p/131582874

**零拷贝其实是根据内核状态划分的，在这里没有经过CPU的拷贝，数据在用户态的状态下，经历了零次拷贝，所以才叫做零拷贝，但不是说不拷贝**

![image](https://note.youdao.com/yws/public/resource/128bec5e2028fdd77a8da4db2231a1f6/xmlnote/17FD45B9081D438AAFA36F0B8B62754F/17673)

![image](https://note.youdao.com/yws/public/resource/128bec5e2028fdd77a8da4db2231a1f6/xmlnote/0E934AF1524347F4BECCBF4599FFF7C5/17675)

## 专业术语

- TOPIC：主题。在 Kafka 中，使用一个类别属性来划分消息的所属类，划分消息的这个类称为 topic。topic 相当于消息的分类标签，是一个逻辑概念。
- **Partition**：分区。topic 中的消息被分割为一个或多个 partition，其是一个物理概念，对应到系统上就是一个或若干个目录
- **segment**：段。将 partition 进一步细分为了若干的 segment，每个 segment 文件的最大大小相等。
- **Broker**：Kafka 集群包含一个或多个服务器，每个服务器节点称为一个 broker。 一个 topic 中设置 partition 的数量是 broker 数量的整数倍。

- **Producer**：生产者。即消息的发布者，其会将某 topic 的消息发布到相应的 partition 中。
- **Consumer Group**：消费者。可以从 broker 中读取消息，其是以 consumer group 的形式出现的。consumer group 是 kafka 提供的可扩展且具有容错性的消费者机制。组内可以有多个消费者，它们共享一个公共的 ID，即 group ID。组内的所有消费者会协调在一起平均消费订阅主题的所有分区。

- **Replicas of partition**：分区副本。副本是一个分区的备份，是为了防止消息丢失而创建的分区的备份。

- **Partition Leader**：每个 partition 有多个副本，其中有且仅有一个作为 Leader，Leader 是当前负责消息读写

  的 partition。即所有读写操作只能发生于 Leader 分区上。

- **Partition Follower**：所有 Follower 都需要从 Leader 同步消息，Follower 与 Leader 始终保持消息同步。partition leader 与 partition follower 是主备关系，不是主从。

- **ISR**：In-Sync Replicas，是指副本同步列表。

- AR，Assiged Replicas，是指所有副本。 

- OSR，Outof-Sync Replicas 

- AR = ISR + OSR

- **offset**：偏移量。每条消息都有一个当前 Partition 下唯一的 64 字节的 offset，它是相对于当前分区第一条消息的偏移量

- **offset commit**：当 consumer 从 partition 中消费了消息后，consumer 会将其消费的消息的 offset 提交给broker，表示当前 partition 已经消费到了该 offset 所标识的消息。Consumer 从 partition 中取出一批消息写入到 buffer 对其进行消费，在规定时间内消费完消息后，会自动将其消费消息的 offset 提交给 broker，以让 broker 记录下哪些消息是消费过的。当然，若在时限内没有消费完毕，其是不会提交 offset 的
- **Rebalance**：当消费者组中消费者数量发生变化，或 Topic 中的 partition 数量发生了变化时，partition的所有权会在消费者间转移，即 partition 会重新分配，这个过程称为再均衡 Rebalance。再均衡能够给消费者组及 broker 集群带来高可用性和伸缩性，但在再均衡期间消费者是无法读取消息的，即整个 broker 集群有一小段时间是不可用的。因此要避免不必要的再均衡。

- **__consumer_offsets**：消费者提交的 offset 被封装为了一种特殊的消息被写入到了一个由系统创建的、名称为__consumer_offset 的特殊主题的 partitions 中了。该 Topic 默认包含 50 个分区。每个 Consumer Group 的消费的 offset 都会被记录在__consumer_offsets 主题的 partition中。该主题的 partition 默认有 50 个，那么 Consumer Group 消费的 offset 存放在哪个分区呢？其有计算公式：Math.abs(groupID.hashCode()) % 50

- **Broker Controller**：Kafka 集群的多个 broker 中，有一个会被选举为 controller，负责管理整个集群中 partition

  和副本 replicas 的状态。

- **Zookeeper**：Zookeeper 负责维护和协调 broker，负责 Broker Controller 的选举。
- **Group Coordinator**：group Coordinator 是运行在每个 broker 上的进程，主要用于 Consumer Group 中的各个成员的 offset 位移管理和 Rebalance。Group Coordinator 同时管理着当前 broker 的所有消费者组。
- LEO：每个副本的最大offset
- HW：消费者可见的最大offset，ISO队列中的最小LEO

## 分区🐟消费者的关系



## 分区策略





