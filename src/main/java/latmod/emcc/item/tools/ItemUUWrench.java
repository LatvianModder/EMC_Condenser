package latmod.emcc.item.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.api.item.ODItems;
import latmod.emcc.EMCC;
import latmod.emcc.EMCCItems;
import latmod.emcc.api.IEmcWrenchable;
import latmod.emcc.api.ToolInfusion;
import latmod.emcc.config.EMCCConfigTools;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemUUWrench extends ItemToolEMCC
{
	private static final String NBT_KEY = "WrenchData";
	
	@SideOnly(Side.CLIENT)
	public IIcon icon_full;
	
	public ItemUUWrench(String s)
	{
		super(s);
		setMaxDamage(32);
		setMaxStackSize(1);
		setFull3D();
	}
	
	@Override
	public void loadRecipes()
	{
		if(EMCCConfigTools.wrench.getAsBoolean())
			getMod().recipes.addRecipe(new ItemStack(this), "UBU", " S ", " S ", 'U', EMCCItems.b_uu_block, 'S', ODItems.STICK, 'B', EMCCItems.i_emc_battery);
	}
	
	@Override
	public int getItemEnchantability()
	{ return 15; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		itemIcon = ir.registerIcon(EMCC.mod.lowerCaseModID + ":tools/wrench");
		icon_full = ir.registerIcon(EMCC.mod.lowerCaseModID + ":tools/wrench_full");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack is, int r)
	{
		if(is.stackTagCompound != null && is.stackTagCompound.hasKey(NBT_KEY)) return icon_full;
		return itemIcon;
	}
	
	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World w, int x, int y, int z, int side, float x1, float y1, float z1)
	{
		if(!ep.canPlayerEdit(x, y, z, side, is)) return false;
		
		if(ep.isSneaking())
		{
			if(!w.isRemote)
			{
				NBTTagCompound tag = (NBTTagCompound) (is.hasTagCompound() ? is.stackTagCompound.getTag(NBT_KEY) : null);
				
				if(tag == null)
				{
					TileEntity te = w.getTileEntity(x, y, z);
					
					if(te != null && te instanceof IEmcWrenchable)
					{
						IEmcWrenchable wr = (IEmcWrenchable) te;
						
						if(wr.canWrench(ep))
						{
							tag = new NBTTagCompound();
							wr.writeToWrench(tag);
							
							if(!is.hasTagCompound()) is.stackTagCompound = new NBTTagCompound();
							tag.setString("PlaceBlock", Block.blockRegistry.getNameForObject(w.getBlock(x, y, z)));
							tag.setShort("PlaceMetadata", (short) w.getBlockMetadata(x, y, z));
							is.stackTagCompound.setTag(NBT_KEY, tag);
							
							wr.onWrenched(ep, is);
							w.setBlockToAir(x, y, z);
						}
					}
				}
				else
				{
					x += ForgeDirection.VALID_DIRECTIONS[side].offsetX;
					y += ForgeDirection.VALID_DIRECTIONS[side].offsetY;
					z += ForgeDirection.VALID_DIRECTIONS[side].offsetZ;
					
					if(w.isAirBlock(x, y, z))
					{
						String placeId = tag.getString("PlaceBlock");
						
						if(placeId.isEmpty()) return true;
						
						int placeMeta = tag.getShort("PlaceMetadata");
						
						Block b = (Block) Block.blockRegistry.getObject(placeId);
						
						w.setBlock(x, y, z, b);
						w.setBlockMetadataWithNotify(x, y, z, placeMeta, 3);
						
						TileEntity te = w.getTileEntity(x, y, z);
						
						if(te != null && te instanceof IEmcWrenchable)
						{
							IEmcWrenchable wr = (IEmcWrenchable) te;
							
							b.onBlockPlacedBy(w, x, y, z, ep, ep.getHeldItem());
							wr.readFromWrench(tag);
							
							is.stackTagCompound.removeTag(NBT_KEY);
							is.damageItem(1, ep);
						}
					}
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	public double getEmcPerDmg(ItemStack is)
	{ return EMCCConfigTools.tool_emc_per_damage.getAsDouble(); }
	
	@Override
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{ return t.is(ToolInfusion.UNBREAKING); }
	
	@Override
	public boolean isEffective(Block b)
	{ return false; }
}