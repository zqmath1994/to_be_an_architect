第三次学习总结 7.20-7.26
一.maven
  maven 是一个项目管理工具,负责管理项目开发过程.
  ***好处:不用再去到处找jar包,不同版本jar包之间产生冲突的可能减小
  1.maven工程结构
  
  pom.xml(依赖关系的配置文件)
  target(项目输出位置,由maven负责生成)
  src
  --main
    --java(Source Folders 代码区)
    --resources(Resource Folders 配置文件区)
  --test
    --java(Test Source Folders 测试代码区,例如Junit测试类)
    --resources
  
  2.生命周期
    编译(mvn compile)、测试(mvn test)、打包(mvn package)、集成测试、验证、部署(mvn install)
    maven中所有的执行动作(goal)都需要指明自己在这个过程中的执行位置，然后maven执行的时候，就依照过程的发展依次调用这些goal进行各种处理.
  
  3.版本规范
    <!--组织(公司)标识符-->
    <groupId>com.xxx</groupId>
    <!--项目唯一标识符-->
    <artifactId>mavenSpringDemo03</artifactId>
    <!--打包方式,项目类型-->
    <packaging>war</packaging>
    <!--项目的版本-->
    <version>1.0-SNAPSHOT</version>
    
    <!--依赖关系-->
    <dependencies>
      <!--具体依赖关系,例如以junit为例-->
      <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
      </dependency>
    </dependencies>
二.mybatis,mybatis-spring结合
  如果了解hibernate,mybatis就比较简单.
  mybatis是一个ORM框架,持久层框架,
  只使用XML和注解来配置和映射基本数据类型、Map接口和POJO到数据库记录.
  相对Hibernate的"全自动",Mybatis 是一种“半自动化”的ORM实现,需要自己写SQL.
  1.mybatis结构
    mybatis-config.xml(相当于hibernate的config.xml)
      配置内容：(数据源)dataSource、(事务)transaction、properties、(别名)typeAliases、(全局设置)settings、mapper配置.
    SqlSessionFactory--会话工厂，作用是创建SqlSession,开发中以单例模式管理 SqlSessionFactory.
    SqlSession--会话，是一个面向用户程序员的接口，使用mapper代理方法开发时不需要程序员直接调用sqlSession的方法.
    SqlSession线程不安全，适用在方法体内
  2.mybatis开发DAO的方法:
    mapper代理方法,只要写mapper接口(相当于dao接口),mybatis自动根据mapper接口和mapper接口对应的statement自动生成代理对象（接口实现类对象）。
	 1）xxxMapper.xml中namespace是mapper接口的全限定名
	 2）xxxMapper.xml中statement的id为mapper接口方法名(select insert delete update对应的方法)
	 3）xxxMapper.xml中statement的输入映射类型(parameterType)和mapper接口方法输入参数类型一致,例如基本数据类型,pojo
	 4) xxxMapper.xml中statement的输出映射类型(resultType)和mapper接口方法返回结果类型一致
  3.和spring结合
    config.xml下的数据源,别名,mapper配置交给spring管理,在ApplicationContext.xml中进行配置
    需要引入依赖
    <!--mybatis-spring适配器-->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>1.3.1</version>
    </dependency>

