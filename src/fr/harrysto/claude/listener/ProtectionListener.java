package fr.harrysto.claude.listener;

import fr.harrysto.claude.Main;
import fr.harrysto.claude.commands.VkeyCommandAdmin;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ProtectionListener implements Listener {

    public Main plugin;

    public ProtectionListener(Main instance) {
        plugin = instance;
    }

    String loc = "";
    int coordinates;

    String NewID = "";
    String SubID = "";

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
            int Y = event.getClickedBlock().getY() - 1;
            NewID = event.getClickedBlock().getX() + "," + event.getClickedBlock().getY() + "," + event.getClickedBlock().getZ();
            SubID = event.getClickedBlock().getX() + "," + Y + "," + event.getClickedBlock().getZ();
            loc = event.getClickedBlock().getLocation().toString();
        }

        // File CONFIG
        final File file = new File(plugin.getDataFolder(), "data.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        final String key = "zone." + "§c" + NewID;
        final String SubKey = "zone." + "§c" + SubID;
        final ConfigurationSection configurationSection = configuration.getConfigurationSection(key);
        final ConfigurationSection configurationSection1 = configuration.getConfigurationSection(SubKey);
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

        if (item.getType() == Material.valueOf("VALIENT_KEY_ITEM") && event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && item.getItemMeta().getDisplayName().equalsIgnoreCase("§cClé non définie")) {
                if (configurationSection == null && item.getType() == Material.valueOf("VALIENT_KEY_ITEM")) {
                    // Give key + id
                    player.getItemInHand().setAmount(0);

                    ItemStack vkey = new ItemStack(Material.valueOf("VALIENT_KEY_ITEM"), 1);
                    ItemMeta vk = vkey.getItemMeta();
                    vk.setDisplayName("§c" + NewID);
                    vk.setLore(Arrays.asList("Clé de " + event.getPlayer().getPlayerListName()));
                    vkey.setItemMeta(vk);
                    player.getInventory().setItemInMainHand(vkey);
                    player.updateInventory();
                    // Add in Configuration File
                    configuration.set(key + ".loc", event.getClickedBlock().getLocation().toString());
                    configuration.set(key + ".id", "§c" + NewID);
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
            if (item.getType() == Material.valueOf("VALIENT_KEY_ITEM") || item.getType() == Material.valueOf("VALIENT_SPECIALKEY_ITEM") && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (loc1.equals(loc2) && key3.equals(key2)) {
                    player.sendMessage(plugin.getConfig().getString("message.valide-key"));
                } else {
                    player.sendMessage(plugin.getConfig().getString("message.no-valide-key"));
                    event.setCancelled(true);
                }

            }
        }

        if(VkeyCommandAdmin.bypass != 1){
            if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (item.getType() == Material.valueOf("VALIENT_KEY_ITEM") || item.getType() == Material.valueOf("VALIENT_SPECIALKEY_ITEM")) {
                } else {
                    if (loc1.equals(loc2)) {
                        event.setCancelled(true);
                    }
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
        final String key = "zone." + NewID;
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
