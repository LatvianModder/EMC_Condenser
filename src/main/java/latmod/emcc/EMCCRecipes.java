package latmod.emcc;

import latmod.core.recipes.LMRecipes;
import net.minecraft.item.ItemStack;

import com.pahimar.ee3.recipe.RecipesAludel;

public class EMCCRecipes extends LMRecipes
{
	public static final EMCCRecipes instance = new EMCCRecipes();

	public void addInfusing(ItemStack out, ItemStack in, ItemStack with)
	{ RecipesAludel.getInstance().addRecipe(out, in, with); }
}