package fr.harrysto.claude.listener;

import fr.harrysto.claude.Main;
import fr.harrysto.claude.commands.VkeyCommandAdmin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.SplashPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ClaimMenuListener implements Listener {

    public Main plugin;

    public ClaimMenuListener(Main instance) {
        plugin = instance;
    }

    public static String cle = null;

    @EventHandler
    public void onInteract(PlayerInteractEvent event){

        Player player = event.getPlayer();

        // Check ID item
        ItemStack item = player.getInventory().getItemInHand();
        ItemStack it = event.getItem();
        player.updateInventory();

        if(it != null){
            cle = it.getItemMeta().getDisplayName();
        }

        final File file = new File(plugin.getDataFolder(), "data.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        final String key = "zone." + cle;
        final ConfigurationSection configurationSection = configuration.getConfigurationSection(key);
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(item.getType() == Material.valueOf("VB_VALIENTKEYS") && event.getAction().equals(Action.RIGHT_CLICK_AIR) && item.getItemMeta().getDisplayName().equalsIgnoreCase(cle)) {
            Inventory ClaimManager = Bukkit.createInventory(null, 27, cle);

            // ItemStack
            ItemStack barrier = new ItemStack(Material.BARRIER);
            ItemMeta leavemenu = barrier.getItemMeta();
            leavemenu.setDisplayName("§cQuittez le menu");
            barrier.setItemMeta(leavemenu);

            ItemStack goldBlock = new ItemStack(Material.GOLD_BLOCK);
            ItemMeta addPlayer = goldBlock.getItemMeta();
            addPlayer.setDisplayName("§cFaire un double des clés");
            addPlayer.setLore(Arrays.asList("Ticket permettant de créer un double des clés"));
            goldBlock.setItemMeta(addPlayer);

            ItemStack redstoneBlock = new ItemStack(Material.REDSTONE_BLOCK);
            ItemMeta removeClaim = redstoneBlock.getItemMeta();
            removeClaim.setDisplayName("§cSupprimez votre claim");
            removeClaim.setLore(Arrays.asList("§cAttention cette action et irrévocable !", "§cIl n'y aura pas de message de confirmation !"));
            redstoneBlock.setItemMeta(removeClaim);

            ItemStack book = new ItemStack(Material.BOOK);
            ItemMeta info = book.getItemMeta();
            info.setDisplayName("§eInformations");
            info.setLore(Arrays.asList("Joueur: " + configurationSection.getString("player"), "Coordonnées:", "x: " + configurationSection.getString("x"), "y: " + configurationSection.getString("y"), "z: " + configurationSection.getString("z")));
            book.setItemMeta(info);

            // add item

            ClaimManager.setItem(26, barrier);
            ClaimManager.setItem(10, goldBlock);
            ClaimManager.setItem(16, book);
            ClaimManager.setItem(13, redstoneBlock);

            if(configurationSection.getString("player").equals(player.getDisplayName())) {
                player.openInventory(ClaimManager);
            } else {
                player.sendMessage(plugin.getConfig().getString("message.no-correct-player"));
            }
        }


    }

    @EventHandler
    public void onClick(InventoryClickEvent event){

        Inventory ClaimManager = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();
        if(current == null) return;

        if(ClaimManager.getName().equalsIgnoreCase(cle) || ClaimManager.getName().equalsIgnoreCase(VkeyCommandAdmin.id)){

            // Leave menu
            if(current.getType() == Material.BARRIER){
                player.closeInventory();
            }

            // Add Player
            if(current.getType() == Material.GOLD_BLOCK){
                player.closeInventory();

                ItemStack NameTag = new ItemStack(Material.NAME_TAG);
                ItemMeta newKey = NameTag.getItemMeta();
                newKey.setDisplayName("Clé non utilisable");
                newKey.setLore(Arrays.asList(cle));
                NameTag.setItemMeta(newKey);
                player.getInventory().addItem(NameTag);
                player.sendMessage("§e[ValientKey] Une deuxième clé a été générée allez la faire construire chez un forgeron");
            }

            if(current.getType() == Material.BOOK){
                player.closeInventory();
            }

            if(current.getType() == Material.REDSTONE_BLOCK){
                final File file = new File(plugin.getDataFolder(), "data.yml");
                final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                final String key = "zone." + "§c" + cle;
                final ConfigurationSection configurationSection = configuration.getConfigurationSection(key);

                configuration.set(cle, null);
                player.closeInventory();
                player.sendMessage("§e[ValientKey] Claim supprimé.");
            }
        }

    }


}
