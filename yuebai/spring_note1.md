<h4>1.3  Spring的整体架构</h4>

![spring_note1.1.png](https://i.loli.net/2017/07/19/596f50e55f6b2.png)</br>
  Spring IoC模块：提供最基本的IOC容器BeanFactory（接口）的接口与实现，实现如XmlBeanFactory、SimpleJndiBeanFactory、StaticListableBeanFactory等最基本的容器形态。也有容器的高级形态ApplicationContext应用上下文供用户使用，如FileSystemXmlApplicationContext、ClassPathXMLApplicationContext，对应用来说，是IoC容器更面向框架的使用方式。还有各种支持实现如国际化的消息和应用支持事件等。

  Spring AOP模块：集成AspectJ作为AOP的一个特定实现，同事还在JVM动态代理/CGLIB的基础上，实现了一个AOP框架，作为集成其他模块的工具。

  Spring MVC模块：以DispatcherServlet为核心，实现MVC模式，包括怎样与Web容器环境的集成，Web请求的拦截、分发、处理和ModelAndView数据的返回，以及如何集成各种UI视图展现和数据表现（PDF、Excel等）。

  Spring JDBC/ORM模块：在JDBC规范的基础上，Spring做了一层封装，使通过JDBC完成对数据库的操作更加简洁。Spring JDBC包提供了jdbcTemplate作为模板类，封装了基本的数据库操作方法，也提供了RDBMS的操作对象，如MappingSqlQuery将数据库记录直接映射到对象集合，类似简单的ORM工具。Spring也提供了对ORM工具的封装，如Hibernate、iBatis等，让这些工具用起来更方便，比如可以将工具的使用和Spring提供的声明式事物结合。同事Spring提供的许多模板对象包装ORM工具的通用过程，如Session的获取和关闭、事务处理的关联等，体现了Spring的平台作用。

  Spring事务处理模块：Spring事务处理是一个通过Spring Aop实现自身功能增强的典型模块。在这个模块中，Spring把应用中事务处理的主要过程抽象出来，通过AOP的切面增强实现了声明式事务处理的功能，使得应用只需在IOc容器中对事务属性进行配置即可完成。同时，事务处理的基本过程和具体的事务处理器实现式无关的，即应用可以选择不同的具体的事务处理机制，如JTA、JDBC、Hibernate等。因为使用了声明式事务处理，这些具体的事务处理机制被纳入Spring事务处理的统一框架中完成，并与具体业务代码解耦。

  Spring远端调用模块：Spring能够将应用解耦，一方面降低设计的复杂性，另一方面，解耦以后将应用分布式部署，从而提高系统整体的性能。在分布式部署后，会用到Spring的远端调用，通过Spring的封装，从一个Spring应用到另一个之间的端到端调用。通过封装，为应用屏蔽了各种通信和调用细节的实现，同时使应用可以选择不同的远端调用来实现，如HTTP调用器、第三方二进制通信实现Hessian/Burlap等。
<h4>2.1 IoC容器的实现</h4>

依赖注入（控制反转）：依赖对象的获得被反转了。之前是主动添加，现在由容器实现。
在Spring中，IoC容器是实现这个模式的载体，它可以在对象生成或初始化时直接将数据注入到对象中，也可以通过将对象引用注入到对象数据域中的方式来注入对方法调用的依赖。这种依赖注入是可以递归的，对象被逐层注入。

<h4>2.2 IoC容器系列的设计与实现：BeanFactory和ApplicationContext</h4>

![spring_note1.2.png](https://i.loli.net/2017/07/19/596f52a928f7f.png)
 
Spring通过定义BeanDefinition来管理基于Spring的应用中的各种对象以及它们之间的相互依赖关系。对于IoC容器来说，BeanDefinition是对依赖反转模式中管理的对象依赖关系的数据抽象，是容器实现依赖反转功能的核心数据结构。

<h4>IoC容器的设计：接口设计</h4>

![spring_note1.3.png](https://i.loli.net/2017/07/19/596f53127a466.png)
 
从接口BeanFactory到HierarchicalBeanFactory，再到ConfigurableBeanFactory是一条主要的BeanFactory设计路径。BeanFactory定义基本的IoC容器规范，HierarchicalBeanFactory接口继承BeanFactory并增加getParentBeanFactory()的接口功能，使BeanFactory具备双亲IoC容器管理功能。ConfigurableBeanFactory接口则是在前面的基础上，定义了一些对BeanFactory的配置功能，如通过setParentBeanFactory()设置双亲IoC容器，通过addBeanPostProcessor()配置Bean后置处理器等。第二条接口设计主线就是以ApplicationContext应用上下文接口为核心的接口设计。我们最常用的应用上下文基本都是ConfigurableApplicationContext或者WebApplicationContext的实现。对于ApplicationContext接口，它通过继承MessageSource、ResourceLoader、ApplicationEventPublisher接口，在BeanFactory简单IoC容器的基础上添加了许多对高级容器的特性的支持。
默认BeanFactory实现DefaultListableBeanFactory，实现了ConfigurableBeanFactory。其他IoC容器如XmlBeanFactory主要在它的基础上做拓展。

<h4>BeanFactory接口设计</h4>

![spring_note1.4.png](https://i.loli.net/2017/07/19/596f5320ac312.png)
 
getBean方法是BeanFactory用来取得IoC容器管理中的Bean。可以通过指定名字、类型对Bean检索。如需获取prototype类型的Bean，用户可以为这个类型的Bean指定构造函数的对应参数。
containsBean方法判断是否含有指定名字的Bean。
isSingleton方法判断指定名字的Bean是否是Singleton类型。用户可以在BeanDefinition中指定Singleton属性。
isPrototype方法判断指定名字的Bean是否是Prototype类型，也可在BeanDefinition中指定。
isTypeMatch方法判断指定名字的Bean的Class类型是否和指定的Class类型一致。后面Class类型由用户指定。
getType方法来查询指定名字Bean的Class类型。
getAliases方法来查询指定名字Bean的所有别名。这些别名在BeanDefinition中定义。

<h4>BeanFactory容器设计原理</h4>

以XmlBeanFactory实现为例。在Spring中，DefaultListableBeanFactory作为一个默认的功能完整的IoC容器来使用。XmlBeanFactory继承自它，在它的基础上，还增加了与XML相关的功能。XmlBeanFactory可以说是读取以XML文件方式定义的BeanDefinition的IoC容器。
    XML读取功能的实现：在XmlBeanFactory中，初始化了一个XmlBeanDefinitionReader对象，来处理XML形式的信息。
    
![spring_note1.5.png](https://i.loli.net/2017/07/19/596f532614e31.png)
 
  构造XmlBeanFactory时，需要指定BeanDefinition的信息来源，而这个信息来源需要封装成Spring中的Resource类来给出。Resource是Spring用来封装I/O操作的类。如我们的BeanDefinition信息以XML文件存在，可以获取Resource：ClassPathResource res = new ClassPathResource(“beans.xml”)；然后将Resource作为构造参数传递给XmlBeanFactory构造函数，从而从BeanDefinition中获取信息来对Bean完成容器的初始化和依赖注入过程。
![spring_note1.6.png](https://i.loli.net/2017/07/19/596f53253123e.png)

<h4>ApplicationContext的应用场景</h4>ApplicationContext除了有容器的基本功能外，还提供了附加服务。

![spring_note1.7.png](https://i.loli.net/2017/07/19/596f532e2fc6d.png)

  支持不同的信息源，MessageSource接口支持国际化的实现。访问资源，对ResourceLoader和Resource的支持，我们可以从不同地方得到Bean定义资源，因为AbstractApplicationContext继承DefaultResourceLoader。支持应用事件，继承了ApplicationEventPublisher接口。在ApplicationContext中提供附加服务，因为与BeanFactory相比，对ApplicationContext的使用是一种面向框架的使用风格，所以一般建议在开发时使用ApplicationContext作为IoC容器的基本形式。

<h4>ApplicationContext容器的设计原理</h4>
    以FileSystemXmlApplicationContext的具体实现为例。ApplicationContext应用上下文的主要功能在FileSystemXmlApplicationContext的基类AbstractXmlApplicationContext中实现了，FileSystemXmlApplicationContext主要实现自身相关的两个功能。其一是如果应用直接启动FileSystemXmlApplicationContext，对于实例化应用上下文的支持，同时启动IoC容器的refresh()过程。在代码中如下：
    
![spring_note1.8.png](https://i.loli.net/2017/07/19/596f531dca694.png)

  这个refresh()过程会牵涉IoC容器启动的一系列复杂操作。具体后面解析。
功能二：FileSystemXmlApplicationContext从文件系统中加载XML的Bean定义资源有关。通过这个过程，可以为在文件系统中读取以XML形式存在的BeanDefinition	做准备，具体实现如下，返回FileSystemResource的资源定位：

![spring_note1.9.png](https://i.loli.net/2017/07/19/596f531eed837.png)
 

<h4>2.3 IoC容器的初始化过程</h4>

IoC容器的初始化由refresh()方法启动，启动包括BeanDefinition和Resource定位、载入和注册。

![spring_note1.10.png](https://i.loli.net/2017/07/19/596f5322e1d5b.png)

上面的代码可以看到Resource定位和载入过程的接口调用。
第一个过程Resource定位过程：指BeanDefinition的资源定位，由ResourceLoader通过统一的Resource接口来完成，这个Resource对各种形式的BeanDefinition的使用提供了统一接口。
第二个过程是BeanDefinition的载入：把用户定义好的Bean表示成IoC容器内部的数据结构，该类型的数据结构就是BeanDefinition，就是POJO对象在IoC容器中的抽象。
第三个过程是向IoC容器注册BeanDefinition的过程：通过调用BeanDefinitionRegister接口的实现来完成。这个过程把载入过程中的BeanDefinition向IoC容器进行注册。注册过程是在IoC内部将BeanDefinition注入到一个HashMap中。
<h5>在Spring IoC的设计中，Bean定义的载入和依赖注入是两个独立的过程。</h5>依赖注入一般发生在应用第一次通过getBean向容器索取Bean的时候。在使用IoC容器时有一个预实例化，可以通过为Bean定义信息中的lazyinit属性，设置这个Bean的依赖注入过程在IoC容器初始化时就预先完成，而不需要等到getBean才触发。

![spring_note1.11.png](https://i.loli.net/2017/07/19/596f532eaa030.png)
