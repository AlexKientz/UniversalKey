package fr.harrysto.claude;

import fr.harrysto.claude.commands.VkeyCommandAdmin;
import fr.harrysto.claude.commands.VkeyCommands;
import fr.harrysto.claude.listener.ClaimMenuListener;
import fr.harrysto.claude.listener.ProtectionListener;
import fr.harrysto.claude.listener.TransformKey;
import fr.harrysto.claude.listener.keythief;
import fr.harrysto.claude.recipe.NewKey;
import fr.harrysto.claude.recipe.thiefkey;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;


public class Main extends JavaPlugin implements Listener {

    public static Main main;

    public final Logger logger = Logger.getLogger("Minecraft");

    private NewKey recipeNewKey;
    private thiefkey recipeKeyThief;


    public void onEnable() {
        // Config
        saveDefaultConfig();
        File configFile = new File("plugins/Claude/config.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);

        // Listener
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new ProtectionListener(this), this);
        getServer().getPluginManager().registerEvents(new ClaimMenuListener(this), this);
        getServer().getPluginManager().registerEvents(new TransformKey(this), this);
        getServer().getPluginManager().registerEvents(new keythief(this), this);

        // Command
        getCommand("vkey").setExecutor(new VkeyCommands(this));
        getCommand("vkeyadmin").setExecutor(new VkeyCommandAdmin(this));

        // Recipe
        this.recipeNewKey = new NewKey(this);
        this.getServer().addRecipe(this.recipeNewKey.addRecipeNewKey());

        this.recipeKeyThief = new thiefkey(this);
        this.getServer().addRecipe(this.recipeKeyThief.addRecipeThiefKey());

        // Message
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " Has Been Enabled !");
    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " Has Been Disable");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.updateInventory();
        VkeyCommandAdmin.bypass = 0;
    }

}



