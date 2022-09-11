package fr.harrysto.claude.listener;

import fr.harrysto.claude.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.SplashPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Key implements Listener {
    public Main plugin;

    public Key(Main instance) {
        plugin = instance;
    }

    @EventHandler
    public void InteractKey(PlayerInteractEvent event){

        ItemStack itemStack = null;

        if(event.getPlayer().getInventory().getItemInHand() != null){
            itemStack = event.getPlayer().getInventory().getItemInHand();
        }

        Location location = event.getClickedBlock().getLocation();
        String LocationString = location.getX() + "a" + location.getZ()+"a"+location.getZ();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            final File file = new File(plugin.getDataFolder(), "data.yml");
            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            final ConfigurationSection configurationSection = configuration.getConfigurationSection(LocationString);
            try {
                configuration.save(file);
            } catch (IOException e){
                e.printStackTrace();
            }

            if(configurationSection == null && itemStack.getType() == Material.valueOf(plugin.getConfig().getString("item.key"))) {
                event.getPlayer().sendMessage(plugin.getConfig().getString("message.claim.valide-claim"));
                configuration.set(LocationString + ".player", event.getPlayer().getDisplayName());
                configuration.set(LocationString + ".key", itemStack.getItemMeta().getDisplayName());
                configuration.set(LocationString + ".loc", location);
                event.setCancelled(true);
                try {
                    configuration.save(file);
                } catch (IOException e){
                    e.printStackTrace();
                }
            } else if (configurationSection != null && itemStack.getType() == Material.valueOf(plugin.getConfig().getString("item.key"))) {
                String key = configurationSection.getString("key");
                if (key.equals(itemStack.getItemMeta().getDisplayName())){
                    event.setCancelled(false);
                } else {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(plugin.getConfig().getString("message.claim.error-claim"));
                }
            } else {
                if(configurationSection != null){
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(plugin.getConfig().getString("message.error-claim"));
                    System.out.println(configurationSection.getString("player"));
                }
            }
        }
    }
}
