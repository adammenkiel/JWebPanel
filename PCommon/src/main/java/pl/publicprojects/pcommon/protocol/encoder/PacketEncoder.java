package pl.publicprojects.pcommon.protocol.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import pl.publicprojects.pcommon.protocol.PanelBuffer;
import pl.publicprojects.pcommon.protocol.packet.Packet;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(packet.getId());
        packet.write(new PanelBuffer(byteBuf));
    }
}
