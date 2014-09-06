package latmod.emcc.item.tools;
import java.util.Set;

import latmod.core.*;
import latmod.emcc.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.collect.Sets;

public class ItemUUPick extends ItemToolEMCC
{
	public static final Set<Block> effective = Sets.newHashSet(new Block[] {Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail});
	
	public ItemUUPick(String s)
	{
		super(s, effective);
		
		setHarvestLevel(EnumToolClass.PICKAXE.toolClass, EnumToolClass.ALUMITE);
	}
	
	public void loadRecipes()
	{
		if(EMCC.mod.config().tools.enablePick)
			EMCC.mod.recipes().addRecipe(new ItemStack(this), "UUU", " S ", " S ",
					'U', EMCCItems.UU_ITEM,
					'S', ODItems.STICK);
	}
	
	public boolean canHarvestBlock(Block b, ItemStack is)
	{ return true; }
	
	public boolean isEffective(Block b)
	{ return super.isEffective(b) || isEffectiveAgainst(b.getMaterial(), Material.iron, Material.anvil, Material.rock); }
	
	//public float getStrVsBlock(ItemStack is, Block b)
	//{ return isEffective(b) ? (efficiencyOnProperMaterial / (isArea(is) ? 8F : 1F)) : 1F; }
	
	public boolean onBlockStartBreak(ItemStack tool, int x, int y, int z, EntityPlayer ep)
	{
		return false;
		/*
		if(!isBlazing(tool)) return false;
		return EMCCUtils.breakBlockWithBlazingItem(ep.worldObj, x, y, z, ep, tool, this);
		*/
	}
	
	public boolean onBlockDestroyed(ItemStack is, World w, Block bid, int x, int y, int z, EntityLivingBase el)
    {
		return super.onBlockDestroyed(is, w, bid, x, y, z, el);
		//return EMCCUtils.destroyBlockArea(w, x, y, z, el, is, bid, this);
    }
}