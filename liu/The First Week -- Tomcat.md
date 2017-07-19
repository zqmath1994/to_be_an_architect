Server组件：可以看成Tomcat运行实例的抽象。


作用1：提供了监听器机制，用于在Tomcat整个生命周期中对不同事件进行处理。
作用2：提供了Tomcat容器全局的命名资源的实现。
作用3：监听某个端口以接受SHUTDOWN命令。

Service组件：可以看成Tomcat内不同服务的抽象。
其中包含Connector、Executor、Container(容器)。


Connector：负责接收客户端连接和客户端请求的处理加工。可以有多个Connector，每一个Connector对应监听一个端口。

Protocol: 协议，BIO(传统IO)、NIO、AJP(静态资源交给apache时的协议 )。
BIO：一个请求对应Executor一条线程，默认上限200。

NIO：先由一条线程接受过来，由多条线程(通常为CPU个数)轮询执行，默认上限10000。

在这里解析request和response用了门面模式，将Request替换成了RequestFacade，不会将全部的数据暴露给开发人员，提高安全性。

Endpoint：接收端的抽象,有JIOEndpoint、NIOEndpoint、AJPEndpoint。

Acceptor：接受客户端连接的接收器组件，并交给Executor。

接受请求总要有个峰值，BIO模式默认为200，这个值由LimitLatch，一个计数器，基于AQS并发框架实现，但是并没有使用AQS内部的状态变量，而是定义了一个AtomicLong类型的变量用于计数，copyUpOrAwait()方法，当计数超过最大限制值时会阻塞线程，countDown递减和唤醒线程，每处理完一个套接字请求，执行一次countDown。


如果是NIO模式会稍有不同，NIO模式默认为10000，如果超过10000，不再接收请求，但是这时操作系统还维护了一个队列，会接收请求，上限为100.
BIO阻塞模型


Acceptor：默认线程只有一条，监听并接收连接。
ServerSocketFactory：针对是否是https，选择是否对消息进行加密。
SocketProcessor：对套接字进行处理并输出相应报文，连接计数器减一，关闭套接字。

NIO模型

Acceptor接收Socket对象返回SocketChannel对象，非阻塞。
然后注册NioChannel，这里有值得学习的地方，由于NioChannel大量的被创建，我们可以将NioChannel缓存起来，将NioChannel放入一个LinkedBlockingQueue中，需要的时候，将原来对象中的值作重置，如此一来不必生成和消除NioChannel对象。需要对连接进行管理，管理的方式是不断地遍历事件列表，对相应的事件作出处理，这个任务交给poller轮询事件列表，一旦发现相应的事件，进而扔进线程池中执行任务，如果Connector没有创建线程池，那么由poller自己执行。如果有，由线程池处理，然后通过协议解析器Http11Nioprocessor组件对Http协议进行解析，同时通过适配器(Adapter)匹配到指定的容器进行处理，最后响应客户端。

Executor：接受客户端请求的线程池。
Tomcat的线程池实现是基于JDK线程池的一层包装，包装内容如下：
1、个性化定义Thread的名称。
2、配置拒绝策略，执行失败的任务在再次进入队列中重新执行。(待研究)
3、当其中有一个Context停止的时候，要对所有线程进行重置。
4、重写offer方法，只有coresize<maxiumSize的时候，才有潜力可挖，会乐观的使用线程池去执行，会很“凶”的压榨线程池的能力。


Processor：处理客户端请求的处理器。
Mapper：提供了对客户端请求的URL映射功能，通过URL找到对应的Servlet。
CoyoteAdaptor：负责将Connector与Engine容器适配起来，把Request和Response解析后交给Engine。

Engine：全局的引擎容器。
Host：虚拟主机。
Context：Web应用的抽象。
Wrapper：Servlet的抽象。

Tomcat中责任链(管道模式)的概念


容器中的标准实现分别为StandardEngine、StandardHost、StandardContext、StandardWrapper，Request和Response对象在这四个容器之间进行传递，
每一个容器都有对应Value，通过invoke实现
getPipeline().getFirst().invoke(request, response);，执行到Wrapper的时候，会先处理FileterChain，然后再处理Servlet。

优点：对整个处理流程进行详细划分，互相划分的每一个小模块互相独立且各自负责一段逻辑处理，前一模块的输出作为后一模块的输入，修改时可针对某一模块进行修改，细化颗粒度。

LifeCycle：生命周期管理，基于观察者模式。

Tomcat中的类加载
1、同一个web服务器，各个项目之间各自使用的类库隔离。
2、同一个web服务器，各个web项目之间可以提供共享的Java类库。
3、服务器类库与应用程序类库隔离。
4、支持热插拔。
 
Common是所有自定义类加载器的父类。
与双亲委派不同的是子优先，通过一个变量控制是否打破双亲委派机制。