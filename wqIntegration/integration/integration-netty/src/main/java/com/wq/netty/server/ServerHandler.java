package com.wq.netty.server;


import com.wq.netty.constains.Constants;
import com.wq.netty.protocol.RequestProto;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wq
 */
@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<RequestProto.ReqProtocol> {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);

    private final static RequestProto.ReqProtocol pongRequest = RequestProto.ReqProtocol.newBuilder()
            .setRequestId(0L)
            .setReqMsg("pong")
            .setType(Constants.CommandType.PING)
            .build();
    /**
     * 取消绑定
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //可能出现业务判断离线后再次触发 channelInactive

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {

                LOGGER.info("定时检测客户端端是否存活");
                //todo

            }
        }
        super.userEventTriggered(ctx, evt);
    }



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestProto.ReqProtocol msg) throws Exception {
        LOGGER.info("received msg=[{}]", msg.toString());

        if (msg.getType() == Constants.CommandType.LOGIN) {
            //保存客户端与 Channel 之间的关系
            //todo
            LOGGER.info("client [{}] online success!!", msg.getReqMsg());
        }

        //心跳更新时间
        if (msg.getType() == Constants.CommandType.PING){
            ctx.channel().attr(AttributeKey.valueOf("readerTime")).set(System.currentTimeMillis());
            //向客户端响应 pong 消息
            ctx.writeAndFlush(pongRequest).addListeners((ChannelFutureListener) future -> {
                if (!future.isSuccess()) {
                    LOGGER.error("IO error,close Channel");
                    future.channel().close();
                }
            }) ;
        }

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        LOGGER.error(cause.getMessage(), cause);

    }

}
