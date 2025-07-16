package bronya.admin.ws.server.netty;

import io.netty.handler.codec.http.FullHttpRequest;
import org.dromara.hutool.json.JSONObject;
import org.dromara.hutool.json.JSONUtil;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 一旦连接，第一个被执行
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("有新的客户端链接：[{}]", ctx.channel().id().asLongText());
        // 添加到channelGroup 通道组
        NettyConfig.getChannelGroup().add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (obj instanceof TextWebSocketFrame textWebSocketFrame) {
            String text = textWebSocketFrame.text();
            log.info("服务器收到消息：{}", text);
            // 获取用户ID,关联channel
            // JSONObject jsonObject = JSONUtil.parseObj(obj);
            // String uid = jsonObject.getStr("uid");
            // NettyConfig.getChannelMap().put(uid, ctx.channel());
            ////
            // // 将用户ID作为自定义属性加入到channel中，方便随时channel中获取用户ID
            // AttributeKey<String> key = AttributeKey.valueOf("userId");
            // ctx.channel().attr(key).setIfAbsent(uid);

            // 回复消息
            ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器收到消息啦"));
            return;
        }
        throw new RuntimeException("读取消息异常,非TextWebSocketFrame类型!");
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("用户下线了:{}", ctx.channel().id().asLongText());
        // 删除通道
        NettyConfig.getChannelGroup().remove(ctx.channel());
        removeUserId(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("异常：{}", cause.getMessage());
        // 删除通道
        NettyConfig.getChannelGroup().remove(ctx.channel());
        removeUserId(ctx);
        ctx.close();
    }

    /**
     * 删除用户与channel的对应关系
     */
    private void removeUserId(ChannelHandlerContext ctx) {
        AttributeKey<String> key = AttributeKey.valueOf("userId");
        String userId = ctx.channel().attr(key).get();
        NettyConfig.getChannelMap().remove(userId);
    }
}
