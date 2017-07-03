from liu

# Spring5官方文档学习笔记----bean部分 #

1、设置别名，<alias name= "fromName" alias = "toName">

2、通常情况下，指定要构造的bean类，容器本身通过反射调用bean
的构造方法直接创建bean，与在java代码中使用new是等价的。

3、使用构造函数方式创建bean时，要提供无参的构造方法。

4、当通过静态工厂方法创建对象时，首先，创建对象时，通过传入接口实例，来创建对象，配置时，bean中传入的不再是实现类，而是静态工厂类，需要添加factory-method属性，也就是静态工厂方法名字，同时在bean的子标签<constructor-arg>中配置该工厂方法的参数，bean的子标签<property>设置setter方法要传入的值。

5，使用实例工厂的话，不同之处在于要配置实例工厂的bean。当有多个工厂时，都需要进行配置。

6，基于构造函数的依赖注入：
 <construct-args>   

 ref标签设置引用类型的参数。
 type标签表示参数类型，基本数据类型可以直接写，比如int。String要写为java.lang.String
index显示指定参数索引
如果出现参数名称歧义的问题，可以使用@ConstructorProperties注解重新设置参数名称。
例如：@ConstructorProperties({"years", "ultimateAnswer"})

7、在setter方法上面添加注解@Required来检查依赖是否注入。

8、如果第三方库没有提供setter方法，使用构造函数注入是一个很好的选择。

9、容器分层，父容器id与子容器id名称相同，可以使用ref标签的parent属性。

10、当使用bean的集合合并的时候，子集合的bean标签的parent属性定义父集合，子集合的props标签的merge属性为true时，可以将父子集合的merge属性进行合并。

11、Spring把null字符串都默认为''"，用<null/>表示null值

12、xmlns:p="http://www.springframework.org/schema/p" 
可以使用p标签简略编写。

 <bean name="classic" class="com.example.ExampleBean">
        <property name="email" value="foo@bar.com"/>
    </bean>

    <bean name="p-namespace" class="com.example.ExampleBean"
        p:email="foo@bar.com"/>

13.可以使用混合属性名字，例如：
<bean id="foo" class="foo.Bar">
    <property name="fred.bob.sammy" value="123" />
</bean>

foo.Bar类中，有一个属性fred，fred中有bob，bob中有sammy，sammy的值为123。
在这里，最后一个属性前面的所有属性不能为null，也就是fred、bob不能为null。

14、bean标签的属性depends-on="A"，仅在singleton模式下适用，表示当前A类在当前bean初始化之前初始化，在当前bean销毁之前销毁。depends-on中可以存放多个值，可以使用空格，逗号，或者分号分隔开。

15、bean标签中的属性lazy-init属性为true，为延迟加载，lazy-init，ApplicationContext在读取所有的bean的时候，会对所有bean进行预初始化，如果不希望IOC对该bean进行与初始化，而是在第一次请求的时候，直接创建一个bean实例。

16、当一个延迟加载的bean是一个非延迟加载的Singleton bean的依赖时，ApplicationContext会初始化这个延迟加载的bean，因为必须提供Singleton bean的依赖。在其他地方是非延迟初始化的。

17、自动装配bean，默认是没有自动装配的，也就是Autowired=true
    自动装配有四种模式：
     ---- no：默认为no，无自动装配，当系统比较庞大的情况下，Spring官方推荐使用默认装配，因为这样可以很清晰的描述系统结构。
     ---- byName：通过属性名称自动装配，找到对应名称的bean，通过setter方法将属性名称赋值。
     ---- byType：通过类型自动装配，如果系统中只有一个属性的类型与之对应，那么将自动装配，如果该类型大于一个的话，会抛出异常，如果没有的话，那么bean的属性的不会被赋值。
     ---- constructor：通过构造函数自动装配，如果没有与之对应的bean的参数类型，那么会出现错误。

18、property与constructor-arg会覆盖自动装配。

19、bean标签的autowire-candidate=false，可以将该bean排除在自动装配之外。

20、1、Spring支持bean的六种作用域，如果使用AppliactionContext的话，只可以使用5种。
    ---- singleton：（默认）每一个Spring IOC容器单个bean定义只能产生一个对象实例。
    ---- prototype：单个bean定义可以创建任何数量的定义。
    ---- request：单个bean定义的创建实例的作用域是一次Http request请求。
    ---- session：单个bean定义的创建实例的作用域是session的生命周期。
    ---- application：单个bean定义的创建实例的作用域是ServletRequest的生命周期。
    ---- websocket：单个bean定义的创建实例的作用域是websocket的生命周期。

21、使用singleton时，单个bean只管理一个共享实例，当根据配置文件创建bean之后，这个单独的实例，存储在单个bean的缓存中。任何对这个bean的请求和引用都会返回那个缓存的对象。


![single示意图](http://upload-images.jianshu.io/upload_images/5943394-8323565d77a8fb90.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

22、非单例模式，bean部署采用原型作用域时，每次产生一个bean的创建的请求时，都会创建一个新的bean的实例。

23、与其他作用域相比，Spring不管理prototype bean的生命周期，容器初始化，配置，组装原型对象，传递给客户端。使用prototype bean的时候，要注意，释放对象，因为spring不会管理bean的销毁工作。

24、request session application websocket 都是由ApplicationContext实现，由正规的IoC容器ClassPathXmlApplicationContext来使用这些作用域，会抛出IllegalStateException异常。

25、request作用域注解：@RequestScope
      session作用域注解：@SessionScope
      application作用域注解：@ApplicationScope
      
26、当把将不同作用范围的bean注入时，可以使用代理完成这一项工作。

27、当将propertyScope的bean声明为<aop:scoped-proxy/>时，每个在共享代理的方法调用都会引起一个新目标实例的创建。

28、<aop:scoped-proxy/>默认为CGLIB代理。

29、通过配置<aop:scoped-proxy/>标签的proxy-target-class="false"属性，来配置jdk代理，但是这时候需要有接口存在。

30、通过实现org.springframework.beans.factory.config.Scope接口
重写其中的方法，在使用的过程中要先创建当前自定义scope实例，然后通过BeanFactory的registScope() 方法对当前作用域命名和注册。

31、可以通过实现InitializingBean接口，与DisposableBean接口，实现其中的方法，完成在初始化或者销毁时的回调函数。但是官方不推荐这样做，因为这样会与Spring框架耦合，官方推荐采用@PostConstruct、@PreDestroy或者xml配置，在bean标签的的属性init-method="methodName"、destroy-method="methodName"，其中methodName就是回调函数的方法名称。

32、对于这类回调函数，官方推荐使用标准化的方法命名规范，例如init()、initialize()、dispose()，以便于共同开发，保持一致性。

33、对于有许多这样的回调函数，没有必要每次都声明，只需要在其中的一个bean中添加属性default-init-method="init"，后续的所有的bean都将init设置为初始化回调函数。也可以在<beans>标签设置这样的顶层属性。

34、当项目中出现全局beans中配置的方法名与bean中规定的方法名称不相同的时候，bean中属性定义的方法名称会覆盖掉beans中的方法名称。

35、关于初始化与销毁的回调函数一共三种方式，注解配置，实现接口，xml配置。当程序中出现一个bean的多个配置同时存在时。调用优先级顺序如下：
    ---- 先调用注解方式，然后是接口方式，最后是xml方式。
    要注意，配置多个lifecycle method，bean只会执行一次，即优先级最高的那一个。

36、父bean是不能被初始化的，如果想要声明为父bean，那么bean标签中abstract属性一定要为true，否则的话，Spring会尝试初始化这个bean。

37、BeanPostProcessors操作一个bean实例，通过实现BeanPostProcessor接口完成对IoC的扩展。

38、BeanFactoryPostProcessors支持元数据，与BeanPostProcessors一样都是只作用与当前容器。

39、配置数据源时，可以在xml中使用${jdbc.*}，然后使用<context:property-placeholder location="classpath:com/foo/jdbc.properties"/>
使用properties文件。location后的数据之间用逗号隔开。

40、@Autowired完成自动装配，可以作用于构造方法，属性，与setter方法。