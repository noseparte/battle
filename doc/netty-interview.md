# Netty 经典面试题

## netty的线程模型 

```
  1.private static EventLoopGroup boss = new NioEventLoopGroup();
    private static EventLoopGroup work = new NioEventLoopGroup();
    boss、work分别指什么？
```

```
  服务端启动的时候，创建了俩个NioEventLoopGroup,他们实际是俩个独立的Reactor线程池。
  一个用于接收客户端的TCP连接，另一个用于处理I/O相关的读写操作，或者执行系统Task、定时      任务Task等。
  Netty用于接收客户端请求的线程池职责如下。
    (1) 接收客户端TCP连接，初始化Channel参数；
    (2) 将链路状态变更事件通知给ChannelPipeline。
  Netty处理I/O操作的Reactor线程池职责如下。
    (1) 异步读取通信对端的数据报，发送读事件到ChannelPipeline;
    (2) 异步发送消息到通信对端，调用Pipeline的消息发送接口;
    (3) 执行系统调用Task;
    (4) 执行定时任务Task，例如链路空闲状态检测定时任务;(心跳包 Ping-Pong)
```

## Handler的执行顺序
```
  2.ChannelPipeline中的Handler的执行顺序是什么？
```

```
  Inbound -- Outbound
```


## TCP 粘包/拆包
```
  3.Netty是如何解决TCP 粘包/拆包问题？有哪几种？
```

```
  1) LineBasedFrameDecoder+StringDecoder 基于换行符("\n"或者"\r\n")
  2) DelimiterBasedFrameDecoder+StringDecoder 基于自定义分隔符(“_$”)
  3) FixedLengthFrameDecoder+StringDecoder 基于固定长度(100)解码

```


## Netty的异步通信

```
  4.Netty是如何实现异步通信的？
```

```
  
```