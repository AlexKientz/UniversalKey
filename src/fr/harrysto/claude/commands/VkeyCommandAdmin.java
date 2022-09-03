package fr.harrysto.claude.commands;

import fr.harrysto.claude.Main;
import javafx.print.PageLayout;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.Arrays;


public class VkeyCommandAdmin implements CommandExecutor {

    public Main plugin;
    public static int checkid = 0;
    public static int bypass = 0;
    public static String id = "";

    public VkeyCommandAdmin(Main instance) {
        plugin = instance;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("vkeyadmin")) {
                if (args.length == 0) {
                    player.sendMessage("§cArgument invalide ! Commande disponible :");
                    player.sendMessage("§cbypass : true/false");
                }
                if (args[0].equalsIgnoreCase("bypass")) {
                    if (args[1].equalsIgnoreCase("true")) {
                        player.sendMessage(plugin.getConfig().getString("message.admin-on"));
                        VkeyCommandAdmin.bypass = 1;

                    }
                    if (args[1].equalsIgnoreCase("false")) {
                        player.sendMessage(plugin.getConfig().getString("message.admin-off"));
                        VkeyCommandAdmin.bypass = 0;
                    }
                }

                if(args[0].equalsIgnoreCase("checkid")){
                    player.sendMessage("[ValientKey] CheckID on/off");
                    if(args[1].equalsIgnoreCase("on")){
                        checkid = 1;
                        player.sendMessage(plugin.getConfig().getString("message.admin-on"));
                    }
                    if(args[1].equalsIgnoreCase("off")){
                        checkid = 0;
                        player.sendMessage(plugin.getConfig().getString("message.admin-off"));
                    }

                }

                if(args[0].equalsIgnoreCase("open")){
                    if(args[1] == null) {
                        player.sendMessage("§c[ValientKey] Il faut spécifier un id.");
                    }
                    if(args[1] != null){
                        id = args[1];
                        final File file = new File(plugin.getDataFolder(), "data.yml");
                        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                        final String key = "zone." + "§c" + id;
                        final ConfigurationSection configurationSection = configuration.getConfigurationSection(key);

                        if(configurationSection != null){
                            Inventory ClaimManager = Bukkit.createInventory(null, 27, id);

                            // ItemStack
                            ItemStack barrier = new ItemStack(Material.BARRIER);
                            ItemMeta leavemenu = barrier.getItemMeta();
                            leavemenu.setDisplayName("§cQuittez le menu");
                            barrier.setItemMeta(leavemenu);

                            ItemStack goldBlock = new ItemStack(Material.GOLD_BLOCK);
                            ItemMeta addPlayer = goldBlock.getItemMeta();
                            addPlayer.setDisplayName("§cFaire un double des clés");
                            addPlayer.setLore(Arrays.asList("Ticket permettant de créer un double des clés", "§eFonction désactivé pour le menu admin"));
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

                            player.openInventory(ClaimManager);

                        } else {
                            player.sendMessage("§c[ValientKey] Cette id n'existe pas.");
                        }
                    }
                }



            }
            return true;
        }
}
