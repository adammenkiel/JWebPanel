package pl.publicprojects.pnettyserver.protocol.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import pl.publicprojects.pnettyserver.protocol.PanelBuffer;
import pl.publicprojects.pnettyserver.protocol.packet.PacketUtil;

import java.util.List;

public class SizeDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes() < 4) {
            return;
        }
        byteBuf.markReaderIndex();

        int len = byteBuf.readInt();

        if(len > byteBuf.readableBytes()) {
            byteBuf.resetReaderIndex();
            return;
        }
        list.add(new PanelBuffer(byteBuf.readBytes(len)));

    }
}
