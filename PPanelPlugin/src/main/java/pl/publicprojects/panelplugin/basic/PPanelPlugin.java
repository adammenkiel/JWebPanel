package pl.publicprojects.panelplugin.basic;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.publicprojects.panelplugin.handler.SimpleSessionHandler;
import pl.publicprojects.panelplugin.helper.ChatQueue;
import pl.publicprojects.panelplugin.listeners.ChatListener;
import pl.publicprojects.pnettyserver.basic.NettyServer;

@Getter
public class PPanelPlugin extends JavaPlugin {

    private NettyServer nettyServer;
    private ChatQueue chatQueue;

    @Override
    public void onEnable() {
        this.chatQueue = new ChatQueue();
        this.nettyServer = new NettyServer(9876);
        this.nettyServer.registerHandler(new SimpleSessionHandler(this));

        Bukkit.getScheduler().runTaskAsynchronously(
                this,
                () -> nettyServer.start()
        );

        this.getServer().getPluginManager().registerEvents(
                new ChatListener(this),
                this
        );
    }
}
