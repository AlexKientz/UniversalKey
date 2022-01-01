package fr.harrysto.claude.listener;

import fr.harrysto.claude.Main;
import fr.harrysto.claude.commands.VkeyCommandAdmin;
import fr.harrysto.claude.lang.shortcut;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;
import java.util.Locale;

public class CheckID implements Listener {

    public Main plugin;

    public CheckID(Main instance) {
        plugin = instance;
    }

    String coordinates = "";

    @EventHandler
    public void onInteract(PlayerInteractEvent event){

        if(VkeyCommandAdmin.checkid == 1){
            if(event.getClickedBlock() != null) {
                coordinates = event.getClickedBlock().getX() + "," + event.getClickedBlock().getY() + "," + event.getClickedBlock().getZ();
            }

            Player player = event.getPlayer();

            player.sendMessage(shortcut.PreMSG + "Id du claim : " + coordinates);

            final File file = new File(plugin.getDataFolder(), "data.yml");


            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            final String key = "zone." + "§c" + coordinates;
            final ConfigurationSection configurationSection = configuration.getConfigurationSection(key);

            Location loc = event.getClickedBlock().getLocation();

            player.sendMessage(shortcut.PreMSG + "Propriétaire du claim : " + configurationSection.getString("player"));
            player.sendMessage(shortcut.PreMSG + "Coordonées du claim :" + loc.getX() + " " + loc.getY() + " " + loc.getZ());

            event.setCancelled(true);
        }
    }
}
