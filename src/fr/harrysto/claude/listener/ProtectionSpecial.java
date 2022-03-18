package fr.harrysto.claude.listener;

import fr.harrysto.claude.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ProtectionSpecial implements Listener {

    public Main plugin;

    public ProtectionSpecial(Main instance) {
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

            if (item.getType() == Material.valueOf("VB_VALIENTKEYSPECIAL") && event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && item.getItemMeta().getDisplayName().equalsIgnoreCase("§cClé spécial non définie")) {
                if (configurationSection == null && item.getType() == Material.valueOf("VB_VALIENTKEYSPECIAL")) {
                    // Give key + id
                    player.getItemInHand().setAmount(0);

                    ItemStack vkey = new ItemStack(Material.valueOf("VB_VALIENTKEYSPECIAL"), 1);
                    ItemMeta vk = vkey.getItemMeta();
                    vk.setDisplayName("§c" + NewID);
                    vk.setLore(Arrays.asList("Clé on ne sais pas encore"));
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
    }
}
