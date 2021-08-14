[TOC]

# 基本概念

## ELK

是三个开源软件的缩写，分别表示：Elasticsearch , Logstash, Kibana 

## elasticsearch

Elasticsearch是个开源分布式搜索引擎，提供搜集、分析、存储数据三大功能。它的特点有：分布式，零配置，自动发现，索引自动分片，索引副本机制，restful风格接口，多数据源，自动搜索负载等。

## Logstash 

主要是用来日志的搜集、分析、过滤日志的工具，支持大量的数据获取方式。一般工作方式为c/s架构，client端安装在需要收集日志的主机上，server端负责将收到的各节点日志进行过滤、修改等操作在一并发往elasticsearch上去。

## Kibana 

也是一个开源和免费的工具，Kibana可以为 Logstash 和 ElasticSearch 提供的日志分析友好的 Web 界面，可以帮助汇总、分析和搜索重要数据日志。

## es高扩展

- 水平扩张：更多的服务器
- 垂直扩展：提升服务器性能

## 全文搜索

![image](https://note.youdao.com/yws/public/resource/128bec5e2028fdd77a8da4db2231a1f6/xmlnote/752565165B3644EFBC845251C508AA67/17621)

## lucene

是一个全文搜索引擎的架构，提供了完整的查询引擎和索引引擎，部分文本分析引擎。

## 倒排索引

倒排索引源于实际应用中需要根据属性的值来查找记录。这种索引表中的每一项都包括一个属性值和具有该属性值的各记录的地址。由于不是由记录来确定属性值，而是由属性值来确定记录的位置，因而称为倒排索引(inverted index)。倒排索引中的对象是文档或者是文档集合中的单词，用来存储这些单词在一个索引或者一组索引的存储位置

进一步解释：**当用户在主页上搜索关键词“华为手机”时，假设只存在正向索引（forward index），那么就需要扫描索引库中的所有文档，找出所有包含关键词“华为手机”的文档，再根据打分模型进行打分，排出名次后呈现给用户。**因为互联网上收录在搜索引擎中的文档的数目是个天文数字，**这样的索引结构根本无法满足实时返回排名结果的要求。**

![image](https://note.youdao.com/yws/public/resource/128bec5e2028fdd77a8da4db2231a1f6/xmlnote/95BC8649D3A94AFEA505C08F0333A6E4/17623)

Lucene将上面3列分为词典文件，频率文件，位置文件

- 词典文件：包含关键词，指向频率文件和位置文件的指针

## 正排索引

搜索的时候依赖的是倒排索引。排序的时候依赖的是正排索引，看到每个document的field，然后进行排序，以供排序，聚合，过来等操作，doc values被保存在磁盘上的，此时如果内存足够，os回自动将其缓存在内存中，性能会很高，如果内存不足够，os会将其写入磁盘上。

## 分片

因为往往来说一个index非常大，所以会将索引分解成多个分片

## 主分片（primary shard）

每个文档都会存储在一个分片上，存储的时候首先会存储到主分片上，然后会复制到不同的副本中。默认一个索引5个主分片

## 副本分片（replica shard）

副本是主分片的复制。主要是1. 增加高可用，当主分片挂的时候，可以从副本选择一个作为主分片。2. 提高性能，查询可以在副本分片上查询。副本分片必须和主分片部署在不同的节点上。

## 索引词（term）

在es中索引词是可以被索引精确查找的词。foo,Foo,FOO是不同的索引词。

## 文本

文本是一段非结构化文字，通常文本回被分析成一个个索引词，存储在索引库中。

## 分析

分析是文本转化为索引词的过程，分析结果依赖于分词器。比如FOO BAR ,foo-bar,foo bar可能被分析成相同的索引词foo和bar。

## 路由

当存储一个文档时，他会存储在唯一的主分片中，具体哪个分片是通过散列值选择。这个值可以由文档id生成，如果文档有指定的父文档，则从父文档id中生成

## 分片

分片是 Elasticsearch 在集群中分发数据的关键。把分片想象成数据的容器。文档存储在分片中，然后分片分配到集群中的节点上。当集群扩容或缩小，Elasticsearch 将会自动在节点间迁移分片，以使集群保持平衡。它只是保存了索引中所有数据的一部分。这类似于 MySql 的分库分表，只不过 Mysql 分库分表需要借助第三方组件而 ES 内部自身实现了此功能

## 索引（index）

索引是具有相同结构的文档集合。eg：订单数据的索引，产品目录的索引

## 类型（type）

在索引中，可以定义一个或者多个类型，类型是索引的逻辑分区。一般一个类型被定义为具有一组公共字段的文档。eg：博客系统，将所有的博客数据存储在一个索引中，在系统中定义用户数据类型，博客数据类型，评论数据类型。

## 文档（document）

文档是存储在es中的json字符串，就像关系数据库中的一行数据。原始的json文档存储在_source字段中。

## 映射（mapping）

映射就像关系数据库中的表结构，每个索引都有一个映射，定义了索引中每个字段的含义。

## 字段（field）

字段类似于关系数据库的列。文档中包含零个或多个字段

## 来源字段（source field）

默认原文档存储在_source字段下。

## 整个索引数据架构

![image](https://note.youdao.com/yws/public/resource/128bec5e2028fdd77a8da4db2231a1f6/xmlnote/28116BC06B364DAAAC6BF9BCA5A5A70C/17625)

## es与数据库对比

| es       | 数据库 |      |
| -------- | ------ | ---- |
| Document | 行     |      |
| type     | 表     |      |
| index    | 库     |      |

## 集群的健康状态

green：每个主副分片都是active状态

yellow：每个主分片是active状态，部分副分片不是active

red：不是所有索引的主分片都active，部分索引有数据丢失

## exact value和full text

- exact：精准匹配
- full：全文搜索索锁缩 China va cn，格式转换likes vs like，大小写 Like vs like，同义词like vs love

## 默认分词器

- standard：
- standard tokenizer：以单词边界切分
- standard token filter：什么都不做
- lowercase token filter：将所有字母转为小写
- stop token filer（默认禁用）：移除停用词，eg：a the it等

```
PUT /index
{
	"settings":{
		"analysis":{
			"filed ":{
				"es_std":{
					"type":"standard",
					"stopwords":"_english_"
				}
			}
		}
	}
}
```

## luence的segment概念

存储逆向索引的文件，每个segment本质上就是一个逆向索引，每秒都会生成一个segment文件，当文件过多时es会自动进行segment merge（合并文件），合并时会同时将已经标注删除的文档物理删除。本质上是一个小的index

# es基本操作

## 查看有那些索引

GET /_cat/indices?v

## 简单索引操作（query search sql）

- 创建索引：PUT /index_name?pretty

- 删除索引：DELETE /index_name?pretty

- 新增文档：PUT /index/type/document

- 删除文档：DELETE /index/type/document

- 查询文档：GET /index/type/document

- 更新文档：POST /index/type/document/_update {"doc":{"修改信息"}}

- 查询所有文档：GET /index/type/_search
  - 根据名称查询：GET /my_test/shop/_search?q=name:xiaomi
  - 根据排序查询：GET /my_test/shop/_search?sort=price:esc
  
- 建立索引并指定分片规则

  - ```
    PUT /test_index
    {
      "settings": {
        "number_of_replicas": 1, 
         "number_of_shards": 3
      }
    }
    ```

  - 

## query dsl

- 查询所有文档

  - ```
    GET /my_test/shop/_search
    {
      "query": {
        "match_all": {}
      }
    }
    ```

- 查询条件

  - ```
    GET /my_test/shop/_search
    {
      "query": {
        "match": {
          "name": "xiaomi"
        }
      },
      "sort": [
        {
          "name": {
            "order": "desc"
          }
        }
      ]
    }
    ```

- 分页查询

  - ```
    GET /my_test/shop/_search
    {
      "query": {
        "match_all": {}
      },
      "from": 0, 
      "size": 2
    }
    ```

- 特定列

  - ```
    GET /my_test/shop/_search
    {
      "query": {
        "match_all": {}
      },
      "_source": ["name","price"]
    }
    ```

- 多条件查询

  - ```
    GET /my_test/shop/_search
    {
      "query": {
        "bool": {
          "must": [
            {
              "match": {
                "name": "xiaomi"
              }
            }
          ],
          "filter": {
            "range": {
              "price": {
                "gte": 10
              }
            }
          }
        }
      }
    }
    ```

- 全文检索：将输入的搜索串拆解，然后去倒排索引里面一一匹配，只要匹配上一个就返回文档

  - ```
    GET /my_test/shop/_search
    {
      "query": {
        "match": {
          "name": "wei hua"
        }
      }
    }
    ```

- 短语搜索：搜索串必须和文档中的一摸一样才返回

  - ```
    GET /my_test/shop/_search
    {
      "query": {
        "match_phrase": {
          "name": "hua wei"
        }
      }
    }
    ```

- 文本高亮

  - ```
    GET /my_test/shop/_search
    {
      "query": {
        "match": {
          "name": "hua wei"
        }
      },
      "highlight": {
        "fields": {
          "name": {}
        }
      }
    }
    ```

### filter于query

filter：仅按照条件过来出需要的数据

query：回计算每个document相对于搜索条件的相关度，并排序

## 建立mapping

mapping类似于字段类型，可以指定extra value字段，只可以在增加，不可以修改

```
PUT /index
{
	"mappings":{
		"type":{
			"field":{
				"type":"long"
			}
		}
	}
}
```

## 验证搜索是否合法

`?explain`

# linux 安装es

> https://www.cnblogs.com/weibanggang/p/11589464.html

## es对复杂分布式机制的透明隐藏特性

### 分片

![image](https://note.youdao.com/yws/public/resource/128bec5e2028fdd77a8da4db2231a1f6/xmlnote/78DDFAA40629463A95B201EF774B4837/17627)

### 副本

### 负载均衡



## es的垂直扩容与水平扩容机制

- 垂直扩容：升级服务器配置
- 水平扩容：加机器

## 增减节点数据的rebalance

## master节点

管理es集群的元数据，eg：索引创建和删除，维护索引元数据，节点增加和删除等

## 节点平等的分布式架构

- 节点对等，每个节点都可以接收到请求
- 请求路由：将请求路由到对应的节点上
- 响应手机：接受请求的节点将路由处理请求节点的响应返回给请求者

# es扩充知识

## 两个节点是如何分片的

![image](https://note.youdao.com/yws/public/resource/128bec5e2028fdd77a8da4db2231a1f6/xmlnote/B4E9A9897D8D4B78992F1A240DA78443/17629)

## 横向扩容过程，如果超出扩容极限，如何提升容错性

假设3primary,1replica,2台机器，此时每天机器3个分片没太大的容错性，此时加入一台新机器，es会自动负载，每个机器2个节点

1. primary和replica自动负载均衡
2. 每个node有更少的shard，IO/CPU/Memory资源给每个shard分配的更多，性能更好
3. 扩容极限，6个shard最多扩展到6个机器，每个shard性能最好
4. 超出扩容极限，可以修改replica数量，9个shard（3p，6r）
5. 3台机器，9个shard，容错性好，最多两个机器宏机，6个shard只能容忍1台

![image](https://note.youdao.com/yws/public/resource/128bec5e2028fdd77a8da4db2231a1f6/xmlnote/C7DB2EBDC8E14ABFBCFAC384C132B96B/17631)

## master选举，replica容错，数据恢复

1. master选举一个节点为master节点
2. 新master节点将某个副本分片提升为主分片，此时cluster status为yellow，因为主分片都是active，但是少了一个副本分片
3. 重启故障节点，新master会将缺失的副本都copy一份到该node上去，而且该node会使用之前已有的分片数据，只是同步一下宏机之后的数据修改，此时cluster status 变为green

![image](https://note.youdao.com/yws/public/resource/128bec5e2028fdd77a8da4db2231a1f6/xmlnote/FB003C8BBE7A48069F2E4A8F438E1AD6/17633)

## 自建id和随机id

随机Id采用GUID算法，可以保证在分布式环境下，不同节点同一时间生成id是不一样

手动 GET 自动POST

## _source元数据自定义返回数据

放在body中的json串，会在get时候返回，放在_source下

```
PUT /test_index/test_type/1
{
  "test_field1":"test field1",
  "test_field2":"test field2"
}
GET /test_index/test_type/1
{
  "_index": "test_index",
  "_type": "test_type",
  "_id": "1",
  "_version": 2,
  "found": true,
  "_source": {
    "test_field1": "test field1",
    "test_field2": "test field2"
  }
}
```

指定返回数据

```
GET /test_index/test_type/1?_source=test_field2
{
  "_index": "test_index",
  "_type": "test_type",
  "_id": "1",
  "_version": 2,
  "found": true,
  "_source": {
    "test_field2": "test field2"
  }
}
```

## _document全量替换和删除原理，强制删除操作

1. 如何documentId不存在就是创建，如果did已经存在就是全量替换。（原理，老数据会被标记为deleted但是没有被删除，创建新的文档，当es中的数据越来愈多的时候，es会自动把后台标记为deleted的document都给物理删除，释放空间）

```
PUT /test_index/test_type/4
{
  "test_field":"test test"
}
```

2. 强制创建

```
PUT /test_index/test_type/4/_create
{
  "test_field":"test test 333"
}
```

3. 删除原理和全量替换一样

   删除同一个id的数据，如果再次插入的话，那么它的version会++，变相的证明了不是物理删除

## es并发冲突问题

![image](https://note.youdao.com/yws/public/resource/128bec5e2028fdd77a8da4db2231a1f6/xmlnote/5752A29E7BD346A1BACCDA73A267D250/17635)

### es中乐观锁

es中的数据都会有版本好这个字段，在更新的时候会利用乐观锁判断_version字段是否一致，如果一致则可以写入成功，不一致无法写入成功。可以利用es中的version字段做乐观锁更新机制

### es中怎样用乐观锁更新数据

```
PUT /index/type/documentId?version=1
# 然后返回就报了409 乐观锁问题
```

### external version

es提供feature，就是说你可以不用内部version进行并发控制，可以基于自己维护的一个版本号来进行并发控制。如果基于这个的话传入的version不必与原先的一致，只要比原先大就可以

```
PUT /index/type/documentId?version>1&version_type=external
```

### es乐观锁更新重试

```
POST /index/type/id?retry_on_confilct=3（最大重试次数）
```

## mget批量查询

```
# 其中可以不加index和type，但是doc节点内要加“_index”,"_type"等字段
GET /index/type/_mget
{
  "docs":[
    {
      "_id":1
    },
    {
      "_id":2
    }
    ]
}
```

## bulk批量增删改

1. delete
2. create强制创建
3. index：普调put操作
4. update：部分更新操作

```
POST /test_index/test_type/_bulk
{"delete":{"_id":4}}
{"create":{"_id":111}}
{"test_field":"111"}
```

每个json格式只能是一行，json串之间有换行，多个操作如果有失败不会停止执行，而是返回对应的失败结果，会被加载在内存中，如果太大性能会下降

### 为什么采用奇特的格式

因为bulk一般来说请求量比较大，如果放到一个json里面，那么es在接收数据的时候，整个数据就会在内存中出现一个一摸一样的copy，一份数据是json文本，一份是json对象。如过大批量请求的话，那么就会致使内存翻倍。

## 部分更新的原理

```
POST /index/type/documentId/_update
{
"doc":{
	"field":"value"
}
}
```

其实是在分片内部全量更新，但是这种操作更加的节约网络传输，减少传输次数，尊需乐观锁更新机制

1. es内部查询数据获取数据和version

2. 根据传送过来的field更新document

3. 将老的置为deleted

4. 创建新的document，使用之前获取到的version

## document（routing）路由的原理

路由算法：`shard = hash(routing)%number_of_primary_shards`

每次增删改查的时候都会带过来一个routing number 默认就是doucmentId

所以说默认主分片数量一旦确认就不可以更改

## timeout使用

限定时间内返回数据，无论已经查询了多少

## preference指定使用的shard

决定了哪些shard会被用来执行操作

### 解决跳跃（bouncing results）问题

问题描述：两个document，field值一样，不同的shard上排序不同，导致每次轮训请求打到不同的shard上面，每次页面上看到的排序结果不同，这就是跳跃

解决方案就是在操作的时候将preference设置为一个字符串，比如说user_id，让每个user搜索的时候，都使用同一个shard去执行

## document增删改的原理

1. 客户端选择一个node发送请求
2. 接收到请求的node称为协调节点，会对document进行路由，转发到对应的primary shard上去
3. 实际primary shard处理请求，然后将数据同步到replica shard
4. 协调节点如果发现主副本都处理完毕后，就返回响应给客户端

### 写入原理

> https://www.jianshu.com/p/d6fd7e8cf220

1. 数据同时写入buffer缓冲区（此时数据是不能被搜索到的，即segment关闭）和translog日志里面
2. buffer每秒同步到新的segment文件里面，并写入os cache，此时segment被打开供search使用
3. 清空buffer缓冲
4. 重复1-3过程，segment被不断写入，buffer缓冲不断被清空，translog日志文件越来越大
5. 当translog每隔30分钟或者大小达到阈值的时候会触发flush操作，commit point
   1. buffer中的数据被写入一个新的segment中，并写入os cash，打开供使用
   2. buffer被清空
   3. 一个commit point被写入磁盘，里面标识着这个 `commit point` 对应的所有 `segment file`
   4. os cachse中缓存的所有index segment file缓存数据，被强制fsync到磁盘
   5. 现有的tanslog被清空，创建一个新的translog

![image](https://note.youdao.com/yws/public/resource/128bec5e2028fdd77a8da4db2231a1f6/xmlnote/F856486E76C241078EC2388C7B87CD83/17637)

### 写入过程如何保证宏机器数据不丢失

磁盘中保存的是上一次刷新到磁盘的所有数据，而translog保存的是上次刷新到磁盘后未同步磁盘的数据（上次commit point到现在的数据变更记录），索引在宏机恢复的时候，会将translog中的变更记录进行回放，重新执行之前的各种操作，在buffer中执行，再重新刷新到一个个的segment到os cache中，等待下次的commit发送即可

### translog是否会丢失数据

translog 其实也是先写入 os cache 的，默认每隔 5 秒刷一次到磁盘中去，所以默认情况下，可能有 5 秒的数据会仅仅停留在 buffer 或者 translog 文件的 os cache 中，如果此时机器挂了，会**丢失** 5 秒钟的数据。但是这样性能比较好，最多丢 5 秒的数据。也可以将 translog 设置成每次写操作必须是直接 `fsync` 到磁盘，但是性能会差很多。

### 删除

每次commit的时候会有一个.del文件，标记了哪些segment中的那些document被标记删除了，搜索的时候，就不回返回了

### 更新

如果是更新操作，就是将原来的 doc 标识为 `deleted` 状态，然后新写入一条数据。

### 如果segment文件太多了怎么办？

buffer 每次 refresh 一次，就会产生一个 `segment file`，所以默认情况下是 1 秒钟一个 `segment file`，这样下来 `segment file` 会越来越多，此时会定期执行 merge。每次 merge 的时候，会将多个 `segment file` 合并成一个，同时这里会将标识为 `deleted` 的 doc 给**物理删除掉**，然后将新的 `segment file` 写入磁盘，这里会写一个 `commit point`，标识所有新的 `segment file`，然后打开 `segment file` 供搜索使用，同时删除旧的 `segment file`。

1. 选择一些近似大小的segment，meger成一个更大的segment
2. 将segment flush到磁盘
3. 写入一个新的commit point，包括新的segement，排除旧的segment
4. 将新的segment打开供搜索
5. 将旧的segment移除

## 查询请求的原理

1. 客户端选择一个node发送请求
2. 接收到请求的node称为协调节点，会对document进行路由，转发到对应的节点上去，此时会使用随机轮训算法，将读请求负载到对应分片的主副本上
3. 接收请求的node节点返回数据给协调节点
4. 协调节点返回给客户端

特殊情况，多现场并发读写，如果转发到replica上，则可能primary上的数据还没有同步到replica上此时是无法获取到数据的

### 查询的timeout机制

`GET /_search?timeout=10ms`

如果一个请求请求的数据过多，那么es可能处理的时间非常长，但是es有一个超时机制，如果超过规定的时间，那么就返回在查询时间内的数据。

eg：1分钟查6000数据，现在timeout10ms，可以查询10条数据，就只返回这10条数据

### 传统分页使用

from表示查询结果的起始下标，size表示从起始下标开始返回文档的个数

```
GET /_search?from=0&size=3 from=0 3 6 9 代表第几页
```

#### 传统分页引起深分页（deep paging）

> https://blog.csdn.net/ztchun/article/details/91406928

query阶段

（1） Client 发送一次搜索请求，node1 接收到请求，然后，node1 创建一个大小为 from + size 的优先级队列用来存结果，我们管 node1 叫 coordinating node。

（2）coordinating node将请求广播到涉及到的 shards，每个 shard 在内部执行搜索请求，然后，将结果存到内部的大小同样为 from + size 的优先级队列里，可以把优先级队列理解为一个包含 top N 结果的列表。

（3）每个 shard 把暂存在自身优先级队列里的数据返回给 coordinating node，coordinating node 拿到各个 shards 返回的结果后对结果进行一次合并，产生一个全局的优先级队列，存到自身的优先级队列里。

在上面的过程中，coordinating node 拿到 (from + size) * 分片数目 条数据，然后合并并排序后选择前面的 from + size 条数据存到优先级队列，以便 fetch 阶段使用。另外，各个分片返回给 coordinating node 的数据用于选出前 from + size 条数据，所以，只需要返回唯一标记 doc 的 _id 以及用于排序的 _score 即可，这样也可以保证返回的数据量足够小。
（4）coordinating node 计算好自己的优先级队列后，query 阶段结束，进入 fetch 阶段。

只需要返回唯一标记 doc 的 _id 以及用于排序的 _score 即可，这样也可以保证返回的数据量足够小。

fetch阶段

（1）coordinating node 发送MGET 请求到相关shards。
（2）shard 根据 doc 的 _id 取到数据详情，然后返回给 coordinating node。
（3）coordinating node 合并数据后返回给 Client。

搜索的特别深，比如总共有60000条数据，三个primary shard,每个shard上分了20000条数据，每页是10条数据，这个时候，你要搜索到第1000页，实际上要拿到的是10001~10010。

请求首先可能是打到一个不包含这个index的shard的node上去，这个node就是一个协调节点coordinate node，那么这个coordinate node就会将搜索请求转发到index的三个shard所在的node上去。比如说我们之前说的情况下，要搜索60000条数据中的第1000页，实际上每个shard都要将内部的20000条数据中的第10001~10010条数据，拿出来，不是才10条，是10010条数据。3个shard的每个shard都返回10010条数据给协调节点coordinate node，coordinate node会收到总共30030条数据，然后在这些数据中进行排序，根据_score相关度分数，然后取到10001~10010这10条数据，就是我们要的第1000页的10条数据。

### 大数据量的快照分页（scroll）

相对于from&size的分页来说，使用scroll可以模拟一个传统的游标来记录当前读取的文档信息位置。采用此分页方法，不是为了实时查询数据，而是为了查询大量甚至全部的数据。此方式相对于维护了一份当前索引的快照信息，在执行数据查询时，scroll将会从这个快照信息中获取数据。它相对于传统的分页方式来说，不是查询所有数据再剔除掉不需要的部分，而是记录一个读取的位置来保证下次对数据的继续获取。

scroll 类似于sql中的cursor，使用scroll，每次只能获取一页的内容，然后会返回一个scroll_id。根据返回的这个scroll_id可以不断地获取下一页的内容，所以scroll并不适用于有跳页的情景。

(1)初始搜索请求应该在查询中指定 scroll 参数，如 ?scroll=1m,这可以告诉 Elasticsearch 需要保持搜索的上下文环境多久。
(2)每次对 scroll API 的调用返回了结果的下一个批次结果，直到 hits 数组为空。scroll_id 则可以在请求体中传递。scroll 参数告诉 Elasticsearch 保持搜索的上下文等待另一个3m。返回数据的size与初次请求一致。

```
GET /index/type/_search?scorll=1m
{
	"query":{
		"match_all":{}
	}
	"sort":["_doc"],
	"size":3
}
GET /index/type/_search?scorll=1m
{
	"scorll":"1m",
	"scorll_id":"xxx"
}
```




## 写一致性

在我们写入数据的时候可以带上一个consistency参数，指明我们想要的写一致性是什么

- one：写操作，只要有一个primary shard 处于acitve就可以执行
- all：写操作，所有的primary shard和replica都处于active才可以执行
- quorum（默认）：大多数shard是active，才可以执行
  - quroum=int((primary+number_of_replicas)/2) + 1 
  - p=3 r=1则至少3个shard是active
  - 引出问题假设p=1，r=1，则根据公式知识有2个节点存活才能写，但是挂了一台，我们就永远无法写入了，此时es提供一种机制r必须>1才生效
  - quorum可以添加timeout机制，单位秒，默认1分钟

## 分数_source计算算法

- term frequency（TF）： 搜索文本的各个词条在field中出现了多少次，出现次数越多，越相关
- inverse document frequency（IDF）：文本中的词条出现次数越多，相关度越低
- field-length norm：field越长，相关度越低

## 如过索引mapping建立错误并已经添加数据了，如何做到0停机切换

1. 重新建立一个新的索引，指定mapping
2. 通过scoll api将数据查询出来
3. 通过bulk api写入新建立的索引里面
4. 循环写入完毕
5. 在es上移除旧索引，通过别名将新索引指向原来的索引

```
POST /_aliases
{
	"actions":[
		{"remove":{"index":"index_old","alias":"my_index"},
		{"add":{"index":"index_new","alias":"my_index"}
	]
}
```

