package pl.publicprojects.panelplugin.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncChatEvent event) {
        String playerName = event.getPlayer().getName();
        String message = event.message().toString();
    }
}
