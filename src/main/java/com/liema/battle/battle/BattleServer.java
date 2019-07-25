package com.liema.battle.battle;

import com.liema.battle.client.HeartBeatReqHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class BattleServer {

    public void run(int port) {
        EventLoopGroup users = new NioEventLoopGroup();
        EventLoopGroup events = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(users, events)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline cp = socketChannel.pipeline();
                            cp.addLast("http-codec", new HttpServerCodec());
                            cp.addLast("heart-beat", new HeartBeatReqHandler());
                            cp.addLast("aggregator",new HttpObjectAggregator(65536));
                            cp.addLast("http-chunked", new ChunkedWriteHandler());
                            cp.addLast("handler", new WebSocketServerHandler());
                        }
                    });
            Channel ch = b.bind(port).sync().channel();
            System.out.println("Web Socket server started at port " + port + ".");
            System.out.println("Open your browser and navigate to http://localhost:" + port + "/");
            ch.closeFuture().sync();
        }catch (Exception e){
            users.shutdownGracefully();
            events.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        int port = 8888;
        new BattleServer().run(port);
    }

}
