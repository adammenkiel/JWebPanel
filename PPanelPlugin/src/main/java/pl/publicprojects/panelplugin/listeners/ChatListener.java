package pl.publicprojects.panelplugin.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.publicprojects.panelplugin.basic.PPanelPlugin;
import pl.publicprojects.pcommon.protocol.helper.ChatQueue;
import pl.publicprojects.pcommon.protocol.packet.packets.clientbound.MessagePacket;
import pl.publicprojects.pnettyserver.session.Session;

import java.util.List;

public class ChatListener implements Listener {

    private final PPanelPlugin plugin;
    private final List<Session> sessionList;
    private final ChatQueue chatQueue;

    public ChatListener(PPanelPlugin plugin) {
        this.plugin = plugin;
        this.sessionList = Session.getSessionList();
        this.chatQueue = this.plugin.getChatQueue();
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        Bukkit.getScheduler().runTask(this.plugin, () -> {
            String playerName = event.getPlayer().getName();
            String message = event.message().toString();
            String outputMessage = playerName + ": " + message;

            this.chatQueue.add(outputMessage);

            for(Session session : this.sessionList)
                session.sendPacket(new MessagePacket(outputMessage));
        });
    }
}
