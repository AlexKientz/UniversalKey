package fr.harrysto.claude;

import fr.harrysto.claude.commands.VkeyCommands;
import fr.harrysto.claude.listener.PluginListener;
import io.netty.handler.codec.http2.Http2RemoteFlowController;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {

    @Override
    public void onEnable() {

        getCommand("vkey").setExecutor(new VkeyCommands());

        Listener l = new PluginListener();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(l, this);

        super.onEnable();
    }
}
