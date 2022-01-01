package fr.harrysto.claude.listener;

import fr.harrysto.claude.Main;
import fr.harrysto.claude.lang.shortcut;
import net.md_5.bungee.api.chat.ClickEvent;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

// p.openInventory(((Player) clicked).getInventory());

public class keythief implements Listener {

    public Main plugin;

    public keythief(Main instance) {
        plugin = instance;
    }

    int clickevent = 0;

    @EventHandler
    public void thiefInteract(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        Entity clicked = event.getRightClicked();
        Player clickedPlayer = (Player) clicked;
        if (event.getRightClicked().getType().equals(EntityType.PLAYER) && p.getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§eVoleur de clé")) {
            ItemStack item = p.getItemInHand();
            item.setAmount(0);
            p.openInventory(((Player) clicked).getInventory());
            clickevent = 1;

        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        ItemStack current = event.getCurrentItem();
        if(current == null) return;

        if(clickevent == 1){
            if(current.getType() != Material.BLAZE_ROD) {
                player.closeInventory();
                player.sendMessage("§c" + shortcut.PreMSG + "Vous ne pouvez pas voler cette item.");
            }
            if(current.getType() == Material.CHORUS_FRUIT_POPPED){
                player.closeInventory();
                player.sendMessage("§c" + shortcut.PreMSG + "Vous ne pouvez pas voler les clés spéciales");
            }
        }
    }

    @EventHandler
    public void CloseInventory(InventoryCloseEvent event){
        clickevent = 0;
    }


}
