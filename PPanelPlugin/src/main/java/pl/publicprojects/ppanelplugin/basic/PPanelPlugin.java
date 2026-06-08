package pl.publicprojects.ppanelplugin.basic;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.publicprojects.ppanelplugin.handler.SimpleSessionHandler;
import pl.publicprojects.pcommon.app.helper.ChatQueue;
import pl.publicprojects.ppanelplugin.listeners.ChatListener;
import pl.publicprojects.pnettyserver.basic.NettyServer;

@Getter
public class PPanelPlugin extends JavaPlugin {

    private NettyServer nettyServer;
    private ChatQueue chatQueue;

    @Override
    public void onEnable() {
        this.getLogger().info("PPanelPlugin is loading....");
        this.chatQueue = new ChatQueue();
        this.nettyServer = new NettyServer(this.getLogger(), 9876);
        this.nettyServer.registerHandler(new SimpleSessionHandler(this));

        Bukkit.getScheduler().runTaskAsynchronously(
                this,
                () -> nettyServer.start()
        );

        this.getServer().getPluginManager().registerEvents(
                new ChatListener(this),
                this
        );
        this.getLogger().info("Loaded!");
    }
}
