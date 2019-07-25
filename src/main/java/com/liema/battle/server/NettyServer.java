package com.liema.battle.server;

import com.liema.battle.constant.NettyConstant;
import com.liema.battle.codec.NettyMessageDecoder;
import com.liema.battle.codec.NettyMessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class NettyServer {

    public void bind() throws Exception {

        EventLoopGroup work = new NioEventLoopGroup();
        EventLoopGroup boss = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();

        b.group(work, boss)
                .option(ChannelOption.SO_BACKLOG, 100)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline ch = socketChannel.pipeline();
                        ch.addLast(
                                new NettyMessageDecoder(1024 * 1024, 4, 4));
                        ch.addLast(new NettyMessageEncoder());
                        ch.addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
                        ch.addLast(new LoginAuthRespHandler());
                        ch.addLast("heartBeatHandler", new HeartBeatRespHandler());
                    }
                });
        b.bind(NettyConstant.REMOTEIP, NettyConstant.PORT).sync();

        System.out.println("Netty Server Start ok: " + NettyConstant.REMOTEIP + ":" + NettyConstant.PORT);
    }

    public static void main(String[] args) {
        try {
            new NettyServer().bind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
