package fr.harrysto.claude.commands;

import fr.harrysto.claude.Main;
import fr.harrysto.claude.listener.Key;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class createkey implements CommandExecutor {

    public Main plugin;

    public createkey(Main instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {

        if(args.length == 2){
            String name;
            String player;

            name = args[0];
            player = args[1];

            ItemStack item = new ItemStack(Material.valueOf("STICK"));
            ItemMeta vk = item.getItemMeta();
            vk.setDisplayName(name + "," + player);
            vk.setLore(Arrays.asList(plugin.getConfig().getString("message.commands.key-lore")));
            item.setItemMeta(vk);
            Player p = (Player) sender;
            p.getInventory().addItem(item);
            p.updateInventory();

        } else {
            sender.sendMessage(plugin.getConfig().getString("message.commands.syntax-error-key"));
        }


        return false;
    }
}
