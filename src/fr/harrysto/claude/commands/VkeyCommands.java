package fr.harrysto.claude.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class VkeyCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(label.equalsIgnoreCase("vkey") && sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0){
                player.sendMessage("[Valient] /vkey <name>");
                return true;
            }
            if(args.length == 1){
                String keyname = args[0];
                player.sendMessage("[Valient] Votre clé " + keyname + " a été générée !");
                ItemStack vkey = new ItemStack(Material.TRIPWIRE_HOOK, 1);
                ItemMeta vk = vkey.getItemMeta();
                vk.setDisplayName("§4Key");
                vk.setLore(Arrays.asList("Clé " + keyname));
                vkey.setItemMeta(vk);
                player.getInventory().setItemInOffHand(vkey);
                player.updateInventory();
                return true;
            }

        }
        return true;
    }
}
