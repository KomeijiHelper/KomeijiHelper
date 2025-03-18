package komeiji.modules.websocket.channel.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import komeiji.modules.websocket.channel.events.MessageForwardEvent;
import komeiji.modules.websocket.message.impl.TextMessage;

public class TextMessageHandler extends SimpleChannelInboundHandler<TextMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextMessage msg) {

        System.out.println(msg);
        ctx.fireUserEventTriggered(new MessageForwardEvent(msg));

//        System.out.println("服务器收到消息 " + msg.text());
//
//        for(String id: Utils.getSessionManager().getSessions().keySet()) {
//            Utils.getSessionManager().findSession(id).getConnect()
//                    .writeAndFlush(new TextWebSocketFrame("服务器时间" + LocalDateTime.now() +  " " + msg.text()));
//            ctx.fireUserEventTriggered(new MessageForwardEvent());
//        }
    }

    //当web客户端连接后， 触发方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //id 表示唯一的值，LongText 是唯一的 ShortText 不是唯一
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asLongText());
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asShortText());
    }



    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        System.out.println("handlerRemoved 被调用" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生 " + cause.getMessage());
        ctx.close(); //关闭连接
    }
}