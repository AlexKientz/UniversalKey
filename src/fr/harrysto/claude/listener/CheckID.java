package fr.harrysto.claude.listener;

import fr.harrysto.claude.Main;
import fr.harrysto.claude.commands.VkeyCommandAdmin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class CheckID implements Listener {

    public Main plugin;

    public CheckID(Main instance) {
        plugin = instance;
    }

    int id = 0;

    @EventHandler
    public void onInteract(PlayerInteractEvent event){

        if(VkeyCommandAdmin.checkid == 1){
            if(event.getClickedBlock() != null) {
                id = event.getClickedBlock().getX() + event.getClickedBlock().getY() + event.getClickedBlock().getZ();
            }

            Player player = event.getPlayer();

            player.sendMessage("§e[ValientKey] L'id du bloc cliqué est " + id);
            player.sendMessage("§e[ValientKey] Vous pouvez ouvrir le menu du claim en executant la commande /vkeyadmin open " + id);

            event.setCancelled(true);

        }
    }
}
