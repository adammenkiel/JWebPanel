package pl.publicprojects.pcommon.protocol.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import pl.publicprojects.pcommon.protocol.PanelBuffer;
import pl.publicprojects.pcommon.protocol.packet.Packet;

public class SizeEncoder extends MessageToByteEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf packet, ByteBuf byteBuf) throws Exception {
        byte[] bytes = new byte[packet.readableBytes()];
        packet.readBytes(bytes);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}