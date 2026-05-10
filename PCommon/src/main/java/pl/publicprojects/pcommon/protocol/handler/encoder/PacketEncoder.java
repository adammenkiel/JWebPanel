package pl.publicprojects.pcommon.protocol.handler.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import pl.publicprojects.pcommon.protocol.PanelBuffer;
import pl.publicprojects.pcommon.protocol.packet.Packet;


/**
 * That class writes packet id as 4 bytes and data of packet in <code>Packet#write</code> method.
 * Later <code>ByteBuf byteBuf</code> arrives into <code>SizeEncoder</code>
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(packet.getId());
        packet.write(new PanelBuffer(byteBuf));
    }
}
