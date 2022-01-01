package fr.harrysto.claude.listener;

import fr.harrysto.claude.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.security.Key;
import java.util.Arrays;

public class TransformKey implements Listener {

    public Main plugin;
    public TransformKey(Main instance) {plugin = instance;}

    String cle = null;
    String KeyID = null;

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInHand();
        ItemStack it = event.getItem();
        player.updateInventory();

        if(it !=null){
            cle = item.getItemMeta().getDisplayName();
        }

        if(item.getType() == Material.valueOf("VALIENT_KEY_ITEM")){
            KeyID = item.getItemMeta().getDisplayName();
        }

        if (item.getType() == Material.NAME_TAG && cle != null) {
            if(player.hasPermission("forgeron.valient.use")) {
                player.sendMessage("§e[ValientKey] Prend désormais une clé vierge et fait clic droit dans l'air.");
                player.getItemInHand().setAmount(0);
            } else {
                player.sendMessage(plugin.getConfig().getString("message.no-forgeron-permission"));
            }
        }


        if(item.getType() == Material.valueOf("VALIENT_KEY_CREATE") && event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (player.hasPermission("forgeron.valient.use")) {
                ItemStack vkey = new ItemStack(Material.valueOf("VALIENT_KEY_ITEM"), 1);
                ItemMeta vk = vkey.getItemMeta();
                vk.setDisplayName(KeyID);
                vk.setLore(Arrays.asList("Clé de " + event.getPlayer().getPlayerListName()));
                vkey.setItemMeta(vk);
                player.getInventory().setItemInMainHand(vkey);
                player.updateInventory();
            } else{
                player.sendMessage(plugin.getConfig().getString("message.no-forgeron-permission"));
            }
        }
    }
}
