package fr.harrysto.claude;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.harrysto.claude.commands.VkeyCommands;
import fr.harrysto.claude.commands.keylist;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class main extends JavaPlugin implements Listener {

    public WorldGuardPlugin worldGuardPlugin;

    public void onEnable() {

        worldGuardPlugin = getWorldGuard();

        getCommand("vkey").setExecutor(new VkeyCommands());
        getCommand("vkeylist").setExecutor(new keylist());

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);

        super.onEnable();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.updateInventory();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        saveDefaultConfig();
        File datafile = new File("plugin/Claude/datafile.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(datafile);
        Player player = event.getPlayer();

        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "ClaudePlugin Started [Valient@2021]");

        Block b = event.getClickedBlock();
        Action action = event.getAction();
        ItemStack it = event.getItem();

        if(it != null && it.getType() == Material.NETHER_STAR){
            Inventory trousseau = Bukkit.createInventory(player, 18, "§4Trousseau de clé de " + player.getName());
            player.openInventory(trousseau);

        }

        if(it != null && it.getType() == Material.TRIPWIRE_HOOK){
            player.sendMessage("[Valient] Il faut cliquer sur une porte");
            player.sendMessage("You click on this block : " + b.getType().toString());

            }
        }

    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = this.getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }

}


