package pl.publicprojects.pcommon.protocol.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import pl.publicprojects.pcommon.protocol.PanelBuffer;
import pl.publicprojects.pcommon.protocol.connection.AbstractConnection;
import pl.publicprojects.pcommon.protocol.packet.PacketUtil;
import pl.publicprojects.pcommon.protocol.packet.Packet;

public class PacketDecoder extends SimpleChannelInboundHandler<PanelBuffer> {

    private final AbstractConnection abstractConnection;
    private final PacketUtil packetUtil;

    public PacketDecoder(PacketUtil packetUtil, AbstractConnection connection) {
        this.packetUtil = packetUtil;
        this.abstractConnection = connection;
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        abstractConnection.loginConnection(channelHandlerContext);
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PanelBuffer panelBuffer) throws Exception {
        int packetId = panelBuffer.byteBuf().readInt();

        if(packetUtil.getPacketById(packetId) == null) {
            channelHandlerContext.disconnect();
            return;
        }

        Packet packet = packetUtil.getPacketById(packetId);
        packet.read(panelBuffer);
        this.abstractConnection.handle(packet);
    }
}
