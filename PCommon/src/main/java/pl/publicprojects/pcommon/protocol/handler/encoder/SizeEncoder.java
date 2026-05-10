package pl.publicprojects.pcommon.protocol.handler.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import pl.publicprojects.pcommon.protocol.PanelBuffer;
import pl.publicprojects.pcommon.protocol.packet.Packet;

/**
* Packet id + data decoded by <code>PacketEncoder</code> to byte table arrives here
* This class check length of this byte table, send this length and send that byte table
* Later endpoint decoding these bytes[] and changes it into Packet classes
*/
public class SizeEncoder extends MessageToByteEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf packet, ByteBuf byteBuf) throws Exception {
        int length = packet.readableBytes();
        byteBuf.writeInt(length);
        byteBuf.writeBytes(packet, packet.readerIndex(), length);
    }
}