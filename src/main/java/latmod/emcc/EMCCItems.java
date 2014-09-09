package latmod.emcc;
import latmod.emcc.block.*;
import latmod.emcc.item.*;
import latmod.emcc.item.tools.*;
import net.minecraft.item.ItemStack;

import com.pahimar.ee3.init.ModItems;

public class EMCCItems
{
	public static BlockUUBlock b_uu_block;
	public static BlockCondenser b_condenser;
	public static ItemMaterials i_mat;
	public static ItemEmcBattery i_emc_battery;
	public static ItemLifeRing i_life_ring;
	public static ItemBlackHoleBand i_black_hole_band;
	
	public static ItemUUWrench i_wrench;
	public static ItemUUSword i_sword;
	public static ItemUUPick i_pick;
	public static ItemUUShovel i_shovel;
	public static ItemUUAxe i_axe;
	public static ItemUUHoe i_hoe;
	public static ItemUUSmasher i_smasher;
	
	public static ItemStack BLOCK_UUS;
	public static ItemStack CONDENSER;
	
	public static ItemStack ITEM_UUS;
	public static ItemStack MINIUM_STAR;
	public static ItemStack NUGGET_EMERALD;
	public static ItemStack INGOT_UUS;
	
	public static ItemStack DUST_VERDANT;
	public static ItemStack DUST_AZURE;
	public static ItemStack DUST_MINIUM;
	
	public static void load()
	{
		DUST_VERDANT = new ItemStack(ModItems.alchemicalDust, 1, 1);
		DUST_AZURE = new ItemStack(ModItems.alchemicalDust, 1, 2);
		DUST_MINIUM = new ItemStack(ModItems.alchemicalDust, 1, 3);
	}
}