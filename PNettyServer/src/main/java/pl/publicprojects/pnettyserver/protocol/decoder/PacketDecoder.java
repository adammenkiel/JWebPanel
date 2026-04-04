package pl.publicprojects.pnettyserver.protocol.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import pl.publicprojects.pnettyserver.protocol.PanelBuffer;
import pl.publicprojects.pnettyserver.protocol.packet.PacketUtil;
import pl.publicprojects.pnettyserver.protocol.packet.Packet;

public class PacketDecoder extends SimpleChannelInboundHandler<PanelBuffer> {

    private PacketUtil packetUtil;

    public PacketDecoder(PacketUtil packetUtil) {
        this.packetUtil = packetUtil;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PanelBuffer panelBuffer) throws Exception {
        int packetId = panelBuffer.byteBuf().readInt();
        Packet packet = packetUtil.getPacketById(packetId);
        packet.read(panelBuffer);
    }
}
