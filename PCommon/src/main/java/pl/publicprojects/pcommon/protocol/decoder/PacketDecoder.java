package pl.publicprojects.pcommon.protocol.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import pl.publicprojects.pcommon.protocol.PanelBuffer;
import pl.publicprojects.pcommon.protocol.connection.AbstractConnection;
import pl.publicprojects.pcommon.protocol.packet.PacketUtil;
import pl.publicprojects.pcommon.protocol.packet.Packet;

public class PacketDecoder extends SimpleChannelInboundHandler<PanelBuffer> {

    private final PacketUtil packetUtil;
    private final AbstractConnection abstractConnection;

    public PacketDecoder(AbstractConnection connection) {
        this.packetUtil = connection.getPacketUtil();
        this.abstractConnection = connection;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PanelBuffer panelBuffer) throws Exception {
        int packetId = panelBuffer.byteBuf().readInt();
        Packet packet = packetUtil.getPacketById(packetId);
        packet.read(panelBuffer);
        this.abstractConnection.handle(packet);
    }
}
