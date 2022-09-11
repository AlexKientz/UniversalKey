package fr.harrysto.claude;

import fr.harrysto.claude.commands.VkeyCommandAdmin;
import fr.harrysto.claude.commands.VkeyCommands;
import fr.harrysto.claude.commands.VkeyCommandsSpecial;
import fr.harrysto.claude.commands.createkey;
import fr.harrysto.claude.listener.*;
import fr.harrysto.claude.listener.Key;

import fr.harrysto.claude.update.Updater;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;


public class Main extends JavaPlugin implements Listener {

    public static Main main;

    public final Logger logger = Logger.getLogger("Minecraft");


    String version = "0.1";

    public void onEnable() {
        // Config
        saveDefaultConfig();
        File configFile = new File("plugins/Claude/config.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);

        // Listener
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new Key(this), this);

        getCommand("key").setExecutor(new createkey(this));

        // Message
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println("[UniversalKey] Plugin loaded version: " + version);

        Updater updater = new Updater(this, 674685, getFile(), Updater.UpdateType.DEFAULT, true);


    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " Has Been Disable");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.updateInventory();
        VkeyCommandAdmin.bypass = 0;
    }

}



