package com.wq.netty.client;

import com.wq.netty.constains.Constants;
import com.wq.netty.protocol.RequestProto;
import com.wq.netty.protocol.ResponseProto;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author wq
 */
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<ResponseProto.ResProtocol> {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);


    private ThreadPoolExecutor threadPoolExecutor ;

    private ScheduledExecutorService scheduledExecutorService ;

    private final static RequestProto.ReqProtocol pingRequest = RequestProto.ReqProtocol.newBuilder()
            .setRequestId(0L)
            .setReqMsg("ping")
            .setType(Constants.CommandType.PING)
            .build();

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt ;

            if (idleStateEvent.state() == IdleState.WRITER_IDLE){

                ctx.writeAndFlush(pingRequest).addListeners((ChannelFutureListener) future -> {
                    if (!future.isSuccess()) {
                        LOGGER.error("IO error,close Channel");
                        future.channel().close();
                    }
                }) ;
            }

            //TODO  改为可配置 用户多久未操作释放连接时间
            //用户长时间不用断开连接

        }

        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //客户端和服务端建立连接时调用
        LOGGER.info("cim server connect success!");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        //todo

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseProto.ResProtocol msg) throws Exception {

        //心跳更新时间
        if (msg.getType() == Constants.CommandType.PING){
            //LOGGER.info("收到服务端心跳！！！");
            ctx.channel().attr(AttributeKey.valueOf("readerTime")).set(System.currentTimeMillis());
        }

        if (msg.getType() != Constants.CommandType.PING) {
            //todo 回调 处理消息

        }





    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //异常时断开连接
        cause.printStackTrace() ;
        ctx.close() ;
    }
}
