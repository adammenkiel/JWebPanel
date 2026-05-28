package pl.publicprojects.pcommon.protocol.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import pl.publicprojects.pcommon.protocol.connection.AbstractConnection;

public class ExceptionHandler extends ChannelDuplexHandler {

    private final AbstractConnection abstractConnection;

    public ExceptionHandler(AbstractConnection abstractConnection) {
        this.abstractConnection = abstractConnection;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        this.abstractConnection.disconnectWithCause(cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if(evt instanceof IdleStateEvent) {
            this.abstractConnection.disconnectWithReason("Timeout!");
        }
    }
}
