##RPC介绍
远程过程调用（Remote Procedure Call，缩写为 RPC）是一个计算机通信协议。该协议允许运行于一台计算机的程序调用另一台计算机的子程序，而程序员无需额外地为这个交互作用编程。

远程过程调用是一个分布式计算的客户端-服务器（Client/Server）的例子，**远程过程调用总是由客户端对服务器发出一个执行若干过程请求，并用客户端提供的参数**。执行结果将返回给客户端。

为了允许不同的客户端均能访问服务器，许多标准化的 RPC 系统应运而生了。其中大部分采用接口描述语言（Interface Description Language，IDL），方便跨平台的远程过程调用。

![](http://i.imgur.com/wBvFNwd.png)

RPC 本身是 client-server模型,也是一种 request-response 协议。

有些实现扩展了远程调用的模型，实现了双向的服务调用，但是不管怎样，调用过程还是由一个客户端发起，服务器端提供响应，基本模型没有变化。

服务的调用过程为：
client调用client stub，这是一次本地过程调用
client stub将参数打包成一个消息，然后发送这个消息。打包过程也叫做 marshalling
client所在的系统将消息发送给server
server的的系统将收到的包传给server stub
server stub解包得到参数。 解包也被称作 unmarshalling
最后server stub调用服务过程. 返回结果按照相反的步骤传给client

##国内外知名的RPC框架
RPC只是描绘了 Client 与 Server 之间的点对点调用流程，包括 stub、通信、RPC 消息解析等部分，在实际应用中，还需要考虑服务的高可用、负载均衡等问题，所以产品级的 RPC 框架除了点对点的 RPC 协议的具体实现外，还应包括服务的发现与注销、提供服务的多台 Server 的负载均衡、服务的高可用等更多的功能。 目前的 RPC 框架大致有两种不同的侧重方向，一种偏重于**服务治理**，另一种偏重于**跨语言调用**。

服务治理型的 RPC 框架有Alibab Dubbo、Motan 等，这类的 RPC 框架的特点是功能丰富，提供高性能的远程调用以及服务发现及治理功能，适用于大型服务的微服务化拆分以及管理，对于特定语言（Java）的项目可以十分友好的透明化接入。但缺点是语言耦合度较高，跨语言支持难度较大。+

跨语言调用型的 RPC 框架有 Thrift、gRPC、Hessian、Hprose， Finagle 等，这一类的 RPC 框架重点关注于服务的跨语言调用，能够支持大部分的语言进行语言无关的调用，非常适合于为不同语言提供通用远程服务的场景。但这类框架没有服务发现相关机制，实际使用时一般需要代理层进行请求转发和负载均衡策略控制。

##RPC vs RESTful

RPC 的消息传输可以通过 TCP、UDP 或者 HTTTP等，所以有时候我们称之为 RPC over TCP、 RPC over HTTP。RPC 通过 HTTP 传输消息的时候和 RESTful的架构师类似的，但是也有不同。

比较 RPC over HTTP 和 RESTful。

* RPC 的客户端和服务器端师紧耦合的
* 客户端需要知道调用的过程的名字，过程的参数以及它们的类型、顺序等。一旦服务器更改了过程的实现， 客户端的实现很容易出问题
* RESTful基于 http的语义操作资源，参数的顺序一般没有关系，也很容易的通过代理转换链接和资源位置，从这一点上来说，RESTful 更灵
* RPC 操作的是方法和过程，它要操作的是方法对象。
* RESTful 操作的是资源(resource)，而不是方法
* RESTful执行的是对资源的操作，增加、查找、修改和删除等,主要是CURD，所以如果你要实现一个特定目的的操作比如为名字姓张的学生的数学成绩都加上10这样的操作， RESTful的API设计起来就不是那么直观或者有意义。在这种情况下, RPC的实现更有意义，它可以实现一个 Student.Increment(Name, Score) 的方法供客户端调用。

##RPC over TCP
* 可以通过长连接减少连接的建立所产生的花费，在调用次数非常巨大的时候(这是目前互联网公司经常遇到的情况)大并发的情况下，这个花费影响是非常巨大的
* RESTful 也可以通过 keep-alive 实现长连接， 但是它最大的一个问题是它的request-response模型是阻塞的 (http1.0和 http1.1, http 2.0没这个问题)， 发送一个请求后只有等到response返回才能发送第二个请求 (有些http server实现了pipeling的功能，但不是标配)， RPC的实现没有这个限制。

当今用户和资源都是大数据大并发的趋势下，微服务的架构模式越来越多的被应用到产品的设计和开发中， 服务和服务之间的通讯也越发的重要， 所以 RPC 不失是一个解决服务之间通讯的好办法


##RPC服务端
* RPC服务端可以通过调用ServeConn处理单个连接上的请求。
* 多数情况下，RPC服务端将创建一个TCP网络监听器并调用Accept，或创建一个HTTP监听器并调用HandleHTTP和http.Serve。
* 如果没有明确指定RPC传输过程中使用何种编码解码器，默认将使用标准库提供的encoding/gob包进行数据传输的编解码器。

##RPC客户端
* 将要使用RPC服务的客户端需要建立连接，然后在连接上调用NewClient函数。
* net/rpc包提供了便利的rpc.Dial()和rpc.DialHTTP()方法来与指定的RPC服务端建立连接。
* net/rpc包允许客户端使用同步或异步的方式接收RPC服务端的处理结果：**调用RPC客户端的Call()方法将进行同步处理，客户端程序顺序执行，只有接收完RPC服务端的处理结果之后才可继续执行后面的程序；** **调用RPC客户端的Go()方法时将进行异步处理，RPC客户端程序无需等待服务端的结果即可执行后面的程序，而当接收到RPC服务端的处理结果时，再对其进行相应的处理。**
* 如果没有明确指定RPC传输过程中使用何种编码解码器，默认将使用标准库提供的encoding/gob包进行数据传输的编解码器。

### HTTP RPC服务端
http://studygolang.com/articles/7195
	>func main() {
    arith := new(Arith)
    rpc.Register(arith)
    rpc.HandleHTTP()

    l, e := net.Listen("tcp", ":1234")
    defer l.Close()

    if e != nil {
        log.Fatal("listen error:", e)
        return
    }

    go http.Serve(l, nil)
    log.Println("rpc server started!")

    for {
        time.Sleep(1 * time.Second)
    }
### HTTP RPC客户端
	> err = client.Call("Arith.Multiply", args1, &reply1)
    if err != nil {
        log.Fatal("Arith error:", err)
        return

    }
    log.Println(reply1) // 6

##TCP RPC服务端
	>func main() {
    arith := new(Arith)
    server := rpc.NewServer()
    server.Register(arith)

    l, e := net.Listen("tcp", ":1234")
    defer l.Close()

    if e != nil {
        log.Fatal("listen error:", e)
        return
    }

    go server.Accept(l)
    log.Println("rpc server started!")

    for {
        time.Sleep(1 * time.Second)
    }

##TCP RPC客户端 
	>    // 同步方式RPC
    err = client.Call("Arith.Multiply", args1, &reply1)
    if err != nil {
        log.Fatal("Arith error:", err)
        return
    }
    log.Println(reply1) // 6

    // 异步方式RPC
    call2 := client.Go("Arith.Divide", args2, &reply2, nil)
    if call2 != nil {
        if replyCall, ok := <-call2.Done; ok {
            if replyCall.Error != nil {
                log.Fatal("Arith error:", replyCall.Error)
                return
            }
            log.Println(reply2) // {3 1}
        }
    }

    // 异步方式RPC
    call3 := client.Go("Arith.Divide", args3, &reply3, nil)
    if call3 != nil {
        if replyCall, ok := <-call3.Done; ok {
            if replyCall.Error != nil {
                log.Fatal("Arith error:", replyCall.Error) // Arith error:divide by zero
                return
            }
            log.Println(reply3) // {3 1}
        }
    }




