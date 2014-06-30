package latmod.emcc.gui;
import latmod.core.base.ContainerLM;
import latmod.emcc.EMCC;
import latmod.emcc.tile.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ContainerCondenser extends ContainerLM
{
	public ContainerCondenser(EntityPlayer ep, TileCondenser t)
	{
		super(ep, t);
		
		addSlotToContainer(new Slot(t, TileCondenser.SLOT_TARGET, 8, 9));
		
		for(int i = 0; i < TileCondenser.CHEST_SLOTS.length; i++)
		{
			int x = i % 9;
			int y = i / 9;
			
			addSlotToContainer(new Slot(t, TileCondenser.CHEST_SLOTS[i], 8 + x * 18, 36 + y * 18));
		}
		
		for(int y = 0; y < 3; y++) for(int x = 0; x < 9; x++)
		addSlotToContainer(new Slot(ep.inventory, x + y * 9 + 9, 8 + x * 18, 140 + y * 18));
		
		for(int x = 0; x < 9; x++)
		addSlotToContainer(new Slot(ep.inventory, x, 8 + x * 18, 198));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer ep)
	{ return true; }
	
	public ItemStack transferStackInSlot(EntityPlayer ep, int i)
	{
		ItemStack is = null;
		Slot slot = (Slot)inventorySlots.get(i);
		
		if (slot != null && slot.getHasStack())
		{
			ItemStack is1 = slot.getStack();
			is = is1.copy();
			
			if (i < TileCondenser.CHEST_SLOTS.length)
			{
				if (!mergeItemStack(is1, TileCondenser.CHEST_SLOTS.length, inventorySlots.size(), true))
					return null;
			}
			else if (!mergeItemStack(is1, 0, TileCondenser.CHEST_SLOTS.length, false))
				return null;
			
			if (is1.stackSize == 0)
				slot.putStack((ItemStack)null);
			else slot.onSlotChanged();
		}
		
		return is;
	}

	public ResourceLocation getTexture()
	{ return EMCC.mod.getLocation("textures/gui/condenser.png"); }
}