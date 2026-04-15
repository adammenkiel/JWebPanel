package pl.publicprojects.pcommon.protocol.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import pl.publicprojects.pcommon.protocol.PanelBuffer;
import pl.publicprojects.pcommon.protocol.packet.Packet;

public class SizeEncoder extends MessageToByteEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf packet, ByteBuf byteBuf) throws Exception {
        int length = packet.readableBytes();
        byteBuf.writeInt(length);
        byteBuf.writeBytes(packet, packet.readerIndex(), length);
    }
}