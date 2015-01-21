package latmod.emcc.client.gui;
import latmod.core.*;
import latmod.core.gui.*;
import latmod.core.mod.LC;
import latmod.emcc.*;
import latmod.emcc.api.IEmcStorageItem;
import latmod.emcc.client.container.ContainerCondenser;
import latmod.emcc.tile.TileCondenser;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiCondenser extends GuiLM
{
	public static final ResourceLocation texLoc = EMCC.mod.getLocation("textures/gui/condenser.png");
	
	public final TextureCoords
	texBar = new TextureCoords(texLoc, 0, 240),
	texTarget = new TextureCoords(texLoc, 176, 0),
	texTinyPressed = new TextureCoords(texLoc, 176, 16);
	
	public TileCondenser condenser;
	public ButtonLM buttonSettings, buttonSafeMode, buttonTransItems;
	public WidgetLM barEMC, targetIcon;
	
	public GuiCondenser(final ContainerCondenser c)
	{
		super(c, texLoc);
		condenser = (TileCondenser)c.inv;
		ySize = 240;
		
		widgets.add(buttonSettings = new ButtonLM(this, 153, 7, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				LatCoreMC.openClientGui(c.player, condenser, 1);
				playClickSound();
			}
		});
		
		buttonSettings.title = LC.mod.translate("button.settings");
		
		widgets.add(buttonSafeMode = new ButtonLM(this, 153, 25, 7, 6)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton(EMCCGuis.Buttons.SAFE_MODE, b);
				playClickSound();
			}
			
			public void addMouseOverText(FastList<String> l)
			{
				l.add(condenser.safeMode.getTitle());
				l.add(condenser.safeMode.getText());
			}
		});
		
		widgets.add(buttonTransItems = new ButtonLM(this, 162, 25, 7, 6)
		{
			public void onButtonPressed(int b)
			{
				if(b == 0)
				{
					condenser.sendClientAction(TileCondenser.ACTION_TRANS_ITEMS, null);
					playClickSound();
				}
			}
		});
		
		buttonTransItems.title = EMCC.mod.translate("takeitems");
		
		barEMC = new WidgetLM(this, 30, 9, 118, 16)
		{
			public void addMouseOverText(FastList<String> l)
			{
				ItemStack tar = condenser.items[TileCondenser.SLOT_TARGET];
				
				double emc1 =  EMCC.getEMC(tar);
				
				boolean charging = tar != null && tar.getItem() instanceof IEmcStorageItem;
				
				boolean repairing = tar != null && !charging && condenser.repairTools.isOn() && tar.isItemStackDamageable() && !tar.isStackable();
				
				if(repairing && tar.getItemDamage() > 0)
				{
					ItemStack tar1 = tar.copy();
					if(tar1.hasTagCompound())
						tar1.stackTagCompound.removeTag("ench");
					
					ItemStack tar2 = tar1.copy();
					tar2.setItemDamage(tar1.getItemDamage() - 1);
					
					double ev = EMCC.getEMC(tar1);
					double ev2 = EMCC.getEMC(tar2);
					
					emc1 = ev2 - ev;
				}
				
				l.add(EnumChatFormatting.GOLD.toString() + "" + formatEMC(condenser.storedEMC) + (emc1 <= 0D ? "" : (" / " + formatEMC(emc1))));
				if(charging && condenser.storedEMC > 0D) l.add(EMCC.mod.translate("charging"));
				else if(emc1 > 0D && repairing && tar.getItemDamage() > 0) l.add(EMCC.mod.translate("repairing"));
			}
		};
		
		targetIcon = new WidgetLM(this, 8, 9, 16, 16);
		targetIcon.title = EMCC.mod.translate("notarget");
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int mx, int my)
	{
		boolean b = GL11.glIsEnabled(GL11.GL_LIGHTING);
		if(b) GL11.glDisable(GL11.GL_LIGHTING);
		
		super.drawGuiContainerBackgroundLayer(f, mx, my);
		
		ItemStack tar = condenser.items[TileCondenser.SLOT_TARGET];
		
		double emc1 =  EMCC.getEMC(tar);
		
		boolean charging = tar != null && tar.getItem() instanceof IEmcStorageItem;
		
		boolean repairing = tar != null && !charging && condenser.repairTools.isOn() && tar.isItemStackDamageable() && !tar.isStackable();
		
		if(repairing && tar.getItemDamage() > 0)
		{
			ItemStack tar1 = tar.copy();
			if(tar1.hasTagCompound())
				tar1.stackTagCompound.removeTag("ench");
			
			ItemStack tar2 = tar1.copy();
			tar2.setItemDamage(tar1.getItemDamage() - 1);
			
			double ev = EMCC.getEMC(tar1);
			double ev2 = EMCC.getEMC(tar2);
			
			emc1 = ev2 - ev;
		}
		
		if(emc1 > 0L)
			barEMC.render(texBar, (condenser.storedEMC % emc1) / emc1, 1D);
		
		if(condenser.items[TileCondenser.SLOT_TARGET] == null)
			targetIcon.render(texTarget);
		
		if(condenser.safeMode.isOn())
			buttonSafeMode.render(texTinyPressed);
		
		if(buttonTransItems.mouseOver(mx, my) && Mouse.isButtonDown(0))
			buttonTransItems.render(texTinyPressed);
		
		buttonSettings.render(button_settings);
		
		if(b) GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	public void drawScreen(int mx, int my, float f)
	{
		String s = targetIcon.title + "";
		targetIcon.title = (condenser.items[TileCondenser.SLOT_TARGET] == null) ? s : null;
		
		super.drawScreen(mx, my, f);
		
		targetIcon.title = s;
	}
	
	public static String formatEMC(double d)
	{
		d = ((long)(d * 1000D)) / 1000D;
		
		String s = "" + d;
		
		if(!LC.proxy.isShiftDown())
		{
			if(d > 1000)
			{
				double d1 = d / 1000D;
				d1 = ((long)(d1 * 1000D)) / 1000D;
				s = "" + d1 + "K";
			}
			
			if(d > 1000000)
			{
				double d1 = d / 1000000D;
				d1 = ((long)(d1 * 100D)) / 100D;
				s = "" + d1 + "M";
			}
		}
		
		if(s.endsWith(".0"))
			s = s.substring(0, s.length() - 2);
		
		return s;
	}
}