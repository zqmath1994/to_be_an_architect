<h1>Spring技术内幕 ：Spring AOP的实现（一）</h1>
<h3>Spring AOP概述</h3>
<h4>AOP概念</h4>
**AOP**-Aspect-Oriented Programming（面向切面编程），是一种**模块化**机制，用来描述分散在对象、类或函数中的横切关注点。说起来还是很复杂，看不懂，接下来解释。
提到模块化，很容易想到的就是面向对象设计，它把相关的数据及其处理方法放在一起。与C语言那种单纯使用子函数进行封装相比，面向对象的模块化特性更完备。然而面向对象这么优秀的设计，还是会出现重复的代码和功能，虽然可以设计成公共函数，但是这种显式的调用，有时候用起来并不是很方便。而AOP的出现，不仅可以将这些重复的代码抽取出来单独维护，还在使用时统一调用提供了丰富灵活的方式。通过AOP提出**横切** 的概念，将模块功能正交化的同时，也在此基础上提供了一些列横切的灵活实现，如通过使用Proxy代理对象、拦截器字节码翻译技术等。
AOP联盟（专门成立用来讨论AOP的标准化）网站上有的**AOP技术** ：
```
    * AspectJ:源代码和字节码级别的编织器，用户需要使用不同于Java的新语言。
    * AspectWerkz：AOP框架，使用字节码动态编织器和XML配置。
    * JBoss-AOP：给予拦截器和元数据的AOP框架，运行在JBoss应用服务器上。以及在AOP中用到的一些相关技术实现。
    * BCEL（Byte-Code Engineering Library）：Java字节码操作类库。
    * Javassist：Java字节码操作类库，JBoss的一个子项目。
```
Spring AOP的实现主要是使用Java本身的语言特性，如Java Proxy代理类、拦截器等技术。Spring AOP除了自身的AOP实现之外，还封装了AspectJ供应用使用。
<h4>Advice通知</h4>
Adivice（通知）定义在连接点做什么，为切面增强提供织入接口。接口具体定义在org.aopalliance.aop.Advice中。其中具体的通知类型有BeforeAdvice、AfterAdvice、ThrowsAdvice等。从名字上可以看出，这几个通知的主要是在被增强的方法执行之前、之后以及抛出异常的时候生效，具体增强的行为可以是，统计被增强方法调用次数（此例为书中例子）等。
<h4>Pointcut切点</h4>
Pointcut（切点）决定Advice通知应该作用与哪些连接点，也就是说通过Pointcut来定义需要增强的方法的集合。同事集合的选取可以按照一定的规则来完成，如某正则表达式、某方法名匹配等。
Spring AOP提供了具体的切点供用户使用，如JdkRegexpMethodPointcut正则表达式匹配的切点、NameMatchMethodPointcut方法名匹配的切点。
Pointcut基本接口：
```
public interface Pointcut {
	ClassFilter getClassFilter();
	MethodMatcher getMethodMatcher();
	Pointcut TRUE = TruePointcut.INSTANCE;
}
```
从上面的接口定义可以看到，Point匹配需要返回一个MethodMatcher ，对Point的匹配判断，由这个返回的MethodMatcher 来完成，即由MethodMatcher 来判断是否需要对当前方法调用进行增强。
<h4>Advisor通知器</h4>
当完成对目标放方法的切面增强设计（Advice）和关注点的设计（Pointcut）以后，需要一个对象把它们结合起来，完成这个作用的就是**Advisor**。下面给出一个Advisor的实现（DefaultPointcutAdvisor），它有advice和pointcut两个属性，通过这两个属性，分别配置Advice和Pointcut。
```
public class DefaultPointcutAdvisor extends AbstractGenericPointcutAdvisor implements Serializable {
	private Pointcut pointcut = Pointcut.TRUE;

	public DefaultPointcutAdvisor() {
	}
	public DefaultPointcutAdvisor(Advice advice) {
		this(Pointcut.TRUE, advice);
	}
	public DefaultPointcutAdvisor(Pointcut pointcut, Advice advice) {
		this.pointcut = pointcut;
		setAdvice(advice);
	}
	public void setPointcut(Pointcut pointcut) {
		this.pointcut = (pointcut != null ? pointcut : Pointcut.TRUE);
	}
	@Override
	public Pointcut getPointcut() {
		return this.pointcut;
	}
	@Override
	public String toString() {
		return getClass().getName() + ": pointcut [" + getPointcut() + "]; advice [" + getAdvice() + "]";
	}

}
```
上面的实现类中，pointcut默认设置为Pointcut.True,这个Pointcut.True在Pointcut接口中定义为：
```
Pointcut TRUE = TruePointcut.INSTANCE;
```
TruePointcut.INSTANCE是一个单例，创建一次后就一直调用同一个。同时在TruePointcut的methodMatcher视线中，使用TrueMethodMatcher作为方法的匹配器，这个方法匹配器对任何的方法匹配都返回true的结果，也就是指任何方法都能匹配成功。TrueMethodMatcher也是一个单例。TruePointcut的实现：
```
class TruePointcut implements Pointcut, Serializable {

	public static final TruePointcut INSTANCE = new TruePointcut();
	private TruePointcut() {
	}
	@Override
	public ClassFilter getClassFilter() {
		return ClassFilter.TRUE;
	}
	@Override
	public MethodMatcher getMethodMatcher() {
		return MethodMatcher.TRUE;
	}
	private Object readResolve() {
		return INSTANCE;
	}
	@Override
	public String toString() {
		return "Pointcut.TRUE";
	}

}
```
<h3>Spring AOP的设计与实现</h3>
<h4>JVM的动态代理特性</h4>
在Spring AOP的视线中，使用的核心技术就是动态代理，而这种动态代理实际上就是JDK的一个特性（JDK 1.3以上的版本）。通过动态代理特性，可以为任意Java对象创建代理对象。这个特性是通过Java Reflection API来完成的，在具体了解Reflection之前，先了解Proxy模式（代理模式）：
![代理模式类图](https://i.loli.net/2017/08/16/5993bcd28d033.png)
从上图中可以看到有一个RealSubject，这个对象就是目标对象，而在代理模式的设计中，会设计一个接口与目标对象一直的代理对象Proxy，它们都实现了接口Subject的request方法。在这种情况下，对目标对象的request的调用，往往就被代理对象“浑水摸鱼”给拦截了，通过这种拦截，代理可以在调用目标对象的request方法前后做一系列处理，而这些处理对目标对象时透明的、毫不知情的，这就是Proxy（代理）模式。代理模式的调用关系：
![代理模式调用关系](https://i.loli.net/2017/08/16/5993bea531960.png)
这种代理模式在JDK中已经实现，可以查看java.lang.reflect.Proxy类，在使用时，需要为代理对象（Proxy）设计一个回调方法，这个回调方法起到的作用是，在其中加入了作为代理需要额外处理的动作（参考上图的preOperation和postOperation方法）。这个回调方法，如果在JDK中实现，需实现下面所示的InvocationHandler接口：
```
public interface InvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable;
}
```
在这个接口方法中，只声明了一个invoke方法，第一个参数是代理对象实例，第二个是method方法对象，代表当前Proxy被调用的方法，最后一个是被调用方法中的参数。如何将invoke与Proxy链接上，只要在实现通过调用Proxy.newInstance方法生成具体Proxy对象时把InvocationHandler设置到参数里面就可以了，剩下的由Java虚拟机完成。
<h4>Spring AOP的应用场景与实现</h4>
AOP在应用中的使用，如通过AOP实现应用程序中的日志功能，另一方面在Spring内部，一些支持模块也是通过Spring AOP来实现，如事务处理等。下面以ProxyFactoryBean为例来了解Spring AOP的具体设计和实现。
**配置ProxyFactoryBean**
```
<bean id="testAdvisor" class="com.abc.TestAdvisor" />
<bean id="testAOP" class="org.springframework.aop.ProxyFactoryBean">
	<property name="proxyInterfaces">
		<value>com.test.AbcInterface</value>
	</property>
	<property name="target">
		<bean class="com.abc.TestTarget" />
	</property>
	<property name="interceptorNames">
		<list>
			<value>testAdvisor</value>
		</list>
	</property>
</bean>
```
①定义使用的通知器Advisor。②定义ProxyFactoryBean，配置AOP实现相关的重要属性，如proxyInterfaces、interceptorNames、target等；其中interceptorNames为通知器。③定义target属性，就是之前提到的需要被增强的对象，也就是base对象。
**ProxyFactoryBean生成AopProxy代理对象**
在ProxyFactoryBean中，需要为target目标对象生成Proxy代理对象，从而为AOP横切面的编织做好准备工作，具体的代理生成工作，是通过JDK的Proxy或CGLIB来完成的。
![代理对象生成](https://i.loli.net/2017/08/16/5993e6e26b172.png)
从FactoryBean中获取对象，是以getObject方法作为入口完成的，ProxyFactoryBean实现中的getObject方法，是FactoryBean需要实现的接口。
```
@Override
	public Object getObject() throws BeansException {
		//初始化通知器链
		initializeAdvisorChain();
		//对singleton和prototype区分，生成对应的proxy
		if (isSingleton()) {
			return getSingletonInstance();
		}
		else {
			if (this.targetName == null) {
		logger.warn("Using non-singleton proxies with singleton targets is often undesirable. " +
						"Enable prototype proxies by setting the 'targetName' property.");
			}
			return newPrototypeInstance();
		}
	}
```
getObject方法首先对通知器链进行初始化，通知器链封装了一系列的拦截器，这些拦截器都要从配置中读取，然后为代理对象的生成做好准备。在生成代理对象时，因为Spring中有singleton和prototype类型这两种不同的bean，所以要对代理对象的生成做一个区分。

为Proxy代理对象配置Advisor链是在initializeAdvisorChain方法中完成的，这个方法中的初始化过程有一个advisorChainInitialized标志位来判断是否已经初始化。也就是说初始化工作发生在应用第一次通过ProxyFactoryBean去获取代理对象的时候。完成初始化后，会读取配置中出现的所有通知器，获取通知器过程只需要将通知器名字交给容器的getBean方法即可（通过IoC容器回调完成），然后把获得的通知器加入拦截器链中，由addAdvisorOnChainCreation方法完成。
```
private synchronized void initializeAdvisorChain() throws AopConfigException, BeansException {
		if (this.advisorChainInitialized) {//标志位
			return;
		}
		if (!ObjectUtils.isEmpty(this.interceptorNames)) {
			//判断一些错误情况并抛出错误
			...
			// 添加Advisor链的调用，通过interceptorNames属性进行配置
			for (String name : this.interceptorNames) {
				if (name.endsWith(GLOBAL_SUFFIX)) {
					if (!(this.beanFactory instanceof ListableBeanFactory)) {
						throw new AopConfigException(
						"Can only use global advisors or interceptors with a ListableBeanFactory");
					}
					addGlobalAdvisor((ListableBeanFactory) this.beanFactory,
							name.substring(0, name.length() - GLOBAL_SUFFIX.length()));
				}
				else {
					//在此加入命名的拦截器advice，并检查是singleton还是prototype类型
					Object advice;
					if (this.singleton || this.beanFactory.isSingleton(name)) {
						// 加入advice或者advisor
						advice = this.beanFactory.getBean(name);
					}
					else {
						//对prototype类型Bean的处理
						advice = new PrototypePlaceholderAdvisor(name);
					}
					addAdvisorOnChainCreation(advice, name);
				}
			}
		}
		this.advisorChainInitialized = true;
	}
```
生成singleton的代理对象在getObject方法中的getSingletonInstance()方法中完成，这个方法是ProxyFactoryBean生成AopProxy代理对象的调用入口。代理对象会封装对target目标对象的调用，也就是生成的代理对象会拦截目标方法。生成过程：首先读取ProxyFactoryBean中的配置，做好必要准备（如设置代理的方法调用接口），再通过AopProxy类来具体生成代理对象。
```
private synchronized Object getSingletonInstance() {
		if (this.singletonInstance == null) {
			this.targetSource = freshTargetSource();
			if (this.autodetectInterfaces && getProxiedInterfaces().length == 0 && !isProxyTargetClass()) {
				//根据AOP框架来判断需要代理的接口
				Class<?> targetClass = getTargetClass();
				if (targetClass == null) {
		throw new FactoryBeanNotInitializedException("Cannot determine target class for proxy");
				}
				//设置代理对象的接口
		setInterfaces(ClassUtils.getAllInterfacesForClass(targetClass, this.proxyClassLoader));
			}
			super.setFrozen(this.freezeProxy);
			//使用ProxyFactory生成Proxy
			this.singletonInstance = getProxy(createAopProxy());
		}
		return this.singletonInstance;
	}
	//通过createAopproxy返回AopProxy得到代理对象
	protected Object getProxy(AopProxy aopProxy) {
		return aopProxy.getProxy(this.proxyClassLoader);
	}
```
上面出现了AopProxy类型的对象，AopProxy是一个接口，由两个子类实现，一个是CglibAopProxy，另一个是JdkDynamicProxy。Spring分别通过CGLIB和JDK来生成需要的Proxy代理对象。

具体的代理对象的生成，是在ProxyFactoryBean的基类AdvisedSupport的实现中借助AopProxyFactory完成的（JDK或CGLIB），在ProxyFactoryBean的基类ProxyCreatorSupport可以看到，具体的AopProxy是通过AopProxyFactory来生成的。而所需的信息都封装在AdvisedSupport里，因为ProxyCreatorSupport是AdvisedSupport的其中一个子类，所以生成AopProxy的方法设置this本身作为参数。代码如下：
```
//ProxyCreatorSupport类中的方法
	protected final synchronized AopProxy createAopProxy() {
		if (!this.active) {
			activate();
		}
		return getAopProxyFactory().createAopProxy(this);
	}
```
上面的AopProxyFactory的具体实现是由DefaultAopProxyFactory实现，其中创建AopProxy的代码为：
```
@Override
	public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
		if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
			Class<?> targetClass = config.getTargetClass();
			if (targetClass == null) {
				throw new AopConfigException("TargetSource cannot determine target class: " +
						"Either an interface or a target is required for proxy creation.");
			}
			if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
				return new JdkDynamicAopProxy(config);
			}
			return new ObjenesisCglibAopProxy(config);
		}
		else {
			return new JdkDynamicAopProxy(config);
		}
	}
```
上面生成AopProxy代理对象最最重要的就是new ObjenesisCglibAopProxy(config)和JdkDynamicAopProxy(config)。下面主要解析一下JDK生成AopProxy代理对象，**Spring默认的方式也是使用DJK来产生代理对象**，如果遇到配置的目标对象不是接口类的实现，会使用CGLIB的方式。
<h4>JDK生成AopProxy代理对象</h4>
JdkDynamicAopProxy使用JDK的Proxy类来生成代理对象，在生成Proxy对象之前，首先需要从advised对象中取得代理对象的代理接口配置，然后调用Proxy的newProxyInstance方法，最终得到对应的Proxy代理对象。在生成代理对象时，需要指明三个参数，一个是类装载器，一个是代理接口，另一个就是Proxy回调方法所在的对象，这个对象需要实现InvocationHandler接口。这个接口的invoke方法提供代理对象的回调入口。**重点：**JdkDynamicAopProxy实现了InvocationHandler接口和invoke方法，也就是说JdkDynamicAopProxy对象本身，在Proxy代理的接口方法被调用时，会触发invoke方法的回调，这个回调方法完成了AOP编织实现的封装。
```
public Object getProxy(ClassLoader classLoader) {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating JDK dynamic proxy: target source is " + this.advised.getTargetSource());
		}
		Class<?>[] proxiedInterfaces = AopProxyUtils.completeProxiedInterfaces(this.advised, true);
		findDefinedEqualsAndHashCodeMethods(proxiedInterfaces);
		return Proxy.newProxyInstance(classLoader, proxiedInterfaces, this);
	}
```
以上内容主要用作自己学习笔记，同时检出书中重点，如有错误，敬请指出。