package pl.publicprojects.pcommon.protocol.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import pl.publicprojects.pcommon.protocol.connection.AbstractConnection;

public class ExceptionHandler extends ChannelDuplexHandler {

    private final AbstractConnection abstractConnection;

    public ExceptionHandler(AbstractConnection abstractConnection) {
        this.abstractConnection = abstractConnection;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        abstractConnection.disconnectWithCause(cause);
    }
}
