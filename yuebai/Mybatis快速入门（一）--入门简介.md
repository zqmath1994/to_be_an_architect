<h1>Mybatis快速入门（一）</h1>
因为入职后需要进行情景模拟（老员工针对工作中的痛点出题），小组组员有部分对SSM框架中的Mybatis不太熟悉，为了尽快让大家上手项目，特意整理Mybatis框架的基础知识，让组员迅速掌握入门，上手开发。

<h4>Mybatis简介</h4>
首先看到官网给出的简介：

>MyBatis 是支持定制化 SQL、存储过程以及高级映射的优秀的持久层框架。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以对配置和原生Map使用简单的 XML 或注解，将接口和 Java 的 POJOs(Plain Old Java Objects,普通的 Java对象)映射成数据库中的记录。

从上面给出的简介我们可以看到，Mybatis的特性：支持定制化SQL、存储过程、高级映射、避免JDBC代码和手动设置参数、可以通过XML和注解的配置等，将接口和普通Java对象映射到数据库中。

<h4>Mybatis安装</h4>
方式一：直接将mybatis-x.x.x.jar文件从buildpath引入项目。
方式二：在Maven项目的pom.xml文件中添加:
```
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis</artifactId>
  <version>x.x.x</version>
</dependency>
```
<h4>Mybatis基本原理</h4>
每个基于 MyBatis 的应用都是以一个**SqlSessionFactory** 的实例为中心的。SqlSessionFactory 的实例可以通过 SqlSessionFactoryBuilder 获得。而 SqlSessionFactoryBuilder 则可以从 XML 配置文件或一个预先定制的 Configuration 的实例构建出 SqlSessionFactory 的实例。然后通过SqlSessionFactory 来获取**SqlSession，而SqlSession完全包含了面向数据库执行 SQL 命令所需的所有方法。**
XML 文件中构建 SqlSessionFactory 的实例非常简单，建议使用类路径下的资源文件进行配置。Resources 是Mybatis的一个工具类，它包含一些实用方法，可使加载资源文件更加容易。获取SqlSessionFactory如下：
```
String resource = "org/mybatis/example/mybatis-config.xml";
InputStream inputStream = Resources.getResourceAsStream(resource);
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
```
上面获取SqlSessionFactory依赖于mybatis-config.xml这个文件，文件中定义了数据库连接实例的数据源（DateSource）、事务管理器等信息。简单的xml配置文件如下：
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
  <environments default="development">
    <environment id="development">
      <transactionManager type="JDBC"/>
      <dataSource type="POOLED">
        <property name="driver" value="${driver}"/>
        <property name="url" value="${url}"/>
        <property name="username" value="${username}"/>
        <property name="password" value="${password}"/>
      </dataSource>
    </environment>
  </environments>
  <mappers>
    <mapper resource="org/mybatis/example/BlogMapper.xml"/>
  </mappers>
</configuration>
```
其中比较重要的标签分别是< transactionManager>事务管理器标签、< dataSource>数据库连接实例数据源、< mappers>mapper 映射器集合（这些 mapper 的 XML 文件包含了 SQL 代码和映射定义信息）。

除了通过XML配置文件获取SqlSessionFactory外，也可以通过Java程序来获取，如下：
```
DataSource dataSource = BlogDataSourceFactory.getBlogDataSource();
TransactionFactory transactionFactory = new JdbcTransactionFactory();
Environment environment = new Environment("development", transactionFactory, dataSource);
Configuration configuration = new Configuration(environment);
configuration.addMapper(BlogMapper.class);
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
```
上面的Java程序，通过映射器类BlogMapper.class来映射Java对象与数据库记录的关系。虽然Java程序摆脱了XML配置文件的依赖，但是注解的限制性以及一些高级映射的复杂性（嵌套 Join 映射）在Java程序中无法完成，所以推荐使用XML配置文件方式来使用Mybatis。

前面说到通过SqlSessionFactory可以获取到SqlSession，然后由SqlSession来执行操作数据库语句，下面进入这个过程，并解析。首先来看到版本一：
```
SqlSession session = sqlSessionFactory.openSession();
try {
  Blog blog = (Blog) session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 101);
} finally {
  session.close();
}
```
从sqlsessionFactory获取到SqlSession，然后session执行selectOne方法，调用BlogMapper的selectBlog方法，并传入参数101。这是最直接的方式。下面我们看方式二：
```
SqlSession session = sqlSessionFactory.openSession();
try {
  BlogMapper mapper = session.getMapper(BlogMapper.class);
  Blog blog = mapper.selectBlog(101);
} finally {
  session.close();
}
```
首先也是获取session，然后是获取映射器类BlogMapper，然后执行映射器类的selectBlog方法。通过比较我们能够发现，第二种方式，能够让我们更加清晰的知道方法执行的参数、返回值，同时提供了类型安全的代码，不会因为字符串字面值填写错误而出错以及强制类型转换。

下面我们再来看看上面执行的SQL的执行过程，大致了解一下SQL 语句映射。下面给出一个满足上面两种方式执行的XML配置文件：
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.mybatis.example.BlogMapper">
  <select id="selectBlog" resultType="Blog">
    select * from Blog where id = #{id}
  </select>
</mapper>
```
配置文件中除了文档声明信息外，其他部分都具有良好的自解释性。
在命名空间“org.mybatis.example.BlogMapper”中定义了一个名为“selectBlog”的映射语句，这样它就允许你使用指定的完全限定名“org.mybatis.example.BlogMapper.selectBlog”来调用映射语句，就像上面的例子中做的那样：
```
Blog blog = (Blog) session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 101);
```
上面这种方式和使用完全限定名调用 Java 对象的方法是相似的，这个命名可以直接映射到在命名空间中同名的 Mapper 类并匹配方法的名字、参数、返回值来获取方法，就可以很容易调用对应Mapper接口的方法。但是下面这种方式会更具优势，就像之前说的，不基于字符串，更加安全等：
```
BlogMapper mapper = session.getMapper(BlogMapper.class);
Blog blog = mapper.selectBlog(101);
```
关于命名空间，以前的MyBatis 版本是可选的，现在的是必须要填写的，目的是希望能比只是简单的使用更长的完全限定名来区分语句更进一步。
关于命名解析，为了减少输入量，MyBatis 对所有的命名配置元素（包括语句，结果映射，缓存等）使用了如下的命名解析规则。

>**完全限定名**（比如“com.mypackage.MyMapper.selectAllThings”）将被直接查找并且找到即用。
**短名称**（比如“selectAllThings”）如果全局唯一也可以作为一个单独的引用。如果不唯一，有两个或两个以上的相同名称（比如“com.foo.selectAllThings ”和“com.bar.selectAllThings”），那么使用时就会收到错误报告说短名称是不唯一的，这种情况下就必须使用完全限定名。

对于像 BlogMapper 这样的映射器类（Mapper class）来说，还可以直接在Java程序中使用注解来做操作，但是因为有些复杂的映射语句Java程序无法完成，所以还是推荐使用XML配置文件形式来实现。
```
package org.mybatis.example;
public interface BlogMapper {
  @Select("SELECT * FROM blog WHERE id = #{id}")
  Blog selectBlog(int id);
}
```

<h4>作用域（Scope）和生命周期</h4>
讨论作用域与生命周期的含义是为了更好的理解和使用Mybatis。
**SqlSessionFactoryBuilder**创建了完SqlSessionFactory之后，就不再需要它了。因此 SqlSessionFactoryBuilder 实例的最佳作用域是方法作用域（也就是局部方法变量）。
**SqlSessionFactory**SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在。因此 SqlSessionFactory 的最佳作用域是应用作用域。可以使用单例模式或者静态单例模式。
**SqlSession**每个线程都应该有它自己的 SqlSession 实例。SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。如果你现在正在使用一种 Web 框架，要考虑 SqlSession 放在一个和 HTTP 请求对象相似的作用域中。换句话说，每次收到的 HTTP 请求，就可以打开一个 SqlSession，返回一个响应，就关闭它。
```
SqlSession session = sqlSessionFactory.openSession();
try {
  // do something
} finally {
  session.close();
}
```
**映射器实例（Mapper Instances）**映射器是创建用来绑定映射语句的接口。映射器接口的实例是从 SqlSession 中获得的。因此从技术层面讲，映射器实例的最大作用域是和 SqlSession 相同的，因为它们都是从 SqlSession 里被请求的。为了保持简单和易管理，最好把映射器放在方法作用域（method scope）内。
```
SqlSession session = sqlSessionFactory.openSession();
try {
  BlogMapper mapper = session.getMapper(BlogMapper.class);
  // do work
} finally {
  session.close();
}
```