package bronya.admin.ws.server.netty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * webSocket协议名
     */
    static final String WEBSOCKET_PROTOCOL = "WebSocket";

    /**
     * webSocket路径
     */
    @Value("${webSocket.netty.path:/webSocket}")
    String webSocketPath;
    private final WebSocketHandler webSocketHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 设置管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 流水线管理通道中的处理程序（Handler），用来处理业务
        // webSocket协议本身是基于http协议的，所以这边也要使用http编解码器
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ObjectEncoder());
        // 以块的方式来写的处理器
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(8192));
        pipeline.addLast(new WebSocketServerProtocolHandler(webSocketPath, WEBSOCKET_PROTOCOL, true, 65536 * 10));
        // 自定义的handler，处理业务逻辑
        pipeline.addLast(webSocketHandler);
    }
}
