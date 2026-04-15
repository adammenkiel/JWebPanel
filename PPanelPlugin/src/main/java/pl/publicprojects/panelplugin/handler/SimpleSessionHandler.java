package pl.publicprojects.panelplugin.handler;

import pl.publicprojects.panelplugin.basic.PPanelPlugin;
import pl.publicprojects.pcommon.protocol.helper.ChatQueue;
import pl.publicprojects.pcommon.protocol.packet.Packet;
import pl.publicprojects.pcommon.protocol.packet.packets.clientbound.MessageGroupPacket;
import pl.publicprojects.pcommon.protocol.packet.packets.serverbound.JoinPacket;
import pl.publicprojects.pnettyserver.handler.AbstractHandler;
import pl.publicprojects.pnettyserver.session.Session;

public class SimpleSessionHandler implements AbstractHandler {

    private final PPanelPlugin plugin;
    private final ChatQueue queue;

    public SimpleSessionHandler(PPanelPlugin plugin) {
        this.plugin = plugin;
        this.queue = this.plugin.getChatQueue();
    }

    @Override
    public void handle(Session session, Packet packet) {
        if(packet instanceof JoinPacket) {
            session.sendPacket(new MessageGroupPacket(queue.getChatQueue().stream().toList()));
        }
    }
}
