package fr.harrysto.claude.recipe;

import fr.harrysto.claude.Main;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class NewKey {

    private Main plugin;

    public NewKey(Main main){
        this.plugin = main;
    }

    public ShapedRecipe addRecipeNewKey(){
        ItemStack nk = new ItemStack(Material.BONE, 1);
        ItemMeta key = nk.getItemMeta();
        key.setDisplayName("Clé non utilisable");
        key.setLore(Arrays.asList("Clique sur une Porte/Coffre/Four pour le claim"));
        nk.setItemMeta(key);

        ShapedRecipe NewKey = new ShapedRecipe(nk);
        NewKey.shape("   ", " I ", "GTG");
        NewKey.setIngredient('G', Material.GOLD_NUGGET);
        NewKey.setIngredient('I', Material.IRON_NUGGET);
        NewKey.setIngredient('T', Material.TRIPWIRE_HOOK);
        return NewKey;
    }


}
