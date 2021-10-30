package fr.harrysto.claude;


import fr.harrysto.claude.commands.VkeyCommands;
import fr.harrysto.claude.commands.keylist;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.plaf.synth.Region;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements Listener {

    public static Main INSTANCE;
    public ConfigFile config;


    public List<RegionManager> regions;

    @Override
    public void onEnable() {

        INSTANCE = this;

        reloadConfig();

        this.config = new ConfigFile(this, "config.yml");

        Bukkit.getPluginManager().registerEvents(new ClaimManager(), this);
        regions = new ArrayList<>();
        for(String uuids : config.get().getKeys(false)){
            List<String> coo = config.get().getStringList(uuids);

            Location pos1 = new Location(Bukkit.getWorld(coo.get(7)), Double.parseDouble(coo.get(0)), Double.parseDouble(coo.get(1)), Double.parseDouble(coo.get(2)));
            Location pos2 = new Location(Bukkit.getWorld(coo.get(7)), Double.parseDouble(coo.get(0)), Double.parseDouble(coo.get(1)), Double.parseDouble(coo.get(2)));

            RegionManager region = new RegionManager(pos1, pos2, coo.get(8));
            this.regions.add(region);
        }

        getCommand("vkey").setExecutor(new VkeyCommands());
        getCommand("vkeylist").setExecutor(new keylist());

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);

        super.onEnable();
    }

    @Override
    public void onDisable() {}

    public static Main getINSTANCE(){
        return INSTANCE;
    }


    public class ConfigFile {
        private File file;
        private YamlConfiguration conf;

        public ConfigFile(JavaPlugin plugin, String fileName) {
            this.file = new File(plugin.getDataFolder(), fileName);
            if (!file.exists())
                try {
                    if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
                    InputStream in = plugin.getResource(fileName);
                    if (in != null) {
                        OutputStream out = new FileOutputStream(file);

                        byte[] buf = new byte[1024 * 4];
                        int len = in.read(buf);
                        while (len != -1) {
                            out.write(buf, 0, len);
                            len = in.read(buf);
                        }
                        out.close();
                        in.close();
                    }
                    else file.createNewFile();
                } catch(Exception e) {e.printStackTrace();}
            reload();
        }

        public void reload() {
            try {
                conf = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            }
            catch (UnsupportedEncodingException | FileNotFoundException e){ e.printStackTrace();  }

        }

        public YamlConfiguration get(){
            return conf;
        }

        public void save(){
            try {
                conf.save(file);
            } catch (IOException e){e.printStackTrace();}
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.updateInventory();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Block b = event.getClickedBlock();
        Action action = event.getAction();
        ItemStack it = event.getItem();

        if (it != null && it.getType() == Material.NETHER_STAR) {
            Inventory trousseau = Bukkit.createInventory(player, 18, "§4Trousseau de clé de " + player.getName());
            player.openInventory(trousseau);

        }

    }
}

