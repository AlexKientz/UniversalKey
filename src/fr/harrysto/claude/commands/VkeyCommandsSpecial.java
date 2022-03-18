package fr.harrysto.claude.commands;

import fr.harrysto.claude.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class VkeyCommandsSpecial implements CommandExecutor {


    public static int idDefine;

    public Main plugin;

    public VkeyCommandsSpecial(Main instance) {
        plugin = instance;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("VB_VALIENTKEYSPECIAL") && sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0){
                player.sendMessage(plugin.getConfig().getString("message.assist-commands-vkey"));
                return true;
            }
            if(args.length == 1){
                // Generate ID

                // Generate KEY
                System.out.println("non");

                player.sendMessage(plugin.getConfig().getString("message.creation-key-no-id"));

                ItemStack vkey = new ItemStack(Material.valueOf("VB_VALIENTKEYSPECIAL"),1);


                ItemMeta vk = vkey.getItemMeta();

                vk.setDisplayName("§cClé spécial non définie");

                vk.setLore(Arrays.asList("Clé non définie !"));

                vkey.setItemMeta(vk);

                player.getInventory().setItemInOffHand(vkey);

                player.updateInventory();

                return true;
            }

        }
        return true;
    }
}
