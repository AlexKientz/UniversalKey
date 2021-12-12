package fr.harrysto.claude.recipe;

import fr.harrysto.claude.Main;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class thiefkey {

    private Main plugin;

    public thiefkey(Main main){this.plugin = main;}

    public ShapedRecipe addRecipeThiefKey(){
        ItemStack chorusfruit = new ItemStack(Material.CHORUS_FRUIT_POPPED);
        ItemMeta keythief = chorusfruit.getItemMeta();
        keythief.setDisplayName("§eVoleur de clé");
        keythief.setLore(Arrays.asList("Cet item se permet de volé de clé aux autres joueurs.", "§cAttention cette item n'a que 1 de durabilité !"));
        chorusfruit.setItemMeta(keythief);

        ShapedRecipe ThiefKey = new ShapedRecipe(chorusfruit);
        ThiefKey.shape("   ", " X ", "XIX");
        ThiefKey.setIngredient('X', Material.IRON_INGOT);
        ThiefKey.setIngredient('I', Material.STICK);
        return ThiefKey;
    }

}
