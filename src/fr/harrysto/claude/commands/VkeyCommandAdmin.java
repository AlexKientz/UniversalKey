package fr.harrysto.claude.commands;

import fr.harrysto.claude.Main;
import javafx.print.PageLayout;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class VkeyCommandAdmin implements CommandExecutor {

    public static int bypass = 0;

    public Main plugin;

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
                        player.sendMessage(plugin.getConfig().getString("message.bypass-on"));
                        VkeyCommandAdmin.bypass = 1;

                    }
                    if (args[1].equalsIgnoreCase("false")) {
                        player.sendMessage(plugin.getConfig().getString("message.bypass-off"));
                        VkeyCommandAdmin.bypass = 0;
                    }
                }
            }
            return true;
        }
}
