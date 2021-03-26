[TOC]

# 基本概念

## foreach使用

```java
<!-- collection：指定输入的集合参数的参数名称 --> 
<!-- item：声明集合参数中的元素变量名 -->
<!-- open：集合遍历时，需要拼接到遍历sql语句的前面 --> 
<!-- close：集合遍历时，需要拼接到遍历sql语句的后面 --> <!-- separator：集合遍历时，需要拼接到遍历sql语句之间的分隔符号 --> <foreach collection="ids" item="id" open=" AND id IN ( " close=" ) " separator=",">
#{id} 
</foreach>
```



## 一对一（多）查询

<img src="https://note.youdao.com/yws/public/resource/128bec5e2028fdd77a8da4db2231a1f6/xmlnote/D9DE570B7CEB4CB695EBC77F12945E3D/17613" alt="image" style="zoom:50%;" />

## **什么是延迟加载**

```
<settings> <!-- 延迟加载总开关 --> 
<setting name="lazyLoadingEnabled" value="true"/> 
<!-- 侵入式延迟加载开关 关闭入侵则开启深度 --> 
<setting name="aggressiveLazyLoading" value="true"/> 
</settings> 
```



MyBatis中的延迟加载，也称为**懒加载**，是指在进行关联查询时，按照设置延迟规则推迟对关联对象的select查询。延迟加载可以有效的减少数据库压力。Mybatis的延迟加载，需要通过**resultMap****标签中的****association****和****collection**子标签才能演示成功。

- **直接加载：** 执行完对主加载对象的select语句，马上执行对关联对象的select查询。

- **侵入式延迟**：执行对主加载对象的查询时，不会执行对关联对象的查询。但当要访问主加载对象的某个属性（该属性不是关联对象的属性）时，就会马上执行关联对象的select查询。

- **深度延迟：**执行对主加载对象的查询时，不会执行对关联对象的查询。访问主加载对象的详情时也不会执行关联对象的select查询。只有当真正访问关联对象的详情时，才会执行对关联对象的select查询。

## 插入返回主键

```
  <insert id="insertSelective" parameterType="com.kreditplus.kptdao.model.ApplyInfo"  useGeneratedKeys="true" keyProperty="id"  keyColumn="id">

```

```
<insert id="insertUser" parameterType="com.kkb.mybatis.po.User"> <!-- selectKey将主键返回，需要再返回 --> <selectKey keyProperty="id" order="AFTER" resultType="java.lang.Integer"> select LAST_INSERT_ID() </selectKey> insert into user(username,birthday,sex,address) values(#{username},#{birthday},#{sex},#{address}); </insert>
```

添加selectKey标签实现主键返回。

**keyProperty**:指定返回的主键，存储在pojo中的哪个属性

**order**：selectKey标签中的sql的执行顺序，是相对与insert语句来说。由于mysql的自增原理，执

行完insert语句之后才将主键生成，所以这里selectKey的执行顺序为after。 

**resultType**:返回的主键对应的JAVA类型

**LAST_INSERT_ID()**:是mysql的函数，返回auto_increment自增列新记录id值。

# mybaits

## Xml 映射文件中，除了常见的 select|insert|updae|delete 标签之外，还有哪些标签？

还有很多其他的标签，`<resultMap>`、`<parameterMap>`、`<sql>`、`<include>`、`<selectKey>`，加上动态 sql 的 9 个标签，`trim|where|set|foreach|if|choose|when|otherwise|bind`等，其中为 sql 片段标签，通过`<include>`标签引入 sql 片段，`<selectKey>`为不支持自增的主键生成策略标签

## 最佳实践中，通常一个 Xml 映射文件，都会写一个 Dao 接口与之对应，请问，这个 Dao 接口的工作原理是什么？Dao 接口里的方法，参数不同时，方法能重载吗

Dao 接口，就是人们常说的 `Mapper`接口，接口的全限名，就是映射文件中的 namespace 的值，接口的方法名，就是映射文件中`MappedStatement`的 id 值，接口方法内的参数，就是传递给 sql 的参数。`Mapper`接口是没有实现类的，当调用接口方法时，接口全限名+方法名拼接字符串作为 key 值，可唯一定位一个`MappedStatement`，举例：`com.mybatis3.mappers.StudentDao.findStudentById`，可以唯一找到 namespace 为`com.mybatis3.mappers.StudentDao`下面`id = findStudentById`的`MappedStatement`。在 MyBatis 中，每一个`<select>`、`<insert>`、`<update>`、`<delete>`标签，都会被解析为一个`MappedStatement`对象。

Dao 接口里的方法，是不能重载的，因为是全限名+方法名的保存和寻找策略。

Dao 接口的工作原理是 JDK 动态代理，MyBatis 运行时会使用 JDK 动态代理为 Dao 接口生成代理 proxy 对象，代理对象 proxy 会拦截接口方法，转而执行`MappedStatement`所代表的 sql，然后将 sql 执行结果返回。

## MyBatis 是如何进行分页的？分页插件的原理是什么？

MyBatis 使用 RowBounds 对象进行分页，它是针对 ResultSet 结果集执行的内存分页，而非物理分页，可以在 sql 内直接书写带有物理分页的参数来完成物理分页功能，也可以使用分页插件来完成物理分页。

分页插件的基本原理是使用 MyBatis 提供的插件接口，实现自定义插件，在插件的拦截方法内拦截待执行的 sql，然后重写 sql，根据 dialect 方言，添加对应的物理分页语句和物理分页参数。

举例：select _ from student，拦截 sql 后重写为：select t._ from （select \* from student）t limit 0，10

## 简述 MyBatis 的插件运行原理，以及如何编写一个插件。

MyBatis 仅可以编写针对 ParameterHandler、ResultSetHandler、StatementHandler、Executor 这 4 种接口的插件，MyBatis 使用 JDK 的动态代理，为需要拦截的接口生成代理对象以实现接口方法拦截功能，每当执行这 4 种接口对象的方法时，就会进入拦截方法，具体就是 InvocationHandler 的 invoke()方法，当然，只会拦截那些你指定需要拦截的方法。
实现 MyBatis 的 Interceptor 接口并复写 intercept()方法，然后在给插件编写注解，指定要拦截哪一个接口的哪些方法即可，记住，别忘了在配置文件中配置你编写的插件。

## MyBatis 动态 sql 是做什么的？都有哪些动态 sql？能简述一下动态 sql 的执行原理不？

MyBatis 动态 sql 可以让我们在 Xml 映射文件内，以标签的形式编写动态 sql，完成逻辑判断和动态拼接 sql 的功能，MyBatis 提供了 9 种动态 sql 标签 trim|where|set|foreach|if|choose|when|otherwise|bind。
其执行原理为，使用 OGNL 从 sql 参数对象中计算表达式的值，根据表达式的值动态拼接 sql，以此来完成动态 sql 的功能。

## MyBatis 是否支持延迟加载？如果支持，它的实现原理是什么？

MyBatis 仅支持 association 关联对象和 collection 关联集合对象的延迟加载，association 指的就是一对一，collection 指的就是一对多查询。在 MyBatis 配置文件中，可以配置是否启用延迟加载 lazyLoadingEnabled=true|false。
它的原理是，使用 CGLIB 创建目标对象的代理对象，当调用目标方法时，进入拦截器方法，比如调用 a.getB().getName()，拦截器 invoke()方法发现 a.getB()是 null 值，那么就会单独发送事先保存好的查询关联 B 对象的 sql，把 B 查询上来，然后调用 a.setB(b)，于是 a 的对象 b 属性就有值了，接着完成 a.getB().getName()方法的调用。这就是延迟加载的基本原理。
当然了，不光是 MyBatis，几乎所有的包括 Hibernate，支持延迟加载的原理都是一样的。

## MyBatis 都有哪些 Executor 执行器？它们之间的区别是什么？

MyBatis 有三种基本的 Executor 执行器，SimpleExecutor、ReuseExecutor、BatchExecutor。

**SimpleExecutor：**每执行一次 update 或 select，就开启一个 Statement 对象，用完立刻关闭 Statement 对象。

**ReuseExecutor：**执行 update 或 select，以 sql 作为 key 查找 Statement 对象，存在就使用，不存在就创建，用完后，不关闭 Statement 对象，而是放置于 Map<String, Statement>内，供下一次使用。简言之，就是重复使用 Statement 对象。

**BatchExecutor：**执行 update（没有 select，JDBC 批处理不支持 select），将所有 sql 都添加到批处理中（addBatch()），等待统一执行（executeBatch()），它缓存了多个 Statement 对象，每个 Statement 对象都是 addBatch()完毕后，等待逐一执行 executeBatch()批处理。与 JDBC 批处理相同。

## Mybatis 缓存了解么？
Mybatis 中有一级缓存和二级缓存，默认情况下一级缓存是开启的，而且是不能关闭的。一级缓存是指 SqlSession 级别的缓存，当在同一个 SqlSession 中进行相同的 SQL 语句查询时，第二次以后的查询不会从数据库查询，而是直接从缓存中获取，一级缓存最多缓存 1024 条 SQL。二级缓存是指可以跨 SqlSession 的缓存。是 mapper 级别的缓存，对于 mapper 级别的缓存不同的
sqlsession 是可以共享的

### Mybatis 的一级缓存原理（sqlsession 级别)

一级缓存是**SqlSession**级别的缓存。在操作数据库时需要构造 sqlSession对象，在对象中有一个数据结构（HashMap）用于存储缓存数据。不同的sqlSession之间的缓存数据区域（HashMap）是互相不影响的。

第一次发出一个查询 sql，sql 查询结果写入 sqlsession 的一级缓存中，缓存使用的数据结构是一
个 map。
key：MapperID+offset+limit+Sql+所有的入参
value：用户信息
同一个 sqlsession 再次发出相同的 sql，就从缓存中取出数据。如果两次中间出现 commit 操作（修改、添加、删除），本 sqlsession 中的一级缓存区域全部清空，下次再去缓存中查询不到所以要从数据库查询，从数据库查询到再写入缓存。

### 二级缓存原理（mapper 基本）

![image](https://note.youdao.com/yws/public/resource/128bec5e2028fdd77a8da4db2231a1f6/xmlnote/8656B01BB3DF4018AD69143AF0AEB645/17615)

二级缓存的范围是 mapper 级别（mapper 同一个命名空间），多个SqlSession去操作同一个Mapper的sql语句，多个SqlSession可以共用二级缓存，二级缓存是跨SqlSession的。mapper 以命名空间为单位创建缓存数据结构，结构是 map。mybatis 的二级缓存是通过 CacheExecutor 实现的。CacheExecutor121623125152125125其实是 Executor 的代理对象。所有的查询操作，在 CacheExecutor 中都会先匹配缓存中是否存在，不存在则查询数据库。
key：MapperID+offset+limit+Sql+所有的入参

具体使用需要配置：
1. Mybatis 全局配置中启用二级缓存配置
2. 在对应的 Mapper.xml 中配置 cache 节点
3. 在对应的 select 查询节点中添加 useCache=true
![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/FA683E2848CE4B009E318BE4C07CFA3B/16516)

### sql基本开启二级缓存

**flushCache**.如果查询语句设置成true，那么每次查询都是去数据库查询，即意味着该查询的二级缓存失效。

\* 如果增删改语句设置成false，即使用二级缓存，那么如果在数据库中修改了数据，而缓存数据还是

原来的，这个时候就会出现脏读。

```
<select id="findUserById" parameterType="int" resultType="com.kkb.mybatis.po.User" useCache="true" flushCache="true"> SELECT * FROM user WHERE id = #{id} </select>
```

### 应用场景

使用场景：

对于访问响应速度要求高，但是实时性不高的查询，可以采用二级缓存技术。

注意事项：

在使用二级缓存的时候，要设置一下**刷新间隔**（cache标签中有一个**flflashInterval**属性）来定时

刷新二级缓存，这个刷新间隔根据具体需求来设置，比如设置30分钟、60分钟等，**单位为毫秒**。

