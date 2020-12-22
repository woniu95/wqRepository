package com.wq.netty.client;

import com.wq.netty.constains.Constants;
import com.wq.netty.protocol.RequestProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class CIMClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(CIMClient.class);

    private EventLoopGroup group = new NioEventLoopGroup(0, new DefaultThreadFactory("cim-work"));


    private SocketChannel channel;

    @Value("${netty.server.ip}")
    private String serverIp;
    @Value("${netty.server.port}")
    private int serverPort;
    /**
     * 重试次数
     */
    private int errorCount;

    @PostConstruct
    public void start()  {
        //启动客户端
        startClient(serverIp, serverPort);

    }

    /**
     * 启动客户端
     *
     * @throws Exception
     */
    private void startClient(String ip, int port) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientHandleInitializer())
        ;

        ChannelFuture future = null;
        try {
            future = bootstrap.connect(ip, port).sync();
        } catch (Exception e) {
            errorCount++;
            //todo
            LOGGER.error("连接失败次数达到上限[{}]次", errorCount);

            LOGGER.error("Connect fail!", e);
        }
        if (future.isSuccess()) {
            LOGGER.info("启动 cim client 成功");
        }
        channel = (SocketChannel) future.channel();
    }



    /**
     * 发送消息字符串
     *
     * @param msg
     */
    public void sendStringMsg(String msg) {
        ByteBuf message = Unpooled.buffer(msg.getBytes().length);
        message.writeBytes(msg.getBytes());
        ChannelFuture future = channel.writeAndFlush(message);
        future.addListener((ChannelFutureListener) channelFuture ->
                LOGGER.info("客户端手动发消息成功={}", msg));

    }

    /**
     * 发送 Google Protocol 编解码字符串
     *
     */
    public void sendGoogleProtocolMsg(Long requestId, String requestMsg) {

        RequestProto.ReqProtocol protocol = RequestProto.ReqProtocol.newBuilder()
                .setRequestId(requestId)
                .setReqMsg(requestMsg)
                .setType(Constants.CommandType.MSG)
                .build();

        ChannelFuture future = channel.writeAndFlush(protocol);
        future.addListener((ChannelFutureListener) channelFuture ->
                LOGGER.info("客户端手动发送 Google Protocol 成功={}", requestMsg));

    }


    /**
     * 1. clear route information.
     * 2. reconnect.
     * 3. shutdown reconnect job.
     * 4. reset reconnect state.
     * @throws Exception
     */
    public void reconnect() throws Exception {
        if (channel != null && channel.isActive()) {
            return;
        }

        start();
    }

    /**
     * 关闭
     *
     * @throws InterruptedException
     */
    public void close() throws InterruptedException {
        if (channel != null){
            channel.close();
        }
    }
}
