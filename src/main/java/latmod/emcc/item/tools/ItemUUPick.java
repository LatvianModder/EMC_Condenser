package latmod.emcc.item.tools;

import ftb.lib.api.item.ODItems;
import ftb.lib.api.item.Tool;
import latmod.emcc.api.EMCCUtils;
import latmod.emcc.api.ToolInfusion;
import latmod.emcc.config.EMCCConfigTools;
import latmod.emcc.item.ItemMaterialsEMCC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class ItemUUPick extends ItemToolEMCC
{
	public static final List<Block> effectiveBlocks = Arrays.asList(Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail);
	public static final List<Material> effectiveMaterials = Arrays.asList(Material.iron, Material.anvil, Material.rock);
	
	public ItemUUPick(String s)
	{
		super(s);
		setHarvestLevel(Tool.Type.PICK, Tool.Level.ALUMITE);
	}
	
	@Override
	public void loadRecipes()
	{
		if(EMCCConfigTools.tools.getAsBoolean())
			getMod().recipes.addRecipe(new ItemStack(this), "UUU", " S ", " S ", 'U', ItemMaterialsEMCC.INGOT_UUS, 'S', ODItems.STICK);
	}
	
	@Override
	public boolean isEffective(Block b)
	{ return effectiveBlocks.contains(b) || effectiveMaterials.contains(b.getMaterial()); }
	
	@Override
	public boolean onBlockStartBreak(ItemStack tool, int x, int y, int z, EntityPlayer ep)
	{
		return false;
		/*
		if(!isBlazing(tool)) return false;
		return EMCCUtils.breakBlockWithBlazingItem(ep.worldObj, x, y, z, ep, tool, this);
		*/
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack is, World w, Block bid, int x, int y, int z, EntityLivingBase el)
	{
		EMCCUtils.destroyBlockArea(w, x, y, z, el, is, bid, this);
		return super.onBlockDestroyed(is, w, bid, x, y, z, el);
	}
	
	@Override
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{ return t.is(ToolInfusion.SHARPNESS, ToolInfusion.UNBREAKING, ToolInfusion.FORTUNE, ToolInfusion.FIRE, ToolInfusion.SILKTOUCH); }
}