package fr.harrysto.claude.listener;

import fr.harrysto.claude.Main;
import fr.harrysto.claude.commands.VkeyCommandAdmin;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class ProtectionListener implements Listener {

    public Main plugin;

    public ProtectionListener(Main instance) {
        plugin = instance;
    }

    String loc = "";
    int coordinates;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Check ID item
        ItemStack item = player.getInventory().getItemInHand();
        ItemStack it = event.getItem();
        player.updateInventory();


        String cle = "";

        if (it != null) {
            cle = item.getItemMeta().getDisplayName();
        }

        if(event.getClickedBlock() != null) {
            coordinates = event.getClickedBlock().getX() + event.getClickedBlock().getY() + event.getClickedBlock().getZ();
        }

        if(event.getClickedBlock() != null) {
            loc = event.getClickedBlock().getLocation().toString();
        }

        // File CONFIG
        final File file = new File(plugin.getDataFolder(), "data.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        final String key = "zone." + "§c" + coordinates;
        final ConfigurationSection configurationSection = configuration.getConfigurationSection(key);
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String loc2 = null;
        String loc1 = loc;
        if(configurationSection != null) {
            loc2 = configurationSection.getString("loc");
        }

        String key3 = null;
        if(configurationSection != null) {
            key3 = configurationSection.getString("id");
        }
        String key2 = cle;

        if (item.getType() == Material.BONE && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(configurationSection == null && item.getType() == Material.BONE) {
                // Give key + id
                player.getItemInHand().setAmount(0);

                ItemStack vkey = new ItemStack(Material.BLAZE_ROD, 1);
                ItemMeta vk = vkey.getItemMeta();
                vk.setDisplayName("§c" + coordinates);
                vk.setLore(Arrays.asList("Clé de " + event.getPlayer().getPlayerListName()));
                vkey.setItemMeta(vk);
                player.getInventory().setItemInMainHand(vkey);
                player.updateInventory();
                // Add in Configuration File
                configuration.set(key + ".loc", event.getClickedBlock().getLocation().toString());
                configuration.set(key + ".id", "§c" + coordinates);
                configuration.set(key + ".player", player.getDisplayName());
                configuration.set(key + ".x", event.getClickedBlock().getX());
                configuration.set(key + ".y", event.getClickedBlock().getY());
                configuration.set(key + ".z", event.getClickedBlock().getZ());

                event.setCancelled(true);
                try {
                    configuration.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                    player.sendMessage(plugin.getConfig().getString("message.error-claim"));
                }
        }

        if(VkeyCommandAdmin.bypass !=1) {
            if (item.getType() == Material.BLAZE_ROD && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (loc1.equals(loc2) && key3.equals(key2)) {
                    player.sendMessage(plugin.getConfig().getString("message.valide-key"));
                } else {
                    player.sendMessage(plugin.getConfig().getString("message.no-valide-key"));
                    event.setCancelled(true);
                }

            }
        }

        // Bloquez
        if(VkeyCommandAdmin.bypass !=1) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && item.getType() != Material.BLAZE_ROD) {
                if (loc1.equals(loc2)) {
                    event.setCancelled(true);
                }

            }
        }

        if(VkeyCommandAdmin.bypass !=1) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && item.getType() != Material.BLAZE_ROD) {
                if (loc1.equals(loc2)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        player.updateInventory();
        String cle = "";

        // File
        final File file = new File(plugin.getDataFolder(), "data.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        final String key = "zone." + coordinates;
        final ConfigurationSection configurationSection = configuration.getConfigurationSection(key);

        // Variable Location

        String loc1 = loc;
        String loc2 = "";
        if(configurationSection != null) {
            loc2 = configurationSection.getString("loc");
        }
        // Loc 3
        if(VkeyCommandAdmin.bypass !=1) {
            if (loc1.equals(loc2)) {
                player.sendMessage(plugin.getConfig().getString("message.no-valide-key"));
                event.setCancelled(true);
            }
        }

    }
}